/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.exceptions.ConfigException;
import io.adamantic.quicknote.types.ChannelState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Entry point for the Quicknote library.
 */
@Slf4j
public class Quicknote {

    @Getter
    private static String clientId = UUID.randomUUID().toString();

    /**
     * Changes the default client ID used by the library. This is
     * useful to get advanced delivery/redelivery semantics.
     * @param id the new client ID to use.
     */
    public static void clientId(String id) {
        clientId = id;
    }

    /**
     * Lazily creates and initializes a singleton instance
     * of the Quicknote library.
     * @return the instance, configured and ready for use
     */
    public static synchronized Quicknote instance() {

        if (instance == null) {
            instance = new Quicknote();
        }
        return instance;
    }

    private Quicknote() {
        config = new QuicknoteConfig();
        config.globalConfig(); // fail fast, and save time for channel instantiation
        log.info("Quicknote subsystem initialized.");
    }

    public Sender sender(String name) throws ChannelNotFound {
        return delegateSender(name);
    }

    public Receiver receiver(String name) throws ChannelNotFound {
        return delegateReceiver(name);
    }

    public Connector connector(String name) {
        synchronized (connectors) {
            Connector conn = connectors.get(name);
            if (conn != null) {
                if (conn.state() == ChannelState.OPEN) {
                    return conn;
                }
                log.warn("Connector found for name [{}], but status is [{}], re-creating", name, conn.state().name());
                conn.close();
            }
            return createConnector(name);
        }
    }

    private Connector createConnector(String name) {
        log.debug("Attempting to create connector [{}]", name);
        var connConfig = config.configForConnector(name);
        String connClass = connConfig.getString("class");
        if (connClass == null) {
            throw new ConfigException("[class] is not set for connector [" + name + "]");
        }
        try {
            Class<?> clsInstance = Class.forName(connClass);
            Connector cnn = (Connector) clsInstance.getDeclaredConstructor().newInstance();
            cnn.initialize(config);
            cnn.open();
            connectors.put(name, cnn);
            log.info("Created and started new connector [{}]", name);
            return cnn;
        } catch (Exception exc) {
            throw new ConfigException("Cannot create connector [" + name + "]", exc);
        }
    }

    private Sender delegateSender(String name) throws ChannelNotFound {
        log.debug("Getting or creating sender [{}]", name);
        var senderConfig = config.configForSender(name);
        String connName = senderConfig.getString("connector");
        if (connName == null) {
            throw new ConfigException("[connector] is not set for sender [" + name + "]");
        }
        Connector conn = connector(connName);
        return conn.sender(name);
    }

    private Receiver delegateReceiver(String name) throws ChannelNotFound {
        log.debug("Getting or creating receiver [{}]", name);
        var receiverConfig = config.configForReceiver(name);
        String connName = receiverConfig.getString("connector");
        if (connName == null) {
            throw new ConfigException("[connector] is not set for receiver [" + name + "]");
        }
        Connector conn = connector(connName);
        return conn.receiver(name);
    }


    @Getter
    private final QuicknoteConfig config;
    private final Map<String, Connector> connectors = new HashMap<>();

    private static Quicknote instance;


}



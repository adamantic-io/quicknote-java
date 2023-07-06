package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.exceptions.ConfigException;
import io.adamantic.quicknote.types.ChannelState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for the Quicknote library.
 */
@Slf4j
public class Quicknote {
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
        synchronized (senders) {
            Sender sender = senders.get(name);
            if (sender != null) {
                if (sender.state() == ChannelState.OPEN) {
                    return sender;
                }
                log.warn("Sender found for name [{}], but status is [{}], re-creating", name, sender.state().name());
                sender.close();
            }
            return createSender(name);
        }
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
            cnn.initialize(connConfig);
            cnn.open();
            connectors.put(name, cnn);
            log.info("Created and started new connector [{}]", name);
            return cnn;
        } catch (Exception exc) {
            throw new ConfigException("Cannot create connector [" + name + "]", exc);
        }
    }

    private Sender createSender(String name) {
        log.debug("Attempting to create sender [{}]", name);
        var scfg = config.configForSender(name);
        return null;
    }


    @Getter
    private final QuicknoteConfig config;
    private final Map<String, Sender>    senders = new HashMap<>();
    private final Map<String, Connector> connectors = new HashMap<>();

    private static Quicknote instance;


}



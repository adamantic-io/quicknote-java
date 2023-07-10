/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ConfigException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.tree.ImmutableNode;

/**
 * Quicknote main configuration class.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class QuicknoteConfig {

    /**
     * Returns the global configuration object.
     * @return the global configuration object.
     */
    public synchronized BaseHierarchicalConfiguration globalConfig() {
        if (config == null) loadConfig();
        return config;
    }

    /**
     * Returns the main configuration entry point for a specific connector.
     * @param name the name of the connector.
     * @return a configuration object for the connector.
     * @throws ConfigException if the configuration for the connector is not found.
     */
    public HierarchicalConfiguration<ImmutableNode> configForConnector(String name) {
        return loadChildOrThrowConfigException("quicknote.connectors." + name);
    }

    /**
     * Returns the main configuration entry point for a specific sender.
     * @param name the name of the sender.
     * @return a configuration object for the sender.
     * @throws ConfigException if the configuration for the sender is not found.
     */
    public HierarchicalConfiguration<ImmutableNode> configForSender(String name) {
        return loadChildOrThrowConfigException("quicknote.senders." + name);
    }

    /**
     * Returns the main configuration entry point for a specific receiver.
     * @param name the name of the receiver.
     * @return a configuration object for the receiver.
     * @throws ConfigException if the configuration for the receiver is not found.
     */
    public HierarchicalConfiguration<ImmutableNode> configForReceiver(String name) {
        return loadChildOrThrowConfigException("quicknote.receivers." + name);
    }

    /**
     * Loads a string from configuration, requiring that it's not empty.
     * @param c the configuration object from which to load the string.
     * @param path the path to the string in the configuration object.
     * @return the string loaded from configuration.
     * @throws ConfigException if the string is not found or is empty.
     */
    public static String requireStringNotEmpty(Configuration c, String path) throws ConfigException {
        String val = c.getString(path, null);
        if (val == null || val.isEmpty()) {
            throw new ConfigException("Required configuration property '" + path + "' not found.");
        }
        return val;
    }

    private void loadConfig() {
        try {
            initDotEnv();
            var c = new Configurations().fileBased(YAMLConfiguration.class, "quicknote.yaml");
            c.getInterpolator().addDefaultLookup(System::getenv);
            c.getInterpolator().addDefaultLookup(System::getProperty);
            config = c;
        } catch (Exception exc) {
            throw new ConfigException("Cannot initialize Quicknote configuration.", exc);
        }
    }

    private HierarchicalConfiguration<ImmutableNode> loadChildOrThrowConfigException(String name) {
        var cfg = globalConfig().configurationAt(name);
        if (cfg == null) {
            throw new ConfigException("missing: " + name);
        }
        return cfg;
    }
    private void initDotEnv() {
        Dotenv
                .configure()
                .ignoreIfMissing()
                .systemProperties()
                .load();
    }

    private BaseHierarchicalConfiguration config;

}

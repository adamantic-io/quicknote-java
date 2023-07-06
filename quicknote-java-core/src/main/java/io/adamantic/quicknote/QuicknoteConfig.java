package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ConfigException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.tree.ImmutableNode;

public class QuicknoteConfig {

    public synchronized BaseHierarchicalConfiguration globalConfig() {
        if (config == null) loadConfig();
        return config;
    }

    public HierarchicalConfiguration<ImmutableNode> configForConnector(String name) {
        return loadChildOrThrowConfigException("quicknote.connectors." + name);
    }

    public HierarchicalConfiguration<ImmutableNode> configForSender(String name) {
        return loadChildOrThrowConfigException("quicknote.senders." + name);
    }

    public HierarchicalConfiguration<ImmutableNode> configForReceiver(String name) {
        return loadChildOrThrowConfigException("quicknote.receivers." + name);
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

package com.test.automation.common.framework;


import org.apache.commons.configuration.AbstractHierarchicalFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Implements Yaml format for Commons Configuration
 */
public class YamlConfiguration extends AbstractHierarchicalFileConfiguration {

    public YamlConfiguration(String yamlPath) throws ConfigurationException {
	        super(yamlPath);
    }

    @Override
    public void load(Reader in) throws ConfigurationException {
        Yaml yaml = new Yaml();
        Iterable<Object> objects = yaml.loadAll(in);
        for (Object object : objects) {
            addProperties(null, object);
        }
    }

    @SuppressWarnings("unchecked")
    private void addProperties(String context, Object properties) {
        if (properties != null) {
            if (properties instanceof Map) {
                Map<String, ?> propertiesMap = (Map<String, ?>) properties;
                for (Map.Entry<String, ?> entry : propertiesMap.entrySet()) {
                    addProperties((context != null ? context + "." : "") + entry.getKey(), entry.getValue());
                }
            } else if (properties instanceof List) {
                addProperty(context, properties);
            } else {
                addProperty(context, properties.toString());
            }
        } else {
            addProperty(context, null);
        }
    }

    @Override
    public void save(Writer out) throws ConfigurationException {
        throw new ConfigurationException("Unimplemented");
    }
}
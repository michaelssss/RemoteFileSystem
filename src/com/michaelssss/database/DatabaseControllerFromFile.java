package com.michaelssss.database;

import com.michaelssss.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by michaelssss on 2017/2/6.
 */
public abstract class DatabaseControllerFromFile implements DatabaseController {
    protected final static Logger logger = Logger.getLogger("DatabaseController");

    private Properties properties;
    protected final static String root = DatabaseControllerFromFile.class.getResource("/").getPath();

    protected DatabaseControllerFromFile() {
        properties = new Properties();
    }

    protected void load(String file) {
        try (InputStream in = new FileInputStream(file)) {
            properties.load(in);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public String getValue(String key) {
        return StringUtils.isNotBlank(properties.getProperty(key)) ? properties.getProperty(key) : "";
    }

    @Override
    public String getValue(String key, String defaultValue) {
        return StringUtils.isNotBlank(getValue(key)) ? getValue(key) : defaultValue;
    }
}

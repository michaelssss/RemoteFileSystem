package com.michaelssss.database;

/**
 * Created by michaelssss on 2017/2/6.
 */
public interface DatabaseController {
    String getValue(String key);

    String getValue(String key, String defaultValue);
}

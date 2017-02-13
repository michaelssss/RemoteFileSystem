package com.michaelssss.database;

import java.nio.file.Paths;

/**
 * Created by michaelssss on 2017/2/6.
 */
public class UserProfileDatabaseController extends DatabaseControllerFromFile {
    private UserProfileDatabaseController() {
        load(root + "/MyProperties.properties");
    }

    private static volatile DatabaseController instance;

    public static DatabaseController getInstance() {
        if (null == instance) {
            instance = new UserProfileDatabaseController();
        }
        return instance;
    }
}

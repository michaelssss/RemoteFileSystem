package com.michaelssss;

import com.michaelssss.database.DatabaseController;
import com.michaelssss.database.UserProfileDatabaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 2017/1/26.
 */
public final class Guardian {
    private static volatile Guardian instance;
    private final static DatabaseController myproperties = UserProfileDatabaseController.getInstance();

    private Guardian() {

    }

    static Guardian getInstance() {
        if (null == instance) {
            instance = new Guardian();
        }
        return instance;
    }

    boolean isLogin(HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String password = request.getHeader("password");
        return myproperties.getValue("username").equalsIgnoreCase(userName) && myproperties.getValue("password").equalsIgnoreCase(password);
    }

    public boolean hasRight2Upload(HttpServletRequest request) {
        return true;
    }
}

package com.demo.util;

import com.demo.entities.User;

public class TestValid {
    public static boolean use(User user) {
        if (user.getName() != null && user.getName().trim() != "" && user.getPassword().trim().length() > 5 && user.getSex() != null)
            return true;
        return false;
    }
}

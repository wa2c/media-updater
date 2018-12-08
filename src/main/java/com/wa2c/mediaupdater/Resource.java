package com.wa2c.mediaupdater;

import java.util.ResourceBundle;

public class Resource {

    private static final ResourceBundle bundle;
    static {
        bundle = ResourceBundle.getBundle("resource");
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

}

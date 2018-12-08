package com.wa2c.mediaupdater;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

public class Logger {

    private static java.util.logging.Logger logger;
    private static File logFile = new File(Resource.get("app.dir"), "app.log");
    static {
        logger = java.util.logging.Logger.getLogger("app");
        try {
            Handler handler = new FileHandler(logFile.getPath(), 1, 1000);
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void d(Object obj) {
        logger.log(Level.FINEST, obj.toString());
    }

    public static void i(Object obj) {
        logger.log(Level.INFO, obj.toString());
    }

    public static void w(Object obj) {
        logger.log(Level.WARNING, obj.toString());
    }

    public static void e(Object obj) {
        if (obj == null)
            return;

        logger.log(Level.SEVERE, obj.toString());
        if (obj instanceof Exception) {
            ((Exception)obj).getStackTrace();
        }
    }
}

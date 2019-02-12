package com.katalon.utils;

class LogUtils {

    static void info(Logger logger, String message) {
        if (logger == null) {
            logger = new ConsoleLogger();
        }
        logger.info(message);
    }
}

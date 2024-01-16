package com.roubsite.trans.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Log {
    private static Logger log;

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        LoggerConfig loggerConfig = ctx.getConfiguration().getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.DEBUG);
        log = (Logger) ctx.getLogger(Log.class);
    }

    public static void trace(String s) {
        log.trace(s);
    }


    public static void trace(String s, Object o) {
        log.trace(s, o);
    }


    public static void trace(String s, Object o, Object o1) {
        log.trace(s, o, o1);
    }


    public static void trace(String s, Object... objects) {
        log.trace(s, objects);
    }


    public static void trace(String s, Throwable throwable) {
        log.trace(s, throwable);
    }

    public static void trace(Marker marker, String s) {
        log.trace((Marker) marker, s);
    }


    public static void trace(Marker marker, String s, Object o) {
        log.trace(marker, s, o);
    }


    public static void trace(Marker marker, String s, Object o, Object o1) {
        log.trace(marker, s, o, o1);
    }


    public static void trace(Marker marker, String s, Object... objects) {
        log.trace(marker, s, objects);
    }


    public static void trace(Marker marker, String s, Throwable throwable) {
        log.trace(marker, s, throwable);
    }

    public static void debug(String s) {
        log.debug(s);
    }

    public static void debug(String s, Object obj) {
        log.debug(s, obj);
    }

    public static void debug(String s, Object o, Object o1) {
        log.debug(s, o, o1);
    }

    public static void debug(String s, Object... objects) {
        log.debug(s, objects);
    }


    public static void debug(String s, Throwable throwable) {
        log.debug(s, throwable);
    }

    public static void debug(Marker marker, String s) {
        log.debug(marker, s);
    }


    public static void debug(Marker marker, String s, Object o) {
        log.debug(marker, s, o);
    }


    public static void debug(Marker marker, String s, Object o, Object o1) {
        log.debug(marker, s, o, o1);
    }


    public static void debug(Marker marker, String s, Object... objects) {
        log.debug(marker, s, objects);
    }


    public static void debug(Marker marker, String s, Throwable throwable) {
        log.debug(marker, s, throwable);
    }

    public static void info(String s) {
        log.info(s);
    }


    public static void info(String s, Object o) {
        log.info(s, o);
    }


    public static void info(String s, Object o, Object o1) {
        log.info(s, o, o1);
    }


    public static void info(String s, Object... objects) {
        log.info(s, objects);
    }


    public static void info(String s, Throwable throwable) {
        log.info(s, throwable);
    }

    public static void info(Marker marker, String s) {
        log.info(marker, s);
    }


    public static void info(Marker marker, String s, Object o) {
        log.info(marker, s, o);
    }


    public static void info(Marker marker, String s, Object o, Object o1) {
        log.info(marker, s, o, o1);
    }


    public static void info(Marker marker, String s, Object... objects) {
        log.info(marker, s, objects);
    }


    public static void info(Marker marker, String s, Throwable throwable) {
        log.info(marker, s, throwable);
    }

    public static void warn(String s) {
        log.warn(s);
    }


    public static void warn(String s, Object o) {
        log.warn(s, o);
    }


    public static void warn(String s, Object... objects) {
        log.warn(s, objects);
    }


    public static void warn(String s, Object o, Object o1) {
        log.warn(s, o, o1);
    }


    public static void warn(String s, Throwable throwable) {
        log.warn(s, throwable);
    }

    public static void warn(Marker marker, String s) {
        log.warn(marker, s);
    }


    public static void warn(Marker marker, String s, Object o) {
        log.warn(marker, s, o);
    }


    public static void warn(Marker marker, String s, Object o, Object o1) {
        log.warn(marker, s, o, o1);
    }


    public static void warn(Marker marker, String s, Object... objects) {
        log.warn(marker, s, objects);
    }


    public static void warn(Marker marker, String s, Throwable throwable) {
        log.warn(marker, s, throwable);
    }

    public static void error(String s) {
        log.error(s);
    }


    public static void error(String s, Object o) {
        log.error(s, o);
    }


    public static void error(String s, Object o, Object o1) {
        log.error(s, o, o1);
    }


    public static void error(String s, Object... objects) {
        log.error(s, objects);
    }


    public static void error(String s, Throwable throwable) {
        log.error(s, throwable);
    }

    public static void error(Marker marker, String s) {
        log.error(marker, s);
    }


    public static void error(Marker marker, String s, Object o) {
        log.error(marker, s, o);
    }


    public static void error(Marker marker, String s, Object o, Object o1) {
        log.error(marker, s, o, o1);
    }


    public static void error(Marker marker, String s, Object... objects) {
        log.error(marker, s, objects);
    }


    public static void error(Marker marker, String s, Throwable throwable) {
        log.error(marker, s, throwable);
    }

    public boolean isDebugEnabled() {
        return true;
    }
}

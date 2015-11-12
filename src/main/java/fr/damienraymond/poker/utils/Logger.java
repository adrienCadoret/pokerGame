package fr.damienraymond.poker.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

/**
 * Created by damien on 12/11/2015.
 */
public class Logger {

    public static void showLoggerStatut(){
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
    }

    private static ch.qos.logback.classic.Logger logger(){
        return (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("Poker");
    }

    public static void trace(String m){
        Logger.logger().trace(m);
    }

    public static void trace(String m, Throwable t){
        Logger.logger().trace(m, t);
    }



    public static void debug(String m){
        Logger.logger().debug(m);
    }

    public static void debug(String m, Throwable t){
        Logger.logger().debug(m, t);
    }



    public static void info(String m){
        Logger.logger().info(m);
    }

    public static void info(String m, Throwable t){
        Logger.logger().info(m, t);
    }



    public static void warn(String m){
        Logger.logger().warn(m);
    }

    public static void warn(String m, Throwable t){
        Logger.logger().warn(m, t);
    }



    public static void error(String m){
        Logger.logger().error(m);
    }

    public static void error(String m, Throwable t){
        Logger.logger().error(m, t);
    }
}

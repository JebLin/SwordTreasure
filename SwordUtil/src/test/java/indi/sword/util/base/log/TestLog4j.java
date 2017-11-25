package indi.sword.util.base.log;
import org.apache.log4j.Logger;

/**
 * 测试指南：
 * 1、删除目录下文件，然后跑一遍下面的main方法
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/25 17:12
 */
public class TestLog4j {

    private static Logger logger_file = Logger.getLogger("File");
    private static Logger logger_Rolling = Logger.getLogger("Rolling");
    private static Logger logger_DailyRollingFile = Logger.getLogger("DailyRollingFile");
    private static Logger logger_Class = Logger.getLogger(TestLog4j.class);
    private static Logger logger_userInfoLog = Logger.getLogger("userInfoLog");
    public static void main(String[] args) {
        System.out.println("System..............");
        logger_Class.debug("debug");
        logger_Class.info("info");
        logger_Class.warn("warning");
        logger_Class.error("error");

        logger_userInfoLog.info("user");
    }

}

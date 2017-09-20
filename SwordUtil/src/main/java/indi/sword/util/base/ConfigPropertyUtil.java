package indi.sword.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description 获取porperty的工具类
 * @Author:rd_jianbin_lin
 * @Date: 20:18 2017/9/19
 */
public class ConfigPropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigPropertyUtil.class);
    private static Properties props;

//    static {
//        loadProps();
//    }

//    synchronized static private void loadProps() {
    synchronized static private void loadProps() {
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            // 第一种，通过类加载器进行获取properties文件流
//            in = ConfigPropertyUtil.class.getClassLoader().getResourceAsStream("job51.properties");
            in = ConfigPropertyUtil.class.getClass().getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

    public static void main(String[] args) {
        System.out.println(ConfigPropertyUtil.getProperty("jdbc.password"));
//        System.out.println("111");
    }
}
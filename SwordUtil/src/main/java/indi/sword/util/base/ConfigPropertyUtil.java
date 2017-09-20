package indi.sword.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * @Description 加载properties内容
     * @Author:rd_jianbin_lin
     * @Date: 14:26 2017/9/20
     */
    synchronized static private void loadProps(String path) {
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            // 第一种，通过类加载器进行获取properties文件流
//            in = ConfigPropertyUtil.class.getClassLoader().getResourceAsStream("/jdbc.properties");
            in = ConfigPropertyUtil.class.getClass().getResourceAsStream(path);
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

    /**
     * @Description 根据路径获取单个属性值
     * @Author:rd_jianbin_lin
     * @Date: 14:26 2017/9/20
     */
    public static String getProperty(String path,String key) {
        if (null == props) {
            loadProps(path);
        }
        return props.getProperty(key);
    }

    /**
     * @Description 获取全部属性
     * @Author:rd_jianbin_lin
     * @Date: 14:26 2017/9/20
     */
    public static Map<String,String> getAllProperties(String path){
        if (null == props) {
            loadProps(path);
        }

        Map<String,String> propertyMap = new HashMap<>();
        Enumeration<?> propertyKeys = props.propertyNames();
        while(propertyKeys.hasMoreElements()){
            String key = (String)propertyKeys.nextElement();
            propertyMap.put(key,getProperty(path,key));
        }
        return propertyMap;
    }


    public static void main(String[] args) {
        System.out.println(ConfigPropertyUtil.getProperty("/jdbc.properties","jdbc.password"));
        System.out.println(ConfigPropertyUtil.getAllProperties("/jdbc.properties"));

    }
}
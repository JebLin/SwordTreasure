package indi.sword.util.base;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public final class ConfigPropertyUtils {


    @SuppressWarnings("rawtypes")
    public static Map<String, String> getAllInfomation(String properties_path) {
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> information = new HashMap<>();

        try {

            input = ConfigPropertyUtil.class.getClassLoader()
                    .getResourceAsStream(properties_path);

            // load a properties file
            prop.load(input);

            Iterator itrator = prop.entrySet().iterator();
            while (itrator.hasNext()) {
                Entry entry = (Entry) itrator.next();
                information.put(entry.getKey().toString(), entry.getValue()
                        .toString());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return information;
    }

    /**
     * 从配置文件中获取指定的变量
     *
     * @param properties_path 配置文件所在地址
     * @param property_name   变量名
     * @return:
     * @author rongyang_lu
     * @date 2015年8月12日 下午5:00:00
     */
    public static String getProperty(String properties_path,
                                     String property_name) {
        Properties prop = new Properties();
        URL url = ConfigPropertyUtil.class.getClassLoader().getResource(
                properties_path);
        System.out.println("ConfigPropertyUtil.getProperty(),url=" + url.getPath());
        InputStream input = null;
        // FileUtil.readFileAsStream(properties_path);

        try {

            input = ConfigPropertyUtil.class.getClassLoader()
                    .getResourceAsStream(properties_path);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return prop.getProperty(property_name);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ConfigPropertyUtil.getProperty("resources/jdbc.properties", "jdbc.password"));
    }

}

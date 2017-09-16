package indi.sword.util.base;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Description 系统参数配置
 * @Author:rd_jianbin_lin
 * @Date: 10:24 2017/9/16
 */
public class SystemPropertyUtils {

    // ---------------------- JAVA 相关系统配置 ----------------------------
    // JAVA 关键字
    private final static String[] JAVA_KEYWORDS =
            {
                    "assert",
                    "abstract",
                    "continue",
                    "for",
                    "new",
                    "switch",
                    "boolean",
                    "default",
                    "goto",
                    "null",
                    "synchronized",
                    "break",
                    "do",
                    "if",
                    "package",
                    "this",
                    "byte",
                    "double",
                    "implements",
                    "private",
                    "threadsafe",
                    "byvalue",
                    "else",
                    "import",
                    "protected",
                    "throw",
                    "case",
                    "extends",
                    "instanceof",
                    "public",
                    "transient",
                    "catch",
                    "false",
                    "int",
                    "return",
                    "true",
                    "char",
                    "final",
                    "interface",
                    "short",
                    "try",
                    "class",
                    "finally",
                    "long",
                    "static",
                    "void",
                    "const",
                    "float",
                    "native",
                    "super",
                    "while",
                    "volatile",
                    "strictfp"};

    /**
     * @Description 返回JDK版本
     * @Author:rd_jianbin_lin
     * @Date: 10:17 2017/9/16
     */
    public String getJDKVersion() {
        String javaVersion = System.getProperty("java.version");
        return javaVersion.substring(0, 3);
    }

    /**
     * @Descrption 是否合法的Java标识符
     * @author rd_jianbin_lin
     * @Date Jul 15, 2017 6:50:06 PM
     */
    public static boolean isJavaIdentifier(String s) {
        if (StringUtils.isEmpty(s))
            return false;
        else {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!Character.isJavaIdentifierPart(chars[i])
                        || (i == 0 && !Character.isJavaIdentifierStart(chars[i]))) {
                    return false;
                }
            }
            if (Arrays.asList(JAVA_KEYWORDS).contains(s))
                return false;
        }
        return true;
    }

    /**
     * @Descrption 是否合法的Java Class Name
     * @author rd_jianbin_lin
     * @Date Jul 15, 2017 6:50:18 PM
     */
    public static boolean isJavaClassName(String name) {
        if (StringUtils.isEmpty(name))
            return true;
        if (name.startsWith(" ")
                || name.startsWith(".")
                || name.endsWith(" ")
                || name.endsWith(".")) {
            return false;
        } else {
            String[] pks = name.split(".");
            for (int i = 0; i < pks.length; i++) {
                if (!isJavaIdentifier(pks[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    // ---------------------- 电脑 相关系统配置 ----------------------------


    /**
     * @Description 返回系统位数   64位还是32位
     * @Author:rd_jianbin_lin
     * @Date: 10:19 2017/9/16
     */
    public String getSystemBit() {
        String systemBit = System.getProperty("sun.arch.data.model"); //判断是32位还是64位
        return systemBit;
    }

    /**
     * 获取当前操作系统名称
     *
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jul 1, 2017 12:00:00 PM
     * @return
     */
    public static String getCurrentOsName() {
        String osName = System.getProperty("os.name");
        return osName;
    }

    /**
     * 获取当前操作系统文件分隔符
     *
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jul 1, 2017 12:00:14 PM
     * @return
     */
    public static String getCurrentFileSeparator() {
        String separator = System.getProperty("file.separator");
        return separator;
    }

    /**
     * @Description 疯狂输出系统配置
     * @Author:rd_jianbin_lin
     * @Date: 10:44 2017/9/16
     */
    public static void listSystemProperties(){
        Properties properties = System.getProperties();
        properties.list(System.out);
//        Enumeration enumeration = properties.propertyNames();
//        while(enumeration.hasMoreElements()){
//            String property = (String) enumeration.nextElement();
//            System.out.println(property + ":" + properties.getProperty((property)));
//        }
    }




    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.list(System.out);
    }
}

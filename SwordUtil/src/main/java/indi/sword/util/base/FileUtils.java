package indi.sword.util.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @ClassName WSFileUtil
 * @Description 处理与文件操作相关的工具类
 * @author rd_jianbin_lin
 * @date 2015年8月13日 下午2:39:27
 */
public final class FileUtils {

    private final static String ROOT_LINUX = "/var/data/files";
    private final static String ROOT_WIN = "D:/data";
    private final static String IMAGE_PATH = getImagePath();
    private final static String TEMP_PATH = getTempPath();

    /**
     * 文件根目录
     */
    private static String root() {
        String path = "";
        if(SystemPropertyUtils.getCurrentOsName().toLowerCase().startsWith("win")){ // windows
            path = ROOT_WIN;
        }else{
            path = ROOT_LINUX;
        }
        return checkAndCreate(path);
    }

    private static String getImagePath() {
        return checkAndCreate(root() + "/image_save/");
    }

    private static String getTempPath() {
        return checkAndCreate(root() + "/temp_file/");
    }
    /**
     * @Description 判断文件夹是否存在，不存在则创建一个
     * @Author:rd_jianbin_lin
     * @Date: 20:03 2017/9/11
     */
    private static String checkAndCreate(String path) {
        try {
            File file = new File(path);
            if (!file.isDirectory()) {
                file.mkdir();
            }
            return path;
        } catch (Exception e) {
            System.out.println("文件夹创建失败【" + path + "】，请检查文件加创建权限");
            return null;
        }
    }

    /**
     * @Description: TODO 转化Base64字符串为普通字符串
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String getStringFromBase64(String base64_string, String ext) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64_string); // 将base64字符串转换成字节数组；
            String file_path = TEMP_PATH + MD5Util.getMD5() + ext;
            File file = getFileFromBytes(bytes, file_path);
            return readFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO 转化Base64字符串为文件
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static File getFileFromBase64(String base64_string, String ext) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64_string); // 将base64字符串转换成字节数组；
            String file_path = TEMP_PATH + MD5Util.getMD5() + ext;
            File file = getFileFromBytes(bytes, file_path);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO 抓取本地文件获取Base64字符串
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String getStringByBase64(String file_name) {
        File file = new File(file_name);
        String content = null; // base64字符串
        if (file.exists()) {
            byte[] bty = getBytesFromFile(file); // 将文件转换成字节数组；
            BASE64Encoder encoder = new BASE64Encoder();
            content = encoder.encode(bty);// 返回Base64编码过的字节数组字符串
        } else {
            System.out.println("找不到指定的文件！");
        }
        return content;
    }

    /**
     * @Description: TODO 直接从文件获取Base64字符串
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String getStringByBase64(File file) {
        String content = null; // base64字符串
        if (file.exists()) {
            byte[] bty = getBytesFromFile(file); // 将文件转换成字节数组；
            BASE64Encoder encoder = new BASE64Encoder();
            content = encoder.encode(bty);// 返回Base64编码过的字节数组字符串
        } else {
            System.out.println("找不到指定的文件！");
        }
        return content;
    }

    /**
     * @Description: TODO 抓取本地文件获取文本
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String readFile(String file_name, String charset) {
        String result = "";
        try {
            File file = new File(file_name);
            if (file.isFile() && file.exists()) {
                if (StringUtils.isEmpty(charset)) {
                    charset = getCharset(file);
                }
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT = null;
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    result += lineTXT.toString().trim();
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: TODO 抓取本地文件获取文本
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String readFile(String file_name) {
        String charset = getCharset(file_name);
        return readFile(file_name, charset);
    }

    /**
     * @Description: TODO 直接从文件获取文本
     * @author rd_jianbin_lin
     * @date 2015年9月23日 下午3:55:50
     */
    public static String readFile(File file) {
        String result = "";
        try {
            if (file.isFile() && file.exists()) {
                // 获取字符串
                String file_charset = getCharset(file.getPath());
                // System.out.println("读取的文本编码格式为：" + file_charset);
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), file_charset);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT = null;
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    result += lineTXT.toString().trim();
                    // System.out.println("该行文本为：" + lineTXT.toString().trim());
                }
                read.close();
                bufferedReader.close();
            } else {
                System.out.println("找不到指定的文件！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: TODO 根据文件获取字节流
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    public static byte[] getBytesFromFile(String file_name) {
        byte[] bytes = null;
        try {
            if (file_name == null) {
                return null;
            }
            File file = new File(file_name);
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * @Description: TODO 直接从文件获取字节流
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @Description: TODO 根据字节流获取文件
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    public static File getFileFromBytes(byte[] file_bytes, String file_path) {
        BufferedOutputStream buff_out = null;
        FileOutputStream file_out = null;
        File file = null;
        if (StringUtils.isEmpty(file_path)) {
            file_path = TEMP_PATH + MD5Util.getMD5() + ".html";
        }
        try {
            file = new File(file_path);
            file_out = new FileOutputStream(file);
            buff_out = new BufferedOutputStream(file_out);
            buff_out.write(file_bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buff_out != null) {
                try {
                    buff_out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (file_out != null) {
                try {
                    file_out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * @Description: TODO 从文件获取编码
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    public static String getCharset(File file) {
        try {
            // 获取文件的编码
            String file_charset = null;
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
            int p = (bin.read() << 8) + bin.read();
            switch (p) {
                case 0xefbb:
                    file_charset = "UTF-8";
                    break;
                case 0xfffe:
                    file_charset = "Unicode";
                    break;
                case 0xfeff:
                    file_charset = "UTF-16BE";
                    break;
                default:
                    file_charset = "GBK";
            }
            bin.close();

            // 获取字符串编码（通过前20行内的文本来判断）
            int line = 0;
            String content = "";
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), file_charset);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTXT = null;
            while ((lineTXT = bufferedReader.readLine()) != null) {
                line++;
                content += lineTXT.toString().trim();
                if (line >= 20)
                    break;
            }

            // 矫正文件编码
            String str_charset = getStringCode(content);
            read.close();
            bufferedReader.close();
            return str_charset;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO 根据文件路径获取文件编码
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    public static String getCharset(String fileName) {
        File file = new File(fileName);
        return getCharset(file);
    }

    /**
     * @Description: TODO 根据文件路径获取文件编码
     * @author rd_jianbin_lin
     * @date 2015年11月20日 下午5:25:03
     */
    private static String getStringCode(String str) {
        try {
            String encode = "GB2312";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
            encode = "ISO-8859-1";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
            encode = "UTF-8";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
            encode = "GBK";
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO 创建文件并写入文本
     * @author rd_jianbin_lin
     * @date 2016年1月11日 上午11:22:01
     */
    public static void contentToTxt(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                file.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String writeFile(String content, String path) throws FileNotFoundException {
        String result = "";
        String dir = StringUtils.getDir(path);
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStream output = new FileOutputStream(path);
        try {
            output.write(content.getBytes());
        } catch (IOException e) {
            result = StringUtils.stackToString(e);
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }

    public static String[] getAllFileName(String dir_path) {
        File dir = new File(dir_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!dir.isDirectory()) {
            return null;
        }
        return dir.list();
    }

    public static InputStream readFileAsStream(String file_path) {
        File file = new File(file_path);
        System.out.println(file.getAbsolutePath());
        // if (!file.exists() || !file.isFile()) {
        // return null;
        // }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        return inputStream;
    }

    public static InputStream readFileAsStream(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        return inputStream;
    }

    public static String writeFile(InputStream is, String path) throws FileNotFoundException {

        String result = "";
        String dir = StringUtils.getDir(path);
        makeInexistentDirs(dir);
        OutputStream output = new FileOutputStream(path);
        try {
            output.write(IOUtils.toByteArray(is));
        } catch (IOException e) {
            result = StringUtils.stackToString(e);
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static boolean deleteFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public static void makeInexistentDirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @Descrption 复制单个文件
     * @author rd_jianbin_lin
     * @Date Jul 12, 2017 2:21:11 PM
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            System.out.println("源文件：" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            System.out.println("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            } else {
                System.out.println("复制文件失败，目标文件：" + srcFileName + "已经存在，不进行覆盖！");
                return false;
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @Descrption 复制整个文件夹
     * @author rd_jianbin_lin
     * @Date Jul 12, 2017 2:21:11 PM
     * @param srcDirName
     *            待复制目录的目录名
     * @param destDirName
     *            目标目录名
     * @param overlay
     *            如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            System.out.println("复制目录失败：源目录" + srcDirName + "不存在！");
            return false;
        } else if (!srcDir.isDirectory()) {
            System.out.println("复制目录失败：" + srcDirName + "不是目录！");
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                new File(destDirName).delete();
            } else {
                System.out.println("复制目录失败：目的目录" + destDirName + "已存在！");
                return false;
            }
        } else {
            // 创建目的目录
            System.out.println("目的目录不存在，准备创建。。。");
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
                flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("复制目录" + srcDirName + "至" + destDirName + "失败！");
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
    }
}

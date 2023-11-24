package indi.sword.util.lombok;

import lombok.Cleanup;

import java.io.*;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:11 PM 04/06/2018
 * @MODIFIED BY
 */
// @Cleanup: 自动帮我们调用close()方法。
public class TestCleanUp {
    public static void main(String[] args) throws IOException {

    }

    public static void cleanUp() throws IOException {
        File file = new File("/Users/momo/Documents/temp/1.txt");
        File file2 = new File("/Users/momo/Documents/temp/2.txt");
        @Cleanup InputStream in = new FileInputStream(file);
        @Cleanup OutputStream out = new FileOutputStream(file2);
        byte[] b = new byte[1024];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }

    public static void normal() throws IOException {
        File file = new File("/Users/momo/Documents/temp/1.txt");
        File file2 = new File("/Users/momo/Documents/temp/2.txt");

        InputStream in = new FileInputStream(file);
        try {
            OutputStream out = new FileOutputStream(file2);
            try {
                byte[] b = new byte[1024];
                while (true) {
                    int r = in.read(b);
                    if (r == -1) break;
                    out.write(b, 0, r);
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


}

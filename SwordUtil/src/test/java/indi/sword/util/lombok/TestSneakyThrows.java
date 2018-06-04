package indi.sword.util.lombok;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 5:08 PM 04/06/2018
 * @MODIFIED BY
 */
public class TestSneakyThrows {
    public static void main(String[] args) {
        test();
    }

    @SneakyThrows private static void test() {
        File file = new File("/Users/momo/Documents/temp/1.txt");
        InputStream is = new FileInputStream(file);
    }
    // 相当于 下面

    private static void test2() throws IOException{
        File file = new File("/Users/momo/Documents/temp/1.txt");
        InputStream is = new FileInputStream(file);
    }
}

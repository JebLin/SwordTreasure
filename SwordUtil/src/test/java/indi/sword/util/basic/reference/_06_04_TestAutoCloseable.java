package indi.sword.util.basic.reference;


import lombok.Cleanup;

import java.io.*;

/**
 * @author jeb_lin
 * 4:58 PM 2019/9/27
 */
public class _06_04_TestAutoCloseable {
    public static void main(String[] args) throws Exception {
        @Cleanup BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File("testQueue.txt")));
        @Cleanup BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File("out.txt")));
        int b;
        while ((b = bin.read()) != -1){
            bout.write(b);
        }
    }
}
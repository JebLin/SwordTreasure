package indi.sword.util.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created by rd_jianbin_lin  on 2017/9/8.
 */
public class _05_TestPipe {

    @Test
    public void test() throws Exception{

        // 1、获取管道
        Pipe pipe = Pipe.open();

        // 2、获取缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 3、缓冲区数据写入管道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);


        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        System.out.println(new String(buf.array(),0,len));

        sourceChannel.close();
        sinkChannel.close();
        
    }


}

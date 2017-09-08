package indi.sword.util.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 
 * 一、使用NIO 完成网络通信的三个核心：
 * 
 * 1、通道（channel）：负责连接
 * 
 * 		java.nio.channels.Channel 接口
 * 			|--SelectableChannel 
 *		TCP:	|--SocketChannel				
 * 				|--ServerSocketChannel     
 * 		UDP:	|--DatagramChannel 
 * 
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 * 
 * 2、缓冲区（Buffer）：负责数据的存储
 * 
 * 3、选择器（Selector）：是SelectableChannel 的多路复用器。用于监控SelectableChannel的 IO状况
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 7, 2017 12:09:25 PM
 */
public class _03_TestBlockingNIO_NetworkIO {
		
	// 客户端
	@Test
	public void client() throws Exception{
		
		// 1、获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
		
		FileChannel inChannel = FileChannel.open(Paths.get("F:/1.jpg"),StandardOpenOption.READ);
		
		// 2、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		// 3、读取本地文件，并发送到服务端
		while(inChannel.read(buf) != -1){
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		
		sChannel.shutdownOutput(); //Shutdown the connection for writing without closing the channel.
		
		// 4、接收服务端的反馈
		buf.clear();
		int len = 0;
		while((len = sChannel.read(buf)) != -1){
			buf.flip();
			System.out.println(new String(buf.array(),0,len));
			buf.clear();
		}
		
		// 4、关闭通道
		inChannel.close();
		sChannel.close();
	}
	
	// 服务端
	@Test
	public void server() throws IOException{
		
		// 1、获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		FileChannel outChannel = FileChannel.open(Paths.get("F:/2.jpg"), StandardOpenOption.WRITE,
				StandardOpenOption.CREATE);

		// 2、绑定连接
		ssChannel.bind(new InetSocketAddress(9898)); // Creates a socket address from a hostname and a port number.
		
		//3、获取客户端连接的通道
		SocketChannel sChannel = ssChannel.accept();
		
		//4、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//5、接收客户端的数据，并保存到本地
		while(sChannel.read(buf) != -1){
			buf.flip(); //Flips this buffer.  The limit is set to the current position and then the position is set to zero.
			outChannel.write(buf);
			buf.clear();
		}
		
		// 6、接收完毕，发送反馈给客户端
		System.out.println("服务端反馈...");
		buf.put("服务端接收数据成功...".getBytes());
		buf.flip();
		sChannel.write(buf);
		
		//7、关闭通道
		outChannel.close();
		sChannel.close();
		ssChannel.close();
	}
}

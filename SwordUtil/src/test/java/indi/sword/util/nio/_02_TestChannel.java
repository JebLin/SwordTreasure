package indi.sword.util.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/*			
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 		
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 6, 2017 7:20:57 PM
 */
public class _02_TestChannel {

	
	/**
	 * 字符集：Charset
	 * 编码：字符串 -> 字节数组
	 * 解码：字节数组  -> 字符串
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 6, 2017 8:51:26 PM
	 */
	@Test
	public void test_Charset() throws Exception{
		
		Charset cs1 = Charset.forName("GBK");
		
		//获取编码器
		CharsetEncoder ce = cs1.newEncoder();
		
		//获取解码器
		CharsetDecoder cd = cs1.newDecoder();
		
		CharBuffer cBuf_En = CharBuffer.allocate(1024);
		cBuf_En.put("Sword52888国泰民安！*-");
		cBuf_En.flip();
		
		//编码
		ByteBuffer bBuf_En = ce.encode(cBuf_En);
//		System.out.println(bBuf_En.toString()); // java.nio.HeapByteBuffer[pos=0 lim=22 cap=34]
		
		for(int i = 0; i < bBuf_En.limit();i++){
			System.out.print(bBuf_En.get());
		}
		System.out.println();
		System.out.println("-----------------------------------------------");
		
		//解码
		bBuf_En.flip();
		CharBuffer cBuf_De = cd.decode(bBuf_En);
		System.out.println("cBuf_De.toString() -> " + cBuf_De.toString());
		
		bBuf_En.flip();
		Charset cs2 = Charset.forName("GBK");
		CharBuffer cBuf3 = cs2.decode(bBuf_En);
		System.out.println(cBuf3.toString());
		
		
		
		
	}
	
	/**
	 * 五、分散(Scatter)与聚集(Gather)
	 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
	 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 6, 2017 8:30:56 PM
	 */
//	@Test
	public void test_InDirect_Scatter_Gather() throws Exception{
		
		RandomAccessFile raf1 = new RandomAccessFile("F:/1.txt","r");
		
		// 1、获取通道
		FileChannel inChannel = raf1.getChannel();
		
		// 2、分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		// 3、分散读取
		ByteBuffer[] bufs = {buf1,buf2};
		inChannel.read(bufs);
		
		for(ByteBuffer buf: bufs){
			buf.flip();
		}
		
		System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
		System.out.println("--------------------------------------------");
		System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));
		
		
		// 4、聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("F:/2.txt","rw");
		FileChannel outChannel = raf2.getChannel();
		
		outChannel.write(bufs);
		
	}
	
	
	/**
	 * //通道之间的数据传输(直接缓冲区)
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 6, 2017 8:14:53 PM
	 */
//	@Test
	public void test_Direct_Transfer()	throws Exception{
		// 1、获取通道
		FileChannel inChannel = FileChannel.open(Paths.get("F:/1.mkv"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("F:/2.mkv"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
		//StandardOpenOption.CREATE: Create a new file if it does not exist.
		//CREATE_NEW ： Create a new file, failing if the file already exists. 加个NEW 就是我一定要新建，不然抛异常
		
//		inChannel.transferTo(0, inChannel.size(),outChannel);
		outChannel.transferFrom(outChannel, 0, inChannel.size());
		
//		inChannel.close();
		outChannel.close();
	}

	/**
	 * 完成文件的复制   （直接缓冲区）
	 * 
	 * 针对各个通道提供了静态方法 open()
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 6, 2017 8:07:38 PM
	 */
	// @Test
	public void test_Direct_MapByteBuffer() throws Exception {
		long start = System.currentTimeMillis();

		// 1、获取通道
		FileChannel inChannel = FileChannel.open(Paths.get("F:/1.mkv"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("F:/2.mkv"), StandardOpenOption.WRITE,
				StandardOpenOption.READ, StandardOpenOption.CREATE);
		// StandardOpenOption.CREATE: Create a new file if it does not exist.
		// CREATE_NEW ： Create a new file, failing if the file already exists.
		// 加个NEW 就是我一定要新建，不然抛异常

		// 2、内存映射文件
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

		// 3、直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);

		inChannel.close();
		outChannel.close();

		long end = System.currentTimeMillis();
		System.out.println("it cost : " + (end - start)); // 1734
	}

	/**
	 * 利用通道完成文件的复制（非直接缓冲区）
	 * 	本地 IO：
	 * 		FileInputStream/FileOutputStream
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 6, 2017 8:07:51 PM
	 */
	// @Test
	public void test_InDirect_FileXXXStream() throws Exception {

		long start = System.currentTimeMillis(); // 9803

		// 文件流
		FileInputStream fis = new FileInputStream("F:/1.mkv");
		/*
		 * If the file exists but is a directory rather than a regular file,
		 * does not exist but cannot be created, or cannot be opened for any
		 * other reason then a <code>FileNotFoundException</code> is thrown.
		 */
		FileOutputStream fos = new FileOutputStream("F:/2.mkv");

		// 1、获取通道
		FileChannel inChannel = fis.getChannel();
		FileChannel outChannel = fos.getChannel();

		// 2、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);

		while (inChannel.read(buf) != -1) {
			buf.flip();
			outChannel.write(buf);
			buf.clear(); // Invoke this method before using a sequence of
							// channel-read or put operations to fill this
							// buffer
		}

		long end = System.currentTimeMillis();
		System.out.println("it cost : " + (end - start)); //
	}

}

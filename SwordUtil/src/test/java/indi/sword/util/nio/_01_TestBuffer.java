package indi.sword.util.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 
 * 　如果将同步I/O方式下的数据传输比做数据传输的零星方式(这里的零星是指在数据传输的过程中是以零星的字节方式进行的)，
 * 那么就可以将非阻塞I/O方式下的数据传输比做数据传输的集装箱方式(在字节和低层数据传输之间，多了一层缓冲区，因此，可以将缓冲区看做是装载字节的集装箱)。
 * 大家可以想象，如果我们要运送比较少的货物，用集装箱好象有点不太合算，而如果要运送上百吨的货物，用集装箱来运送的成本会更低。
 * 在数据传输过程中也是一样，如果数据量很小时，使用同步I/O方式会更适合，如果数据量很大时(一般以G为单位)，使用非阻塞I/O方式的效率会更高。
 * 因此，从理论上说，数据量越大，使用非阻塞I/O方式的单位成本就会越低。产生这种结果的原因和缓冲区的一些特性有着直接的关系。
 * 
 * java NIO ： java non-blocking IO 
 * 
 * 以前的JAVA IO 流 ：可以比作是 管道运输，一条来一条走
 * 现在的NIO ：
 * channel 通道   ，可以比作是 铁路， 所以一条铁路就行了，管道需要两条
 * 缓冲区   可以比作是上面的 火车车厢 ， 火车去，火车回来，装东西 卸载东西 一辆火车
 * 
 * 
 * 一、缓冲区（Buffer）：在Java NIO中负责数据的存储。缓冲区就是数组。用于存储不同数据类型的数据
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 * 
 * 二、缓冲区存取数据的两个核心方法：
 * put():存入数据到缓冲区
 * get():获取缓冲区中的数据
 * 
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量。表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界面。表示缓冲区中可以操作数据的大小。（limit后数据不能读写）
 * position ： 位置。表示缓冲区中正在操作数据的位置。可以理解为指针或者下标
 * 
 * mark ： 标记，表示记录当前position的位置。可以通过reset() 恢复到mark的位置
 * 
 * 0 <= mark <= position <= limit <= capacity
 * 
 * 四、 字节缓冲区要么是直接的，要么是非直接的。
 * 直接缓冲区与非直接缓冲区：
 * 非直接缓冲区： 通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM的内存中。
 * 直接缓冲区 ： 通过allocateDirect() 方法分配直接缓冲区，将缓冲区简历在物理内存中，可以提高效率
 * 
 * 非直接缓冲区，也就是正常情况下，数据的流动是：
 *       OS            |         JVM
 * 物理磁盘  --> 内核地址空间   --> 用户地址空间  --> 应用程序
 * 物理磁盘  <-- 内核地址空间   <-- 用户地址空间  <-- 应用程序
 * 
 * 直接缓冲区：
 * 	 OS            |          JVM
 *  物理磁盘  --> 物理磁盘映射文件  --> 应用程序
 * 
 * 非直接缓冲区 
 * 优点：
 * 1）在一定程度上分离了内核空间和用户空间，保护系统本身的运行安全；2）可以减少读盘的次数，从而提高性能。
 * 2）可以减少读盘的次数，从而提高性能。
 * 缺点：
 * 在缓存 I/O 机制中，DMA 方式可以将数据直接从磁盘读到页缓存中，或者将数据从页缓存直接写回到磁盘上， * 而不能直接在应用程序地址空间和磁盘之间进行数据传输，
 * 这样，数据在传输过程中需要在应用程序地址空间（用户空间）和缓存（内核空间）之间进行多次数据拷贝操作，这些数据拷贝操作所带来的CPU以及内存开销是非常大的。
 * 
 * 直接缓冲区：
 * 优点：
 * 直接IO就是应用程序直接访问磁盘数据，而不经过内核缓冲区，这样做的目的是减少一次从内核缓冲区到用户程序缓存的数据复制。
 * 比如说数据库管理系统这类应用，它们更倾向于选择它们自己的缓存机制，因为数据库管理系统往往比操作系统更了解数据库中存放的数据，数据库管理系统可以提供一种更加有效的缓存机制来提高数据库中数据的存取性能。
 * 
 * 缺点：
 * 如果访问的数据不在应用程序缓存中，那么每次数据都会直接从磁盘加载，这种直接加载会非常缓存。
 * 通常直接IO与异步IO结合使用，会得到比较好的性能。（异步IO：当访问数据的线程发出请求之后，线程会接着去处理其他事，而不是阻塞等待）
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 5, 2017 7:18:52 PM
 */
public class _01_TestBuffer {
	
	@Test
	public void test_base(){
		
		String str = "abcde";
		
		// 1、分配一个指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		System.out.println("-------------- allocate ---------------");
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		// 2、 利用 put() 存入数据到缓冲区
		buf.put(str.getBytes());
		
		System.out.println("-------------- put ---------------");
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		// 3、 切换读取数据模式
		buf.flip();
		
		System.out.println("-------------- flip ---------------");
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		// 4、 利用 get() 读取缓冲区里面的数据
		byte[] dst = new byte[buf.limit()];
		buf.get(dst); // 把缓冲区的数据 读到dst中去
		
		System.out.println("-------------- get ---------------");
		System.out.println(new String(dst,0,buf.limit()));
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		// 5、rewind() ：可以重复读，也就是重新读的意思。
		buf.rewind(); //The position is set to zero and the mark is discarded.
	     
		System.out.println("-------------- rewind ---------------");
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		// 5、clear() : 
		buf.clear();  //Clears this buffer.  The position is set to zero, the limit is set to the capacity, 
					  // and the mark is discarded.This method does not actually erase the data in the buffer.
		System.out.println("-------------- clear ---------------");
		System.out.println(buf.position()); // 下标
		System.out.println(buf.limit()); // 读写限制
		System.out.println(buf.capacity()); //容量
		
		System.out.println((char)buf.get(1)); // 并不是真的抹除数据，而是移个下标而已
	}
	
	
	@Test
	public void test_mark_reset(){
		String str = "abcde";
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		buf.put(str.getBytes());
		
		buf.flip();
		
		byte[] dst = new byte[buf.limit()];
		buf.get(dst,0,3);
		System.out.println(new String(dst,0,3));
		System.out.println(buf.position());
		
		// mark（） ： Sets this buffer's mark at its position.
		buf.mark(); 
		buf.get(dst, 3, 2);
		System.out.println(new String(dst,3,2));

		// reset() ：Resets this buffer's position to the previously-marked position.
		buf.reset();
		System.out.println(buf.position()); 
		
		//  Tells whether there are any elements between the current position and the limit.
		if(buf.hasRemaining()){
			System.out.println(buf.remaining()); // Returns the number of elements between the current position and the limit.
		}
		
	}
	
	@Test
	public void test_get(){
		String str = "abcde";
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(str.getBytes());
		buf.flip();
		System.out.println(buf.position());
		System.out.println((char)buf.get()); // Reads the byte at this buffer's current position, and then increments the position.
		System.out.println((char)buf.get());
		System.out.println((char)buf.get());
		System.out.println((char)buf.get());
	}
	
	@Test
	public void test_allocateDirect(){
		
		// 分配直接缓冲区
		ByteBuffer buf = ByteBuffer.allocateDirect(1024); // Allocates a new direct byte buffer.
		System.out.println(buf.isDirect());
		System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
		
	}
	
	@Test
	public void test(){
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put("gg".getBytes());
		System.out.println(buf.position());
		System.out.println(buf.limit());
		
		// flip 完只是说 最好用来读，不是说不能用来put ,put的话 会覆盖的
		buf.flip();
		buf.put("cc".getBytes());
		System.out.println(buf.position());
		System.out.println(buf.limit());
		buf.flip();
		System.out.println((char)buf.get());
	}
}

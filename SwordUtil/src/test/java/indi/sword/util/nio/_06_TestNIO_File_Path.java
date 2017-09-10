package indi.sword.util.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;

/**
 * Created by rd_jianbin_lin on 2017/9/10.
 */
public class _06_TestNIO_File_Path {


    //自动资源管理：自动关闭实现 AutoCloseable 接口的资源
    @Test
    public void test_autoClose_Channel(){
        try(FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)){

            ByteBuffer buf = ByteBuffer.allocate(1024);
            int len = 0;
            while( (len = inChannel.read(buf)) != -1){
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
        }catch(IOException e){

        }
    }


    /*
    Files常用方法：用于操作内容
        SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
        DirectoryStream newDirectoryStream(Path path) : 打开 path 指定的目录
        InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
        OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
     */
    public void test_Files_content()  throws Exception{
        SeekableByteChannel newByteChannel = Files.newByteChannel(Paths.get("1.jpg"), StandardOpenOption.READ);

        DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get("e:/"));

        for (Path path : newDirectoryStream) {
            System.out.println(path);
        }
    }

    /*
    Files常用方法：
        Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
        Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
        Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
        void delete(Path path) : 删除一个文件
        Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
        long size(Path path) : 返回 path 指定文件的大小


        boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
        boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
        boolean isExecutable(Path path) : 判断是否是可执行文件
        boolean isHidden(Path path) : 判断是否是隐藏文件
        boolean isReadable(Path path) : 判断文件是否可读
        boolean isWritable(Path path) : 判断文件是否可写
        boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
        public static <A extends BasicFileAttributes> A readAttributes(Path path,Class<A> type,LinkOption... options) : 获取与 path 指定的文件相关联的属性。
     */
    @Test
    public void test_File() throws Exception{
        Path path1 = Paths.get("e:/nio/1.txt");
        System.out.println(Files.size(path1));

        Path path2 = Paths.get("e:/nio/2.txt");
        Path path3 = Paths.get("e:/nio/3.txt");
        Files.copy(path1,path3, StandardCopyOption.REPLACE_EXISTING);

        Path path4 = Paths.get("e:/nio/dir/");
//        Files.createDirectory(path4);

//        Files.delete(path2);
        Files.deleteIfExists(path2);
        Files.move(path1,path4);

    }


    /*
    Paths 提供的 get() 方法用来获取 Path 对象：
        Path get(String first, String … more) : 用于将多个字符串串连成路径。
    Path 常用方法：
        boolean endsWith(String path) : 判断是否以 path 路径结束
        boolean startsWith(String path) : 判断是否以 path 路径开始
        boolean isAbsolute() : 判断是否是绝对路径
        Path getFileName() : 返回与调用 Path 对象关联的文件名
        Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
        int getNameCount() : 返回Path 根目录后面元素的数量
        Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
        Path getRoot() ：返回调用 Path 对象的根路径
        Path resolve(Path p) :将相对路径解析为绝对路径
        Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
        String toString() ： 返回调用 Path 对象的字符串表示形式
     */
    @Test
    public void test_Path() throws Exception{
        Path path = Paths.get("e:/","nio/1.txt");
        System.out.println("endWith : " + path.endsWith("1.txt"));
        System.out.println("startWith : " + path.startsWith("e:"));

        System.out.println("isAbsolute : " + path.isAbsolute()); // Tells whether or not this path is absolute.
        System.out.println("fileName" + path.getFileName());

        System.out.println("nameCount :" + path.getNameCount()); //Returns the number of name elements in the path.
        for (int i = 0;i < path.getNameCount();i++){
            System.out.println("name : " + path.getName(i));
        }


        System.out.println("root:" + path.getRoot());
        System.out.println("parent:" + path.getParent());
        System.out.println(path.toFile());

        Path newPath = path.toAbsolutePath();
        System.out.println("newPath : " + newPath.toString());
        System.out.println("path :" + path.toString());

    }
}

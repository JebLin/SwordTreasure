package indi.sword.util.base.network;

import java.net.URL ;
import java.io.InputStream ;
import java.util.Scanner ;
public class URLDemo{
	public static void main(String args[]) throws Exception {	// 所有异常抛出

		URL url = new URL("http","www.mldnjava.cn",80,"/curriculum.htm") ;
		InputStream input = url.openStream() ;	// 打开输入流
		Scanner scan = new Scanner(input) ;		// 实例化Scanner类
		scan.useDelimiter("\n") ;	// 设置读取分隔符
		while(scan.hasNext()){
			System.out.println(scan.next()) ;
		}
	}
};
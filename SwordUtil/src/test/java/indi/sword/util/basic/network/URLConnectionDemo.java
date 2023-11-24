package indi.sword.util.basic.network;

import java.net.URL ;
import java.net.URLConnection ;

public class URLConnectionDemo{
	public static void main(String args[]) throws Exception {	// 所有异常抛出
		URL url = new URL("http://www.mldnjava.cn") ;
		URLConnection urlCon = url.openConnection() ;	// 建立连接
		System.out.println("内容大小：" + urlCon.getContentLength()) ;
		System.out.println("内容类型：" + urlCon.getContentType()) ;
	}
};
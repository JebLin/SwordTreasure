package indi.sword.util.http;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Description htmlUnit小例子
 * @Author:rd_jianbin_lin
 * @Date: 19:58 2017/9/15
 */
public class Test_HtmlUnit {


    @Test
    public void test_simple() throws IOException {
        String str;
        //创建一个webclient
        WebClient webClient = new WebClient();
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("http://www.baidu.com/");
        //获取页面的TITLE
        str = page.getTitleText();
        System.out.println(str);
        //获取页面的XML代码
        str = page.asXml();
        System.out.println(str);
        //获取页面的文本
        str = page.asText();
        System.out.println(str);
        //关闭webclient
    }

    @Test
    public void test_multibrowser() throws IOException {
        String str;
        //使用FireFox读取网页
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage("http://www.baidu.com/");
        str = page.getTitleText();
        System.out.println(str);
        //关闭webclient
    }
    @Test
    public void test_03() throws IOException {
        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");
        //通过id获得"百度一下"按钮
        HtmlInput btn = (HtmlInput)page.getHtmlElementById("su");
        System.out.println(btn.getDefaultValue());
        //关闭webclient
    }
    @Test
    public void test_04() throws IOException {
        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");
        //查找所有div
        List<?> hbList = page.getByXPath("//div");
        HtmlDivision hb = (HtmlDivision)hbList.get(0);
        System.out.println(hb.toString());
        //查找并获取特定input
        List<?> inputList = page.getByXPath("//input[@id='su']");
        HtmlInput input = (HtmlInput)inputList.get(0);
        System.out.println(input.toString());
        //关闭webclient
    }
    @Test
    public void test_05() throws IOException {
        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");
        //获取搜索输入框并提交搜索内容
        HtmlInput input = (HtmlInput)page.getHtmlElementById("kw");
        System.out.println(input.toString());
        input.setValueAttribute("雅蠛蝶");
        System.out.println(input.toString());
        //获取搜索按钮并点击
        HtmlInput btn = (HtmlInput)page.getHtmlElementById("su");
        HtmlPage page2 = btn.click();
        //输出新页面的文本
        System.out.println(page2.asText());
    }
}

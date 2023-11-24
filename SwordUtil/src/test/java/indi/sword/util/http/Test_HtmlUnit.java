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
        System.out.println("-------------page.getTitleText() Begin ------------------------");
        str = page.getTitleText();
        System.out.println(str);
        System.out.println("-------------page.getTitleText() End ------------------------");
        //获取页面的XML代码
        System.out.println("--------------page.asXml() Begin -----------------------");
        str = page.asXml();
        System.out.println(str);
        System.out.println("--------------page.asXml() End -----------------------");
        System.out.println("--------------page.asText() End ---------------------");
        //获取页面的文本
        str = page.asText();
        System.out.println(str);
        System.out.println("------------------------------------------");
        //关闭webclient
    }

    @Test
    public void test_appointed_browser() throws IOException {
        String str;
        //使用FireFox读取网页
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage("http://www.baidu.com/");
        str = page.getTitleText();
        System.out.println(str);
        //关闭webclient
    }
    @Test
    public void test_getElement() throws IOException {

        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");

        //通过id获得"百度一下"按钮 --搜索按钮
        HtmlInput btn = (HtmlInput)page.getHtmlElementById("su");
        System.out.println("btn.getDefaultValue -> " + btn.getDefaultValue());

        //查找所有div
        List<?> hbList = page.getByXPath("//div");
        HtmlDivision hb = (HtmlDivision)hbList.get(0);
        System.out.println("hb.toString() ->" +hb.toString());

        //查找并获取特定input
        List<?> inputList = page.getByXPath("//input[@id='su']");
        HtmlInput input = (HtmlInput)inputList.get(0);
        System.out.println("input.toString() ->" + input.toString());

    }
    @Test
    public void test_operateElement() throws IOException {
        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");
        HtmlInput input_kw = (HtmlInput)page.getHtmlElementById("kw");

        System.out.println(input_kw.toString());
        input_kw.setValueAttribute("雅蠛蝶");
        System.out.println(input_kw.toString());
        //获取搜索按钮并点击
        HtmlInput btn = (HtmlInput)page.getHtmlElementById("su");
        HtmlPage page2 = btn.click();
        //输出新页面的文本
        System.out.println(page2.asText());
    }
}

package indi.sword.util.dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;

/**
 * @Description  测试JSoup
 * @Author:rd_jianbin_lin
 * @Date: 11:52 2017/9/18
 */
public class TestJsoup {

    @Test
    public void test_Simple() throws Exception{
        String html = "<html><head><title>代码王国</title></head>"
                +"<body><p>这里是jsoup 项目</p></body></html>";
        Document doc = Jsoup.parse(html);
        Element body = doc.body(); // doc.getElementsByTag("body")
        System.out.println(doc.text());
        System.out.println(body.text());
        System.out.println(doc.html());
        System.out.println(body.html());


        File input = new File("/tmp/input.html");
        Document doc_File = Jsoup.parse(input, "UTF-8", "http://example.com/"); //baseUri参数用于解决文件中URLs是相对路径的问题。如果不需要可以传入一个空的字符串。

    }
    @Test
    public void test_Connect() throws Exception{

        Document doc = Jsoup.connect("http://sou.zhaopin.com/").get(); // 智联招聘 职位搜索页面
        String title = doc.title();
//        System.out.println(doc.html());


        Elements required_input_fields = doc.select("input");
        //迭代搜索
        for (Element required_input_field :
                required_input_fields) {
            String value = required_input_field.attr("value");
            String filed_name = required_input_field.attr("name");
//            System.out.println(filed_name + " -> " + value);

            String checked = required_input_field.attr("checked");
            if (required_input_field.attr("type").equals("radio")) {
                System.out.println(required_input_field.text());
                continue;
            }
            if (required_input_field.attr("type").equals("checkbox")) {
                if (checked.equals("checked")) {
                    System.out.println(required_input_field.text());
                }
                continue;
            }
        }


        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println(link.html());
        }


    }

    @Test
    public void test1(){
        //暂时用字符串代替从页面取来的dom结构
        String html= "<html><head><title>First parse</title></head><body><p id='hehe'>Parsed HTML into a doc.</p></body></html>";
        //解析字符串获得document对象
        Document doc=Jsoup.parse(html);
        //从doc对象中取得id为hehe的元素然后获取其中的文字值
        System.out.println(doc.getElementById("hehe").text());
        //从doc对象中取得id为hehe的元素然后获取其中的html对象
        System.out.println(doc.getElementById("hehe").html());
        //从doc对象中取得id为hehe的元素然后回溯出整体
        System.out.println(doc.getElementById("hehe"));
        //你可以把document对象看做后台版的js,通过class找，name找甚至利用jQuery都是支持的
    }
}

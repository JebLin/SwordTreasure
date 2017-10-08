package indi.sword.util.base;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @Description  XML工具类
 * @Author:rd_jianbin_lin
 * @Date: 17:30 2017/9/21
 */
public class XmlUtils {


    public static void parserXml_dom4j(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputXml);
            Element rootElement = document.getRootElement();
            System.out.println(rootElement.getText());
            for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();
                for (Iterator j = user.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
                System.out.println();
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 其中,reader的read方法是重载的,可以从InputStream,File,Url等多种不同的源来读取.
     * 得到的Document对象就代表了整个XML. 读取的字符编码是按照XML文件头定义的编码来转换的.
     * 如果遇到乱码问题,注意要把各处的编码名称保持一致即可.
     *
     * @param fileName
     * @return
     * @throws DocumentException
     */
    public Document read(String fileName) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(fileName));
        return document;
    }

    /**
     * 读取后的第二步就是得到Root节点, 熟悉XML的人都知道,一切XML分析都是从Root元素开始的.
     *
     * @param doc
     * @return
     */
    public Element getElement(Document doc) {
        return doc.getRootElement();
    }

    /**
     * 1) 枚举(Iterator) 获取所有子节点的迭代
     *
     * @param doc
     */
    public void getAllIterator(Document doc) {
        Element root = this.getElement(doc);
        // 枚举所有子节点
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String text = element.getText();
            System.out.println(text);
            // do something
        }
    }

    /**
     * 1) 枚举(Iterator) 获取制定节点的迭代
     *
     * @param doc
     * @param fooName
     */
    public void getFooIterator(Document doc, String fooName) {
        Element root = this.getElement(doc);
        // 枚举名称为foo的节点
        for (Iterator i = root.elementIterator(fooName); i.hasNext();) {
            Element foo = (Element) i.next();
            String text = foo.getText();
            System.out.println(text);
            // do something
        }
    }

    /**
     * 1) 枚举(Iterator) 获取某节点的所有属性的迭代
     *
     * @param doc
     */
    public void getAttributeIterator(Document doc) {
        Element root = this.getElement(doc);
        // 枚举属性
        for (Iterator i = root.attributeIterator(); i.hasNext();) {
            Attribute attribute = (Attribute) i.next();
            String text = attribute.getText();
            System.out.println(text);
            // do something
        }
    }

    /**
     * 2)递归 递归也可以采用Iterator作为枚举手段，但文档中提供了另外的做法
     *
     * @param doc
     */
    public void treeWalk(Document doc) {
        Element root = this.getElement(doc);
        treeWalk(root);
    }

    private void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                treeWalk((Element) node);
            } else {
                String text = node.getText();
                System.out.println(text);
                // do something
            }
        }
    }

    /**
     * 3) Visitor模式
     *
     * @param doc
     */
    public void getVisit(Document doc) {
        Element root = this.getElement(doc);
        root.accept(new Dom4jVisitorUtil());
    }

    /**
     * DOM4J对XPath有良好的支持，如访问一个节点，可直接用XPath选择。 XPath语法:
     * http://www.w3school.com.cn/xpath/xpath_syntax.asp
     * nodename 选取此节点的所有子节点。
     *  / 从根节点选取。
     *  // 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置。
     *   . 选取当前节点。
     *   .. 选取当前节点的父节点。
     *   @ 选取属性。
     */
    public void bar(Document doc) {
        /***********************************************************************
         <?xml version="1.0" encoding="ISO-8859-1"?>
         <bookstore>
         <book>
         <title lang="eng">Harry Potter</title>
         <price>29.99</price>
         </book>
         <book>
         <title lang="eng">Learning XML</title>
         <price>39.95</price>
         </book>
         </bookstore>

         **********************************************************************/
        // 选取 bookstore 元素的所有子节点。
        List list1 = doc.selectNodes("bookstore");
        /*选取根元素 bookstore。
         * 注释：假如路径起始于正斜杠( / )，则此路径始终代表到某元素的绝对路径！
         */
        List list2 = doc.selectNodes("/bookstore");
        //选取属于 bookstore 的子元素的所有 book 元素。
        List list3 = doc.selectNodes("bookstore/book");
        //选取所有 book 子元素，而不管它们在文档中的位置。
        List list4 = doc.selectNodes("//book");
        /*选择属于 bookstore 元素的后代的所有 book 元素，
         *     而不管它们位于 bookstore 之下的什么位置。
        */
        List list5 = doc.selectNodes("bookstore//book");
        /**
         * 选取名为 lang 的所有属性。
         */
        List list6 = doc.selectNodes("//@lang");
    }

    /**
     * 查找XHTML文档中所有的超链接
     * @param
     * @throws DocumentException
     */
    public void findLinks(Document doc) throws DocumentException {
        List list = doc.selectNodes("//a/@href");
        for(Iterator i = list.iterator();i.hasNext();){
            Attribute attribute = (Attribute) i.next();
            String url = attribute.getValue();
        }
    }
    /**
     *
     * 类名称: Dom4jUtil.java
     * @Title: getStringXml
     * @Description:XML转字符串
     * @param doc
     * @return String
     * @author NoureWang
     * @date Oct 13, 2011
     */
    public String getStringXml(Document doc){
        return doc.asXML();
    }
    /**
     * 字符串转XML
     * @param xml
     * @return
     * @throws DocumentException
     */
    public Document getDocumentXml(String xml) throws DocumentException{
        return DocumentHelper.parseText(xml);
    }
    /**
     * 一个简单的输出方法是将一个Document或任何的Node通过write方法输出
     * @param doc
     * @param xml
     * @throws IOException
     */
    public void simpleWrite(Document doc,String xml) throws IOException{
        FileWriter out = new FileWriter(xml);
        doc.write(out);
    }
    /**
     * 如果你想改变输出的格式，比如美化输出或缩减格式，可以用XMLWriter类
     * @param
     * @throws IOException
     */
    public void formatWrite(Document doc, String xml) throws IOException {
        // 指定文件
        XMLWriter writer = new XMLWriter(new FileWriter(xml));

        writer.write(doc);
        writer.close();
        // 美化格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write(doc);
        // 缩减格式
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter(System.out, format);
        writer.write(doc);
    }
    /**
     * 建立一个XML文档,文档名由输入属性决定
     *
     * @param filename
     *            需建立的文件名
     * @return 返回操作结果, 0表失败, 1表成功
     */
    public int createXMLFile(String filename) {
        /** 返回操作结果, 0表失败, 1表成功 */
        int returnValue = 0;
        /** 建立document对象 */
        Document document = DocumentHelper.createDocument();
        /** 建立XML文档的根books */
        Element booksElement = document.addElement("books");
        /** 加入一行注释 */
        booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");
        /** 加入第一个book节点 */
        Element bookElement = booksElement.addElement("book");
        /** 加入show属性内容 */
        bookElement.addAttribute("show", "yes");
        /** 加入title节点 */
        Element titleElement = bookElement.addElement("title");
        /** 为title设置内容 */
        titleElement.setText("Dom4j Tutorials");
        /** 类似的完成后两个book */
        bookElement = booksElement.addElement("book");
        bookElement.addAttribute("show", "yes");
        titleElement = bookElement.addElement("title");
        titleElement.setText("Lucene Studing");
        bookElement = booksElement.addElement("book");
        bookElement.addAttribute("show", "no");
        titleElement = bookElement.addElement("title");
        titleElement.setText("Lucene in Action");
        /** 加入owner节点 */
        Element ownerElement = booksElement.addElement("owner");
        ownerElement.setText("O'Reilly");
        try {
            /** 将document中的内容写入文件中 */
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)));
            writer.write(document);
            writer.close();
            /** 执行成功,需返回1 */
            returnValue = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }

    public static void main(String[] args) {
        String fileName = "D:\\test.xml";
        XmlUtils xmlUtils = new XmlUtils();

    }


}
class Dom4jVisitorUtil extends VisitorSupport {
    public void visit(Element element){
        System.out.println(element.getName());
    }

    public void visit(Attribute attr){
        System.out.println(attr.getName());
    }
}
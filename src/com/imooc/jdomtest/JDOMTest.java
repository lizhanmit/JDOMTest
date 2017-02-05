package com.imooc.jdomtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.imooc.entity.Book;

public class JDOMTest {
	// 用来保存遍历出的book对象
	private static ArrayList<Book> booksList = new ArrayList<Book>();
	
	/**
	 * 解析xml
	 */
	private void parseXML() {
		// 进行对book.xml文件的JDOM解析
		// 准备工作
		// 1.创建一个SAXBuilder的对象
		SAXBuilder saxBuilder = new SAXBuilder();
		
		InputStream in;
		try {
			// 2.创建一个输入流，将xml文件夹加载到输入流中
			in = new FileInputStream("src/res/books.xml");
			// 解决乱码问题
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			// 3.通过SAXBuilder的build方法，将输入流加载到saxBuilder中
			Document document = saxBuilder.build(isr);
			// 4.通过document对象获取xml文件的根节点 
			Element rootElement = document.getRootElement();
			// 5.获取根节点下的子节点的List集合
			List<Element> bookList = rootElement.getChildren();
			// 继续进行解析
			for (Element book : bookList) {
				// 创建一个book对象用于存储book的信息
				Book bookEntity = new Book();
				
				System.out.println("=====开始解析第" + (bookList.indexOf(book) + 1) + "本书=====");
				// 解析book的属性集合
				List<Attribute> attrList = book.getAttributes();
//				// 知道节点下属性的名称时，获取节点值
//				book.getAttributeValue("id");
				// 遍历attrList（针对不知道book节点下属性的名称和数量）
				for (Attribute attr : attrList) {
					// 获取属性名
					String attrName = attr.getName();
					// 获取属性值
					String attrValue = attr.getValue(); // 此处的getValue()与DOM和SAX方式中的不同，这个不包括空格，所以不需要对空格进行处理
					System.out.println("属性名：" + attrName + "---属性值：" + attrValue); 
				
					// 判断并存储book的id信息
					if (attrName.equals("id")) {
						bookEntity.setId(attrValue);
					}
				}
				// 对book节点的子节点的名和值得遍历
				List<Element> bookChildren = book.getChildren();
				for (Element bookChild : bookChildren) {
					System.out.println("节点名：" + bookChild.getName() + "---节点值：" + bookChild.getValue()); 
				
					// 判断并存储book的其他信息
					if (bookChild.getName().equals("name")) {
						bookEntity.setName(bookChild.getValue());
					}
					else if (bookChild.getName().equals("author")) {
						bookEntity.setAuthor(bookChild.getValue());
					}
					else if (bookChild.getName().equals("year")) {
						bookEntity.setYear(bookChild.getValue());
					}
					else if (bookChild.getName().equals("price")) {
						bookEntity.setPrice(bookChild.getValue());
					}
					else if (bookChild.getName().equals("language")) {
						bookEntity.setLanguage(bookChild.getValue());
					}
					
				} 
				
				System.out.println("=====结束解析第" + (bookList.indexOf(book) + 1) + "本书=====");
				
				// 将book对象存入booksList中
				booksList.add(bookEntity);
				bookEntity = null;
				
				System.out.println("Total number of books: " + booksList.size());
				// 测试book对象已经存入booksList中，此处仅用于简单测试，正确情况应该写getBooksList()方法然后for each 循环出各个attributes
				System.out.println(booksList.get(0).getId());
				System.out.println(booksList.get(0).getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成xml
	 */
	private void createXML() {
		// 1.生成一个根节点rss
		Element rss = new Element("rss");
		// 2.为根节点添加属性
		rss.setAttribute("version", "2.0");
		// 3.生成一个document对象
		Document document = new Document(rss);
		// 4.生成子节点
		Element channel = new Element("channel");
		// 5.将子节点添加到根节点中
		rss.addContent(channel);
		// 6.生成二级子节点
		Element title = new Element("title");
		// 7.设置二级子节点的内容
		title.setText("国内最新新闻");
		// 8.将二级子节点添加到子节点中
		channel.addContent(title);
		// 9.设置生成xml的格式
		Format format = Format.getCompactFormat();
		format.setIndent("");
		format.setEncoding("GBK");
		
		// 10.创建XMLOutputter对象
		XMLOutputter outputter = new XMLOutputter(format);
		try {
			// 11.利用outputter将document对象转换成xml文档
			outputter.output(document, new FileOutputStream(new File("rssnews.xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new JDOMTest().createXML();
	}

}

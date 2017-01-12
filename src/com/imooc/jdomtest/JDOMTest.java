package com.imooc.jdomtest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import com.imooc.entity.Book;

public class JDOMTest {
	// ���������������book����
	private static ArrayList<Book> booksList = new ArrayList<Book>();
	
	public static void main(String[] args) {
		// ���ж�book.xml�ļ���JDOM����
		// ׼������
		// 1.����һ��SAXBuilder�Ķ���
		SAXBuilder saxBuilder = new SAXBuilder();
		
		InputStream in;
		try {
			// 2.����һ������������xml�ļ��м��ص���������
			in = new FileInputStream("src/res/books.xml");
			// �����������
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			// 3.ͨ��SAXBuilder��build�����������������ص�saxBuilder��
			Document document = saxBuilder.build(isr);
			// 4.ͨ��document�����ȡxml�ļ��ĸ��ڵ� 
			Element rootElement = document.getRootElement();
			// 5.��ȡ���ڵ��µ��ӽڵ��List����
			List<Element> bookList = rootElement.getChildren();
			// �������н���
			for (Element book : bookList) {
				// ����һ��book�������ڴ洢book����Ϣ
				Book bookEntity = new Book();
				
				System.out.println("=====��ʼ������" + (bookList.indexOf(book) + 1) + "����=====");
				// ����book�����Լ���
				List<Attribute> attrList = book.getAttributes();
//				// ֪���ڵ������Ե�����ʱ����ȡ�ڵ�ֵ
//				book.getAttributeValue("id");
				// ����attrList����Բ�֪��book�ڵ������Ե����ƺ�������
				for (Attribute attr : attrList) {
					// ��ȡ������
					String attrName = attr.getName();
					// ��ȡ����ֵ
					String attrValue = attr.getValue(); // �˴���getValue()��DOM��SAX��ʽ�еĲ�ͬ������������ո����Բ���Ҫ�Կո���д���
					System.out.println("��������" + attrName + "---����ֵ��" + attrValue); 
				
					// �жϲ��洢book��id��Ϣ
					if (attrName.equals("id")) {
						bookEntity.setId(attrValue);
					}
				}
				// ��book�ڵ���ӽڵ������ֵ�ñ���
				List<Element> bookChildren = book.getChildren();
				for (Element bookChild : bookChildren) {
					System.out.println("�ڵ�����" + bookChild.getName() + "---�ڵ�ֵ��" + bookChild.getValue()); 
				
					// �жϲ��洢book��������Ϣ
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
				
				System.out.println("=====����������" + (bookList.indexOf(book) + 1) + "����=====");
				
				// ��book�������booksList��
				booksList.add(bookEntity);
				bookEntity = null;
				
				System.out.println("Total number of books: " + booksList.size());
				// ����book�����Ѿ�����booksList�У��˴������ڼ򵥲��ԣ���ȷ���Ӧ��дgetBooksList()����Ȼ��for each ѭ��������attributes
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

}

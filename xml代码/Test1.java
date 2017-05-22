package com.whty.cms.web.netdisk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.Digester;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Test1 extends DefaultHandler{
	private List<ResourceXml> list = null;
	private ResourceXml resource = null;
	private String preTag = null;
	
	public static void main(String args[]){
		File file = new File("E://221.xml");
		saxXml(file);
	}
	/**
	 * dom解析
	 * @param file
	 * @return
	 */
	public static List<ResourceXml> domXml(File file){
		List<ResourceXml> domList = new ArrayList<ResourceXml>();
		try {
			InputStream inputStream = new FileInputStream(file);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			Document document = builder.parse(inputStream); 
			Element element = document.getDocumentElement();
			NodeList nodeList = element.getElementsByTagName("resource");//加了<resources></resources>
			for(int i=0; i < nodeList.getLength(); i++){
				Element tagElement = (Element)nodeList.item(i);
				ResourceXml xml = new ResourceXml();
				NodeList childNodeList = tagElement.getChildNodes();
				for(int j = 0; j < childNodeList.getLength(); j++){
					if("title".equals(childNodeList.item(j).getNodeName())){
						xml.setTitle(childNodeList.item(j).getFirstChild().getNodeValue());
					}
					if("resTypeIdList".equals(childNodeList.item(j).getNodeName())){
						Element valeElement = (Element)childNodeList.item(j);
						NodeList valueNodeList = valeElement.getChildNodes();
						for(int k = 0; k < valueNodeList.getLength(); k++){
							if("value".equals(valueNodeList.item(k).getNodeName())){
								xml.addResTypeId(valueNodeList.item(k).getFirstChild().getNodeValue());
							}
						}
					}
				}
				domList.add(xml);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return domList;
	}
	
	/**
	 * sax解析
	 * @param file
	 * @return
	 */
	public static List<ResourceXml> saxXml(File file){
		Test1 text = new Test1();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance(); 
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, text);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return text.getResourceXml();
	}
	
	public List<ResourceXml> getResourceXml(){
		return list;
	}
	
	@Override
	public void startDocument() throws SAXException {
		/*super.startDocument();*/
		list = new ArrayList<ResourceXml>();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		/*super.startElement(uri, localName, qName, attributes);*/
		if("resource".equals(qName)){
			resource = new ResourceXml();
		}
		preTag = qName;
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		/*super.endElement(uri, localName, qName);*/
		if("resource".equals(qName)){
			list.add(resource);
			resource = null;
		}
		preTag = null;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		/*super.characters(ch, start, length);*/
		if(null != preTag){
			String content = new String(ch,start,length);
			if("title".equals(preTag)){
				resource.setTitle(content);
			}
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static ResourceXml degister(File file){
		ResourceXml resourceXml = null;
		Digester digester = new Digester();  
	    digester.setValidating(false);  
	    digester.addObjectCreate("resource", ResourceXml.class);
	    digester.addBeanPropertySetter("resource/title");
		digester.addBeanPropertySetter("resource/keyword");
		digester.addBeanPropertySetter("resource/intro");
		digester.addBeanPropertySetter("resource/resForm");
		digester.addBeanPropertySetter("resource/url");
		digester.addBeanPropertySetter("resource/periodId");
		digester.addBeanPropertySetter("resource/subjectId");
		digester.addBeanPropertySetter("resource/editionId");
		digester.addBeanPropertySetter("resource/gradeId");
		digester.addBeanPropertySetter("resource/volumeId");
		digester.addBeanPropertySetter("resource/textbookId");
		digester.addCallMethod("resource/chapterIdList/value", "addChapterId", 0);
		digester.addCallMethod("resource/resTypeIdList/value", "addResTypeId", 0);
		digester.addBeanPropertySetter("resource/userId");
		digester.addBeanPropertySetter("resource/userName");
		digester.addBeanPropertySetter("resource/userAccount");
		digester.addBeanPropertySetter("resource/orgId");
		digester.addBeanPropertySetter("resource/orgName");
		try {
			resourceXml = (ResourceXml) digester.parse(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		resourceXml.setFileMd5Name(file.getName().substring(0, file.getName().lastIndexOf(".xml")));
		resourceXml.setFileExt(resourceXml.getFileMd5Name()
				.substring(resourceXml.getFileMd5Name().lastIndexOf(".") + 1).toLowerCase());
		resourceXml.setFilePath(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".xml")));
		return resourceXml;
	}
}

package com.example.rss.data.xml;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {

    private DocumentBuilder documentBuilder;
    private Document document;
    Channel channelEntity;


    public XmlParser(String xmlRaw) {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlRaw)));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public XmlParser(URL url) {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(url.openStream());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public Channel parseChannel() throws IOException {
            NodeList rssTag = document.getElementsByTagName("rss");
            NodeList channelTag = document.getElementsByTagName("channel");
            String version = getVersion(rssTag);
            channelEntity = getMetaChannel(channelTag);
            return channelEntity;
    }

    public Channel getChannel(){
        return channelEntity;
    }


    private Channel getMetaChannel(NodeList channelTag) throws IOException {
        if (channelTag.getLength() > 1)
            throw new IOException("Several '<channel>' tag");

        Node channel = channelTag.item(0);
        NodeList meta = channel.getChildNodes();

        Channel channelObj = new Channel();

        for (int i = 0; i < meta.getLength(); i++){
            if (meta.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) meta.item(i);

                if (element.getTagName().equals("item")) {
                    continue;
                }

                switch (element.getTagName()) {
                    case "title":
                        channelObj.setTitle(element.getFirstChild().getNodeValue());
                        break;
                    case "description":
                        channelObj.setDescription(element.getFirstChild().getNodeValue());
                        break;
                    case "link":
                        channelObj.setLink(element.getFirstChild().getNodeValue());
                        break;
                    case "lastBuildDate":
                        channelObj.setLastBuild(element.getFirstChild().getNodeValue());
                        break;
                    case "image":
                        NodeList imageTag = element.getChildNodes();
                        File fileEntity = new File();
                        for (int j = 0; j < imageTag.getLength(); j++) {
                            if (imageTag.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element imageElement = (Element) imageTag.item(j);
                                switch (imageElement.getTagName()) {
                                    case "url":
                                        fileEntity.setPath(imageElement.getFirstChild().getNodeValue());
                                        break;
                                    case "title":
                                        fileEntity.setTitle(imageElement.getFirstChild().getNodeValue());
                                        break;
                                    case "description":
                                        fileEntity.setDescription(imageElement.getFirstChild().getNodeValue());
                                        break;
                                }
                            }
                        }
                        channelObj.setFile(fileEntity);
                        break;
                }
            }
        }

        return channelObj;

    }

    private String getVersion(NodeList rssTag) throws IOException {
        if (rssTag.getLength() > 1)
            throw new IOException("Several 'rss' tag");

        Node rss = rssTag.item(0);
        if (rss.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) rss;
            String value = element.getAttribute("version");
            if (value != null){
                return value;
            }
        }
        throw new IOException("Error find version rss");
    }
}

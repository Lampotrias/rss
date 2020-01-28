package com.example.rss.domain.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {

    private DocumentBuilder documentBuilder;
    private Document document;
    private String rssVersion;

    public XmlParser(InputStream inputStream) {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public XmlChannelRawObject parseChannel() throws IOException {
        NodeList channelTag = document.getElementsByTagName("channel");
        return getMetaChannel(channelTag, getVersion());
    }

    public String getVersion() throws IOException{
        if (rssVersion == null){
            NodeList rssTag = document.getElementsByTagName("rss");
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
        return rssVersion;
    }

    private XmlChannelRawObject getMetaChannel(NodeList channelTag, String rssVersion) throws IOException {
        if (channelTag.getLength() > 1)
            throw new IOException("Several '<channel>' tag");

        Node channel = channelTag.item(0);
        NodeList meta = channel.getChildNodes();

        XmlChannelRawObject channelEntity = new XmlChannelRawObject();

        for (int i = 0; i < meta.getLength(); i++){
            if (meta.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) meta.item(i);

                if (element.getTagName().equals("item")) {
                    continue;
                }

                switch (element.getTagName()) {
                    case "title":
                        channelEntity.setTitle(element.getFirstChild().getNodeValue());
                        break;
                    case "description":
                        channelEntity.setDescription(element.getFirstChild().getNodeValue());
                        break;
                    case "link":
                        channelEntity.setLink(element.getFirstChild().getNodeValue());
                        break;
                    case "lastBuildDate":
                        channelEntity.setLastBuild(element.getFirstChild().getNodeValue());
                        break;
                    case "image":
                        NodeList imageTag = element.getChildNodes();
                        XmlFileRawObject fileEntity = new XmlFileRawObject();
                        for (int j = 0; j < imageTag.getLength(); j++) {
                            if (imageTag.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element imageElement = (Element) imageTag.item(j);
                                switch (imageElement.getTagName()) {
                                    case "url":
                                        fileEntity.setPath(imageElement.getFirstChild().getNodeValue());
                                        break;
                                    case "description":
                                        fileEntity.setDescription(imageElement.getFirstChild().getNodeValue());
                                        break;
                                }
                            }
                        }
                        channelEntity.setFile(fileEntity);
                        break;
                }
            }
        }

        return channelEntity;
    }
}

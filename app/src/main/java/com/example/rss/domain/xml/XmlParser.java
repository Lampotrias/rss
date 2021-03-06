package com.example.rss.domain.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    private String getVersion() throws IOException{
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

    public List<XmlItemRawObject> parseItems () throws IOException {
        NodeList itemTags = document.getElementsByTagName("item");
        return getChildItems(itemTags, getVersion());
    }

    private List<XmlItemRawObject> getChildItems(NodeList itemTags, String rssVersion){
        List<XmlItemRawObject> items = new ArrayList<>();
        for (int i = 0; i < itemTags.getLength(); i++) {
            if (itemTags.item(i).getNodeType() == Node.ELEMENT_NODE) {
                NodeList itemChild = itemTags.item(i).getChildNodes();
                XmlItemRawObject item = new XmlItemRawObject();
                for (int j=0; j < itemChild.getLength(); j++){
                    if (itemChild.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) itemChild.item(j);

                        switch (element.getTagName()) {
                            case "title":
                                item.setTitle(element.getFirstChild().getNodeValue());
                                break;
                            case "link":
                                item.setLink(element.getFirstChild().getNodeValue());
                                break;
                            case "description":
                                NodeList subDescription = element.getChildNodes();
                                if (subDescription.getLength() == 1) {
                                    item.setDescription(element.getFirstChild().getNodeValue());
                                    break;
                                }
                                for (int sub = 0; sub < subDescription.getLength(); sub++){
                                    if (subDescription.item(sub).getNodeType() == Node.CDATA_SECTION_NODE){
                                        item.setDescription(subDescription.item(sub).getNodeValue());
                                        break;
                                    }
                                }

                                break;
                            case "pubDate":
                                item.setPubDate(element.getFirstChild().getNodeValue());
                                break;
                            case "enclosure":
                                String url = element.getAttribute("url");
                                if (url.length() > 0){
                                    XmlFileRawObject xmlFileRawObject = new XmlFileRawObject();
                                    xmlFileRawObject.setPath(url);
                                    String description = element.getAttribute("type");
                                    if (description.length() > 0)
                                        xmlFileRawObject.setDescription(description);
                                    else
                                        xmlFileRawObject.setDescription("");
                                    item.setEnclosure(xmlFileRawObject);
                                }
                                break;
                        }
                    }
                }
                item.setGuid(makeMd5(item.getTitle() + item.getLink()));
                items.add(item);
            }
        }
        return items;
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
                        channelEntity.setEnclosure(fileEntity);
                        break;
                }
            }
        }
        return channelEntity;
    }

    private String makeMd5(String s){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}

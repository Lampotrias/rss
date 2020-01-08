package com.example.rss.data.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {

    private DocumentBuilder documentBuilder;
    private Document document;

    ArrayList<String> whiteListChannelsTag = new ArrayList<>(Arrays.asList("title", "description", "category", "image_id", "link", "last_build"));

    public XmlParser(String xmlRaw) {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlRaw)));

            NodeList rssTag = document.getElementsByTagName("rss");
            NodeList channelTag = document.getElementsByTagName("channel");
            String version = getVersion(rssTag);
            HashMap<String, String> metaChannel = getMetaChannel(channelTag);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> getMetaChannel(NodeList channelTag) throws IOException {
        if (channelTag.getLength() > 1)
            throw new IOException("Several '<channel>' tag");

        Node channel = channelTag.item(0);
        NodeList meta = channel.getChildNodes();

        for (int i = 0; i < meta.getLength(); i++){
            Node nNode = meta.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE){

            }
        }


        return null;
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

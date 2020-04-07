package com.example.rss.domain.xml;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class XmlParserTest {

    private ArrayList<String> paths = new ArrayList<>();

    @Before
    public void before() {
        paths.add("https://www.feedforall.com/sample.xml");
        paths.add("https://lenta.ru/rss");
        paths.add("http://feeds.bbci.co.uk/news/world/rss.xml");
        paths.add("http://billmaher.hbo.libsynpro.com/rss");
        paths.add("https://rss.art19.com/the-bill-simmons-podcast");


    }

    @Test
    public void parseChannel() {

        for (String path : paths) {
            try {
                InputStream stream = new URL(path).openStream();
                XmlParser xmlParser = new XmlParser(stream);
                XmlChannelRawObject xmlChannelRawObjects = xmlParser.parseChannel();

                isNotNull(xmlChannelRawObjects, xmlChannelRawObjects.getTitle());
                isNotNull(xmlChannelRawObjects, xmlChannelRawObjects.getDescription());
                isNotNull(xmlChannelRawObjects, xmlChannelRawObjects.getLink());

                int len = xmlChannelRawObjects.getDescription().length();
                assertThat(len).isGreaterThan(5);

                isNotNull(xmlChannelRawObjects, xmlChannelRawObjects.getEnclosure().getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void isNotNull(Object o, String str) {
        if (o instanceof XmlChannelRawObject) {
            assertThat(str).isNotEmpty();
        } else {
            assertThat(str).isNotEmpty();
        }
    }

    @Test
    public void parseItems() {
        for (String path : paths) {
            try {
                InputStream stream = new URL(path).openStream();
                XmlParser xmlParser = new XmlParser(stream);
                List<XmlItemRawObject> xmlItemRawObjects = xmlParser.parseItems();

                assertThat(xmlItemRawObjects).isNotEmpty();

                for (XmlItemRawObject xmlItemRawObject : xmlItemRawObjects) {
                    isNotNull(xmlItemRawObject, xmlItemRawObject.getDescription());
                    isNotNull(xmlItemRawObject, xmlItemRawObject.getGuid());

                    isNotNull(xmlItemRawObject, xmlItemRawObject.getPubDate());
                    isNotNull(xmlItemRawObject, xmlItemRawObject.getTitle());
                    if (xmlItemRawObject.getEnclosure() != null) {
                        isNotNull(xmlItemRawObject, xmlItemRawObject.getEnclosure().getDescription());
                        isNotNull(xmlItemRawObject, xmlItemRawObject.getEnclosure().getPath());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
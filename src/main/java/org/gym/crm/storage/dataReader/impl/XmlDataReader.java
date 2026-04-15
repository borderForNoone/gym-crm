package org.gym.crm.storage.dataReader.impl;

import org.gym.crm.storage.dataReader.DataReader;
import org.gym.crm.storage.exceptions.StorageException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlDataReader implements DataReader {
    @Override
    public List<String[]> readData(String filePath) {
        List<String[]> records = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i) instanceof Element element) {
                    NodeList fields = element.getChildNodes();
                    String[] fieldArray = new String[fields.getLength()];

                    for (int j = 0; j < fields.getLength(); j++) {
                        if (fields.item(j) instanceof Element field) {
                            fieldArray[j] = field.getTextContent();
                        }
                    }
                    records.add(fieldArray);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new StorageException("Failed to read XML file: " + filePath, e);
        }

        return records;
    }
}

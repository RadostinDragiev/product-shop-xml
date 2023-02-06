package com.example.productshopxml.utils.impl;

import com.example.productshopxml.utils.XmlParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser {
    private JAXBContext jaxbContext;

    @Override
    public void marshalFile(Object object, String path) throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = this.jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(path));
    }

    @Override
    public <T> Object unmarshalFile(Class<T> tClass, String path) throws JAXBException, FileNotFoundException {
        this.jaxbContext = JAXBContext.newInstance(tClass);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(bufferedReader);
    }
}

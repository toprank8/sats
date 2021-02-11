package com.tr8.pdf.serviceContrats;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface IPDFFormatCircuitProcessorService {


    public void load(String xmlPath, String year, String id) throws ParserConfigurationException, IOException, SAXException ;

}

package com.tr8.pdf.service;


import com.tr8.pdf.serviceContrats.IPDFFormatCircuitProcessorService;
import com.tr8.pdf.serviceContrats.IPDFFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@EnableScheduling
public class PDFFormatService implements IPDFFormatService  {

    @Autowired
    private IPDFFormatCircuitProcessorService ipdfFormatCircuitProcessorService ;

    @Value("${pdf.folder.path}")
    private String XML_FOLDER ;


    @Scheduled(fixedDelay = 600000)
    public void process() throws IOException, ParserConfigurationException, SAXException {
        File folder = new File(XML_FOLDER);
        int count = 1 ;
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                String FILENAME = XML_FOLDER + "/" + file.getName() ;
                File f = new File(FILENAME) ;
                if(Files.probeContentType(f.toPath()).toLowerCase().contains("xml")){
                    System.out.println(count + "- " + FILENAME);
                    this.processXML(FILENAME);
                    count++ ;
                }
            }
        }
    }

    private void processXML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(filename);
        document.getDocumentElement().normalize();

        /**
         * PDF with format Circuit
         */
        NodeList texts = document.getElementsByTagName("text");
        if(texts != null && texts.getLength() > 10) {
            if (texts.item(1).getTextContent().contains("Circuit")) {
                String file = filename.split("/")[filename.split("/").length - 1];
                if (file.split("-").length == 3) {
                    String type = file.split("-")[0];
                    String year = file.split("-")[1];
                    String id = file.split("-")[2].split("\\.")[0];
                    ipdfFormatCircuitProcessorService.load(filename, year, id);
                }
            }
        }


    }



}

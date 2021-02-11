package com.tr8.pdf;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;

public class 	Test {

	private static final String FILE_NAME = "/tmp/itext.pdf";

	public static void main(String[] args) {

		PdfReader reader;

		try {

			reader = new PdfReader("/home/ups/Descargas/a/mds.pdf");

			// pageNumber = 1



			String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

			System.out.println(textFromPage);

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
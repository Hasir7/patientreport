package com.example.patientreport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MedicalReport {

    public String readPdfLineByLine(String filePath) {
        StringBuilder pdfContent = new StringBuilder();

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            String[] lines = text.split(System.lineSeparator());

            for (String line : lines) {
                pdfContent.append(line).append("<br>"); // Adding <br> for line breaks in HTML
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pdfContent.toString();
    }
}

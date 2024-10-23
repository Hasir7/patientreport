package com.example.patientreport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

@Service
public class MedicalReport {

    private static final String PDF_FILE_PATH = "src/main/resources/uploads/Report.pdf";

    public String readPdf() {
        StringBuilder content = new StringBuilder();

        try {

            File pdfFile = new File(PDF_FILE_PATH);
            PDDocument document = PDDocument.load(pdfFile);


            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);


            String[] lines = pdfText.split("\n");


            StringBuilder patientInfo = new StringBuilder();
            List<String> vitalLogs = new ArrayList<>();
            HashSet<String> patientNames = new HashSet<>();
            boolean isPatientInfoCaptured = false;


            for (String line : lines) {
                // Capture patient information only once
                if (line.contains("Patient Name") && !patientNames.contains(line)) {
                    patientNames.add(line.trim());
                    if (!isPatientInfoCaptured) {
                        patientInfo.append("<strong>Patient Information:</strong><br>"); // Bold heading
                        patientInfo.append(line.trim()).append("<br>");
                        isPatientInfoCaptured = true; // Capture only once
                    }
                }


                if (line.contains("Study Date") || line.contains("MRN") || line.contains("Age") ||
                        line.matches(".*(Gender|Race|Height|Weight|BSA).*")) {
                    patientInfo.append(line.trim()).append("<br>");
                }


                if (line.matches("\\d{1,2}:\\d{2}:\\d{2}.*SpO2.*|.*HR.*|.*NBP.*|.*RR.*|.*Temp.*")) {
                    vitalLogs.add(line.trim());
                }
            }


            content.append(patientInfo).append("<br>"); // Adding space

            content.append("<strong>Event Log (Vitals and Measurements):</strong><br>");


            for (String log : vitalLogs) {
                content.append(log).append("<br>");
            }


            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            content.append("Failed to read the PDF file.");
        }

        return content.toString();
    }
}

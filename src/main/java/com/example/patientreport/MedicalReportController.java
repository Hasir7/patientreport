package com.example.patientreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalReportController {

    @Autowired
    private MedicalReport medicalReport;

    @GetMapping("/read-pdf")
    public String readPdf() {

        String filePath = "src/main/resources/uploads/Report.pdf";
        return medicalReport.readPdfLineByLine(filePath);
    }
}


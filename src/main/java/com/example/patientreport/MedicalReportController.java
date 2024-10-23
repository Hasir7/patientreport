package com.example.patientreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalReportController {

    @Autowired
    private MedicalReport medicalReport;

    @GetMapping(value = "/read-pdf", produces = "text/html")  // Change response type to HTML
    @ResponseBody
    public String readPdf() {
        return medicalReport.readPdf();
    }
}


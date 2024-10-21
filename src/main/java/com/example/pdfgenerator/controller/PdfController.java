package com.example.pdfgenerator.controller;

import com.example.pdfgenerator.model.InvoiceRequest;
import com.example.pdfgenerator.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest request) throws Exception {
        String pdfPath = pdfService.generatePdf(request);
        return ResponseEntity.ok("PDF generated at: " + pdfPath);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable String hash) throws Exception {
        File pdfFile = new File("pdfs/" + hash + ".pdf");

        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + pdfFile.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

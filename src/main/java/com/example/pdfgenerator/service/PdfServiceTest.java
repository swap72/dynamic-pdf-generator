package com.example.pdfgenerator.service;

import com.example.pdfgenerator.model.InvoiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

class PdfServiceTest {

    private PdfService pdfService;

    @BeforeEach
    void setUp() {
        pdfService = new PdfService();  // Initialize the PdfService
    }

    @Test
    void testGeneratePdf_Success() throws Exception {
        // Given: Prepare the invoice request data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSeller("XYZ Pvt. Ltd.");
        invoiceRequest.setBuyer("Vedant Computers");
        invoiceRequest.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequest.setSellerAddress("New Delhi, India");
        invoiceRequest.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequest.setBuyerAddress("New Delhi, India");

        // Add items
        InvoiceRequest.Item item = new InvoiceRequest.Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);

        invoiceRequest.setItems(List.of(item));

        // When: Generate the PDF
        String pdfPath = pdfService.generatePdf(invoiceRequest);

        // Then: Verify that the PDF is generated correctly
        assertNotNull(pdfPath, "PDF file path should not be null");
        assertTrue(pdfPath.endsWith(".pdf"), "Generated file should have a .pdf extension");

        // Check that the file exists
        File pdfFile = new File(pdfPath);
        assertTrue(pdfFile.exists(), "PDF file should exist on the filesystem");

        // Clean up (delete the file)
        pdfFile.delete();
    }

    @Test
    void testGeneratePdf_ReuseExistingFile() throws Exception {
        // Given: Prepare the same input data as before
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSeller("XYZ Pvt. Ltd.");
        invoiceRequest.setBuyer("Vedant Computers");
        invoiceRequest.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequest.setSellerAddress("New Delhi, India");
        invoiceRequest.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequest.setBuyerAddress("New Delhi, India");

        InvoiceRequest.Item item = new InvoiceRequest.Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);
        invoiceRequest.setItems(List.of(item));

        // When: Generate the PDF twice with the same data
        String pdfPath1 = pdfService.generatePdf(invoiceRequest);
        String pdfPath2 = pdfService.generatePdf(invoiceRequest);

        // Then: Verify that the same file path is returned
        assertEquals(pdfPath1, pdfPath2, "The same file path should be returned when the input data is identical");

        // Clean up (delete the file)
        File pdfFile = new File(pdfPath1);
        pdfFile.delete();
    }
}

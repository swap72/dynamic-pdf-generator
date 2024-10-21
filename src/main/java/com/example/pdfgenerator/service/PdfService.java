package com.example.pdfgenerator.service;

import com.example.pdfgenerator.model.InvoiceRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

@Service
public class PdfService {

    private static final String PDF_STORAGE_PATH = "pdfs/";

    // Generate a consistent hash from the input data
    private String generateHash(InvoiceRequest request) throws Exception {
        StringBuilder dataToHash = new StringBuilder();
        dataToHash.append(request.getSeller()).append(request.getSellerGstin()).append(request.getSellerAddress());
        dataToHash.append(request.getBuyer()).append(request.getBuyerGstin()).append(request.getBuyerAddress());

        // Append each item's properties to the hash
        request.getItems().forEach(item -> {
            dataToHash.append(item.getName()).append(item.getQuantity())
                      .append(item.getRate()).append(item.getAmount());
        });

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(dataToHash.toString().getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();  // Return hash as hex string
    }

    public String generatePdf(InvoiceRequest request) throws Exception {
        // Generate the hash from the input data to use as the file name
        String uniqueId = generateHash(request);

        // Construct the file path
        String fileName = PDF_STORAGE_PATH + uniqueId + ".pdf";
        File pdfFile = new File(fileName);

        // Check if the PDF already exists, if yes, return the file path
        if (pdfFile.exists()) {
            return fileName;  // PDF already exists, so return its path
        }

        // Create directories if they do not exist
        Files.createDirectories(Paths.get(PDF_STORAGE_PATH));

        // Generate the PDF and save it to the file system
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        // Create a table for seller and buyer info with 2 columns
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100); // Set table width to 100% of the page
        infoTable.setSpacingAfter(10); // Add spacing after this table

        // Add cells for Seller info
        PdfPCell sellerCell = new PdfPCell();
        sellerCell.addElement(new Phrase("Seller:\n" + request.getSeller() + "\n" + request.getSellerAddress() + "\nGSTIN: " + request.getSellerGstin()));
        sellerCell.setPadding(10);
        infoTable.addCell(sellerCell);

        // Add cells for Buyer info
        PdfPCell buyerCell = new PdfPCell();
        buyerCell.addElement(new Phrase("Buyer:\n" + request.getBuyer() + "\n" + request.getBuyerAddress() + "\nGSTIN: " + request.getBuyerGstin()));
        buyerCell.setPadding(10);
        infoTable.addCell(buyerCell);

        // Add the infoTable to the document
        document.add(infoTable);

        // Add a blank line
        document.add(new Paragraph(" "));

        // Create a table for the item details with 4 columns
        PdfPTable itemTable = new PdfPTable(4);
        itemTable.setWidthPercentage(100);  // Set table width to 100%
        itemTable.setWidths(new float[]{2, 1, 1, 1});  // Column widths for Item, Quantity, Rate, Amount

        // Add headers for the item table
        addTableHeader(itemTable, "Item", "Quantity", "Rate", "Amount");

        // Loop through each item in the request and add it to the table
        for (InvoiceRequest.Item item : request.getItems()) {
            addTableCell(itemTable, item.getName());
            addTableCell(itemTable, item.getQuantity());
            addTableCell(itemTable, String.valueOf(item.getRate()));
            addTableCell(itemTable, String.valueOf(item.getAmount()));
        }

        // Add itemTable to the document
        document.add(itemTable);

        document.close();  // Close the document

        return fileName;  // Return the file path of the newly generated PDF
    }

    // Helper method to add table headers
    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header));
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }
    }

    // Helper method to add table cells
    private void addTableCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setPadding(5);
        table.addCell(cell);
    }
}

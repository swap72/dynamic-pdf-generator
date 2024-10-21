# Dynamic PDF Generator - Spring Boot Application

## Project Description

This project is a **Spring Boot** application that provides a **REST API** for generating PDF files based on input data. The PDF is generated using the **iText** library and includes details like seller, buyer, and a list of purchased items. The generated PDF is stored on the local file system, and if the same data is provided again, the application will reuse the existing PDF instead of generating a new one. (with the help of caching and Object Hashing).

---

## Features

- Accepts input data via REST API and generates a PDF.
- Stores the generated PDF in local storage.
- Reuses existing PDFs when the same data is provided, preventing redundant file generation.
- Provides the ability to download the generated PDF. (Local File System)
- Only generates a fresh new file if the details are not duplicate


---

## Project Structure

```
src
├── main
│   └── java
│       └── com
│           └── example
│               └── pdfgenerator
│                   ├── controller
│                   │   └── PdfController.java   // REST controller for PDF generation
│                   ├── model
│                   │   └── InvoiceRequest.java  // Data model representing the invoice structure
│                   ├── service
│                   │   └── PdfService.java      // Service responsible for PDF generation and storage
│                   └── PdfGeneratorApplication.java // Spring Boot application main class

```

---

## Requirements

- Java 8+ (or compatible JDK)
- Maven
- iTextPDF 5.5.13.2
- Spring Boot 3.x
---

## Dependencies

The project uses the following Maven dependencies:

```xml
<dependencies>
    <!-- Spring Boot Dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- iTextPDF for PDF generation -->
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.2</version>
    </dependency>

    <!-- Lombok for reducing boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Boot Starter Test for testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## API Endpoints

### 1. **Generate PDF**

- **URL**: `/api/pdf/generate`
- **Method**: `POST`
- **Description**: This endpoint accepts the invoice data as a JSON payload, generates a PDF, and stores it locally.
- **Request Body**:
    ```json
    {
        "seller": "XYZ Pvt. Ltd.",
        "sellerGstin": "29AABBCCDD121ZD",
        "sellerAddress": "New Delhi, India",
        "buyer": "Vedant Computers",
        "buyerGstin": "29AABBCCDD131ZD",
        "buyerAddress": "New Delhi, India",
        "items": [
            {
                "name": "Product 1",
                "quantity": "12 Nos",
                "rate": 123.00,
                "amount": 1476.00
            }
        ]
    }
    ```

- **Response**:
    - **200 OK**: Returns the path to the generated PDF.
    - **400 Bad Request**: If the input data is invalid.
    - **500 Internal Server Error**: If an error occurs during PDF generation.

- **Sample Success Response**:
    ```json
    {
        "message": "PDF generated successfully: pdfs/invoice-xyz123.pdf"
    }
    ```

---

## How to Run the Application

1.To Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. To Navigate to the project directory:
   ```bash
   cd pdf-generator
   ```

3. To Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```

4. Use **Postman** to test the API:

   **Example Post Method of Postman**:
   
   ```json
   {
    "seller": "XYZ Pvt. Ltd.",
    "sellerGstin": "29AABBCCDD121ZD",
    "sellerAddress": "New Delhi, India",
    "buyer": "Vedant Computers",
    "buyerGstin": "29AABBCCDD131ZD",
    "buyerAddress": "New Delhi, India",
    "items": [
        {
            "name": "Product 1",
            "quantity": "12 Nos",
            "rate": 123.00,
            "amount": 1476.00
        }
    ]
   } 
   ```
5. To use this Spring Boot Application:
    - Download this repository as zip
    - Import it as a Maven Project in Spring Tool Suite, click finish
    - Run as Spring Boot Application
    - Test your response while the Application runs.
      

---

---

## Project Demo

To demonstrate the project, you can:
1. Run the Spring Boot application.
2. Use **Postman** to submit valid and invalid data.
3. Check the local **`pdfs/`** folder for generated PDFs.
4. The program will automatically create a directory locally if not found.

---

#### ⚠️In case you encounter web server failed to start

```
***************************
APPLICATION FAILED TO START
***************************
```
#### Description:
Web server failed to start. Port 8080 was already in use.

Action:
Identify and stop the process that's listening on port 8080 or configure this application to listen on another port.

**Solution**: Find the service of the PID using /listening from the port 8080 : 
In Local your Terminal / Shell
```netstat -ano | findstr :8080```
Then Force kill a Process with PID
```taskkill /F /PID 5122```
or you can configure the Application to listen from another port altogether from Application properties file
this will prevent any other service from using that port.





## Visual References
![ss1](https://github.com/user-attachments/assets/3bd3b983-f8c6-4ec5-8b60-bb4d2cb9a2db)
![Screenshot 2024-10-21 182139](https://github.com/user-attachments/assets/8c94e7eb-0f58-46d1-bdbf-85e4520a4ab5)
![Screenshot 2024-10-21 182220](https://github.com/user-attachments/assets/3e8ead2b-8313-438e-a247-2d6ab6b23b29)


---


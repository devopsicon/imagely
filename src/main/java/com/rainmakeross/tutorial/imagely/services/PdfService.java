package com.rainmakeross.tutorial.imagely.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class PdfService {

    /**
     * Service for producing and returning a PDF file from Image string
     *
     * @param imgString is base64 string
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public File createPDFFromImageString(String imgString) throws IOException, DocumentException {
        String result = imgString.split(",")[1];
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(result);

        String generatedString = LocalDateTime.now() +".pdf";
        try (OutputStream stream = new FileOutputStream(generatedString)) {
            stream.write(bytes);
        }

        FileOutputStream fos = new FileOutputStream(generatedString);

        Document doc = new Document();

        PdfWriter writer = PdfWriter.getInstance(doc, fos);
        writer.open();

        doc.open();
        doc.add(Image.getInstance(bytes));
        doc.close();

        writer.close();
        fos.close();

        return new File(generatedString);
    }
}

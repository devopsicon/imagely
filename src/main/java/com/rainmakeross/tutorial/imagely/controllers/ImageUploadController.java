package com.rainmakeross.tutorial.imagely.controllers;

import com.itextpdf.text.DocumentException;
import com.rainmakeross.tutorial.imagely.services.PdfService;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static org.asynchttpclient.Dsl.*;

import java.io.*;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/image")
@CrossOrigin()
public class ImageUploadController {

    @Autowired
    AsyncHttpClient asyncHttpClient;

    Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @Autowired
    PdfService pdfService;

    /**
     * End point to receive base64 Image String
     * @param bodyString
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     * @throws DocumentException
     */
    @PostMapping()
    public ResponseEntity<?> receiveImage(@RequestBody String bodyString) throws ExecutionException, InterruptedException, IOException, DocumentException {
        String pwd =  System.getenv("MAILGUN_PWD");
        String username =  System.getenv("MAILGUN_NAME");
        String url = System.getenv("MAILGUN_URL");
        String from = System.getenv("MAILGUN_FROM");
        String to = System.getenv("MAILGUN_TO");


        File pdf = pdfService.createPDFFromImageString(bodyString);

        Realm realm = new Realm.Builder(username, pwd)
                .setUsePreemptiveAuth(true)
                .setScheme(Realm.AuthScheme.BASIC)
                .build();
        Request r= asyncHttpClient.preparePost(url)
                .setRealm(realm)
                .addBodyPart(new StringPart("from", from))
                .addBodyPart(new StringPart("to",to))
                .addBodyPart(new StringPart("subject","Ur image is contained"))
                .addBodyPart(new StringPart("text", "Congratulations, you just sent yourself an image taken by your very own camera"))
                .addBodyPart(new FilePart("inline",pdf))
                .build();

        ListenableFuture<Response> r1 = asyncHttpClient.executeRequest(r);

        logger.info(r1.get().getStatusText());
        pdf.delete();
        return new ResponseEntity<>("All Good", HttpStatus.OK);
    }
}

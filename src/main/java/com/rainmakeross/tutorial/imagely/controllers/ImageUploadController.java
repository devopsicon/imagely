package com.rainmakeross.tutorial.imagely.controllers;

import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.asynchttpclient.Dsl.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/image")
@CrossOrigin()
public class ImageUploadController {
    AsyncHttpClient asyncHttpClient = asyncHttpClient();

    Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @PostMapping()
    public ResponseEntity<?> receiveImage(@RequestBody String bodyString) throws ExecutionException, InterruptedException, IOException {
        String pwd =  System.getenv("MAILGUN_PWD");
        String username =  System.getenv("MAILGUN_NAME");
        String url = System.getenv("MAILGUN_URL");
        String from = System.getenv("MAILGUN_FROM");
        String to = System.getenv("MAILGUN_TO");


        String result = bodyString.split(",")[1];
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = LocalDateTime.now() +".jpg";
        byte[] bytes = decoder.decode(result);
        try (OutputStream stream = new FileOutputStream(generatedString)) {
            stream.write(bytes);
        }

        File imgFile = new File(generatedString);

        Realm realm = new Realm.Builder(username, pwd)
                .setUsePreemptiveAuth(true)
                .setScheme(Realm.AuthScheme.BASIC)
                .build();
        Request r= post(url)
                .setRealm(realm)
                .addBodyPart(new StringPart("from", from))
                .addBodyPart(new StringPart("to",to))
                .addBodyPart(new StringPart("subject","Ur image is contained"))
                .addBodyPart(new StringPart("text", "Congratulations, you just sent yourself an image taken by your very own camera"))
                .addBodyPart(new FilePart("inline",imgFile))
                .build();

        Response r1 = asyncHttpClient.executeRequest(r).get();

        logger.info(r1.getStatusText());
        imgFile.delete();
        return new ResponseEntity<>("All Good", HttpStatus.OK);
    }
}

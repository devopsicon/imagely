package com.rainmakeross.tutorial.imagely.controllers;

import com.itextpdf.text.DocumentException;
import com.rainmakeross.tutorial.imagely.services.MockHttpClient;
import com.rainmakeross.tutorial.imagely.services.PdfService;
import com.sun.org.apache.xpath.internal.Arg;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageUploadControllerTest {

    @Rule
    public final EnvironmentVariables environmentVariables
            = new EnvironmentVariables();

    @Mock
    AsyncHttpClient mockHttpClient;

    @Mock
    File mockFile;

    @Mock
    PdfService pdfServiceMock;

    @Mock
    Request requestMock;

    @Mock
    BoundRequestBuilder mockBoundRequestBuilder;

    @Mock
    ListenableFuture<Response> listenableFutureMock;

    @Mock
    Response responseMock;

    @InjectMocks
    ImageUploadController imageUploadController;

    @Before
    public void setUp() throws Exception {
        //imageUploadController.setAsyncHttpClient(mockHttpClient);
        Mockito.when(pdfServiceMock.createPDFFromImageString("test")).thenReturn(mockFile);

        Mockito.when(mockFile.isFile()).thenReturn(true);
        Mockito.when(mockFile.canRead()).thenReturn(true);


        Mockito.when(mockHttpClient.preparePost("test")).thenReturn(mockBoundRequestBuilder);
        Mockito.when(mockHttpClient.executeRequest(requestMock)).thenReturn(listenableFutureMock);
        Mockito.when(listenableFutureMock.get()).thenReturn(responseMock);
        Mockito.when(responseMock.getStatusText()).thenReturn("All Good");

        Mockito.when(mockBoundRequestBuilder.setRealm(ArgumentMatchers.any(Realm.class))).thenReturn(mockBoundRequestBuilder);
        Mockito.when(mockBoundRequestBuilder.addBodyPart(ArgumentMatchers.any(StringPart.class))).thenReturn(mockBoundRequestBuilder);
        Mockito.when(mockBoundRequestBuilder.addBodyPart(ArgumentMatchers.any(FilePart.class))).thenReturn(mockBoundRequestBuilder);
        Mockito.when(mockBoundRequestBuilder.build()).thenReturn(requestMock);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void receiveImage() throws ExecutionException, InterruptedException, IOException, DocumentException {
        environmentVariables.set("MAILGUN_URL", "test");
        environmentVariables.set("MAILGUN_NAME", "test");
        environmentVariables.set("MAILGUN_PWD", "test");
        environmentVariables.set("MAILGUN_FROM", "test");
        environmentVariables.set("MAILGUN_TO", "test");



        imageUploadController.receiveImage("test");
        Mockito.verify(mockHttpClient).preparePost("test");
    }
}
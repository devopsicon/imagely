package com.rainmakeross.tutorial.imagely.services;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@Service
public class HttpClientService {

    /**
     * Produces an Async HttpClient bean for DI
     * @return Async HttpClient
     */
    @Bean
    public AsyncHttpClient createHttpClient(){
        return new DefaultAsyncHttpClient();
    }
}

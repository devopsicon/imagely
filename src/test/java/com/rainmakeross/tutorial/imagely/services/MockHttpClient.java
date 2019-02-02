package com.rainmakeross.tutorial.imagely.services;

import org.asynchttpclient.*;

import java.io.IOException;
import java.util.function.Predicate;

public class MockHttpClient implements AsyncHttpClient {
    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public AsyncHttpClient setSignatureCalculator(SignatureCalculator signatureCalculator) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepare(String method, String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareGet(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareConnect(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareOptions(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareHead(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder preparePost(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder preparePut(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareDelete(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder preparePatch(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareTrace(String url) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareRequest(Request request) {
        return null;
    }

    @Override
    public BoundRequestBuilder prepareRequest(RequestBuilder requestBuilder) {
        return null;
    }

    @Override
    public <T> ListenableFuture<T> executeRequest(Request request, AsyncHandler<T> handler) {
        return null;
    }

    @Override
    public <T> ListenableFuture<T> executeRequest(RequestBuilder requestBuilder, AsyncHandler<T> handler) {
        return null;
    }

    @Override
    public ListenableFuture<Response> executeRequest(Request request) {
        return null;
    }

    @Override
    public ListenableFuture<Response> executeRequest(RequestBuilder requestBuilder) {
        return null;
    }

    @Override
    public ClientStats getClientStats() {
        return null;
    }

    @Override
    public void flushChannelPoolPartitions(Predicate<Object> predicate) {

    }

    @Override
    public AsyncHttpClientConfig getConfig() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}

package br.com.fiapgrp02.jvappfromqueuetodb.utils;

public class AzConnUtils {
    public static String getAzConnStr(){
        return new StringBuilder()
                .append("DefaultEndpointsProtocol=").append(System.getenv("ENDPOINT_PROTOCOL")).append(";")
                .append("AccountName=").append(System.getenv("ACCOUNT_NAME")).append(";")
                .append("AccountKey=").append(System.getenv("ACCOUNT_KEY")).append(";")
                .append("EndpointSuffix=").append(System.getenv("ENDPOINT_SUFFIX")).toString();
    }
}

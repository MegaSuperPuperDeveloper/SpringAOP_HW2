package com.example.springaop_hw2.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textInputChannel")
public interface FileGateway {
    void WriteToFile(@Header(FileHeaders.FILENAME) String filename, String content);
}
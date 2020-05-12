package com.smc.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smc.service.ExcelProcessorService;

public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ExcelProcessorService fileProcessorService;

    @PostMapping("/importData")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		this.fileProcessorService.processExcel(file);

        return ResponseEntity.ok("Import stock price successfully.");
    }


}

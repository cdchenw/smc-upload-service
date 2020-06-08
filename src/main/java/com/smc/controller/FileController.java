package com.smc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smc.service.ExcelProcessorService;
import com.smc.vo.StockPriceVo;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Autowired
    private ExcelProcessorService fileProcessorService;

    @PostMapping("/excel")
    public Map<String, List<StockPriceVo>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    	Map<String, List<StockPriceVo>> rsMap = this.fileProcessorService.processExcel(file);
        return rsMap;
    }


}

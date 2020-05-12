package com.smc.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.smc.repository.StockPriceRepository;



/**
 * Excel format sample.
Stock Code	Stock Exchange	Price Per Share(in Rs)		Date Time
TOSYY1 			BSE				356.23					2019/6/8 10:30:00
TOSYY1 			BSE				361.31					2019/6/8 10:35:00
TOSYY1 			BSE				358.12					2019/6/8 10:40:00
TOSYY1 			BSE				357.09					2019/6/8 10:45:00
TOSYY1 			BSE				353.62					2019/6/8 10:50:00
TOSYY1 			BSE				349.56					2019/6/8 10:55:00
TOSYY1 			BSE				351.43					2019/6/8 11:00:00
TOSYY1 			BSE				350.12					2019/6/8 11:05:00
TOSYY1 			BSE				348.91					2019/6/8 11:10:00
* @Description: Parse the excel input and import the stock price row to correspond database tables
* @author cdchenw
* @date May 12, 2020
*
*/
public class ExcelProcessorService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelProcessorService.class);
	
	@Autowired
	private StockPriceRepository stockPriceRepository;
	
	public long processExcel(MultipartFile file) throws IOException {
		long successRs = 0;
		long failedRs = 0;
		InputStream iputStream = file.getInputStream();
		try {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(iputStream);
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			long index = 0;
			while (rowIterator.hasNext()) {
				if (index > 0) {
					Row row = rowIterator.next();
					try {
						String stockCode = row.getCell(0).getStringCellValue();// stock code
						String stockExchangeCode = row.getCell(1).getStringCellValue();//exchange short name
						BigDecimal price = new BigDecimal(row.getCell(2).getNumericCellValue());// stock price
						Timestamp openDate = Timestamp.valueOf(row.getCell(0).getStringCellValue());// transaction date time
						this.persistRow(price, openDate, stockCode, stockExchangeCode);
						successRs++;
					}catch(Exception e) {
						logger.error("import row failed", e.getMessage());
						failedRs++;
					}
				}
				index++;
			}
			workbook.close();
		} catch (Exception e) {
			logger.error("Exception when parse excel. terminate process", e);
		}
		return successRs;
	}
	
	
	private void persistRow(BigDecimal price, Timestamp openDate, String stockCode, String stockExchangeCode) {
		this.stockPriceRepository.saveStockPrice(price, openDate, stockCode, stockExchangeCode);
	}

}

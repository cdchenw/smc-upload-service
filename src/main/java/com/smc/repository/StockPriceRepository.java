package com.smc.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface StockPriceRepository extends CrudRepository<Entity, String> {
	
	@Modifying
	@Transactional
	@Query(value = "insert into tb_stock_price \n" + 
			"select ce.id, ? as price, ? as date, 'data import' as remarks from tb_company_exchange ce \n" + 
			"inner join tb_stock_exchange se on ce.exchange_id=se.id where ce.stock_code=? and se.short_name=?;" ,nativeQuery = true)
	public int saveStockPrice(BigDecimal price, Timestamp openDate, String stockCode, String stockExchangeCode);

}
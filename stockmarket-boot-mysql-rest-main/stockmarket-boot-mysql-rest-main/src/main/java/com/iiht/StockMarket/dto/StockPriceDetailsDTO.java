package com.iiht.StockMarket.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockPriceDetailsDTO {

	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@JsonProperty(value = "companyCode")
	@NotNull
	private Long companyCode;

	@JsonProperty(value = "currentStockPrice")
	@Digits(integer = 10, fraction = 2, message = "currentStockPrice must have precision 10 and factional part of 2 decimals")
	@NotNull
	private Double currentStockPrice;

	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	@JsonProperty(value = "stockPriceDate")
	private LocalDate stockPriceDate;

	@NotNull
	@Temporal(TemporalType.TIME)
	@PastOrPresent
	@JsonProperty(value = "stockPriceTime")
	private LocalTime stockPriceTime;

	// ---------------------------------------------------------------------------------------------------------------------------------
	public StockPriceDetailsDTO() {
		super();
	}

	public StockPriceDetailsDTO(Long id, @NotNull @Size(min = 1, max = 10) Long companyCode,
			@NotNull Double currentStockPrice, @NotNull LocalDate stockPriceDate, @NotNull LocalTime stockPriceTime) {
		super();
		Id = id;
		this.companyCode = companyCode;
		this.currentStockPrice = currentStockPrice;
		this.stockPriceDate = stockPriceDate;
		this.stockPriceTime = stockPriceTime;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Double getCurrentStockPrice() {
		return currentStockPrice;
	}

	public void setCurrentStockPrice(Double currentStockPrice) {
		this.currentStockPrice = currentStockPrice;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public LocalDate getStockPriceDate() {
		return stockPriceDate;
	}

	public void setStockPriceDate(LocalDate stockPriceDate) {
		this.stockPriceDate = stockPriceDate;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public LocalTime getStockPriceTime() {
		return stockPriceTime;
	}

	public void setStockPriceTime(LocalTime stockPriceTime) {
		this.stockPriceTime = stockPriceTime;
	}
}
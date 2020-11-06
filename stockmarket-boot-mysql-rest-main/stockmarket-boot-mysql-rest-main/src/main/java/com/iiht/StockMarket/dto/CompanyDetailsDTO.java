package com.iiht.StockMarket.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDetailsDTO {

	@JsonProperty(value = "companyCode")
	@NotNull(message = "companyCode cannot be null")
	private Long companyCode;

	@JsonProperty(value = "stockExchange")
	@NotNull(message = "stockExchange cannot be null")
	@Size(min = 3, max = 100, message = "stockExchange length should be in between 3 characters to 100 characters")
	private String stockExchange;

	@JsonProperty(value = "companyName")
	@NotNull(message = "companyName cannot be null")
	@Size(min = 3, max = 100, message = "companyName length should be in between 3 characters to 100 characters")
	private String companyName;

	@JsonProperty(value = "companyCEO")
	@NotNull(message = "companyCEO cannot be null")
	@Size(min = 5, max = 100, message = "companyCEO length should be in between 5 characters to 100 characters")
	private String companyCEO;

	@JsonProperty(value = "turnover")
	@NotNull(message = "turnover cannot be null")
	@Digits(integer = 10, fraction = 2, message = "turnover must have precision 10 and factional part of 2 decimals")
	private Double turnover;

	@JsonProperty(value = "boardOfDirectors")
	@NotNull(message = "boardOfDirectors cannot be null")
	@Size(min = 5, max = 200, message = "boardOfDirectors length should be in between 5 characters to 200 characters")
	private String boardOfDirectors;

	@JsonProperty(value = "companyProfile")
	@NotNull(message = "companyProfile cannot be null")
	@Size(min = 5, max = 255, message = "companyProfile length should be in between 5 characters to 255 characters")
	private String companyProfile;

	// ---------------------------------------------------------------------------------------------------------------------------------
	public CompanyDetailsDTO() {
		super();
	}

	public CompanyDetailsDTO(Long companyCode, String stockExchange, String companyName, String companyCEO,
			Double turnover, String boardOfDirectors, String companyProfile) {
		super();
		this.companyCode = companyCode;
		this.stockExchange = stockExchange;
		this.companyName = companyName;
		this.companyCEO = companyCEO;
		this.turnover = turnover;
		this.boardOfDirectors = boardOfDirectors;
		this.companyProfile = companyProfile;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyCEO() {
		return companyCEO;
	}

	public void setCompanyCEO(String companyCEO) {
		this.companyCEO = companyCEO;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}

	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}
}
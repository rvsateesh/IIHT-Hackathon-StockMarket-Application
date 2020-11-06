package com.iiht.StockMarket.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CompanyDetails")
public class CompanyDetails implements Serializable {

	private static final long serialVersionUID = 531050392622113165L;

	@Id
	@Column(name = "companyCode", unique = true, nullable = false)
	@NotNull(message = "companyCode should not be null and must be unique")
	private Long companyCode;

	@Column(name = "stockExchange")
	@NotNull(message = "stockExchange cannot be null")
	@Size(min = 3, max = 100, message = "stockExchange length should be in between 3 characters to 100 characters")
	private String stockExchange;

	@Column(name = "companyName")
	@Size(min = 3, max = 100, message = "companyName length should be in between 3 characters to 100 characters")
	@NotNull(message = "companyName cannot be null")
	private String companyName;

	@Column(name = "companyCEO")
	@Size(min = 5, max = 100, message = "companyCEO length should be in between 5 characters to 100 characters")
	@NotNull(message = "companyCEO cannot be null")
	private String companyCEO;

	@Column(name = "turnover", precision = 10, scale = 2)
	@NotNull(message = "turnover cannot be null")
	private Double turnover;

	@Column(name = "boardOfDirectors")
	@Size(min = 5, max = 200, message = "boardOfDirectors length should be in between 5 characters to 200 characters")
	@NotNull(message = "boardOfDirectors cannot be null")
	private String boardOfDirectors;

	@Column(name = "companyProfile")
	@Size(min = 5, max = 255, message = "companyProfile length should be in between 5 characters to 255 characters")
	@NotNull(message = "companyProfile cannot be null")
	private String companyProfile;

	// ---------------------------------------------------------------------------------------------------------------------------------
	public CompanyDetails() {
		super();
	}

	public CompanyDetails(Long companyCode, String stockExchange, String companyName, String companyCEO,
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

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Set<StockPriceDetails> getStockPriceDetails() {
		return StockPriceDetails;
	}

	public void setStockPriceDetails(Set<StockPriceDetails> stockPriceDetails) {
		StockPriceDetails = stockPriceDetails;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	@OneToMany(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "companyCode")
	private Set<StockPriceDetails> StockPriceDetails;
}
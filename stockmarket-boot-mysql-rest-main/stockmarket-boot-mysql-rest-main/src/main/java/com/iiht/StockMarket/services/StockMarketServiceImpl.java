package com.iiht.StockMarket.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iiht.StockMarket.dto.StockPriceDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceIndexDTO;
import com.iiht.StockMarket.exception.CompanyNotFoundException;
import com.iiht.StockMarket.exception.InvalidStockException;
import com.iiht.StockMarket.exception.StockNotFoundException;
import com.iiht.StockMarket.model.StockPriceDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;

@Service
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

	@Autowired
	private StockPriceRepository stockRepository;

	@Autowired
	private CompanyInfoRepository companyRepository;

	// ----------------------------------------------------------------------------
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO)
			throws InvalidStockException {
		if (isValidStockPriceDetails(stockPriceDetailsDTO)) {
			stockRepository.saveAndFlush(StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO));
			return stockPriceDetailsDTO;
		} else {
			throw new InvalidStockException("StockExchangeDetails are invalid");
		}
	}

	// ----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> deleteStock(Long companyCode) throws CompanyNotFoundException {
		if (isValidcompanyCode(companyCode)) {
			stockRepository.deleteStockByCompanyCode(companyCode);
		} else {
			throw new CompanyNotFoundException("CompanyCode provided does not exist");
		}
		return StockMarketUtility.convertToStockPriceDetailsDtoList(stockRepository.findAll());
	}

	// ----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode) throws CompanyNotFoundException {
		if (isValidcompanyCode(companyCode)) {
			return StockMarketUtility
					.convertToStockPriceDetailsDtoList(stockRepository.findStockByCompanyCode(companyCode));
		} else {
			throw new CompanyNotFoundException("CompanyCode provided does not exist");
		}
	};

	// ----------------------------------------------------------------------------
	public StockPriceDetailsDTO getStockPriceDetailsDTO(StockPriceDetails stockDetails) {
		return StockMarketUtility.convertToStockPriceDetailsDTO(stockDetails);
	}

	// ----------------------------------------------------------------------------
	public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findMaxStockPrice(companyCode, startDate, endDate);
	}

	public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findAvgStockPrice(companyCode, startDate, endDate);
	}

	public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		return stockRepository.findMinStockPrice(companyCode, startDate, endDate);
	}

	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate)
			throws StockNotFoundException {
		StockPriceIndexDTO stockPriceIndexDTO = new StockPriceIndexDTO();
		if (isValidcompanyCode(companyCode)) {
			if (endDate.isAfter(startDate)) {
				stockPriceIndexDTO.setCompanyDto(StockMarketUtility
						.convertToCompanyDetailsDTO(companyRepository.findCompanyDetailsById(companyCode)));
				stockPriceIndexDTO.setStockPriceList(StockMarketUtility.convertToStockPriceDetailsDtoList(
						stockRepository.findStockIndexByCompanyCode(companyCode, startDate, endDate)));
				stockPriceIndexDTO.setMaxStockPrice(getMaxStockPrice(companyCode, startDate, endDate));
				stockPriceIndexDTO.setAvgStockPrice(getAvgStockPrice(companyCode, startDate, endDate));
				stockPriceIndexDTO.setMinStockPrice(getMinStockPrice(companyCode, startDate, endDate));
			} else {
				throw new StockNotFoundException("endDate is before startDate");
			}
		}
		return stockPriceIndexDTO;
	}

	// ----------------------StockPriceDetails
	// validation----------------------------------

	// StockPriceDetails
	private boolean isValidStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) throws InvalidStockException {
		boolean isValid = false;
		if (stockPriceDetailsDTO != null) {
			if (isValidcompanyCode(stockPriceDetailsDTO.getCompanyCode())
					&& isValidcurrentStockPrice(stockPriceDetailsDTO.getCurrentStockPrice())
					&& isValidLocalDate(stockPriceDetailsDTO.getStockPriceDate()) && isValidLocalTime(
							stockPriceDetailsDTO.getStockPriceDate(), stockPriceDetailsDTO.getStockPriceTime())) {
				isValid = true;
			}
		} else {
			isValid = false;
			throw new InvalidStockException("StockExchangeDetails are invalid");
		}
		return isValid;
	}

	private boolean isValidLocalTime(LocalDate stockPriceDate, LocalTime stockPriceTime) throws InvalidStockException {
		boolean isValid = false;

		if (stockPriceTime != null) {
			if (isValidLocalDate(stockPriceDate) && stockPriceDate.isBefore(LocalDate.now())) {
				isValid = true;
			} else if (isValidLocalDate(stockPriceDate) && stockPriceDate == LocalDate.now()) {
				if (stockPriceTime.isBefore(LocalTime.now())) {
					isValid = true;
				} else {
					isValid = false;
					throw new InvalidStockException("StockPriceTime never exceed current Time of today");
				}
			} else {
				isValid = false;
				throw new InvalidStockException("StockPriceTime never exceed current DateTime");
			}
		} else {
			isValid = false;
			throw new InvalidStockException(
					"StockPriceTime cannot be null, must be of HH:MM:SS format and never exceed current DateTime");
		}
		return isValid;
	}

	private boolean isValidLocalDate(LocalDate stockPriceDate) throws InvalidStockException {
		boolean isValid = false;

		if (stockPriceDate != null && !(stockPriceDate.isAfter(LocalDate.now()))) {
			isValid = true;
		} else {
			isValid = false;
			throw new InvalidStockException(
					"StockPriceDate cannot be null, must be of YYYY/MM/DD format and never exceed current date");
		}
		return isValid;
	}

	private boolean isValidcurrentStockPrice(Double currentStockPrice) throws InvalidStockException {
		boolean isValid = false;
		if (BigDecimal.valueOf(currentStockPrice).precision() == 10
				&& BigDecimal.valueOf(currentStockPrice).scale() == 2) {
			isValid = true;
		} else {
			isValid = false;
			throw new InvalidStockException(
					"CurrentStockPrice provided should be not null and must be precision 10 and scale 2.");
		}
		return isValid;
	}

	private boolean isValidcompanyCode(Long companyCode) throws InvalidStockException {
		boolean isValid = false;
		if (companyRepository.existsById(companyCode)) {
			isValid = true;
		} else {
			isValid = false;
			throw new InvalidStockException("companyCode provided does not exist");
		}
		return isValid;
	}
}
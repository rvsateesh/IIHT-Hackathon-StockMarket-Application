package com.iiht.StockMarket.services;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.exception.CompanyNotFoundException;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService {

	@Autowired
	private CompanyInfoRepository repository;

	@Autowired
	private StockPriceRepository stockRepo;

	public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) throws InvalidCompanyException {
		if (isCompanyDetailsValid(companyDetailsDTO)) {
			repository.saveAndFlush(StockMarketUtility.convertToCompanyDetails(companyDetailsDTO));
			return companyDetailsDTO;
		} else {
			throw new InvalidCompanyException("Company detials are not valid");
		}
	};

	// ----------------------------------------------------------------------------
	public CompanyDetailsDTO deleteCompany(Long companyCode) {
		CompanyDetailsDTO deletedDetails = new CompanyDetailsDTO();
		if (isCompanyCodeExists(companyCode)) {
			deletedDetails = StockMarketUtility
					.convertToCompanyDetailsDTO(repository.findCompanyDetailsById(companyCode));
			stockRepo.deleteStockByCompanyCode(companyCode);
			repository.deleteByCompanyCode(companyCode);
		} else {
			deletedDetails = null;
		}

		return deletedDetails;
	};

	// ----------------------------------------------------------------------------
	public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
		if (isCompanyCodeExists(companyCode)) {
			return StockMarketUtility.convertToCompanyDetailsDTO(repository.findCompanyDetailsById(companyCode));
		} else {
			return null;
		}
	};

	// ----------------------------------------------------------------------------
	public List<CompanyDetailsDTO> getAllCompanies() {
		return StockMarketUtility.convertToCompanyDetailsDtoList(repository.findAll());
	}

	// --------------------Company Details
	// Validation----------------------------------------

	// CompanyDetailsEntity validation
	public boolean isCompanyDetailsValid(CompanyDetailsDTO companyDetailsDTO) throws InvalidCompanyException {
		boolean isValid = false;
		if (companyDetailsDTO != null) {
			if (isStockExchangeValid(companyDetailsDTO.getStockExchange())
					&& isCompanyNameValid(companyDetailsDTO.getCompanyName())
					&& isCompanyCEOValid(companyDetailsDTO.getCompanyCEO())
					&& isTurnoverValid(companyDetailsDTO.getTurnover())
					&& isBoardOfDirectorsValid(companyDetailsDTO.getBoardOfDirectors())
					&& isCompanyProfileValid(companyDetailsDTO.getCompanyProfile())) {
				if ((repository.findCompanyDetailsById(companyDetailsDTO.getCompanyCode()) == null)) {
					isValid = true;
				} else {
					isValid = false;
					throw new InvalidCompanyException("Company ID already exists");
				}

			} else {
				isValid = false;
				throw new InvalidCompanyException("Company details provided are incorrect");
			}
		} else {
			isValid = false;
			throw new InvalidCompanyException("Company details cannot be null");
		}
		return isValid;
	}

	// CompanyCode validation
	public boolean isCompanyCodeExists(Long companyCode) throws CompanyNotFoundException {
		boolean isExists = false;
		if (companyCode > 0 && repository.findCompanyDetailsById(companyCode) != null) {
			isExists = true;
		} else {
			throw new CompanyNotFoundException("Company ID does not exist");
		}
		return isExists;
	}

	// stockExchange validation
	public boolean isStockExchangeValid(String stockExchange) throws InvalidCompanyException {
		boolean isValid = false;
		if (stockExchange != null && stockExchange.length() > 2 && stockExchange.length() < 101) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"StockExchange value should be not null and must be min 3 and max 100 characters.");
		}
		return isValid;
	}

	// companyName validation
	public boolean isCompanyNameValid(String companyName) throws InvalidCompanyException {
		boolean isValid = false;
		if (companyName != null && companyName.length() > 2 && companyName.length() < 101) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"CompanyName value should be not null and must be min 3 and max 100 characters.");
		}
		return isValid;
	}

	// companyCEO validation
	public boolean isCompanyCEOValid(String companyCEO) throws InvalidCompanyException {
		boolean isValid = false;
		if (companyCEO != null && companyCEO.length() > 4 && companyCEO.length() < 101) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"CompanyCEO value should be not null and must be min 5 and max 100 characters.");
		}
		return isValid;
	}

	// turnover validation
	public boolean isTurnoverValid(Double turnover) throws InvalidCompanyException {
		boolean isValid = true;
		if (BigDecimal.valueOf(turnover).precision() == 10 && BigDecimal.valueOf(turnover).scale() == 2) {
			// if(turnover > 00000000.01 && turnover <= 99999999.99 &&
			// BigDecimal.valueOf(turnover).scale() == 2) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"Turnover value should be not null and must be precision 10 and scale 2.");
		}

		return isValid;
	}

	// boardOfDirectors validation
	public boolean isBoardOfDirectorsValid(String boardOfDirectors) throws InvalidCompanyException {
		boolean isValid = false;
		if (boardOfDirectors != null && boardOfDirectors.length() > 4 && boardOfDirectors.length() < 201) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"BoardOfDirectors value should be not null and must be min 5 and max 200 characters.");
		}
		return isValid;
	}

	// companyProfile validation
	public boolean isCompanyProfileValid(String companyProfile) throws InvalidCompanyException {
		boolean isValid = false;
		if (companyProfile != null && companyProfile.length() > 4 && companyProfile.length() < 256) {
			isValid = true;
		} else {
			throw new InvalidCompanyException(
					"CompanyProfile value should be not null and must be min 5 and max 255 characters.");
		}
		return isValid;
	}

}
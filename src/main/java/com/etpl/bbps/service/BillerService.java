package com.etpl.bbps.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etpl.bbps.model.BillerInfo;
import com.etpl.bbps.model.BillerInputParams;
import com.etpl.bbps.model.dto.BillerInfoRequestDto;
import com.etpl.bbps.model.dto.BillerInfoResponseDTO;
import com.etpl.bbps.repository.BillerInfoRepository;
import com.etpl.bbps.repository.BillerInputParamsRepository;

@Service
public class BillerService {
	  @Autowired
	  private BillerInfoRepository billerInfoRepository;

	   @Autowired
	   private BillerInputParamsRepository billerInputParamRepository;
	   
	   public BillerInfoResponseDTO getBillerInfo(BillerInfoRequestDto request) {
		    BillerInfoResponseDTO response = new BillerInfoResponseDTO();
		    
		    List<BillerInfo> billerInfos;
		    
		    if (request.getBillerId() == null || request.getBillerId().isEmpty()) {
		        // Fetch all billers
		        billerInfos = billerInfoRepository.findAll();
		    } else {
		        // Fetch a single biller by ID
		        Optional<BillerInfo> optionalBillerInfo = billerInfoRepository.findById(request.getBillerId());
		        if (!optionalBillerInfo.isPresent()) {
		            response.setResponseCode("001");
		            BillerInfoResponseDTO.ErrorInfo errorInfo = new BillerInfoResponseDTO.ErrorInfo();
		            BillerInfoResponseDTO.ErrorInfo.Error error = new BillerInfoResponseDTO.ErrorInfo.Error();
		            error.setErrorCode("E017");
		            error.setErrorMessage("Biller Id invalid");
		            errorInfo.setError(List.of(error));
		            response.setErrorInfo(List.of(errorInfo));
		            return response;
		        }
		        // Wrap the single item in a list
		        billerInfos = List.of(optionalBillerInfo.get());
		    }
		    
		    // Convert to DTOs
		    List<BillerInfoResponseDTO.BillerInfoDto> billerDTOs = billerInfos.stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		    
		    response.setResponseCode("000");
		    response.setBillers(billerDTOs);
		    return response;
		}

	   private  BillerInfoResponseDTO.BillerInfoDto convertToDTO(BillerInfo billerInfo){
		   BillerInfoResponseDTO.BillerInfoDto billerDto = new BillerInfoResponseDTO.BillerInfoDto();
		   billerDto.setBillerId(billerInfo.getBillerId());
		   billerDto.setBillerName(billerInfo.getBillerName());
		   billerDto.setBillerCategory(billerInfo.getBillerCategory());
		   billerDto.setBillerAdhoc(billerInfo.isBillerAdhoc());
		   billerDto.setBillerCoverage(billerInfo.getBillerCoverage());
		   billerDto.setBillerFetchRequirement(billerInfo.getBillerFetchRequirement());
		   billerDto.setBillerPaymentExactness(billerInfo.getBillerPaymentExactness());
		   billerDto.setBillerSupportBillValidation(billerInfo.getBillerSupportBillValidation());
		   billerDto.setBillerInputParams(billerInfo.getBillerInputParams().stream().map(this::convertToParamInfoDto)
				   .collect(Collectors.toList()));
		   
		   billerDto.setBillerAmountOptions(billerInfo.getBillerAmountOptions());
		   billerDto.setBillerPaymentModels(billerInfo.getBillerPaymentModels());
		   billerDto.setBillerDescription(billerInfo.getBillerDescription());
		   billerDto.setRechargeAmountInValidationRequest(billerInfo.getRechargeAmountInValidationRequest());
		    return billerDto;
		   
	   }
	   private BillerInfoResponseDTO.BillerInfoParamsDto convertToParamInfoDto(BillerInputParams paramInfo){
		   BillerInfoResponseDTO.BillerInfoParamsDto paramInfoDto = new BillerInfoResponseDTO.BillerInfoParamsDto();
		   paramInfoDto.setParamName(paramInfo.getParamName());
		   paramInfoDto.setDataType(paramInfo.getDataType());
		   paramInfoDto.setOptional(paramInfo.isOptional());
		   paramInfoDto.setMinLength(paramInfo.getMinLength());
		   paramInfoDto.setMaxLength(paramInfo.getMaxLength());
		   return paramInfoDto;
	   }
}



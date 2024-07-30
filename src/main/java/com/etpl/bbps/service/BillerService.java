package com.etpl.bbps.service;

import com.etpl.bbps.model.BillerInfo;
import com.etpl.bbps.model.BillerInputParams;
import com.etpl.bbps.model.dto.BillerInfoRequestDto;
import com.etpl.bbps.model.dto.BillerInfoResponseDTO;
import com.etpl.bbps.repository.BillerInfoRepository;
import com.etpl.bbps.repository.BillerInputParamsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillerService {

    @Autowired
    private BillerInfoRepository billerInfoRepository;

    @Autowired
    private BillerInputParamsRepository billerInputParamsRepository;

    @Value("${billavenue.accessCode}")
    private String accessCode;

    @Value("${billavenue.instituteId}")
    private String instituteId;

    @Value("${billavenue.stagingUrl}")
    private String stagingApiUrl;

    @Value("${billavenue.productionUrl}")
    private String productionApiUrl;

    private static final int MAX_BATCH_SIZE = 2000;

    public BillerInfoResponseDTO getBillerInfo(BillerInfoRequestDto request) {
        List<String> billerIds = request.getBillerIds();
        BillerInfoResponseDTO response = new BillerInfoResponseDTO();

        if (billerIds == null || billerIds.isEmpty()) {
            List<BillerInfo> allBillerInfos = billerInfoRepository.findAll();
            List<BillerInfoResponseDTO.BillerInfoDto> billerDTOs = allBillerInfos.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            response.setResponseCode("000");
            response.setBillers(billerDTOs);
        } else {
            if (billerIds.size() > MAX_BATCH_SIZE) {
                return buildErrorResponse("E020", "Exceeded maximum allowed Biller IDs (2000)");
            }

            List<BillerInfo> billerInfos = billerInfoRepository.findByIds(billerIds);
            if (billerInfos.isEmpty()) {
                return buildErrorResponse("E017", "Biller Id invalid");
            }

            List<BillerInfoResponseDTO.BillerInfoDto> billerDTOs = billerInfos.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            response.setResponseCode("000");
            response.setBillers(billerDTOs);
        }
        return response;
    }

    private BillerInfoResponseDTO buildErrorResponse(String errorCode, String errorMessage) {
        BillerInfoResponseDTO response = new BillerInfoResponseDTO();
        response.setResponseCode("001");
        BillerInfoResponseDTO.ErrorInfo errorInfo = new BillerInfoResponseDTO.ErrorInfo();
        BillerInfoResponseDTO.ErrorInfo.Error error = new BillerInfoResponseDTO.ErrorInfo.Error();
        error.setErrorCode(errorCode);
        error.setErrorMessage(errorMessage);
        errorInfo.setError(List.of(error));
        response.setErrorInfo(List.of(errorInfo));
        return response;
    }

    private BillerInfoResponseDTO.BillerInfoDto convertToDTO(BillerInfo billerInfo) {
        BillerInfoResponseDTO.BillerInfoDto billerDto = new BillerInfoResponseDTO.BillerInfoDto();
        billerDto.setBillerId(billerInfo.getBillerId());
        billerDto.setBillerName(billerInfo.getBillerName());
        billerDto.setBillerCategory(billerInfo.getBillerCategory());
        billerDto.setBillerAdhoc(billerInfo.isBillerAdhoc());
        billerDto.setBillerCoverage(billerInfo.getBillerCoverage());
        billerDto.setBillerFetchRequirement(billerInfo.getBillerFetchRequirement());
        billerDto.setBillerPaymentExactness(billerInfo.getBillerPaymentExactness());
        billerDto.setBillerSupportBillValidation(billerInfo.getBillerSupportBillValidation());
        billerDto.setBillerInputParams(billerInfo.getBillerInputParams().stream()
                .map(this::convertToParamInfoDto)
                .collect(Collectors.toList()));
        billerDto.setBillerAmountOptions(billerInfo.getBillerAmountOptions());
        billerDto.setBillerPaymentModels(billerInfo.getBillerPaymentModes());
        billerDto.setBillerDescription(billerInfo.getBillerDescription());
        billerDto.setRechargeAmountInValidationRequest(billerInfo.getRechargeAmountInValidationRequest());
        return billerDto;
    }

    private BillerInfoResponseDTO.BillerInfoParamsDto convertToParamInfoDto(BillerInputParams paramInfo) {
        BillerInfoResponseDTO.BillerInfoParamsDto paramInfoDto = new BillerInfoResponseDTO.BillerInfoParamsDto();
        paramInfoDto.setParamName(paramInfo.getParamName());
        paramInfoDto.setDataType(paramInfo.getDataType());
        paramInfoDto.setOptional(paramInfo.isOptional());
        paramInfoDto.setMinLength(paramInfo.getMinLength());
        paramInfoDto.setMaxLength(paramInfo.getMaxLength());
        return paramInfoDto;
    }

    public BillerInfoResponseDTO fetchBillerInfo(BillerInfoRequestDto request) {
        // Create XML request
        String xmlRequest = createXmlRequest(request.getBillerIds());

        // Encrypt XML request
        String encryptedRequest = com.etpl.bbps.common.AESUtil.encrypt(xmlRequest);

        // Call BillAvenue API
        String xmlResponse = callBillerApi(encryptedRequest);

        // Process API response
        return processResponse(xmlResponse);
    }

    private String createXmlRequest(List<String> billerIds) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BillerInfoRequestDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            BillerInfoRequestDto requestDto = new BillerInfoRequestDto();
            requestDto.setBillerIds(billerIds);

            StringWriter sw = new StringWriter();
            // creates a marshaller for converting the BillerInfoRequestDto object to XML.
            marshaller.marshal(requestDto, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Error creating XML request", e);
        }
    }

    private String callBillerApi(String encryptedRequestXml) {
        String url = stagingApiUrl; // or productionApiUrl depending on environment

        String requestId = com.etpl.bbps.common.RequestIdGenerator.generateRequestId(35);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        String apiRequestXml = createApiRequest(encryptedRequestXml);
        //Creates the API request XML and wraps it in an HttpEntity with the headers.
        HttpEntity<String> requestEntity = new HttpEntity<>(apiRequestXml, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + "?accessCode=" + accessCode + "&requestId=" + requestId + "&ver=1.0&instituteId=" + instituteId,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return responseEntity.getBody();
    }

    private String createApiRequest(String encryptedRequestXml) {
        return "<request>" +
                "<accessCode>" + accessCode + "</accessCode>" +
                "<encRequest>" + encryptedRequestXml + "</encRequest>" +
                "<requestId>" + com.etpl.bbps.common.RequestIdGenerator.generateRequestId(35) + "</requestId>" +
                "<instituteId>" + instituteId + "</instituteId>" +
                "<ver>1.0</ver>" +
                "</request>";
    }

    private BillerInfoResponseDTO processResponse(String xmlResponse) {
        try {
            String decryptedResponse = com.etpl.bbps.common.AESUtil.decrypt(xmlResponse);
            return convertToBillerInfoResponseDto(decryptedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("E999", "Error processing response");
        }
    }

    private BillerInfoResponseDTO convertToBillerInfoResponseDto(String xmlResponse) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BillerInfoResponseDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlResponse);
            //convert the decrypted response to the xml
            return (BillerInfoResponseDTO) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
            return buildErrorResponse("E999", "Error converting response");
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchBillerInfoFromAPIAndUpdateDatabase() {
        BillerInfoRequestDto requestDto = new BillerInfoRequestDto();
        List<String> allBillerIds = billerInfoRepository.findAll().stream()
                .map(BillerInfo::getBillerId)
                .collect(Collectors.toList());
        requestDto.setBillerIds(allBillerIds);

        BillerInfoResponseDTO response = fetchBillerInfo(requestDto);
        //response comes in xml form so we convert it in billerInfo entity for saving it to the database 

        if ("000".equals(response.getResponseCode())) {
            List<BillerInfo> billerInfos = response.getBillers().stream()
                    .map(this::convertToBillerInfo)
                    .collect(Collectors.toList());
            
            // Clear existing data
            billerInfoRepository.deleteAll();

            // Save new data
            billerInfoRepository.saveAll(billerInfos);
        } else {
            // Log error
            System.err.println("Failed to fetch biller info: " + response.getResponseCode());
        }
    }

    private BillerInfo convertToBillerInfo(BillerInfoResponseDTO.BillerInfoDto dto) {
        BillerInfo billerInfo = new BillerInfo();
        billerInfo.setBillerId(dto.getBillerId());
        billerInfo.setBillerName(dto.getBillerName());
        billerInfo.setBillerCategory(dto.getBillerCategory());
        billerInfo.setBillerAdhoc(dto.isBillerAdhoc());
        billerInfo.setBillerCoverage(dto.getBillerCoverage());
        billerInfo.setBillerFetchRequirement(dto.getBillerFetchRequirement());
        billerInfo.setBillerPaymentExactness(dto.getBillerPaymentExactness());
        billerInfo.setBillerSupportBillValidation(dto.getBillerSupportBillValidation());
        billerInfo.setBillerInputParams(dto.getBillerInputParams().stream()
                .map(this::convertToBillerInputParams)
                .collect(Collectors.toList()));
        billerInfo.setBillerAmountOptions(dto.getBillerAmountOptions());
        billerInfo.setBillerPaymentModels(dto.getBillerPaymentModes());
        billerInfo.setBillerDescription(dto.getBillerDescription());
        billerInfo.setRechargeAmountInValidationRequest(dto.getRechargeAmountInValidationRequest());
        return billerInfo;
    }

    private BillerInputParams convertToBillerInputParams(BillerInfoResponseDTO.BillerInfoParamsDto dto) {
        BillerInputParams param = new BillerInputParams();
        param.setParamName(dto.getParamName());
        param.setDataType(dto.getDataType());
        param.setOptional(dto.isOptional());
        param.setMinLength(dto.getMinLength());
        param.setMaxLength(dto.getMaxLength());
        return param;
    }
}

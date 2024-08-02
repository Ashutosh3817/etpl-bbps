package com.etpl.bbps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.etpl.bbps.common.AESUtil;
import com.etpl.bbps.common.RequestIdGenerator;
import com.etpl.bbps.model.dto.RechargePlanRequestDTO;
import com.etpl.bbps.model.dto.RechargePlanResponseDTO;
import com.etpl.bbps.repository.RechargePlanDetailsRepository;
import com.etpl.bbps.repository.RechargePlanRepository;

@Service
public class RechargePlanService {
	@Autowired
	private RechargePlanRepository rechargePlanRepository;
	
	@Autowired
	private RechargePlanDetailsRepository rechargePlanDetailsRepository;
	
	@Value("${billavenue.accessCode}")
	private String accessCode;
	
	@Value("${billavenue.instituteId}")
	private String instituteId;
	
    @Value("${billavenue.stagingUrl}")
	private String stagingApiUrl;

	@Value("${billavenue.productionUrl}")
	private String productionApiUrl;
	
	@Value("${billavenue.version}")
	private String version;
	
	//private String REQUEST_ID = RequestIdGenerator.generateRequestId(35); 
	//first we convert the requestDto to the xml and then encrypt it set it to the post parameter and then we
	//receive the encrption xml response and then we decrypt the response and set it the rechargeResponseDto class
	public RechargePlanResponseDTO getRechargePlans(RechargePlanRequestDTO requestDto) {
		//convert the requestDto to the xml
		String xmlRequest = convertToXml(requestDto);
		
		//Encrypt the xml request
		String encryptedRequest = AESUtil.encrypt(xmlRequest);
		
		//Generate the requestId
		String requestId = RequestIdGenerator.generateRequestId(35);
		
		//Prepare the RequestPayload 
		HttpHeaders headers = new HttpHeaders();
		
		headers.setC
	}
	
}

/*
    public RechargePlanResponseDto getRechargePlans(RechargePlanRequestDto requestDto) {
        // Convert request DTO to XML
        String xmlRequest = convertToXml(requestDto);

        // Encrypt the XML request
        String encryptedRequest = AESUtil.encrypt(xmlRequest);

        // Generate a unique request ID
        String requestId = generateRequestId();

        // Prepare the request payload
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        String apiRequestXml = createApiRequest(encryptedRequest, requestId);
        HttpEntity<String> requestEntity = new HttpEntity<>(apiRequestXml, headers);

        // Call the API and get the encrypted response
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl + "?accessCode=" + accessCode + "&requestId=" + requestId + "&ver=1.0&instituteId=" + instituteId,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String encryptedResponse = responseEntity.getBody();

        // Decrypt the response
        String responseXml = AESUtil.decrypt(encryptedResponse);

        // Convert the response XML to DTO
        return convertFromXml(responseXml, RechargePlanResponseDto.class);
    }

    private String convertToXml(Object object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.marshal(object, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Error converting object to XML", e);
        }
    }

    private <T> T convertFromXml(String xml, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException("Error converting XML to object", e);
        }
    }

    private String createApiRequest(String encryptedRequestXml, String requestId) {
        return "<request>" +
                "<accessCode>" + accessCode + "</accessCode>" +
                "<encRequest>" + encryptedRequestXml + "</encRequest>" +
                "<requestId>" + requestId + "</requestId>" +
                "<instituteId>" + instituteId + "</instituteId>" +
                "<ver>1.0</ver>" +
                "</request>";
    }

    private String generateRequestId() {
        return java.util.UUID.randomUUID().toString();
    }
}
*/
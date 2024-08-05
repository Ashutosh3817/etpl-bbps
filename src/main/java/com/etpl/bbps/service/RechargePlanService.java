package com.etpl.bbps.service;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
		
		headers.setContentType(MediaType.APPLICATION_XML);
		
		String apiRequestXml = createApiRequest(encryptedRequest,requestId);
		
		HttpEntity<String> requestEntity = new HttpEntity<>(apiRequestXml,headers);
		//call the api and get the encrypted response
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				stagingApiUrl + "?accessCode="+accessCode+"&requestId="+requestId+"&ver=1.0&instituteId="+instituteId,
				HttpMethod.POST,
				requestEntity,
				String.class
				);
		//we get the encrypted response in the string format
		
		String encryptedResponse = responseEntity.getBody();
		
		//Decrypt the response
		String responseXml = AESUtil.decrypt(encryptedResponse
				);
		
		//convert the xml to dto
		return convertToDto(responseXml,RechargePlanResponseDTO.class);
	}
	
	private String convertToXml(RechargePlanRequestDTO requestDto){ 
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RechargePlanRequestDTO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(requestDto,sw);
			return sw.toString();
			//xml data returned in the string format
		}
		catch(JAXBException e) {
			throw new RuntimeException("Error in converting the object to xml",e);
		}
	}
	
	private RechargePlanResponseDTO convertToDto(String xml,Class<RechargePlanResponseDTO> class1) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RechargePlanResponseDTO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return (RechargePlanResponseDTO) unmarshaller.unmarshal(reader);
		}
		catch(JAXBException e) {
			throw new RuntimeException("Error converting XML to ResponseDto",e);
		}
	}

	private String createApiRequest(String encryptedRequestXml,String requestId) {
		return "<request>" +
                "<accessCode>" + accessCode + "</accessCode>" +
                "<encRequest>" + encryptedRequestXml + "</encRequest>" +
                "<requestId>" + requestId + "</requestId>" +
                "<instituteId>" + instituteId + "</instituteId>" +
                "<ver>1.0</ver>" +
                "</request>";
	}
}


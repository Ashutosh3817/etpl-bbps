package com.etpl.bbps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.etpl.bbps.common.RequestIdGenerator;
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
	
	private String REQUEST_ID = RequestIdGenerator.generateRequestId(35); 
	
}

/*
@Service
public class RechargePlanService {

    
    private static final String API_URL = "https://api.example.com/rechargePlans";
    private static final String ACCESS_CODE = "your_access_code";
    private static final String INSTITUTE_ID = "your_institute_id";
    private static final String VERSION = "1.0";

    public RechargePlanResponseDto getRechargePlans(RechargePlanRequestDto requestDto) {
        // Convert request DTO to XML
        String requestXml = convertToXml(requestDto);

        // Call the API
        String encryptedResponse = callApi(requestXml);

        // Decrypt and parse the response
        String responseXml = decryptResponse(encryptedResponse);
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

    private String callApi(String requestXml) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?accessCode=" + ACCESS_CODE + "&requestId=" + generateRequestId() + "&ver=" + VERSION + "&instituteId=" + INSTITUTE_ID;
        return restTemplate.postForObject(url, requestXml, String.class);
    }

    private String decryptResponse(String encryptedResponse) {
        // Implement decryption logic here
        return encryptedResponse; // For now, returning as-is
    }

    private String generateRequestId() {
        // Implement request ID generation logic here
        return "some_generated_request_id";
    }

    public void saveRechargePlans(RechargePlanResponseDto response) {
        if ("000".equals(response.getResponseCode())) {
            RechargePlan rechargePlan = new RechargePlan();
            List<RechargePlanResponseDto.RechargePlan.RechargePlanDetails> details = response.getRechargePlan().getRechargePlansDetails();
            for (RechargePlanResponseDto.RechargePlan.RechargePlanDetails detail : details) {
                RechargePlanDetails planDetail = new RechargePlanDetails();
                planDetail.setRechargePlan(rechargePlan);
                planDetail.setAmount(detail.getAmount());
                planDetail.setDescription(detail.getDescription());
                planDetail.setLocationName(detail.getLocationName());
                planDetail.setPlanName(detail.getPlanName());
                planDetail.setServiceProviderName(detail.getServiceProviderName());
                planDetail.setTalktime(detail.getTalktime());
                planDetail.setValidity(detail.getValidity());
                rechargePlan.getRechargePlanDetails().add(planDetail);
            }
            rechargePlanRepository.save(rechargePlan);
        } else {
            System.out.println("Error: " + response.getResponseReason());
        }
    }
}
*/
package com.etpl.bbps.service;

import java.io.StringReader;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etpl.bbps.model.BillAvenueBillerInfo;
import com.etpl.bbps.model.BillAvenueBillerInfoResponse;
import com.etpl.bbps.model.BillAvenueBillerInputParams;
import com.etpl.bbps.model.BillAvenueBillerPaymentChannel;
import com.etpl.bbps.repository.BillAvenueBillerInfoRepository;
import com.etpl.bbps.repository.BillAvenueBillerInputParamsRepository;
import com.etpl.bbps.repository.BillAvenueBillerPaymentChannelRepository;

@Service
public class BillAvenueBillerInfoService {

	 @Autowired
	    private BillAvenueBillerInfoRepository billAvenueBillerInfoRepository;
	    
	    @Autowired
	    private BillAvenueBillerInputParamsRepository billAvenueBillerInputParamsRepository;
	    
	    @Autowired
	    private BillAvenueBillerPaymentChannelRepository billAvenuePaymentChannelRepository;
	   
	    @Transactional
	    public void saveBillerInfo(String xmlData) throws JAXBException {
	        try {
	        	xmlData = removeBOM(xmlData);
	        	xmlData = cleanXml(xmlData);
	            // log XML data 
	            System.out.println("Processed XML Data: " + xmlData);

	            // Setup JAXB context and unmarshaller
	            JAXBContext context = JAXBContext.newInstance(BillAvenueBillerInfoResponse.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();

	            // Unmarshal the XML to the response class
	            BillAvenueBillerInfoResponse response = (BillAvenueBillerInfoResponse) unmarshaller.unmarshal(new StringReader(xmlData));

	            // Save to repository if response code is 000
	            if ("000".equals(response.getResponseCode())) {
	                List<BillAvenueBillerInfo> billerInfos =  response.getBillersInfo(); // Get list of billers

	                if (billerInfos != null) {
	                    for (BillAvenueBillerInfo billerInfo : billerInfos) {
	                        billAvenueBillerInfoRepository.save(billerInfo);

	                        List<BillAvenueBillerInputParams> inputParams = billerInfo.getBillerInputParams();
	                        if (inputParams != null) {
	                            inputParams.forEach(billerInputParam -> billAvenueBillerInputParamsRepository.save(billerInputParam));
	                        }

	                        List<BillAvenueBillerPaymentChannel> paymentChannels = billerInfo.getBillerPaymentChannels();
	                        if (paymentChannels != null) {
	                            paymentChannels.forEach(paymentChannelInfo -> billAvenuePaymentChannelRepository.save(paymentChannelInfo));
	                        }
	                    }
	                } else {
	                    System.out.println("No biller information found in response.");
	                }
	            } else {
	                System.out.println("Response code is not 000. Data not saved.");
	            }
	        } catch (JAXBException e) {
	            System.err.println("JAXB Exception occurred: " + e.getMessage());
	            e.printStackTrace(); 
	            throw e;
	        } catch (Exception e) {
	            System.err.println("An unexpected error occurred: " + e.getMessage());
	            e.printStackTrace(); 
	            throw e;
	        }
	    }

	    private String removeBOM(String xml) {
	        // Remove byte-order-mark  is a special marker added at the very beginning of an Unicode file encoded in UTF-8, UTF-16 or UTF-32. if present
	        if (xml != null && xml.startsWith("\uFEFF")) {
	            return xml.substring(1);
	        }
	        return xml;
	    }

	    private String cleanXml(String xml) {
	        // Remove any leading whitespace or characters before the XML declaration
	        if (xml != null) {
	            xml = xml.trim(); // Remove leading and trailing whitespace
	            if (xml.startsWith("<?xml")) {
	                return xml;
	            }
	        }
	        return xml;
	    }
	}

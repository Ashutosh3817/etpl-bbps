package com.etpl.bbps.service;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etpl.bbps.model.BillAvenueBillerInfo;
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
    private BillAvenueBillerInputParamsRepository billAvenueBillerInputParamsRepository; // Correct repository
    
    @Autowired
    private BillAvenueBillerPaymentChannelRepository billAvenuePaymentChannelRepository;
    

    public void saveBillerInfo(String xmlData) throws JAXBException {
        try {
            xmlData = removeBOM(xmlData);  // Remove BOM if present
            xmlData = cleanXml(xmlData);   // Clean XML string

            System.out.println("Processed XML Data: " + xmlData);

            // Unmarshal XML data
            JAXBContext context = JAXBContext.newInstance(BillAvenueBillerInfo.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlData);

            BillAvenueBillerInfo billerInfo = (BillAvenueBillerInfo) unmarshaller.unmarshal(reader);

            // Save to repository if response code is 000
            if ("000".equals(billerInfo.getResponseCode())) {
                billAvenueBillerInfoRepository.save(billerInfo);

                List<BillAvenueBillerInputParams> inputParams = billerInfo.getBillerInputParams();
                if (inputParams != null) {
                    inputParams.forEach(billAvenueBillerInputParamsRepository::save);
                }

                List<BillAvenueBillerPaymentChannel> paymentChannels = billerInfo.getBillerPaymentChannels();
                if (paymentChannels != null) {
                    paymentChannels.forEach(billAvenuePaymentChannelRepository::save);
                }
            } else {
                System.out.println("Response code is not 000. Data not saved.");
            }
        } catch (JAXBException e) {
            System.err.println("JAXB Exception occurred: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more details
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more details
            throw e;
        }
    }
    private String removeBOM(String xml) {
        // Remove BOM if present
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
            // Optionally, if there's an issue with prolog, you can look for other issues here
        }
        return xml;
    }

}
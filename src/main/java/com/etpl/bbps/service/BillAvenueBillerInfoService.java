package com.etpl.bbps.service;

import java.io.StringReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	    @PersistenceContext
	    private EntityManager entityManager;
	   
	    @Autowired
	    private BillAvenueBillerInfoRepository billAvenueBillerInfoRepository;
	    
	    @Autowired
	    private BillAvenueBillerInputParamsRepository billAvenueBillerInputParamsRepository;
	    
	    @Autowired
	    private BillAvenueBillerPaymentChannelRepository billAvenuePaymentChannelRepository;
	   
	    @Transactional
	    public void saveBillerInfo(String xmlData) throws JAXBException {
	        try {
	           
	           // xmlData = cleanXml(xmlData);

	            System.out.println("Processed XML Data: " + xmlData);

	            JAXBContext context = JAXBContext.newInstance(BillAvenueBillerInfoResponse.class);
	            Unmarshaller unmarshaller = context.createUnmarshaller();

	            // Unmarshal the XML to the response class
	            BillAvenueBillerInfoResponse response = (BillAvenueBillerInfoResponse) unmarshaller.unmarshal(new StringReader(xmlData));

	            // Check if response code is "000"
	            if ("000".equals(response.getResponseCode())) {
	                List<BillAvenueBillerInfo> billerInfos = response.getBillersInfo(); // Get list of billers

	                if (billerInfos != null) {
	                    // Clear existing data
	                	clearAndResetData();
	                    // Save new data
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
	            System.out.println("JAXB Exception occurred: " + e.getMessage());
	            e.printStackTrace();
	            throw e;
	        } catch (Exception e) {
	            System.out.println("An unexpected error occurred: " + e.getMessage());
	            e.printStackTrace();
	            throw e;
	        }
	    }

	    @Transactional
	    public void clearAndResetData() {
	        try {
	            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

	            truncateAndResetAutoIncrement("TL_biller_Input_Param");
	            truncateAndResetAutoIncrement("TL_biller_Payment_Channel");
	            truncateAndResetAutoIncrement("TT_bill_Avenue_Biller");

	            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

	            System.out.println("Cleared existing data and reset auto-increment.");
	        } catch (Exception e) {
	            System.err.println("Exception during data clearing and resetting: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    private void truncateAndResetAutoIncrement(String tableName) {
	        try {
	           
	            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();

	            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1").executeUpdate();

	            //System.out.println("Truncated and reset auto-increment for table: " + tableName);
	        } catch (Exception e) {
	           // System.err.println("Exception during truncating table " + tableName + ": " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}

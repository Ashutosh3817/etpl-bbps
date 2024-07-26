package com.etpl.bbps.schedular;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.etpl.bbps.common.CommonUtils;
import com.etpl.bbps.common.EtplConstant;
import com.etpl.bbps.common.MailUtil;
import com.etpl.bbps.common.ResponseBean;
import com.etpl.bbps.constant.ResponseModel;
import com.etpl.bbps.model.AllowedPaymentMethod;
import com.etpl.bbps.model.Authenticators;
import com.etpl.bbps.model.BbpsTransaction;
import com.etpl.bbps.model.BillerModel;
import com.etpl.bbps.model.PaymentChannels;
import com.etpl.bbps.repository.BbpsTransactionRepository;
import com.etpl.bbps.service.BbpsService;
import com.etpl.bbps.service.BbpsTransactionService;
import com.etpl.bbps.service.ComplaintService;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class Schedular {

	@Autowired
	BbpsService bbpsService;

	@Autowired
	ComplaintService compService;

	@Autowired
	BbpsTransactionService trxService;

	@Value("${CONTENT_TYPE}")
	private String contentType;

	@Value("${ACCEPT}")
	private String accept;

	@Value("${CLIENT_ID}")
	private String clientId;

	@Value("${OU_ID}")
	private String ouId;

	@Value("${INIT_CHANNEL}")
	private String initChannel;

	@Value("${AGENT_ID}")
	private String agentId;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SOURCE_ID}")
	private String sourceId;

	@Value("${GENERATE_BILLERS_FILE_URL}")
	private String generateBillersFileUrl;

	@Value("${DOWNLOAD_BILLERS_FILE_URL}")
	private String downloadBillersFileUrl;

	@Value("${RETRIEVE_BILL_URL}")
	private String retrieveBillUrl;

	@Value("${VALIDATE_PAYMENT_URL}")
	private String validatePaymentUrl;

	@Value("${MAKE_PAYMENT_URL}")
	private String makePaymentUrl;

	@Value("${GET_ALL_OPERATOR_LIST_URL}")
	private String getAllOperatorSeriesUrl;

	@Value("${GET_ALL_RECHARGE_PLAN_LIST_URL}")
	private String getAllRechargePlanUrl;

	@Value("${GET_RECHARGE_PLAN_USING_BILLERID_URL}")
	private String getRechargePlanUrl;

	@Value("${UPDATE_MAIN_WALLET_BALANCE_URL}")
	private String updateMainBalanceUrl;

	@Value("${RAISE_COMPLAINT_URL}")
	private String raiseComplaintUrl;

	@Value("${RETRIEVE_COMPLAINT_URL}")
	private String retrieveComplaintUrl;
	
	@Value("${CHECK_BILLER_AVAILABILITY_URL}")
	private String checkBillerAvailabilityUrl;

	@Value("${SMS_URL}")
	private String smsUrl;

	@Value("${SMS_WORKING_KEY}")
	private String smsWorkingKey;

	@Value("${VALIDATE_TOKEN}")
	private String tokenurl;
	
	@Value("${DMS_MDM_UPLOAD_URL}")
	private String dmsUploadUrl;
	
	@Value("${DMS_PRESIGNED_URL}")
	private String dmsPresignedUrl;
	
	@Value("${RECON_MAILER_TO_LIST}")
	private String getReconMailerToList;
	
	@Value("${RECON_MAILER_CC_LIST}")
	private String getReconMailerCcList;
	
	@Value("${GET_PAYMENT_STATUS_URL}")
	private String getPaymentStatusUrl;
	
	@Autowired
    private BbpsTransactionRepository trxRepo;
	
	/*
	 * @Autowired TransWalletService trxService;
	 * 
	 * @Autowired MainWalletService mainWalletService;
	 */

	/*@Scheduled(cron = "0 0/30 * * * ?")
	public void updatePendingTransaction() throws Exception {

		
	}*/
	
	
	//@Scheduled(cron = "0 00 18 * * THU")
	//@Scheduled(cron = "0 0 03 * * ?")//PROD
	public void sendReconFile() throws Exception {

		System.out.println("/*****************BBPS Recon file Sending Mail Start*****************/");
        List<BbpsTransaction> list = trxRepo.getReconFileList();
        if(null!=list && list.size()>0)
        {
        System.out.println("List size is::::::::::::::::: "+list.size());
        MailUtil sm=new MailUtil();
        List<String> toList = new ArrayList<>(Arrays.asList(getReconMailerToList.split(",")));
        List<String> ccList = new ArrayList<>(Arrays.asList(getReconMailerCcList.split(","))); 
        File file = new File("recon.txt");
		FileWriter writer  = new FileWriter(file);
		for(BbpsTransaction trx:list)
		{
		System.out.println(sourceId+EtplConstant.MSG_SEPERATOR+trx.getCustomerId()+EtplConstant.MSG_SEPERATOR+trx.getBillerId()+EtplConstant.MSG_SEPERATOR+trx.getPaymentId()
		+EtplConstant.MSG_SEPERATOR+trx.getSourceRefNo()+EtplConstant.MSG_SEPERATOR+trx.getPaymentAmount()+EtplConstant.MSG_SEPERATOR+CommonUtils.getDate(trx.getTxnDateTime())+"\n");
		writer.write(sourceId+EtplConstant.MSG_SEPERATOR+trx.getCustomerId()+EtplConstant.MSG_SEPERATOR+trx.getBillerId()+EtplConstant.MSG_SEPERATOR+trx.getPaymentId()
		+EtplConstant.MSG_SEPERATOR+trx.getSourceRefNo()+EtplConstant.MSG_SEPERATOR+trx.getPaymentAmount()+EtplConstant.MSG_SEPERATOR+CommonUtils.getDate(trx.getTxnDateTime())+"\n");
		}
		writer.flush();
		writer.close();
        sm.sendReconMail(toList, ccList,file);
        file.delete();
        }
        System.out.println("/*****************BBPS Recon file Sending Mail End***********************/");
		
	}
	
	
	
	// BBPS Scheduler for pending transactions
	
	
		//@Scheduled(cron = "0 */2 * * *")
		@Scheduled(cron = "0 0/300 * * * ?")//PROD
		public void updatePendingTransactins() throws Exception {

			System.out.println("/*****************BBPS Scheduler for pending Transaction Start*****************/");
	        List<BbpsTransaction> list = trxRepo.getPendingTransactionList();
	        if(null!=list && list.size()>0)
	        {
	        	
	        	System.out.println("pending list size is >>>>"+list.size());
	        	for(BbpsTransaction trans :list) {
	        		try
		        	{
	        		HttpHeaders headers = new HttpHeaders();
				headers.add("Accept", accept);
				headers.add("Content-Type", contentType);
				String bdTraceId = CommonUtils.getBdTraceId(20);
				String bdTimeStamp = CommonUtils.getBdTimeStamp();
				headers.add("BD-Traceid", bdTraceId);
				headers.add("BD-Timestamp", bdTimeStamp);
				String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + trans.getCustomerId()
						+ "/billpay/payments" + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
						+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
				System.out.println("HMAC String is " + request);
				String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
				System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
				System.out.println("BD-Traceid is " + bdTraceId);
				System.out.println("bdTimeStamp is " + bdTimeStamp);
				headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<String> requestEntity = new HttpEntity<>(headers);
				String withSourceId = getPaymentStatusUrl.replace("<sourceid>", sourceId);
				String finalUrl = withSourceId.replace("<customerid>", trans.getCustomerId());
				ResponseEntity<JsonNode> response = restTemplate.exchange(finalUrl, HttpMethod.GET, requestEntity,
						JsonNode.class);
				// update Transaction Hisotry
				JsonNode node = response.getBody();
				BbpsTransaction model =	trxRepo.findByCustomerId(trans.getCustomerId());
				model.setBillerStatus(node.get(0).get("biller_status").asText());
				model.setPaymentStatus(node.get(0).get("payment_status").asText());
				model.setPaymentRemarks(model.getPaymentRemarks()+"(updated via SCH)");
				trxRepo.save(model);
	        	}
	        	catch(Exception e)
	        	{
	        		
	        		System.out.println("Exception generated with customer id "+trans.getCustomerId());
	        	}
	        	}
	        }
	        System.out.println("/*****************BBPS Scheduler for pending Transaction End***********************/");
			
		}
	
	
	
	
	//second, minute, hour, day of month, month, day(s) of week
	//@Scheduled(cron = "0 45 15 * * THU")
	@Scheduled(cron = "0 0 13 * * SUN")   //PROD
	public void syncBillerCategory() throws Exception {

		System.out.println("/*****************Syncing Biller Category Starts*****************/");
        
		System.out.println("\n Generating biller file");
        final String fileName = this.generateBillerFile();
        System.out.println("\n Generated file name -> " + fileName);
        
        System.out.println("\n Downloading biller file -> "+fileName);
        Thread.sleep(900000);
        String uploadFileUrl = this.downloadFile(fileName);
        System.out.println("/*****************Syncing Biller Category End***********************/");
		
	}
	
	public String generateBillerFile() throws Exception {
		System.out.println("generate-biller-file>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String requestBody = "{\"withhold_callback\":\"Y\"}";
		System.out.println("before has request body is " + requestBody);
		String hashBody = CommonUtils.hashSHA256(requestBody);
		System.out.println("after hash request body is" + hashBody);
		String request = "POST" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/DRK01/billpay/billers/file"
				+ EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String without Body is " + request);
		System.out.println("Request Body is " + hashBody);
		request = request + EtplConstant.MSG_SEPERATOR + hashBody;
		System.out.println("HMAC String with Body is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		System.out.println("request url is /hgpay/v2_1/DRK01/billpay/billers/file");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);
		generateBillersFileUrl = generateBillersFileUrl.replace("<sourceid>", sourceId);
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(generateBillersFileUrl, entity, JsonNode.class);
		//return ResponseEntity.ok(response.getBody());
		return response.getBody().get("fileid").asText();
		
	}
	
	public String downloadFile(String fileName) throws Exception {
		String uploadFileUrl=null;
		System.out.println("FileName Recieved _-> "+fileName);
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ started ::::::::::>>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/billpay/billers/file/"
				+ fileName + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		downloadBillersFileUrl = downloadBillersFileUrl.replace("<sourceid>", sourceId);
		System.out.println("Download Biller File URL -> "+downloadBillersFileUrl);
		System.out.println("FileName -> "+fileName);
		System.out.println("Headers -> "+headers);
		System.out.println("Calling Download Biller File");
		ResponseEntity<byte[]> response = restTemplate.exchange(downloadBillersFileUrl + fileName, HttpMethod.GET,
				requestEntity, byte[].class);
		System.out.println("Call Success");
	
		HttpHeaders hed = new HttpHeaders();
		hed.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> imgBody = new LinkedMultiValueMap<>();
		imgBody.add("file", new FileSystemResource(convert(response.getBody(), fileName)));
		HttpEntity<MultiValueMap<String, Object>> uploadEntiry = new HttpEntity<>(imgBody, hed);
		ResponseEntity<ResponseBean> imgResp = restTemplate.postForEntity(dmsUploadUrl, uploadEntiry,
				ResponseBean.class);
		uploadFileUrl=imgResp.getBody().getFileUrl();
		System.out.println("File Uploaded successfully in s3 bucket with url>>>>>>>>"+uploadFileUrl);
		
		//Files.write(Paths.get("HGA1F090160000011572.zip"), response.getBody());
		//Files.write(Paths.get("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip"), response.getBody());
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ end ::::::::::>>>>>>>>>>>>>>>>>>>");
		//res.setMessage("File downloaded successfully");
		
		System.out.println("Now uploading starts..........");
		uploadBillerDataInDB(response.getBody());
		System.out.println("Now uploading Ends..........");
		
		//res.setStatus("200");
		return uploadFileUrl;
	}
	
	public void uploadBillerDataInDB(byte[] zipData) throws Exception {
		System.out.println("/upload-biller-file started ::::::::::>>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		
		InputStream is = null;
		BufferedReader bfReader = null;
		System.out.println("Read Starts");
		is = new ByteArrayInputStream(zipData);
		bfReader = new BufferedReader(new InputStreamReader(is));
		System.out.println("Got file in BufferReder");
		//String temp = null;
		//while((temp = bfReader.readLine()) != null){
			//System.out.println("Temp -> "+temp);
		//}
		
		System.out.println("Read Zip Starts");
		List<ZipEntry> entries = new ArrayList<>();
		ZipInputStream zis = null;
		BufferedReader reader = null;
	    try {
	    	System.out.println("Unzipping file and getting extracted fileName");
	        zis = new ZipInputStream(new ByteArrayInputStream(zipData));

	        ZipEntry zipEntry = null;
	        while ((zipEntry = zis.getNextEntry()) != null) {
	            entries.add(zipEntry);
	            System.out.println("Name -> "+zipEntry.getName());
	            System.out.println("Size -> "+zipEntry.getSize());
	            reader = new BufferedReader(new InputStreamReader(zis, "UTF-8"));
	            
	            StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				sb.append("[");
				while (line != null) {
					sb.append(line);
					sb.append(",");
					line = reader.readLine();
				}
				sb.append("]");
				JSONArray jsonArr = new JSONArray(sb.toString());
				if(jsonArr.length()>=1)
				{
					System.out.println("Deleting Existing Data Before Uploading new Data.");
					bbpsService.deleteAll();
				}
				System.out.println("Fetched JsonArray Size -> "+jsonArr.length());
				
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					System.out.println("Got JsonObject "+i+"->"+jsonObj);
					BillerModel model = new BillerModel();
					if (jsonObj.has("objectid"))
						model.setObjectid(jsonObj.getString("objectid"));
					if (jsonObj.has("billerid"))
						model.setBillerId(jsonObj.getString("billerid"));
					if (jsonObj.has("biller_legal_name"))
						model.setBiller_legal_name(jsonObj.getString("biller_legal_name"));
					if (jsonObj.has("biller_name"))
						model.setBiller_name(jsonObj.getString("biller_name"));
					if (jsonObj.has("biller_location"))
						model.setBiller_location(jsonObj.getString("biller_location"));
					if (jsonObj.has("biller_category"))
						model.setBillerCategory(jsonObj.getString("biller_category"));
					if (jsonObj.has("biller_reg_address"))
						model.setBiller_reg_address(jsonObj.getString("biller_reg_address"));
					if (jsonObj.has("biller_reg_city"))
						model.setBiller_reg_city(jsonObj.getString("biller_reg_city"));
					if (jsonObj.has("biller_reg_pin"))
						model.setBiller_reg_pin(jsonObj.getString("biller_reg_pin"));
					if (jsonObj.has("biller_reg_state"))
						model.setBiller_reg_state(jsonObj.getString("biller_reg_state"));
					if (jsonObj.has("biller_reg_country"))
						model.setBiller_reg_country(jsonObj.getString("biller_reg_country"));
					if (jsonObj.has("biller_mode"))
						model.setBiller_mode(jsonObj.getString("biller_mode"));
					if (jsonObj.has("billerid"))
						System.out.println(jsonObj.getString("billerid"));
					List<AllowedPaymentMethod> apmList = new ArrayList();
					if (jsonObj.has("allowed_payment_methods")) {
						JSONArray paymentMethod = (JSONArray) jsonObj.get("allowed_payment_methods");
						for (int j = 0; j < paymentMethod.length(); j++) {
							AllowedPaymentMethod apmModel = new AllowedPaymentMethod();
							JSONObject arr = paymentMethod.getJSONObject(j);
							if (arr.has("payment_method")) {
								apmModel.setPayment_method(arr.getString("payment_method"));
								System.out.println(arr.getString("payment_method"));
							}
							if (arr.has("min_limit")) {
								apmModel.setMin_limit(arr.getString("min_limit"));
								System.out.println(arr.getString("min_limit"));
							}
							if (arr.has("max_limit")) {
								apmModel.setMax_limit(arr.getString("max_limit"));
								System.out.println(arr.getString("max_limit"));
							}

							if (arr.has("autopay_allowed")) {
								apmModel.setAutopay_allowed(arr.getString("autopay_allowed"));
								System.out.println(arr.getString("autopay_allowed"));
							}

							if (arr.has("paylater_allowed")) {
								apmModel.setPaylater_allowed(arr.getString("paylater_allowed"));
								System.out.println(arr.getString("paylater_allowed"));
							}
							apmList.add(apmModel);
						}
					}

					List<PaymentChannels> pcList = new ArrayList();
					if (jsonObj.has("payment_channels")) {
						JSONArray paymentChannel = (JSONArray) jsonObj.get("payment_channels");
						for (int j = 0; j < paymentChannel.length(); j++) {
							PaymentChannels pcModel = new PaymentChannels();
							JSONObject arr = paymentChannel.getJSONObject(j);

							if (arr.has("payment_channel")) {
								pcModel.setPayment_channel(arr.getString("payment_channel"));
								System.out.println(arr.getString("payment_channel"));
							}

							if (arr.has("min_limit")) {
								pcModel.setMin_limit(arr.getString("min_limit"));
								System.out.println(arr.getString("min_limit"));
							}

							if (arr.has("max_limit")) {
								pcModel.setMax_limit(arr.getString("max_limit"));
								System.out.println(arr.getString("max_limit"));
							}
							pcList.add(pcModel);
						}
					}
					if (jsonObj.has("biller_status"))
						model.setBiller_status(jsonObj.getString("biller_status"));
					if (jsonObj.has("biller_created_date"))
						model.setBiller_created_date(jsonObj.getString("biller_created_date"));
					if (jsonObj.has("biller_lastmodified_date"))
						model.setBiller_lastmodified_date(jsonObj.getString("biller_lastmodified_date"));
					System.out.println(jsonObj.getString("biller_status"));

					List<Authenticators> acList = new ArrayList();
					if (jsonObj.has("authenticators")) {
						JSONArray paymentMethod = (JSONArray) jsonObj.get("authenticators");
						for (int j = 0; j < paymentMethod.length(); j++) {
							Authenticators acModel = new Authenticators();
							JSONObject arr = paymentMethod.getJSONObject(j);
							if (arr.has("parameter_name")) {
								acModel.setParameter_name(arr.getString("parameter_name"));
								System.out.println(arr.getString("parameter_name"));
							}

							if (arr.has("data_type")) {
								acModel.setData_type(arr.getString("data_type"));
								System.out.println(arr.getString("data_type"));
							}
							if (arr.has("optional")) {
								acModel.setOptional(arr.getString("optional"));
								System.out.println(arr.getString("optional"));
							}
							if (arr.has("regex")) {
								acModel.setRegex(arr.getString("regex"));
								System.out.println(arr.getString("regex"));
							}
							if (arr.has("error_message")) {
								acModel.setError_message(arr.getString("error_message"));
								System.out.println(arr.getString("error_message"));
							}
							if (arr.has("seq")) {
								acModel.setSeq(arr.getString("seq"));
								System.out.println(arr.getString("seq"));
							}
							if (arr.has("encryption_required")) {
								acModel.setEncryption_required(arr.getString("encryption_required"));
								System.out.println(arr.getString("encryption_required"));
							}
							if (arr.has("user_input")) {
								acModel.setUser_input(arr.getString("user_input"));
								System.out.println(arr.getString("user_input"));
							}
							acList.add(acModel);
						}
					}
					model.setAllowedPaymentMethod(apmList);
					model.setPaymentChannels(pcList);
					model.setAuthenticators(acList);
					if (jsonObj.has("biller_logo"))
						model.setBiller_logo(jsonObj.getString("biller_logo"));
					if (jsonObj.has("biller_bill_copy"))
						model.setBiller_bill_copy(jsonObj.getString("biller_bill_copy"));
					if (jsonObj.has("biller_type"))
						model.setBiller_type(jsonObj.getString("biller_type"));
					if (jsonObj.has("partial_pay"))
						model.setPartial_pay(jsonObj.getString("partial_pay"));
					if (jsonObj.has("pay_after_duedate"))
						model.setPay_after_duedate(jsonObj.getString("pay_after_duedate"));
					if (jsonObj.has("online_validation"))
						model.setOnline_validation(jsonObj.getString("online_validation"));
					if (jsonObj.has("isbillerbbps"))
						model.setIsbillerbbps(jsonObj.getString("isbillerbbps"));
					if (jsonObj.has("paymentamount_validation"))
						model.setPaymentamount_validation(jsonObj.getString("paymentamount_validation"));
					if (jsonObj.has("bill_presentment"))
						model.setBill_presentment(jsonObj.getString("bill_presentment"));
					
					System.out.println("Save Code Starts");
					System.out.println("Model -> "+model);
					bbpsService.saveBiller(model);
					System.out.println("Save Code Ends");
					
					System.out.println(jsonObj.getString("biller_logo"));
				}
	            
	            /*Scanner sc = new Scanner(zis);
	            while (sc.hasNextLine())
	            {
	            	System.out.println("Print Starts...");
	            	System.out.println(sc.nextLine());
	            }*/
	            System.out.println("reading " + zipEntry.getName() + " completed");
	        }
	    } finally {
	        if (zis != null) {
	            zis.close();
	        }
	    }
	    System.out.println("Read Zip Ends");
		
		System.out.println("Read Ends");
		System.out.println("/upload-biller-file ended ::::::::::>>>>>>>>>>>>>>>>>>>");
		
	}
	
	
	public String getFileName()
	{
		String fileNameWithOutExt = null;
		try
		{
			String fileName = "/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip";
    		FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis); 
        
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {

                fileNameWithOutExt = FilenameUtils.removeExtension(ze.getName());
                System.out.println("Final File Name -> "+fileNameWithOutExt);
                
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fileNameWithOutExt;
	}
	
	/**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
	
	static File convFile = null;
	public static File convert(byte[] file, String fileName) {
		convFile = new File(fileName);
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convFile;
	}
	
	
    public String unzip(String zipFilePath, String destDirectory) {
       
    	//Check if dest directory is created or not if not created then create it
    	String extractedFileName = null;
    	try
    	{
	    	File destDir = new File(destDirectory);
	        if (!destDir.exists()) {
	            destDir.mkdir();
	        }
	        
	        //Loading zip file in ZipInputStream by zipFilePath
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	        //Getting zip entry means getting files present in zip
	        ZipEntry entry = zipIn.getNextEntry();
	        
	        // iterates over entries in the zip file
	        while (entry != null) {
	            //generating file path where we will extract zip entry
	        	String filePath = destDirectory + File.separator + entry.getName();
	        	System.out.println("FileName -> "+entry.getName());
	        	String fileNameWithOutExt = FilenameUtils.removeExtension(entry.getName());
	        	System.out.println("FileName Without Extension -> "+fileNameWithOutExt);
	            
	        	extractedFileName =  FilenameUtils.removeExtension(entry.getName());
	        	
	        	if (!entry.isDirectory()) {
	                // if the entry is a file, extracts it
	                extractFile(zipIn, filePath);
	            } else {
	                // if the entry is a directory, make the directory
	                File dir = new File(filePath);
	                dir.mkdirs();
	            }
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Exception Occured -> "+e.getMessage());
    		e.printStackTrace();
    	}
        return extractedFileName;
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}

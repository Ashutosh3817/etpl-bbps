package com.etpl.bbps.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.etpl.bbps.common.CommonUtils;
import com.etpl.bbps.common.EtplConstant;
import com.etpl.bbps.common.MailUtil;
import com.etpl.bbps.common.ResponseBean;
import com.etpl.bbps.config.TokenRequest;
import com.etpl.bbps.config.TokenResponse;
import com.etpl.bbps.constant.ResponseModel;
import com.etpl.bbps.model.AllowedPaymentMethod;
import com.etpl.bbps.model.Authenticators;
import com.etpl.bbps.model.BbpsTransaction;
import com.etpl.bbps.model.BillerInfo;
import com.etpl.bbps.model.BillerInputParams;
import com.etpl.bbps.model.BillerModel;
import com.etpl.bbps.model.Complaint;
import com.etpl.bbps.model.PaymentChannels;
import com.etpl.bbps.repository.BbpsTransactionRepository;
import com.etpl.bbps.service.BbpsService;
import com.etpl.bbps.service.BbpsTransactionService;
import com.etpl.bbps.service.BillerService;
import com.etpl.bbps.service.ComplaintService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
@RestController
//@RequestMapping("/biller")
@RequestMapping("/bbps/biller")
@CrossOrigin(origins = "*")
public class BBPSController {

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
	
	@Value("${REVERSE_MAIN_WALLET_BALANCE_URL}")
	private String reverseMainBalanceUrl;

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
	
	@Value("${GET_PAYMENT_STATUS_URL}")
	private String getPaymentStatusUrl;

	@Autowired
    private BbpsTransactionRepository trxRepo;
	
	@PostMapping("/generate-biller-file")
	public ResponseEntity<JsonNode> listTransaction(@RequestBody Map<String, String> uBody) throws Exception {
		System.out.println("generate-biller-file>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		System.out.println(uBody);
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
		return ResponseEntity.ok(response.getBody());
	}

	@GetMapping("/retrieve-biller-list/{billerCategory}")
	public ResponseEntity<ResponseModel> retrieveBillerList(@PathVariable String billerCategory) throws Exception {
		System.out.println("retrieve-biller-list by category start >>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		List<BillerModel> billerList = new ArrayList();
		List<Map<String,String>> objList=null;
		if(null!=billerCategory && billerCategory.equalsIgnoreCase("education"))
		{
			objList = bbpsService.getBillerDtl(billerCategory);
			res.setData(objList);
		}
		else
		{	
		    billerList = bbpsService.findByBillerCategory(billerCategory);
		    res.setData(billerList);
		}
		if (!billerList.isEmpty()) {
			res.setMessage("Records found");
			res.setStatus("200");
			res.setData(billerList);
		} else {
			if(null!=objList)
			{
				res.setMessage("Records found");
				res.setStatus("200");
				res.setData(objList);
			}
			else {
			res.setData(null);	
			res.setMessage("Records not found");
			res.setStatus("404");
			}
		}
		System.out.println("retrieve-biller-list by category end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(res);
	}

	
	// get all city list of Education category
	
	@GetMapping("/get-city-list/{billerCategory}")
	public ResponseEntity<ResponseModel> getCityList(@PathVariable String billerCategory) throws Exception {
		System.out.println("/get-city-list/{billerCategory} start >>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		List<Map<String,String>> objList=null;
		
			objList = bbpsService.getBillerCityList(billerCategory);
			res.setData(objList);
		
		if (null!=objList) {
			res.setMessage("Records found");
			res.setStatus("200");
			res.setData(objList);
		} else {
		
			res.setData(null);	
			res.setMessage("Records not found");
			res.setStatus("404");
			}
		System.out.println("/get-city-list/{billerCategory} end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(res);
	}
	
	
	@GetMapping("/get-all-biller-category-name")
	public ResponseEntity<ResponseModel> getAllBillerCategoryName() throws Exception {
		System.out.println("/get-all-biller-category-name start >>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		List<Map<String,String>> objList=null;
		
			objList = bbpsService.getAllBillerCategoryName();
			res.setData(objList);
		
		if (null!=objList) {
			res.setMessage("Records found");
			res.setStatus("200");
			res.setData(objList);
		} else {
		
			res.setData(null);	
			res.setMessage("Records not found");
			res.setStatus("404");
			}
		System.out.println("/get-all-biller-category-name end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(res);
	}
	
	// get all biller list of Education category
	
		@GetMapping("/get-biller-list-by-city/{city}")
		public ResponseEntity<ResponseModel> getBillerList(@PathVariable String city) throws Exception {
			System.out.println("/get-biller-list-by-city/{city} start >>>>>>>>>>>>>>>>>>>");
			ResponseModel res = new ResponseModel();
			List<BillerModel> list=null;
			
				list = bbpsService.getEducationBillerList(city);
				res.setData(list);
			
			if (null!=list) {
				res.setMessage("Records found");
				res.setStatus("200");
				res.setData(list);
			} else {
			
				res.setData(null);	
				res.setMessage("Records not found");
				res.setStatus("404");
				}
			System.out.println("/get-biller-list-by-city/{city} end >>>>>>>>>>>>>>>>>>>");
			return ResponseEntity.ok(res);
		}

	
	
	@GetMapping("/retrieve-biller-by-biller-id/{billerId}")
	public ResponseEntity<ResponseModel> retrieveBillerByBillerId(@PathVariable String billerId) throws Exception {
		System.out.println("retrieve-biller-by-biller-id start >>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		BillerModel biller = bbpsService.findByBillerId(billerId);
		res.setData(biller);
		res.setMessage("Records found");
		res.setStatus("200");
		System.out.println("retrieve-biller-by-biller-id end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(res);
	}

	@PostMapping("/validate-payment/{customerId}")
	public ResponseEntity<JsonNode> validatePayment(@PathVariable String customerId, @RequestBody JsonNode requestBody,
			HttpServletRequest req) throws Exception {
		System.out.println("validate-payment start >>>>>>>>>>>>>>>>>>>");
		System.out.println("Customer ID ->"+customerId);
		HttpHeaders headers = new HttpHeaders();
		
		System.out.println(requestBody);
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		((ObjectNode) requestBody.get("metadata").get("agent")).put("agentid", agentId);
		((ObjectNode) requestBody.get("metadata").get("device")).put("init_channel", initChannel);
		((ObjectNode) requestBody.get("risk").get(0)).put("score_provider", ouId);
		
		System.out.println("before hash request body is " + requestBody);
		
		System.out.println("before hash request body in string format is " + requestBody.toString());
		
		String hashBody = CommonUtils.hashSHA256(requestBody.toString());
		
		System.out.println("after hash request body is" + hashBody);
		
		String request = "POST" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
				+ "/billpay/validate" + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
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
		
		System.out.println("Request Body -> "+requestBody.toString());
		System.out.println("Headers -> "+headers);
		String updatedUrl = validatePaymentUrl.replace("<sourceid>", sourceId);
		System.out.println("customer id before replace in url is "+customerId);
		String updatedUrlNew = updatedUrl.replace("<customerid>", customerId);
		
		
		System.out.println("Validate Payment Url -> "+updatedUrlNew);
		
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(updatedUrlNew, entity, JsonNode.class);
		System.out.println("Response ->"+response);
		System.out.println("validate-payment end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/make-payment/{customerId}")
	public ResponseEntity<ResponseModel> makePayment(@PathVariable String customerId, @RequestBody JsonNode requestBody,
			HttpServletRequest req) throws Exception {
		System.out.println("make-payment start >>>>>>>>>>>>>>>>>>>");
		ResponseModel rs = new ResponseModel();
		TokenRequest tRequest = new TokenRequest();
		String billerCategory = req.getHeader("billercategory");
		tRequest.setToken(getToken(req));
		HttpEntity<TokenRequest> re = new HttpEntity<>(tRequest);
		TokenResponse tokenRes = restTemplate.exchange(tokenurl, HttpMethod.POST, re, TokenResponse.class).getBody();
		if (tokenRes.getStatus().contentEquals("True")) {
			HttpHeaders headers = new HttpHeaders();
			System.out.println(requestBody);
			headers.add("Accept", accept);
			headers.add("Content-Type", contentType);
			String bdTraceId = CommonUtils.getBdTraceId(20);
			String bdTimeStamp = CommonUtils.getBdTimeStamp();
			headers.add("BD-Traceid", bdTraceId);
			headers.add("BD-Timestamp", bdTimeStamp);
			((ObjectNode) requestBody.get("metadata").get("agent")).put("agentid", agentId);
			((ObjectNode) requestBody.get("metadata").get("device")).put("init_channel", initChannel);
			((ObjectNode) requestBody.get("risk").get(0)).put("score_provider", ouId);
			System.out.println("before has request body is " + requestBody);
			String hashBody = CommonUtils.hashSHA256(requestBody.toString());
			System.out.println("after hash request body is" + hashBody);
			String request = "POST" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/"
					+ customerId + "/billpay/payments" + EtplConstant.MSG_SEPERATOR + contentType
					+ EtplConstant.MSG_SEPERATOR + accept + EtplConstant.MSG_SEPERATOR + bdTraceId
					+ EtplConstant.MSG_SEPERATOR + bdTimeStamp;
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
			System.out.println("request url is /hgpay/v2_1/DRK01/customers/<customerno>/billpay/payments");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);
			String url = makePaymentUrl;
			String updatedMakePayUrl = url.replace("<sourceid>", sourceId);
			System.out.println("customer id before replace in url is "+customerId);
			String updatedUrlMakePayNew = updatedMakePayUrl.replace("<customerid>", customerId);
			System.out.println("makePaymentUrl Payment Url -> "+updatedUrlMakePayNew);
			if (requestBody.get("payment_account").get("payment_method").asText().equals(EtplConstant.Wallet)) {
				System.out.println("inside wallet ");
				headers = new HttpHeaders();
				headers.set(EtplConstant.CONNECT_TYPE, "application/json");
				Map<String, String> m = new HashMap<>();
				m.put("agentId", tokenRes.getAgentId());
				m.put("amount", requestBody.get("debit_amount").asText());
				m.put("source", requestBody.get("payment_type").asText());
				m.put("paymentid", requestBody.get("validationid").asText()); // validation id
				m.put("source_ref_no", requestBody.get("source_ref_no").asText());
				m.put("biller_category", billerCategory); // Biller Category
				RestTemplate restTemp = new RestTemplate();
				ResponseEntity<ResponseModel> res = restTemp.postForEntity(updateMainBalanceUrl, m,
						ResponseModel.class);
				if (res.getBody().getStatus().equals("409")) {
					System.out.println("wallet balance is low");
					rs.setData(null);
					rs.setStatus("409");
					rs.setMessage("wallet balance is low, can not transfer money");
				}
				if (res.getBody().getStatus().equals("200")) {
					System.out.println("wallet balance deducted successfully");
					rs.setData(requestBody);
					rs.setStatus("200");
					rs.setMessage("Payment successfull");
					ResponseEntity<JsonNode> response=null;
					try {
						System.out.println("before hitting the make payment api url");
					response = restTemplate.postForEntity(updatedUrlMakePayNew, entity, JsonNode.class);
					System.out.println(response);	
					System.out.println("after hitting the make payment api url");
					System.out.println("make payment api response in payment type wallet is>>>>>>>>>>>>>>>>>>> ");
					System.out.println("Response -> "+response.getBody());
					}
					catch(Exception e)
					{
						System.out.println("Exception generated in calling make payment at BBPS side");
						System.out.println(e); 
						headers = new HttpHeaders();
						headers.set(EtplConstant.CONNECT_TYPE, "application/json");
						m = new HashMap<>();
						m.put("agentId", tokenRes.getAgentId());
						m.put("amount", requestBody.get("debit_amount").asText());
						m.put("source", requestBody.get("payment_type").asText());
						m.put("paymentid", requestBody.get("validationid").asText()); // validation id
						m.put("source_ref_no", requestBody.get("source_ref_no").asText());
						m.put("biller_category", billerCategory); // Biller Category
						restTemp = new RestTemplate();
						ResponseEntity<ResponseModel> reverseResp = restTemp.postForEntity(reverseMainBalanceUrl, m,
								ResponseModel.class);
						System.out.println("Transaction reversed");
					}
					// update main wallet
					JsonNode node = response.getBody();
					if (node.get("biller_status").asText().equals(EtplConstant.SUCCESS) || node.get("biller_status").asText().equals(EtplConstant.PENDING)
							|| node.get("biller_status").asText().equals(EtplConstant.PAID)) {
					// save Transaction Details in db
					try {
						BbpsTransaction trx = new BbpsTransaction();
						if (node.has("bbps_ref_no")) {
							trx.setBbpsRefNo(node.get("bbps_ref_no").asText());
						}
						trx.setBillerCatRes(node.get("biller_category").asText());
						trx.setBillerCategory(billerCategory);
						//billerCategory
						trx.setBillerId(node.get("billerid").asText());
						trx.setBillerName(node.get("biller_name").asText());
						trx.setBillerStatus(node.get("biller_status").asText());
						trx.setCurrency(node.get("currency").asText());
						trx.setPaymentStatus(node.get("payment_status").asText());
						trx.setObjectId(node.get("objectid").asText());
						trx.setPaymentAmount(node.get("payment_amount").asText());
						trx.setPaymentId(node.get("paymentid").asText());
						trx.setPaymentMethod(node.get("payment_account").get("payment_method").asText());
						trx.setPaymentRemarks(node.get("payment_remarks").asText());
						trx.setPaymentStatus(node.get("payment_status").asText());
						trx.setPaymentType(EtplConstant.Wallet);
						trx.setSourceId(node.get("sourceid").asText());
						trx.setSourceRefNo(node.get("source_ref_no").asText());
						trx.setTxnDateTime(node.get("txn_date_time").asText());
						trx.setCustomerMobile(tokenRes.getMobileNo());
						trx.setAuthenticators(node.get("authenticators").toString());
						trx.setCustomerId(customerId);
						if(node.has("additional_info"))
						trx.setAdditionalInfo(node.get("additional_info").toString());
						
						if(node.has("billlist"))
						{
							trx.setBillList(node.get("billlist").toString());
						}
						
						if(node.has("biller_approval_code"))
						{
							trx.setBillerApprovalCode(node.get("biller_approval_code").asText());
						}
						
						if(node.has("cou_conv_fee"))
						{
							trx.setCouConvFee(node.get("cou_conv_fee").asText());
						}
						if(node.has("bou_conv_fee"))
						{
							trx.setBouConvFee(node.get("bou_conv_fee").asText());
						}
						
						trx.setCustomerId(customerId);
						System.out.println("saving transaction");
						trx = trxService.save(trx);

						// send sms to agent

						try {
							if (node.has("bbps_ref_no")) {
								String message = EtplConstant.BBPS_PAYMENT_SUCCESS_MESSAGE_TEMPLATE;
								message = message.replace("{#amt#}", node.get("debit_amount").asText());
								message = message.replace("{#serviceName#}", node.get("biller_name").asText());
								if (node.has("customerid"))
									message = message.replace("{#consumerNo#}",
											node.get("customerid").asText());
								else
									message = message.replace("{#consumerNo#}", node.get("paymentid").asText());
								message = message.replace("{#date#}", node.get("txn_date_time").asText());
								message = message.replace("{#paymentMethod#}",
										node.get("payment_account").get("payment_method").asText());
								message = message.replace("{#bbps_refNo#}", node.get("paymentid").asText());
								String urlParameters = "workingkey=" + smsWorkingKey + "&sender=JDRKPL&to="
										+ tokenRes.getMobileNo() + "&message=" + message;
								RestTemplate resTemp = new RestTemplate();
								String result = resTemp.getForObject(smsUrl + urlParameters, String.class);
							} else {
								String message = EtplConstant.NON_BBPS_PAYMENT_SUCCESS_MESSAGE_TEMPLATE;
								message = message.replace("{#amt#}", node.get("debit_amount").asText());
								message = message.replace("{#serviceName#}", node.get("biller_name").asText());
								message = message.replace("{#paymentId#}", node.get("paymentid").asText());
								String urlParameters = "workingkey=" + smsWorkingKey + "&sender=JDRKPL&to="
										+ tokenRes.getMobileNo() + "&message=" + message;
								RestTemplate resTemp = new RestTemplate();
								String result = resTemp.getForObject(smsUrl + urlParameters, String.class);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					rs.setData(node);
					rs.setStatus("200");
					rs.setMessage("Payment successfull");
					}
					else if(node.get("biller_status").asText().equals(EtplConstant.FAILED) || node.get("biller_status").asText().equals(EtplConstant.FAIL))
					{
						headers = new HttpHeaders();
						headers.set(EtplConstant.CONNECT_TYPE, "application/json");
						m = new HashMap<>();
						m.put("agentId", tokenRes.getAgentId());
						m.put("amount", requestBody.get("debit_amount").asText());
						m.put("source", requestBody.get("payment_type").asText());
						m.put("paymentid", requestBody.get("validationid").asText()); // validation id
						m.put("source_ref_no", requestBody.get("source_ref_no").asText());
						m.put("biller_category", billerCategory); // Biller Category
						restTemp = new RestTemplate();
						ResponseEntity<ResponseModel> reverseResp = restTemp.postForEntity(reverseMainBalanceUrl, m,
								ResponseModel.class);
						if (reverseResp.getBody().getStatus().equals("200")) {
							
							//save transaction in db
							BbpsTransaction trx = new BbpsTransaction();
							if (node.has("bbps_ref_no")) {
								trx.setBbpsRefNo(node.get("bbps_ref_no").asText());
							}
							trx.setBillerCatRes(node.get("biller_category").asText());
							trx.setBillerCategory(billerCategory);
							trx.setBillerId(node.get("billerid").asText());
							trx.setBillerName(node.get("biller_name").asText());
							trx.setBillerStatus(node.get("biller_status").asText());
							trx.setCurrency(node.get("currency").asText());
							trx.setPaymentStatus(node.get("payment_status").asText());
							trx.setObjectId(node.get("objectid").asText());
							trx.setPaymentAmount(node.get("payment_amount").asText());
							trx.setPaymentId(node.get("paymentid").asText());
							trx.setPaymentMethod(node.get("payment_account").get("payment_method").asText());
							trx.setPaymentRemarks(node.get("payment_remarks").asText());
							trx.setPaymentStatus(node.get("payment_status").asText());
							trx.setPaymentType(EtplConstant.Wallet);
							trx.setSourceId(node.get("sourceid").asText());
							trx.setSourceRefNo(node.get("source_ref_no").asText());
							trx.setTxnDateTime(node.get("txn_date_time").asText());
							trx.setCustomerMobile(tokenRes.getMobileNo());
							trx.setAuthenticators(node.get("authenticators").toString());
							trx.setCustomerId(customerId);
							//trx.setAdditionalInfo(node.get("additional_info").toString());
							trx.setCustomerId(customerId);
							System.out.println("saving transaction");
							trx = trxService.save(trx);

							
							
							rs.setData(null);
							rs.setStatus("409");
							rs.setMessage("Payment Failed");
						}
					}
							}
		}
			else if (requestBody.get("payment_account").get("payment_method").asText().equals(EtplConstant.UPI)) {
				ResponseEntity<JsonNode> response = restTemplate.postForEntity(updatedUrlMakePayNew, entity, JsonNode.class);
				
				System.out.println("make payment api response in payment type UPI is>>>>>>>>>>>>>>>>>>> ");
				System.out.println(response.getBody());
				
				JsonNode node = response.getBody();
				// save Transaction Details in db
				if (node.get("biller_status").asText().equals(EtplConstant.SUCCESS) || node.get("biller_status").asText().equals(EtplConstant.PENDING)
						|| node.get("biller_status").asText().equals(EtplConstant.PAID)) {
				try {
					BbpsTransaction trx = new BbpsTransaction();
					if (node.has("bbps_ref_no")) {
						trx.setBbpsRefNo(node.get("bbps_ref_no").asText());
					}
					trx.setBillerCatRes(node.get("biller_category").asText());
					trx.setBillerCategory(billerCategory);
					trx.setBillerId(node.get("billerid").asText());
					trx.setBillerName(node.get("biller_name").asText());
					trx.setBillerStatus(node.get("biller_status").asText());
					trx.setCurrency(node.get("currency").asText());
					trx.setPaymentStatus(node.get("payment_status").asText());
					trx.setObjectId(node.get("objectid").asText());
					trx.setPaymentAmount(node.get("payment_amount").asText());
					trx.setPaymentId(node.get("paymentid").asText());
					trx.setPaymentMethod(node.get("payment_account").get("payment_method").asText());
					trx.setPaymentRemarks(node.get("payment_remarks").asText());
					trx.setPaymentStatus(node.get("payment_status").asText());
					trx.setPaymentType(EtplConstant.UPI);
					trx.setVpa(node.get("payment_account").get("vpa").asText());
					trx.setSourceId(node.get("sourceid").asText());
					trx.setSourceRefNo(node.get("source_ref_no").asText());
					trx.setTxnDateTime(node.get("txn_date_time").asText());
					trx.setCustomerMobile(tokenRes.getMobileNo());
					trx.setAuthenticators(node.get("authenticators").toString());
					trx.setCustomerId(customerId);
					trx = trxService.save(trx);

					// send sms to consumer

					try {
						if (node.has("bbps_ref_no")) {
							String message = EtplConstant.BBPS_PAYMENT_SUCCESS_MESSAGE_TEMPLATE;
							message = message.replace("{#amt#}", node.get("debit_amount").asText());
							message = message.replace("{#serviceName#}", node.get("biller_name").asText());
							if (node.has("customerid"))
								message = message.replace("{#consumerNo#}", node.get("customerid").asText());
							else
								message = message.replace("{#consumerNo#}", node.get("paymentid").asText());
							message = message.replace("{#date#}", node.get("txn_date_time").asText());
							message = message.replace("{#paymentMethod#}",
									node.get("payment_account").get("payment_method").asText());
							message = message.replace("{#bbps_refNo#}", node.get("paymentid").asText());
							headers = new HttpHeaders();
							String urlParameters = "workingkey=" + smsWorkingKey + "&sender=JDRKPL&to="
									+ tokenRes.getMobileNo() + "&message=" + message;
							RestTemplate resTemp = new RestTemplate();
							String result = resTemp.getForObject(smsUrl + urlParameters, String.class);
						} else {
							String message = EtplConstant.NON_BBPS_PAYMENT_SUCCESS_MESSAGE_TEMPLATE;
							message = message.replace("{#amt#}", node.get("debit_amount").asText());
							message = message.replace("{#serviceName#}", node.get("biller_name").asText());
							message = message.replace("{#paymentId#}", node.get("paymentid").asText());
							headers = new HttpHeaders();
							String urlParameters = "workingkey=" + smsWorkingKey + "&sender=JDRKPL&to="
									+ tokenRes.getMobileNo() + "&message=" + message;
							RestTemplate resTemp = new RestTemplate();
							String result = resTemp.getForObject(smsUrl + urlParameters, String.class);
						}
						
						rs.setData(node);
						rs.setStatus("200");
						rs.setMessage("Payment successfull");
					}

					catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
			
	}

		else {
			rs.setStatus(EtplConstant.NOT_AUTHORIZED);
			rs.setMessage(EtplConstant.NOT_AUTHORIZED_MESSAGE);
		}

		System.out.println("make-payment end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(rs);
	}

	// get bbps transaction history from DB

	@PostMapping("/transaction-history")
	public ResponseEntity<ResponseModel> getTransactionHistoryList(@RequestBody Map<String, String> map,
			HttpServletRequest req) throws Exception {
		Map<String, Object> data = trxService.getTransactionHistoryList(map, req);
		ResponseModel rs = new ResponseModel();
		rs.setData(data);
		rs.setMessage("Records found");
		rs.setStatus("200");
		return ResponseEntity.ok(rs);
	}

	@GetMapping("/get-transaction-history-by-id/{id}")
	public ResponseEntity<ResponseModel> getTransactionHistoryById(@PathVariable Long id) throws Exception {
		BbpsTransaction data = trxService.getTransactionHistoryById(id);
		ResponseModel rs = new ResponseModel();
		rs.setData(data);
		rs.setMessage("Records found");
		rs.setStatus("200");
		return ResponseEntity.ok(rs);
	}

	
	// get single bill
	@GetMapping("/retrieve-bill/{customerId}/{billId}")
	public ResponseEntity<JsonNode> retrieveBill(@PathVariable String customerId, @PathVariable String billId)
			throws Exception {
		System.out.println("retrieve-bill/{customerId}/{billId} start >>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
				+ "/billpay/bills/" + billId + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR
				+ accept + EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		validatePaymentUrl = validatePaymentUrl.replace("<sourceid>", sourceId);
		validatePaymentUrl = validatePaymentUrl.replace("<customerid>", customerId);
		ResponseEntity<JsonNode> response = restTemplate.exchange(retrieveBillUrl + billId, HttpMethod.GET,
				requestEntity, JsonNode.class);
		System.out.println("retrieve-bill/{customerId}/{billId} end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	// get all bill
	@GetMapping("/retrieve-all-bills/{customerId}")
	public ResponseEntity<JsonNode> retrieveAllBills(@PathVariable String customerId) throws Exception {
		System.out.println("/retrieve-all-bills/{customerId}/{billId} start >>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
				+ "/billpay/bills/" + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		validatePaymentUrl = validatePaymentUrl.replace("<sourceid>", sourceId);
		validatePaymentUrl = validatePaymentUrl.replace("<customerid>", customerId);
		ResponseEntity<JsonNode> response = restTemplate.exchange(retrieveBillUrl, HttpMethod.GET, requestEntity,
				JsonNode.class);
		System.out.println("/retrieve-all-bills/{customerId}/{billId} end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	@GetMapping("/download-biller-file/{fileName}")
	public ResponseEntity<ResponseModel> downloadFile(@PathVariable String fileName) throws Exception {
		System.out.println("FileName Recieved -> "+fileName);
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
		
		ResponseEntity<byte[]> response = restTemplate.exchange(downloadBillersFileUrl + fileName, HttpMethod.GET,
				requestEntity, byte[].class);
		//Files.write(Paths.get("HGA1F090160000011572.zip"), response.getBody());
		
		System.out.println("Call Success");
				
		HttpHeaders hed = new HttpHeaders();
		hed.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> imgBody = new LinkedMultiValueMap<>();
		imgBody.add("file", new FileSystemResource(convert(response.getBody(), fileName)));
		HttpEntity<MultiValueMap<String, Object>> uploadEntiry = new HttpEntity<>(imgBody, hed);
		ResponseEntity<ResponseBean> imgResp = restTemplate.postForEntity(dmsUploadUrl, uploadEntiry,
				ResponseBean.class);
		res.setMessage(imgResp.getBody().getFileUrl());
		System.out.println("File Uploaded successfully in s3 bucket with url>>>>>>>>"+imgResp.getBody().getFileUrl());
		
		ResponseBean req = new ResponseBean();
		req.setFileUrl(imgResp.getBody().getFileUrl());
		restTemplate = new RestTemplate();
		HttpEntity<ResponseBean> re = new HttpEntity<>(req);
		ResponseBean imgResponse = restTemplate
				.exchange(dmsPresignedUrl, HttpMethod.POST, re, ResponseBean.class).getBody();
		
		System.out.println("uploade file presigned url is>>>>>>>>>>>>>>"+imgResponse.getFileUrl());
		
		//Files.write(Paths.get("HGA1F090160000011572.zip"), response.getBody());
		//Files.write(Paths.get("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip"), response.getBody());
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ end ::::::::::>>>>>>>>>>>>>>>>>>>");
		//res.setMessage("File downloaded successfully");
		//res.setStatus("200");
		System.out.println("Now uploading starts..........");
		uploadBillerFile(response.getBody());
		System.out.println("Now uploading Ends..........");
		
		//Files.write(Paths.get("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip"), response.getBody());
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ end ::::::::::>>>>>>>>>>>>>>>>>>>");
		res.setMessage("File uploaded successfully");
		res.setStatus("200");
		return ResponseEntity.ok(res);
	}

	// get operator series list
	// >>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++=============================>>>>>>>>>>>>>>>>>
	@GetMapping("/list-operatorseries/{mobileNo}")
	public ResponseEntity<JsonNode> getOperatorSeriesList(@PathVariable String mobileNo) throws Exception {
		System.out.println("list-operatorseries started ::::::::::>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/billpay/operatorseries/"
				+ mobileNo + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		
		String getAllOperatorSeriesUrlUpdated = getAllOperatorSeriesUrl.replace("<sourceid>", sourceId);
		System.out.println("url is "+getAllOperatorSeriesUrlUpdated);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getAllOperatorSeriesUrlUpdated + "/" + mobileNo,
				HttpMethod.GET, requestEntity, JsonNode.class);
		System.out.println("list-operatorseries end ::::::::::>>>>>>>>>>>>>>>>>>>");
		
		return ResponseEntity.ok(response.getBody());
	}
	
	// get payment status based on customer id
	// get all bill
		@GetMapping("/get-payment-status/{customerId}")
		public ResponseEntity<JsonNode> getPaymentStatus(@PathVariable String customerId) throws Exception {
			System.out.println("/get-payment-status/{customerId} start >>>>>>>>>>>>>>>>>>>");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", accept);
			headers.add("Content-Type", contentType);
			String bdTraceId = CommonUtils.getBdTraceId(20);
			String bdTimeStamp = CommonUtils.getBdTimeStamp();
			headers.add("BD-Traceid", bdTraceId);
			headers.add("BD-Timestamp", bdTimeStamp);
			String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
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
			String finalUrl = withSourceId.replace("<customerid>", customerId);
			ResponseEntity<JsonNode> response = restTemplate.exchange(finalUrl, HttpMethod.GET, requestEntity,
					JsonNode.class);
			System.out.println("/get-payment-status/{customerId} end >>>>>>>>>>>>>>>>>>>");
			return ResponseEntity.ok(response.getBody());
		}

	// get all recharge plan list
	// >>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++=============================>>>>>>>>>>>>>>>>>
	@GetMapping("/list-recharge-plan")
	public ResponseEntity<JsonNode> getAllRechargePlanList() throws Exception {
		System.out.println("list-recharge-plan started ::::::::::>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/billpay/plans"
				+ EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		getAllRechargePlanUrl = getAllRechargePlanUrl.replace("<sourceid>", sourceId);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getAllRechargePlanUrl, HttpMethod.GET, requestEntity,
				JsonNode.class);
		System.out.println("list-recharge-plan end ::::::::::>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	// get all recharge plan by biller id
	// >>>>>>>>>>>>>>>>>>>>>>>>>++++++++++++++++++++++=============================>>>>>>>>>>>>>>>>>

	@PostMapping("/get-recharge-plan-by-biller-id")
	public ResponseEntity<JsonNode> getRechargePlanUsingBillerId(@RequestBody Map<String, String> rBody)
			throws Exception {
		System.out.println("get-recharge-plan-by-biller-id started::::::::::>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/billpay/plans?billerid="
				+ rBody.get("billerId") + "&from_creation_date=" + rBody.get("fromCreationDate")
				+ EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
				+ EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		String getRechargePlanUrlUpdted = getRechargePlanUrl.replace("<sourceid>", sourceId);
		System.out.println("url is "+getRechargePlanUrlUpdted);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getRechargePlanUrlUpdted + rBody.get("billerId") + "&from_creation_date=" + rBody.get("fromCreationDate"),
				HttpMethod.GET, requestEntity, JsonNode.class);
		System.out.println("get-recharge-plan-by-biller-id end ::::::::::>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	// Raise Complaint
	// ///////////////////////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@PostMapping("/raise-complaint/{customerId}")
	public ResponseEntity<JsonNode> raiseComplaint(@PathVariable String customerId, @RequestBody JsonNode requestBody)
			throws Exception {
		System.out.println("raise-complaint start >>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		System.out.println(requestBody);
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		System.out.println("before has request body is " + requestBody);
		System.out.println("before has request body in string format is " + requestBody.toString());
		String hashBody = CommonUtils.hashSHA256(requestBody.toString());
		System.out.println("after hash request body is" + hashBody);
		String request = "POST" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
				+ "/billpay/complaints" + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR + accept
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
		System.out.println("request url is /hgpay/v2_1/<sourceid>/customers/<customerid>/billpay/complaints");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), headers);
		
		String newUrl = raiseComplaintUrl.replace("<sourceid>", sourceId);
		String updatedUrl = newUrl.replace("<customerid>", customerId);
		
		System.out.println("URL -> "+ updatedUrl);
		System.out.println("Headers -> "+ headers);
		System.out.println("Body -> "+ requestBody.toString());
		
		ResponseEntity<JsonNode> response = restTemplate.postForEntity(updatedUrl, entity, JsonNode.class);
		System.out.println("Response -> "+response);
		
		try {
			// save complaint response
			JsonNode node = response.getBody();
			Complaint comp = null;
			if (!Boolean.TRUE.equals(compService.existsByPaymentId(node.get("paymentid").asText()))) {
				comp = new Complaint();
				comp.setObjectId(node.get("objectid").asText());
				comp.setComplaintId(node.get("complaintid").asText());
				// comp.setComplaintDate(node.get("complaint_date").asText());
				comp.setComplaintType(node.get("complaint_type").asText());
				comp.setDisposition(node.get("disposition").asText());
				comp.setComplaintDesc(node.get("complaint_desc").asText());
				comp.setComplaintStatus(node.get("complaint_status").asText());
				comp.setAssigned(node.get("assigned").asText());
				// "response_code": "000",
				// comp.setResponseStatus(node.get("response_status").asText());
				if (node.has("bbps_ref_no"))
					comp.setBbpsRefNo(node.get("bbps_ref_no").asText());
				if (node.has("paymentid"))
					comp.setPaymentId(node.get("paymentid").asText());
				comp.setSourceRefNo(node.get("source_ref_no").asText());
				comp.setCustomerMobile(requestBody.get("mobile").asText());
				comp.setAgentId(requestBody.get("agentId").asText());
				comp = compService.saveComplaint(comp);
			} else {
				comp = compService.getCompDtlByPaymentId(node.get("paymentid").asText());
				comp.setObjectId(node.get("objectid").asText());
				comp.setComplaintId(node.get("complaintid").asText());
				// comp.setComplaintDate(node.get("complaint_date").asText());
				comp.setComplaintType(node.get("complaint_type").asText());
				comp.setDisposition(node.get("disposition").asText());
				comp.setComplaintDesc(node.get("complaint_desc").asText());
				comp.setComplaintStatus(node.get("complaint_status").asText());
				comp.setAssigned(node.get("assigned").asText());
				// "response_code": "000",
				// comp.setResponseStatus(node.get("response_status").asText());
				if (node.has("bbps_ref_no"))
					comp.setBbpsRefNo(node.get("bbps_ref_no").asText());
				if (node.has("paymentid"))
					comp.setPaymentId(node.get("paymentid").asText());
				comp.setSourceRefNo(node.get("source_ref_no").asText());
				comp = compService.saveComplaint(comp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("raise-complaint end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	// Retrieve Complaint
	// //////////////////////////<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@PostMapping("/complaint-list")
	public ResponseEntity<ResponseModel> backendUserList(@RequestBody Map<String, String> map, HttpServletRequest req)
			throws Exception {
		Map<String, Object> data = compService.getComplaintList(map, req);
		ResponseModel rs = new ResponseModel();
		rs.setData(data);
		rs.setMessage("Records found");
		rs.setStatus("200");
		return ResponseEntity.ok(rs);
	}

	@PostMapping("/retrieve-complaint/{customerId}")
	public ResponseEntity<JsonNode> retrieveComplaint(@PathVariable String customerId,
			@RequestBody Map<String, String> rBody) throws Exception {
		System.out.println("retrieve-complaint started::::::::::>>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/customers/" + customerId
				+ "/billpay/complaints?complaintid=" + rBody.get("complaintId") + "&complaint_type="
				+ rBody.get("complaintType") + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR
				+ accept + EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		String newUrl = retrieveComplaintUrl.replace("<sourceid>", sourceId);
		String updatedUrl = newUrl.replace("<customerid>", customerId);
		ResponseEntity<JsonNode> response = restTemplate.exchange(
				updatedUrl + rBody.get("complaintId") + "&complaint_type=" + rBody.get("complaintType"),
				HttpMethod.GET, requestEntity, JsonNode.class);

		try {
			// save complaint response
			JsonNode node = response.getBody();
			Complaint comp = compService.getCompDtlByPaymentId(node.get("paymentid").asText());
			comp.setObjectId(node.get("objectid").asText());
			comp.setComplaintId(node.get("complaintid").asText());
			// comp.setComplaintDate(node.get("complaint_date").asText());
			comp.setComplaintType(node.get("complaint_type").asText());
			comp.setDisposition(node.get("disposition").asText());
			comp.setComplaintDesc(node.get("complaint_desc").asText());
			comp.setComplaintStatus(node.get("complaint_status").asText());
			comp.setAssigned(node.get("assigned").asText());
			// "response_code": "000",
			comp.setResponseStatus(node.get("response_status").asText());
			if (node.has("bbps_ref_no"))
				comp.setBbpsRefNo(node.get("bbps_ref_no").asText());
			if (node.has("paymentid"))
				comp.setPaymentId(node.get("paymentid").asText());
			comp.setSourceRefNo(node.get("source_ref_no").asText());
			comp = compService.saveComplaint(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("retrieve-complaint end ::::::::::>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	@GetMapping("/upload-biller-file/{fileName}")
	public ResponseEntity<ResponseModel> uploadBillerFile(byte[] zipData) throws Exception {
		
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

		res.setMessage("File uploaded successfully");
		res.setStatus("200");
		System.out.println("/upload-biller-file ended ::::::::::>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(res);
	}

	
	// check biller availability api
	@GetMapping("/check-all-biller-availability")
	public ResponseEntity<JsonNode> checkBiller()
			throws Exception {
		System.out.println("check-all-biller-availability start >>>>>>>>>>>>>>>>>>>");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", accept);
		headers.add("Content-Type", contentType);
		String bdTraceId = CommonUtils.getBdTraceId(20);
		String bdTimeStamp = CommonUtils.getBdTimeStamp();
		headers.add("BD-Traceid", bdTraceId);
		headers.add("BD-Timestamp", bdTimeStamp);
		String request = "GET" + EtplConstant.MSG_SEPERATOR + "/hgpay/v2_1/" + sourceId + "/billpay/billeralerts" + EtplConstant.MSG_SEPERATOR + contentType + EtplConstant.MSG_SEPERATOR
				+ accept + EtplConstant.MSG_SEPERATOR + bdTraceId + EtplConstant.MSG_SEPERATOR + bdTimeStamp;
		System.out.println("HMAC String is " + request);
		String hmacvalue = CommonUtils.HmacSHA256(request, secretKey);
		System.out.println("complete Authorization header is " + clientId + ":" + hmacvalue);
		System.out.println("BD-Traceid is " + bdTraceId);
		System.out.println("bdTimeStamp is " + bdTimeStamp);
		headers.add("Authorization", "HMACSignature " + clientId + ":" + hmacvalue);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		checkBillerAvailabilityUrl = checkBillerAvailabilityUrl.replace("<sourceid>", sourceId);
		System.out.println("url is>>>>>>>>"+checkBillerAvailabilityUrl);
		ResponseEntity<JsonNode> response = restTemplate.exchange(checkBillerAvailabilityUrl, HttpMethod.GET,requestEntity, JsonNode.class);
		System.out.println("check-all-biller-availability end >>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(response.getBody());
	}

	
	
	public String getToken(HttpServletRequest req) {

		final String requestTokenHeader = req.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);
		}
		return jwtToken;
	}

	
	/*@GetMapping("/test-schedular")
	public ResponseEntity<String> test(){
		
		String response = "Failed";
		//Schedular obj = new Schedular();
		//try {
			//obj.syncBillerCategory();
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		try
		{
		System.out.println("/*****************Syncing Biller Category Starts*****************");
        
		System.out.println("\n Generating biller file");
        final String fileName = this.generateBillerFile();
        System.out.println("\n Generated file name -> " + fileName);
        response = "File Generated";
        System.out.println("\n Downloading biller file");
        this.downloadFile(fileName);
        response = "File Downloaded";
        System.out.println("\n Uploading biller file");
        this.uploadBillerFile();
        response = "File Uploaded";
        System.out.println("/*****************Syncing Biller Category Starts***********************");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
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
		return response.getBody().get("fileid").toString();
		
	}
	
	public void downloadFiles(String fileName) throws Exception {
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
		System.out.println("Calling Download API -> ");
		ResponseEntity<byte[]> response = restTemplate.exchange(downloadBillersFileUrl + fileName, HttpMethod.GET,
				requestEntity, byte[].class);
		System.out.println("Response -> "+response);
		System.out.println("Response -> "+response.getStatusCodeValue());
		Files.write(Paths.get("HGA1F090160000011572.zip"), response.getBody());
		Files.write(Paths.get("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip"), response.getBody());
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ end ::::::::::>>>>>>>>>>>>>>>>>>>");
		res.setMessage("File downloaded successfully");
		res.setStatus("200");
	}
	
	public void uploadBillerFile() throws Exception {
		System.out.println("/upload-biller-file started ::::::::::>>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		//BufferedReader reader = new BufferedReader(new FileReader("E:/unzip/" + fileName + ".txt"));
		
		String fileName = unzip("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip","/home/ec2-user/ETPL/bbpsCallPull");
		System.out.println("Unzipped FileName -> "+fileName);
		BufferedReader reader = new BufferedReader(new FileReader("/home/ec2-user/ETPL/bbpsCallPull/" + fileName + ".txt"));
		
		try {
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
			
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
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
				bbpsService.saveBiller(model);
				System.out.println(jsonObj.getString("biller_logo"));
			}
		} finally {
			reader.close();
		}
		res.setMessage("File uploaded successfully");
		res.setStatus("200");
		System.out.println("/upload-biller-file/" + fileName + " ended ::::::::::>>>>>>>>>>>>>>>>>>>");
		
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
	*/
	/**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    /*public String unzip(String zipFilePath, String destDirectory) {
       
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
    }*/
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    /*private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }*/
	
    
    
	@GetMapping("/test-schedular")
	public void syncBillerCategory() throws Exception {

		System.out.println("/*****************Syncing Biller Category Test Schedular Starts*****************/");
        
		Map<String, String> uBody = new HashMap<String,String>();
		System.out.println("\n Generating biller file");
        
		ResponseEntity<JsonNode> response = this.listTransaction(uBody);
		//final String fileName = this.generateBillerFileTest(uBody);
        System.out.println("\n Generated file name -> " + response.getBody().get("fileid").toString());
        String fileName = response.getBody().get("fileid").toString();
        System.out.println("\n Downloading biller file -> "+fileName);
        Thread.sleep(10000);
        this.downloadFile(fileName);
        
        //System.out.println("\n Uploading biller file");
        //Thread.sleep(10000);
        //this.uploadBillerFileTest();
        System.out.println("/*****************Syncing Biller Category Starts***********************/");
		
	}
	
	public String generateBillerFileTest() throws Exception {
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
		return response.getBody().get("fileid").toString();
		
	}
	
	public void downloadFileTest(String fileName) throws Exception {
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
		Files.write(Paths.get("HGA1F090160000011572.zip"), response.getBody());
		Files.write(Paths.get("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip"), response.getBody());
		System.out.println("/hgpay/v2_1/DRK01/billpay/billers/file/ end ::::::::::>>>>>>>>>>>>>>>>>>>");
		res.setMessage("File downloaded successfully");
		res.setStatus("200");
	}
	
	public void uploadBillerFileTest() throws Exception {
		System.out.println("/upload-biller-file started ::::::::::>>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
		//BufferedReader reader = new BufferedReader(new FileReader("E:/unzip/" + fileName + ".txt"));
		
		String fileName = unzip("/home/ec2-user/ETPL/bbpsCallPull/HGA1F090160000011572.zip","/home/ec2-user/ETPL/bbpsCallPull");
		System.out.println("Unzipped FileName -> "+fileName);
		BufferedReader reader = new BufferedReader(new FileReader("/home/ec2-user/ETPL/bbpsCallPull/" + fileName + ".txt"));
		
		try {
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
			
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
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
				bbpsService.saveBiller(model);
				System.out.println(jsonObj.getString("biller_logo"));
			}
		} finally {
			reader.close();
		}
		res.setMessage("File uploaded successfully");
		res.setStatus("200");
		System.out.println("/upload-biller-file/" + fileName + " ended ::::::::::>>>>>>>>>>>>>>>>>>>");
		
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
	
	@GetMapping("/send-recon-file")
	public ResponseEntity<ResponseModel> sendReconFileManually() throws Exception {
		System.out.println("retrieve-biller-list by category start >>>>>>>>>>>>>>>>>>>");
		ResponseModel res = new ResponseModel();
        List<BbpsTransaction> list = trxRepo.getReconFileList();
        if(null!=list && list.size()>0)
        {
        System.out.println("List size is::::::::::::::::: "+list.size());
        MailUtil sm=new MailUtil();
        List<String> toList = new ArrayList();
        //toList.add("bpsingh7412@gmail.com");
        toList.add("ebpprecon@billdesk.com");
        toList.add("maheshgohil@billdesk.com");
        toList.add("ritesh.parekh@billdesk.com");
        List<String> ccList = new ArrayList(); 
        //ccList.add("bhanu.pratap@gmail.com");
        ccList.add("rohit.das@billdesk.com");
        ccList.add("souvik@billdesk.com");
        ccList.add("Sneha.mishra@billdesk.com");
        ccList.add("onkar.ramade@billdesk.com");
        ccList.add("sachin.maru@billdesk.com");
        ccList.add("dinesh_solanki@drkjodhpur.com");
        ccList.add("ankush_marmat@drkjodhpur.com");
        
        File file = new File("recon.txt");
		FileWriter writer  = new FileWriter(file);
		for(BbpsTransaction trx:list)
		{
		System.out.println(sourceId+EtplConstant.MSG_SEPERATOR+trx.getCustomerId()+EtplConstant.MSG_SEPERATOR+trx.getBillerId()+EtplConstant.MSG_SEPERATOR+trx.getPaymentId()
		+EtplConstant.MSG_SEPERATOR+trx.getSourceRefNo()+EtplConstant.MSG_SEPERATOR+trx.getPaymentAmount()+EtplConstant.MSG_SEPERATOR+CommonUtils.getDate(trx.getTxnDateTime()));;	
		writer.write(sourceId+EtplConstant.MSG_SEPERATOR+trx.getCustomerId()+EtplConstant.MSG_SEPERATOR+trx.getBillerId()+EtplConstant.MSG_SEPERATOR+trx.getPaymentId()
		+EtplConstant.MSG_SEPERATOR+trx.getSourceRefNo()+EtplConstant.MSG_SEPERATOR+trx.getPaymentAmount()+EtplConstant.MSG_SEPERATOR+CommonUtils.getDate(trx.getTxnDateTime()));
		}
		writer.flush();
		writer.close();
        sm.sendReconMail(toList, ccList,file);
        file.delete();
        }
		return ResponseEntity.ok(res);
	}

}
package com.etpl.bbps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		String str = "[\r\n"
				+ "    {\r\n"
				+ "        \"sourceid\": \"DRK01\",\r\n"
				+ "        \"customerid\": \"1671528291847\",\r\n"
				+ "        \"paymentid\": \"HGA4P180D00000039801\",\r\n"
				+ "        \"objectid\": \"payment\",\r\n"
				+ "        \"currency\": \"356\",\r\n"
				+ "        \"billerid\": \"OU1201000MEE17\",\r\n"
				+ "        \"validationid\": \"HGA1V16B490000184937\",\r\n"
				+ "        \"source_ref_no\": \"16715283340468130236107\",\r\n"
				+ "        \"payment_amount\": \"500.00\",\r\n"
				+ "        \"payment_remarks\": \"test payment\",\r\n"
				+ "        \"biller_name\": \"Meerut Institute Of Technology\",\r\n"
				+ "        \"biller_category\": \"Education\",\r\n"
				+ "        \"authenticators\": [\r\n"
				+ "            {\r\n"
				+ "                \"parameter_name\": \"Roll No\",\r\n"
				+ "                \"value\": \"501\"\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"payment_type\": \"instapay\",\r\n"
				+ "        \"txn_date_time\": \"20-12-2022 09:31:00\",\r\n"
				+ "        \"biller_approval_code\": \"139786275\",\r\n"
				+ "        \"biller_status\": \"SUCCESS\",\r\n"
				+ "        \"payment_account\": {\r\n"
				+ "            \"objectid\": \"paymentaccount\",\r\n"
				+ "            \"payment_method\": \"Wallet\",\r\n"
				+ "            \"mobileno\": \"9261749931\",\r\n"
				+ "            \"wallet_name\": \"DRK Wallet\"\r\n"
				+ "        },\r\n"
				+ "        \"billlist\": [\r\n"
				+ "            {\r\n"
				+ "                \"objectid\": \"bill\",\r\n"
				+ "                \"billid\": \"HGA1V16B490000184937B0\",\r\n"
				+ "                \"billerid\": \"OU1201000MEE17\",\r\n"
				+ "                \"sourceid\": \"DRK01\",\r\n"
				+ "                \"customerid\": \"1671528291847\",\r\n"
				+ "                \"billstatus\": \"PAID\",\r\n"
				+ "                \"authenticators\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"parameter_name\": \"Roll No\",\r\n"
				+ "                        \"value\": \"501\"\r\n"
				+ "                    }\r\n"
				+ "                ],\r\n"
				+ "                \"billnumber\": \"1001\",\r\n"
				+ "                \"billperiod\": \"Monthly\",\r\n"
				+ "                \"additional_details\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"seq\": \"1\",\r\n"
				+ "                        \"label\": \"Biller Unique Number\",\r\n"
				+ "                        \"value\": \"INV000001011\"\r\n"
				+ "                    }\r\n"
				+ "                ],\r\n"
				+ "                \"validationid\": \"HGA1V16B490000184937\",\r\n"
				+ "                \"customer_name\": \"SXCXIX XAXAXI\",\r\n"
				+ "                \"billdate\": \"16-06-2021\",\r\n"
				+ "                \"billduedate\": \"30-08-2022\",\r\n"
				+ "                \"billamount\": \"500.00\"\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"payment_status\": \"PAID\",\r\n"
				+ "        \"debit_amount\": \"500.00\",\r\n"
				+ "        \"cou_conv_fee\": \"0.00\",\r\n"
				+ "        \"bou_conv_fee\": \"0.00\",\r\n"
				+ "        \"bbps_ref_no\": \"BD012354BA4AAAAAATMF\",\r\n"
				+ "        \"additional_info\": [\r\n"
				+ "            {\r\n"
				+ "                \"parameter_name\": \"Biller Unique Number\",\r\n"
				+ "                \"value\": \"INV000002044\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";  
		ObjectMapper map = new ObjectMapper();  
		JsonNode node = map.readTree(str);  
		System.out.println(node);
		System.out.println(node.get(0).get("biller_status").asText());
		System.out.println(node.get(0).get("payment_account").get("payment_method").asText());
		System.out.println(node.get(0).get("billlist").get(0).get("billstatus").asText());

	}

}

package com.etpl.bbps.common;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;

public class CommonUtils {
	
	public static String HmacSHA256(String message, String secret) {
        MessageDigest md = null;
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte raw[] = sha256_HMAC.doFinal(message.getBytes());

            StringBuffer ls_sb = new StringBuffer();
            for (int i = 0; i < raw.length; i++) {
                ls_sb.append(char2hex(raw[i]));
            }
            return ls_sb.toString(); //step 6
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static String hashSHA256(String plaintext) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256"); //step 2
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (Exception e) {

            md = null;
        }
        StringBuffer ls_sb = new StringBuffer();
        byte raw[] = md.digest(); //step 4
        for (int i = 0; i < raw.length; i++) {
            ls_sb.append(char2hex(raw[i]));
        }


        return ls_sb.toString(); //step 6        

    }
	
	public static String char2hex(byte x)
	{
	char arr[]={
				  '0','1','2','3',
				  '4','5','6','7',
				  '8','9','A','B',
				  'C','D','E','F'
				};
	char c[] = {arr[(x & 0xF0)>>4],arr[x & 0x0F]};

	return (new String(c));
	}	
	
	public String getToken(HttpServletRequest req) {
		final String requestTokenHeader = req.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);
		}
		return jwtToken;
	}

	public static String getBdTraceId(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
	
	public static String getBdTimeStamp()
	{
		Date date = new Date();
		return new SimpleDateFormat("yyyyMMddhhmmss").format(date);
	}
	
	
	public static String getHMACSHA256(String key, String data) throws Exception {
		  Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		  sha256_HMAC.init(secret_key);

		  return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
		}
	
	
	public static String getDate(String start_dt) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
		System.out.println(start_dt);
		Date date = (Date)formatter.parse(start_dt);
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");
		return newFormat.format(date); 
	}
	
	public static String getCurrentDate()
	{
		Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
	    return formatter.format(date);		
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, IOException, ParseException {
	
		String validatePaymentUrl = "https://uat2.billdesk.com/hgpay/v2_1/<sourceid>/customers/<customerid>/billpay/validate";
				validatePaymentUrl=	validatePaymentUrl.replace("<sourceid>", "DRK01");
		validatePaymentUrl = validatePaymentUrl.replace("<customerid>", "12345678910");
		System.out.println(validatePaymentUrl);
		
		
		
		
		//System.out.println(hashSHA256("{ \"withhold_callback\": \"Y\" }"));
		
		/*
		 * String start_dt="15-11-2022 09:55:17"; DateFormat formatter = new
		 * SimpleDateFormat("dd-mm-yyyy"); System.out.println(start_dt); Date date =
		 * (Date)formatter.parse(start_dt); SimpleDateFormat newFormat = new
		 * SimpleDateFormat("yyyymmdd"); String finalString = newFormat.format(date);
		 * System.out.println(finalString);
		 */
//		Date date = new Date();
//	      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
//	       String str = formatter.format(date);
//	      System.out.print("Current date: "+str);
		//System.out.println(hashSHA256("{\"billerid\":\"BANK00000NATKB\",\"authenticators\":[{\"parameter_name\":\"Registered Mobile Number\",\"value\":\"8879769461\"},{\"parameter_name\":\"Last 4 digits of Credit Card Number\",\"value\":\"2021\"}],\"sitxn\":\"no\",\"customer\":{\"firstname\":\"ABmC\",\"lastname\":\"XYkZ\",\"mobile\":\"9349895862\",\"email\":\"abmmc@billdesk.com\"},\"metadata\":{\"agent\":{\"agentid\":\"BD01BD02AGT000000001\"},\"device\":{\"init_channel\":\"Agent\",\"ip\":\"124.124.1.1\",\"mobile\":\"9321775851\",\"geocode\":\"28.6139,78.5555\",\"postal_code\":\"600001\",\"terminalid\":\"123456\"}},\"risk\":[{\"score_provider\":\"BD01\",\"score_value\":\"030\",\"score_type\":\"TXNRISK\"},{\"score_provider\":\"BBPS\",\"score_value\":\"030\",\"score_type\":\"TXNRISK\"}]}"));

		//System.out.println("abc");
		//System.out.println(getBdTimeStamp());
		
		String test = "<PidData>\r\n" + 
				"  <Resp errCode=\"0\" errInfo=\"\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" nmPoints=\"36\" qScore=\"80\"/>\r\n" + 
				"  <DeviceInfo dpId=\"PRECISION.PB\" rdsId=\"PRECISION.WIN.001\" rdsVer=\"1.2.3\" dc=\"60e6a841-bcff-4e1b-be86-c3daf44c60ac\" mi=\"PBCS200\" mc=\"MIID+jCCAuKgAwIBAgIIZwAdjEVRnDwwDQYJKoZIhvcNAQELBQAwgewxNzA1BgNVBAMTLkRTIFBSRUNJU0lPTiBCSU9NRVRSSUMgSU5ESUEgUFJJVkFURSBMSU1JVEVEIDMxJjAkBgNVBDMTHU5PIDIyIEhBQklCVUxMQUggUk9BRCBUIE5BR0FSMRAwDgYDVQQJEwdDSEVOTkFJMRIwEAYDVQQIEwlUQU1JTE5BRFUxIjAgBgNVBAsTGVNPTFVUSU9OIERFVkVMT1BNRU5UIFRFQU0xMjAwBgNVBAoTKVBSRUNJU0lPTiBCSU9NRVRSSUMgSU5ESUEgUFJJVkFURSBMSU1JVEVEMQswCQYDVQQGEwJJTjAeFw0yMjAyMTcxMzU5NTlaFw0yMjAzMTkxMzU5NTlaMFAxFDASBgNVBAoMC1BSRUNJU0lPTlJEMRIwEAYDVQQDDAlQcmVjaXNpb24xEDAOBgNVBAcMB0NIRU5OQUkxEjAQBgNVBAsMCUJpb21ldHJpYzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMAEGRkVTypDRO/UY/UdTkhqN1Pg/b8mLRc3OZ+Q+HORXd2yLjuZBoYYS4i6lDheFoY5sSv/9ORazgQuaNL6O4pgt6w7pKC4B5NsfPuISkDFM2ESCsdcQle8ztGQdLc1IhW9sRUOB2I32EzntXM4Hh0jn9tNDuTHtJslcMgeKSRhgz6r58qvTiojuSk+Xm5Lj+EI9jUG++ysd2fRmnY6O5hzCN6p/ZJIM4QYNpMmpb8YaNJMAVUbPE6cKXHENzJoBmRwbMnZMggabD88C5MGdyHdI2d1275XZDoV78xr08L8aXO5EFzbgl/hg/G+WiF3Huajs0hMv5ppMub5wCuvDgsCAwEAAaM7MDkwCQYDVR0TBAIwADALBgNVHQ8EBAMCAYYwHwYDVR0jBBgwFoAUtTWwpFcL+TBx63TcMhe9Ih9zKGcwDQYJKoZIhvcNAQELBQADggEBAHm3OxiI7j3yXZt2TO6j/S4eRGRcQcuW3IsrDaROJWQTptl3j+gNIZBfmiMvgkTZdOZaWvhgi5Fa38Cirm82gGSWMbpQsGFum7glU5thGamnb3uMgvIJOQvPfY83KN56HzF/7Xne8zuKeoaIVo6ZKv8CmcTE0WedAdMYpEtD/2nawxGPLGg3vUpDVh1b7YWWytBaNoH6wfLgN7r+TTzp8y+JPNw3Cb3mNQHIFk+9aXTL6vPf5pgprilFNHyWgiEk3s7Cxx81UvjA9XH5m1Ig9ZOxYC/6n4eG1BbLbPD2xMPsGxpBnHbwm5Q3iYteby4u/rfC2jkJRqaZh5xCu2GGkI0=\">\r\n" + 
				"    <additional_info>\r\n" + 
				"      <Param name=\"srno\" value=\"P121609814\"/>\r\n" + 
				"      <Param name=\"ts\" value=\"2022-02-24 19:19:39 +05:30\"/>\r\n" + 
				"      <Param name=\"sysid\" value=\"4C4C4544-004C-4D10-8034-C6C04F305932\"/>\r\n" + 
				"      <Param name=\"Locking\" value=\"N\"/>\r\n" + 
				"    </additional_info>\r\n" + 
				"  </DeviceInfo>\r\n" + 
				"  <Skey ci=\"20221021\">Hs4HmzzD4aSLyXZgUMRW9DaqHmPwQW+n8lWBdL6Uam7HJbG/IEtc2HAsCaI0ZtA3BnMkrAf09TvoNqt2pHCQqkTFNk7ge790rNlXMUMxJTKOBxUTX1KK213f2ZGKfwjrRHIJzcN7E49t57xrx//LFdXJKNB0CqQ2Cj+4CDXRqVGERgviMlkXMruRUaHKETAkW52q/uWFK69oTjzB40ScMMH7MCDVsEF2RaIL9amnyIP+74/1UKuOQ2I2Dp6C7AxQxCFovQtfdCIDNd4przltNfGxy9xNR4eCi8+XHyngCZOqhi1kpanpKSlcvnKz+0Z+vJRuHqJ+YVYuzHieBcsCGw==</Skey>\r\n" + 
				"  <Hmac>dE5zy7hyyJq4oxzwrYnbMYvBtvN/sYpg9riXTWO+VP1ELXW6qhmOr6Hv4ywsnNzT</Hmac>\r\n" + 
				"  <Data type=\"X\">MjAyMi0wMi0yNFQxOToxOTozOZ7z+Msv5zs7MOqXjYB4AOjdsrsmNPJiQiHBWZOJdRRXTuHcgd/VVCeWebMC0f1HdL4liZDu5tzx5SN7O+MANrQoumLWDgtTNzm6woadq1JZKwdl0D0tyuNUp93AV/UsOlVfVn3oq2YP7r23xAkS07x4Yfzx9ZkBzWoyig0ktf//SGE5pk5GYR1BFGzLQt8R6Hvqu+xlakNUo5f18T/fI7rkpP4TA8//Kq0cxZvqCix05DcTHhnsaBJuzg6csXDfA81okRruH4T4bCTa6xatOkx/9thla0vU+nFbx1dEdCZXj207OKOVnpwpO87C1jEEV1Av4Ii77C2xg+AD0bNqwr8p2a7muPa5GNMwAPuhBjz5JfFp/Lc4vb53WCZ+lFUMpa7NFzidmF2GZi7Y1y5/HyewS3g5DikYC058Drj13z6aRNsNXf8qR28uq97LAObLjkNBo8T9uLrUwh+nBFsrvAJN2SNsUebf1WjHo14QlVvkY+iAMPRKriB2hUGvJH649bUP29WWVrmlpS1eJ8OAjVH/Qrwi1EbZX0ki6pMgE53X5jjCu7azccDD1wiz5yuQ4POZ9UbmC9x5eComnfYOWkgB/VTBJkc9X49pc3fHc5+sRDvO/9mgJY/P1R5L51P7csao+UW01ovr7UfYPZnrhfuNEleH97svIcykuWsGAdM3JpkVa6TFNQu3LkRB2BJ/pg8CZjU1fBhRp1AgAUvqAdUQuTmu7DJzv3cBRFn0+6bsYQp6eFMW8HWwEbEFBwA72wGLOlefpqaFbZDVQmktLAxsSeIJP0fiRQ49Q78miANrGPpXuRxTvxK3NapDaVXeRXh5NvftZPI+ekYyfzqWHywiZXcc6F1fZiJ+F/6oM6kCtIUT/2FiNgrsb4azyANMZP7G0JfUd/KFKkK7xF+jmb+XeQgq1AtHOKikVR+Nn6L9KOMaD2I5w/zCfmb6vTswqqKBQ+fS0P047IVJhiRPJKhKwjatLNtOHIgchzWAz607sFvow79wZXiuFonil4yKkHWQVt/sxVGWgSRv7UuFZ0mVHVYZNz5J8lDBKmcIYHkOH6xoGo8GfJbk7ywi+GgxrR6QNO/psJuagg8WFvs62UbU8h2b/J4np0XIOCcvYpg=</Data>\r\n" + 
				"</PidData>";
        //System.out.println(StringEscapeUtils.unescapeJava(test));
	}
	
	
}

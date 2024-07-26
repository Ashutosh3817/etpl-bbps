package com.etpl.bbps.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.etpl.bbps.common.CommonUtils;
import com.etpl.bbps.common.EtplConstant;
import com.etpl.bbps.constant.ResponseModel;

//@Component
//@Aspect
public class TokenValidation {

	Logger logg = LoggerFactory.getLogger(TokenValidation.class);
	
	@Value("${VALIDATE_TOKEN}")
	private String tokenurl;

	//@Value("${VALIDATE_URL}")
	//private String validateUrl;

	// @Autowired // RestTemplate restTemplate;

	RestTemplate restTemplate = new RestTemplate();

	@Pointcut("within(com.etpl.bbps.controller..*)")
	private void myPointcut() {
	}

	@SuppressWarnings("unchecked")
	  
	@Around("myPointcut()")
	public ResponseEntity<Object> applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		try {
			
			HttpServletRequest httpservletReq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			TokenRequest request = new TokenRequest();
			CommonUtils utils = new CommonUtils();
			request.setToken(utils.getToken(httpservletReq));
			HttpEntity<TokenRequest> re = new HttpEntity<>(request);
			TokenResponse tokenResponse = restTemplate.exchange(tokenurl, HttpMethod.POST, re, TokenResponse.class)
					.getBody();
			if (tokenResponse != null && tokenResponse.getStatus().contentEquals("True")) {
				//if (checkRoleBasedAuthorization(httpservletReq, tokenResponse)) {
					httpservletReq.setAttribute(EtplConstant.TOKEN_RESPONSE, tokenResponse);
					return (ResponseEntity<Object>) pjp.proceed();
				//}
			}
			return ResponseEntity.ok().body(new ResponseModel(300,EtplConstant.UNAUTHORIZED_ACCESS));
		} catch (Exception e) {
			logg.error(e.getMessage(), e);
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseModel(300,EtplConstant.UNAUTHORIZED_ACCESS));
		}

	}


	/*public boolean checkRoleBasedAuthorization(HttpServletRequest httpservletReq, TokenResponse tokenResponse) {
		Map<String, String> req = new HashMap<>();
		Map<String, String> pathVariableMap = (Map<String, String>) httpservletReq
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String url = removePathVariablesFromUrl(httpservletReq.getRequestURI(), pathVariableMap);
		System.out.println("path after removal" + url);
		req.put(EtplConstant.URL, url);
		req.put(EtplConstant.CURRENT_ROLE, tokenResponse.getUserrole());
		req.put(EtplConstant.REQUEST_TYPE, httpservletReq.getMethod());
		HttpEntity<Map<String, String>> request = new HttpEntity<>(req);
		RestTemplate rest = new RestTemplate();

		@SuppressWarnings("unchecked")
		Map<String, String> response = restTemplate.exchange(validateUrl, HttpMethod.POST, request, Map.class)
				.getBody();
		if (response != null && response.get(EtplConstant.AUTHORIZED).equalsIgnoreCase("True")) {
			return true;
		}
		return false;
	}

	public String removePathVariablesFromUrl(String url, Map<String, String> map) {
		for (Map.Entry<String, String> entryset : map.entrySet()) {
			String s = "/" + entryset.getValue();
			url = url.replaceFirst(s, "");
		}
		return url;
	}*/
	 
}

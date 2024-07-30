package com.etpl.bbps.controller;

import java.util.List;

import org.apache.poi.poifs.crypt.Decryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etpl.bbps.model.dto.BillerInfoRequestDto;
import com.etpl.bbps.model.dto.BillerInfoResponseDTO;
import com.etpl.bbps.service.BillerService;

@RestController
@RequestMapping("/api/billers")
public class BillerInfoController {
	
	@Autowired
	private BillerService billerService;
	
	@PostMapping("/info")
	public ResponseEntity<BillerInfoResponseDTO> getBillerInfo(@RequestParam(required=false) List<String> billerIds){
		BillerInfoRequestDto requestDto = new BillerInfoRequestDto();
		requestDto.setBillerIds(billerIds);
		
		BillerInfoResponseDTO response = billerService.getBillerInfo(requestDto);
		
		if("000".equals(response.getResponseCode())) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
}

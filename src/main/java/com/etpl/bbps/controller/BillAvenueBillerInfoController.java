package com.etpl.bbps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etpl.bbps.service.BillAvenueBillerInfoService;
@RestController
@RequestMapping("/myapp/api/addData")
public class BillAvenueBillerInfoController {
    @Autowired
    private BillAvenueBillerInfoService billAvenueBillerInfoService;

    @PostMapping("/save")
    public ResponseEntity<String> saveBillerInfo(@RequestBody String xmlData) {
        try {
            billAvenueBillerInfoService.saveBillerInfo(xmlData);
            return new ResponseEntity<>("Biller information saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save biller information: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

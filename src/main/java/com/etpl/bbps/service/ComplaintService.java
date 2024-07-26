package com.etpl.bbps.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.etpl.bbps.model.Complaint;
import com.etpl.bbps.repository.ComplaintRepository;

@Transactional
@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository compRepo;

	public Complaint saveComplaint(Complaint comp) {
		return compRepo.save(comp);
	}

	public Complaint getCompDtlByBBPSRefNo(String bbpsRefNo) {
		return compRepo.findByBbpsRefNo(bbpsRefNo);
	}
	
	public Complaint getCompDtlByPaymentId(String paymentId) {
		return compRepo.findByPaymentId(paymentId);
	}

	public Object existsByBbpsRefNo(String bbpsRefNo) {
		return compRepo.existsByBbpsRefNo(bbpsRefNo);
	}
	
	public Object existsByPaymentId(String paymentId) {
		return compRepo.existsByPaymentId(paymentId);
	}

	public Map<String, Object> getComplaintList(Map<String, String> map, HttpServletRequest req) {
			
			Pageable pagingSort = PageRequest.of(Integer.parseInt(map.get("pageNo")), Integer.parseInt(map.get("pageSize")));
			map = validate(map);
			Page<Complaint> pgList;
			pgList = compRepo.getComplaintList(map.get("mobileNo"),map.get("fromDate"),map.get("toDate"),map.get("paymentId"),map.get("agentId"),pagingSort);
			 Map<String, Object> response = new HashMap<>();
				response.put("data", pgList.getContent());
				response.put("currentPage", pgList.getNumber());
				response.put("totalItems", pgList.getTotalElements());
				response.put("totalPages", pgList.getTotalPages());
			return response;
	}
	
	
	private Map<String, String> validate(Map<String, String> map) {
		
		if(map.containsKey("mobileNo"))
		{
			if(null==map.get("mobileNo"))
			map.put("mobileNo", "%");
		}
		else
		{
			map.put("mobileNo", "%");
		}
		
		if(map.containsKey("fromDate"))
		{
			if(null==map.get("fromDate"))
			map.put("fromDate", "2001-01-01");
		}
		else
		{
			map.put("fromDate", "2001-01-01");
		}
		
		if(map.containsKey("toDate"))
		{
			if(null==map.get("toDate"))
			map.put("toDate", "2080-01-01");
		}
		else
		{
			map.put("toDate", "2080-01-01");
		}
		
		if(map.containsKey("paymentId"))
		{
			if(null==map.get("paymentId"))
			map.put("paymentId", "%");
		}
		else
		{
			map.put("paymentId", "%");
		}
		
		if(map.containsKey("agentId"))
		{
			if(null==map.get("agentId"))
			map.put("agentId", "%");
		}
		else
		{
			map.put("agentId", "%");
		}
		
		return map;
	}
}

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

import com.etpl.bbps.model.BbpsTransaction;
import com.etpl.bbps.model.Complaint;
import com.etpl.bbps.repository.BbpsTransactionRepository;
import com.etpl.bbps.repository.ComplaintRepository;

@Transactional
@Service
public class BbpsTransactionService {

    @Autowired
    private BbpsTransactionRepository trxRepo;

	
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
		
		if(map.containsKey("billerCategory"))
		{
			if(null==map.get("billerCategory"))
			map.put("billerCategory", "%");
		}
		else
		{
			map.put("billerCategory", "%");
		}
		
		if(map.containsKey("billerStatus"))
		{
			if(null==map.get("billerStatus"))
			map.put("billerStatus", "%");
		}
		else
		{
			map.put("billerStatus", "%");
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
		
		if(map.containsKey("bbpsRefNo"))
		{
			if(null==map.get("bbpsRefNo"))
			map.put("bbpsRefNo", "%");
		}
		else
		{
			map.put("bbpsRefNo", "%");
		}
		
		if(map.containsKey("sourceRefNo"))
		{
			if(null==map.get("sourceRefNo"))
			map.put("sourceRefNo", "%");
		}
		else
		{
			map.put("sourceRefNo", "%");
		}
		
		
		return map;
	}


	public BbpsTransaction save(BbpsTransaction trx) {
		return trxRepo.save(trx);
	}


	public Map<String, Object> getTransactionHistoryList(Map<String, String> map, HttpServletRequest req) {
		Pageable pagingSort = PageRequest.of(Integer.parseInt(map.get("pageNo")), Integer.parseInt(map.get("pageSize")));
		map = validate(map);
		Page<BbpsTransaction> pgList;
		pgList = trxRepo.getTransactionHistoryList(map.get("mobileNo"),map.get("fromDate"),map.get("toDate"),map.get("bbpsRefNo"),map.get("billerCategory"),map.get("billerStatus"),map.get("sourceRefNo"),pagingSort);
		 Map<String, Object> response = new HashMap<>();
			response.put("data", pgList.getContent());
			response.put("currentPage", pgList.getNumber());
			response.put("totalItems", pgList.getTotalElements());
			response.put("totalPages", pgList.getTotalPages());
		return response;
	}


	public BbpsTransaction getTransactionHistoryById(Long id) {
		// TODO Auto-generated method stub
		return trxRepo.findById(id).get();
	}
}

package com.etpl.bbps.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etpl.bbps.common.CommonUtils;
import com.etpl.bbps.model.BillerModel;
import com.etpl.bbps.repository.BillerRepository;

@Transactional
@Service
public class BbpsService {

    @Autowired
    private BillerRepository billerRepo;
    

public static void traceId() {

	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int day = cal.get(Calendar.DAY_OF_MONTH);
	int hh = cal.get(Calendar.HOUR);
	int mi = cal.get(Calendar.MINUTE);
	int sc = cal.get(Calendar.SECOND);
	System.out.println(year);
	System.out.println(month);
	System.out.println(day);
	System.out.println(hh);
	System.out.println(mi);
	System.out.println(sc);
}
	
public static void main(String[] args) throws Exception {

	System.out.println("BD Trace Id is ::::::::: " + CommonUtils.getBdTraceId(20));
	System.out.println("BD Timestamp Id is ::::::::: " + CommonUtils.getBdTimeStamp());

}

public void saveBiller(BillerModel model) {
	billerRepo.save(model);
}

public List<BillerModel> findByBillerCategory(String billerCategory) {
	return billerRepo.findByBillerCategory(billerCategory);
}

public BillerModel findByBillerId(String billerId) {
	return billerRepo.findByBillerId(billerId);
}

public void deleteAll() {
	

	billerRepo.deleteAll();

}

public List<Map<String, String>> getBillerDtl(String billerCategory) {
	return billerRepo.getBillerDtl(billerCategory);
}

public List<Map<String, String>> getBillerCityList(String billerCategory) {
	return billerRepo.getBillerCityList(billerCategory);
}

public List<BillerModel> getEducationBillerList(String city) {
	return billerRepo.getEducationBillerList(city);
}

public List<Map<String, String>> getAllBillerCategoryName() {
	return billerRepo.getAllBillerCategory();
}

}

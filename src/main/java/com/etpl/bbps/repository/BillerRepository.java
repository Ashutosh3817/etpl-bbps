package com.etpl.bbps.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.etpl.bbps.model.BillerModel;

@Component
@Repository
public interface BillerRepository extends JpaRepository<BillerModel,Long> {

	BillerModel findByBillerId(String billerId);
	
	// select id,BILLER_CATEGORY,BILLER_ID,biller_name,IS_BILLER_BBPS from tt_biller Where BILLER_CATEGORY=?1
	
	@Query(
            value = "select id,BILLER_CATEGORY,BILLER_ID,biller_name,IS_BILLER_BBPS,BILLER_STATUS,BILLER_LOGO from tt_biller Where BILLER_CATEGORY=?1",
            nativeQuery = true
    )
	List<Map<String, String>> getBillerDtl(String billerCategory);
	

	@Query(
            value = "select BILLER_REG_CITY from tt_biller where BILLER_CATEGORY=?1 group by BILLER_REG_CITY",
            nativeQuery = true
    )
	List<Map<String, String>> getBillerCityList(String billerCategory);
	
	@Query(
            value = "select BILLER_CATEGORY from tt_biller group by BILLER_CATEGORY",
            nativeQuery = true
    )
	List<Map<String, String>> getAllBillerCategory();
	
	@Query(
            value = "select * from tt_biller where BILLER_REG_CITY=?1 and BILLER_CATEGORY='Education'",
            nativeQuery = true
    )
	List<BillerModel> getEducationBillerList(String city);

	
	List<BillerModel> findByBillerCategory(String billerCategory);
	
	@Transactional
	@Modifying
	@Query(
            value = "CALL truncate_biller_tables(); ",
            nativeQuery = true
    )
    void deleteAll();
	
}
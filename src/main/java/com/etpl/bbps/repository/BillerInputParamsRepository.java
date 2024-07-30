package com.etpl.bbps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.etpl.bbps.model.BillerInputParams;

public interface BillerInputParamsRepository extends JpaRepository<BillerInputParams, Long>{
	
//	 @Query("SELECT bip FROM BillerInputParam bip")
//	    List<BillerInputParams> findAllParams();
}

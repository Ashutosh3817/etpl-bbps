package com.etpl.bbps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etpl.bbps.model.BillerInfo;
@Repository
public interface BillerInfoRepository extends JpaRepository<BillerInfo, Long>{
	//for fetching all the billerinfo with the particular billerInputParams
	 @Query("SELECT b FROM BillerInfo b LEFT JOIN FETCH b.billerInputParams")
	 List<BillerInfo> findAll();

	 @Query("SELECT b FROM BillerInfo b LEFT JOIN FETCH b.billerInputParams WHERE b.billerId = :billerId")
	  Optional<BillerInfo> findById(@Param("billerId") String billerId);
}

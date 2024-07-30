package com.etpl.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etpl.bbps.model.RechargePlan;

@Repository
public interface RechargePlanRepository extends JpaRepository<RechargePlan,Long>{
	
}

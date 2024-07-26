package com.etpl.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.etpl.bbps.model.Complaint;
import com.etpl.bbps.model.OperatorModel;

@Component
@Repository
public interface OperatorSeriesRepository extends JpaRepository<OperatorModel,Long> {

	Complaint findByBillerId(String billerId);

}
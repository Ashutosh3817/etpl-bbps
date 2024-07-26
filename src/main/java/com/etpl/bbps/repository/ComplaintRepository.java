package com.etpl.bbps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.etpl.bbps.model.Complaint;

@Component
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {

	Complaint findByBillerId(String billerId);
	Complaint findByBbpsRefNo(String bbpsRefNo);
	Complaint findByPaymentId(String paymentId);
	List<Complaint> findByCustomerMobile(String mobile);
	List<Complaint> findByAgentId(String agentId);
	Boolean existsByBbpsRefNo(String bbpsRefNo);
	Boolean existsByPaymentId(String paymentId);
	
	@Query(value = "select c.* from tl_bbps_complaint c where c.CUSTOMER_MOBILE like ?1 and date(c.CREATED_ON)>=?2 and date(c.CREATED_ON)<=?3 and c.PAYMENT_ID like ?4 and c.AGENT_ID like ?5 order by c.updated_on desc",
			countQuery="SELECT COUNT(*) from tl_bbps_complaint c where c.CUSTOMER_MOBILE like ?1 and date(c.CREATED_ON)>=?2 and date(c.CREATED_ON)<=?3 and c.PAYMENT_ID like ?4 and c.AGENT_ID like ?5 order by c.updated_on desc",nativeQuery = true)
	Page<Complaint> getComplaintList(String mobileNo, String fromDate, String toDate, String paymentId,String agentId, Pageable pagingSort);
	
}
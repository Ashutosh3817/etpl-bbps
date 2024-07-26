package com.etpl.bbps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.etpl.bbps.model.BbpsTransaction;

@Component
@Repository
public interface BbpsTransactionRepository extends JpaRepository<BbpsTransaction,Long> {

	@Query(value = "select t.* from tl_bbps_transaction t where ifnull(t.CUSTOMER_MOBILE,'') like ?1 and date(t.CREATED_ON)>=?2 and date(t.CREATED_ON)<=?3 and ifnull(t.BBPS_REF_NO,'') like ?4 and t.BILLER_CATEGORY like ?5 and t.BILLER_STATUS like ?6 and t.SOURCE_REF_NO like ?7 order by t.updated_on desc",
			countQuery="SELECT COUNT(*) from tl_bbps_transaction t where ifnull(t.CUSTOMER_MOBILE,'') like ?1 and date(t.CREATED_ON)>=?2 and date(t.CREATED_ON)<=?3 and ifnull(t.BBPS_REF_NO,'') like ?4 and t.BILLER_CATEGORY like ?5 and t.BILLER_STATUS like ?6 and t.SOURCE_REF_NO like ?7 order by t.updated_on desc",nativeQuery = true)
	Page<BbpsTransaction> getTransactionHistoryList(String mobileNo, String fromDate, String toDate, String bbpsRefNo,String billerCategory, String billerStatus, String sourceRefNo ,Pageable pagingSort);
	
	@Query(value = "select a.* from tl_bbps_transaction a where a.PAYMENT_STATUS not in('FAIL','FAILED') and date(a.CREATED_ON)<date(CURDATE()) and date(a.CREATED_ON)>=date(CURDATE() - INTERVAL 1 day)",nativeQuery = true)
	List<BbpsTransaction> getReconFileList();
	
	@Query(value = "select a.* from tl_bbps_transaction a where a.BILLER_STATUS in('PENDING')",nativeQuery = true)
	List<BbpsTransaction> getPendingTransactionList();

	BbpsTransaction findByCustomerId(String customerId);
	
}
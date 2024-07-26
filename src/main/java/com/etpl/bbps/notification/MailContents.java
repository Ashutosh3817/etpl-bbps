package com.etpl.bbps.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailContents {
	private static final Logger logger = LogManager.getLogger(MailContents.class);

	
	/*
	 * public static EmailNotification submitMailToUser(TokenResponse user,
	 * LocalConveyanceModel model) {
	 * logger.debug("Local Conveyance submitMailToUser:Start"); EmailNotification
	 * email = new EmailNotification();
	 * email.setSubject("Local Conveyance Applied"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullName()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance No. "+model.getTrxNo()
	 * +" has been submitted to your direct superior. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance submitMailToUser:End"); return email; }
	 * 
	 * public static EmailNotification submitMailToApprover(UserModel user,
	 * LocalConveyanceModel model) {
	 * logger.debug("Local Conveyance submitMailToApprover :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Request Received"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("You have received a Local Conveyance No. "+model.getTrxNo()
	 * +" request for Approval. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance submitMailToApprover :End"); return email; }
	 * 
	 * public static EmailNotification approveHodMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance approveHodMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved by HOD"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your reimbursement application No. "
	 * +trxNo+" has been approved by your direct superior and now will be sent to Finance. <br/><br/>"
	 * ); sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveHodMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification approveHRMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance approveHRMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved by HR"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your reimbursement application No. "
	 * +trxNo+" has been approved by your HR and now will be sent to Finance. <br/><br/>"
	 * ); sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveHRMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification submitMailToHR(UserModel uModel, WorkFlowDto
	 * model) { logger.debug("Local Conveyance submitMailToHR :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Request Received"); String [] sendTo =
	 * {uModel.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb =
	 * new StringBuilder();
	 * sb.append("Dear ").append(uModel.getFullname()).append(",<br/><br/>");
	 * sb.append("You have received a Local Conveyance No. "+model.getTrxNo()
	 * +" request for Approval. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance submitMailToHR :End"); return email; }
	 * 
	 * 
	 * public static EmailNotification approveFinanceOneMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance approveFinanceOneMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+" has been approved by Finance and now will be sent to Finance Director. <br/><br/>"
	 * ); sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveFinanceOneMailToUser :End"); return
	 * email; }
	 * 
	 * 
	 * public static EmailNotification approveFinanceDirMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance approveFinanceDirMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+" has been approved by Finance Director and now will be sent to President Director. <br/><br/>"
	 * ); sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveFinanceDirMailToUser :End"); return
	 * email; }
	 * 
	 * 
	 * public static EmailNotification approvePrecidentMailToUser(UserModel user,
	 * String trxNo) { logger.debug("Local Conveyance approveRMMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+" has been approved by Finance and now will be sent to President Director. <br/><br/>"
	 * ); sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveRMMailToUser :End"); return email; }
	 * 
	 * 
	 * public static EmailNotification approveMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance approveMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Approved"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+" has been approved. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance approveMailToUserr :End"); return email; }
	 * 
	 * public static EmailNotification rejectHodMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance rejectRMMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Rejected"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been rejected by your direct superior. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance rejectRMMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification rejectHRMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance rejectHRMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Rejected"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been rejected by your HR. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance rejectHRMailToUser :End"); return email; }
	 * 
	 * 
	 * public static EmailNotification rejectFinanceMailToUser(UserModel user,
	 * String trxNo) { logger.debug("Local Conveyance rejectRMMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Rejected"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been rejected by your Finance. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance rejectRMMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification rejectFinanceDirMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance rejectFinanceDirMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Rejected"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been rejected by your Finance Director. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance rejectFinanceDirMailToUser :End"); return
	 * email; }
	 * 
	 * public static EmailNotification rejectPrecidentMailToUser(UserModel user,
	 * String trxNo) { logger.debug("Local Conveyance rejectRMMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Rejected"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been rejected by your President Director. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance rejectRMMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification submitMailToFinanceOne(UserModel uModel,
	 * WorkFlowDto model) {
	 * logger.debug("Local Conveyance submitMailToApprover :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Request Received"); String [] sendTo =
	 * {uModel.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb =
	 * new StringBuilder();
	 * sb.append("Dear ").append(uModel.getFullname()).append(",<br/><br/>");
	 * sb.append("You have received a Local Conveyance No. "+model.getTrxNo()
	 * +" request for Approval. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance submitMailToFinanceOne :End"); return email; }
	 * 
	 * public static EmailNotification submitMailToFinanceDir(UserModel uModel,
	 * WorkFlowDto model) {
	 * logger.debug("Local Conveyance submitMailToApprover :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Request Received"); String [] sendTo =
	 * {uModel.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb =
	 * new StringBuilder();
	 * sb.append("Dear ").append(uModel.getFullname()).append(",<br/><br/>");
	 * sb.append("You have received a Local Conveyance No. "+model.getTrxNo()
	 * +" request for Approval. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance submitMailToFinanceOne :End"); return email; }
	 * 
	 * 
	 * public static EmailNotification returnHodMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance returnHodMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Returned"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been returned by your direct superior. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance returnHodMailToUser :End"); return email; }
	 * 
	 * public static EmailNotification returnHRMailToUser(UserModel user, String
	 * trxNo) { logger.debug("Local Conveyance returnHRMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Returned"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been returned by your HR. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance returnHRMailToUser :End"); return email; }
	 * 
	 * 
	 * public static EmailNotification returnFinanceOneMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance returnFinanceOneMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Returned"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been returned by Finance Department. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance returnFinanceOneMailToUser :End"); return
	 * email; }
	 * 
	 * public static EmailNotification returnFinanceDirMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance returnFinanceDirMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Returned"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been returned by Finance Director. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance returnFinanceDirMailToUser :End"); return
	 * email; }
	 * 
	 * public static EmailNotification returnPrecidentDirMailToUser(UserModel user,
	 * String trxNo) {
	 * logger.debug("Local Conveyance returnPrecidentDirMailToUser :Start");
	 * EmailNotification email = new EmailNotification();
	 * email.setSubject("Local Conveyance Returned"); String [] sendTo =
	 * {user.getOfficialEmailId()}; email.setSendTo(sendTo); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Dear ").append(user.getFullname()).append(",<br/><br/>");
	 * sb.append("Your Local Conveyance application No. "
	 * +trxNo+"  has been returned by Precident Director. <br/><br/>");
	 * sb.append("Regards: <br/>").append("Bata Indonesia <br/><br/><br/>");
	 * email.setBodyContent(sb.toString());
	 * logger.debug("Local Conveyance returnPrecidentDirMailToUser :End"); return
	 * email; }
	 */
}

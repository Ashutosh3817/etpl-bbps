package com.etpl.bbps.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="billerInfoResponse")
public class BillerInfoResponseDTO{
	
	private String responseCode;
	private List<BillerInfoDto> biller;
	private List<ErrorInfo> errorInfo;
	
	@XmlElement
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@XmlElement
	public List<BillerInfoDto> getBillers() {
		return biller;
	}
	public void setBillers(List<BillerInfoDto> biller) {
		this.biller = biller;
	}
	
	//inner class to represent each billerInfo
	public static class BillerInfoDto{
		private String billerId;
	    private String billerName;
	    private String billerCategory;
	    private boolean billerAdhoc;
	    private String billerCoverage;
	    private String billerFetchRequirement;
	    private String billerPaymentExactness;
	    private String billerSupportBillValidation;
	    private List<BillerInfoParamsDto> billerInputParams;
	    private String billerAmountOptions;
	    private List<String> billerPaymentModes;
	    private String billerDescription;
	    private String rechargeAmountInValidationRequest;
	    
	    @XmlElement
		public String getBillerId() {
			return billerId;
		}
		public void setBillerId(String billerId) {
			this.billerId = billerId;
		}
		@XmlElement
		public String getBillerName() {
			return billerName;
		}
		public void setBillerName(String billerName) {
			this.billerName = billerName;
		}
		@XmlElement
		public String getBillerCategory() {
			return billerCategory;
		}
		public void setBillerCategory(String billerCategory) {
			this.billerCategory = billerCategory;
		}
		@XmlElement
		public boolean isBillerAdhoc() {
			return billerAdhoc;
		}
		public void setBillerAdhoc(boolean billerAdhoc) {
			this.billerAdhoc = billerAdhoc;
		}
		@XmlElement
		public String getBillerCoverage() {
			return billerCoverage;
		}
		public void setBillerCoverage(String billerCoverage) {
			this.billerCoverage = billerCoverage;
		}
		@XmlElement
		public String getBillerFetchRequirement() {
			return billerFetchRequirement;
		}
		public void setBillerFetchRequirement(String billerFetchRequirement) {
			this.billerFetchRequirement = billerFetchRequirement;
		}
		@XmlElement
		public String getBillerPaymentExactness() {
			return billerPaymentExactness;
		}
		public void setBillerPaymentExactness(String billerPaymentExactness) {
			this.billerPaymentExactness = billerPaymentExactness;
		}
		@XmlElement
		public String getBillerSupportBillValidation() {
			return billerSupportBillValidation;
		}
		public void setBillerSupportBillValidation(String billerSupportBillValidation) {
			this.billerSupportBillValidation = billerSupportBillValidation;
		}
		@XmlElement
		public List<BillerInfoParamsDto> getBillerInputParams() {
			return billerInputParams;
		}
		public void setBillerInputParams(List<BillerInfoParamsDto> billerInputParams) {
			this.billerInputParams = billerInputParams;
		}
		@XmlElement
		public String getBillerAmountOptions() {
			return billerAmountOptions;
		}
		public void setBillerAmountOptions(String billerAmountOptions) {
			this.billerAmountOptions = billerAmountOptions;
		}
		@XmlElement
		public List<String> getBillerPaymentModes() {
			return billerPaymentModes;
		}
		public void setBillerPaymentModels(List<String> billerPaymentModes) {
			this.billerPaymentModes = billerPaymentModes;
		}
		@XmlElement
		public String getBillerDescription() {
			return billerDescription;
		}
		public void setBillerDescription(String billerDescription) {
			this.billerDescription = billerDescription;
		}
		@XmlElement
		public String getRechargeAmountInValidationRequest() {
			return rechargeAmountInValidationRequest;
		}
		public void setRechargeAmountInValidationRequest(String rechargeAmountInValidationRequest) {
			this.rechargeAmountInValidationRequest = rechargeAmountInValidationRequest;
		}
	}
	
	//inner class which represent billerInfoParam
	public static class BillerInfoParamsDto{
		  private String paramName;
		  private String dataType;
		  private boolean isOptional;
		  private int minLength;
		  private int maxLength;
		 
	    @XmlElement
		public String getParamName() {
			return paramName;
		}
		public void setParamName(String paramName) {
			this.paramName = paramName;
		}
		@XmlElement
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		@XmlElement
		public boolean isOptional() {
			return isOptional;
		}
		public void setOptional(boolean isOptional) {
			this.isOptional = isOptional;
		}
		@XmlElement
		public int getMinLength() {
			return minLength;
		}
		public void setMinLength(int minLength) {
			this.minLength = minLength;
		}
		@XmlElement
		public int getMaxLength() {
			return maxLength;
		}
		public void setMaxLength(int maxLength) {
			this.maxLength = maxLength;
		}
	}
	
	

    public static class ErrorInfo {
        private List<Error> error;

        @XmlElement(name = "error")
        public List<Error> getError() {
            return error;
        }

        public void setError(List<Error> error) {
            this.error = error;
        }

        public static class Error {
            private String errorCode;
            private String errorMessage;

            @XmlElement
            public String getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(String errorCode) {
                this.errorCode = errorCode;
            }

            @XmlElement
            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }
        }
    }
    @XmlElement(name = "errorInfo")
    public List<ErrorInfo> getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(List<ErrorInfo> errorInfo) {
        this.errorInfo = errorInfo;
    }
}





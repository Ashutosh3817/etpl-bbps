package com.etpl.bbps.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rechargePlanResponse")
public class RechargePlanResponseDTO {
    private String responseCode;
    private String responseReason;
    private RechargePlan rechargePlan;
    private ErrorInfo errorInfo;

    @XmlElement
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @XmlElement
    public String getResponseReason() {
        return responseReason;
    }

    public void setResponseReason(String responseReason) {
        this.responseReason = responseReason;
    }

    @XmlElement
    public RechargePlan getRechargePlan() {
        return rechargePlan;
    }

    public void setRechargePlan(RechargePlan rechargePlan) {
        this.rechargePlan = rechargePlan;
    }

    @XmlElement
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public static class RechargePlan {
        private List<RechargePlansDetails> rechargePlansDetails;

        @XmlElementWrapper(name = "rechargePlansDetails")
        @XmlElement(name = "rechargePlansDetails")
        public List<RechargePlansDetails> getRechargePlansDetails() {
            return rechargePlansDetails;
        }

        public void setRechargePlansDetails(List<RechargePlansDetails> rechargePlansDetails) {
            this.rechargePlansDetails = rechargePlansDetails;
        }
    }

    public static class RechargePlansDetails {
        private String amount;
        private String description;
        private String locationName;
        private String planName;
        private String serviceProviderName;
        private String talktime;
        private String validity;

        @XmlElement
        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @XmlElement
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlElement
        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        @XmlElement
        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        @XmlElement
        public String getServiceProviderName() {
            return serviceProviderName;
        }

        public void setServiceProviderName(String serviceProviderName) {
            this.serviceProviderName = serviceProviderName;
        }

        @XmlElement
        public String getTalktime() {
            return talktime;
        }

        public void setTalktime(String talktime) {
            this.talktime = talktime;
        }

        @XmlElement
        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
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
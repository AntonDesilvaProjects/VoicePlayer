package com.voiceplayer.common.googledrive.model;

import java.util.List;

public class Error {
    public static class ErrorDetail {
        private String locationType;
        private String domain;
        private String message;
        private String reason;
        private String location;

        public String getLocationType() {
            return locationType;
        }

        public ErrorDetail setLocationType(String locationType) {
            this.locationType = locationType;
            return this;
        }

        public String getDomain() {
            return domain;
        }

        public ErrorDetail setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public ErrorDetail setMessage(String message) {
            this.message = message;
            return this;
        }

        public String getReason() {
            return reason;
        }

        public ErrorDetail setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public String getLocation() {
            return location;
        }

        public ErrorDetail setLocation(String location) {
            this.location = location;
            return this;
        }
    }

    private int code;
    private String message;
    private List<ErrorDetail> errors;

    public int getCode() {
        return code;
    }

    public Error setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Error setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public Error setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
        return this;
    }
}

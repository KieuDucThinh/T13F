package com.entity;

import java.sql.Timestamp;
import java.util.Objects;


public class Customer implements java.io.Serializable{
    private String cId;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    private String cName;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    private String cPhone;

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    private String cEmail;

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    private Timestamp cAdddate;

    public Timestamp getcAdddate() {
        return cAdddate;
    }

    public void setcAdddate(Timestamp cAdddate) {
        this.cAdddate = cAdddate;
    }

    private String cAddress;

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(cId, customer.cId) && Objects.equals(cName, customer.cName) && Objects.equals(cPhone, customer.cPhone) && Objects.equals(cEmail, customer.cEmail) && Objects.equals(cAdddate, customer.cAdddate) && Objects.equals(cAddress, customer.cAddress);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(cId);
        result = 31 * result + Objects.hashCode(cName);
        result = 31 * result + Objects.hashCode(cPhone);
        result = 31 * result + Objects.hashCode(cEmail);
        result = 31 * result + Objects.hashCode(cAdddate);
        result = 31 * result + Objects.hashCode(cAddress);
        return result;
    }

    @Override
    public String toString() {
        return cId + " " + cName + " " + cPhone + " " + cEmail + " " + cAdddate + " " + cAddress;
    }
}

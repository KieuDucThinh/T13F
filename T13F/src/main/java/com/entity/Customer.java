package com.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Customer implements java.io.Serializable{

    private String cId;

    private String cName;

    private String cPhone;

    private String cEmail;

    private Timestamp cAdddate;

    private String cAddress;

    @ToString.Exclude
    private Set<GoodsDeliveryNote> goodsDeliveryNoteSet = new HashSet<GoodsDeliveryNote>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(cId, customer.cId) && Objects.equals(cName, customer.cName) && Objects.equals(cPhone, customer.cPhone) && Objects.equals(cEmail, customer.cEmail) && Objects.equals(cAddress, customer.cAddress);
    }

//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(cId);
//        result = 31 * result + Objects.hashCode(cName);
//        result = 31 * result + Objects.hashCode(cPhone);
//        result = 31 * result + Objects.hashCode(cEmail);
//        result = 31 * result + Objects.hashCode(cAdddate);
//        result = 31 * result + Objects.hashCode(cAddress);
//        return result;
//    }
}

package com.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoodsDeliveryNote implements Serializable {
    private String gdnId;
//    @Basic
//    @Column(name = "username")
//    private String username;
//    @Basic
//    @Column(name = "c_id")
//    private String cId;

    private String gdnAddress;

    private Date gdnDate;

    private BigDecimal totalDeliveryPrice;

    private String gdnDescription;

    @ToString.Exclude
    private Customer customer;

    @ToString.Exclude
    private User deliveryUser;

    @ToString.Exclude
    private Set<GdnDetail> gdnDetailsSet = new HashSet<>();
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        GoodsDeliveryNote that = (GoodsDeliveryNote) o;
//        return Objects.equals(gdnId, that.gdnId) && Objects.equals(username, that.username) && Objects.equals(cId, that.cId) && Objects.equals(gdnAddress, that.gdnAddress) && Objects.equals(totalDeliveryPrice, that.totalDeliveryPrice) && Objects.equals(gdnDescription, that.gdnDescription);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(gdnId);
//        result = 31 * result + Objects.hashCode(username);
//        result = 31 * result + Objects.hashCode(cId);
//        result = 31 * result + Objects.hashCode(gdnAddress);
//        result = 31 * result + Objects.hashCode(totalDeliveryPrice);
//        result = 31 * result + Objects.hashCode(gdnDescription);
//        return result;
//    }
}

package com.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoodsReceivedNote implements Serializable {
    private String grnId;

    private Timestamp grnDate;

    private Timestamp verifyDate;

    private BigDecimal totalReceivedPrice;

    private String grnDescription;

    @ToString.Exclude
    private Supplier supplier;

    @ToString.Exclude
    private User receivedUser;

    @ToString.Exclude
    private Set<GrnDetail> grnDetailsSet = new HashSet<>();

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        GoodsReceivedNote that = (GoodsReceivedNote) o;
//        return Objects.equals(grnId, that.grnId) && Objects.equals(username, that.username) && Objects.equals(supId, that.supId) && Objects.equals(grnDate, that.grnDate) && Objects.equals(verifyDate, that.verifyDate) && Objects.equals(totalReceivedPrice, that.totalReceivedPrice) && Objects.equals(grnDescription, that.grnDescription);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(grnId);
//        result = 31 * result + Objects.hashCode(username);
//        result = 31 * result + Objects.hashCode(supId);
//        result = 31 * result + Objects.hashCode(grnDate);
//        result = 31 * result + Objects.hashCode(verifyDate);
//        result = 31 * result + Objects.hashCode(totalReceivedPrice);
//        result = 31 * result + Objects.hashCode(grnDescription);
//        return result;
//    }
}

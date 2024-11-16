package com.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Supplier implements Serializable {

    private String supId;

    private String supName;

    private String supAddress;

    private String supPhone;

    private String supEmail;

    private Timestamp supAdddate;

    private String supDescription;

    @ToString.Exclude
    private Set<GoodsReceivedNote> goodsReceivedNoteSet = new HashSet<GoodsReceivedNote>();
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Supplier supplier = (Supplier) o;
//        return Objects.equals(supId, supplier.supId) && Objects.equals(supName, supplier.supName) && Objects.equals(supAddress, supplier.supAddress) && Objects.equals(supPhone, supplier.supPhone) && Objects.equals(supEmail, supplier.supEmail) && Objects.equals(supAdddate, supplier.supAdddate) && Objects.equals(supDescription, supplier.supDescription);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(supId);
//        result = 31 * result + Objects.hashCode(supName);
//        result = 31 * result + Objects.hashCode(supAddress);
//        result = 31 * result + Objects.hashCode(supPhone);
//        result = 31 * result + Objects.hashCode(supEmail);
//        result = 31 * result + Objects.hashCode(supAdddate);
//        result = 31 * result + Objects.hashCode(supDescription);
//        return result;
//    }
}

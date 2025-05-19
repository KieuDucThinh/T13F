package com.entity;

import com.generator.SupIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {
    @GeneratedValue(generator = "sup_id")
    @GenericGenerator(name = "sup_id", type = SupIDGenerator.class)
    @Id
    @Column(name = "sup_id")
    private String supId;
    @Basic
    @Column(name = "sup_name")
    private String supName;
    @Basic
    @Column(name = "sup_address")
    private String supAddress;
    @Basic
    @Column(name = "sup_phone")
    private String supPhone;
    @Basic
    @Column(name = "sup_email")
    private String supEmail;
    @Basic
    @Column(name = "sup_adddate")
    private Timestamp supAdddate;
    @Basic
    @Column(name = "sup_description")
    private String supDescription;

    @ToString.Exclude
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
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

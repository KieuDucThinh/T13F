package com.entity;

import com.generator.GrnIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "goods_received_note", schema = "fruitwarehousemanagement")
public class GoodsReceivedNote implements Serializable {
    @GeneratedValue(generator = "grn_id")
    @GenericGenerator(name = "grn_id", type = GrnIDGenerator.class)
    @Id
    @Column(name = "grn_id")
    private String grnId;

    @Basic
    @Column(name = "grn_date")
    private Timestamp grnDate;
    @Basic
    @Column(name = "verify_date")
    private Timestamp verifyDate;
    @Basic
    @Column(name = "total_received_price")
    private BigDecimal totalReceivedPrice;
    @Basic
    @Column(name = "grn_description")
    private String grnDescription;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sup_id")
    private Supplier supplier;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "username")
    private User receivedUser;

    @ToString.Exclude
    @OneToMany(mappedBy = "grnDetail", /*cascade = {CascadeType.REMOVE},*/ fetch = FetchType.EAGER)
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

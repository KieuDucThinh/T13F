package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@jakarta.persistence.Table(name = "grn_detail", schema = "fruitwarehousemanagement")
//@IdClass(com.entity.GrnDetailPK.class)
public class GrnDetail implements Serializable {
    @EmbeddedId
    private GrnDetailKey id;

    @Basic
    @Column(name = "quantity")
    private short quantity;

    @ToString.Exclude
    @ManyToOne
    @MapsId("skuGRN")
    @JoinColumn(name = "sku")
    private Goods goodsGRNDetail = new Goods();

    @ToString.Exclude
    @ManyToOne
    @MapsId("grnID")
    @JoinColumn(name = "grn_id")
    private GoodsReceivedNote grnDetail = new GoodsReceivedNote();
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrnDetail grnDetail = (GrnDetail) o;
        return quantity == grnDetail.quantity && Objects.equals(sku, grnDetail.sku) && Objects.equals(grnId, grnDetail.grnId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sku);
        result = 31 * result + Objects.hashCode(grnId);
        result = 31 * result + quantity;
        return result;
    }
     */
}

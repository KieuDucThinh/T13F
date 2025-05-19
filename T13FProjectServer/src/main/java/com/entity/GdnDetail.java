package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@jakarta.persistence.Table(name = "gdn_detail", schema = "fruitwarehousemanagement")
//@IdClass(com.entity.GdnDetailPK.class)
public class GdnDetail implements Serializable {
    @EmbeddedId
    private GdnDetailKey id;

    @Basic
    @Column(name = "quantity")
    private short quantity;

    @Basic
    @Column(name = "discount")
    private BigDecimal discount;

    @ToString.Exclude
    @ManyToOne
    @MapsId("skuGDN")
    @JoinColumn(name = "sku")
    private Goods goodsGDNDetail = new Goods();

    @ToString.Exclude
    @ManyToOne
    @MapsId("gdnID")
    @JoinColumn(name = "gdn_id")
    private GoodsDeliveryNote gdnDetail = new GoodsDeliveryNote();
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GdnDetail gdnDetail = (GdnDetail) o;
        return quantity == gdnDetail.quantity && Objects.equals(sku, gdnDetail.sku) && Objects.equals(gdnId, gdnDetail.gdnId) && Objects.equals(discount, gdnDetail.discount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sku);
        result = 31 * result + Objects.hashCode(gdnId);
        result = 31 * result + quantity;
        result = 31 * result + Objects.hashCode(discount);
        return result;
    }
     */
}

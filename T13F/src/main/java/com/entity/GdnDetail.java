package com.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GdnDetail implements Serializable {
    private GdnDetailKey id;

    private short quantity;

    private BigDecimal discount;

    @ToString.Exclude
    private Goods goodsGDNDetail = new Goods();

    @ToString.Exclude
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

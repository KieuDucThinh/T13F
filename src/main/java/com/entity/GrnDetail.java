package com.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@IdClass(com.entity.GrnDetailPK.class)
public class GrnDetail implements Serializable {

    private GrnDetailKey id;

    private short quantity;

    @ToString.Exclude
    private Goods goodsGRNDetail = new Goods();

    @ToString.Exclude
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

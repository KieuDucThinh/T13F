package com.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods implements Serializable {
    private String sku;
//    @Basic
//    @Column(name = "ft_id")
//    private String ftId;

    private String size;

    private String status;

    private BigDecimal receivedPrice;

    private BigDecimal deliveryPrice;

    private String original;

    private Date justRipeDate;

    private Date ripeDate;

    private Date overripDate;

    private Date expDate;

    private BigDecimal quantityInBox;

    private short availableQuantity;

    private short totalQuantity;

    @ToString.Exclude
    private FruitType goodsFruitType;

    @ToString.Exclude
    private Set<Position> positionSet = new HashSet<>();

    @ToString.Exclude
    private Set<GdnDetail> gdnDetailSet = new HashSet<>();

    @ToString.Exclude
    private Set<GrnDetail> grnDetailSet = new HashSet<>();
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;
        return availableQuantity == goods.availableQuantity && totalQuantity == goods.totalQuantity && Objects.equals(sku, goods.sku) && Objects.equals(ftId, goods.ftId) && Objects.equals(size, goods.size) && Objects.equals(status, goods.status) && Objects.equals(receivedPrice, goods.receivedPrice) && Objects.equals(deliveryPrice, goods.deliveryPrice) && Objects.equals(original, goods.original) && Objects.equals(justRipeDate, goods.justRipeDate) && Objects.equals(ripeDate, goods.ripeDate) && Objects.equals(overripDate, goods.overripDate) && Objects.equals(expDate, goods.expDate) && Objects.equals(quantityInBox, goods.quantityInBox);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sku);
        result = 31 * result + Objects.hashCode(ftId);
        result = 31 * result + Objects.hashCode(size);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(receivedPrice);
        result = 31 * result + Objects.hashCode(deliveryPrice);
        result = 31 * result + Objects.hashCode(original);
        result = 31 * result + Objects.hashCode(justRipeDate);
        result = 31 * result + Objects.hashCode(ripeDate);
        result = 31 * result + Objects.hashCode(overripDate);
        result = 31 * result + Objects.hashCode(expDate);
        result = 31 * result + Objects.hashCode(quantityInBox);
        result = 31 * result + availableQuantity;
        result = 31 * result + totalQuantity;
        return result;
    }
     */
}

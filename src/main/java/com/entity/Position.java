package com.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Position implements Serializable {
    private int posId;
//    @Basic
//    @Column(name = "ft_id")
//    private String ftId;
//    @Basic
//    @Column(name = "sku")
//    private String sku;

    private short row;

    private String col;

    private short stack;

    @ToString.Exclude
    private FruitType fruitType;

    @ToString.Exclude
    private Goods goodsPos;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Position position = (Position) o;
//        return row == position.row && stack == position.stack && Objects.equals(posId, position.posId) && Objects.equals(ftId, position.ftId) && Objects.equals(sku, position.sku) && Objects.equals(col, position.col);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(posId);
//        result = 31 * result + Objects.hashCode(ftId);
//        result = 31 * result + Objects.hashCode(sku);
//        result = 31 * result + row;
//        result = 31 * result + Objects.hashCode(col);
//        result = 31 * result + stack;
//        return result;
//    }
}

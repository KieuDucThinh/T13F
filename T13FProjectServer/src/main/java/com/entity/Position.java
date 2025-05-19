package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "position")
public class Position implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pos_id")
    private int posId;
//    @Basic
//    @Column(name = "ft_id")
//    private String ftId;
//    @Basic
//    @Column(name = "sku")
//    private String sku;
    @Basic
    @Column(name = "row")
    private short row;
    @Basic
    @Column(name = "col")
    private String col;
    @Basic
    @Column(name = "stack")
    private short stack;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ft_id")
    private FruitType fruitType;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sku")
    private Goods goodsPos;

    public String getPos(){
        return String.valueOf(this.col + this.row + "-" + stack);
    }

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

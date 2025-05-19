package com.entity;

import com.generator.SKUGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "goods")
public class Goods implements Serializable {
    @GeneratedValue(generator = "sku")
    @GenericGenerator(name = "sku", type = SKUGenerator.class)
    @Id
    @Column(name = "sku")
    private String sku;
//    @Basic
//    @Column(name = "ft_id")
//    private String ftId;
    @Basic
    @Column(name = "size")
    private String size;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "received_price")
    private BigDecimal receivedPrice;
    @Basic
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
    @Basic
    @Column(name = "original")
    private String original;
    @Basic
    @Column(name = "just_ripe_date")
    private Date justRipeDate;
    @Basic
    @Column(name = "ripe_date")
    private Date ripeDate;
    @Basic
    @Column(name = "overrip_date")
    private Date overripDate;
    @Basic
    @Column(name = "exp_date")
    private Date expDate;
    @Basic
    @Column(name = "quantity_in_box")
    private BigDecimal quantityInBox;
    @Basic
    @Column(name = "available_quantity")
    private short availableQuantity;
    @Basic
    @Column(name = "total_quantity")
    private short totalQuantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ft_id")
    private FruitType goodsFruitType;

    @ToString.Exclude
    @OneToMany(mappedBy = "goodsPos", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Position> positionSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "goodsGDNDetail", cascade = {/*CascadeType.DETACH, */CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<GdnDetail> gdnDetailSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "goodsGRNDetail", cascade = {/*CascadeType.DETACH, */CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<GrnDetail> grnDetailSet = new HashSet<>();

    /*
    public Goods(Goods goods){
        this.sku = goods.getSku();
        this.size = goods.getSize();
        this.status = goods.getStatus();
        this.receivedPrice = goods.getReceivedPrice();
        this.deliveryPrice = goods.getDeliveryPrice();
        this.original = goods.getOriginal();
        this.justRipeDate = goods.getJustRipeDate();
        this.ripeDate = goods.getRipeDate();
        this.overripDate = goods.getOverripDate();
        this.expDate = goods.getExpDate();
        this.quantityInBox = goods.getQuantityInBox();
        this.availableQuantity = goods.getAvailableQuantity();
        this.totalQuantity = goods.getTotalQuantity();
        this.goodsFruitType = goods.getGoodsFruitType();
        this.positionSet = goods.getPositionSet();
        this.gdnDetailSet = goods.getGdnDetailSet();
        this.grnDetailSet = goods.getGrnDetailSet();
        /*
        if(goods.getPositionSet() != null){
            this.positionSet.addAll(goods.getPositionSet());
        }
        if(goods.getGdnDetailSet() != null){
            this.gdnDetailSet.addAll(goods.getGdnDetailSet());
        }
        if(goods.getGrnDetailSet() != null){
            this.grnDetailSet.addAll(goods.getGrnDetailSet());
        }
    }
    */
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

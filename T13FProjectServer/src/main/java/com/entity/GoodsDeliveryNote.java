package com.entity;

import com.generator.GdnIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "goods_delivery_note", schema = "fruitwarehousemanagement")
public class GoodsDeliveryNote implements Serializable {
    @GeneratedValue(generator = "gdn_id")
    @GenericGenerator(name = "gdn_id", type = GdnIDGenerator.class)
    @Id
    @Column(name = "gdn_id")
    private String gdnId;
//    @Basic
//    @Column(name = "username")
//    private String username;
//    @Basic
//    @Column(name = "c_id")
//    private String cId;
    @Basic
    @Column(name = "gdn_address")
    private String gdnAddress;
    @Basic
    @Column(name = "gdn_date")
    private Date gdnDate;
    @Basic
    @Column(name = "total_delivery_price")
    private BigDecimal totalDeliveryPrice;
    @Basic
    @Column(name = "gdn_description")
    private String gdnDescription;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Customer customer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "username")
    private User deliveryUser;

    @ToString.Exclude
    @OneToMany(mappedBy = "gdnDetail", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<GdnDetail> gdnDetailsSet = new HashSet<>();
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        GoodsDeliveryNote that = (GoodsDeliveryNote) o;
//        return Objects.equals(gdnId, that.gdnId) && Objects.equals(username, that.username) && Objects.equals(cId, that.cId) && Objects.equals(gdnAddress, that.gdnAddress) && Objects.equals(totalDeliveryPrice, that.totalDeliveryPrice) && Objects.equals(gdnDescription, that.gdnDescription);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hashCode(gdnId);
//        result = 31 * result + Objects.hashCode(username);
//        result = 31 * result + Objects.hashCode(cId);
//        result = 31 * result + Objects.hashCode(gdnAddress);
//        result = 31 * result + Objects.hashCode(totalDeliveryPrice);
//        result = 31 * result + Objects.hashCode(gdnDescription);
//        return result;
//    }
}

package com.entity;

import com.generator.CIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
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
@Table(name = "customer")
public class Customer implements java.io.Serializable{
    @GeneratedValue(generator = "c_id")
    @GenericGenerator(name = "c_id", type = CIDGenerator.class)
    @Id
    @jakarta.persistence.Column(name = "c_id")
    private String cId;

    @Basic
    @Column(name = "c_name")
    private String cName;

    @Basic
    @Column(name = "c_phone")
    private String cPhone;

    @Basic
    @Column(name = "c_email")
    private String cEmail;

    @Basic
    @Column(name = "c_adddate")
    private Timestamp cAdddate;

    @Basic
    @Column(name = "c_address")
    private String cAddress;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GoodsDeliveryNote> goodsDeliveryNoteSet = new HashSet<GoodsDeliveryNote>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(cId, customer.cId) && Objects.equals(cName, customer.cName) && Objects.equals(cPhone, customer.cPhone) && Objects.equals(cEmail, customer.cEmail) && Objects.equals(cAddress, customer.cAddress);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(cId, cName, cPhone, cEmail, cAdddate, cAddress, goodsDeliveryNoteSet);
//    }

}

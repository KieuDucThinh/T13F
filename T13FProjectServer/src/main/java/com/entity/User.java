package com.entity;

import com.generator.SupIDGenerator;
import com.generator.UsernameGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Entity
@ToString
@Table(name = "user")
public class User implements Serializable {
//    @GeneratedValue(generator = "username")
//    @GenericGenerator(name = "username", type = UsernameGenerator.class)
    @Id
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "fullname")
    private String fullname;
    @Basic
    @Column(name = "bod")
    private Date bod;
    @Basic
    @Column(name = "gender")
    private boolean gender;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "citizen_id")
    private String citizenId;
    @Basic
    @Column(name = "hometown")
    private String hometown;
    @Basic
    @Column(name = "salary")
    private BigDecimal salary;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "status")
    private String status;

    @ToString.Exclude
    @OneToMany(mappedBy = "deliveryUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GoodsDeliveryNote> goodsDeliveryNoteSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "receivedUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GoodsReceivedNote> goodsReceivedNoteSet = new HashSet<>();

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return gender == user.gender && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(fullname, user.fullname) && Objects.equals(bod, user.bod) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(citizenId, user.citizenId) && Objects.equals(hometown, user.hometown) && Objects.equals(salary, user.salary) && Objects.equals(startDate, user.startDate) && Objects.equals(endDate, user.endDate) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(fullname);
        result = 31 * result + Objects.hashCode(bod);
        result = 31 * result + Boolean.hashCode(gender);
        result = 31 * result + Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(phone);
        result = 31 * result + Objects.hashCode(citizenId);
        result = 31 * result + Objects.hashCode(hometown);
        result = 31 * result + Objects.hashCode(salary);
        result = 31 * result + Objects.hashCode(startDate);
        result = 31 * result + Objects.hashCode(endDate);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
     */

}

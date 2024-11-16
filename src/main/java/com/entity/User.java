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
public class User implements Serializable {

    private String username;

    private String password;

    private String fullname;

    private Date bod;

    private boolean gender;

    private String address;

    private String phone;

    private String citizenId;

    private String hometown;

    private BigDecimal salary;

    private Date startDate;

    private Date endDate;

    private String status;

    @ToString.Exclude
    private Set<GoodsDeliveryNote> goodsDeliveryNoteSet = new HashSet<>();

    @ToString.Exclude
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

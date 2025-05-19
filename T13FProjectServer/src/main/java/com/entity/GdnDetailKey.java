package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class GdnDetailKey implements Serializable {
    @Column(name = "sku")
    private String skuGDN;

    @Column(name = "gdn_id")
    private String gdnID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GdnDetailKey that = (GdnDetailKey) o;
        return Objects.equals(skuGDN, that.skuGDN) && Objects.equals(gdnID, that.gdnID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuGDN, gdnID);
    }
}

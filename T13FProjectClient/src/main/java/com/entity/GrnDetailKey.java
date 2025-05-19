package com.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GrnDetailKey implements Serializable {
    private String skuGRN;

    private String grnID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrnDetailKey that = (GrnDetailKey) o;
        return Objects.equals(skuGRN, that.skuGRN) && Objects.equals(grnID, that.grnID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuGRN, grnID);
    }
}

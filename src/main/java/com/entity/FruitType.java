package com.entity;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FruitType implements Serializable {
    private String ftId;

    private String ftName;

    private String unitOfCalculation;

    private String ftDescription;

    private byte startSeason;

    private byte endSeason;

    private short timeToJustRipe;

    private short timeToRipe;

    private short timeToOverripe;

    private byte timeToDamaged;

    private byte temperature;

    @ToString.Exclude
    private Set<Position> pos = new HashSet<>();

    @ToString.Exclude
    private Set<Goods> goodsSet = new HashSet<>();

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FruitType fruitType = (FruitType) o;
        return startSeason == fruitType.startSeason && endSeason == fruitType.endSeason && timeToJustRipe == fruitType.timeToJustRipe && timeToRipe == fruitType.timeToRipe && timeToOverripe == fruitType.timeToOverripe && timeToDamaged == fruitType.timeToDamaged && temperature == fruitType.temperature && Objects.equals(ftId, fruitType.ftId) && Objects.equals(ftName, fruitType.ftName) && Objects.equals(unitOfCalculation, fruitType.unitOfCalculation) && Objects.equals(ftDescription, fruitType.ftDescription);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(ftId);
        result = 31 * result + Objects.hashCode(ftName);
        result = 31 * result + Objects.hashCode(unitOfCalculation);
        result = 31 * result + Objects.hashCode(ftDescription);
        result = 31 * result + startSeason;
        result = 31 * result + endSeason;
        result = 31 * result + timeToJustRipe;
        result = 31 * result + timeToRipe;
        result = 31 * result + timeToOverripe;
        result = 31 * result + timeToDamaged;
        result = 31 * result + temperature;
        return result;
    }
    */
}

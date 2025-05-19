package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@ToString
@jakarta.persistence.Table(name = "fruit_type", schema = "fruitwarehousemanagement")
public class FruitType implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "ft_id")
    private String ftId;

    @Basic
    @Column(name = "ft_name")
    private String ftName;

    @Basic
    @Column(name = "unit_of_calculation")
    private String unitOfCalculation;

    @Basic
    @Column(name = "ft_description")
    private String ftDescription;

    @Basic
    @Column(name = "start_season")
    private byte startSeason;

    @Basic
    @Column(name = "end_season")
    private byte endSeason;

    @Basic
    @Column(name = "time_to_just_ripe")
    private short timeToJustRipe;

    @Basic
    @Column(name = "time_to_ripe")
    private short timeToRipe;

    @Basic
    @Column(name = "time_to_overripe")
    private short timeToOverripe;

    @Basic
    @Column(name = "time_to_damaged")
    private byte timeToDamaged;

    @Basic
    @Column(name = "temperature")
    private byte temperature;

    @ToString.Exclude
    @OneToMany(mappedBy = "fruitType", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
//    @JoinColumn(name = "ft_id")
            //(mappedBy = "fruitType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //     (targetEntity = Position.class)
//    @JoinColumn(name = "pos_id")
    private Set<Position> pos = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "goodsFruitType", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Goods> goodsSet = new HashSet<>();

    @Override
    public String toString() {
        return this.getFtName();
    }
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

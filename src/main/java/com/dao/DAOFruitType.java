package com.dao;

import com.entity.FruitType;

import java.rmi.Remote;

public interface DAOFruitType extends Remote {
    public FruitType getFruitType(String ft_id);
}

package com.dao;

import com.entity.FruitType;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOFruitType extends Remote {
    public FruitType getFruitType(String ft_id) throws RemoteException;
    public ObservableList<FruitType> getAllFruitTypes() throws RemoteException;
}

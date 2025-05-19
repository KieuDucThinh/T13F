package com.dao;

import com.entity.FruitType;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOFruitType extends Remote {
    public FruitType getFruitType(String ft_id) throws RemoteException;
    public List<FruitType> getAllFruitTypes() throws RemoteException;
}

package com.dao;

import com.entity.Goods;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOGoods extends Remote {
    public Goods getGoods(String sku) throws RemoteException;
    public void clearExpGoods() throws RemoteException;
    public ObservableList<Goods> getGoodsListBySKU(String sku) throws RemoteException;

    public Goods getGoodsToDelivery(Goods goods) throws RemoteException;
}

package com.dao;

import com.entity.Goods;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOGoods extends Remote {
    public Goods getGoods(String sku) throws RemoteException;
    public void clearExpGoods() throws RemoteException;
    public List<Goods> getGoodsListBySKU(String sku, byte page) throws RemoteException;

    public Goods getGoodsToDelivery(Goods goods) throws RemoteException;

    public byte getMaxPage() throws RemoteException;
}

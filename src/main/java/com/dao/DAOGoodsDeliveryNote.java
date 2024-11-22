package com.dao;

import com.entity.GoodsDeliveryNote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface DAOGoodsDeliveryNote extends Remote {
    public boolean createGDN(GoodsDeliveryNote gdn) throws RemoteException;
    public HashMap<String, List<String>> getGoodsDeliveryPos() throws RemoteException;
}

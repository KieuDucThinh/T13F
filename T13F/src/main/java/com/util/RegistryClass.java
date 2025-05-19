package com.util;

import com.dao.*;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RegistryClass {

    private static final java.rmi.registry.Registry registry;
    private static final DAOCustomer customer;
    private static final DAOFruitType fruitType;
    private static final DAOGoods goods;
    private static final DAOGoodsDeliveryNote goodsDeliveryNote;
    private static final DAOGoodsReceivedNote goodsReceivedNote;
    private static final DAOPosition position;
    private static final DAOUser user;
    private static final DAOSupplier supplier;

    static{
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            customer = (DAOCustomer) registry.lookup("customer");
            fruitType = (DAOFruitType) registry.lookup("fruitType");
            goods = (DAOGoods) registry.lookup("goods");
            goodsDeliveryNote = (DAOGoodsDeliveryNote) registry.lookup("goodsDeliveryNote");
            position = (DAOPosition) registry.lookup("position");
            user = (DAOUser) registry.lookup("user");
            supplier = (DAOSupplier) registry.lookup("supplier");
            goodsReceivedNote = (DAOGoodsReceivedNote) registry.lookup("goodsReceivedNote");
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        } catch (NotBoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public RegistryClass() throws RemoteException, AccessException, NotBoundException {

    }

    public DAOCustomer customer() throws RemoteException {
        return customer;
    }

    public DAOFruitType fruitType() throws RemoteException {
        return fruitType;
    }

    public DAOGoods goods() throws RemoteException {
        return goods;
    }

    public DAOGoodsDeliveryNote goodsDeliveryNote() throws RemoteException {
        return goodsDeliveryNote;
    }

    public DAOGoodsReceivedNote goodsReceivedNote() throws RemoteException {
        return goodsReceivedNote;
    }

    public DAOPosition position() throws RemoteException {
        return position;
    }

    public DAOUser user() throws RemoteException {
        return user;
    }

    public DAOSupplier supplier() throws RemoteException {
        return supplier;
    }

//     try {
//        //Lay thanh ghi dua tren ip va port mat chu
//        java.rmi.registry.Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//
//        //Lay ra obj
//        DAOCustomer calStub = (DAOCustomer) registry.lookup("customer");
//        Customer customer = calStub.getCustomer("CU-0000000001");
//        System.out.println(customer);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}

package com.test;

import com.dao.DAOCustomer;
import com.dao.DAOGoods;
import com.entity.Customer;
import com.entity.Position;
import com.util.RegistryClass;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyClient {
    //Các thuộc tính đặc biệt
    private static RegistryClass registryClass;

    {
        try {
            registryClass = new RegistryClass();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws Exception {
        registryClass = new RegistryClass();
        HashMap<String, Long> map = (HashMap<String, Long>) registryClass.position().getInventory();
        System.out.println(map);
//        RegistryClass registry = new RegistryClass();
//        DAOCustomer daoCustomer = registry.customer();
//
//        Customer customer = daoCustomer.getCustomer("CU-0000000001");
//        Customer customer2 = daoCustomer.getCustomer("CU-0000000002");
//        System.out.println(customer);
//        System.out.println(customer2);
//
//        DAOGoods daoGoods = registry.goods();
//        System.out.println(daoGoods.getMaxPage()+"");
//        List<Position> list = registry.position().getGoodsPositionsByStatus("D", (byte)1);
//        for (Position p : list) {
//            System.out.println(p.getGoodsPos().getSku());
    }

//        try {
//            //Lay thanh ghi dua tren ip va port mat chu
//            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//
//            //Lay ra obj
//            DAOCustomer calStub = (DAOCustomer) registry.lookup("customer");
//            Customer customer = calStub.getCustomer("CU-0000000001");
//            System.out.println(customer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

}


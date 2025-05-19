package com.test;

import com.dao.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends TimerTask {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(()->{
            try{
                DAOUserImpl daoUser = new DAOUserImpl();
                daoUser.recoverUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            //Tao thanh ghi o server
            Registry registry = LocateRegistry.createRegistry(1099);

            //Tao calculator
            DAOCustomer customer = new DAOCustomerImpl();
            DAOFruitType fruitType = new DAOFruitTypeImpl();
            DAOGoods goods = new DAOGoodsImpl();
            DAOGoodsDeliveryNote goodsDeliveryNote = new DAOGoodsDeliveryNoteImpl();
            DAOGoodsReceivedNote goodsReceivedNote = new DAOGoodsReceivedNoteImpl();
            DAOPosition position = new DAOPositionImpl();
            DAOUser user = new DAOUserImpl();
            DAOSupplier supplier = new DAOSupplierImpl();

            //Dang ky object cho thanh ghi
            registry.rebind("customer", customer);
            registry.rebind("fruitType", fruitType);
            registry.rebind("goods", goods);
            registry.rebind("goodsDeliveryNote", goodsDeliveryNote);
            registry.rebind("goodsReceivedNote", goodsReceivedNote);
            registry.rebind("position", position);
            registry.rebind("user", user);
            registry.rebind("supplier", supplier);

            /**/
            Timer timer = new Timer();
            Main task = new Main();
            // Lên lịch cho task chạy ngay, sau đó cứ 6 tiếng lại chạy 1 lần
            timer.schedule(task, 0, 2 * 60 * 60 * 1000);

//            while (true) {
//                System.out.println("Server is running ...");
//                Thread.sleep(5000);
//            }
        } catch (Exception e) {
            System.out.println(e);
        }
//        DAOCustomerImpl dao = new DAOCustomerImpl();
//        System.out.println(dao.getCustomer("CU-0000000001"));
    }

    /**/
    public Main() throws Exception {

    }

    private DAOGoodsImpl daoGoods = new DAOGoodsImpl();

    @Override
    public void run() {
        try {
            daoGoods.updateStatusAuto();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
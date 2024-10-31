package com.test;

import com.dao.DAOCustomer;
import com.entity.Customer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyClient {
    public static void main(String args[]) throws Exception {
        try {
            //Lay thanh ghi dua tren ip va port mat chu
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            //Lay ra obj
            DAOCustomer calStub = (DAOCustomer) registry.lookup("Calculator");
            Customer customer = calStub.getCustomer("CU-0000000001");
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

//Bank b=(Bank)Naming.lookup("rmi://localhost:6666/javatpoint");
//List<Customer> list=b.getCustomers();
//for(Customer c:list){
//System.out.println(c.getId()+" "+c.getMatKhau()+" "+c.getTenNguoiDung()+" "+c.getCapBac());
//}
    }
}

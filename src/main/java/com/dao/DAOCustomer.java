package com.dao;

import com.entity.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOCustomer extends Remote{
    public Customer getCustomer (String cId) throws RemoteException;
    public Customer findCustomerByPhone (String phone) throws RemoteException;

    public boolean validateCuIn (Customer c) throws RemoteException;
    public boolean addCustomer(Customer customer) throws RemoteException;
}

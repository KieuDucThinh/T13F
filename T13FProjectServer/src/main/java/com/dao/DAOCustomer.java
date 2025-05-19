package com.dao;

import com.entity.Customer;
import jakarta.persistence.EntityManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOCustomer extends Remote{
    public Customer getCustomer (String cId) throws RemoteException;
    public Customer findCustomerByPhone (String phone) throws RemoteException;

    public boolean validateCuIn (Customer c) throws RemoteException;
    public boolean addCustomer(Customer customer) throws RemoteException;

    public boolean updateCustomer(Customer customer) throws RemoteException;
    public List<Customer> getAllCustomer() throws RemoteException;
    public List<Customer> getCustomerByKeyword(String keyword) throws RemoteException;
}

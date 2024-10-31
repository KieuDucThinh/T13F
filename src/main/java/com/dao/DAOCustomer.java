package com.dao;

import com.entity.Customer;

import java.rmi.Remote;

public interface DAOCustomer extends Remote{
    public Customer getCustomer(String cId);
}

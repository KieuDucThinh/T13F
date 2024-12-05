package com.dao;

import com.entity.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOSupplier extends Remote {
    public List<Supplier> getSuppliers() throws RemoteException;
    public List<Supplier> getSuppliersBySupplierName(String supName) throws RemoteException;
    public boolean addSupplier(Supplier supplier) throws RemoteException;
    public boolean updateSupplier(Supplier supplier) throws RemoteException;
}

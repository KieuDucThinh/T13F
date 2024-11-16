package com.dao;

import com.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOUser extends Remote{
    public User findUserByPhone(String phone) throws RemoteException;
    public boolean checkExistUser(User user) throws RemoteException;
}

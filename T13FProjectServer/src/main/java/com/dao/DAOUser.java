package com.dao;

import com.entity.User;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.List;

public interface DAOUser extends Remote{
    public User findUserByPhone(String phone) throws RemoteException;
    public boolean checkExistUser(User user) throws RemoteException;

    public boolean logoutUser(User user) throws RemoteException;
    public boolean loginUser(User user) throws RemoteException;
    public User checkLogin(User user) throws RemoteException;

    public boolean changePassword(String oldPwd, User user) throws RemoteException;
    public List<User> getAllUsers() throws RemoteException;
    public List<User> getAllUsersByStatus(String status) throws RemoteException;
    public List<User> getAllUsersByKeyword(String keyword) throws RemoteException;

    public boolean addUser(User user) throws RemoteException;
    public boolean updateUser(User user) throws RemoteException;
    public boolean lockUser(User user) throws RemoteException;
    public User unlockUser(User user) throws RemoteException;

    public void recoverUser() throws RemoteException;
}

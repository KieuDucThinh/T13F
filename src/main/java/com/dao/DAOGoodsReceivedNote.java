package com.dao;

import com.entity.GoodsReceivedNote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOGoodsReceivedNote extends Remote {
    public List<GoodsReceivedNote> getAllVerifyGRN() throws RemoteException;
    public List<GoodsReceivedNote> getVerifyGRNByKeyword(String keyword) throws RemoteException;

    public List<GoodsReceivedNote> getAllNotVerifyGRN() throws RemoteException;
    public GoodsReceivedNote getGRNByID(String id) throws RemoteException;
    public List<GoodsReceivedNote> getUnverifyGRNByKeyword(String keyword) throws RemoteException;

    public boolean createGRN(GoodsReceivedNote grn) throws RemoteException;
    public boolean updateGRN(GoodsReceivedNote grn) throws RemoteException;
    public boolean deleteGRN(String id) throws RemoteException;
}

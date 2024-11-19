package com.dao;

import com.entity.Goods;
import com.entity.Position;
//import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface DAOPosition extends Remote{
    public void createPosition(Position p) throws RemoteException;
    public Position getPosition(short pos_id) throws RemoteException;
    public List<Position> getGoodsPositionsByStatus(String status, byte page) throws RemoteException;
    public boolean addGoodstoPosition(Goods goods) throws RemoteException;
    public long countEmptyPosByFtType(String ft_type) throws RemoteException;
    public boolean clearPositions() throws RemoteException;
    public byte pageQuantity(String status) throws RemoteException;
    public List<Position> getPositionsByFt(String ft_id) throws RemoteException;
    public List<Position> getPosByPosNumber(int positionNumber) throws RemoteException;

//    public boolean getGoodsPos(Goods goods, short quantity, EntityManager em, HashMap<String, List<String>> map) throws RemoteException;
//    public boolean removeGoods(String sku, EntityManager em) throws RemoteException;
}

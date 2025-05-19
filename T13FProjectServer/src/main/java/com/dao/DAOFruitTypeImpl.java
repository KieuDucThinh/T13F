package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.FruitType;
import com.entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;

public class DAOFruitTypeImpl extends UnicastRemoteObject implements DAOFruitType {
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    //Constructor
    public DAOFruitTypeImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
//        this.entityManager = connectionPool.getConnection();
    }

    @Override
    public FruitType getFruitType(String ft_id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            FruitType fruitType = entityManager.find(FruitType.class, ft_id);
            /*
            TypedQuery<Position> query = entityManager.createQuery("SELECT s from Position s JOIN s.fruitType ft WHERE ft.ftId =: ftid", Position.class);
            query.setParameter("ftid", "MA1");
            List<Position> positionSet = query.getResultList();
            for (Position position : positionSet) {
                System.out.println(position.toString());
            }
            */
            transaction.commit();
            return fruitType;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Lấy toàn bộ loại trái cây
    @Override
    public List<FruitType> getAllFruitTypes() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<FruitType> query = entityManager.createQuery("SELECT s from FruitType s", FruitType.class);
            transaction.commit();
            return query.getResultList();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }
}

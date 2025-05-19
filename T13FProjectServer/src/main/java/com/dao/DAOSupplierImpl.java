package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DAOSupplierImpl extends UnicastRemoteObject implements DAOSupplier {
    ///Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    public DAOSupplierImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
    }

    //Lấy toàn bộ danh sách nhà cung cấp
    @Override
    public List<Supplier> getSuppliers() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Supplier> query = entityManager.createQuery("select s from Supplier s", Supplier.class);
            transaction.commit();
            return query.getResultList();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Tìm nhà cung cấp theo tên
    @Override
    public List<Supplier> getSuppliersBySupplierName(String supName) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Supplier> query = entityManager.createQuery("select s from Supplier s where s.supName like ?1", Supplier.class);
            query.setParameter(1, "%"+supName.trim()+"%");
            transaction.commit();
            return query.getResultList();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Thêm nhà cung cấp
    @Override
    public boolean addSupplier(Supplier supplier) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            this.entityManager.persist(supplier);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Sửa nhà cung cấp
    @Override
    public boolean updateSupplier(Supplier supplier) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Supplier sup = this.entityManager.find(Supplier.class, supplier.getSupId());

            //Nếu không thấy thì không sửa
            if(sup == null){
                transaction.commit();
                return false;
            }

            //Thay tên, địa chỉ, điện thoại, email, mô tả
            sup.setSupName(supplier.getSupName());
            sup.setSupAddress(supplier.getSupAddress());
            sup.setSupPhone(supplier.getSupPhone());
            sup.setSupEmail(supplier.getSupEmail());
            sup.setSupDescription(sup.getSupDescription());

            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
}

package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DAOGoodsDeliveryNoteImpl extends UnicastRemoteObject implements DAOGoodsDeliveryNote {
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    private HashMap<String, List<String>> map = new HashMap<>();

    //Constructor
    public DAOGoodsDeliveryNoteImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
    }


    @Override
    public boolean createGDN(GoodsDeliveryNote gdn) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            DAOPositionImpl daoPosition = new DAOPositionImpl();

            System.out.println(gdn.getCustomer());
            //Tìm khách hàng theo số điện thoại
            Customer c = new DAOCustomerImpl().findCustomerByPhone(gdn.getCustomer().getCPhone());
            System.out.println(gdn.getCustomer());

            //Không thấy khách hàng
            if(c==null){
                //Lưu khách hàng
                //entityManager quản lý khách hàng
                entityManager.persist(gdn.getCustomer());
            } else {
                //Thấy thì tìm lại theo id để: entityManager quản lý khách hàng
                gdn.setCustomer(entityManager.find(Customer.class, c.getCId()));
            }

            //Lưu phiếu xuất
            //entityManager quản lý: phiếu xuất
            entityManager.persist(gdn);

            Set<GdnDetail> set = gdn.getGdnDetailsSet();
            for (GdnDetail gdnDetail : set) {
                Goods g = new Goods();
                g = gdnDetail.getGoodsGDNDetail();

                //entityManager quản lý: hàng
                g = entityManager.find(Goods.class, g.getSku());
//                System.out.println(g.getGdnDetailSet().size());
                g.setAvailableQuantity((short)(g.getAvailableQuantity() - gdnDetail.getQuantity()));

                daoPosition.getGoodsPos(g, gdnDetail.getQuantity(), this.entityManager, map);
                /*
                In hash map

                // Duyệt qua HashMap
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    List<String> values = entry.getValue();

                    System.out.println("Key: " + key);

                    for (String value : values) {
                        System.out.println("  Value: " + value);
                    }
                }
                */

                //Loại bỏ hàng theo số lượng khỏi vị trí
                for (int i = 1; i <= gdnDetail.getQuantity(); i++) {
                    daoPosition.removeGoods(g.getSku(), this.entityManager);
                }

                GdnDetailKey key = GdnDetailKey.builder().gdnID(gdn.getGdnId()).skuGDN(g.getSku()).build();
                gdnDetail.setId(key);
                gdnDetail.setGdnDetail(gdn);
                gdnDetail.setGoodsGDNDetail(g);

                //entityManager quản lý: chi tiết phiếu xuất
                entityManager.persist(gdnDetail);
            }
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    @Override
    public HashMap<String, List<String>> getGoodsDeliveryPos() throws RemoteException {
        return this.map;
    }

    //Lấy danh sách toàn bộ phiếu xuất
    @Override
    public List<GoodsDeliveryNote> getAllGDN() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<GoodsDeliveryNote> query = entityManager.createQuery("select s from GoodsDeliveryNote s", GoodsDeliveryNote.class);
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

    //Tìm phiếu xuất theo mã, mã khách, thời gian
    @Override
    public List<GoodsDeliveryNote> getGDNByKeyword(String keyword) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<GoodsDeliveryNote> query = entityManager.createQuery("select s from GoodsDeliveryNote s where s.gdnId like ?1 or s.customer.cId like ?2 or CAST(s.gdnDate AS DATE) = ?3 order by s.gdnDate", GoodsDeliveryNote.class);
            query.setParameter(1, "%"+keyword.trim()+"%");
            query.setParameter(2, "%"+keyword.trim()+"%");
            try{
                query.setParameter(3, Date.valueOf(keyword.trim()));
            } catch (Exception e){
                query.setParameter(3, Date.valueOf("1900-01-01"));
            }
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

    //Lấy ra phiếu xuất theo id
    @Override
    public GoodsDeliveryNote getGDNByID(String id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            GoodsDeliveryNote gdn = entityManager.find(GoodsDeliveryNote.class, id);
            transaction.commit();
            return gdn;
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
}

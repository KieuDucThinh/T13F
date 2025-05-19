package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.Goods;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DAOGoodsImpl extends UnicastRemoteObject implements DAOGoods{
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;
    private byte maxPage = 1;

    private static final byte ITEM_OF_PAGE = 15;

    //Constructor
    public DAOGoodsImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
//        this.entityManager = connectionPool.getConnection();
    }

    //Tìm hàng theo sku
    @Override
    public Goods getGoods(String sku) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Goods goods = entityManager.find(Goods.class, sku);
            transaction.commit();
            return goods;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Dọn các hàng hỏng
    @Override
    public void clearExpGoods() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = this.entityManager.createQuery("UPDATE Goods g SET g.availableQuantity = 0 WHERE g.status =?1");
            query.setParameter(1, "D");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
    }

    //Tìm danh sách hàng theo SKU
    @Override
    public List<Goods> getGoodsListBySKU(String skuString, byte page) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            skuString = skuString.toUpperCase().trim();
            if (!skuString.matches("[0-9A-Z,\\- ]*")) {
                return null;
            }

            //Khởi tạo CriteriaBuilder
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            //Từ CB tạo query
            CriteriaQuery<Goods> cq = cb.createQuery(Goods.class);

            //Tạo root từ Goods: from goods
            Root<Goods> root = cq.from(Goods.class);

            //Chia skuList thành các sku theo dấu ,
            String[] skuList = skuString.split(",");

            //Mảng này chứa thành phần của sku
            String[] skuItem = null;

            //Danh sách chứa query của phép and các thành phần SKU
            List<Predicate> predicates = new ArrayList<Predicate>();

            //Danh sách chứa query của phép or các SKU
            List<Predicate> predicates2 = new ArrayList<Predicate>();

            //Duyêt các SKU
            for (String s : skuList) {
                //sku độ dài lớn hơn 22 là sai
                if (s.length() > 22) {
                    transaction.commit();
                    return null;
                } else {
                    //Chia thành các phần nhỏ của sku bằng dấu gạch nối
                    skuItem = s.trim().split("-");

                    //số thành phần lớn hơn 5 là sai
                    if (skuItem.length > 5) {
                        transaction.commit();
                        return null;
                    }

                    //Duyệt các thành phần với phép like tìm hàng để thêm vào mảng điều kiện and
                    for (String item : skuItem) {
                        item = item.trim();
                        predicates.add(cb.like(root.get("sku"), "%" + item + "%"));
                    }
//                    System.out.println(predicates.size());
                    //Thêm các điều kiện and vào mảng điều kiện or
                    predicates2.add(cb.and(predicates.toArray(new Predicate[0])));
//                    System.out.println(predicates2.size());
                    //Làm trống danh sách chứa điều kiện and
                    predicates.clear();
                }
            }
            //Tạo điều kiện or từ mảng chứa các điều kiện and
            Predicate finalPredicate = cb.and(cb.or(predicates2.toArray(new Predicate[0])), cb.notEqual(root.get("status"), "DE"), cb.notEqual(root.get("status"), "D"));

            //Where
            cq.where(finalPredicate);

            //Order by
            cq.orderBy(cb.desc(root.get("status")), cb.asc(root.get("sku")));

            //Tạo query
            TypedQuery<Goods> query = this.entityManager.createQuery(cq);

            this.maxPage = (byte)(Math.ceil((double)query.getResultList().size()/ITEM_OF_PAGE));
            query.setMaxResults((page - 1)*ITEM_OF_PAGE);
            query.setMaxResults(ITEM_OF_PAGE);

            transaction.commit();
            return query.getResultList();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Tìm hàng theo sku và loại trái cây
    @Override
    public Goods getGoodsToDelivery(Goods goods) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            String skuString = goods.getSku();
            transaction.begin();
            skuString = skuString.toUpperCase().trim();
            if (!skuString.matches("[0-9A-Z,\\-]*")) {
                return null;
            }

            //Khởi tạo CriteriaBuilder
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            //Từ CB tạo query
            CriteriaQuery<Goods> cq = cb.createQuery(Goods.class);

            //Tạo root từ Goods: from goods
            Root<Goods> root = cq.from(Goods.class);

            //Danh sách chứa query của phép and các thành phần SKU
            List<Predicate> predicates = new ArrayList<Predicate>();

//            //Danh sách chứa query của phép or các SKU
//            List<Predicate> predicates2 = new ArrayList<Predicate>();

            //sku độ dài lớn hơn 22 là sai
            if (skuString.length() > 22) {
                transaction.commit();
                return null;
            }

//            else if (skuString.length() >= 19) {
//                transaction.commit();
//                return entityManager.find(Goods.class, skuString);
//            }
//            else {

            //Chia thành các phần nhỏ của sku bằng dấu gạch nối
            String[] skuItem = skuString.trim().split("-");

            //số thành phần lớn hơn 5 là sai
            if (skuItem.length > 5) {
                transaction.commit();
                return null;
            }

            //Duyệt các thành phần với phép like tìm hàng để thêm vào mảng điều kiện and
            for (String item : skuItem) {
                item = item.trim();
                predicates.add(cb.like(root.get("sku"), "%" + item + "%"));
            }
//          }

            //Nối các phần tử bằng phép and
            Predicate predicate2 = (cb.and(predicates.toArray(new Predicate[0])));


            //Tạo điều kiện or từ mảng chứa các điều kiện and và and với điều kiện trạng thái
            Predicate finalPredicate = cb.and(predicate2, cb.equal(root.get("status"), goods.getStatus()));

            //Where
            cq.where(finalPredicate);

            //Order by thời gian quá chín
            cq.orderBy(cb.desc(root.get("overripDate")));

            //Tạo query
            TypedQuery<Goods> query = this.entityManager.createQuery(cq);
            transaction.commit();
//            return (query.getSingleResult());
            if(query.getResultList().size()>0) {
                return (query.getResultList().get(0));
            }
        } catch (
                Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    @Override
    public byte getMaxPage() {
        return maxPage;
    }

    @Override
    public void updateStatusAuto() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = this.entityManager.createQuery("UPDATE Goods g SET g.status = 'JR' WHERE g.justRipeDate <= CURRENT_TIMESTAMP and g.status != 'D' and g.status != 'DE' and g.status != 'P'");
            query.executeUpdate();

            Query query1 = this.entityManager.createQuery("UPDATE Goods g SET g.status = 'RI' WHERE g.ripeDate <= CURRENT_TIMESTAMP and g.status != 'D' and g.status != 'DE' and g.status != 'P'");
            query1.executeUpdate();

            Query query2 = this.entityManager.createQuery("UPDATE Goods g SET g.status = 'OR' WHERE g.overripDate <= CURRENT_TIMESTAMP and g.status != 'D' and g.status != 'DE' and g.status != 'P'");
            query2.executeUpdate();

            Query query3 = this.entityManager.createQuery("UPDATE Goods g SET g.status = 'D' WHERE g.expDate <= CURRENT_TIMESTAMP and g.status != 'D' and g.status != 'DE' and g.status != 'P'");
            query3.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
    }
}

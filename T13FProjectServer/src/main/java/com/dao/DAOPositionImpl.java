package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.Goods;
import com.entity.GrnDetail;
import com.entity.Position;
import com.util.LocalDateMonthY;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DAOPositionImpl extends UnicastRemoteObject implements DAOPosition {
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    private HashMap<String, BigDecimal> mapRevenue = new HashMap<>();

    private static final byte ITEM_OF_PAGE = 15;

    //Constructor
    public DAOPositionImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
//        this.entityManager = connectionPool.getConnection();
    }

    //Thêm vị trí vào trong csdl
    @Override
    public void createPosition(Position p) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(p);
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

    //Lấy vị trí theo pos_id
    @Override
    public Position getPosition(short pos_id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Position p = entityManager.find(Position.class, pos_id);
            return p;
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

    //Lấy danh sách hàng theo status với ds có độ dài là 10 bắt đầu từ page
    @Override
    public List<Position> getGoodsPositionsByStatus(String status, byte page) throws RemoteException {
        if (page <= 0) {
            return null;
        }

        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Position> query = entityManager.createQuery("select p from Position p JOIN p.goodsPos g where g.status = ?1", Position.class);
            query.setParameter(1, status);
            query.setFirstResult((page - 1) * ITEM_OF_PAGE);
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

    //Đưa hàng vào vị trí sau khi nhập
    @Override
    public boolean addGoodstoPosition(Goods goods) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            //Không đủ vị trí chứa hàng
            //Thay totalQuantity bằng quantity
            if (countEmptyPosByFtType(goods.getSku().substring(0, 3)) < goods.getTotalQuantity()) {
                return false;
            }

            //Xác định vị trí thêm hàng
            //test nên để trạng thái 2 là D

            //Đã thay thành JR
            String d = "JR";

            boolean posAdded = false;
            if (goods.getStatus().equalsIgnoreCase("UR")) {
                posAdded = true;
            } else if (goods.getStatus().equalsIgnoreCase(d)) {
                posAdded = false;
            } else {
                return false;
            }

            //Thêm hàng vào vị trí
            //Đáng lẽ phải là quantity nhưng đây là thử nghiệm sẽ thay sau

            //Đã thay thành quantity
            Iterator<GrnDetail> iterator = goods.getGrnDetailSet().iterator();
            short quantity = iterator.next().getQuantity();
            for (int i = quantity; i > 0; i--) {
                if (posAdded) {
//                    TypedQuery<Integer> query = entityManager.createQuery("select min(p.posId) from Position p where (p.goodsPos is null) and (p.fruitType.ftId = ?1)", Integer.class);
                    Query query2 = entityManager.createQuery("update Position p set goodsPos = ?1 where posId = (select min(p.posId) from Position p where (p.goodsPos is null) and (p.fruitType.ftId = ?2))");
                    query2.setParameter(1, goods);
                    query2.setParameter(2, goods.getSku().substring(0, 3));
                    query2.executeUpdate();
                } else {
//                    System.out.println(query.getSingleResult());
                    Query query2 = entityManager.createQuery("update Position p set goodsPos = ?1 where posId = (select max(p.posId) from Position p where (p.goodsPos is null) and (p.fruitType.ftId = ?2))");
                    query2.setParameter(1, goods);
                    query2.setParameter(2, goods.getSku().substring(0, 3));
                    query2.executeUpdate();
                }
            }
            entityManager.flush();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    //Đếm số vị trí trống
    @Override
    public long countEmptyPosByFtType(String ft_type) throws RemoteException {
//        EntityManager entityManager = connectionPool.getConnection();
//        EntityTransaction transaction = entityManager.getTransaction();
        try {
//            transaction.begin();
            TypedQuery<Long> query = entityManager.createQuery("select count(*) from Position p where (p.goodsPos.sku is null) and (p.fruitType.ftId = ?1)", Long.class);
            query.setParameter(1, ft_type);
//            transaction.commit();
            return query.getSingleResult();
        } catch (Exception e) {
//            if(transaction.isActive()){
//                transaction.rollback();
//            }
            e.printStackTrace();
        } finally {
//            entityManager.close();
        }
        return 0;
    }

    //Đặt sku của những hàng bị hỏng thành nulll
    @Override
    public boolean clearPositions() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            //Hàng hết hạn có available_quantity = 0
            DAOGoodsImpl daoGoods = new DAOGoodsImpl();
            daoGoods.clearExpGoods();

            //Loại bỏ hàng hỏng khỏi kho
            transaction.begin();
            Query query = entityManager.createQuery("update Position p set p.goodsPos = ?1 where (p.goodsPos.status =?2)");
            query.setParameter(1, null);
            query.setParameter(2, "D");
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Xác định số trang max cho mỗi trạng thái trái cây
    @Override
    public byte pageQuantity(String status) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Long> query = entityManager.createQuery("SELECT count(*) from Position p where p.goodsPos.status = ?1", Long.class);
            query.setParameter(1, status);
            transaction.commit();
            return (byte) (Math.ceil((double) query.getSingleResult() / ITEM_OF_PAGE));
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return 1;
    }

    //Lấy ra cột và hàng theo loại trái cây
    @Override
    public List<Position> getPositionsByFt(String ft_id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            //Khởi tạo Criteria Builder
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            //Tạo query chọn bảng position
            CriteriaQuery<Position> cq = cb.createQuery(Position.class);

            //from position
            Root<Position> stud = cq.from(Position.class);

            //select distinct row, col
            cq.multiselect(stud.get("row"), stud.get("col"));

            //where ft_id = ft_id truyền vào
            cq.where(cb.equal(stud.get("fruitType").get("ftId"), ft_id));

            //group by (không dùng distinct được do nó xét toàn bộ dữ liệu phải giống nhau)
            cq.groupBy(stud.get("row"), stud.get("col"));

            //Lệnh Select
            CriteriaQuery<Position> select = cq.select(stud);
            TypedQuery<Position> query = entityManager.createQuery(select);

            transaction.commit();
            return query.getResultList();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Lấy danh sách hàng theo vị số vị trí
    @Override
    public List<Position> getPosByPosNumber(int positionNumber) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Position> query = entityManager.createQuery("SELECT p from Position p where (p.posId > ?1) and (p.posId <= ?2)", Position.class);
            query.setParameter(1, (positionNumber - 1) * 5);
            query.setParameter(2, (positionNumber) * 5);
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

    //Lấy ra các vị trí của hàng
    @Override
    public boolean getGoodsPos(Goods goods, short quantity, EntityManager em, HashMap<String, List<String>> map) throws RemoteException {
        try {
            TypedQuery<String> q = em.createQuery("Select concat('Hàng: ',p.col,' - Cột: ', p.row,' - Ngăn xếp: ', p.stack) from Position p Where p.goodsPos.sku = ?1 order by posId desc", String.class);
            q.setParameter(1, goods.getSku());
            q.setMaxResults(quantity);
            List<String> list = q.getResultList();
            map.put(goods.getSku(), list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Loại bỏ hàng khỏi vị trí nó chiếm khi đã được xuất
    @Override
    public boolean removeGoods(String sku, EntityManager em) throws RemoteException {
        try {
            Query q = em.createQuery("update Position p set p.goodsPos.sku = null where p.posId = (select max(p2.posId) from Position p2 where p2.goodsPos.sku = ?1)");
            q.setParameter(1, sku);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Lấy ra tồn kho mỗi loại
    @Override
    public HashMap<String, Long> getInventory() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
//        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Query query = entityManager.createNativeQuery("SELECT COUNT(*), fruit_type.ft_name FROM position JOIN fruit_type ON position.ft_id = fruit_type.ft_id WHERE position.sku IS NOT NULL group by position.ft_id ORDER BY position.ft_id");
            List<Object[]> list = query.getResultList();
            HashMap<String, Long> map = new HashMap<>();
            for (Object[] o : list) {
                map.put(String.valueOf(o[1]), (Long) o[0]);
            }

            Query query1 = entityManager.createNativeQuery("SELECT COUNT(*) FROM position WHERE sku IS NULL");
            List<Long> list1 = query1.getResultList();
            map.put("Trống", list1.get(0));

            System.out.println(map);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Lấy ra doanh thu 10 tháng gần nhất
    @Override
    public HashMap<?, ?> getRevenue() throws RemoteException {
        /*
        this.entityManager = connectionPool.getConnection();
        try {
            Query query = entityManager.createNativeQuery("SELECT SUM(total_delivery_price), CONCAT(MONTH(gdn_date),'-',YEAR(gdn_date))\n" +
                    "FROM goods_delivery_note \n" +
                    "where MONTH(gdn_date) = ?1 and YEAR(gdn_date) = ?2 \n" +
                    "GROUP BY MONTH(gdn_date), YEAR(gdn_date) ORDER BY gdn_date DESC");

            List<Object[]> list;

            LocalDateMonthY time = new LocalDateMonthY();
            int month, year;
            String date;
            for (int i = 9; i >= 0; i--) {
                date = time.previousMonth(i) + "-";
                query.setParameter(1, time.previousMonth(i));
                if (time.getMonthNow() < time.previousMonth(i)) {
                    date += time.previousYear(1);
                    query.setParameter(2, time.previousYear(1));
                } else {
                    date += time.getYearNow();
                    query.setParameter(2, time.getYearNow());
                }

                System.out.println(date);

                list = query.getResultList();
                if(list.isEmpty()){
                    mapRevenue.put(String.valueOf(date), BigDecimal.ZERO);
                } else {
                    for (Object[] o : list) {
                        mapRevenue.put(String.valueOf(date), (BigDecimal) o[0]);
                    }
                }
            }

            System.out.println(mapRevenue);

            return mapRevenue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
         */
        return mapRevenue;
    }

    //Lấy ra lợi nhuận 10 tháng gần nhất
    @Override
    public HashMap<?, ?> getProfit() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        try {
            Query query2 = entityManager.createNativeQuery("SELECT SUM(total_delivery_price), CONCAT(MONTH(gdn_date),'-',YEAR(gdn_date))\n" +
                    "FROM goods_delivery_note \n" +
                    "where MONTH(gdn_date) = ?1 and YEAR(gdn_date) = ?2 \n" +
                    "GROUP BY MONTH(gdn_date), YEAR(gdn_date) ORDER BY gdn_date DESC");

            Query query = entityManager.createNativeQuery("SELECT SUM(total_received_price), CONCAT(MONTH(grn_date),'-',YEAR(grn_date))\n" +
                    "FROM goods_received_note \n" +
                    "where MONTH(grn_date) = ?1 and YEAR(grn_date) = ?2 \n" +
                    "GROUP BY MONTH(grn_date), YEAR(grn_date) ORDER BY grn_date DESC");

            List<Object[]> list2;
            List<Object[]> list;

            HashMap<String, BigDecimal> map = new HashMap<>();

            LocalDateMonthY time = new LocalDateMonthY();
            String date;
            for (int i = 9; i >= 0; i--) {
                date = time.previousMonth(i) + "-";
                query.setParameter(1, time.previousMonth(i));
                query2.setParameter(1, time.previousMonth(i));
                if (time.getMonthNow() < time.previousMonth(i)) {
                    date += time.previousYear(1);
                    query.setParameter(2, time.previousYear(1));
                    query2.setParameter(2, time.previousYear(1));
                } else {
                    date += time.getYearNow();
                    query.setParameter(2, time.getYearNow());
                    query2.setParameter(2, time.getYearNow());
                }

                list2 = query2.getResultList();
                list = query.getResultList();

                if (list2.isEmpty()) {
                    mapRevenue.put(String.valueOf(date), BigDecimal.ZERO);
                } else {
//                    for (Object[] o : list2) {
                    mapRevenue.put(String.valueOf(date), (BigDecimal) list2.get(0)[0]);
//                    }
                }

                if (list.isEmpty()) {
                    map.put(String.valueOf(date), mapRevenue.get(date));
                } else {
//                    for (int j = 0; j < list.size(); j++) {
                    map.put(String.valueOf(date), ((BigDecimal) list.get(0)[0]).subtract(mapRevenue.get(date)));
//                    }
                }
            }

            System.out.println(map);
            System.out.println(mapRevenue);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Lấy ra danh sách lợi nhuận và doanh thu 10 tháng gần nhất
    @Override
    public List<HashMap<String, BigDecimal>> getRevenueAndProfit() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        try {
            Query query2 = entityManager.createNativeQuery("SELECT SUM(total_delivery_price), CONCAT(MONTH(gdn_date),'-',YEAR(gdn_date))\n" +
                    "FROM goods_delivery_note \n" +
                    "where MONTH(gdn_date) = ?1 and YEAR(gdn_date) = ?2 \n" +
                    "GROUP BY MONTH(gdn_date), YEAR(gdn_date) ORDER BY gdn_date DESC");

            Query query = entityManager.createNativeQuery("SELECT SUM(total_received_price), CONCAT(MONTH(grn_date),'-',YEAR(grn_date))\n" +
                    "FROM goods_received_note \n" +
                    "where MONTH(grn_date) = ?1 and YEAR(grn_date) = ?2 \n" +
                    "GROUP BY MONTH(grn_date), YEAR(grn_date) ORDER BY grn_date DESC");

            List<Object[]> list2;
            List<Object[]> list;

            HashMap<String, BigDecimal> map = new HashMap<>();

            LocalDateMonthY time = new LocalDateMonthY();
            String date;
            for (int i = 9; i >= 0; i--) {
                date = time.previousMonth(i) + "-";
                query.setParameter(1, time.previousMonth(i));
                query2.setParameter(1, time.previousMonth(i));
                if (time.getMonthNow() < time.previousMonth(i)) {
                    date += time.previousYear(1);
                    query.setParameter(2, time.previousYear(1));
                    query2.setParameter(2, time.previousYear(1));
                } else {
                    date += time.getYearNow();
                    query.setParameter(2, time.getYearNow());
                    query2.setParameter(2, time.getYearNow());
                }

                list2 = query2.getResultList();
                list = query.getResultList();

                if (list2.isEmpty()) {
                    mapRevenue.put(String.valueOf(date), BigDecimal.ZERO);
                } else {
//                    for (Object[] o : list2) {
                    mapRevenue.put(String.valueOf(date), (BigDecimal) list2.get(0)[0]);
//                    }
                }

                if (list.isEmpty()) {
                    map.put(String.valueOf(date), mapRevenue.get(date));
                } else {
//                    for (int j = 0; j < list.size(); j++) {
                    map.put(String.valueOf(date), ((BigDecimal) list.get(0)[0]).subtract(mapRevenue.get(date)));
//                    }
                }
            }

            List<HashMap<String, BigDecimal>> listALL = new ArrayList<>();
            listALL.add(map);
            listALL.add(mapRevenue);
            return listALL;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }
}

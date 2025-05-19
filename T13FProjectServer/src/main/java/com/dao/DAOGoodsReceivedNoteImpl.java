package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.*;
import com.generator.SKUGenerator;
import com.util.LocalDateMonthY;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOGoodsReceivedNoteImpl extends UnicastRemoteObject implements DAOGoodsReceivedNote {
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    private SKUGenerator skuGenerator = new SKUGenerator();

    //Constructor
    public DAOGoodsReceivedNoteImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
//        this.entityManager = connectionPool.getConnection();
    }

    //Lấy toàn bộ phiếu nhập đã được duyệt
    @Override
    public List<GoodsReceivedNote> getAllVerifyGRN() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<GoodsReceivedNote> query = entityManager.createQuery("select s from GoodsReceivedNote s where s.receivedUser.username is not null order by s.verifyDate", GoodsReceivedNote.class);
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

    //Tìm phiếu nhập đã được duyệt
    @Override
    public List<GoodsReceivedNote> getVerifyGRNByKeyword(String keyword) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<GoodsReceivedNote> query = entityManager.createQuery("select s from GoodsReceivedNote s where s.receivedUser.username is not null and (s.grnId like ?1 or s.supplier.supName like ?2 or CAST(s.verifyDate as DATE) = ?3) order by s.verifyDate", GoodsReceivedNote.class);
            query.setParameter(1, "%" + keyword.trim() + "%");
            query.setParameter(2, "%" + keyword.trim() + "%");
            try {
                query.setParameter(3, Date.valueOf(keyword.trim()));
            } catch (Exception e) {
                query.setParameter(3, Date.valueOf("1900-01-01"));
            }
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

    //Lấy danh sách phiếu nhập chưa được duyệt
    @Override
    public List<GoodsReceivedNote> getAllNotVerifyGRN() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<GoodsReceivedNote> query = entityManager.createQuery("select s from GoodsReceivedNote s where s.receivedUser.username is null order by s.grnDate", GoodsReceivedNote.class);
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

    //Tìm phiếu nhập chưa được duyệt
    @Override
    public List<GoodsReceivedNote> getUnverifyGRNByKeyword(String keyword) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<GoodsReceivedNote> query = entityManager.createQuery("select s from GoodsReceivedNote s where s.receivedUser.username is null and (s.grnId like ?1 or s.supplier.supName like ?2 or CAST(s.grnDate as DATE) = ?3) order by s.verifyDate", GoodsReceivedNote.class);
            query.setParameter(1, "%" + keyword.trim() + "%");
            query.setParameter(2, "%" + keyword.trim() + "%");
            try {
                query.setParameter(3, Date.valueOf(keyword.trim()));
            } catch (Exception e) {
                query.setParameter(3, Date.valueOf("1900-01-01"));
            }
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

    //Lấy phiếu nhập theo id
    @Override
    public GoodsReceivedNote getGRNByID(String id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            GoodsReceivedNote grn = entityManager.find(GoodsReceivedNote.class, id);
            transaction.commit();
            return grn;
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

    //Tạo phiếu nhập mới
    @Override
    public boolean createGRN(GoodsReceivedNote grn) throws RemoteException {
        try {
            //Không thấy nhà cung cấp này thì không lưu đơn nhập
            DAOSupplier dao = new DAOSupplierImpl();
            if (dao.getSuppliersBySupplierName(grn.getSupplier().getSupName()) == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            //Lưu ---> donnhaphang
            //entityManager quản lý: DonNhapHang
            entityManager.persist(grn);

//            System.out.println(grn);

            //Set để lưu tạm trái cây
            Set<Goods> goodsSet = new HashSet<>();

            //Set chứa chi tiết đơn nhập
            Set<GrnDetail> grnDetailSet = grn.getGrnDetailsSet();

            for (GrnDetail item : grnDetailSet) {
                //Khởi tạo traiCay
                Goods goods = new Goods();

                //Tìm trái cây đã tồn tại chưa
                //entityManager quản lý: TraiCay
                goods = /*new Goods() */item.getGoodsGRNDetail();

                Set<GrnDetail> gSet = new HashSet<GrnDetail>();
                item.setGrnDetail(grn);
                gSet.add(item);

                goods.setGrnDetailSet(gSet);

                if (goods.getSku() != null) {
                    goods.setStatus(item.getGoodsGRNDetail().getSku().substring(item.getGoodsGRNDetail().getSku().length() - 2));
                }
//                System.out.println(skuGenerator.getSKU(goods));

                Goods goods2 = entityManager.find(Goods.class, skuGenerator.getSKU(goods));

//                System.out.println(goods2);

                //Nếu chưa tồn tại thì
                //Lưu ---> traicay
                //entityManager quản lý: TraiCay
                if (goods2 == null) {
                    item.getGoodsGRNDetail().setSku(null);
                    entityManager.persist(item.getGoodsGRNDetail());
                    goods = item.getGoodsGRNDetail();
                    goods.setStatus("P");
                } else {
                    //Nếu tồn tại thì gắn bằng nó
                    goods = goods2;
                }

                //Thêm: traicay được quản lý vào set
                goodsSet.add(goods);

                //Nối chi tiết đơn nhập với: donnhaphang và traicay được quản lý
                item.setGoodsGRNDetail(goods);
                item.setGrnDetail(grn);

                //Xác định khóa
                GrnDetailKey pk = GrnDetailKey.builder()
                        .grnID(grn.getGrnId())
                        .skuGRN(goods.getSku())
                        .build();
                item.setId(pk);

                //Lưu ---> chitietdonnhap
                //entityManager quản lý: ChiTietDonNhap
                entityManager.persist(item);
            }

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

    //Sửa phiếu nhập
    //Nếu sửa ngày thì coi như phiếu mới
    //Nếu sửa hàng thì tạo hàng mới
    @Override
    public boolean updateGRN(GoodsReceivedNote grn, boolean option) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            //Không thấy không sửa
            GoodsReceivedNote newGrn = entityManager.find(GoodsReceivedNote.class, grn.getGrnId());
            if (newGrn == null) {
                transaction.commit();
                return false;
            }

//            System.out.println(newGrn.getGrnDate());
//            System.out.println(grn.getGrnDate());
//
//            if (newGrn.getGrnDate().equals(grn.getGrnDate())) {
//                System.out.println("-------------------------[OOO]---------------------------");
//            }

            if (!newGrn.getGrnDate().equals(grn.getGrnDate())) {
                deleteGRN(newGrn, this.entityManager);
                transaction.commit();
                this.entityManager.close();

                grn.setGrnId(null);
                return createGRN(grn);
            }

//            System.out.println("-T.T-");

            newGrn.setSupplier(grn.getSupplier());
            newGrn.setTotalReceivedPrice(grn.getTotalReceivedPrice());
            newGrn.setGrnDescription(grn.getGrnDescription());

            //Xóa toàn bộ chi tiết phiếu nhập tạo lại
            //Tìm xem hàng có nằm trong phiếu nhập nào khác không
            TypedQuery<GrnDetail> query = entityManager.createQuery("Select g from GrnDetail g join g.goodsGRNDetail go WHERE go.sku = ?1", GrnDetail.class);

            //Lấy danh sách chi tiết phiếu nhập
            Set<GrnDetail> set = newGrn.getGrnDetailsSet();

            for (GrnDetail grnDetail : set) {
                String sku = grnDetail.getGoodsGRNDetail().getSku();
//                System.out.println(sku);
                this.entityManager.remove(grnDetail);
                query.setParameter(1, sku);

                //Nếu hàng không nằm trong phiếu nhập nào khác thì xóa hàng
                if (query.getResultList().isEmpty()) {
//                    System.out.println(sku);
                    Goods goods = entityManager.find(Goods.class, sku);
                    entityManager.remove(goods);
                }
            }

            entityManager.find(GoodsReceivedNote.class, grn.getGrnId());

            //Set để lưu tạm trái cây
            Set<Goods> goodsSet = new HashSet<>();

            //Set chứa chi tiết đơn nhập
            Set<GrnDetail> grnDetailSet = grn.getGrnDetailsSet();

            for (GrnDetail item : grnDetailSet) {
                //Khởi tạo traiCay
                Goods goods = new Goods();

                //Tìm trái cây đã tồn tại chưa
                //entityManager quản lý: TraiCay
                goods = item.getGoodsGRNDetail();
//                System.out.println("\\//");
//                System.out.println(goods);

                Set<GrnDetail> gSet = new HashSet<GrnDetail>();
                item.setGrnDetail(grn);
                gSet.add(item);

                goods.setGrnDetailSet(gSet);
//                System.out.println("----------------------------");
//                System.out.println(skuGenerator.getSKU(goods));
                goods = entityManager.find(Goods.class, skuGenerator.getSKU(goods));

//                System.out.println(goods);
                //Nếu chưa tồn tại thì
                //Lưu ---> traicay
                //entityManager quản lý: TraiCay
                if (goods == null) {
                    item.getGoodsGRNDetail().setSku(null);
//                    System.out.println(item.getGoodsGRNDetail());
                    entityManager.persist(item.getGoodsGRNDetail());
                    goods = item.getGoodsGRNDetail();
//                    System.out.println(goods);
                    goods.setStatus("P");
                } else {
                    //option là có cập nhật qinbox, giá nhập, giá xuất không
                    //Thêm với đó là hàng chưa nhập
                    if (option && goods.getStatus().equals("P")) {
                        goods.setQuantityInBox(item.getGoodsGRNDetail().getQuantityInBox());
                        goods.setDeliveryPrice(item.getGoodsGRNDetail().getDeliveryPrice());
                        goods.setReceivedPrice(item.getGoodsGRNDetail().getReceivedPrice());
                    }
                }

                //Thêm: traicay được quản lý vào set
                goodsSet.add(goods);

                //Nối chi tiết đơn nhập với: donnhaphang và traicay được quản lý
                item.setGoodsGRNDetail(goods);
                item.setGrnDetail(newGrn);

                //Xác định khóa
                GrnDetailKey pk = GrnDetailKey.builder()
                        .grnID(grn.getGrnId())
                        .skuGRN(goods.getSku())
                        .build();
                item.setId(pk);

                //Lưu ---> chitietdonnhap
                //entityManager quản lý: ChiTietDonNhap
                entityManager.persist(item);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen())
                entityManager.close();
        }
        return false;
    }

    //Xóa phiếu nhập chưa duyệt
    @Override
    public boolean deleteGRN(String id) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            GoodsReceivedNote grn = entityManager.find(GoodsReceivedNote.class, id);

            if (grn == null) {
                transaction.commit();
                return false;
            }

            //Nếu đã xác nhận thì không hủy
            if(grn.getReceivedUser()!=null){
                return false;
            }

            if (grn.getReceivedUser() == null) {
                Set<GrnDetail> setGrnDetail = grn.getGrnDetailsSet();
                for (GrnDetail grnDetail : setGrnDetail) {
                    entityManager.remove(grnDetail);
                }
                entityManager.remove(grn);

                //Tìm xem hàng có nằm trong phiếu nhập nào khác không
                TypedQuery<GrnDetail> query = entityManager.createQuery("Select g from GrnDetail g join g.goodsGRNDetail go WHERE go.sku = ?1", GrnDetail.class);

                //Lấy danh sách chi tiết phiếu nhập
                Set<GrnDetail> set = grn.getGrnDetailsSet();

                for (GrnDetail grnDetail : set) {
                    query.setParameter(1, grnDetail.getGoodsGRNDetail().getSku());

                    //Nếu hàng không nằm trong phiếu nhập nào khác thì xóa hàng
                    if (query.getResultList().isEmpty()) {
//                        System.out.println(grnDetail.getGoodsGRNDetail().getSku());
                        Goods goods = entityManager.find(Goods.class, grnDetail.getGoodsGRNDetail().getSku());
                        entityManager.remove(goods);
                    }
//                    entityManager.remove(grnDetail);
                }
//                entityManager.remove(grn);
                entityManager.flush();
                transaction.commit();
                return true;
            }
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

    //Phương thức khác dùng cho hàm sửa
    @Override
    public boolean deleteGRN(GoodsReceivedNote grn, EntityManager em /*, EntityTransaction transaction*/) throws RemoteException {
        try {
            if (grn.getReceivedUser() == null) {
                //Tìm xem hàng có nằm trong phiếu nhập nào khác không
                TypedQuery<GrnDetail> query = entityManager.createQuery("Select g from GrnDetail g join g.goodsGRNDetail go WHERE go.sku = ?1", GrnDetail.class);

                //Lấy danh sách chi tiết phiếu nhập
                Set<GrnDetail> set = grn.getGrnDetailsSet();

                for (GrnDetail grnDetail : set) {
                    query.setParameter(1, grnDetail.getGoodsGRNDetail().getSku());

                    entityManager.remove(grnDetail);

                    //Nếu hàng không nằm trong phiếu nhập nào khác thì xóa hàng
                    if (query.getResultList().isEmpty()) {
                        Goods goods = entityManager.find(Goods.class, grnDetail.getGoodsGRNDetail().getSku());
                        entityManager.remove(goods);
                    }
                }
                entityManager.remove(grn);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Xác nhận phiếu nhập
    //Chưa test
    @Override
    public boolean verifyGRN(GoodsReceivedNote grn, User user) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            //Không thấy không sửa
            GoodsReceivedNote newGrn = entityManager.find(GoodsReceivedNote.class, grn.getGrnId());
            if (newGrn == null) {
                transaction.commit();
                return false;
            }

            //Nếu xác nhận rồi thì không xác nhận nữa
            if (newGrn.getReceivedUser() != null) {
                transaction.commit();
                return false;
            }

            //Thêm nv nhập và ngày xác nhận vào phiếu và mô tả
            newGrn.setReceivedUser(user);
            newGrn.setVerifyDate(Timestamp.from(Instant.now()));
            newGrn.setGrnDescription(grn.getGrnDescription());

            LocalDateMonthY time = new LocalDateMonthY();

            //Nếu ngày xác nhận trùng ngày nhập
            if (newGrn.getGrnDate().toLocalDateTime().toLocalDate().equals(time.getCurrentDate())) {
                //Dùng để đưa hàng vào trong kho
                DAOPosition dao = new DAOPositionImpl();

                Set<GrnDetail> grnSet = newGrn.getGrnDetailsSet();

                //Duyệt các chi tiết đơn nhập
                for (GrnDetail grnDetail : grnSet) {

                    //Tìm hàng tương ứng
                    Goods goodsReceived = entityManager.find(Goods.class, grnDetail.getGoodsGRNDetail().getSku());

                    //Nếu hàng chưa nhập bao giờ
                    if (goodsReceived.getStatus().equals("P")) {
                        //Cập nhật trạng thái
                        goodsReceived.setStatus(goodsReceived.getSku().substring(goodsReceived.getSku().length() - 2));
                    }

                    //Cộng số lượng vào
                    goodsReceived.setAvailableQuantity((short) (goodsReceived.getAvailableQuantity() + grnDetail.getQuantity()));
                    goodsReceived.setTotalQuantity((short) (goodsReceived.getTotalQuantity() + grnDetail.getQuantity()));

                    //Đưa hàng vào trong kho
                    dao.addGoodstoPosition(goodsReceived);
                }

                transaction.commit();
                return true;
            }

            //Trường hợp xác nhận muộn do giao hàng muộn hoặc sớm

            //Xóa toàn bộ chi tiết phiếu nhập tạo lại
            //Tìm xem hàng có nằm trong phiếu nhập nào khác không
            TypedQuery<GrnDetail> query = entityManager.createQuery("Select g from GrnDetail g join g.goodsGRNDetail go WHERE go.sku = ?1", GrnDetail.class);

            //Lấy danh sách chi tiết phiếu nhập
            Set<GrnDetail> set = newGrn.getGrnDetailsSet();

            for (GrnDetail grnDetail : set) {
                String sku = grnDetail.getGoodsGRNDetail().getSku();
//                System.out.println(sku);
                this.entityManager.remove(grnDetail);
                query.setParameter(1, sku);

                //Nếu hàng không nằm trong phiếu nhập nào khác thì xóa hàng
                if (query.getResultList().isEmpty()) {
//                    System.out.println(sku);
                    Goods goods = entityManager.find(Goods.class, sku);
                    entityManager.remove(goods);
                }
            }

            entityManager.find(GoodsReceivedNote.class, grn.getGrnId());

            //Set để lưu tạm trái cây
            Set<Goods> goodsSet = new HashSet<>();

            //Set chứa chi tiết đơn nhập
            Set<GrnDetail> grnDetailSet = grn.getGrnDetailsSet();

            for (GrnDetail item : grnDetailSet) {
                //Khởi tạo traiCay
                Goods goods = new Goods();

                //Tìm trái cây đã tồn tại chưa
                //entityManager quản lý: TraiCay
                goods = item.getGoodsGRNDetail();
//                System.out.println("\\//");
//                System.out.println(goods);

                Set<GrnDetail> gSet = new HashSet<GrnDetail>();
                item.setGrnDetail(grn);
                gSet.add(item);

                goods.setGrnDetailSet(gSet);
//                System.out.println("----------------------------");
//                System.out.println(skuGenerator.getSKU(goods));
                goods = entityManager.find(Goods.class, skuGenerator.getSKU(goods));

//                System.out.println(goods);
                //Nếu chưa tồn tại thì
                //Lưu ---> traicay
                //entityManager quản lý: TraiCay
                if (goods == null) {
                    item.getGoodsGRNDetail().setSku(null);
//                    System.out.println(item.getGoodsGRNDetail());
                    entityManager.persist(item.getGoodsGRNDetail());
                    goods = item.getGoodsGRNDetail();
//                    System.out.println(goods);
                    goods.setStatus("P");

                    //Ngày chín ... ở front end
                }

                //Thêm: traicay được quản lý vào set
                goodsSet.add(goods);

                //Nối chi tiết đơn nhập với: donnhaphang và traicay được quản lý
                item.setGoodsGRNDetail(goods);
                item.setGrnDetail(newGrn);

                //Xác định khóa
                GrnDetailKey pk = GrnDetailKey.builder()
                        .grnID(grn.getGrnId())
                        .skuGRN(goods.getSku())
                        .build();
                item.setId(pk);

                //Lưu ---> chitietdonnhap
                //entityManager quản lý: ChiTietDonNhap
                entityManager.persist(item);

            }

            //Dùng để đưa hàng vào trong kho
            DAOPosition dao = new DAOPositionImpl();

            Set<GrnDetail> grnSet = newGrn.getGrnDetailsSet();

            //Duyệt các chi tiết đơn nhập
            for (GrnDetail grnDetail : grnSet) {

                //Tìm hàng tương ứng
                Goods goodsReceived = entityManager.find(Goods.class, grnDetail.getGoodsGRNDetail().getSku());

                //Nếu hàng chưa nhập bao giờ
                if (goodsReceived.getStatus().equals("P")) {
                    //Cập nhật trạng thái
                    goodsReceived.setStatus(goodsReceived.getSku().substring(goodsReceived.getSku().length() - 2));
                }

                //Cộng số lượng vào
                goodsReceived.setAvailableQuantity((short) (goodsReceived.getAvailableQuantity() + grnDetail.getQuantity()));
                goodsReceived.setTotalQuantity((short) (goodsReceived.getTotalQuantity() + grnDetail.getQuantity()));

                //Đưa hàng vào trong kho
                dao.addGoodstoPosition(goodsReceived);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen())
                entityManager.close();
        }
        return false;
    }
}


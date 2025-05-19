package com.test;


import com.dao.*;
import com.entity.*;
import com.util.LocalDateMonthY;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test {
    private static void createPos() {
        short[] rows = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] cols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M"};
        String[] fts = {"DU1", "PA1", "RA1", "MA1"};
        DAOPositionImpl daoPosition = null;

        try {
            daoPosition = new DAOPositionImpl();
            /*
            Them du lieu vao position với chỉ số
            i: hàng
            j: cột
            k: stack
            index: mã loại trái cây
             */
            String index = null;
            for (int i = 0; i < rows.length; i++) {
                for (int j = 0; j < cols.length; j++) {
                    for (int k = 1; k <= 5; k++) {
                        if (i <= 4) {
                            if (j <= 5) {
                                index = fts[0];
                            } else {
                                index = fts[1];
                            }
                        } else {
                            if (j <= 5) {
                                index = fts[2];
                            } else {
                                index = fts[3];
                            }
                        }
                        FruitType ft = FruitType.builder()
                                .ftId(index).build();
                        Position pos = Position.builder()
                                .fruitType(ft)
                                .row(rows[i])
                                .col(cols[j])
                                .stack((short) k)
                                .build();
                        try {
                            daoPosition.createPosition(pos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //Test: Lấy vị trí trái cây
    private static void testFPConnect() {
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            Position pos = daoPosition.getPosition((short) 331);
            System.out.println(pos);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: mapping entity
    public static void testMapping(){
        try {
            DAOFruitTypeImpl daoFruitType = new DAOFruitTypeImpl();
            FruitType fruitType = daoFruitType.getFruitType("MA1");
            Set<Position> positionSet = fruitType.getPos();
            System.out.println(positionSet.size());
//            for(Position pos : positionSet){
//                System.out.println(pos.getPosId());
//            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Test: Xem hàng trong chức năng xử lý trái cây
    private static void testFI(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            ObservableList<Position> list = FXCollections.observableArrayList(daoPosition.getGoodsPositionsByStatus("D",(byte)1));
            for (Position p : list) {
                System.out.println(p);
                System.out.println(p.getGoodsPos().getSku());
                System.out.println(p.getFruitType().getUnitOfCalculation());
            }

            list = FXCollections.observableArrayList(daoPosition.getGoodsPositionsByStatus("OR",(byte)1));
            for (Position p : list) {
                System.out.println(p);
                System.out.println(p.getGoodsPos().getSku());
            }

            list = FXCollections.observableArrayList(daoPosition.getGoodsPositionsByStatus("RI",(byte)1));
            for (Position p : list) {
                System.out.println(p);
                System.out.println(p.getGoodsPos().getSku());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Thêm hàng vào vị trí
    private static void addGoodsToPos(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
//            System.out.println(daoPosition.countEmptyPosByFtType("MA1")+"");
            DAOGoodsImpl daoGoods = new DAOGoodsImpl();
            Goods goods = daoGoods.getGoods("MA1-079-S-231004-JR");
            System.out.println(goods);
            if(daoPosition.addGoodstoPosition(goods)){
                System.out.println("Thêm OK");
            } else {
                System.out.println("T.T");
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Dọn dẹp hàng
    public static void clearExpGoods(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            if (daoPosition.clearPositions()){
                System.out.println("Dọn dẹp thành công");
            } else {
                System.out.println("Dọn dẹp T.T");
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Đếm số lượng page max
    private static void testPageQuantity(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            System.out.println(daoPosition.pageQuantity("D")+"");
            System.out.println(daoPosition.pageQuantity("OR")+"");
            System.out.println(daoPosition.pageQuantity("RI")+"");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Lấy cột và hàng theo loại trái cây
    private static void testGetPositionsByFt(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            List<Position> list = daoPosition.getPositionsByFt("MA1");
            for (Position p : list) {
                System.out.println(p.getRow() + "\t" + p.getCol());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Lấy ds trái cây theo kệ
    private static void testGetPosByPosNumber(){
        try {
            DAOPositionImpl daoPosition = new DAOPositionImpl();
            List<Position> list = daoPosition.getPosByPosNumber(118);
            for (Position p : list) {
                System.out.println(p.getGoodsPos()+ "\t" + p.getRow() + "\t" + p.getCol());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Tìm hàng theo sku
    private static void getGoodsListBySKU(){
        try {
            DAOGoodsImpl daoGoods = new DAOGoodsImpl();
            List<Goods> list = daoGoods.getGoodsListBySKU("UR, JR-S", (byte) 1);
            System.out.println(daoGoods.getMaxPage());
            for (Goods goods : list) {
                System.out.println(goods);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Test: Tìm nhân viên theo số điện thoại
    private static void testFindUserByPhone(){
        try{
            DAOUserImpl daoUser = new DAOUserImpl();
            System.out.println(daoUser.findUserByPhone("0343825961"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test: Tìm khách hàng theo số điện thoại
    private static void testFindCustomerByPhone(){
        try{
            DAOCustomer daoCustomer = new DAOCustomerImpl();
            System.out.println(daoCustomer.findCustomerByPhone("0343257896"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test: Kiểm tra thông tin khách hàng có hợp lệ
    private static void testValidateCuIn(){
        try{
            DAOCustomer daoCustomer = new DAOCustomerImpl();
            Customer c = Customer.builder()
                    .cPhone("0343257896").cAddress("Hà Nội").build();
            if(daoCustomer.validateCuIn(c))
                System.out.println("Dữ liệu hợp lệ");
            else
                System.out.println("Dữ liệu không hợp lệ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test: DAOGoodsImpl: getGoodsToDelivery
    private static void testGetGoodsToDelivery(){
        try{
            DAOGoods daoGoods = new DAOGoodsImpl();
            Goods g = Goods.builder()
                    .sku("MA1-M")
                    .status("RI")
                    .build();
            Goods find = daoGoods.getGoodsToDelivery(g);
            System.out.println(find);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test: Xuất hàng
    private static void testCreateGDN(){
        try{
            DAOGoodsDeliveryNote dao = new DAOGoodsDeliveryNoteImpl();

            //Tìm hàng
            DAOGoods daoGoods = new DAOGoodsImpl();
            Goods g = Goods.builder()
                    .sku("MA1-079-M-JR")
                    .status("OR")
                    .build();
            Goods find = daoGoods.getGoodsToDelivery(g);

            Set<GdnDetail> set = new HashSet<GdnDetail>();
            GdnDetail gdnDetail = GdnDetail.builder()
                    .goodsGDNDetail(find)
                    .quantity((short)1)
                    .discount(BigDecimal.valueOf(0))
                    .build();

            set.add(gdnDetail);

            LocalDate now = LocalDate.now();

            Customer c = Customer.builder()
                    .cName("Nguyễn Vân Anh")
                    .cPhone("0384177953")
                    //.cPhone("0345879621")
                    .cAdddate(Timestamp.valueOf(now.atStartOfDay()))
                    .cAddress("Hà Nội")
                    .build();


            DAOUserImpl daoUser = new DAOUserImpl();
            User s = daoUser.findUserByPhone("0343806801");

            GoodsDeliveryNote gdn = GoodsDeliveryNote.builder()
                    .gdnAddress("298 đường Cầu Diễn, quận Bắc Từ Liêm, thành phố Hà Nội")
                    .gdnDate(Date.valueOf(now))
                    .totalDeliveryPrice(BigDecimal.valueOf(270000.0))
                    .gdnDetailsSet(set)
                    .customer(c)
                    .deliveryUser(s)
                    .build();

            if(dao.createGDN(gdn)){
                System.out.println("Lưu thành công");
                HashMap<String, List<String>> map = dao.getGoodsDeliveryPos();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    List<String> values = entry.getValue();

                    System.out.println("Key: " + key);

                    for (String value : values) {
                        System.out.println("  Value: " + value);
                    }
                }
            }
            else
                System.out.println("Lưu thất bại");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testUpdateCustomer(){
        try{
            DAOCustomer daoCustomer = new DAOCustomerImpl();
            Customer c = Customer.builder()
                    .cId("CU-0000000001")
                    .cPhone("0343257896")
//                    .cEmail("kdthinh04@gmail.com")
                    .cAddress("Hà Nội")
                    .build();
            if(daoCustomer.updateCustomer(c))
                System.out.println("Cập nhật khách hàng thành công");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Test đăng nhập
    private static void testLogin(){
        try{
            DAOUser dao = new DAOUserImpl();
            User u = User.builder().username("administrator").password("abc123!").build();
            User u2 = dao.checkLogin(u);
            System.out.println(u2);
            if(u2!=null){
                if(dao.loginUser(u)){
                    System.out.println("Đăng nhập thành công");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Test đăng nhập
    private static void testLogout(){
        try{
            DAOUser dao = new DAOUserImpl();
            User u = User.builder().username("administrator").build();
            if(dao.logoutUser(u)){
                System.out.println("Đăng xuất thành công");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test hủy đơn chưa nhập
    private static void testDeleteGRN(String txt){
        try{
            DAOGoodsReceivedNote dao = new DAOGoodsReceivedNoteImpl();
            if(dao.deleteGRN(txt)){
                System.out.println("Xoa thanh cong");
            } else {
                System.out.println("Xóa thất bại");
            }
        } catch (Exception e){

        }
    }


    //Test hủy đơn chưa nhập
    private static void testAddUser(){
        try{
            String dateString = "03/12/2004";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Date date = Date.valueOf(LocalDate.parse(dateString, formatter));

            DAOUser dao = new DAOUserImpl();
            User u = User.builder()
                    .username("hganh04")
                    .password("abc123!")
                    .fullname("Hoàng Gia Anh")
                    .bod(date)
                    .gender(false)
                    .address("Hà Nội")
                    .phone("0343589761")
                    .citizenId("001204057896")
                    .hometown("Thành phố Hà Nội")
                    .salary(BigDecimal.TEN)
                    .startDate(Date.valueOf(LocalDate.now()))
                    .endDate(Date.valueOf(LocalDate.parse("03/09/2112", formatter)))
                    .status("FI")
                    .build();
            if(dao.addUser(u)){
                System.out.println("Thêm thanh cong");
            } else {
                System.out.println("Thêm thất bại");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testGetInventory(){
        try{
            DAOPosition dao = new DAOPositionImpl();
            dao.getInventory();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testGetRevenueAndProfit(){
        try{
            DAOPosition dao = new DAOPositionImpl();
            dao.getProfit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testUpdateSatusAuto(){
        try {
            DAOGoods dao = new DAOGoodsImpl();
            dao.updateStatusAuto();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra lưu trái cây
    private static void testCreateGRN(){
        try{
            //Lấy nhà cung cấp
            DAOSupplier daoSupplier = new DAOSupplierImpl();
            Supplier supplier = daoSupplier.getSuppliersBySupplierName("T13F").get(0);

            //Lấy loại trái cây ở đầu
            DAOFruitType daoFruitType = new DAOFruitTypeImpl();
            FruitType fruitType = daoFruitType.getFruitType("MA1");

            LocalDate grn_date = LocalDate.of(2024, 12, 7);

            //Tạo trái cây 1
            //(Loại trái cây --> Trái cây)
            Goods goods = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("M")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.TEN)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();

            /*
            //Tạo trái cây 2
            //(Loại trái cây --> Trái cây)
            Goods goods2 = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("S")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.TEN)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();
            */

            //Tạo set để lưu được trái cây vào nhà cung cấp
            //Lưu vào bảng nhacungcap_traicay
            //(Trái cây --> Nhà cung cấp)
//            Set<Traicay> traiCaySet = new HashSet<>();
//            traiCaySet.add(tc);
//            ncc.setTraiCayNhaCungCapSet(traiCaySet);

            //Tạo id chi tiết đơn nhập
            //ChitietdonnhapPK chitietdonnhapPK = ChitietdonnhapPK.builder().build();

            //Tạo đơn nhập hàng
            //(Nhà cung cấp --> Đơn nhập hàng
            GoodsReceivedNote grn = GoodsReceivedNote.builder()
                    .supplier(supplier)
                    .grnDate(Timestamp.valueOf(grn_date.atStartOfDay()))
                    .totalReceivedPrice(BigDecimal.TEN)
                    .build();

            //Tạo chi tiết đơn nhập
            //(Trái cây --> Chi tiết đơn nhập)
            GrnDetail grnDetail1 = GrnDetail.builder()
                    .quantity((short)20)
                    .goodsGRNDetail(goods)
                    .build();

            /*
            GrnDetail grnDetail2 = GrnDetail.builder()
                    .quantity((short)10)
                    .goodsGRNDetail(goods2)
                    .build();
            */

            //Tạo set để lưu được chi tiết đơn nhập trong đơn nhập hàng
            //(Chi tiết đơn nhập --> Đơn nhập hàng)
            Set<GrnDetail> grnDetailSet = new HashSet<>();
            grnDetailSet.add(grnDetail1);
//            grnDetailSet.add(grnDetail2);

            grn.setGrnDetailsSet(grnDetailSet);

            DAOGoodsReceivedNote dao = new DAOGoodsReceivedNoteImpl();

            if (dao.createGRN(grn))
                System.out.println("Tạo đơn nhập thành công");
            else
                System.out.println("Tạo đơn nhập không thành công");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test cập nhật phiếu nhập khi ngày thay đổi
    public static void testUpdateGRNCase1(String txt){
        try{
            DAOGoodsReceivedNote dao = new DAOGoodsReceivedNoteImpl();
            GoodsReceivedNote grn = dao.getGRNByID(txt);

            grn.setGrnDate(Timestamp.valueOf(LocalDate.of(2024, 12, 12).atStartOfDay()));
            if(dao.updateGRN(grn, false)){
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test cập nhật phiếu nhập khi thay đổi chi tiết phiếu nhập
    public static void testUpdateGRNCase2(String txt){
        try{
            DAOGoodsReceivedNote dao = new DAOGoodsReceivedNoteImpl();
            GoodsReceivedNote grn = dao.getGRNByID(txt);

            //Lấy loại trái cây ở đầu
            DAOFruitType daoFruitType = new DAOFruitTypeImpl();
            FruitType fruitType = daoFruitType.getFruitType("PA1");

            LocalDate grn_date = LocalDate.of(2024, 12, 7);

            //Tạo trái cây 1
            //(Loại trái cây --> Trái cây)
            Goods goods = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("M")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.TEN)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();

            /*
            //Tạo trái cây 2
            //(Loại trái cây --> Trái cây)
            Goods goods2 = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("S")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.TEN)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();
            */

            //Tạo chi tiết đơn nhập
            //(Trái cây --> Chi tiết đơn nhập)
            GrnDetail grnDetail1 = GrnDetail.builder()
                    .quantity((short)20)
                    .goodsGRNDetail(goods)
                    .build();

            /*
            GrnDetail grnDetail2 = GrnDetail.builder()
                    .quantity((short)10)
                    .goodsGRNDetail(goods2)
                    .build();
            */

            //Tạo set để lưu được chi tiết đơn nhập trong đơn nhập hàng
            //(Chi tiết đơn nhập --> Đơn nhập hàng)
            Set<GrnDetail> grnDetailSet = new HashSet<>();
            grnDetailSet.add(grnDetail1);
//            grnDetailSet.add(grnDetail2);

            grn.setGrnDetailsSet(grnDetailSet);

            if(dao.updateGRN(grn, false)){
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test cập nhật phiếu nhập khi thay đổi chi tiết phiếu nhập
    public static void testUpdateGRNCase3(String txt){
        try{
            DAOGoodsReceivedNote dao = new DAOGoodsReceivedNoteImpl();
            GoodsReceivedNote grn = dao.getGRNByID(txt);

            //Lấy loại trái cây ở đầu
            DAOFruitType daoFruitType = new DAOFruitTypeImpl();
            FruitType fruitType = daoFruitType.getFruitType("PA1");

            LocalDate grn_date = LocalDate.of(2024, 12, 7);

            //Tạo trái cây 1
            //(Loại trái cây --> Trái cây)
            Goods goods = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("M")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.ONE)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();

            /*
            //Tạo trái cây 2
            //(Loại trái cây --> Trái cây)
            Goods goods2 = Goods.builder()
                    .goodsFruitType(fruitType)
                    .size("S")
                    .status("UR")
                    .receivedPrice(BigDecimal.TEN)
                    .deliveryPrice(BigDecimal.TEN)
                    .original("Hà Nam")
                    .justRipeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToJustRipe())))
                    .ripeDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToRipe())))
                    .overripDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToOverripe())))
                    .expDate(Date.valueOf(grn_date.plusDays(fruitType.getTimeToDamaged())))
                    .quantityInBox(BigDecimal.TEN)
                    .availableQuantity((short) 0)
                    .totalQuantity((short) 0)
                    .build();
            */

            //Tạo chi tiết đơn nhập
            //(Trái cây --> Chi tiết đơn nhập)
            GrnDetail grnDetail1 = GrnDetail.builder()
                    .quantity((short)20)
                    .goodsGRNDetail(goods)
                    .build();

            /*
            GrnDetail grnDetail2 = GrnDetail.builder()
                    .quantity((short)10)
                    .goodsGRNDetail(goods2)
                    .build();
            */

            //Tạo set để lưu được chi tiết đơn nhập trong đơn nhập hàng
            //(Chi tiết đơn nhập --> Đơn nhập hàng)
            Set<GrnDetail> grnDetailSet = new HashSet<>();
            grnDetailSet.add(grnDetail1);
//            grnDetailSet.add(grnDetail2);

            grn.setGrnDetailsSet(grnDetailSet);

            if(dao.updateGRN(grn, true)){
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String a = "MA1-079-S-231004-JR";
//        System.out.println(a.substring(0, 3));

        try {
//            testGetPositionsByFt();
//            testGetPosByPosNumber();
//            clearExpGoods();
//            addGoodsToPos();
//            getGoodsListBySKU();
//            testFindUserByPhone();
//            testFindCustomerByPhone();

//            testValidateCuIn();
//            testGetGoodsToDelivery();

//            testCreateGDN();

//            testUpdateCustomer();

//            testLogin();
//            testLogin();
//            testLogout();

//            String dateString = "03/12/2024";
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            Date date = Date.valueOf(LocalDate.parse(dateString, formatter));
//            GoodsDeliveryNote gdn = GoodsDeliveryNote.builder().gdnDate(date).build();
//            System.out.println(date);
//            System.out.println(gdn);

//            testAddUser();

//            testGetInventory();
//            testGetRevenueAndProfit();

//            testUpdateSatusAuto();
            testCreateGRN();
            testCreateGRN();
//            testDeleteGRN("PN071224047690");
//            testDeleteGRN("PN071224787681");

//            testUpdateGRNCase1("PN071224102223");
//            testUpdateGRNCase2("PN071224593886");
//            testUpdateGRNCase3("PN071224593886");

            /*
            Goods g = Goods.builder().sku("A").build();
            FruitType a = FruitType.builder().ftId("FA").build();
            Set<Goods> goodsSet = new HashSet<>();
            goodsSet.add(g);
            a.setGoodsSet(goodsSet);

            FruitType b = a;
            System.out.println(b.getGoodsSet().size());
            System.out.println(b.getFtId());

            a.setFtId("GB");
            System.out.println(b.getFtId());

            b.setFtId("KO");
            System.out.println(a.getFtId());
             */

        } catch (Exception ex){
            ex.printStackTrace();
        }
        System.exit(0);
    }
}

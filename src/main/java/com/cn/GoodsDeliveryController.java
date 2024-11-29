package com.cn;

import com.entity.*;
import com.util.FruitUtil;
import com.util.ObserverObject;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GoodsDeliveryController {

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private TableColumn<Goods, Short> col_available_quantity;

    @FXML
    private TableColumn<Goods, BigDecimal> col_deliveryPrice;

    @FXML
    private TableColumn<Goods, String> col_original;

    @FXML
    private TableColumn<Goods, BigDecimal> col_qinbox;

    @FXML
    private TableColumn<Goods, String> col_size;

    @FXML
    private TableColumn<Goods, String> col_sku;

    @FXML
    private TableColumn<Goods, String> col_status;

    @FXML
    private BorderPane deliveryPane;

    @FXML
    private GridPane gridPaneGD;

    @FXML
    private Label lblPage;

    @FXML
    private Label lbl_amount_payable;

    @FXML
    private Label lbl_total_price;

    @FXML
    private TextField txt_bonusDiscount;

    @FXML
    private TableView<Goods> tbl_findGoods;

    @FXML
    private TextField txt_c_address;

    @FXML
    private TextField txt_c_email;

    @FXML
    private TextField txt_c_id;

    @FXML
    private TextField txt_c_name;

    @FXML
    private TextField txt_c_phone;

    @FXML
    private TextField txt_gdn_address;

    @FXML
    private TextField txt_skuString;

    @FXML
    private TextArea txt_des;

    //Thuộc tính: Tiện ích
    private FruitUtil dict = new FruitUtil();

    //Thuộc tính: Danh sách trái cây cần tìm
    private ObservableList<Goods> listFG;

    //Thuộc tính: Khách hàng cần xuất hàng
    private Customer customer;

    //Thuộc tính phiếu xuất
    private GoodsDeliveryNote gdn;

    //Thuộc tính page
    private byte page = 1;
    private byte maxPage = -1;

    //Thuộc tính % giảm giá tổng
    private double discount;

    //Thuộc tính cờ tổng trước khi xuất
    private boolean deliveryFlag = false;

    //Kiểm tra: sku có hợp lệ không
    private boolean validateSkuString(String skuString) {
        skuString = skuString.toUpperCase().trim();
        if (skuString.trim().isEmpty())
            return false;
        if (!skuString.matches("[0-9A-Z,\\- ]*"))
            return false;

        //Chia skuList thành các sku theo dấu ,
        String[] skuList = skuString.split(",");

        //Mảng này chứa thành phần của sku
        String[] skuItem = null;

        //Duyêt các SKU
        for (String s : skuList) {
            //sku độ dài lớn hơn 22 là sai
            if (s.length() > 22) {
                return false;
            } else {
                //Chia thành các phần nhỏ của sku bằng dấu gạch nối
                skuItem = s.trim().split("-");

                //số thành phần lớn hơn 5 là sai
                if (skuItem.length > 5) {
                    return false;
                }
            }
        }

        return true;
    }

    //Tìm hàng
    public void findGoods() throws RemoteException {
        if (!validateSkuString(txt_skuString.getText())) {
            dict.showAlertError("Chuỗi SKU không hợp lệ");
            return;
        }
        this.listFG = FXCollections.observableArrayList(this.registryClass.goods().getGoodsListBySKU(txt_skuString.getText(), this.page));
        this.tbl_findGoods.setItems(this.listFG);
        this.maxPage = this.registryClass.goods().getMaxPage();
    }

    //Làm mới tìm hàng
    public void refreshFindGoodsTable() throws RemoteException {
        this.txt_skuString.clear();
        this.listFG = FXCollections.observableArrayList(this.registryClass.goods().getGoodsListBySKU("UR, JR", this.page));
        setInforFindGoodsTable(this.listFG);
        this.maxPage = this.registryClass.goods().getMaxPage();
    }

    //Chen dữ liệu vào bảng: Tìm trái cây
    public void setInforFindGoodsTable(ObservableList<Goods> list) {
        this.col_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
        this.col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        this.col_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.encode(((Goods) cellData.getValue()).getStatus()));
        });
        this.col_deliveryPrice.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Goods) cellData.getValue()).getDeliveryPrice());
        });
        this.col_original.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Goods) cellData.getValue()).getOriginal());
        });
        this.col_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Goods) cellData.getValue()).getQuantityInBox());
        });
        this.col_available_quantity.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<Short>(((Goods) cellData.getValue()).getAvailableQuantity());
        });
        this.tbl_findGoods.setItems(list);
    }

    //Hàm chuyển về trang trước
    public void prev_Page() throws RemoteException {
        //Nếu là trang đầu tiên thì không thể tiến
        if (lblPage.getText().equalsIgnoreCase("1"))
            return;

        //Giảm số trang đi 1
        this.page = (byte) (Byte.parseByte(lblPage.getText()) - 1);
        this.lblPage.setText(this.page+"");

        //Load lại bảng
        this.listFG = FXCollections.observableArrayList(this.registryClass.goods().getGoodsListBySKU(txt_skuString.getText(), this.page));
        this.tbl_findGoods.setItems(listFG);
        //setInforEGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG)));
    }

    //Hàm chuyển về trang sau
    public void next_Page() throws RemoteException {

        //Nếu là trang cuối thì không thể lùi
        if (lblPage.getText().equalsIgnoreCase(this.maxPage+""))
            return;

        //Tăng số trang lên 1
        this.page = (byte) (Byte.parseByte(lblPage.getText()) + 1);
        this.lblPage.setText(this.page+"");

        //Load lại bảng
        this.listFG = FXCollections.observableArrayList(this.registryClass.goods().getGoodsListBySKU(txt_skuString.getText(), this.page));
        this.tbl_findGoods.setItems(listFG);
        //setInforEGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG)));
    }

    //Làm sạch các thẻ xuất hàng
    private void clearGridPaneGD(){
        this.gridPaneGD.getChildren().clear();
        this.gridPaneGD.getRowConstraints().clear();
        this.gridPaneGD.getColumnConstraints().clear();
    }

    private short row = 0;

    //Thuộc tính chứa các controller
    private List<CardGoodsDeliveryController> listCard = new ArrayList<>();

    //Thuộc tính ObserverObject
    private ObserverObject obs;

    //Thêm thẻ xuất hàng
    public void addCardGD(){
        //Chèn CardGD vào Grid
        try{
            if (!listCard.isEmpty())
                listCard.getLast().checkGoods();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/com/cn/CardGoodsDelivery.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            CardGoodsDeliveryController controller = (CardGoodsDeliveryController) loader.getController();

            //Thêm card vào list
            listCard.add(controller);

            //Thêm list,grid, pane vào controller
            controller.setListCard(this.listCard);
            controller.setGrid(gridPaneGD);
            controller.setPane(pane);

            //Thêm list vào obs
            obs.setList(listCard);

            //Thêm obs vào controller
            controller.setObserverObject(obs);

            this.gridPaneGD.add(pane, 0, row++);
            GridPane.setMargin(pane, new Insets(3,0,10,0));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkQuantity(){
//        System.out.println(this.listCard.size());
//        System.out.println(this.gridPaneGD.getChildren().size());
    }

    //Xóa thẻ xuất hàng
    private void removeCardGD(Object obj){
        try{
            this.gridPaneGD.getChildren().remove(obj);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Method: Kiểm tra các dữ liệu khách hàng có hợp lệ không
    private boolean validateCustomer(){
        /*
        if (this.txt_c_name.getText().trim().equals("") || this.txt_c_phone.getText().trim().equals("") || this.txt_c_address.getText().trim().equals("") || this.txt_gdn_address.getText().trim().equals("")){
            this.dict.showAlertError("Hãy điền đầy đủ thông tin");
            return false;
        }

        //Tên phải là chữ cái từ 5 đến 40 ký tự
        if(!this.txt_c_name.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ ]{5,40}")){
            this.dict.showAlertError("Tên không hợp lệ");
            return false;
        }

        //Điện thoại 10 số
        if(!this.txt_c_phone.getText().trim().matches("[0-9]{10}")){
            this.dict.showAlertError("Số điện thoại không hợp lệ");
            return false;
        }

        //Email hợp lệ từ 10 đến 150 ký tự
        //Chuẩn ___@___.com
        if(!this.txt_c_email.getText().trim().equals("") && !this.txt_c_email.getText().trim().matches("^\\w+@\\w+\\.\\w+${10,150}")){
            this.dict.showAlertError("Email không hợp lệ");
            return false;
        }
        */

        //Địa chỉ từ 6 - 255 ký tự
        //Chấp nhận chữ, số, dấu phẩy, dấu cách, dấu /
        if(!this.txt_c_address.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ0-9,/ ]{6,255}")){
            this.dict.showAlertError("Địa chỉ không hợp lệ");
            return false;
        }

        //Địa chỉ từ 6 - 255 ký tự
        //Chấp nhận chữ, số, dấu phẩy, dấu cách, dấu /
        if(!this.txt_gdn_address.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ0-9,/ ]{6,255}")){
            this.dict.showAlertError("Địa chỉ nhận hàng không hợp lệ");
            return false;
        }

        return true;
    }

    //Method: Chuyển sang phần xuất hàng
    //Button: Tiếp theo
    public void navigateToDelivery() throws RemoteException {
        //Tìm khách hàng theo số điện thoại
        this.customer = registryClass.customer().findCustomerByPhone(txt_c_phone.getText());

        //Tìm thấy
        if (customer != null){

            //Set các giá trị và chuyển hướng
            lockTxtCustomer(this.customer);
            gdn = GoodsDeliveryNote.builder()
                    .gdnAddress(this.txt_gdn_address.getText().trim())
                    .customer(customer)
                    .build();
        } else {
            //Không tìm thấy
            //Kiểm tra hợp lệ dữ liệu
            if(!validateCustomer())
                return;

            //Set giá trị cho customer
            customer = Customer.builder()
                    .cName(this.txt_c_name.getText().trim())
                    .cPhone(this.txt_c_phone.getText().trim())
                    .cEmail(this.txt_c_email.getText().trim())
                    .cAddress(this.txt_c_address.getText().trim())
                    .build();

            //Dữ liệu không hợp lệ so với cơ sở dữ liệu (nếu có)
            if(!registryClass.customer().validateCuIn(customer)){
                this.dict.showAlertError("Hãy kiểm tra lại thông tin");
                return;
            }

            //Set giá trị địa chỉ nhận đơn hàng
            gdn = GoodsDeliveryNote.builder()
                    .gdnAddress(this.txt_gdn_address.getText().trim())
                    .customer(customer)
                    .build();
        }

        //Chuyển màn hình
        this.deliveryPane.setVisible(true);

        //Chuyển màu vòng tròn
        exchangeColor(true);
    }

    //Method: Set các thông tin của khách hàng có tồn tại trong csdl
    //Button: "V" trong màn hinh nhập thông tin khách hàng
    public void getInfoCustomer() throws RemoteException {
        //Tìm khách hàng theo số điện thoại
        this.customer = registryClass.customer().findCustomerByPhone(txt_c_phone.getText());
        if (customer == null){
            this.txt_c_id.clear();
            this.txt_c_name.setEditable(true);
            this.txt_c_name.clear();
            this.txt_c_email.clear();
            this.txt_c_address.clear();
            this.txt_gdn_address.clear();
            dict.showAlertError("Không tìm thấy khách hàng có số điện thoại này!");
            return;
        }
        lockTxtCustomer(this.customer);
    }

    //Hàm set các giá trị cho txt của khách và không cho sửa
    private void lockTxtCustomer(Customer c){
        this.txt_c_id.setText(customer.getCId());
        this.txt_c_name.setText(customer.getCName());
        this.txt_c_phone.setText(customer.getCPhone());
        this.txt_c_email.setText(customer.getCEmail());
        this.txt_c_address.setText(customer.getCAddress());
        this.txt_gdn_address.setText(customer.getCAddress());

        this.txt_c_id.setEditable(false);
        this.txt_c_name.setEditable(false);
        this.txt_c_phone.setEditable(false);
        this.txt_c_email.setEditable(false);
        this.txt_c_address.setEditable(false);
    }

    //Hàm chuyển màu 2 vòng tròn
    private void exchangeColor(boolean flag){
        if (flag){
            circle1.getStyleClass().remove("circle_color1");
            circle1.getStyleClass().add("circle_color2");
            circle2.getStyleClass().remove("circle_color2");
            circle2.getStyleClass().add("circle_color1");
        } else {
            circle1.getStyleClass().remove("circle_color2");
            circle1.getStyleClass().add("circle_color1");
            circle2.getStyleClass().remove("circle_color1");
            circle2.getStyleClass().add("circle_color2");
        }
    }

    //Method: Hủy tạo đơn xuất
    //Load lại trang (Chưa xử lý)
    public void cancleGoodsDelivery(){
//        this.listCard.clear();
//        this.clearGridPaneGD();
//        this.deliveryPane.setVisible(false);
//        exchangeColor(false);
//        unlockTxtCustomer();
        gdn = new GoodsDeliveryNote();
        Support.navigateTo((byte) 2, this);
    }

    //Method: Unlock thông tin khách hàng khi hủy tạo đơn
    //Hàm set các giá trị cho txt của khách và không cho sửa
//    private void unlockTxtCustomer(){
//        this.txt_c_id.clear();
//        this.txt_c_name.clear();
//        this.txt_c_phone.clear();
//        this.txt_c_email.clear();
//        this.txt_c_address.clear();
//        this.txt_gdn_address.clear();
//
//        this.txt_c_name.setEditable(true);
//        this.txt_c_phone.setEditable(true);
//        this.txt_c_email.setEditable(true);
//        this.txt_c_address.setEditable(true);
//    }

    //Method: Kiểm tra giá trị giảm giá nhập vào
    private boolean validateGoodsDiscount(){
        try{
            discount = Double.parseDouble(txt_bonusDiscount.getText());
            if (discount<0 || discount>100){
                this.txt_bonusDiscount.setText("0.0");
                this.lbl_amount_payable.setText(this.lbl_total_price.getText());
                return false;
            }
        } catch (NumberFormatException e){
            this.txt_bonusDiscount.setText("0.0");
            this.lbl_amount_payable.setText(this.lbl_total_price.getText());
            return false;
        }
        return true;
    }

    //Method: set giá phải trả khi nhập giảm giá
    public void updateAmountPayable(){
        //Nếu giảm giá không hợp lệ thì đưa về mặc định
        if(!validateGoodsDiscount())
            return;

        //Chạy đoạn new do đang test
        this.gdn = new GoodsDeliveryNote();

        //Số tiền phải trả = tổng tiền * (1 - giảm giá%)
        this.gdn.setTotalDeliveryPrice(BigDecimal.valueOf(Double.parseDouble(lbl_total_price.getText())).multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(Double.parseDouble(txt_bonusDiscount.getText())).divide(BigDecimal.valueOf(100)))).setScale(2, BigDecimal.ROUND_HALF_UP));
        lbl_amount_payable.setText(this.gdn.getTotalDeliveryPrice()+"");
    }

    //Method: lock ghi chú và giảm giá
    private void lockDelivery(){
        txt_des.setEditable(false);
        txt_bonusDiscount.setEditable(false);
    }

    //Method: xuất hàng
    public void delivery() throws RemoteException {
        //Nếu cờ tổng: false
        if(!deliveryFlag){

            //Gán cờ tổng là: true
            deliveryFlag = true;

            //Kiểm tra toàn bộ các controller xem các thẻ đã hợp lệ
            for (CardGoodsDeliveryController item : listCard){
                item.checkGoods();

                //Có thẻ không hợp lệ
                if(!item.isFlag())
                    //Cờ là: false
                    deliveryFlag = false;
            }

            if(deliveryFlag){
                dict.showAlertInfo("Các thông tin đã hợp lệ.\nHãy kiểm tra các thông tin và kích nút \"Xuất phiếu\"");
            }
        } else{
            //Cờ tổng là: true
            //Kiểm tra toàn bộ các controller lần cuối xem các thẻ đã hợp lệ (người dùng có sửa gì không)
            for (CardGoodsDeliveryController item : listCard){
                item.checkGoods();

                //Có thẻ không hợp lệ
                if(!item.isFlag())
                    //Cờ là: false
                    deliveryFlag = false;
            }

            if(deliveryFlag){
                //Khóa các trường thông tin lại nếu các giá trị đã đúng
                lockDelivery();
                Set<GdnDetail> set = new HashSet<>();
                for (CardGoodsDeliveryController item : listCard){
                    item.lockEntryData();
                    set.add(item.getGdnDetail());
                }

                if(dict.showAlertWarning("Bạn có chắc chắn muốn xuất phiếu không")){
                    //User trống do chưa đăng nhập nên chưa lấy được
                    //User ở util
                    //Lấy tạm user

                    /*
                    User s = registryClass.user().findUserByPhone("0343806801");
                    gdn.setDeliveryUser(s);
                    gdn.setGdnDate(Date.valueOf(LocalDate.now()));
                    gdn.setTotalDeliveryPrice(BigDecimal.valueOf(Double.parseDouble(lbl_amount_payable.getText())));
                    gdn.setGdnDetailsSet(set);

                    registryClass.goodsDeliveryNote().createGDN(gdn);
                    */
                    dict.showAlertInfo("Xuất phiếu thành công");


                    //Reset về phiếu xuất
                    gdn = new GoodsDeliveryNote();
                }
            }
        }

    }

    public void navigateToFIPage() throws RemoteException  {
        Support.navigateTo((byte) 3, this);
    }

    //Các thuộc tính đặc biệt
    private RegistryClass registryClass;

    {
        try {
            registryClass = new RegistryClass();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public GoodsDeliveryController(){

    }

    @FXML
    public void initialize() throws RemoteException {
        clearGridPaneGD();
//        addCardGD();
//        addCardGD();
//        addCardGD();
//        addCardGD();
//        addCardGD();
        refreshFindGoodsTable();
        updateAmountPayable();
        circle1.getStyleClass().add("circle_color1");
        circle2.getStyleClass().add("circle_color2");

        obs = ObserverObject.builder()
                .gdn(gdn)
                .lbl_total_price(lbl_total_price)
                .txt_bonusDiscount(txt_bonusDiscount)
                .lbl_amount_payable(lbl_amount_payable)
                .build();
    }
}


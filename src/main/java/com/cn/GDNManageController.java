package com.cn;

import com.entity.GdnDetail;
import com.entity.Goods;
import com.entity.GoodsDeliveryNote;
import com.entity.User;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;

public class GDNManageController {

    @FXML
    private TableColumn<GoodsDeliveryNote, String> col_c_name;

    @FXML
    private TableColumn<GdnDetail, BigDecimal> col_delivery_price;

    @FXML
    private TableColumn<GdnDetail, String> col_discount;

    @FXML
    private TableColumn<GdnDetail, String> col_ft;

    @FXML
    private TableColumn<GoodsDeliveryNote, String> col_gdn_addr;

    @FXML
    private TableColumn<GoodsDeliveryNote, String> col_gdn_date;

    @FXML
    private TableColumn<GoodsDeliveryNote, String> col_gdn_id;

    @FXML
    private TableColumn<GdnDetail, Integer> col_index;

    @FXML
    private TableColumn<GdnDetail, String> col_original;

    @FXML
    private TableColumn<GdnDetail, String> col_qinbox;

    @FXML
    private TableColumn<GdnDetail, Short> col_quantity;

    @FXML
    private TableColumn<GdnDetail, String> col_sku;

    @FXML
    private TableColumn<GoodsDeliveryNote, BigDecimal> col_total_price;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lbl_c_id;

    @FXML
    private Label lbl_c_name;

    @FXML
    private Label lbl_c_phone;

    @FXML
    private Label lbl_gdn_addr;

    @FXML
    private Label lbl_gdn_date;

    @FXML
    private Label lbl_gdn_id;

    @FXML
    private Label lbl_staff;

    @FXML
    private Label lbl_total_price;

    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab tab_gdn_info;

    @FXML
    private TableView<GoodsDeliveryNote> tbl_GDN;

    @FXML
    private TableView<GdnDetail> tbl_GDN_de;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextArea txt_gdn_des;

    private ObservableList<GoodsDeliveryNote> listGDN;
    private ObservableList<GdnDetail> listGdnDetail;

    private FruitUtil dict = new FruitUtil();

    public void clickBtnFindStaff() throws RemoteException {
        try{
            //Chuyển text thành LocalDate để đúng định dạng và toString
            this.listGDN = FXCollections.observableArrayList(this.registryClass.goodsDeliveryNote().getGDNByKeyword(dict.convertDate(txtKeyWord.getText().trim()).toString()));
            tbl_GDN.setItems(listGDN);
        } catch (Exception e){
            //Nếu không phải ngày tháng năm thì xuống đây
            this.listGDN = FXCollections.observableArrayList(this.registryClass.goodsDeliveryNote().getGDNByKeyword(txtKeyWord.getText().trim()));
            tbl_GDN.setItems(listGDN);
        }
    }

    public void clickBtnRefresh(ActionEvent event) throws RemoteException {
        txtKeyWord.clear();
        this.listGDN = FXCollections.observableArrayList(this.registryClass.goodsDeliveryNote().getAllGDN());
        tbl_GDN.setItems(listGDN);
    }

    public void view() {
        GoodsDeliveryNote gdn = this.tbl_GDN.getSelectionModel().getSelectedItem();
        if(gdn == null)
            return;

        //Chuyển tab
        tab_pane.getSelectionModel().select(tab_gdn_info);

        this.listGdnDetail = FXCollections.observableArrayList(gdn.getGdnDetailsSet());
        tbl_GDN_de.setItems(listGdnDetail);

        lbl_gdn_id.setText(gdn.getGdnId());
        lbl_gdn_addr.setText(gdn.getGdnAddress());
        lbl_c_id.setText(gdn.getCustomer().getCId());
        lbl_c_name.setText(gdn.getCustomer().getCName());
        lbl_c_phone.setText(gdn.getCustomer().getCPhone());
        lbl_staff.setText(gdn.getDeliveryUser().getFullname());
        lbl_gdn_date.setText(dict.convertDate(gdn.getGdnDate()));
        txt_gdn_des.setText(gdn.getGdnDescription());
        lbl_total_price.setText(gdn.getTotalDeliveryPrice().toString());
    }

    //Đưa dữ liệu vào bảng
    public void setInforGDNTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_gdn_id.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((GoodsDeliveryNote) cellData.getValue()).getGdnId());
        });
        this.col_gdn_date.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((GoodsDeliveryNote) cellData.getValue()).getGdnDate()));
        });
        this.col_c_name.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty((((GoodsDeliveryNote) cellData.getValue()).getCustomer().getCName()));
        });
        this.col_total_price.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((GoodsDeliveryNote) cellData.getValue()).getTotalDeliveryPrice());
        });
        this.col_gdn_addr.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((GoodsDeliveryNote) cellData.getValue()).getGdnAddress());
        });
        this.tbl_GDN.setItems(listGDN);
    }

    //Đưa dữ liệu vào bảng
    public void setInforGdnDetailTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_index.setCellValueFactory((cellData) -> {
            return new SimpleIntegerProperty(listGdnDetail.indexOf(cellData.getValue()) + 1).asObject();
        });
        this.col_sku.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getSku());
        });
        this.col_ft.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getGoodsFruitType().getFtName());
        });
        this.col_original.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getOriginal()));
        });
        this.col_qinbox.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getQuantityInBox().toString() + " " + ((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getGoodsFruitType().getUnitOfCalculation()));
        });
        this.col_quantity.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<Short>(((GdnDetail) cellData.getValue()).getQuantity());
        });
        this.col_delivery_price.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<BigDecimal>(((GdnDetail) cellData.getValue()).getGoodsGDNDetail().getDeliveryPrice());
        });
        this.col_discount.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GdnDetail) cellData.getValue()).getDiscount().toString() +"%");
        });
        this.tbl_GDN_de.setItems(listGdnDetail);
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

    public GDNManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        this.listGDN = FXCollections.observableArrayList(this.registryClass.goodsDeliveryNote().getAllGDN());
        setInforGdnDetailTable();
        setInforGDNTable();
    }

}

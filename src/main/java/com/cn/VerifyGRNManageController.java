package com.cn;

import com.entity.GdnDetail;
import com.entity.GoodsDeliveryNote;
import com.entity.GoodsReceivedNote;
import com.entity.GrnDetail;
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
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class VerifyGRNManageController {

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_supplier_name;

    @FXML
    private TableColumn<GrnDetail, BigDecimal> col_received_price;

    @FXML
    private TableColumn<GrnDetail, String> col_status;

    @FXML
    private TableColumn<GrnDetail, String> col_ft;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_verify_date;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_date;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_id;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_staff;

    @FXML
    private TableColumn<GrnDetail, Integer> col_index;

    @FXML
    private TableColumn<GrnDetail, String> col_original;

    @FXML
    private TableColumn<GrnDetail, String> col_qinbox;

    @FXML
    private TableColumn<GrnDetail, Short> col_quantity;

    @FXML
    private TableColumn<GrnDetail, String> col_sku;

    @FXML
    private TableColumn<GoodsReceivedNote, BigDecimal> col_total_price;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lbl_sup_id;

    @FXML
    private Label lbl_sup_name;

    @FXML
    private Label lbl_sup_phone;

    @FXML
    private Label lbl_sup_addr;

    @FXML
    private Label lbl_verify_date;

    @FXML
    private Label lbl_grn_date;

    @FXML
    private Label lbl_grn_id;

    @FXML
    private Label lbl_staff;

    @FXML
    private Label lbl_total_price;

    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab tab_vgrn_info;

    @FXML
    private TableView<GoodsReceivedNote> tbl_Verify_GRN;

    @FXML
    private TableView<GrnDetail> tbl_GRN_de;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextArea txt_grn_des;

    private ObservableList<GoodsReceivedNote> list_Verify_GRN;
    private ObservableList<GrnDetail> listGrnDetail;

    private FruitUtil dict = new FruitUtil();

    public void clickBtnFindVGRN() throws RemoteException {
        try{
            //Chuyển text thành LocalDate để đúng định dạng và toString
            this.list_Verify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getVerifyGRNByKeyword(dict.convertDate(txtKeyWord.getText().trim()).toString()));
            tbl_Verify_GRN.setItems(list_Verify_GRN);
        } catch (Exception e){
            //Nếu không phải ngày tháng năm thì xuống đây
            this.list_Verify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getVerifyGRNByKeyword(txtKeyWord.getText().trim()));
            tbl_Verify_GRN.setItems(list_Verify_GRN);
        }
    }

    public void clickBtnRefresh(ActionEvent event) throws RemoteException {
        txtKeyWord.clear();
        this.list_Verify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllVerifyGRN());
        tbl_Verify_GRN.setItems(list_Verify_GRN);
    }

    public void view() {
        GoodsReceivedNote grn = this.tbl_Verify_GRN.getSelectionModel().getSelectedItem();
        if(grn == null)
            return;

        //Chuyển tab
        tab_pane.getSelectionModel().select(tab_vgrn_info);
        this.listGrnDetail = FXCollections.observableArrayList(grn.getGrnDetailsSet());
        tbl_GRN_de.setItems(listGrnDetail);

        lbl_grn_id.setText(grn.getGrnId());
        lbl_sup_addr.setText(grn.getSupplier().getSupAddress());
        lbl_sup_id.setText(grn.getSupplier().getSupId());
        lbl_sup_name.setText(grn.getSupplier().getSupName());
        lbl_sup_phone.setText(grn.getSupplier().getSupPhone());

        lbl_grn_date.setText(dict.convertDate(grn.getGrnDate()));
        lbl_staff.setText(grn.getReceivedUser().getFullname());
        lbl_verify_date.setText(dict.convertDate(grn.getVerifyDate()));
        txt_grn_des.setText(grn.getGrnDescription());
        lbl_total_price.setText(grn.getTotalReceivedPrice().toString());
    }

    //Đưa dữ liệu vào bảng
    public void setInforVerify_GRNTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_grn_id.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((GoodsReceivedNote) cellData.getValue()).getGrnId());
        });
        this.col_grn_date.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((GoodsReceivedNote) cellData.getValue()).getGrnDate()));
        });
        this.col_supplier_name.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty((((GoodsReceivedNote) cellData.getValue()).getSupplier().getSupName()));
        });
        this.col_total_price.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((GoodsReceivedNote) cellData.getValue()).getTotalReceivedPrice());
        });
        this.col_verify_date.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((GoodsReceivedNote) cellData.getValue()).getVerifyDate()));
        });
        this.col_staff.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((GoodsReceivedNote) cellData.getValue()).getReceivedUser().getFullname());
        });
        this.tbl_Verify_GRN.setItems(list_Verify_GRN);
    }

    //Đưa dữ liệu vào bảng
    public void setInforGrnDetailTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_index.setCellValueFactory((cellData) -> {
            return new SimpleIntegerProperty(listGrnDetail.indexOf(cellData.getValue()) + 1).asObject();
        });
        this.col_sku.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku());
        });
        this.col_ft.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getGoodsFruitType().getFtName());
        });
        this.col_original.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getOriginal()));
        });
        this.col_qinbox.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getQuantityInBox().toString() + " " + ((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getGoodsFruitType().getUnitOfCalculation()));
        });
        this.col_quantity.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<Short>(((GrnDetail) cellData.getValue()).getQuantity());
        });
        this.col_received_price.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<BigDecimal>(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getReceivedPrice());
        });
        this.col_status.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(dict.encode(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku().substring(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku().length() - 2)));
        });
        this.tbl_GRN_de.setItems(listGrnDetail);
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

    public VerifyGRNManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        this.list_Verify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllVerifyGRN());
        setInforGrnDetailTable();
        setInforVerify_GRNTable();
    }

}

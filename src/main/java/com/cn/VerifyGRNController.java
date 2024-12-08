package com.cn;

import com.entity.GoodsReceivedNote;
import com.entity.GrnDetail;
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

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;

public class VerifyGRNController {

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_supplier_name;

    @FXML
    private TableColumn<GrnDetail, BigDecimal> col_received_price;

    @FXML
    private TableColumn<GrnDetail, String> col_status;

    @FXML
    private TableColumn<GrnDetail, String> col_ft;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_date;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_id;

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
    private Label lbl_grn_date;

    @FXML
    private Label lbl_grn_id;

    @FXML
    private Label lbl_sup_addr;

    @FXML
    private Label lbl_sup_id;

    @FXML
    private Label lbl_sup_name;

    @FXML
    private Label lbl_sup_phone;

    @FXML
    private Label lbl_total_price;

    @FXML
    private Tab tab_list;

    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab tab_uvgrn_info;

    @FXML
    private TableView<GrnDetail> tbl_GRN_de;

    @FXML
    private TableView<GoodsReceivedNote> tbl_Unverify_GRN;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextArea txt_grn_des;

    private ObservableList<GoodsReceivedNote> list_Unverify_GRN;
    private ObservableList<GrnDetail> listGrnDetail;
    private FruitUtil dict = new FruitUtil();

    private GoodsReceivedNote grnChoose;

    public void clickBtnFindUVGRN() throws RemoteException {
        try{
            //Chuyển text thành LocalDate để đúng định dạng và toString
            this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getUnverifyGRNByKeyword(dict.convertDate(txtKeyWord.getText().trim()).toString()));
            tbl_Unverify_GRN.setItems(list_Unverify_GRN);
        } catch (Exception e){
            //Nếu không phải ngày tháng năm thì xuống đây
            this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getUnverifyGRNByKeyword(txtKeyWord.getText().trim()));
            tbl_Unverify_GRN.setItems(list_Unverify_GRN);
        }
    }

    public void clickBtnRefresh() throws RemoteException {
        txtKeyWord.clear();
        this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllNotVerifyGRN());
        tbl_Unverify_GRN.setItems(list_Unverify_GRN);
    }

    private void configTimeForGoods(){
        LocalDate urDate = LocalDate.now();
        for (GrnDetail grnDetail : listGrnDetail){
            //Nếu trạng thái là P cần đưa về trạng thái đúng
            if(grnDetail.getGoodsGRNDetail().getStatus().equals("P")){
                grnDetail.getGoodsGRNDetail().setStatus(grnDetail.getGoodsGRNDetail().getSku().substring(grnDetail.getGoodsGRNDetail().getSku().length() - 2));
            }

            //Nếu nhập sớm hoặc muộn thì cần cập nhật lại thời gian
            if(!grnChoose.getGrnDate().toLocalDateTime().toLocalDate().equals(urDate)){
                System.out.println("[]===========================");

                //Nếu trái cây có trạng thái vừa chín thì đưa thời gian về chưa chín để xử lý
                if(grnDetail.getGoodsGRNDetail().getStatus().equals("JR")){
                    urDate = urDate.minusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToJustRipe());
                }

                grnDetail.getGoodsGRNDetail().setJustRipeDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToJustRipe())));
                grnDetail.getGoodsGRNDetail().setRipeDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToRipe())));
                grnDetail.getGoodsGRNDetail().setOverripDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToOverripe())));
                grnDetail.getGoodsGRNDetail().setExpDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToDamaged())));
            }
        }
    }

    private void lockVerifyScreen(){
        tab_uvgrn_info.setDisable(true);
        this.tab_pane.getSelectionModel().select(tab_list);
    }

    public void clickBtnVerifyGRN() throws RemoteException {
        //Đưa giá trị vào biến
        //Đặt thời gian cho hàng hóa
        configTimeForGoods();

        grnChoose.setGrnDescription(txt_grn_des.getText());
        grnChoose.setGrnDetailsSet(new HashSet<>(listGrnDetail));
        grnChoose.setReceivedUser(Support.account);
        grnChoose.setVerifyDate(Timestamp.from(Instant.now()));

        if(this.registryClass.goodsReceivedNote().verifyGRN(grnChoose, Support.account)){
            dict.showAlertInfo("Thành công", "Đã xác nhận hàng");
            lockVerifyScreen();
            clickBtnRefresh();
        } else {
            dict.showAlertError("Thất bại", "Xác nhận không thành công");
        }
    }

    public void view() {
        grnChoose = this.tbl_Unverify_GRN.getSelectionModel().getSelectedItem();
        if(grnChoose == null)
            return;

        tab_uvgrn_info.setDisable(false);

        //Chuyển tab
        tab_pane.getSelectionModel().select(tab_uvgrn_info);
        this.listGrnDetail = FXCollections.observableArrayList(grnChoose.getGrnDetailsSet());
        tbl_GRN_de.setItems(listGrnDetail);

        lbl_grn_id.setText(grnChoose.getGrnId());
        lbl_sup_addr.setText(grnChoose.getSupplier().getSupAddress());
        lbl_sup_id.setText(grnChoose.getSupplier().getSupId());
        lbl_sup_name.setText(grnChoose.getSupplier().getSupName());
        lbl_sup_phone.setText(grnChoose.getSupplier().getSupPhone());

        lbl_grn_date.setText(dict.convertDate(grnChoose.getGrnDate()));
        txt_grn_des.setText(grnChoose.getGrnDescription());
        lbl_total_price.setText(grnChoose.getTotalReceivedPrice().toString());
    }

    //Đưa dữ liệu vào bảng
    public void setInforUnverify_GRNTable() {
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
//        this.col_sup_addr.setCellValueFactory((cellData) ->{
//            return new SimpleStringProperty((((GoodsReceivedNote) cellData.getValue()).getSupplier().getSupAddress()));
//        });
        this.tbl_Unverify_GRN.setItems(list_Unverify_GRN);
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

    //Chuyển đến trang xác nhận phiếu nhập
    public void navigateToVeifyGRN(){
        Support.navigateTo((byte) 1, this);
    }

    //Chuyển đến trang lập phiếu xuất
    public void navigateToGoodsDelivery(){
        Support.navigateTo((byte) 2, this);
    }

    //Chuyển đến trang xác định trái cây
    public void navigateToFIPage() throws RemoteException  {
        Support.navigateTo((byte) 3, this);
    }

    //Chuyển đến trang đổi mật khẩu
    public void navigateToChangePwdPage() throws RemoteException  {
        Support.navigateTo((byte) 4, this);
    }

    //Đăng xuất
    public void clickBtnLogOut() throws RemoteException {
        if(!dict.showAlertWarning("Bạn có chắc chắn muốn thoát không?","Nhấn \"Có\" để xác nhận."))
            return;
        this.registryClass.user().logoutUser(Support.account);
        Support.navigateTo((byte) 0, this);
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

    public VerifyGRNController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllNotVerifyGRN());
        setInforGrnDetailTable();
        setInforUnverify_GRNTable();
    }

}

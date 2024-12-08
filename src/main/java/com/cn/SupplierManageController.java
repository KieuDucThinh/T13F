package com.cn;

import com.entity.Customer;
import com.entity.Supplier;
import com.util.CitizenIdentification;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;

public class SupplierManageController {

    @FXML
    private TableColumn<Supplier, String> col_sup_adddate;

    @FXML
    private TableColumn<Supplier, String> col_sup_address;

    @FXML
    private TableColumn<Supplier, String> col_sup_email;

    @FXML
    private TableColumn<Supplier, String> col_sup_id;

    @FXML
    private TableColumn<Supplier, String> col_sup_name;

    @FXML
    private TableColumn<Supplier, String> col_sup_phone;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lbl_sup_id;

    @FXML
    private TableView<Supplier> tbl_SU;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextField txt_sup_addr;

    @FXML
    private TextArea txt_sup_description;

    @FXML
    private TextField txt_sup_email;

    @FXML
    private TextField txt_sup_name;

    @FXML
    private TextField txt_sup_phone;

    private FruitUtil dict = new FruitUtil();
    private CitizenIdentification ci = new CitizenIdentification();

    private ObservableList<Supplier> listSU;

    public void setInforSuTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_sup_id.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Supplier) cellData.getValue()).getSupId());
        });
        this.col_sup_name.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Supplier) cellData.getValue()).getSupName());
        });
        this.col_sup_phone.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Supplier) cellData.getValue()).getSupPhone());
        });
        this.col_sup_email.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Supplier) cellData.getValue()).getSupEmail());
        });
        this.col_sup_adddate.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((Supplier) cellData.getValue()).getSupAdddate()));
        });
        this.col_sup_address.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Supplier) cellData.getValue()).getSupAddress());
        });
        this.tbl_SU.setItems(listSU);
    }

    public void clickBtnFindCustomer() throws RemoteException {
        this.listSU = FXCollections.observableArrayList(this.registryClass.supplier().getSuppliersBySupplierName(txtKeyWord.getText().trim()));
        this.tbl_SU.setItems(this.listSU);
    }

    public void clickBtnRefresh() throws RemoteException {
        txtKeyWord.clear();
        this.listSU = FXCollections.observableArrayList(this.registryClass.supplier().getSuppliers());
        this.tbl_SU.setItems(this.listSU);
    }

    //Làm sạch
    public void clickBtnClearTxt(){
        txt_sup_addr.clear();
        txt_sup_description.clear();
        txt_sup_email.clear();
        txt_sup_name.clear();
        txt_sup_phone.clear();
        lbl_sup_id.setText("");
    }

    //Lấy dữ liệu từ trong bảng ra
    public void view(){
        Supplier infoData = this.tbl_SU.getSelectionModel().getSelectedItem();
        if (infoData != null) {
            txt_sup_addr.setText(infoData.getSupAddress());
            txt_sup_description.setText(infoData.getSupDescription());
            txt_sup_email.setText(infoData.getSupEmail());
            txt_sup_name.setText(infoData.getSupName());
            txt_sup_phone.setText(infoData.getSupPhone());
            lbl_sup_id.setText(infoData.getSupId());
        }
    }

    //Kiểm tra thông tin nhập vào
    private boolean validateSupInfo(){
        if(txt_sup_name.getText().trim().equalsIgnoreCase("")){
            dict.showAlertInfo("Tên nhà cung cấp không được trống");
            return false;
        }
        if(txt_sup_phone.getText().trim().equalsIgnoreCase("")){
            dict.showAlertInfo("Điện thoại nhà cung cấp không được trống");
            return false;
        }
        if(txt_sup_addr.getText().trim().equalsIgnoreCase("")){
            dict.showAlertInfo("Địa chỉ nhà cung cấp không được trống");
            return false;
        }
        if(txt_sup_email.getText().trim().equalsIgnoreCase("")){
            dict.showAlertInfo("Email nhà cung cấp không được trống");
            return false;
        }
        //Tên phải là chữ cái từ 5 đến 40 ký tự
        if(!this.txt_sup_name.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ ]{5,40}")){
            this.dict.showAlertError("Tên không hợp lệ");
            return false;
        }

        //Điện thoại 10 số
        if(!ci.checkPhoneNum(this.txt_sup_phone.getText().trim())){
            this.dict.showAlertError("Số điện thoại không hợp lệ");
            return false;
        }

        //Email hợp lệ từ 10 đến 150 ký tự
        //Chuẩn ___@___.com
        if(!this.txt_sup_email.getText().trim().equals("") && !this.txt_sup_email.getText().trim().matches("^\\w+@\\w+\\.\\w+${10,150}")){
            this.dict.showAlertError("Email không hợp lệ");
            return false;
        }

        //Địa chỉ từ 6 - 255 ký tự
        //Chấp nhận chữ, số, dấu phẩy, dấu cách, dấu /
        if(!this.txt_sup_addr.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ0-9,/ ]{6,255}")){
            this.dict.showAlertError("Địa chỉ không hợp lệ");
            return false;
        }
        return true;
    }

    public void clickBtnAddSupplier() throws RemoteException {
        //Nếu thông tin không hợp lệ sẽ không thực hiện
        if(!validateSupInfo()){
            return;
        }

        //Tạo supplier
        Supplier sup = Supplier.builder()
                .supName(txt_sup_name.getText().trim())
                .supAddress(txt_sup_addr.getText().trim())
                .supPhone(txt_sup_phone.getText().trim())
                .supEmail(txt_sup_email.getText().trim())
                .supDescription(txt_sup_description.getText().trim())
                .supAdddate(Timestamp.from(Instant.now()))
                .build();

        if(!this.registryClass.supplier().addSupplier(sup))
            dict.showAlertError("Thêm không thành công");
        else{
            clickBtnRefresh();
            clickBtnClearTxt();
            dict.showAlertInfo("Thông báo", "Thêm thành công");
        }
    }

    public void clickBtnUpdateSupplier() throws RemoteException {
        //Nếu thông tin không hợp lệ sẽ không thực hiện
        if(!validateSupInfo()){
            return;
        }

        //Tạo supplier
        Supplier sup = Supplier.builder()
                .supId(lbl_sup_id.getText())
                .supName(txt_sup_name.getText().trim())
                .supAddress(txt_sup_addr.getText().trim())
                .supPhone(txt_sup_phone.getText().trim())
                .supEmail(txt_sup_email.getText().trim())
                .supDescription(txt_sup_description.getText().trim())
                .build();

        if(!this.registryClass.supplier().updateSupplier(sup))
            dict.showAlertError("Sửa thông tin không thành công");
        else{
            clickBtnRefresh();
            clickBtnClearTxt();
            dict.showAlertInfo("Thông báo", "Sửa thông tin thành công");
        }
    }

    //Chuyển các màn hình
    public void navigateToSupplierManagement(){
        Support.navigateTo((byte) 5, this);
    }

    public void navigateToCustomerManagement(){
        Support.navigateTo((byte) 6, this);
    }

    public void naviagteToStaffManagement(){
        Support.navigateTo((byte) 7, this);
    }

    public void navigateToGNNavigate(){
        Support.navigateTo((byte) 8, this);
    }

    public void navigateToPositionManagement(){
        Support.navigateTo((byte) 9, this);
    }

    public void navigateToStatisticsManagement(){
        Support.navigateTo((byte) 10, this);
    }

    public void navigateToManagerAccountManagement(){
        Support.navigateTo((byte) 11, this);
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

    public SupplierManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lbl_sup_id.setText("");
        lblFullName.setText(Support.account.getFullname());
        this.listSU = FXCollections.observableArrayList(this.registryClass.supplier().getSuppliers());
        setInforSuTable();
    }
}

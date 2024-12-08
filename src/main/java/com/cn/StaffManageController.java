package com.cn;


import com.entity.User;
import com.util.CitizenIdentification;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class StaffManageController {

    @FXML
    private RadioButton btn_radio_0;

    @FXML
    private RadioButton btn_radio_1;

    @FXML
    private ComboBox<String> cboStatus;

    @FXML
    private TableColumn<User, String> col_addr;

    @FXML
    private TableColumn<User, String> col_bod;

    @FXML
    private TableColumn<User, String> col_fullname;

    @FXML
    private TableColumn<User, String> col_gender;

    @FXML
    private TableColumn<User, String> col_phone;

    @FXML
    private TableColumn<User, String> col_status;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lblPwd;

    @FXML
    private Label lbl_endDate;

    @FXML
    private Label lbl_newPwd;

    @FXML
    private Label lbl_startDate;

    @FXML
    private Label lbl_status;

    @FXML
    private TableView<User> tbl_Staff;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextField txt_addr;

    @FXML
    private DatePicker txt_bod;

    @FXML
    private TextField txt_ci;

    @FXML
    private TextField txt_fullname;

    @FXML
    private TextField txt_hometown;

    @FXML
    private TextField txt_phone;

    @FXML
    private TextField txt_salary;

    @FXML
    private TextField txt_username;

    @FXML
    private Tab tab_staff_info;

    @FXML
    private TabPane tab_pane;

    private ObservableList<User> listStaff;

    private FruitUtil dict = new FruitUtil();

    private CitizenIdentification ci = new CitizenIdentification();

    private Random random = new Random();

    public void clickBtnAddSupplier() throws RemoteException {
        if(!validateInfoStaff()){
            return;
        }

        lbl_newPwd.setText(String.format("%010d", (System.currentTimeMillis() + random.nextInt(9999999)) % 1000000000));

        User u = User.builder()
                .username(txt_username.getText().trim())
                .fullname(txt_fullname.getText().trim())
                .bod(Date.valueOf(txt_bod.getValue()))
                .gender(btn_radio_1.isSelected())
                .address(txt_addr.getText().trim())
                .phone(txt_phone.getText().trim())
                .citizenId(txt_ci.getText().trim())
                .hometown(txt_hometown.getText().trim())
                .salary(BigDecimal.valueOf(Double.parseDouble(txt_salary.getText().trim())))
                .status("FI")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf((LocalDate.parse("03/09/2112", DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .password(lbl_newPwd.getText())
                .build();

        if(this.registryClass.user().addUser(u)){
            dict.showAlertInfo("Thông báo", "Thêm nhân viên thành công");
            lbl_newPwd.setVisible(true);
            clickBtnRefresh();
        } else {
            dict.showAlertError("Thêm nhân viên thất bại");
        }
    }

    //Làm sạch thông tin nhân viên
    public void clickBtnClearTxt() {
        txt_username.clear();
        txt_username.setEditable(true);
        txt_fullname.clear();
        txt_bod.setValue(null);
        txt_addr.clear();
        txt_phone.clear();
        txt_ci.clear();
        txt_hometown.clear();
        txt_salary.clear();

        lbl_status.setText("FI");
        lbl_startDate.setText(dict.convertDate(LocalDate.now()));
        lbl_endDate.setText("03/09/2112");

        lblPwd.setVisible(true);
        lbl_newPwd.setVisible(false);
    }

    //Phương thức tìm kiếm
    public void clickBtnFindStaff() throws RemoteException {
        cboStatus.getSelectionModel().clearSelection();
        this.listStaff = FXCollections.observableArrayList(this.registryClass.user().getAllUsersByKeyword(txtKeyWord.getText().trim()));
        tbl_Staff.setItems(this.listStaff);
    }

    //Refresh bảng
    public void clickBtnRefresh() throws RemoteException {
        txtKeyWord.clear();
        cboStatus.setValue("");
        this.listStaff = FXCollections.observableArrayList(this.registryClass.user().getAllUsersByKeyword(""));
        tbl_Staff.setItems(this.listStaff);
    }

    //Kiểm tra dữ liệu đầu vào hợp lệ
    private boolean validateInfoStaff(){
        if(txt_username.getText().trim().equals("") || txt_fullname.getText().trim().equals("") || txt_bod.getValue() == null || txt_addr.getText().trim().equals("")
        || txt_phone.getText().trim().equals("") || txt_ci.getText().trim().equals("") || txt_hometown.getText().trim().equals("") || txt_salary.getText().trim().equals("")){
            dict.showAlertError("Không được bỏ trống thông tin");
            return false;
        }

        //Tên phải là chữ cái từ 5 đến 40 ký tự
        if(!this.txt_fullname.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ ]{5,40}")){
            this.dict.showAlertError("Tên không hợp lệ");
            return false;
        }

        //Điện thoại 10 số
        if(!ci.checkPhoneNum(this.txt_phone.getText().trim())){
            this.dict.showAlertError("Số điện thoại không hợp lệ");
            return false;
        }

        //Địa chỉ từ 6 - 255 ký tự
        //Chấp nhận chữ, số, dấu phẩy, dấu cách, dấu /
        if(!this.txt_addr.getText().trim().matches("[a-zA-Zà-ỹÀ-Ỹ0-9,/ ]{6,255}")){
            this.dict.showAlertError("Địa chỉ không hợp lệ");
            return false;
        }

        //Lương là số dương
        try{
            double salary = Double.parseDouble(this.txt_salary.getText().trim());
            if(salary < 0){
                dict.showAlertError("Lượng không hợp lệ");
                return false;
            }
        } catch(NumberFormatException e){
            dict.showAlertError("Lượng không hợp lệ");
            return false;
        }

        //Kiểm tra CCCD
        if(!ci.checkCI(txt_ci.getText().trim())){
            dict.showAlertError("CCCD không đúng 12 số");
            return false;
        }

        if(!ci.checkThreeNumber(txt_ci.getText().trim(), txt_hometown.getText())){
            dict.showAlertError("CCCD không khớp với các thông tin nhập vào");
            return false;
        }

        if(!ci.checkBirthOfDateAndGender(txt_ci.getText().trim(), txt_bod.getValue().getYear(), btn_radio_1.isSelected())){
            dict.showAlertError("CCCD không khớp với các thông tin nhập vào");
            return false;
        }

        int age = LocalDate.now().getYear() - txt_bod.getValue().getYear();
        if(age < 15 || age > 61){
            dict.showAlertError("Nhân viên ngoài độ tuổi lao động");
            return false;
        }

        return true;
    }

    //Khi nhấn nút cập nhật
    public void clickBtnUpdateSupplier() throws RemoteException {
        if(lbl_status.getText().equals("QUI")) {
            dict.showAlertError("Nhân viên này đã nghỉ việc.", "Không thể chỉnh sửa thông tin \nnhằm đảm bảo tính toàn vẹn.");
            return;
        }

        if(!validateInfoStaff()){
            return;
        }

        User u = User.builder()
                .username(txt_username.getText())
                .fullname(txt_fullname.getText().trim())
                .bod(Date.valueOf(txt_bod.getValue()))
                .gender(btn_radio_1.isSelected())
                .address(txt_addr.getText().trim())
                .phone(txt_phone.getText().trim())
                .citizenId(txt_ci.getText().trim())
                .hometown(txt_hometown.getText().trim())
                .salary(BigDecimal.valueOf(Double.parseDouble(txt_salary.getText().trim())))
                .build();

        if(this.registryClass.user().updateUser(u)){
            dict.showAlertInfo("Thông báo", "Sửa nhân viên thành công");
            clickBtnRefresh();
        } else {
            dict.showAlertError("Sửa nhân viên thất bại");
        }
    }

    //Hiển thị dữ liệu khi chọn 1 nhân viên trong bảng
    public void view() {
        User u = this.tbl_Staff.getSelectionModel().getSelectedItem();
        if(u==null){
            return;
        }

        txt_username.setText(u.getUsername());
        txt_username.setEditable(false);
        txt_fullname.setText(u.getFullname());
        txt_bod.setValue(dict.convertDate2(u.getBod()));
        txt_addr.setText(u.getAddress());

        txt_phone.setText(u.getPhone());
        txt_hometown.setText(u.getHometown());
        txt_ci.setText(u.getCitizenId());
        txt_salary.setText(u.getSalary().toString());

        lbl_status.setText(u.getStatus());
        lbl_startDate.setText(dict.convertDate(u.getStartDate()));
        lbl_endDate.setText(dict.convertDate(u.getEndDate()));

        //Chỉ hiện mật khẩu với nhân viên là FI
        if(u.getStatus().equalsIgnoreCase("FI")){
            lbl_newPwd.setText(u.getPassword());
            lblPwd.setVisible(true);
            lbl_newPwd.setVisible(true);
        } else {
            lblPwd.setVisible(false);
            lbl_newPwd.setVisible(false);
        }


        if(u.isGender())
            btn_radio_1.setSelected(true);

        this.tab_pane.getSelectionModel().select(tab_staff_info);
    }

    //Đưa dữ liệu vào bảng
    public void setInforStaffTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_fullname.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((User) cellData.getValue()).getFullname());
        });
        this.col_bod.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((User) cellData.getValue()).getBod()));
        });
        this.col_gender.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.encode(((User) cellData.getValue()).isGender()));
        });
        this.col_addr.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((User) cellData.getValue()).getAddress());
        });
        this.col_phone.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((User) cellData.getValue()).getPhone());
        });
        this.col_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((User) cellData.getValue()).getStatus());
        });;
        this.tbl_Staff.setItems(listStaff);
    }

    //Khởi tạo cbo
    private void cboInit(){
        cboStatus.setItems(FXCollections.observableArrayList(
                "ON", "OFF", "FI", "QUI"
        ));
    }

    //Khi nhấn cbo
    public void clickCbo() throws RemoteException {
        if(cboStatus.getSelectionModel().getSelectedItem()==null){
            return;
        }
        txtKeyWord.clear();
        this.listStaff = FXCollections.observableArrayList(this.registryClass.user().getAllUsersByStatus(cboStatus.getValue()));
        tbl_Staff.setItems(this.listStaff);
    }

    //Khóa tài khoản nhân viên
    public void clickBtnLockStaff() throws RemoteException {
        if(txt_username.getText().trim().equalsIgnoreCase("")){
            dict.showAlertError("Không để trống tên đăng nhập");
            return;
        }

        if(lbl_status.getText().trim().equalsIgnoreCase("QUI")){
            dict.showAlertError("Nhân viên đã nghỉ việc");
            return;
        }

        User u = User.builder().username(txt_username.getText()).build();

        if(this.registryClass.user().lockUser(u)){
            lbl_status.setText("QUI");
            lbl_endDate.setText(dict.convertDate(LocalDate.now()));
            lbl_newPwd.setVisible(false);
            dict.showAlertInfo("Thông báo", "Đã khóa tài khoản này");
        } else {
            dict.showAlertError("Có lỗi xảy ra");
        }
    }

    //Mở khóa tài khoản nhân viên
    public void clickBtnUnlockStaff() throws RemoteException {
        if(txt_username.getText().trim().equalsIgnoreCase("")){
            dict.showAlertError("Không để trống tên đăng nhập");
            return;
        }

        if(!lbl_status.getText().trim().equalsIgnoreCase("QUI")){
            dict.showAlertError("Nhân viên chưa nghỉ việc");
            return;
        }

        User u = this.registryClass.user().unlockUser(User.builder().username(txt_username.getText()).build());

        if(u!=null){
            lbl_status.setText("FI");
            lbl_endDate.setText(dict.convertDate(u.getEndDate()));
            lbl_newPwd.setText(u.getPassword());
            lbl_newPwd.setVisible(true);
            lblPwd.setVisible(true);
            dict.showAlertInfo("Thông báo", "Đã mở khóa tài khoản này");
        } else {
            dict.showAlertError("Có lỗi xảy ra");
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

    public StaffManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        this.listStaff = FXCollections.observableArrayList(this.registryClass.user().getAllUsersByKeyword(""));
        setInforStaffTable();
        cboInit();
        clickBtnClearTxt();
    }

}

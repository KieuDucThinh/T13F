package com.cn;

import com.entity.User;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginController {

    @FXML
    private PasswordField txtPwd;

    @FXML
    private TextField txtUsername;

    private FruitUtil fruitUtil = new FruitUtil();

    @FXML
    public void clickBtnLogin() throws RemoteException {
        //Set username, pwd vào biến Support.account
        Support.account.setUsername(txtUsername.getText());
        Support.account.setPassword(txtPwd.getText());

        //Nhận dữ liệu user từ BE
        User account = this.registryClass.user().checkLogin(Support.account);

        //Kiểm tra hợp lệ
        if (account == null) {
            fruitUtil.showAlertError("Tên đăng nhập không tồn tại!");
        } else if (account.getStatus().equalsIgnoreCase("ON")){
            fruitUtil.showAlertError("Lỗi đăng nhập","Tài khoản đang đăng nhập trên thiết bị khác.");
        } else if (account.getStatus().equalsIgnoreCase("QUI")){
            fruitUtil.showAlertError("Lỗi đăng nhập","Nhân viên này đã nghỉ việc");
        } else if (!this.registryClass.user().loginUser(Support.account)){
            fruitUtil.showAlertError("Mật khẩu không đúng");
        } else{
            Support.account = account;

            Support.stage.setWidth(1400);
            Support.stage.setHeight(800);
            if (txtUsername.getText().equals("administrator")) {
                Support.navigateTo((byte) 5, this);
            } else {
                if(!Support.account.getStatus().equalsIgnoreCase("FI")){
                    Support.navigateTo((byte) 1, this);
                } else{
                    Support.navigateTo((byte) 4, this);
                }

            }
        }
    }

    public void pressEnterKey(KeyEvent event) throws RemoteException {
        if(event.getCode() == KeyCode.ENTER){
            clickBtnLogin();
        }
    }

    public void pressTabKey(KeyEvent event) throws RemoteException {
        if(event.getCode() == KeyCode.TAB){
            txtPwd.requestFocus();
        }
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

    public LoginController(){

    }

    @FXML
    public void initialize() throws RemoteException {

    }
}

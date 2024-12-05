package com.cn;

import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class StaffAccountManageController {

    @FXML
    private TextField txtPwdConfirm;

    @FXML
    private TextField txtPwdNew;

    @FXML
    private TextField txtPwdOld;

    @FXML
    private Label lblFullName;

    private FruitUtil dict = new FruitUtil();

    public void clickBtnChangePwd() throws RemoteException {
        if(txtPwdNew.getText().length()<6){
            dict.showAlertError("Mật khẩu mới cần độ dài lớn hơn 6 ký tự.");
        } else{
            if(!txtPwdNew.getText().equals(txtPwdConfirm.getText())){
                dict.showAlertError("Mật khẩu mới không khớp với mật khẩu xác nhận");
            } else {
                Support.account.setPassword(txtPwdNew.getText());
                if(this.registryClass.user().changePassword(txtPwdOld.getText(), Support.account)){
                    txtPwdOld.clear();
                    txtPwdNew.clear();
                    txtPwdConfirm.clear();
                    dict.showAlertInfo("Thông báo","Đổi mật khẩu thành công");
                    Support.account.setPassword(null);
                } else {
                    dict.showAlertInfo("Thông báo","Đổi mật khẩu thất bại");
                }
            }
        }
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

    public StaffAccountManageController(){

    }

    @FXML
    void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
    }

}

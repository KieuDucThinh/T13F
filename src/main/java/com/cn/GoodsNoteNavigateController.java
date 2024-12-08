package com.cn;

import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GoodsNoteNavigateController {

    @FXML
    private Label lblFullName;

    private FruitUtil dict = new FruitUtil();

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

    public void navigateToUnverifyGRNManagement(){
        Support.navigateTo((byte) 12, this);
    }

    public void navigateToVerifyGRNManagement(){
        Support.navigateTo((byte) 13, this);
    }

    public void navigateToGDNManagement(){
        Support.navigateTo((byte) 14, this);
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

    public GoodsNoteNavigateController(){

    }

    @FXML
    public void initialize() {
        lblFullName.setText(Support.account.getFullname());
    }
}

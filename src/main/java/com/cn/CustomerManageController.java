package com.cn;

import com.entity.Customer;
import com.entity.Position;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CustomerManageController {

    @FXML
    private TableColumn<Customer, String> col_c_adddate;

    @FXML
    private TableColumn<Customer, String> col_c_address;

    @FXML
    private TableColumn<Customer, String> col_c_email;

    @FXML
    private TableColumn<Customer, String> col_c_id;

    @FXML
    private TableColumn<Customer, String> col_c_name;

    @FXML
    private TableColumn<Customer, String> col_c_phone;

    @FXML
    private Label lblFullName;

    @FXML
    private TableView<Customer> tbl_Customer;

    @FXML
    private TextField txtKeyWord;

    private FruitUtil dict = new FruitUtil();

    private ObservableList<Customer> listCU;

    public void setInforCuTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_c_id.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Customer) cellData.getValue()).getCId());
        });
        this.col_c_name.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Customer) cellData.getValue()).getCName());
        });
        this.col_c_phone.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Customer) cellData.getValue()).getCPhone());
        });
        this.col_c_email.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Customer) cellData.getValue()).getCEmail());
        });
        this.col_c_adddate.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((Customer) cellData.getValue()).getCAdddate()));
        });
        this.col_c_address.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Customer) cellData.getValue()).getCAddress());
        });
        this.tbl_Customer.setItems(listCU);
    }

    public void clickBtnFindCustomer() throws RemoteException {
        this.listCU = FXCollections.observableArrayList(this.registryClass.customer().getCustomerByKeyword(txtKeyWord.getText().trim()));
        this.tbl_Customer.setItems(this.listCU);
    }

    public void clickBtnRefresh() throws RemoteException {
        txtKeyWord.clear();
        this.listCU = FXCollections.observableArrayList(this.registryClass.customer().getAllCustomer());
        this.tbl_Customer.setItems(this.listCU);
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

    public CustomerManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        this.listCU = FXCollections.observableArrayList(this.registryClass.customer().getAllCustomer());
        setInforCuTable();
    }
}

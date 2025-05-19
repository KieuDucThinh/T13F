package com.cn;

import com.entity.Position;
import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public class FruitIdentificationController {

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_EG_next;

    @FXML
    private Button btn_EG_prev;

    @FXML
    private Button btn_OG_next;

    @FXML
    private Button btn_OG_prev;

    @FXML
    private Button btn_RG_next;

    @FXML
    private Button btn_RG_prev;

    @FXML
    private TableColumn<Position, BigDecimal> col_EG_delivery_price;

    @FXML
    private TableColumn<Position, String> col_EG_exp;

    @FXML
    private TableColumn<Position, String> col_EG_pos;

    @FXML
    private TableColumn<Position, BigDecimal> col_EG_qinbox;

    @FXML
    private TableColumn<Position, String> col_EG_size;

    @FXML
    private TableColumn<Position, String> col_EG_sku;

    @FXML
    private TableColumn<Position, String> col_EG_status;

    @FXML
    private TableColumn<Position, BigDecimal> col_OG_deliveryprice;

    @FXML
    private TableColumn<Position, String> col_OG_exp;

    @FXML
    private TableColumn<Position, String> col_OG_pos;

    @FXML
    private TableColumn<Position, BigDecimal> col_OG_qinbox;

    @FXML
    private TableColumn<Position, String> col_OG_size;

    @FXML
    private TableColumn<Position, String> col_OG_sku;

    @FXML
    private TableColumn<Position, String> col_OG_status;

    @FXML
    private TableColumn<Position, BigDecimal> col_RG_deliveryprice;

    @FXML
    private TableColumn<Position, String> col_RG_ovdate;

    @FXML
    private TableColumn<Position, String> col_RG_pos;

    @FXML
    private TableColumn<Position, BigDecimal> col_RG_qinbox;

    @FXML
    private TableColumn<Position, String> col_RG_size;

    @FXML
    private TableColumn<Position, String> col_RG_sku;

    @FXML
    private TableColumn<Position, String> col_RG_status;

    @FXML
    private Label lbl_EG_page;

    @FXML
    private Label lbl_OG_page;

    @FXML
    private Label lbl_RG_page;

    @FXML
    private TableView<Position> tbl_EG;

    @FXML
    private TableView<Position> tbl_OG;

    @FXML
    private TableView<Position> tbl_RG;

    @FXML
    private Label lblFullName;

    //Thuộc tính: Các trang hiện tại là 1
    private byte pageEG = 1;
    private byte pageOG = 1;
    private byte pageRG = 1;

    //Thuộc tính: Các trang tối đa
    private byte maxPageEG = 1;
    private byte maxPageOG = 1;
    private byte maxPageRG = 1;

    //Thuộc tính: Lớp tiện ích
    private FruitUtil dict = new FruitUtil();

    //Chen dữ liệu vào bảng: Trái cây hết hạn
    public void setInforEGTable(ObservableList<Position> list) {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_EG_sku.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSku());
        });
        this.col_EG_size.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSize());
        });
        this.col_EG_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.encode(((Position) cellData.getValue()).getGoodsPos().getStatus()));
        });
        this.col_EG_delivery_price.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getDeliveryPrice());
        });
        this.col_EG_exp.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getExpDate()));
        });
        this.col_EG_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getQuantityInBox());
        });
        this.col_EG_pos.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getPos());
        });
        this.tbl_EG.setItems(list);
    }

    //Chen dữ liệu vào bảng: Trái cây quá chín
    public void setInforOGTable(ObservableList<Position> list) {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_OG_sku.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSku());
        });
        this.col_OG_size.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSize());
        });
        this.col_OG_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.encode(((Position) cellData.getValue()).getGoodsPos().getStatus()));
        });
        this.col_OG_deliveryprice.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getDeliveryPrice());
        });
        this.col_OG_exp.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getExpDate()));
        });
        this.col_OG_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getQuantityInBox());
        });
        this.col_OG_pos.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getPos());
        });
        this.tbl_OG.setItems(list);
    }

    //Chen dữ liệu vào bảng: Trái cây chín
    public void setInforRGTable(ObservableList<Position> list) {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_RG_sku.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSku());
        });
        this.col_RG_size.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSize());
        });
        this.col_RG_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.encode(((Position) cellData.getValue()).getGoodsPos().getStatus()));
        });
        this.col_RG_deliveryprice.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getDeliveryPrice());
        });
        this.col_RG_ovdate.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getOverripDate()));
        });
        this.col_RG_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getQuantityInBox());
        });
        this.col_RG_pos.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getPos());
        });
        this.tbl_RG.setItems(list);
    }

    //Hàm chuyển về trang trước của trái cây hỏng
    public void prev_EG() throws RemoteException {
        //Nếu là trang đầu tiên thì không thể tiến
        if (lbl_EG_page.getText().equalsIgnoreCase("1"))
            return;

        //Giảm số trang đi 1
        this.pageEG = (byte) (Byte.parseByte(lbl_EG_page.getText()) - 1);
        this.lbl_EG_page.setText(this.pageEG+"");

        //Load lại bảng
        this.listEG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG));
        this.tbl_EG.setItems(listEG);
        //setInforEGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG)));
    }

    //Hàm chuyển về trang sau của trái cây hỏng
    public void next_EG() throws RemoteException {

        //Nếu là trang cuối thì không thể lùi
        if (lbl_EG_page.getText().equalsIgnoreCase(this.maxPageEG+""))
            return;

        //Tăng số trang lên 1
        this.pageEG = (byte) (Byte.parseByte(lbl_EG_page.getText()) + 1);
        this.lbl_EG_page.setText(this.pageEG+"");

        //Load lại bảng
        this.listEG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG));
        this.tbl_EG.setItems(listEG);
        //setInforEGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG)));
    }

    //Hàm chuyển về trang trước của trái cây quá chín
    public void prev_OG() throws RemoteException {
        //Nếu là trang đầu tiên thì không thể tiến
        if (lbl_OG_page.getText().equalsIgnoreCase("1"))
            return;

        //Giảm số trang đi 1
        this.pageOG = (byte) (Byte.parseByte(lbl_OG_page.getText()) - 1);
        this.lbl_OG_page.setText(this.pageOG+"");

        //Load lại bảng
        this.listOG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("OR", this.pageOG));
        this.tbl_OG.setItems(listOG);
        //setInforOGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("OR", this.pageOG)));
    }

    //Hàm chuyển về trang sau của trái cây quá chín
    public void next_OG() throws RemoteException {
        //Nếu là trang cuối thì không thể lùi
        if (lbl_EG_page.getText().equalsIgnoreCase(this.maxPageOG+""))
            return;

        //Tăng số trang lên 1
        this.pageOG = (byte) (Byte.parseByte(lbl_OG_page.getText()) + 1);
        this.lbl_OG_page.setText(this.pageOG+"");

        //Load lại bảng
        this.listOG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("OR", this.pageOG));
        this.tbl_OG.setItems(listOG);
        //setInforOGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("OR", this.pageOG)));
    }

    //Hàm chuyển về trang trước của trái cây chín
    public void prev_RG() throws RemoteException {
        //Nếu là trang đầu tiên thì không thể tiến
        if (lbl_RG_page.getText().equalsIgnoreCase("1"))
            return;

        //Giảm số trang đi 1
        this.pageRG = (byte) (Byte.parseByte(lbl_RG_page.getText()) - 1);
        this.lbl_RG_page.setText(this.pageRG+"");

        //Load lại bảng
        this.listRG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("RI", this.pageRG));
        this.tbl_RG.setItems(listRG);
        //setInforRGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("RI", this.pageRG)));
    }

    //Hàm chuyển về trang trước của trái cây chín
    public void next_RG() throws RemoteException {
        //Nếu là trang cuối thì không thể lùi
        if (lbl_RG_page.getText().equalsIgnoreCase(this.maxPageRG+""))
            return;

        //Tăng số trang lên 1
        this.pageRG = (byte) (Byte.parseByte(lbl_RG_page.getText()) + 1);
        this.lbl_RG_page.setText(this.pageRG+"");

        //Load lại bảng
        this.listRG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("RI", this.pageRG));
        this.tbl_RG.setItems(listRG);
        //setInforRGTable(FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("RI", this.pageRG)));
    }

    public void clearPosition() throws RemoteException {
        if (this.dict.showAlertWarning("Bạn có chắc chắn muốn dọn dẹp không?")) {
            //Xóa toàn bộ hàng hết hạn trong kho
            //Tạm thời đóng băng do lệnh nguy hiểm
            registryClass.position().clearPositions();

            this.tbl_EG.getItems().clear();
            this.lbl_EG_page.setText("1");
            this.maxPageEG = 1;
        }
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



    private ObservableList<Position> listEG;
    private ObservableList<Position> listOG;
    private ObservableList<Position> listRG;


    public FruitIdentificationController (){

    }

    @FXML
    void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());

        //Lấy danh sách các trái cây
        this.listEG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", this.pageEG));
        this.listOG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("OR", this.pageOG));
        this.listRG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("RI", this.pageRG));

        //Đưa dữ liệu danh sách vào các bảng
        setInforEGTable(listEG);
        setInforOGTable(listOG);
        setInforRGTable(listRG);

        //Lấy số trang lớn nhất có thể
        this.maxPageEG = registryClass.position().pageQuantity("D");
        this.maxPageOG = registryClass.position().pageQuantity("OR");
        this.maxPageRG = registryClass.position().pageQuantity("RI");
    }
}

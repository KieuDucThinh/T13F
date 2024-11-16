package com.cn;

import com.entity.Position;
import com.util.RegistryClass;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private TableColumn<Position, Date> col_EG_exp;

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
    private TableColumn<Position, Date> col_OG_exp;

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
    private TableColumn<Position, Date> col_RG_ovdate;

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

    public void setInforEGTable(ObservableList<Position> list) {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_EG_sku.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSku());
        });
        this.col_EG_size.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSize());
        });
        this.col_EG_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getStatus());
        });
        this.col_EG_delivery_price.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getDeliveryPrice());
        });
        this.col_EG_exp.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<Date>(((Position) cellData.getValue()).getGoodsPos().getExpDate());
        });
        this.col_EG_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getQuantityInBox());
        });
        this.col_EG_pos.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getPos());
        });
        this.tbl_EG.setItems(list);
    }

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

    public FruitIdentificationController (){

    }

    @FXML
    void initialize() throws RemoteException {
        this.listEG = FXCollections.observableArrayList(registryClass.position().getGoodsPositionsByStatus("D", (byte)1));
        setInforEGTable(listEG);
//        List<Position> list = registryClass.position().getGoodsPositionsByStatus("D",(byte)1);
//        for (Position p : list){
//            System.out.println(p.getGoodsPos().getSku());
//        }
    }
}

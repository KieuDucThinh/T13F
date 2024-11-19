package com.cn;

import com.entity.FruitType;
import com.entity.Position;
import com.util.RegistryClass;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


public class PositionManageController {

    @FXML
    private ComboBox<FruitType> cbo_Ft;

    @FXML
    private TableColumn<Position, String> col_area_col;

    @FXML
    private TableColumn<Position, Short> col_area_row;

    @FXML
    private TableColumn<?, ?> col_expd;

    @FXML
    private TableColumn<?, ?> col_jrd;

    @FXML
    private TableColumn<?, ?> col_original;

    @FXML
    private TableColumn<?, ?> col_ovd;

    @FXML
    private TableColumn<?, ?> col_qinbox;

    @FXML
    private TableColumn<?, ?> col_rd;

    @FXML
    private TableColumn<?, ?> col_sku;

    @FXML
    private TableColumn<?, ?> col_stack;

    @FXML
    private TableColumn<?, ?> col_status;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    private Label lbl_col;

    @FXML
    private Label lbl_row;

    @FXML
    private Label lbl_shelf;

    @FXML
    private TableView<Position> tbl_area;

    @FXML
    private TableView<?> tbl_pos;

    private Map<Button, Integer> buttonIdMap = new HashMap<>();

    private void addBtn(){
        //Don dep
        this.gridPane1.getChildren().clear();
        this.gridPane1.getRowConstraints().clear();
        this.gridPane1.getColumnConstraints().clear();

        //Don dep
        this.gridPane2.getChildren().clear();
        this.gridPane2.getRowConstraints().clear();
        this.gridPane2.getColumnConstraints().clear();

        // Tạo và thêm các button vào GridPane
        byte row = 0;
        byte col = 0;

        //Tạo các nút sau đó thêm vào 2 grid với nút được đánh số từ 1 đến 120
        //từ trái sang phải và từ trên xuống
        for (int i = 1; i <= 120; i++) {
            Button button = new Button("      ");
            button.getStyleClass().add("btn_pos");
            buttonIdMap.put(button, i);
            i+=6;

            Button button2 = new Button("      ");
            button2.getStyleClass().add("btn_pos");
            buttonIdMap.put(button2, i);

            if(i%12 != 0)
                i-=6;

            button.setOnAction(event -> {
                Button clickedButton = (Button) event.getSource();
                int id = buttonIdMap.get(clickedButton);
                System.out.println("Bạn đã nhấn vào nút có ID: " + id);
                // Ở đây bạn sẽ thực hiện các thao tác với cơ sở dữ liệu dựa trên ID
            });

            button2.setOnAction(event -> {
                Button clickedButton = (Button) event.getSource();
                int id = buttonIdMap.get(clickedButton);
                System.out.println("Bạn đã nhấn vào nút có ID: " + id);
                // Ở đây bạn sẽ thực hiện các thao tác với cơ sở dữ liệu dựa trên ID
            });

            //Mỗi grid có 6 cột thêm 1 hàng nếu hàng đó đã đủ 6 phần tử
            if(col==6){
                col = 0;
                row++;
            }

            //Thêm nút và tạo margin
            gridPane1.add(button, col, row);
            GridPane.setMargin(button, new Insets(7.5));
            gridPane2.add(button2, col, row);
            GridPane.setMargin(button2, new Insets(7.5));
            col++;
        }
    }

    //Hàm khởi tạo comboBox
    private void cboInit() throws RemoteException {
        //Xét giá trị cho cbo
        this.cbo_Ft.setItems(FXCollections.observableArrayList(registryClass.fruitType().getAllFruitTypes()));

        //Xét giá trị nếu cbo được update
        this.cbo_Ft.setCellFactory(lv -> new ListCell<FruitType>(){
            protected void updateItem(FruitType item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getFtName());
                } else {
                    setText(null);
                }
            }

        });
    }

    //Khi chọn loại trái cây sẽ điền dữ liệu vào bảng
    public void setAreaByFT() throws RemoteException {
        FruitType fruitType = (FruitType) cbo_Ft.getValue();
        setInforAreaTable(FXCollections.observableArrayList(registryClass.position().getPositionsByFt(fruitType.getFtId())));
    }

    //Chen dữ liệu vào bảng: Khu vực
    public void setInforAreaTable(ObservableList<Position> list) {
        this.col_area_row.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<Short>(((Position) cellData.getValue()).getRow());
        });
        this.col_area_col.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getCol());
        });
        this.tbl_area.setItems(list);
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

    public PositionManageController(){

    }

    @FXML
    void initialize() throws RemoteException {
        addBtn();
        cboInit();
    }
}


package com.cn;

import com.entity.FruitType;
import com.entity.Position;
import com.util.FruitUtil;
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
    private TableColumn<Position, String> col_expd;

    @FXML
    private TableColumn<Position, String> col_jrd;

    @FXML
    private TableColumn<Position, String> col_original;

    @FXML
    private TableColumn<Position, String> col_ovd;

    @FXML
    private TableColumn<Position, BigDecimal> col_qinbox;

    @FXML
    private TableColumn<Position, String> col_rd;

    @FXML
    private TableColumn<Position, String> col_sku;

    @FXML
    private TableColumn<Position, Short> col_stack;

    @FXML
    private TableColumn<Position, String> col_status;

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
    private TableView<Position> tbl_pos;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabPos;

    //Thuộc tính: Tiện ích
    private FruitUtil dict = new FruitUtil();

    //Thuộc tính: Danh sách các hàng trong kệ
    private ObservableList<Position> listPos;

    //Thuộc tính: Quản lý các nút bấm
    private Map<Button, Integer> buttonIdMap = new HashMap<>();

    private Button btn_pos = new Button();

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
                try {
                    clickShelf(clickedButton, id);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                // Ở đây bạn sẽ thực hiện các thao tác với cơ sở dữ liệu dựa trên ID
            });

            button2.setOnAction(event -> {
                Button clickedButton = (Button) event.getSource();
                int id = buttonIdMap.get(clickedButton);
                try {
                    clickShelf(clickedButton, id);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
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

    //Sự kiện nút bấm
    public void clickShelf(Button btn_selected, int id) throws RemoteException {
        //Loại màu đỏ khỏi nút được chọn
        this.btn_pos.getStyleClass().remove("btn_pos_selected");

        //Lấy danh sách các vị trí theo nút được chọn
        this.listPos = FXCollections.observableArrayList(registryClass.position().getPosByPosNumber(id));

        //Set giá trị nhãn
        setPosLabel(listPos.getFirst(), id);

        //Nếu ngăn có hàng thì set giá trị vào bảng
        if(this.listPos.getFirst().getGoodsPos() == null)
            this.tbl_pos.getItems().clear();
        else
            setInfoPosTable(listPos);

        //Chuyển thuộc tính btn_pos chuyển sang chứa nút được chọn và thêm style màu đỏ
        this.btn_pos = btn_selected;
        this.btn_pos.getStyleClass().add("btn_pos_selected");

        //Chọn tab pos hiển thị
        this.tabPane.getSelectionModel().select(tabPos);
    }

    //Set dữ liệu cho các nhãn của vị trí
    public void setPosLabel(Position p, int id){
        this.lbl_shelf.setText(id+"");
        this.lbl_col.setText(p.getCol());
        this.lbl_row.setText(p.getRow()+"");
    }

    //Chèn dữ liệu vào bảng: Vị trí
    public void setInfoPosTable(ObservableList<Position> list) {
        this.col_stack.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<Short>(((Position) cellData.getValue()).getStack());
        });
        this.col_sku.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getSku());
        });
        this.col_status.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(this.dict.encode(((Position) cellData.getValue()).getGoodsPos().getStatus()));
        });
        this.col_original.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((Position) cellData.getValue()).getGoodsPos().getOriginal());
        });
        this.col_jrd.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(this.dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getJustRipeDate()));
        });
        this.col_rd.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(this.dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getRipeDate()));
        });
        this.col_ovd.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(this.dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getOverripDate()));
        });
        this.col_expd.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(this.dict.convertDate(((Position) cellData.getValue()).getGoodsPos().getExpDate()));
        });
        this.col_qinbox.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((Position) cellData.getValue()).getGoodsPos().getQuantityInBox());
        });
        this.tbl_pos.setItems(list);
    }

    //Nếu tab area được chọn (tab pos đang chọn trước đó) thì bỏ màu nút
    public void selectTabArea(){
        if(tabPane.getSelectionModel().getSelectedItem().isSelected())
            this.btn_pos.getStyleClass().remove("btn_pos_selected");
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


package com.cn;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


public class PositionManageController {

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

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

    @FXML
    void initialize() {
        addBtn();
    }
}


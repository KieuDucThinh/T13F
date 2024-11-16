package com.cn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class ButtonGridExample extends Application {

    private Map<Button, Integer> buttonIdMap = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        // Giả sử bạn có danh sách ID từ cơ sở dữ liệu
        int[] ids = {1, 2, 3, 4, 5};

        // Tạo và thêm các button vào GridPane
        for (int i = 0; i < ids.length; i++) {
            Button button = new Button();
            button.setPrefSize(50, 50);
            buttonIdMap.put(button, ids[i]);

            button.setOnAction(event -> {
                Button clickedButton = (Button) event.getSource();
                int id = buttonIdMap.get(clickedButton);
                System.out.println("Bạn đã nhấn vào nút có ID: " + id);
                // Ở đây bạn sẽ thực hiện các thao tác với cơ sở dữ liệu dựa trên ID
            });
            GridPane.setMargin(button, new Insets(10));
            gridPane.add(button, i % 3, i / 3); // Sắp xếp button thành 3 cột
        }

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

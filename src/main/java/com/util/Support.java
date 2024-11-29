package com.util;

import com.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Support {
    public static User account;

    public static Stage stage;
    public static Image icon;
    public static String colorButton = "-fx-background-color:#137b9e";

    public static Scene scene;
    public static Parent root;

    public Support() {
    }

    //Method chuyển đến trang khác
    public static void navigateTo(byte page, Object o) {
        try{
            switch (page) {
                case 2:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/GoodsDelivery.fxml"));
                    break;
                case 3:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/FruitIdentification.fxml"));
                    break;
                default:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/FruitIdentification.fxml"));
            }
            Support.scene.setRoot(Support.root);
            Support.stage.show();
        } catch (IOException ex) {
            System.out.println("Không thấy file " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Lỗi");
            e.printStackTrace();
        }
    }
}

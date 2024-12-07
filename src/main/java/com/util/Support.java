package com.util;

import com.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Support {
    public static User account = new User();

    public static Stage stage;
    public static Image icon;
    public static String colorButton = "-fx-background-color:#137b9e";

    public static Scene scene;
    public static Parent root;

    private static FruitUtil fruitUtil = new FruitUtil();

    public Support() {
    }

    //Method chuyển đến trang khác
    public static void navigateTo(byte page, Object o) {
        try{
            if(page == 0){
                Support.stage.setWidth(800);
                Support.stage.setHeight(600);
            }

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            double x = bounds.getMinX() + (bounds.getWidth() - Support.stage.getWidth()) / 2;
            double y = bounds.getMinY() + (bounds.getHeight() - Support.stage.getHeight()) / 2;

            Support.stage.setX(x);
            Support.stage.setY(y);

            switch (page) {
                case 0:
                    Support.account.setUsername(null);
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/Login.fxml"));
                    break;
                case 2:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/GoodsDelivery.fxml"));
                    break;
                case 3:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/FruitIdentification.fxml"));
                    break;
                case 4:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/StaffAccountManage.fxml"));
                    break;
                case 9:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/PositionManage.fxml"));
                    break;
                case 11:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/ManagerAccountManage.fxml"));
                    break;
                default:
                    Support.root = (Parent) FXMLLoader.load(o.getClass().getResource("/com/cn/Login.fxml"));
                    Support.account.setUsername(null);
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

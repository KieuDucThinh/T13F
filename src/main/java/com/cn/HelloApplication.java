package com.cn;

import com.util.RegistryClass;
import com.util.Support;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class HelloApplication extends Application {
    //    private AnchorPane root;

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

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//            System.out.println(this.getClass().getResource("FruitIdentification.fxml"))
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnverifyGRNManage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

            //Set stage và scene cho lớp Support trong util
            Support.stage = stage;
            Support.scene = scene;
            Support.stage.setResizable(false);
    }

    @Override
    public void stop() throws RemoteException {
        if(Support.account.getUsername() != null){
            this.registryClass.user().logoutUser(Support.account);
        }
    }

    //PositionManage
    //GoodsDelivery.fxml
    public static void main2(String[] args) {
        launch();
    }
/*
    Parent root = FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/LoginDesign.fxml"));
//            Parent root = FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/TenantDesign_1.fxml"));

    Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);

    // Kiểm tra xem đường dẫn hình ảnh có chính xác không
    //System.out.println(getClass().getResource("/com/ltc/btl_javafx/imageIcon/icon_miniapartment.png"));
    Support.icon = new Image("/com/ltc/btl_javafx/imageIcon/icon_miniapartment.png");
            primaryStage.getIcons().add(Support.icon);

            primaryStage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Đăng nhập");
            primaryStage.show();
            */

}
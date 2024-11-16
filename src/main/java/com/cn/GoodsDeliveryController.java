package com.cn;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public class GoodsDeliveryController {

    @FXML
    private GridPane gridPaneGD;

    private void clearGridPaneGD(){
        this.gridPaneGD.getChildren().clear();
        this.gridPaneGD.getRowConstraints().clear();
        this.gridPaneGD.getColumnConstraints().clear();
    }

    private short row = 0;

    private void addCardGD(){
        //Chèn CardGD vào Grid
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/com/cn/CardGoodsDelivery.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            CardGoodsDeliveryController controller = (CardGoodsDeliveryController) loader.getController();
            this.gridPaneGD.add(pane, 0, row++);
            GridPane.setMargin(pane, new Insets(3,0,10,0));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removeCardGD(Object obj){
        try{
            this.gridPaneGD.getChildren().remove(obj);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        clearGridPaneGD();
        addCardGD();
        addCardGD();
        addCardGD();
        addCardGD();
        addCardGD();
    }
}


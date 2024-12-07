package com.cn;

import com.util.FruitUtil;
import com.util.RegistryClass;
import com.util.Support;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsManageController {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label lblFullName;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private PieChart pieChart2;

    @FXML
    private AnchorPane supportSceen;

    private FruitUtil dict = new FruitUtil();

    @FXML
    void clickBtnLogOut(ActionEvent event) {

    }

    //Đưa dữ liệu vào pie chart
    public void setDataToPieChart() throws RemoteException{
        //Lấy dữ liệu tồn kho
        HashMap<String, Long> map = (HashMap<String, Long>) this.registryClass.position().getInventory();

        //Tạo List chứa data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        //Đưa dữ liệu vào List
        for (String key : map.keySet()) {
            pieChartData.add(new PieChart.Data(key, map.get(key)));
        }

        //Chèn dữ liệu vào hình
        pieChart.getData().addAll(pieChartData);

        //Chèn dữ liệu vào hình to
        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
        for (String key : map.keySet()) {
            pieChartData2.add(new PieChart.Data(key, map.get(key)));
        }
        pieChart2.getData().addAll(pieChartData2);
    }

    //Mở màn hình phụ
    public void openSupportSceen(){
        supportSceen.setVisible(true);
    }

    //Đóng màn hình phụ
    public void closeSupportSceen(){
        supportSceen.setVisible(false);
    }

    //Đưa dữ liệu vào biểu đồ lợi nhuận và doanh thu
    public void setDataToLineAndBarChart() throws RemoteException{
        ArrayList<HashMap<String, BigDecimal>> listAll = (ArrayList<HashMap<String, BigDecimal>>) this.registryClass.position().getRevenueAndProfit();

        //Lấy lợi nhuận
        HashMap<String, BigDecimal> mapProfit = listAll.get(0);

        //Tạo dữ liệu
        XYChart.Series seriesProfit = new XYChart.Series();
        for (int i = 0; i < 10; i++) {
            seriesProfit.getData().add(new XYChart.Data(dict.getTime((byte) i), mapProfit.get(dict.getTime((byte) i))));
        }

        //Thêm dữ liệu vào biểu đồ
        lineChart.getData().addAll(seriesProfit);

        //Lấy doanh thu
        HashMap<String, BigDecimal> mapRevenue = listAll.get(1);

        //Tạo dữ liệu
        XYChart.Series seriesRevenue = new XYChart.Series();
        for (int i = 0; i < 10; i++) {
            seriesRevenue.getData().add(new XYChart.Data(dict.getTime((byte) i), mapRevenue.get(dict.getTime((byte) i))));
        }

        //Thêm dữ liệu vào biểu đồ
        barChart.getData().addAll(seriesRevenue);

        //Thêm màu và chú thích
        seriesProfit.getNode().setStyle("-fx-stroke: #82C8E1; -fx-stroke-width: 3px;");
        seriesProfit.setName("Lợi nhuận");
        seriesRevenue.setName("Doanh thu");
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

    public StatisticsManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {
        lblFullName.setText(Support.account.getFullname());
        setDataToPieChart();
        setDataToLineAndBarChart();
    }
}

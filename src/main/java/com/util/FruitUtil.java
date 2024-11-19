package com.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//Lớp tiện ích dùng chuyển đổi dữ liệu cho người dùng dễ đọc
public class FruitUtil {
    private static final HashMap<String, String> dict;
    private static final HashMap<String, String> dictdecode;
    private static final DateFormat dfm_encode;
    private Alert alertWarning = new Alert(Alert.AlertType.WARNING);

    static{
        dfm_encode = new SimpleDateFormat("dd/MM/yyyy");
        dict = new HashMap<>();
        dict.put("UR","Chưa chín");
        dict.put("JR","Vừa chín");
        dict.put("RI","Chín");
        dict.put("OR","Quá chín");
        dict.put("D","Hết hạn");
        dict.put("P","Chuẩn bị nhập");

        dictdecode = new HashMap<>();
        dictdecode.put("Chưa chín", "UR");
        dictdecode.put("Vừa chín", "JR");
        dictdecode.put("Chín", "RI");
        dictdecode.put("Quá chín", "OR");
        dictdecode.put("Hết hạn", "D");
        dictdecode.put("Chuẩn bị nhập", "P");
    }

    public FruitUtil(){

    }

    //Giải mã trạng thái trái cây
    public String encode(String word){
        return dict.get(word);
    }

    //Định dạng ngày: dd/mm/YYYY
    public String convertDate(Date date){
        return dfm_encode.format(date);
    }

    public boolean showAlertWarning(){
        this.alertWarning.setTitle("Xác nhận");
        this.alertWarning.setHeaderText("Bạn có chắc chắn muốn dọn dẹp không?");
        this.alertWarning.setContentText("Hành động này không thể hoàn tác.");

        // Tạo các nút tùy chỉnh
        ButtonType buttonTypeYes = new ButtonType("Có");
        ButtonType buttonTypeNo = new ButtonType("Không");

        // Thiết lập các nút cho Alert
        this.alertWarning.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        var result = this.alertWarning.showAndWait();

        // Thực hiện hành động khi người dùng chọn Có
        // Thực hiện hành động khi người dùng chọn Không hoặc hủy
        return result.isPresent() && result.get() == buttonTypeYes;
    }
}

package com.util;

import com.entity.Goods;
import com.entity.GoodsReceivedNote;
import com.entity.GrnDetail;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

//Lớp tiện ích dùng chuyển đổi dữ liệu cho người dùng dễ đọc
public class FruitUtil {
    private static final HashMap<String, String> dict;
    private static final HashMap<String, String> dictdecode;
    private static final HashMap<String, String> dictAddress;
    private static final DateFormat dfm_encode;
    private static Alert alertWarning = new Alert(Alert.AlertType.WARNING);
    private static Alert alertError = new Alert(Alert.AlertType.ERROR);
    private static Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

    private static LocalDateMonthY time;
    private static ArrayList<String> listTime;

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

        dictAddress = new HashMap<>();
        dictAddress.put("Thành phố Hà Nội", "001");
        dictAddress.put("Hà Giang", "002");
        dictAddress.put("Cao Bằng", "004");
        dictAddress.put("Bắc Kạn", "006");
        dictAddress.put("Tuyên Quang", "008");
        dictAddress.put("Lào Cai", "110");
        dictAddress.put("Điện Biên", "111");
        dictAddress.put("Lai Châu", "112");
        dictAddress.put("Sơn La", "114");
        dictAddress.put("Yên Bái", "115");
        dictAddress.put("Hòa Bình", "117");
        dictAddress.put("Thái Nguyên", "119");
        dictAddress.put("Lạng Sơn", "220");
        dictAddress.put("Quảng Ninh", "222");
        dictAddress.put("Bắc Giang", "224");
        dictAddress.put("Phú Thọ", "225");
        dictAddress.put("Vĩnh Phúc", "226");
        dictAddress.put("Bắc Ninh", "227");
        dictAddress.put("Hải Dương", "330");
        dictAddress.put("Thành phố Hải Phòng", "331");
        dictAddress.put("Hưng Yên", "333");
        dictAddress.put("Thái Bình", "334");
        dictAddress.put("Hà Nam", "335");
        dictAddress.put("Nam Định", "336");
        dictAddress.put("Ninh Bình", "337");
        dictAddress.put("Thanh Hóa", "338");
        dictAddress.put("Nghệ An", "440");
        dictAddress.put("Hà Tĩnh", "442");
        dictAddress.put("Quảng Bình", "444");
        dictAddress.put("Quảng Trị", "445");
        dictAddress.put("Thừa Thiên-Huế", "446");
        dictAddress.put("Thành phố Đà Nẵng", "448");
        dictAddress.put("Quảng Nam", "449");
        dictAddress.put("Quảng Ngãi", "551");
        dictAddress.put("Bình Định", "552");
        dictAddress.put("Phú Yên", "554");
        dictAddress.put("Khánh Hòa", "556");
        dictAddress.put("Ninh Thuận", "558");
        dictAddress.put("Bình Thuận", "660");
        dictAddress.put("Kon Tum", "662");
        dictAddress.put("Gia Lai", "664");
        dictAddress.put("Đắk Lắk", "666");
        dictAddress.put("Đắc Nông", "667");
        dictAddress.put("Lâm Đồng", "668");
        dictAddress.put("Bình Phước", "770");
        dictAddress.put("Tây Ninh", "772");
        dictAddress.put("Bình Dương", "777");
        dictAddress.put("Đồng Nai", "775");
        dictAddress.put("Bà Rịa-Vũng Tàu", "777");
        dictAddress.put("Thành Phố Hồ Chí Minh", "779");
        dictAddress.put("Long An", "880");
        dictAddress.put("Tiền Giang", "882");
        dictAddress.put("Bến Tre", "883");
        dictAddress.put("Trà Vinh", "884");
        dictAddress.put("Vĩnh Long", "886");
        dictAddress.put("Đồng Tháp", "887");
        dictAddress.put("An Giang", "889");
        dictAddress.put("Kiên Giang", "991");
        dictAddress.put("Thành phố Cần Thơ", "992");
        dictAddress.put("Hậu Giang", "993");
        dictAddress.put("Sóc Trăng", "994");
        dictAddress.put("Bạc Liêu", "995");
        dictAddress.put("Cà Mau", "996");

        time = new LocalDateMonthY();
        listTime = new ArrayList<>();

        String date;
        for (int i = 9; i >= 0; i--) {
            date = time.previousMonth(i) + "-";
            if (time.getMonthNow() < time.previousMonth(i)) {
                date += time.previousYear(1);
            } else {
                date += time.getYearNow();
            }
            listTime.add(date);
        }
    }

    public FruitUtil(){

    }

    //Giải mã giới tính
    public String encode(boolean gender){
        if(gender)
            return "Nữ";
        return "Nam";
    }

    //Giải mã trạng thái trái cây
    public String encode(String word){
        return dict.get(word);
    }

    //Mã hóa trạng thái trái cây
    public String decode(String word){
        return dictdecode.get(word);
    }

    //Mã hóa địa chỉ
    public String encodeAddrress(String word){
        return dictAddress.get(word);
    }

    //Định dạng ngày: dd/mm/YYYY
    public String convertDate(Date date){
        return dfm_encode.format(date);
    }

    //Định dạng ngày: dd/mm/YYYY
    public LocalDate convertDate2(Date date){
        return LocalDate.parse(date.toString());
    }

    //Định dạng ngày: dd/mm/YYYY
    public String convertDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    //Định dạng ngày: dd/mm/YYYY
    public LocalDate convertDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean showAlertWarning(String headerText){
        this.alertWarning.setTitle("Xác nhận");
        this.alertWarning.setHeaderText(headerText);
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

    public void showAlertError(String headerText){
        this.alertError.setTitle("Kiểm tra lại dữ liệu nhập vào");
        this.alertError.setHeaderText(headerText);

        // Tạo các nút tùy chỉnh
        ButtonType buttonTypeOK = new ButtonType("OK");

        // Thiết lập các nút cho Alert
        this.alertError.getButtonTypes().setAll(buttonTypeOK);

        this.alertError.showAndWait();
    }

    public void showAlertInfo(String headerText){
        this.alertInfo.setTitle("Kiểm tra lại dữ liệu nhập vào");
        this.alertInfo.setHeaderText(headerText);

        // Tạo các nút tùy chỉnh
        ButtonType buttonTypeOK = new ButtonType("OK");

        // Thiết lập các nút cho Alert
        this.alertInfo.getButtonTypes().setAll(buttonTypeOK);

        this.alertInfo.showAndWait();
    }

    public void showAlertInfo(String title, String headerText){
        this.alertInfo.setTitle(title);
        this.alertInfo.setHeaderText(headerText);

        // Tạo các nút tùy chỉnh
        ButtonType buttonTypeOK = new ButtonType("OK");

        // Thiết lập các nút cho Alert
        this.alertInfo.getButtonTypes().setAll(buttonTypeOK);

        this.alertInfo.showAndWait();
    }

    public boolean showAlertWarning(String headerText, String contentText){
        this.alertWarning.setTitle("Xác nhận");
        this.alertWarning.setHeaderText(headerText);
        this.alertWarning.setContentText(contentText);

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

    public void showAlertError(String title, String headerText){
        this.alertError.setTitle(title);
        this.alertError.setHeaderText(headerText);

        // Tạo các nút tùy chỉnh
        ButtonType buttonTypeOK = new ButtonType("OK");

        // Thiết lập các nút cho Alert
        this.alertError.getButtonTypes().setAll(buttonTypeOK);

        this.alertError.showAndWait();
    }

    public String getTime(byte index){
        return listTime.get(index);
    }

    public String getSKU(Goods tc, LocalDate date){
        String datePart = date.format(DateTimeFormatter.ofPattern("ddMMyy"));
        return tc.getGoodsFruitType().getFtId() + "-" + tc.getSize() + "-" + dictAddress.get(tc.getOriginal()) + "-" + datePart + "-" + tc.getStatus();
    }
}

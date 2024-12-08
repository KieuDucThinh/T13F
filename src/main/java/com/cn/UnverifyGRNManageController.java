package com.cn;

import com.dao.DAOFruitType;
import com.dao.DAOGoodsReceivedNote;
import com.dao.DAOSupplier;
import com.entity.*;
import com.util.FruitUtil;
import com.util.LocalDateMonthY;
import com.util.RegistryClass;
import com.util.Support;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public class UnverifyGRNManageController {

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_supplier_name;

    @FXML
    private TableColumn<GrnDetail, BigDecimal> col_received_price;

    @FXML
    private TableColumn<GrnDetail, String> col_status;

    @FXML
    private TableColumn<GrnDetail, String> col_ft;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_date;

    @FXML
    private TableColumn<GoodsReceivedNote, String> col_grn_id;

//    @FXML
//    private TableColumn<GoodsReceivedNote, String> col_sup_addr;

    @FXML
    private TableColumn<GrnDetail, Integer> col_index;

    @FXML
    private TableColumn<GrnDetail, String> col_original;

    @FXML
    private TableColumn<GrnDetail, String> col_qinbox;

    @FXML
    private TableColumn<GrnDetail, Short> col_quantity;

    @FXML
    private TableColumn<GrnDetail, String> col_sku;

    @FXML
    private TableColumn<GoodsReceivedNote, BigDecimal> col_total_price;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lbl_sup_id;

    @FXML
    private Label lbl_sup_name;

    @FXML
    private Label lbl_sup_phone;

    @FXML
    private Label lbl_sup_addr;

    @FXML
    private Label lbl_grn_date;

    @FXML
    private Label lbl_grn_id;

    @FXML
    private Label lbl_total_price;

    @FXML
    private TabPane tab_pane;

    @FXML
    private Tab tab_uvgrn_info;

    @FXML
    private Tab tab_list;

    @FXML
    private TableView<GoodsReceivedNote> tbl_Unverify_GRN;

    @FXML
    private TableView<GrnDetail> tbl_GRN_de;

    @FXML
    private TextField txtKeyWord;

    @FXML
    private TextArea txt_grn_des;

    @FXML
    private ComboBox<FruitType> cbo_ft_name;

    @FXML
    private ComboBox<String> cbo_size;

    @FXML
    private ComboBox<String> cbo_status;

    @FXML
    private TableColumn<GrnDetail, BigDecimal> col_delivery_price2;

//    @FXML
//    private TableColumn<?, ?> col_ft;
//
//    @FXML
//    private TableColumn<?, ?> col_grn_date;
//
//    @FXML
//    private TableColumn<?, ?> col_grn_id;
//
//    @FXML
//    private TableColumn<?, ?> col_index;
//
//    @FXML
//    private TableColumn<?, ?> col_original;

    @FXML
    private TableColumn<GrnDetail, String> col_original2;

//    @FXML
//    private TableColumn<?, ?> col_qinbox;

    @FXML
    private TableColumn<GrnDetail, String> col_qinbox2;

//    @FXML
//    private TableColumn<?, ?> col_quantity;

    @FXML
    private TableColumn<GrnDetail, Short> col_quantity2;

//    @FXML
//    private TableColumn<?, ?> col_received_price;

    @FXML
    private TableColumn<GrnDetail, BigDecimal> col_received_price2;

//    @FXML
//    private TableColumn<?, ?> col_sku;

    @FXML
    private TableColumn<GrnDetail, String> col_sku2;

//    @FXML
//    private TableColumn<?, ?> col_status;
//
//    @FXML
//    private TableColumn<?, ?> col_supplier_name;
//
//    @FXML
//    private TableColumn<?, ?> col_total_price;

//    @FXML
//    private Label lblFullName;
//
//    @FXML
//    private Label lbl_grn_date;
//
//    @FXML
//    private Label lbl_grn_id;

    @FXML
    private Label lbl_grn_id1;

//    @FXML
//    private Label lbl_sup_addr;

    @FXML
    private Label lbl_sup_addr1;

//    @FXML
//    private Label lbl_sup_id;

    @FXML
    private Label lbl_sup_id1;

//    @FXML
//    private Label lbl_sup_name;
//
//    @FXML
//    private Label lbl_sup_phone;

    @FXML
    private Label lbl_sup_phone1;

//    @FXML
//    private Label lbl_total_price;

    @FXML
    private Label lbl_unit;

    @FXML
    private Label lbl_total;

    @FXML
    private Tab tab_basic_form;

    @FXML
    private Tab tab_detail_form;

//    @FXML
//    private Tab tab_list;
//
//    @FXML
//    private TabPane tab_pane;

    @FXML
    private TabPane tab_pane_form;

//    @FXML
//    private Tab tab_uvgrn_info;
//
//    @FXML
//    private TableView<?> tbl_GRN_de;
//
//    @FXML
//    private TableView<?> tbl_Unverify_GRN;

    @FXML
    private TableView<GrnDetail> tbl_grn_detail;

//    @FXML
//    private TextField txtKeyWord;

    @FXML
    private TextField txt_delivery_price;

    @FXML
    private DatePicker txt_grn_date;

//    @FXML
//    private TextArea txt_grn_des;

    @FXML
    private TextArea txt_grn_des1;

    @FXML
    private TextField txt_original;

    @FXML
    private TextField txt_qinbox;

    @FXML
    private TextField txt_quantity;

    @FXML
    private TextField txt_received_price;

    @FXML
    private TextField txt_sup_name;

    private ObservableList<GoodsReceivedNote> list_Unverify_GRN;
    private ObservableList<GrnDetail> listGrnDetail;

    //Form
    private ObservableList<FruitType> listFruitType;
    private ArrayList<Supplier> supplierList;


    //Trung gian form
    private GrnDetail temp;
    private String tempSKU;
    private boolean flag = false;
    private Supplier supplier;
    private BigDecimal total = BigDecimal.ZERO;

    private HashSet<String> skuSet = new HashSet<>();
    private ObservableList<GrnDetail> grnDetailListForm = FXCollections.observableArrayList(new ArrayList<GrnDetail>());

    private FruitUtil dict = new FruitUtil();

    public void clickBtnFindUVGRN() throws RemoteException {
        try{
            //Chuyển text thành LocalDate để đúng định dạng và toString
            this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getUnverifyGRNByKeyword(dict.convertDate(txtKeyWord.getText().trim()).toString()));
            tbl_Unverify_GRN.setItems(list_Unverify_GRN);
        } catch (Exception e){
            //Nếu không phải ngày tháng năm thì xuống đây
            this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getUnverifyGRNByKeyword(txtKeyWord.getText().trim()));
            tbl_Unverify_GRN.setItems(list_Unverify_GRN);
        }
    }

    public void clickBtnRefresh() throws RemoteException {
        txtKeyWord.clear();
        this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllNotVerifyGRN());
        tbl_Unverify_GRN.setItems(list_Unverify_GRN);
    }

    public void view() {
        GoodsReceivedNote grn = this.tbl_Unverify_GRN.getSelectionModel().getSelectedItem();
        if(grn == null)
            return;

        tab_uvgrn_info.setDisable(false);

        //Chuyển tab
        tab_pane.getSelectionModel().select(tab_uvgrn_info);
        this.listGrnDetail = FXCollections.observableArrayList(grn.getGrnDetailsSet());
        tbl_GRN_de.setItems(listGrnDetail);

        lbl_grn_id.setText(grn.getGrnId());
        lbl_sup_addr.setText(grn.getSupplier().getSupAddress());
        lbl_sup_id.setText(grn.getSupplier().getSupId());
        lbl_sup_name.setText(grn.getSupplier().getSupName());
        lbl_sup_phone.setText(grn.getSupplier().getSupPhone());

        lbl_grn_date.setText(dict.convertDate(grn.getGrnDate()));
        txt_grn_des.setText(grn.getGrnDescription());
        lbl_total_price.setText(grn.getTotalReceivedPrice().toString());
    }

    //Đưa dữ liệu vào bảng
    public void setInforUnverify_GRNTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_grn_id.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(((GoodsReceivedNote) cellData.getValue()).getGrnId());
        });
        this.col_grn_date.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty(dict.convertDate(((GoodsReceivedNote) cellData.getValue()).getGrnDate()));
        });
        this.col_supplier_name.setCellValueFactory((cellData) ->{
            return new SimpleStringProperty((((GoodsReceivedNote) cellData.getValue()).getSupplier().getSupName()));
        });
        this.col_total_price.setCellValueFactory((cellData) ->{
            return new SimpleObjectProperty<BigDecimal>(((GoodsReceivedNote) cellData.getValue()).getTotalReceivedPrice());
        });
//        this.col_sup_addr.setCellValueFactory((cellData) ->{
//            return new SimpleStringProperty((((GoodsReceivedNote) cellData.getValue()).getSupplier().getSupAddress()));
//        });
        this.tbl_Unverify_GRN.setItems(list_Unverify_GRN);
    }

    //Đưa dữ liệu vào bảng
    public void setInforGrnDetailTable() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_index.setCellValueFactory((cellData) -> {
            return new SimpleIntegerProperty(listGrnDetail.indexOf(cellData.getValue()) + 1).asObject();
        });
        this.col_sku.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku());
        });
        this.col_ft.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getGoodsFruitType().getFtName());
        });
        this.col_original.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getOriginal()));
        });
        this.col_qinbox.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getQuantityInBox().toString() + " " + ((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getGoodsFruitType().getUnitOfCalculation()));
        });
        this.col_quantity.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<Short>(((GrnDetail) cellData.getValue()).getQuantity());
        });
        this.col_received_price.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<BigDecimal>(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getReceivedPrice());
        });
        this.col_status.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(dict.encode(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku().substring(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku().length() - 2)));
        });
        this.tbl_GRN_de.setItems(listGrnDetail);
    }

    //Đưa dữ liệu vào bảng
    public void setInforGrnDetailTable2() {
//        this.col_EG_sku.setCellValueFactory(new PropertyValueFactory<>("posId"));
        this.col_sku2.setCellValueFactory((cellData) -> {
            if(!((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getStatus().equals("P"))
                return new SimpleStringProperty(dict.getSKU(((GrnDetail) cellData.getValue()).getGoodsGRNDetail(), txt_grn_date.getValue()));
            else
                return new SimpleStringProperty(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getSku());
        });
        this.col_original2.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getOriginal()));
        });
        this.col_qinbox2.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty((((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getQuantityInBox().toString() + " " + ((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getGoodsFruitType().getUnitOfCalculation()));
        });
        this.col_quantity2.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<Short>(((GrnDetail) cellData.getValue()).getQuantity());
        });
        this.col_received_price2.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<BigDecimal>(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getReceivedPrice());
        });
        this.col_delivery_price2.setCellValueFactory((cellData) -> {
            return new SimpleObjectProperty<BigDecimal>(((GrnDetail) cellData.getValue()).getGoodsGRNDetail().getDeliveryPrice());
        });
        this.tbl_grn_detail.setItems(grnDetailListForm);
    }

    private void configTimeForGoods(){
        LocalDate urDate;
        for (GrnDetail grnDetail : grnDetailListForm){
            //Nếu trạng thái là P cần đưa về trạng thái đúng
            if(grnDetail.getGoodsGRNDetail().getStatus().equals("P")){
                grnDetail.getGoodsGRNDetail().setStatus(grnDetail.getGoodsGRNDetail().getSku().substring(grnDetail.getGoodsGRNDetail().getSku().length() - 2));
            }

            //Nếu trái cây có trạng thái vừa chín thì đưa thời gian về chưa chín để xử lý
            if(grnDetail.getGoodsGRNDetail().getStatus().equals("JR")){
                urDate = txt_grn_date.getValue().minusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToJustRipe());
            } else {
                urDate = txt_grn_date.getValue();
            }

            grnDetail.getGoodsGRNDetail().setJustRipeDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToJustRipe())));
            grnDetail.getGoodsGRNDetail().setRipeDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToRipe())));
            grnDetail.getGoodsGRNDetail().setOverripDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToOverripe())));
            grnDetail.getGoodsGRNDetail().setExpDate(Date.valueOf(urDate.plusDays(grnDetail.getGoodsGRNDetail().getGoodsFruitType().getTimeToDamaged())));
        }
    }

    //Nhấn nút thêm mới
    public void clickBtnAddGrnDetail(){
        if(dict.encodeAddrress(txt_original.getText())==null){
            dict.showAlertError("Nguồn gốc không hợp lệ");
            flag = false;
            return;
        }

        Goods goods = Goods.builder()
                .goodsFruitType(cbo_ft_name.getValue())
                .size(cbo_size.getValue())
                .status(dict.decode(cbo_status.getValue()))
                .deliveryPrice(BigDecimal.valueOf(Double.parseDouble(txt_delivery_price.getText())))
                .receivedPrice(BigDecimal.valueOf(Double.parseDouble(txt_received_price.getText())))
                .original(txt_original.getText())
                .quantityInBox(BigDecimal.valueOf(Double.parseDouble(txt_quantity.getText())))
                .availableQuantity((short) 0)
                .totalQuantity((short) 0)
                .build();

        GrnDetail grnDetail = GrnDetail.builder()
                .goodsGRNDetail(goods)
                .quantity(Short.parseShort(txt_quantity.getText()))
                .build();

        if(!skuSet.add(dict.getSKU(grnDetail.getGoodsGRNDetail(), txt_grn_date.getValue()))){
            dict.showAlertError("Sản phẩm này đã nằm trong danh sách");
            flag = false;
            return;
        }

        flag = true;
        grnDetailListForm.add(grnDetail);

        //Tổng tiền
        Goods g = grnDetail.getGoodsGRNDetail();
        total = total.add(g.getReceivedPrice().multiply(BigDecimal.valueOf(grnDetail.getQuantity())));
        lbl_total.setText(total.toString());

//        tbl_grn_detail.setItems(grnDetailListForm);
        clickBtnClearTxtDetailForm();
    }

    //Nút xóa hàng
    public void clickBtnDel(){
        if(!skuSet.remove(tempSKU)){
            dict.showAlertError("Không thể xóa", "Vui lòng chọn trái cây cần xóa");
            flag = false;
            return;
        }
        grnDetailListForm.remove(temp);

        //Tổng tiền
        total = total.subtract(temp.getGoodsGRNDetail().getReceivedPrice().multiply(BigDecimal.valueOf(temp.getQuantity())));
        lbl_total.setText(total.toString());

        clickBtnClearTxtDetailForm();
        flag = true;
    }

    //Nút cập nhật hàng
    public void clickBtnUpdateGrnDetail(){
        if(!skuSet.remove(tempSKU)){
            dict.showAlertError("Không thể xóa", "Vui lòng chọn trái cây cần xóa");
            flag = false;
            return;
        }
        grnDetailListForm.remove(temp);

        //Tổng tiền
        total = total.subtract(temp.getGoodsGRNDetail().getReceivedPrice().multiply(BigDecimal.valueOf(temp.getQuantity())));
        lbl_total.setText(total.toString());
        flag = true;

        //Nếu xóa được thì mới thêm cái mới
        if(flag)
            clickBtnAddGrnDetail();
    }

    //Nút hủy để không tạo đơn nữa
    //Khởi tạo lại toàn bộ giá trị
    public void clickBtnCancle(){
        clickBtnClearTxtDetailForm();
        lbl_grn_id1.setText("");
        lbl_sup_id1.setText("Null");
        lbl_sup_addr1.setText("Null");
        lbl_sup_phone1.setText("Null");
        txt_sup_name.clear();
        txt_grn_date.setValue(LocalDate.now());
        txt_grn_des1.clear();

        flag = false;
        supplier = null;
        total = BigDecimal.ZERO;

        skuSet = new HashSet<>();
        grnDetailListForm = FXCollections.observableArrayList(new ArrayList<GrnDetail>());
        tbl_grn_detail.setItems(grnDetailListForm);

        tab_basic_form.setDisable(false);
        tab_detail_form.setDisable(true);
        tab_pane_form.setVisible(false);
    }

    //Tạo phiếu nhập
    public void clickBtnSaveGRN() throws RemoteException {
        if(grnDetailListForm.isEmpty()){
            dict.showAlertError("Không có hàng không thể lưu phiếu");
            return;
        }

        //Đưa giá trị vào biến
        //Đặt thời gian cho hàng hóa
        configTimeForGoods();

        //(Nhà cung cấp --> Đơn nhập hàng
        GoodsReceivedNote grn = GoodsReceivedNote.builder()
                .supplier(supplier)
                .grnDate(Timestamp.valueOf(txt_grn_date.getValue().atStartOfDay()))
                .totalReceivedPrice(total)
                .grnDescription(txt_grn_des1.getText())
                .build();

        grn.setGrnDetailsSet(new HashSet<>(grnDetailListForm));

        //Lưu phiếu
        if(lbl_grn_id1.getText().equals("")){
            if (this.registryClass.goodsReceivedNote().createGRN(grn)){
                dict.showAlertInfo("Tạo đơn nhập thành công", "Vui lòng kiểm tra lại phiếu nhập.","Nếu trái cây đã trong kho thì sẽ có giá trị cũ.\nMở phiếu nhập và sửa nếu cần.");
                clickBtnCancle();
                tab_uvgrn_info.setDisable(true);
                tab_pane.getSelectionModel().select(tab_list);
                clickBtnRefresh();
            }
            else
                dict.showAlertError("Thất bại", "Tạo đơn nhập không thành công");
        } else {
            grn.setGrnId(lbl_grn_id1.getText());
            //Sửa phiếu
            boolean isUpdateGoods = dict.showAlertWarning("Bạn có muốn cập nhật dữ liệu\ncho các hàng đã có nếu hàng chưa nhập");

            if (this.registryClass.goodsReceivedNote().updateGRN(grn, isUpdateGoods)){
                dict.showAlertInfo("Sửa đơn nhập thành công", "Vui lòng kiểm tra lại phiếu nhập.","Mở lại phiếu nhập và sửa nếu cần.");
                clickBtnCancle();
                tab_uvgrn_info.setDisable(true);
                tab_pane.getSelectionModel().select(tab_list);
                clickBtnRefresh();
            }
            else
                dict.showAlertError("Thất bại", "Sửa đơn nhập không thành công");
        }
    }

    //Nút mở form sửa
    public void clickBtnUpdateGRN(){
        GoodsReceivedNote grn = this.tbl_Unverify_GRN.getSelectionModel().getSelectedItem();
        if(grn == null)
            return;

        //Đưa giá trị vào form và các biến trung gian
        this.grnDetailListForm = FXCollections.observableArrayList(grn.getGrnDetailsSet());
        tbl_grn_detail.setItems(grnDetailListForm);

        // - Đưa giá trị vào form
        lbl_grn_id1.setText(grn.getGrnId());
        lbl_sup_addr1.setText(grn.getSupplier().getSupAddress());
        lbl_sup_id1.setText(grn.getSupplier().getSupId());
        txt_sup_name.setText(grn.getSupplier().getSupName());
        lbl_sup_phone1.setText(grn.getSupplier().getSupPhone());
        txt_grn_date.setValue(grn.getGrnDate().toLocalDateTime().toLocalDate());
        txt_grn_des1.setText(grn.getGrnDescription());
        lbl_total.setText(grn.getTotalReceivedPrice().toString());

        // - Đưa giá trị vào biến trung gian
        supplier = grn.getSupplier();
        total = grn.getTotalReceivedPrice();

        Iterator<GrnDetail> it = grn.getGrnDetailsSet().iterator();
        if(it.hasNext()){
            skuSet.add(it.next().getGoodsGRNDetail().getSku());
        }

        //Mở form lên
        tab_pane_form.getSelectionModel().select(tab_basic_form);
        tab_pane_form.setVisible(true);
    }

    //Nút mở form thêm
    public void clickBtnAddGRN(){
        tab_pane_form.getSelectionModel().select(tab_basic_form);
        tab_pane_form.setVisible(true);
    }

    //Xem lại hàng từ bảng
    public void view2() {
        GrnDetail grn = this.tbl_grn_detail.getSelectionModel().getSelectedItem();

        if(grn == null)
            return;

        //Lấy chỉ số
        int index = this.tbl_grn_detail.getSelectionModel().getSelectedIndex();

        //Lấy sku từ cột
        String sku = this.col_sku2.getCellData(index);

        //Đưa giá trị vào form
        Goods g = grn.getGoodsGRNDetail();

        for(FruitType ft : listFruitType){
            if(ft.getFtId().equals(g.getGoodsFruitType().getFtId())){
                cbo_ft_name.setValue(ft);
                System.out.println(ft);
            }
        }
//        cbo_ft_name.setValue(g.getGoodsFruitType());

        if(g.getStatus().equals("P"))
            cbo_status.setValue(dict.encode(g.getSku().substring(g.getSku().length() - 2)));
        else
            cbo_status.setValue(dict.encode(g.getStatus()));
        cbo_size.setValue(g.getSize());

        txt_delivery_price.setText(String.valueOf(g.getDeliveryPrice()));
        txt_received_price.setText(String.valueOf(g.getReceivedPrice()));
        txt_quantity.setText(String.valueOf(grn.getQuantity()));
        txt_qinbox.setText(String.valueOf(g.getQuantityInBox()));
        txt_original.setText(String.valueOf(g.getOriginal()));

        tempSKU = sku;
        temp = grn;
    }

    //Hủy phiếu
    public void clickBtnDeleteGRN() throws RemoteException {
        if(dict.showAlertWarning("Bạn có chắc chắn muốn hủy phiếu này không?")){
            this.registryClass.goodsReceivedNote().deleteGRN(lbl_grn_id.getText());
            tab_uvgrn_info.setDisable(true);
            clickBtnRefresh();
            tab_pane.getSelectionModel().select(tab_list);
            dict.showAlertInfo("Thành công","Đã xóa phiếu " + lbl_grn_id.getText());
        }
    }

    //Hàm khởi tạo comboBox
    private void cboInit() throws RemoteException {
        //Tạo giá trị cho cbo loại trái cây
        listFruitType = FXCollections.observableArrayList(registryClass.fruitType().getAllFruitTypes());
        this.cbo_ft_name.setItems(listFruitType);
        this.cbo_ft_name.setValue(listFruitType.getFirst());

        lbl_unit.setText(listFruitType.getFirst().getUnitOfCalculation());

        this.cbo_size.setItems(FXCollections.observableArrayList(new String[]{"S","M","L","XL","XXL","XXXL"}));
        this.cbo_size.setValue("M");

        this.cbo_status.setItems(FXCollections.observableArrayList(new String[]{"Chưa chín","Vừa chín"}));
        this.cbo_status.setValue("Chưa chín");

        //Xét giá trị nếu cbo được update
        this.cbo_ft_name.setCellFactory(lv -> new ListCell<FruitType>(){
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

    public void clickBtnCheckSupplier() throws RemoteException {
        supplierList = (ArrayList<Supplier>) this.registryClass.supplier().getSuppliersBySupplierName(txt_sup_name.getText().trim());
        if(supplierList.isEmpty()){
            lbl_sup_id1.setText("Null");
            lbl_sup_addr1.setText("Null");
            lbl_sup_phone1.setText("Null");
            dict.showAlertError("Không tồn tại nhà cung cấp này");
            return;
        }
        supplier = supplierList.get(0);
        lbl_sup_id1.setText(supplier.getSupId());
        txt_sup_name.setText(supplier.getSupName());
        lbl_sup_addr1.setText(supplier.getSupAddress());
        lbl_sup_phone1.setText(supplier.getSupPhone());
    }

    public void clickBtnNext() throws RemoteException {
        clickBtnCheckSupplier();
        if(lbl_sup_id1.getText().equals("Null")){
            dict.showAlertError("Hãy nhập thông tin nhà cung cấp");
            return;
        }
        if(txt_grn_date.getValue().isBefore(LocalDate.now())){
            dict.showAlertError("Ngày nhập phải lớn hơn hoặc bằng ngày hiện tại");
            return;
        }
        this.tab_pane_form.getSelectionModel().select(tab_detail_form);
        tab_basic_form.setDisable(true);
        tab_detail_form.setDisable(false);
    }

    public void clickBtnReturn(){
        this.tab_pane_form.getSelectionModel().select(tab_basic_form);
        tab_detail_form.setDisable(true);
        tab_basic_form.setDisable(false);
    }

    public void clickBtnClearTxtDetailForm(){
        cbo_ft_name.setValue(listFruitType.getFirst());
        cbo_status.setValue("Chưa chín");
        cbo_size.setValue("M");
        lbl_unit.setText(listFruitType.getFirst().getUnitOfCalculation());
        txt_qinbox.setText("1");
        txt_received_price.setText("1");
        txt_delivery_price.setText("1");
        txt_original.clear();
        txt_quantity.setText("1");
        temp = null;
        tempSKU = null;
    }

    private void checkNumber(TextField txt){
        try {
            double qinb = Double.parseDouble(txt.getText());
            if(qinb <= 0){
                txt.setText("1");
            }
        } catch (NumberFormatException e){
            txt.setText("1");
        }
    }

    //Kiểm tra dữ liệu từ form chi tiết
    public void checkQinBox(){
        checkNumber(txt_qinbox);
    }

    //Kiểm tra dữ liệu từ form chi tiết
    public void checkDeliveryPrice(){
        checkNumber(txt_delivery_price);
    }

    //Kiểm tra dữ liệu từ form chi tiết
    public void checkReceivedPrice(){
        checkNumber(txt_received_price);
    }

    public void checkQuantity(){
        try {
            short quantity = Short.parseShort(txt_quantity.getText());
            if(quantity <= 0){
                txt_quantity.setText("1");
            }
        } catch (NumberFormatException e){
            txt_quantity.setText("1");
        }
    }

    public void chooseFt(){
        lbl_unit.setText(cbo_ft_name.getSelectionModel().getSelectedItem().getUnitOfCalculation());
    }

    //Chuyển các màn hình
    public void navigateToSupplierManagement(){
        Support.navigateTo((byte) 5, this);
    }

    public void navigateToCustomerManagement(){
        Support.navigateTo((byte) 6, this);
    }

    public void naviagteToStaffManagement(){
        Support.navigateTo((byte) 7, this);
    }

    public void navigateToGNNavigate(){
        Support.navigateTo((byte) 8, this);
    }

    public void navigateToPositionManagement(){
        Support.navigateTo((byte) 9, this);
    }

    public void navigateToStatisticsManagement(){
        Support.navigateTo((byte) 10, this);
    }

    public void navigateToManagerAccountManagement(){
        Support.navigateTo((byte) 11, this);
    }

    //Đăng xuất
    public void clickBtnLogOut() throws RemoteException {
        if(!dict.showAlertWarning("Bạn có chắc chắn muốn thoát không?","Nhấn \"Có\" để xác nhận."))
            return;
        this.registryClass.user().logoutUser(Support.account);
        Support.navigateTo((byte) 0, this);
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

    public UnverifyGRNManageController() {

    }

    @FXML
    public void initialize() throws RemoteException {

        tab_detail_form.setDisable(true);
        txt_grn_date.setValue(LocalDate.now());

        lblFullName.setText(Support.account.getFullname());
        this.list_Unverify_GRN = FXCollections.observableArrayList(this.registryClass.goodsReceivedNote().getAllNotVerifyGRN());
        setInforGrnDetailTable();
        setInforGrnDetailTable2();
        setInforUnverify_GRNTable();

        cboInit();
    }

}

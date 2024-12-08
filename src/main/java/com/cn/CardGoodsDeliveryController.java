package com.cn;

import com.entity.GdnDetail;
import com.entity.Goods;
import com.util.FruitUtil;
import com.util.ObserverObject;
import com.util.RegistryClass;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

@Setter
@Getter
public class CardGoodsDeliveryController {

    @FXML
    private ComboBox<String> cbo_card_status;

    @FXML
    private Label lbl_card_unit;

    @FXML
    private Label lbl_card_unit2;

    @FXML
    private TextField txt_card_box;

    @FXML
    private TextField txt_card_deliveryPrice;

    @FXML
    private TextField txt_card_discountPrice;

    @FXML
    private TextField txt_card_original;

    @FXML
    private TextField txt_card_qinbox;

    @FXML
    private TextField txt_card_quantity;

    @FXML
    private TextField txt_card_sku;

    //Thuộc tính: Controller chứa nó
    private List<CardGoodsDeliveryController> listCard;
    private GridPane grid;
    private AnchorPane pane;

    private ObserverObject observerObject;

    //Thuộc tính bản thân chứa
    private Goods goods;
    private GdnDetail gdnDetail;
    private FruitUtil fruitUtil;

    //Thuộc tính trung gian
    private BigDecimal quantity;
    private short box;

    private String sku;
    private BigDecimal price;

    //Cờ kiểm tra thẻ có hợp lệ không;
    private boolean flag = false;

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

    //Constructor không tham số
    public CardGoodsDeliveryController() {
        goods = new Goods();
        gdnDetail = new GdnDetail();
        fruitUtil = new FruitUtil();
    }

    //Medthod
    //Xóa bản thân nó
    public void removeSelf() {
        this.listCard.remove(this);
        this.grid.getChildren().remove(this.pane);
        observerObject.calculateTotalPrice();
    }

    //Method: Set giá trị cho cbo
    private void setCbo() {
        cbo_card_status.getItems().addAll("Chưa chín", "Vừa chín", "Chín", "Quá chín");
        cbo_card_status.setValue("Quá chín");
    }

    //Method: Kiểm tra chi tiết sku
    //Kiểm tra: sku có hợp lệ không
    private boolean validateSkuString(String skuString) {
        skuString = skuString.toUpperCase().trim();
        if (skuString.trim().isEmpty())
            return false;
        if (!skuString.matches("[0-9A-Z,\\- ]*"))
            return false;

        //Chia skuList thành các sku theo dấu ,
        String[] skuList = skuString.split(",");

        //Mảng này chứa thành phần của sku
        String[] skuItem = null;

        //Duyêt các SKU
        for (String s : skuList) {
            //sku độ dài lớn hơn 22 là sai
            if (s.length() > 22) {
                return false;
            } else {
                //Chia thành các phần nhỏ của sku bằng dấu gạch nối
                skuItem = s.trim().split("-");

                //số thành phần lớn hơn 5 là sai
                if (skuItem.length > 5) {
                    return false;
                }
            }
        }

        return true;
    }


    //Method: Kiểm tra thông tin nhập có đúng không
    private boolean validateInfoGoods() {
        //SKU không được trống
        if (txt_card_sku.getText().trim().isEmpty()) {
            fruitUtil.showAlertError("SKU không được trống");
            return false;
        }

        //Nguồn gốc nếu có phải tồn tại
        if (!txt_card_original.getText().trim().isEmpty() && fruitUtil.encodeAddrress(txt_card_original.getText().trim()) == null) {
            fruitUtil.showAlertError("Nguồn gốc không hợp lệ");
            return false;
        }

        //Số lượng hoặc hộp không được cùng trống
        if (txt_card_quantity.getText().trim().isEmpty() && txt_card_box.getText().trim().isEmpty()) {
            fruitUtil.showAlertError("Số lượng hoặc số hộp không được để trống");
            return false;
        }

        //Số lượng nếu có phải là số
        if (!txt_card_quantity.getText().trim().isEmpty()) {
            try {
                quantity = BigDecimal.valueOf(Double.parseDouble(txt_card_quantity.getText().trim()));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        //Số hộp nếu có phải là số
        if (!txt_card_box.getText().trim().isEmpty()) {
            try {
                box = Short.parseShort(txt_card_box.getText().trim());
            } catch (NumberFormatException e) {
                return false;
            }
        }

        //Nếu nguồn gốc không được nhập phải kiểm tra tính hợp lệ với sku
        if (txt_card_original.getText().trim().isEmpty()) {
            sku = txt_card_sku.getText().trim();
            if (!validateSkuString(sku)) {
                fruitUtil.showAlertError("SKU không hợp lệ");
                return false;
            }
        } else{
            //Nguồn gốc được nhập thì được cộng với sku
            if (txt_card_sku.getText().trim().length()<=18)
                sku = txt_card_sku.getText().trim() + "-" + fruitUtil.encodeAddrress(txt_card_original.getText().trim());
            //Nguồn gốc được nhập thì được cộng với sku
            if (!validateSkuString(sku)) {
                fruitUtil.showAlertError("Đảm bảo SKU đúng và nếu có mã nguồn gốc thì không cần nhập nguồn gốc");
                return false;
            }
        }

        return true;
    }

    //Method: Lấy hàng và set giá trị
    private void setInfoGoods() throws RemoteException {
        //Test nên phải new
        goods = new Goods();

        //Set sku và trạng thái
        goods.setSku(sku);
        goods.setStatus(fruitUtil.decode(cbo_card_status.getValue()));
        //Tìm trong csdl
        goods = registryClass.goods().getGoodsToDelivery(goods);

        if(goods == null){
            fruitUtil.showAlertError("Không tìm được hàng tương ứng. Vui lòng kiểm tra lại thông tin.");
            return;
        }

        //Set giá trị
        txt_card_sku.setText(goods.getSku());
        txt_card_original.setText(goods.getOriginal());
        txt_card_deliveryPrice.setText((goods.getDeliveryPrice().multiply(BigDecimal.valueOf(box))).toString());

        //Nếu số lượng được nhập thì ưu tiên chuyển số lượng thành hộp
        if(!txt_card_quantity.getText().trim().isEmpty()) {
            quantity = BigDecimal.valueOf(Double.parseDouble(txt_card_quantity.getText().trim()));
            box = (short) Math.ceil(quantity.divide(goods.getQuantityInBox()).doubleValue());
        }

        //Số hộp hợp lệ
        if(box > goods.getAvailableQuantity()){
            fruitUtil.showAlertInfo("Chỉ lấy được số lượng tối đa còn trong kho");
            box = goods.getAvailableQuantity();
        }

        //Chuyển hộp thành số lượng
        quantity = BigDecimal.valueOf(box).multiply(goods.getQuantityInBox());

        //Giảm giá tương ứng
        switch (cbo_card_status.getValue()) {
            case "Chín":
                gdnDetail.setDiscount(BigDecimal.valueOf(0.02));
                price = (goods.getDeliveryPrice().multiply(BigDecimal.valueOf(box))).multiply(BigDecimal.ONE.subtract(gdnDetail.getDiscount()));
                txt_card_discountPrice.setText(price.toString());
                break;
            case "Quá chín":
                gdnDetail.setDiscount(BigDecimal.valueOf(0.05));
                price = (goods.getDeliveryPrice().multiply(BigDecimal.valueOf(box))).multiply(BigDecimal.ONE.subtract(gdnDetail.getDiscount()));
                txt_card_discountPrice.setText(price.toString());
                break;
            default:
                gdnDetail.setDiscount(BigDecimal.valueOf(0));
                price = (goods.getDeliveryPrice().multiply(BigDecimal.valueOf(box)));
                txt_card_discountPrice.setText(price.toString());
                break;
        }

        txt_card_qinbox.setText(goods.getQuantityInBox().toString());
        lbl_card_unit.setText(goods.getGoodsFruitType().getUnitOfCalculation());
        lbl_card_unit2.setText(lbl_card_unit.getText().trim());

        txt_card_box.setText(String.valueOf(box));
        txt_card_quantity.setText(String.valueOf(quantity));

        //gdnDetail đã có 3 giá trị
        gdnDetail.setQuantity(box);
        gdnDetail.setGoodsGDNDetail(goods);

    }

    //Method: Kiểm tra hàng có đúng không
    public void checkGoods() throws RemoteException {
        if (!validateInfoGoods()) {
            flag = false;
            return;
        }
        setInfoGoods();
        if(observerObject.checkGoodsInCard()){
            fruitUtil.showAlertError("Không thể có các thẻ trùng SKU");
            flag = false;
            return;
        }
        flag = true;
        observerObject.calculateTotalPrice();
    }

    public void lockEntryData(){
        txt_card_sku.setEditable(false);
        txt_card_original.setEditable(false);
        txt_card_quantity.setEditable(false);
        txt_card_box.setEditable(false);
        cbo_card_status.setEditable(false);
    }

    @FXML
    public void initialize() {
        setCbo();
    }

}

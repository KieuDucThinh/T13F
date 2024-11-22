package com.util;

import com.cn.CardGoodsDeliveryController;
import com.entity.GoodsDeliveryNote;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Builder
public class ObserverObject {
    private List<CardGoodsDeliveryController> list;

    private Label lbl_amount_payable;

    private Label lbl_total_price;

    private TextField txt_bonusDiscount;

    private GoodsDeliveryNote gdn;

    //Method: Kiểm tra giá trị giảm giá nhập vào
    private boolean validateGoodsDiscount(){
        try{
            Double.parseDouble(txt_bonusDiscount.getText());
        } catch (NumberFormatException e){
            this.txt_bonusDiscount.setText("0.0");
            this.lbl_amount_payable.setText(this.lbl_total_price.getText());
            return false;
        }
        return true;
    }

    //Method: set giá phải trả khi nhập giảm giá
    public void updateAmountPayable(){
        //Nếu giảm giá không hợp lệ thì đưa về mặc định
        if(!validateGoodsDiscount())
            return;

        //Chạy đoạn new do đang test
        this.gdn = new GoodsDeliveryNote();

        //Số tiền phải trả = tổng tiền * (1 - giảm giá%)
        this.gdn.setTotalDeliveryPrice(BigDecimal.valueOf(Double.parseDouble(lbl_total_price.getText())).multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(Double.parseDouble(txt_bonusDiscount.getText())).divide(BigDecimal.valueOf(100)))).setScale(2, BigDecimal.ROUND_HALF_UP));
        lbl_amount_payable.setText(this.gdn.getTotalDeliveryPrice()+"");
    }

    public void calculateTotalPrice(){
        BigDecimal p = BigDecimal.valueOf(0);
        for(CardGoodsDeliveryController item : list){
            p = p.add(item.getPrice());
        }
        p.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.lbl_total_price.setText(p+"");
        updateAmountPayable();
    }

    public boolean checkGoodsInCard(){
        Set<String> listString = new HashSet<String>();
        for(CardGoodsDeliveryController item : list){
            listString.add(item.getGoods().getSku());
        }
        return (listString.size() < list.size());
    }
}

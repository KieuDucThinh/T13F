package com.generator;

import com.entity.Goods;
import com.entity.GoodsReceivedNote;
import com.entity.GrnDetail;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SKUGenerator implements IdentifierGenerator {
    private static HashMap<String, String> hashMap;
    static{
        hashMap = new HashMap<>();
        hashMap.put("Thành phố Hà Nội", "001");
        hashMap.put("Hà Giang", "002");
        hashMap.put("Cao Bằng", "004");
        hashMap.put("Bắc Kạn", "006");
        hashMap.put("Tuyên Quang", "008");
        hashMap.put("Lào Cai", "110");
        hashMap.put("Điện Biên", "111");
        hashMap.put("Lai Châu", "112");
        hashMap.put("Sơn La", "114");
        hashMap.put("Yên Bái", "115");
        hashMap.put("Hòa Bình", "117");
        hashMap.put("Thái Nguyên", "119");
        hashMap.put("Lạng Sơn", "220");
        hashMap.put("Quảng Ninh", "222");
        hashMap.put("Bắc Giang", "224");
        hashMap.put("Phú Thọ", "225");
        hashMap.put("Vĩnh Phúc", "226");
        hashMap.put("Bắc Ninh", "227");
        hashMap.put("Hải Dương", "330");
        hashMap.put("Thành phố Hải Phòng", "331");
        hashMap.put("Hưng Yên", "333");
        hashMap.put("Thái Bình", "334");
        hashMap.put("Hà Nam", "335");
        hashMap.put("Nam Định", "336");
        hashMap.put("Ninh Bình", "337");
        hashMap.put("Thanh Hóa", "338");
        hashMap.put("Nghệ An", "440");
        hashMap.put("Hà Tĩnh", "442");
        hashMap.put("Quảng Bình", "444");
        hashMap.put("Quảng Trị", "445");
        hashMap.put("Thừa Thiên-Huế", "446");
        hashMap.put("Thành phố Đà Nẵng", "448");
        hashMap.put("Quảng Nam", "449");
        hashMap.put("Quảng Ngãi", "551");
        hashMap.put("Bình Định", "552");
        hashMap.put("Phú Yên", "554");
        hashMap.put("Khánh Hòa", "556");
        hashMap.put("Ninh Thuận", "558");
        hashMap.put("Bình Thuận", "660");
        hashMap.put("Kon Tum", "662");
        hashMap.put("Gia Lai", "664");
        hashMap.put("Đắk Lắk", "666");
        hashMap.put("Đắc Nông", "667");
        hashMap.put("Lâm Đồng", "668");
        hashMap.put("Bình Phước", "770");
        hashMap.put("Tây Ninh", "772");
        hashMap.put("Bình Dương", "777");
        hashMap.put("Đồng Nai", "775");
        hashMap.put("Bà Rịa-Vũng Tàu", "777");
        hashMap.put("Thành Phố Hồ Chí Minh", "779");
        hashMap.put("Long An", "880");
        hashMap.put("Tiền Giang", "882");
        hashMap.put("Bến Tre", "883");
        hashMap.put("Trà Vinh", "884");
        hashMap.put("Vĩnh Long", "886");
        hashMap.put("Đồng Tháp", "887");
        hashMap.put("An Giang", "889");
        hashMap.put("Kiên Giang", "991");
        hashMap.put("Thành phố Cần Thơ", "992");
        hashMap.put("Hậu Giang", "993");
        hashMap.put("Sóc Trăng", "994");
        hashMap.put("Bạc Liêu", "995");
        hashMap.put("Cà Mau", "996");
    }
    //Mã trái cây: mã loại - kích thước - mã xuất xứ - ngày được lập phiếu - tính trạng
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        if(o instanceof Goods) {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
//            String datePart = sdf.format(new Date());
            Iterator<GrnDetail> iterator = ((Goods) o).getGrnDetailSet().iterator();
            GoodsReceivedNote grn = iterator.next().getGrnDetail();
            String datePart;

            //Mã trái cây nếu như thời gian duyệt khác thời gian lập
            if(grn.getReceivedUser()!=null){
                datePart = sdf.format(new Date(grn.getVerifyDate().getTime()));
            } else {
                datePart = sdf.format(new Date(grn.getGrnDate().getTime()));
//                System.out.println(grn.getReceivedUser()+"|||||||||");
            }
            return ((Goods) o).getGoodsFruitType().getFtId() + "-" + ((Goods) o).getSize() + "-" + hashMap.get(((Goods) o).getOriginal()) + "-" + datePart + "-" + ((Goods) o).getStatus();
        } else{
            return null;
        }
    }

    public String getSKU(Goods tc){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
//            String datePart = sdf.format(new Date());
        //            String datePart = sdf.format(new Date());
        Iterator<GrnDetail> iterator = tc.getGrnDetailSet().iterator();
        GoodsReceivedNote grn = iterator.next().getGrnDetail();
        String datePart;

        //Mã trái cây nếu như thời gian duyệt khác thời gian lập
        if(grn.getReceivedUser()!=null){
            datePart = sdf.format(new Date(grn.getVerifyDate().getTime()));
//            System.out.println("Flag1: "+datePart);
        } else {
            datePart = sdf.format(new Date(grn.getGrnDate().getTime()));
//            System.out.println("Flag2: "+datePart);
//            System.out.println(grn.getReceivedUser()+"|||||||||");
        }

        return tc.getGoodsFruitType().getFtId() + "-" + tc.getSize() + "-" + hashMap.get(tc.getOriginal()) + "-" + datePart + "-" + tc.getStatus();
    }
}

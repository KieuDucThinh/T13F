package com.generator;

import com.entity.GoodsReceivedNote;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GrnIDGenerator implements IdentifierGenerator {
    //Tạo mã đơn nhập với 2 ký tự đầu là PN, 6 ký tự tiếp theo là ngày tháng năm lập đơn, 6 ký tự cuối ngẫu nhiên
    //Ví dụ: PN031124000000
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "PN";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
//        String datePart = sdf.format(new Date());
        String datePart = sdf.format(new Date(((GoodsReceivedNote) o).getGrnDate().getTime()));
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return prefix + datePart + randomPart;
    }
}

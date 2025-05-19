package com.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GdnIDGenerator implements IdentifierGenerator {
    //Tạo mã đơn nhập với 2 ký tự đầu là PX, 6 ký tự tiếp theo là ngày tháng năm lập đơn, 6 ký tự cuối ngẫu nhiên
    //Ví dụ: PX031124000000
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "PX";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        String datePart = sdf.format(new Date());
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return prefix + datePart + randomPart;
    }
}

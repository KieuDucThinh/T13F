package com.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;

public class SupIDGenerator implements IdentifierGenerator {

    //Tạo mã nhà cung cấp 3 ký tự đầu SU-, 10 ký tự sau ngẫu nhiên
    //Ví dụ: SU-1111111111
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "SU-";
        String randomPart = String.format("%010d", new Random().nextInt(999999999));
        return prefix + randomPart;
    }
}

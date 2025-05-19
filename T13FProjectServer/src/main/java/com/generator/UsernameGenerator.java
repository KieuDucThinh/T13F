package com.generator;

import com.entity.User;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UsernameGenerator implements IdentifierGenerator {

    //Tạo tên đăng nhập
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return ((User) o).getUsername();
    }
}

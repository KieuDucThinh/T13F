package com.generator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator implements IdentifierGenerator {
    public static String generateImportOrderId() {
        String prefix = "PN";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        String datePart = sdf.format(new Date());
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return prefix + datePart + randomPart;
    }

    public static String generateSupplierId() {
        String prefix = "SU-";
        String randomPart = String.format("%010d", new Random().nextInt(999999999));
        return prefix + randomPart;
    }

    public static String generateFruitId(String fruitType, String fruitSize, String fruitOrigin, Date entryDate, String fruitStatus) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        String datePart = sdf.format(entryDate);
        return fruitType + "-" + fruitSize + "-" + fruitOrigin + "-" + datePart + "-" + fruitStatus;
    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return null;
    }
}

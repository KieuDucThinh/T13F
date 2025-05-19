package com.connection;

import jakarta.persistence.EntityManager;

/*
Interface dùng để quản lý kết nối
getConnection: Tạo kết nối với DB
releaseConnection: Ngắt kết nối với DB với EntityManager xác định.
    em: đối tượng ngắt kết nối
 */
public interface ConnectionPool {
    public EntityManager getConnection();
//    public void releaseConnection(EntityManager em);
}

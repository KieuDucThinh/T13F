package com.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Stack;

/*
    Thực hiện interface ConnectionPool dùng để quản lý kết nối
    Khai báo EntityManagerFactory dùng để tạo EntityManager
    Stack: Dùng để quản lý số lượng kết nối
 */
public class ConnectionPoolImpl implements ConnectionPool {
    private static final EntityManagerFactory emf;
    //private static final Stack<EntityManager> pool;

    static{
        //Tạo EntityManagerFactory sử dụng persistence
        emf = Persistence.createEntityManagerFactory("default");

        //Cấp phát bộ nhớ cho pool
        //pool = new Stack<>();
    }
    public ConnectionPoolImpl() {

    }

    //Tạo kết nối
    @Override
    public EntityManager getConnection() {
        //Kiêm tra pool trống thì tạo EntityManager không thì lấy ra dùng
//        if(this.pool.isEmpty()) {
            return this.emf.createEntityManager();
//        } else{
//            return this.pool.pop();
//        }
    }

    /*
    //Giải phóng kết nối bằng cách trả EntityManager về Stack
    @Override
    public void releaseConnection(EntityManager em) {
        pool.push(em);
    }
    */
}

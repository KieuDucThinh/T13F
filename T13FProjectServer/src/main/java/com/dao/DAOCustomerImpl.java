package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.Customer;
import com.entity.GoodsDeliveryNote;
import jakarta.persistence.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DAOCustomerImpl extends UnicastRemoteObject implements DAOCustomer{
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;

    //Constructor
    public DAOCustomerImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
//        this.entityManager = connectionPool.getConnection();
    }

    @Override
    public Customer getCustomer(String cId) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, cId);
            entityManager.flush();
            transaction.commit();
            return customer;
        } catch (Exception e){
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }finally {
            this.entityManager.close();
        }
        return null;
    }

    //Tìm khách hàng dựa trên số điện thoại
    @Override
    public Customer findCustomerByPhone(String phone) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.cPhone = :phone", Customer.class);
            query.setParameter("phone", phone);
            transaction.commit();
            if(!query.getResultList().isEmpty())
                return query.getSingleResult();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Kiểm tra khách hàng có hợp lệ
    @Override
    public boolean validateCuIn(Customer c) throws RemoteException {
        Customer customer = findCustomerByPhone(c.getCPhone());

        //Khối lệnh này ít khi được chạy nếu front end đã xử lý
        //Khách có số điện thoại đúng
        if(customer!=null){

            //Nếu cả email của khách hàng và địa chỉ khách hàng đúng --> hợp lệ
            return ((customer.getCEmail() == c.getCEmail()) && customer.getCAddress().equals(c.getCAddress()));
        }

        //Khách chưa có số điện thoại
        //Email trống
        if(c.getCEmail()==null)
            return true;

        //Email phải chưa có trong csdl
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.cEmail = :email", Customer.class);
            query.setParameter("email", c.getCEmail());
            transaction.commit();
            if(!query.getResultList().isEmpty()){
                return false;
            }
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }

        return true;
    }

    //Chưa test
    //Thêm khách hàng mới
    @Override
    public boolean addCustomer(Customer customer) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try{
            transaction.begin();

            //Tìm khách hàng theo email
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c from Customer c WHERE c.cEmail = :email", Customer.class);
            query.setParameter("email", customer.getCEmail());

            //Tìm thấy --> Khách đã tồn tại
            if(!query.getResultList().isEmpty()) {
                transaction.commit();
                return false;
            }

            //Không thấy --> Lưu --> Nếu trùng sđt --> exception --> false
            entityManager.persist(customer);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    //Cập nhật thông tin khách hàng
    //Hiện tại không dùng
    @Override
    public boolean updateCustomer(Customer customer) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try{
            transaction.begin();
            Customer c = this.entityManager.find(Customer.class, customer.getCId());
            if (customer.equals(c)){
                transaction.commit();
                return true;
            }
            c.setCPhone(customer.getCPhone());
            c.setCEmail(customer.getCEmail());
            c.setCAddress(customer.getCAddress());
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    //Lấy ra toàn bộ thông tin khách hàng
    @Override
    public List<Customer> getAllCustomer() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Customer> query = entityManager.createQuery("select s from Customer s order by s.cAdddate", Customer.class);
            transaction.commit();
            return query.getResultList();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Tìm kiếm khách hàng theo từ được nhập
    @Override
    public List<Customer> getCustomerByKeyword(String keyword) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Customer> query = entityManager.createQuery("select s from Customer s where s.cId like ?1 or s.cName like ?2 or s.cPhone = ?3 order by s.cAdddate", Customer.class);
            query.setParameter(1, "%"+keyword.trim()+"%");
            query.setParameter(2, "%"+keyword.trim()+"%");
            query.setParameter(3, "%"+keyword.trim()+"%");
            transaction.commit();
            return query.getResultList();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }
}

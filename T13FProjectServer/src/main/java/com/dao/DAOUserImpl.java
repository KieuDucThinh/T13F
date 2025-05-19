package com.dao;

import com.connection.ConnectionPool;
import com.connection.ConnectionPoolImpl;
import com.entity.Supplier;
import com.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class DAOUserImpl extends UnicastRemoteObject implements DAOUser {
    //Thuộc tính
    private ConnectionPool connectionPool;
    private EntityManager entityManager;
    private static Random random;
    static {
        random = new Random();
    }

    public DAOUserImpl() throws RemoteException {
        this.connectionPool = new ConnectionPoolImpl();
    }

    //Tìm nhân viên theo số điện thoại
    @Override
    public User findUserByPhone(String phone) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.phone = :phone", User.class);
            query.setParameter("phone", phone);
            transaction.commit();
            return query.getSingleResult();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Kiểm tra nhân viên có tồn tại
    //Chưa code
    @Override
    public boolean checkExistUser(User user) throws RemoteException {

        return false;
    }

    //Đăng xuất và chuyển trạng thái thành OFF nếu tài khoản hợp lệ
    @Override
    public boolean logoutUser(User user) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            User userClient = this.entityManager.find(User.class, user.getUsername());
            if (userClient != null) {
                if(user.getStatus().equals("FI")) {
                    transaction.commit();
                    return true;
                }
                userClient.setStatus("OFF");
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    //Đăng nhập và chuyển trạng thái thành ON với đúng mật khẩu trạng thái OFF
    //Với FI thì cần đổi mật khẩu mới ON
    @Override
    public boolean loginUser(User user) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            User userClient = this.entityManager.find(User.class, user.getUsername());
            if (userClient.getPassword().equals(user.getPassword())) {
                if (userClient.getStatus().equals("OFF")) {
                    userClient.setStatus("ON");
                    transaction.commit();
                    return true;
                } else if (userClient.getStatus().equals("FI")) {
                    transaction.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    //Nếu thấy tài khoản thì trả về
    @Override
    public User checkLogin(User user) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            User userClient = this.entityManager.find(User.class, user.getUsername());

            //Không có tài khoản này
            if (userClient == null) {
                return null;
            }

            //Trả về tài khoản không có mật khẩu
            this.entityManager.detach(userClient);
            System.out.println(this.entityManager.contains(userClient));
            userClient.setPassword("");

            return userClient;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            this.entityManager.close();
        }
        return null;
    }

    //Đổi mật khẩu và nếu là FI thì sẽ thành ON
    @Override
    public boolean changePassword(String oldPwd, User user) throws RemoteException {
        this.entityManager = this.connectionPool.getConnection();
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();

            //Tìm và đổi mật khẩu
            User userClient = this.entityManager.find(User.class, user.getUsername());

            //Mật khẩu cũ không đúng
            if(!userClient.getPassword().equals(oldPwd)) {
                return false;
            }

            //Mật khẩu cũ đúng
            userClient.setPassword(user.getPassword());

            //Nếu trạng thái tài khoản là FI
            if(userClient.getStatus().equals("FI"))
                userClient.setStatus("ON");

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<User> query = entityManager.createQuery("select s from User s", User.class);
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

    @Override
    public List<User> getAllUsersByStatus(String status) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<User> query = entityManager.createQuery("select s from User s where s.status = ?1 and username != :username", User.class);
            query.setParameter(1, status);
            query.setParameter("username", "administrator");
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

    @Override
    public List<User> getAllUsersByKeyword(String keyword) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<User> query = entityManager.createQuery("select s from User s where (s.fullname like ?1 or s.phone like ?2) and username != :username", User.class);
            query.setParameter(1, "%"+keyword+"%");
            query.setParameter(2, "%"+keyword+"%");
            query.setParameter("username", "administrator");
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

    //Thêm nhân viên
    @Override
    public boolean addUser(User user) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            this.entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Sửa thông tin nhân viên
    @Override
    public boolean updateUser(User user) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            User u = this.entityManager.find(User.class, user.getUsername());
            //Không thấy sẽ không sửa
            if (u == null) {
                return false;
            }

            //Nếu thấy thì sửa
            u.setFullname(user.getFullname());
            u.setBod(user.getBod());
            u.setGender(user.isGender());
            u.setAddress(user.getAddress());
            u.setPhone(user.getPhone());
            u.setCitizenId(user.getCitizenId());
            u.setHometown(user.getHometown());
            u.setSalary(user.getSalary());

            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Khóa tài khoản nhân viên
    @Override
    public boolean lockUser(User user) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            User u = this.entityManager.find(User.class, user.getUsername());
            //Không thấy sẽ không khóa
            if (u == null) {
                return false;
            }

            //Nếu thấy thì khóa và set ngày kết thúc làm việc là hôm nay
            if(!u.getStatus().equalsIgnoreCase("QUI")){
                u.setStatus("QUI");
                u.setEndDate(Date.valueOf(LocalDate.now()));

                transaction.commit();
                return true;
            }
            transaction.commit();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Mở khóa tài khoản nhân viên
    @Override
    public User unlockUser(User user) throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            User u = this.entityManager.find(User.class, user.getUsername());
            //Không thấy sẽ không mở khóa
            if (u == null) {
                return null;
            }

            //Nếu thấy thì mở khóa và set ngày kết thúc làm việc là null
            if(u.getStatus().equalsIgnoreCase("QUI")){
                //Mở khóa
                u.setStatus("FI");
                u.setEndDate(Date.valueOf(LocalDate.parse("03/09/2112", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

                //Set password mới và trả về
                u.setPassword(String.format("%010d", (System.currentTimeMillis() + random.nextInt(9999999)) % 1000000000));

                transaction.commit();
                return u;
            }
            transaction.commit();
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

    //Phục hồi người dùng khi server sập
    @Override
    public void recoverUser() throws RemoteException {
        this.entityManager = connectionPool.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Query query = entityManager.createQuery("UPDATE User u set u.status = 'OFF' where u.status = 'ON'");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if(entityManager.isOpen()){
                entityManager.close();
            }
        }
    }
}

module com.example.t13fproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;


    opens com.cn to javafx.fxml;
    exports com.cn;
}
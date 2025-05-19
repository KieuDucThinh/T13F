module com.example.t13fproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    requires static lombok;

    opens com.entity to javafx.base;
    opens com.dao to javafx.base;
    opens com.util to javafx.fxml, javafx.base;
    opens com.cn to javafx.fxml, javafx.base;
    exports com.cn;
}
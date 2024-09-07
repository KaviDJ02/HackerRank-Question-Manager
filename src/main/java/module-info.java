module com.example.hackerankq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hackerankq to javafx.fxml;
    exports com.example.hackerankq;
}
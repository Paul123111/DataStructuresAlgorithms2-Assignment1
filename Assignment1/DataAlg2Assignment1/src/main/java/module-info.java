module com.example.dataalg2assignment1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dataalg2assignment1 to javafx.fxml;
    exports com.example.dataalg2assignment1;
    exports com.example.dataalg2assignment1.utils;
    opens com.example.dataalg2assignment1.utils to javafx.fxml;
    exports com.example.dataalg2assignment1.controllers;
    opens com.example.dataalg2assignment1.controllers to javafx.fxml;
}
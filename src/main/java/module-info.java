module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires org.json;



    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}
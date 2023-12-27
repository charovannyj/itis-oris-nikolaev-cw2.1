module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires org.json;



    opens ru.kpfu.itis.oris.nikolaev to javafx.fxml;
    exports ru.kpfu.itis.oris.nikolaev;
}
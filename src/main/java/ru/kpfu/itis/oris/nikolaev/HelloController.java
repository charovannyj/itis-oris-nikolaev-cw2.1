package ru.kpfu.itis.oris.nikolaev;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HelloController {
    @FXML
    private Text start_time;
    @FXML
    private Text end_time;
    @FXML
    private Button button;
    @FXML
    private Text text;
    @FXML
    private Text dep_city;

    @FXML
    private Text arr_city;
    @FXML
    private TextField textFieldForCode;
    @FXML
    private Line line;
    @FXML
    private ImageView plane;
    @FXML
    private Text status;
    private HttpClientImpl httpClient = new HttpClientImpl();

    @FXML
    private void execute() {
        String code = textFieldForCode.getText();
        try {
            String string1ForSb = "https://airlabs.co/api/v9/flight?flight_iata=";
            String string2ForSb = "&api_key=";
            StringBuilder sb = new StringBuilder();
            sb.append(string1ForSb);
            sb.append(code);
            sb.append(string2ForSb);
            sb.append(HelloApplication.api_key);
            String request = sb.toString();
            String answer = httpClient.get(request, null);
            JSONObject json = new JSONObject(answer);
            JSONObject response = json.getJSONObject("response");
            String start = response.getString("dep_time_utc");
            String end = response.getString("arr_time_utc");
            String city1 = response.getString("dep_city");
            String city2 = response.getString("arr_city");

            Date currentDate = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            String date = dateFormat.format(currentDate);

            System.out.println("Текущая дата: " + date);
            System.out.println("Значение переменной start: " + start);
            System.out.println("Значение переменной end: " + end);
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            long diffInMilliesStart = currentDate.getTime() - startDate.getTime();
            long diffInMinutesStart = diffInMilliesStart / (60 * 1000);

            long diffInMilliesEnd = Math.max(endDate.getTime() - currentDate.getTime(), 0);
            long diffInMinutesEnd = diffInMilliesEnd / (60 * 1000);

            System.out.println("Разница между start и текущей датой: " + diffInMinutesStart + " минут");
            System.out.println("Разница между end и текущей датой: " + diffInMinutesEnd + " минут");
            double k = (double) diffInMilliesStart / (double) (diffInMilliesStart + diffInMilliesEnd);
            System.out.println(k);
            int newX = (int) (k * line.getStartX());
            plane.setX(newX);
            dep_city.setText(city1);
            arr_city.setText(city2);
            start_time.setText(start);
            end_time.setText(end);
            String statusText = null;
            if (k < 0.01) {
                statusText = "Самолёт взлетает";
            } else if (k < 0.1) {
                statusText = "Самолёт недавно вылетел";
            } else if (k < 0.9) {
                statusText = "Самолёт в полёте";
            } else if (k < 0.99) {
                statusText = "Самолёт скоро прилетит";
            } else {
                statusText = "Самолёт садится";
            }
            status.setText(statusText);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Введен некорректный номер рейса");
            alert.showAndWait();
        }
        // самолетик по иксу летит от 30 до 330 координаты. итого всего 300 пунктов имеем.
    }
}

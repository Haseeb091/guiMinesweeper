module com.example.minesweeperguiv1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minesweeperguiv1 to javafx.fxml;
    exports com.example.minesweeperguiv1;
}
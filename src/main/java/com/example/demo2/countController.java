package com.example.demo2;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;


public class countController {

    @FXML
    TextField countProductsIn;

    public void pridatCount() throws Exception{

        PredajcaController.count = Integer.valueOf(countProductsIn.getText());
        PredajcaController.windowCount.fireEvent(new WindowEvent(PredajcaController.windowCount, WindowEvent.WINDOW_CLOSE_REQUEST));
        System.out.println("govno");
    }

}

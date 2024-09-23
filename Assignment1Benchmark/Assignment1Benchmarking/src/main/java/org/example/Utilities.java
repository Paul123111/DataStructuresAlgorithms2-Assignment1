package org.example;

//import javafx.scene.control.TextField;

public class Utilities {
    public static int doubleToInt(double doubleToConvert) {
        return (int) Math.round(doubleToConvert);
    }

//    public static void numericText(TextField textField, int num) {
//        textField.setText(Integer.toString(num));
//
//        //https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
//        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                textField.setText(newValue.replaceAll("[^\\d]", ""));
//            }
//        });
//    }
    public static int parseInt(String i) {
        try {
            return Integer.parseInt(i);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}

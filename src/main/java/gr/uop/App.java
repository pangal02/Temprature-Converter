package gr.uop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private class ConvertToFarenheit implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event){
            if(celciusTextField.getText().length() == 0){
                errorLabel.setText("Δώστε μια θερμοκρασία σε βαθμούς Κελσίου.");
                return;
            }

            try{
                int c = Integer.parseInt(celciusTextField.getText().trim());
                errorLabel.setText("");
                int f = (int)(9.0 / 5.0 * c + 32);
                farenheitTextField.setText(f +"");
            }
            catch(NumberFormatException nfe){
                errorLabel.setText("Δώστε ακέραιο αριθμό.");
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    private class ConvertToCelcius implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if(farenheitTextField.getText().length() == 0){
                errorLabel.setText("Δώστε μια θερμοκρασία σε βαθμούς Φαρενάιτ.");
                return;
            }
            try {
                int f = Integer.parseInt(farenheitTextField.getText());
                errorLabel.setText("");
                int c = (int)(5.0 / 9.0 * (f - 32));
                celciusTextField.setText(c + "");
            }
            catch (NumberFormatException e) {
                errorLabel.setText("Δώστε ακέραιο αριθμό.");
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    private TextField celciusTextField;
    private TextField farenheitTextField;
    private Label errorLabel;

    @Override
    public void start(Stage stage) {
        VBox mainPane = new VBox(10);
        mainPane.setPadding(new Insets(10));

        HBox centerPane = new HBox(10);

        Label celciusLabel = new Label("Κελσίου");
        celciusLabel.setAlignment(Pos.BASELINE_RIGHT);
        celciusTextField = new TextField();
        celciusTextField.setPrefColumnCount(4);
        celciusTextField.setAlignment(Pos.CENTER);

        VBox buttonsPane = new VBox();
        Button toFarenheitButton = new Button("->");
        Button toCelciusButton = new Button("<-");
        buttonsPane.getChildren().addAll(toFarenheitButton, toCelciusButton);

        farenheitTextField = new TextField();
        farenheitTextField.setPrefColumnCount(4);
        farenheitTextField.setAlignment(Pos.CENTER);

        Label farenheitLabel = new Label("Φαρενάιτ");
        farenheitLabel.setAlignment(Pos.BASELINE_LEFT);

        centerPane.getChildren().addAll(celciusLabel, celciusTextField, buttonsPane, farenheitTextField, farenheitLabel);
        centerPane.setAlignment(Pos.CENTER);

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Button clearButton = new Button("Καθαρισμός");
        clearButton.setOnAction((e) -> {
            celciusTextField.clear();
            farenheitTextField.clear();
            errorLabel.setText("");
        });

        mainPane.getChildren().addAll(centerPane, errorLabel, clearButton);
        mainPane.setAlignment(Pos.TOP_CENTER);

        ConvertToFarenheit ctf = new ConvertToFarenheit();
        toFarenheitButton.setOnAction(ctf);
        celciusTextField.setOnAction(ctf);

        ConvertToCelcius ctc = new ConvertToCelcius();
        toCelciusButton.setOnAction(ctc);
        farenheitTextField.setOnAction(ctc);

        Platform.runLater(() -> {
            // Make the two labels have equal widths.
            if (celciusLabel.getWidth() < farenheitLabel.getWidth()) {
                celciusLabel.setMinWidth(farenheitLabel.getWidth());
            }
            else {
                farenheitLabel.setMinWidth(celciusLabel.getWidth());
            }
    
            // Resize the stage to fit everything.
            stage.sizeToScene();
            
            stage.setMinHeight(stage.getHeight());
            stage.setMinWidth(stage.getWidth());
        });


        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Μετατροπές θερμοκρασίας");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parnet;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class assignment2 extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        SplitPane splitPane = new SplitPane();
        SplitPane buttonPane = new SplitPane();
        ListView leftList = new ListView();
        ListView rightList = new ListView();
        Button downloadButton = new Button();
        Button uploadButton = new Button();

        VBox left = new VBox(leftList);
        VBox right = new VBox(rightList);
        VBox leftButton = new VBox(downloadButton);
        VBox rightButton = new VBox(uploadButton);

        splitPane.getItems().addAll(left, right);
        buttonPane.getItems().addAll(leftButton, rightButton);

        VBox mainFrm = new VBox(buttonPane);
        mainFrm.getChildren().add(splitPane);

        Scene scene = new Scene(mainFrm);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

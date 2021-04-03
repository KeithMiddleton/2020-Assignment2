import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.scene.Parent;
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

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class assignment2 extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        SplitPane splitPane = new SplitPane();
        ListView leftList = new ListView();
        ListView rightList = new ListView();
        Button downloadButton = new Button("Download");
        Button uploadButton = new Button("Upload");

        VBox left = new VBox(leftList);
        VBox right = new VBox(rightList);

        splitPane.getItems().addAll(left, right);

        HBox buttons = new HBox(downloadButton);
        buttons.getChildren().add(uploadButton);

        VBox mainFrm = new VBox(buttons);
        mainFrm.getChildren().add(splitPane);

        uploadButton.setOnAction(e -> 
        {
            Socket client = new Socket("127.0.0.1", 6969);
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            dout.writeUTF("UPLOAD " + leftList.getSelectionModel().getSelectedItem() + "\n" + new String(Files.readAllBytes(Paths.get(leftList.getSelectionModel().getSelectedItem()), StandardCharsets.UTF_8)));
            dout.flush();
            dout.close();
            client.close();
        });

        downloadButton.setOnAction(e -> 
        {
            Socket client = new Socket("127.0.0.1", 6969);
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            dout.writeUTF("DOWNLOAD " + rightList.getSelectionModel().getSelectedItem());
            dout.flush();
            dout.close();
            DataInputStream din = new DataInputStream(client.getInputStream());
            String data = (String)din.readUTF();
            writer.write(data);
            din.close();
            client.close();
        });

        Scene scene = new Scene(mainFrm);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

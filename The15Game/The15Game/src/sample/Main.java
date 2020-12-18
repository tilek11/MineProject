package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Button[] btns = new Button[16];
    private ArrayList<Integer> rndarr = GenerateNumbers();
    private int freex = 0;
    private int freey = 0;
    private int freeid = 0;
    private static int btnsize = 50;
    private int CheckResult()
    {
        int res = 1;
        for (int i=0;i<14;i++)
        {
            if (rndarr.get(i) !=i) res = 0;
        }
        return res;
    }

    private ArrayList<Integer> GenerateNumbers()
    {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < 16; i++)
        {
            Random random = new Random();
            int r = random.nextInt(16);
            if (!res.contains(r))
            {
                res.add(r);
            }
            else
            {
                i--;
            }
        }
        return res;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initBtnsArray();
        Group group = new Group();
        group.getChildren().add(getGrid());
        Button btnstart = new Button("Try again");
        btnstart.setLayoutX(60);
        btnstart.setLayoutY(210);

        btnstart.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                rndarr = GenerateNumbers();
                for(int i = 0; i < btns.length; i++)
                {
                    btns[i].setText((rndarr.get(i) + 1) + "");
                    if (rndarr.get(i) == 15) {
                        btns[i].setText("");
                        freex = i % 4;
                        freey = i / 4;
                        freeid = i;
                    }
                }
            }
        });

        group.getChildren().add(btnstart);
        Scene scene = new Scene(group);

        primaryStage.setTitle("15ame");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Pane getGrid() {
        int i = 0;
        GridPane gridPane = new GridPane();
        for(Button b : btns) {
            int x = i % 4;
            int y = i / 4;
            gridPane.add(b, x*btnsize, y*btnsize);
            i++;
        }
        return gridPane;
    }

    private void initBtnsArray() {
        for(int i = 0; i < btns.length; i++) {
            btns[i] = new Button((rndarr.get(i) + 1)+"");
            btns[i].setMaxWidth(btnsize);
            btns[i].setMaxHeight(btnsize);
            btns[i].setMinWidth(btnsize);
            btns[i].setMinHeight(btnsize);
            if (rndarr.get(i)==15)
            {
                btns[i].setText("");
                freex = i % 4;
                freey = i / 4;
                freeid = i;
            }
            btns[i].setId(i+"");

            btns[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Button b = (Button)(event.getSource());
                    int id = Integer.parseInt(b.getId());
                    int x = id % 4;
                    int y = id / 4;

                    if (Math.abs(freex-x)+Math.abs(freey-y)==1)
                    {
                        btns[id].setText("");
                        btns[freeid].setText((rndarr.get(id)+1)+"");

                        int tmp = rndarr.get(freeid);
                        rndarr.set(freeid,rndarr.get(id));
                        rndarr.set(id,tmp);

                        freex=x;
                        freey=y;
                        freeid=id;

                    }

                    if (CheckResult()==1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("15ame");
                        alert.setHeaderText(null);
                        alert.setContentText("You win!!!!");
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

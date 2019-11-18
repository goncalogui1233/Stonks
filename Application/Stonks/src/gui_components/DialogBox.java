package gui_components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stonks.Constants;

public class DialogBox implements Constants{
    private static Stage dialogBox;
    private static DIALOG_RETURN answer;
    
    public static void display(DIALOG_TYPE type, String subTitle, String text){
        dialogBox = new Stage();
        
        dialogBox.initStyle(StageStyle.UNDECORATED); /*Remove window default border and buttons (minimize, close, etc...)*/
        dialogBox.initModality(Modality.APPLICATION_MODAL); /*Unables clicks outside of this window*/
        dialogBox.setWidth(DBOX_WIDTH);
        dialogBox.setHeight(DBOX_HEIGHT);
        dialogBox.setAlwaysOnTop(true); /*Cant be onfocused (application wise)*/
        
        setupDialogBox(type, subTitle, text);
        
        dialogBox.showAndWait();
    }
    
    private static DIALOG_RETURN setupDialogBox(DIALOG_TYPE type, String subTitle, String text){
        BorderPane rootLayout = new BorderPane();
        rootLayout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        
        dialogBox.setScene(new Scene(rootLayout));
        
        /*Layout setup*/
        BorderPane topLayout = new BorderPane();
        rootLayout.setTop(topLayout);
        
        Pane centerLayout = new Pane();
        rootLayout.setCenter(centerLayout);
        
        HBox bottomLayout = new HBox();
        rootLayout.setBottom(bottomLayout);
        
        /*Content setup*/
        Label lblTitle;
        ImageView closeBtn = new ImageView(new Image("resources/DialogBox_Close.png"));
        closeBtn.setFitWidth(25);
        closeBtn.setFitHeight(25);
        
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = DIALOG_RETURN.X_CLOSED;
            dialogBox.close();
        });
        
        Label lblSubTitle = new Label(subTitle);
        Label lblText = new Label(text);
        
        List<String> listButtons = new ArrayList<>();
        
        switch(type){
            case SUCCESS:
                lblTitle = new Label("Success");
                listButtons.add("Ok");
                break;
            case ERROR:
                lblTitle = new Label("Error");
                listButtons.add("Ok");
                break;
            case CONFIRM:
                lblTitle = new Label("Are you sure?");
                listButtons.add("Yes");
                listButtons.add("No");
                break;
            default:
                dialogBox.close();
                return DIALOG_RETURN.EXCEPTION;
        }
        topLayout.setLeft(lblTitle);
        topLayout.setRight(closeBtn);
        topLayout.setBottom(lblSubTitle);
        
        centerLayout.getChildren().add(lblText);
        
        return answer;
    }
}

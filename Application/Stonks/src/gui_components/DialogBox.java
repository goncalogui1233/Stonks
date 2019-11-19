package gui_components;

import java.util.ArrayList;
import java.util.List;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stonks.Constants;

public class DialogBox implements Constants{
    private static Stage dialogBox;
    private static DBOX_RETURN answer;
    
    public static DBOX_RETURN display(DBOX_TYPE type, DBOX_CONTENT content){
        dialogBox = new Stage();
        
        dialogBox.initStyle(StageStyle.UNDECORATED); /*Remove window default border and buttons (minimize, close, etc...)*/
        dialogBox.initModality(Modality.APPLICATION_MODAL); /*Unables clicks outside of this window*/
        dialogBox.setWidth(DBOX_WIDTH);
        dialogBox.setHeight(DBOX_HEIGHT);
        dialogBox.setAlwaysOnTop(true); /*Cant be onfocused (application wise)*/
        
        setupDialogBox(type, content.getSubTitle(), content.getText());
        
        dialogBox.showAndWait();
        
        return answer;
    }
    
    private static void setupDialogBox(DBOX_TYPE type, String subTitle, String text){
        BorderPane rootLayout = new BorderPane();
        
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
        
        Pane imageContainer = new Pane();
        ImageView closeBtn = new ImageView(new Image("resources/DialogBox_Close.png"));
        closeBtn.setFitWidth(25);
        closeBtn.setFitHeight(25);
        imageContainer.getChildren().add(closeBtn);
        
        imageContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = DBOX_RETURN.X_CLOSED;
            dialogBox.close();
        });
        
        Label lblSubTitle = new Label(subTitle);
        
        Label lblText = new Label(text);
        lblText.setWrapText(true);
        lblText.setTextAlignment(TextAlignment.JUSTIFY);
        lblText.setMaxWidth(DBOX_WIDTH - 1);
        
        List<Label> listButtons = new ArrayList<>();
        
        Label tempButton;
        switch(type){
            case SUCCESS:
                lblTitle = new Label("Success");
                
                tempButton = new Label("Ok");
                tempButton.setOnMouseClicked(e -> {
                    answer = DBOX_RETURN.OK;
                    dialogBox.close();
                });
                listButtons.add(tempButton);
                break;
            case ERROR:
                lblTitle = new Label("Error");
                
                tempButton = new Label("Ok");
                tempButton.setOnMouseClicked(e -> {
                    answer = DBOX_RETURN.OK;
                    dialogBox.close();
                });
                listButtons.add(tempButton);
                break;
            case CONFIRM:
                lblTitle = new Label("Are you sure?");
                
                tempButton = new Label("Yes");
                tempButton.setOnMouseClicked(e -> {
                    answer = DBOX_RETURN.YES;
                    dialogBox.close();
                });
                listButtons.add(tempButton);
                
                tempButton = new Label("No");
                tempButton.setOnMouseClicked(e -> {
                    answer = DBOX_RETURN.NO;
                    dialogBox.close();
                });
                listButtons.add(tempButton);
                break;
            default:
                dialogBox.close();
                answer = DBOX_RETURN.EXCEPTION;
                return;
        }
        
        /*":unique" & ":last" & ":first" SELECTOR*/
        PseudoClass unique = PseudoClass.getPseudoClass("unique");
        PseudoClass first = PseudoClass.getPseudoClass("first");
        PseudoClass last = PseudoClass.getPseudoClass("last");
        
        for(Label btn:listButtons){
            btn.setId("button");
            
            btn.setMinSize((DBOX_WIDTH / listButtons.size()) - (4 / listButtons.size()), DBOX_BUTTON_HEIGHT);
            btn.setMaxSize((DBOX_WIDTH / listButtons.size()) - (4 / listButtons.size()), DBOX_BUTTON_HEIGHT);
            
            bottomLayout.getChildren().add(btn);
            
            /*APPLY ":unique" SELECTOR IF THERE IS ONLY ONE BUTTON*/
            if(listButtons.size() == 1)
                btn.pseudoClassStateChanged(unique, true);
            /*APPLY ":first" SELECTOR TO THE FIRST BUTTON*/
            else if(btn.equals(listButtons.get(0)))
                btn.pseudoClassStateChanged(first, true);
            /*APPLY ":last" SELECTOR TO THE LAST BUTTON*/
            else if(btn.equals(listButtons.get(listButtons.size() - 1)))
                btn.pseudoClassStateChanged(last, true);
        }
        
        topLayout.setLeft(lblTitle);
        topLayout.setRight(imageContainer);
        topLayout.setBottom(lblSubTitle);
        
        centerLayout.getChildren().add(lblText);

        /*SELECTORS*/
        PseudoClass selector;
        if(type == DBOX_TYPE.ERROR)
            selector = PseudoClass.getPseudoClass("error");
        else if(type == DBOX_TYPE.SUCCESS)
            selector = PseudoClass.getPseudoClass("success");
        else
            selector = null;
        
        try{
            rootLayout.pseudoClassStateChanged(selector, true);
        }catch(NullPointerException ex){ }
        
        /*CSS ID'S*/
        rootLayout.setId("dialogBox");
        
        topLayout.setId("topLayout");
        centerLayout.setId("centerLayout");
        bottomLayout.setId("bottomLayout");
        
        lblTitle.setId("title");
        lblSubTitle.setId("subTitle");
        lblText.setId("text");
        
        /*DEBUG CSS*/
        //topLayout.getStyleClass().add("BACKGROUND_RED");
        //centerLayout.getStyleClass().add("BACKGROUND_GREEN");
        //bottomLayout.getStyleClass().add("BACKGROUND_BLUE");
        //imageContainer.getStyleClass().add("BACKGROUND_BLUE");
    }
}

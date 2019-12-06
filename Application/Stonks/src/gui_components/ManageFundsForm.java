package gui_components;

import exceptions.AuthenticationException;
import exceptions.GoalNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import observables.GoalsObservable;
import stonks.Constants;

/**
 *
 * @author Gonçalo
 */
public class ManageFundsForm {

    private int goalID = 0;
    private final GoalsObservable goalsObs;
    private int valueForAccom;

    private Stage fundsForm;

    private BorderPane root;
    private BorderPane topLayout;
    private Pane imageContainer;

    private HBox buttons;
    private ImageView closeBtn;
    private ImageView imagePlusButton;
    private ImageView imageSubButton;

    private VBox form;

    private VBox fundsDiv;

    private VBox bottom;

    private Label title;
    private Label errorFunds;
    private Label objective;
    private Label accomplished;
    private TextField value;
    private Button btnsubmit;

    public ManageFundsForm(GoalsObservable goalsObs) {
        this.goalsObs = goalsObs;
    }

    public void display(int goalID) {
        this.goalID = goalID;

        try {
            this.valueForAccom = goalsObs.getGoal(goalID).getWallet().getSavedMoney();
        } catch (AuthenticationException ex) {
            Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GoalNotFoundException ex) {
            Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        fundsForm = new Stage();

        fundsForm.initStyle(StageStyle.TRANSPARENT);
        /*Remove window default border and buttons (minimize, close, etc...)*/
        fundsForm.initModality(Modality.APPLICATION_MODAL);
        /*Unables clicks outside of this window*/
        fundsForm.setWidth(300);
        fundsForm.setHeight(370);
        fundsForm.setAlwaysOnTop(true);

        setupWindow(goalID);
        setupEventListeners();

        fundsForm.showAndWait();

    }

    public void setupWindow(int goalID) {

        root = new BorderPane();
        root.setId("fundsForm");
        root.getStyleClass().addAll("stonks-box");
        fundsForm.setScene(new Scene(root));

        topLayout = new BorderPane();
        topLayout.getStyleClass().addAll("topLayout");

        //Title 
        title = new Label("Updating Funds");
        if (goalID > 0) {
            try {
                title.setText(goalsObs.getGoal(goalID).getName() + " Wallet");
            } catch (AuthenticationException ex) {
                Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GoalNotFoundException ex) {
                Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            title.wrapTextProperty().set(true);
        }

        title.getStyleClass().addAll("title");

        //Btn Close
        imageContainer = new Pane();
        closeBtn = new ImageView(new Image("resources/DialogBox_Close.png"));
        closeBtn.setFitWidth(25);
        closeBtn.setFitHeight(25);
        imageContainer.getChildren().add(closeBtn);

        imageContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //answer = Constants.DBOX_RETURN.X_CLOSED; 
            fundsForm.close();
        });

        topLayout.setLeft(title);
        topLayout.setRight(imageContainer);

        //Form 
        form = new VBox();
        form.getStyleClass().addAll("form");

        //Funds TextField 
        fundsDiv = new VBox();
        fundsDiv.getStyleClass().addAll("fieldDiv");

        value = new TextField();
        value.getStyleClass().addAll("value");

        //Only accept number 
        value.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                if (!newValue.matches("\\d*")) {
                    value.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (value.getText().length() >= 9) {
                    value.setText(value.getText().substring(0, 9));

                }

            }
        });

        errorFunds = new Label("Funds cannot be negative");
        errorFunds.getStyleClass().addAll("fieldError");
        errorFunds.setVisible(false);

        //Buttons Sum and Subtraction
        buttons = new HBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(10);

        Pane imagePlusContainer = new Pane();
        imagePlusButton = new ImageView(new Image("resources/FundsFormPlus.png"));
        imagePlusButton.setFitWidth(25);
        imagePlusButton.setFitHeight(25);
        imagePlusContainer.getChildren().add(imagePlusButton);

        imagePlusContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!value.getText().isEmpty()) {
                valueForAccom += Integer.parseInt(value.getText());
                accomplished.setText("Accomplished: " + valueForAccom + "€");
            }
        });

        Pane imageSubContainer = new Pane();
        imageSubButton = new ImageView(new Image("resources/FundsFormSub.png"));
        imageSubButton.setFitWidth(25);
        imageSubButton.setFitHeight(25);
        imageSubContainer.getChildren().add(imageSubButton);

        imageSubContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!value.getText().isEmpty()) {
                if (valueForAccom - Integer.parseInt(value.getText()) >= 0) {
                    valueForAccom -= Integer.parseInt(value.getText());
                    accomplished.setText("Accomplished: " + valueForAccom + "€");
                }
            }
        });

        buttons.getChildren().addAll(imagePlusContainer, imageSubContainer);

        //label Objective
        objective = new Label();
        objective.getStyleClass().addAll("fieldTitle");
        try {
            objective.setText("Objective: " + goalsObs.getGoal(goalID).getObjective() + "€");
        } catch (AuthenticationException ex) {
            Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GoalNotFoundException ex) {
            Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        //label Accomplished
        accomplished = new Label();
        accomplished.getStyleClass().addAll("fieldTitle");
        accomplished.setText("Accomplished: " + valueForAccom + "€");

        fundsDiv.getChildren().addAll(value, buttons, errorFunds, objective, accomplished);

        form.getChildren().add(fundsDiv);

        //button submit
        bottom = new VBox();
        btnsubmit = new Button("Save");
        btnsubmit.getStyleClass().addAll("button", "btn-default", "btn-form");

        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.getChildren().add(btnsubmit);

        root.setTop(topLayout);
        root.setCenter(form);
        root.setBottom(bottom);
    }

    private void setupEventListeners() {
        btnsubmit.setOnAction((ActionEvent e) -> {
            try {
                /*int errors = 0;
                
                try{
                if(!value.getText().isEmpty()){
                switch (goalsObs.verifyData(Constants.GOAL_FIELD.OBJECTIVE, Integer.parseInt(value.getText()))) {
                case MIN_NUMBER:
                errorFunds.setText("Wallet value cannot be negative");
                errorFunds.setVisible(true);
                errors++;
                break;
                case MAX_NUMBER:
                errorFunds.setText("Objective has a maximum value 999999999");
                errorFunds.setVisible(true);
                errors++;
                break;
                default:
                
                errorFunds.setVisible(false);
                break;
                }
                }
                }
                catch(NumberFormatException excep){
                System.out.println(excep);
                }
                
                
                if(errors == 0)*/
                if (valueForAccom > goalsObs.getGoal(goalID).getWallet().getSavedMoney()) //if the value on the label is bigger,
                {
                    valueForAccom -= goalsObs.getGoal(goalID).getWallet().getSavedMoney();  //than he take off what is already saved
                }
                goalsObs.updateWallet(goalID, valueForAccom);

                Constants.DBOX_CONTENT content;
                content = Constants.DBOX_CONTENT.SUCCESS_WALLET_UPDATE;
                content.setSubExtra(goalsObs.getGoal(goalID).getName());

                DialogBox.display(Constants.DBOX_TYPE.SUCCESS, Constants.DBOX_CONTENT.SUCCESS_WALLET_UPDATE);

                fundsForm.close();
            } catch (AuthenticationException ex) {
                Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GoalNotFoundException ex) {
                Logger.getLogger(ManageFundsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}

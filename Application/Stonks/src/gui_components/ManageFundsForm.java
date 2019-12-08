package gui_components;

import exceptions.AuthenticationException;
import exceptions.GoalNotFoundException;
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

    private HBox accomplishedDiv;

    private Label title;
    private Label errorNegative;
    private Label errorEmpty;
    private Label objective;
    private Label accomplishedTitle;
    private Label accomplishedValue;
    private Label accomplishedCurrency;
    private TextField ammountValue;
    private Button btnsubmit;

    public ManageFundsForm(GoalsObservable goalsObs) {
        this.goalsObs = goalsObs;
    }

    public void display(int goalID) {
        this.goalID = goalID;

        try {
            this.valueForAccom = goalsObs.getGoal(goalID).getWallet().getSavedMoney();
        } catch (AuthenticationException ex) {
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_AUTH);
        } catch (GoalNotFoundException ex) {
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
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
                DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_AUTH);
            } catch (GoalNotFoundException ex) {
                DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
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

        ammountValue = new TextField();
        ammountValue.getStyleClass().addAll("value");

        //Only accept number 
        ammountValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                if (!newValue.matches("\\d*")) {
                    ammountValue.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (ammountValue.getText().length() >= 9) {
                    ammountValue.setText(ammountValue.getText().substring(0, 9));

                }

            }
        });

        errorNegative = new Label("Funds value cannot be negative");
        errorNegative.getStyleClass().addAll("fieldError");
        errorNegative.setVisible(false);

        errorEmpty = new Label("Insert an ammount to add or remove");
        errorEmpty.getStyleClass().addAll("fieldError");
        errorEmpty.setVisible(false);

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
            if (!ammountValue.getText().isEmpty()) {
                //if(Integer.parseInt(ammountValue.getText()) > goalsObs.getGoal(goalID).getObjective())
                valueForAccom += Integer.parseInt(ammountValue.getText());
                accomplishedValue.setText(Integer.toString(valueForAccom));
            }
        });

        Pane imageSubContainer = new Pane();
        imageSubButton = new ImageView(new Image("resources/FundsFormSub.png"));
        imageSubButton.setFitWidth(25);
        imageSubButton.setFitHeight(25);
        imageSubContainer.getChildren().add(imageSubButton);

        imageSubContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!ammountValue.getText().isEmpty()) {
                if (valueForAccom - Integer.parseInt(ammountValue.getText()) >= 0) {
                    valueForAccom -= Integer.parseInt(ammountValue.getText());
                    accomplishedValue.setText(Integer.toString(valueForAccom));
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
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_AUTH);
        } catch (GoalNotFoundException ex) {
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
        }

        //Accomplished div
        accomplishedDiv = new HBox();

        //label Accomplished
        accomplishedTitle = new Label("Accomplished: ");
        accomplishedValue = new Label(Integer.toString(valueForAccom));
        accomplishedCurrency = new Label(" €");
        accomplishedTitle.getStyleClass().addAll("fieldTitle");

        accomplishedDiv.getChildren().addAll(accomplishedTitle, accomplishedValue, accomplishedCurrency);

        fundsDiv.getChildren().addAll(ammountValue, errorEmpty, errorNegative, buttons, objective, accomplishedDiv);

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

                int errors = 0;
                //Validate Objective 
                if (ammountValue.getText().isEmpty()) {
                    errorEmpty.setVisible(true);
                    errors++;

                } else {
                    errorEmpty.setVisible(false);
                }

                if (errors == 0) {

                    if (valueForAccom > goalsObs.getGoal(goalID).getWallet().getSavedMoney()) //if the value on the label is bigger,
                    {
                        valueForAccom -= goalsObs.getGoal(goalID).getWallet().getSavedMoney();  //than he take off what is already saved
                    }
                    goalsObs.updateWallet(goalID, Integer.parseInt(accomplishedValue.getText()));

                    DialogBox.display(Constants.DBOX_TYPE.SUCCESS, Constants.DBOX_CONTENT.SUCCESS_WALLET_UPDATE);

                    fundsForm.close();
                }

            } catch (AuthenticationException ex) {
                DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_AUTH);
            } catch (GoalNotFoundException ex) {
                DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
            }
        });
    }

}

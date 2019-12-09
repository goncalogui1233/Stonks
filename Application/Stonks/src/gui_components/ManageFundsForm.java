package gui_components;

import exceptions.AuthenticationException;
import exceptions.GoalNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import models.GoalModel;
import observables.GoalsObservable;
import stonks.Constants;

public class ManageFundsForm implements Constants {

    private int goalID = 0;
    private GoalModel goal;
    private final GoalsObservable goalsObs;
    private int originalAccomplished = 0;
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

    private VBox ammountDiv;

    private VBox bottom;

    private VBox titleContainer;
    private HBox accomplishedDiv;
    private HBox objectiveDiv;

    private Label title;
    private Label subtitle;
    private Label ammountTitle;
    private Label errorAmmount;
    private Label objectiveTitle;
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
            goal = goalsObs.getGoal(goalID);

        } catch (AuthenticationException ex) {
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_AUTH);
            return;
        } catch (GoalNotFoundException ex) {
            DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
            return;
        }

        this.valueForAccom = goal.getWallet().getSavedMoney();
        originalAccomplished = valueForAccom;

        fundsForm = new Stage();

        fundsForm.initStyle(StageStyle.TRANSPARENT);
        /*Remove window default border and buttons (minimize, close, etc...)*/
        fundsForm.initModality(Modality.APPLICATION_MODAL);
        /*Unables clicks outside of this window*/
        fundsForm.setWidth(WALLET_FORM_WIDTH);
        fundsForm.setHeight(WALLET_FORM_HEIGHT);
        fundsForm.setAlwaysOnTop(true);

        setupWindow();
        setupEventListeners();

        fundsForm.showAndWait();

    }

    public void setupWindow() {

        root = new BorderPane();
        root.setId("fundsForm");
        root.getStyleClass().addAll("stonks-box");
        fundsForm.setScene(new Scene(root));

        topLayout = new BorderPane();
        topLayout.getStyleClass().addAll("topLayout");

        //Title 
        titleContainer = new VBox();

        title = new Label("Manage Funds");
        title.getStyleClass().addAll("title");
        subtitle = new Label(goal.getName());
        subtitle.getStyleClass().add("subtitle");
        subtitle.wrapTextProperty().setValue(true);
        subtitle.setMinHeight(50);
        subtitle.setMaxWidth(150);
        titleContainer.getChildren().addAll(title, subtitle);

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

        topLayout.setLeft(titleContainer);
        topLayout.setRight(imageContainer);

        //Form 
        form = new VBox();
        form.getStyleClass().addAll("form");

        //Ammount div
        ammountDiv = new VBox();
        ammountDiv.getStyleClass().addAll("fieldDiv");

        //Ammount Title
        ammountTitle = new Label("Ammount (€)");
        ammountTitle.getStyleClass().addAll("fieldTitle");

        //Ammount TextField 
        ammountValue = new TextField();
        ammountValue.getStyleClass().addAll("value");

        //Ammount Errors
        errorAmmount = new Label("Insert an ammount to add or remove");
        errorAmmount.getStyleClass().addAll("fieldError");
        errorAmmount.setVisible(false);
        errorAmmount.wrapTextProperty().setValue(true);

        ammountDiv.getChildren().addAll(ammountTitle, ammountValue, errorAmmount);
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

        //Buttons Sum and Subtraction
        buttons = new HBox();
        //buttons.setPadding(new Insets(20, 0, 0, 0));
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(10);

        Pane imagePlusContainer = new Pane();
        imagePlusContainer.setMinWidth(25);
        imagePlusContainer.setMinHeight(25);
        imagePlusButton = new ImageView(new Image("resources/FundsFormPlus.png"));
        imagePlusButton.setFitWidth(25);
        imagePlusButton.setFitHeight(25);
        imagePlusContainer.getChildren().add(imagePlusButton);

        imagePlusContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!ammountValue.getText().isEmpty()) {
                System.out.println(Integer.parseInt(ammountValue.getText()) + valueForAccom);
                if (Integer.parseInt(ammountValue.getText()) + valueForAccom > goal.getObjective()) {
                    errorAmmount.setText("Accomplished value cannot be superior than the objective");
                    errorAmmount.setVisible(true);
                } else {
                    valueForAccom += Integer.parseInt(ammountValue.getText());
                    accomplishedValue.setText(Integer.toString(valueForAccom));
                    errorAmmount.setVisible(false);
                }

            } else {
                errorAmmount.setText("Insert an ammount to add");
                errorAmmount.setVisible(true);
            }
        });

        Pane imageSubContainer = new Pane();
        imageSubContainer.setMinWidth(25);
        imageSubContainer.setMinHeight(25);
        imageSubButton = new ImageView(new Image("resources/FundsFormSub.png"));
        imageSubButton.setFitWidth(25);
        imageSubButton.setFitHeight(25);
        imageSubContainer.getChildren().add(imageSubButton);

        imageSubContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!ammountValue.getText().isEmpty()) {
                if (valueForAccom - Integer.parseInt(ammountValue.getText()) >= 0) {
                    valueForAccom -= Integer.parseInt(ammountValue.getText());
                    accomplishedValue.setText(Integer.toString(valueForAccom));
                    errorAmmount.setVisible(false);
                } else {
                    errorAmmount.setText("The ammount to remove cannot be superior than the accomplished");
                    errorAmmount.setVisible(true);
                }
            } else {
                errorAmmount.setText("Insert an ammount to remove");
                errorAmmount.setVisible(true);
            }
        });

        //Accomplished div
        accomplishedDiv = new HBox();
        accomplishedDiv.getStyleClass().add("fieldDiv");
        accomplishedDiv.setPadding(new Insets(5, 0, 0, 0));
        
        //Accomplished
        accomplishedTitle = new Label("Accomplished: ");
        accomplishedValue = new Label(Integer.toString(valueForAccom));
        accomplishedCurrency = new Label("€");
        accomplishedTitle.getStyleClass().addAll("fieldTitle");

        accomplishedDiv.getChildren().addAll(accomplishedTitle, accomplishedValue, accomplishedCurrency);

        buttons.getChildren().addAll(accomplishedDiv, imagePlusContainer, imageSubContainer);

        //label Objective
        objectiveDiv = new HBox();
        objectiveDiv.getStyleClass().add("fieldDiv");
        objectiveTitle = new Label();
        objectiveTitle.getStyleClass().addAll("fieldTitle");
        objective = new Label();

        objectiveTitle.setText("Objective: ");
        objective.setText(goal.getObjective() + "€");
        objectiveDiv.getChildren().addAll(objectiveTitle, objective);

        form.getChildren().addAll(objectiveDiv, ammountDiv, buttons);

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

            int errors = 0;
            //Validate Objective 
            if (ammountValue.getText().isEmpty() && originalAccomplished == valueForAccom) {
                errorAmmount.setText("Insert an ammount to add or remove");
                errorAmmount.setVisible(true);
                errors++;

            } else {
                if (originalAccomplished == valueForAccom) {
                    errorAmmount.setText("Click on the button to add or remove your ammount");
                    errorAmmount.setVisible(true);
                    errors++;
                } else {
                    errorAmmount.setVisible(false);
                }
            }

            if (errors == 0) {

                /*if (valueForAccom > goal.getWallet().getSavedMoney()) //if the value on the label is bigger,
                {
                    valueForAccom -= goal.getWallet().getSavedMoney();  //than he take off what is already saved
                }*/
                DBOX_CONTENT content;
                content = DBOX_CONTENT.CONFIRM_WALLET_UPDATE;
                content.setSubExtra(goal.getName());
                if (DialogBox.display(DBOX_TYPE.CONFIRM, content) == DBOX_RETURN.YES) {
                    if (goalsObs.updateWallet(goalID, Integer.parseInt(accomplishedValue.getText()))) {
                        goalsObs.firePropertyChange(GOAL_EVENT.GOAL_MANAGE_FUNDS.name(), null, null);
                        DialogBox.display(Constants.DBOX_TYPE.SUCCESS, Constants.DBOX_CONTENT.SUCCESS_WALLET_UPDATE);
                        fundsForm.close();
                    } else {
                        DialogBox.display(Constants.DBOX_TYPE.ERROR, Constants.DBOX_CONTENT.ERROR_WALLET_UPDATE);
                    }

                }
            }

        });
    }

}

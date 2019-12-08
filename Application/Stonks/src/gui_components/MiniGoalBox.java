package gui_components;

import java.text.DecimalFormat;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class MiniGoalBox implements Constants{
    private final VBox root;
    private VBox box;
    
    private StackPane progressStack;
    
    private Label lblName;
    private Label lblProgress;
    private ProgressBar pbProgress;
    
    public MiniGoalBox() {
        root = new VBox();
        
        root.setMinWidth(SIDEMENU_WIDTH-30);
        root.setMaxSize(SIDEMENU_WIDTH-30, 80);
        root.setId("miniGoalBox");
    
        setupMiniGoalBox();
    }

    public VBox getRoot() {
        return root;
    }

    private void setupMiniGoalBox() {
        box = new VBox();
        box.getStyleClass().add("stonks-box");
        
        lblName = new Label();
        lblName.getStyleClass().add("boxName");
        
        progressStack = new StackPane();
        
        lblProgress = new Label();
        
        pbProgress = new ProgressBar();
        pbProgress.setMinSize(SIDEMENU_WIDTH-31, 20);
        pbProgress.setMaxSize(SIDEMENU_WIDTH-31, 20);
        
        progressStack.getChildren().addAll(pbProgress, lblProgress);
        
        box.getChildren().addAll(lblName, progressStack);
        root.getChildren().add(box);
    }
    
    public void setName(String name){
        lblName.setText(name);
    }
    
    public void setProgress(double progress){
        lblProgress.setText(new DecimalFormat("#,##0.0#").format(progress * 100) + "%");
        pbProgress.setProgress(progress);
    }
}

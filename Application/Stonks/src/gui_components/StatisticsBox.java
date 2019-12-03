/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import controllers.GoalController;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bizarro
 */
public class StatisticsBox {
    private GoalController controller;
    private VBox root;
    
    public StatisticsBox(GoalController controller) {
        this.controller = controller;
        root = new VBox();
    }

    public VBox getRoot() {
        return root;
    }
    
    
}

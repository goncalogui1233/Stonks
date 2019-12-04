/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Tiago
 */
public class GoalNotFoundException extends Exception {

    public GoalNotFoundException() {
        super("The goal doesnt exist");
    }
}

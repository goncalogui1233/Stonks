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
public class EmptyDepositException extends Exception {

    public EmptyDepositException() {
        super("The goal doesnt have any deposit");
    }
}

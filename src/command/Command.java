/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import Observer.Observador;

/**
 *
 * @author vinny
 */
public abstract class Command {
    
    protected Observador observador;

	public Command(Observador observer) {
		this.observador = observer;
	}
	
	public abstract void execute(String args[]);
    
}

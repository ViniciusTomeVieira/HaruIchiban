/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;


import control.GerenciadorJogo;


/**
 *
 * @author vinny
 */
public abstract class Command {
    
    protected GerenciadorJogo gerenciador;

	public Command(GerenciadorJogo gerenciador, int linha, int coluna) {
		this.gerenciador = gerenciador;
	}
	
	public abstract void execute(int linha, int coluna);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;


import control.GerenciadorJogo;


/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public abstract class Command {
    
    protected GerenciadorJogo gerenciador;

	public Command(GerenciadorJogo gerenciador, int linha, int coluna) {
		this.gerenciador = gerenciador;
	}
	
	public abstract void execute(int linha, int coluna);
    
}

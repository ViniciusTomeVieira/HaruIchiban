/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;


import control.GerenciadorJogoImpl;

/**
 *
 * @author vinny
 */
public abstract class EstadoJogo {
    protected GerenciadorJogoImpl gerenciadorJogo;

    public EstadoJogo(GerenciadorJogoImpl gerenciadorJogo) {
        this.gerenciadorJogo = gerenciadorJogo;
    }
    
    public abstract void proxEstado();

    public void selecionarCores() {
    }

    public void escolherFloresDeck(int row, int col) {
    }
    
    public void escolherFlorParaJogar(int index) {

    }
    public void compararFlores() {
        

    }
}

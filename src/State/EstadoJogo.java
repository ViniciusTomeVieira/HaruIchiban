/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;


import control.GerenciadorJogoImpl;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
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
     public void empate(int vencedor){}

    public void juniorEscuro(int columnAtPoint, int rowAtPoint) {
    }

    public void seniorEscolhe(int columnAtPoint, int rowAtPoint) {
    }

    public void juniorMovePecas(int columnAtPoint, int rowAtPoint) {
    }

    public void seniorEscolheEscuro(int columnAtPoint, int rowAtPoint) {
    }
}

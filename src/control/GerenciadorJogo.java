/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.Icon;
import model.Peca;

/**
 *
 * @author vinny
 */
public interface GerenciadorJogo {
    
    void inicializarTabuleiro() throws Exception;
    void inicializarFlores(Peca[][] floresJogador) throws Exception;
    void addObservador(Observador obs);
    Icon getPeca(int col, int row) throws Exception;
    Icon getFlor(int col, int row, Peca[][] floresJogador) throws Exception;
    
}

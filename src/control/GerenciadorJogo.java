/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.Icon;

/**
 *
 * @author vinny
 */
public interface GerenciadorJogo {
    
    void inicializarTabuleiro() throws Exception;
    void addObservador(Observador obs);
    Icon getPeca(int col, int row) throws Exception;
    
}

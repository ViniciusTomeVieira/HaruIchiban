/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import model.Peca;

/**
 *
 * @author Adroan
 */
public interface VerificarFlores {
    
    int verificar(Peca[][] matriz,int linha,int coluna);
}

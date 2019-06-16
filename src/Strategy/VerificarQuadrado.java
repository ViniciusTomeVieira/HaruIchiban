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
public class VerificarQuadrado implements VerificarFlores {

    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        if (linha < 4 && coluna < 4) {
            if (matriz[coluna][linha].getClass() == matriz[coluna+1][linha].getClass()
                    && matriz[coluna][linha].getClass()== matriz[coluna][linha+1].getClass()
                    && matriz[coluna][linha].getClass() == matriz[coluna+1][linha + 1].getClass()) {
                        return 1;
                    }
                
            
        }
        return 0;
    }

}

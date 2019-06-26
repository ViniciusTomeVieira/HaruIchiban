/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import composite.Peca;

/**
 *
 * @author Adroan
 */
public class VerificarQuadrado implements VerificarFlores {

    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        if (linha < 4 && coluna < 4) {
            if (matriz[coluna][linha].getNome().equals(matriz[coluna+1][linha].getNome())
                    && matriz[coluna][linha].getNome().equals(matriz[coluna][linha+1].getNome())
                    && matriz[coluna][linha].getNome().equals(matriz[coluna+1][linha+1].getNome())) {
                System.out.println("retornou 1");        
                return 1;
                    }
        }
        System.out.println("retornou 0");
        return 0;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import composite.Peca;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class VerificarQuadrado implements VerificarFlores {

    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        if (linha < 4 && coluna < 4) {
                if ((matriz[coluna][linha].getNome().length() == matriz[coluna+1][linha].getNome().length() || (matriz[coluna][linha].getNome().length() + 1) == matriz[coluna+1][linha].getNome().length() || (matriz[coluna][linha].getNome().length()) == matriz[coluna+1][linha].getNome().length() + 1)
                    && (matriz[coluna][linha].getNome().length() == matriz[coluna][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length() + 1) == matriz[coluna][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length()) == matriz[coluna][linha+1].getNome().length() + 1)
                    && (matriz[coluna][linha].getNome().length()==matriz[coluna+1][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length() + 1)== matriz[coluna+1][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length())== matriz[coluna+1][linha+1].getNome().length() + 1)) {
                System.out.println("retornou 1");        
                return 1;
                    }
            }
            
        
        System.out.println("retornou 0");
        return 0;
    }

}

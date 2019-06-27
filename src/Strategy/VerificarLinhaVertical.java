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
public class VerificarLinhaVertical implements VerificarFlores {

    private int numFlores;

    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        while (linha < 3) {
            if (matriz[coluna][linha].getNome().length() == matriz[coluna][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length() + 1) == matriz[coluna][linha+1].getNome().length() || (matriz[coluna][linha].getNome().length()) == matriz[coluna][linha+1].getNome().length() + 1){
                numFlores++;
                linha++;
                
            }else{
            break;
            }
        }
        switch (numFlores) {
            case 3:
                System.out.println("retornou 2");
                return 2;
            case 4:
                System.out.println("retornou 5");
                return 5;
            default:
                System.out.println("retornou 0");
                return 0;
        }
    }
}

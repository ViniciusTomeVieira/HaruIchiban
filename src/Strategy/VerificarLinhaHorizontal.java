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
public class VerificarLinhaHorizontal implements VerificarFlores {

    private int numFlores = 0;

    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        while (coluna < 3) {
            if (matriz[coluna][linha].getClass() == matriz[coluna+1][linha].getClass()) {
                numFlores++;
                coluna++;
               
            }else{
            break;
            }
        }
    
        switch (numFlores) {
            case 4:
                return 2;
            case 5:
                return 5;
            default:
                return 0;
        }
    }

}

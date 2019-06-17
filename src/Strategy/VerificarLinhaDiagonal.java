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
public class VerificarLinhaDiagonal implements VerificarFlores{
    private int numFlores;
    @Override
    public int verificar(Peca[][] matriz, int linha, int coluna) {
        while (linha<4&&coluna < 4) {
            if (matriz[coluna][linha].getClass() == matriz[coluna+1][linha+1].getClass()) {
                numFlores++;
                
                coluna++;
                linha++;
            }else{
            break;
            }
        }
         while (linha > 1 && coluna < 4) {
                if (matriz[coluna][linha].getClass() == matriz[coluna+1][linha-1].getClass()) {
                    numFlores++;

                    coluna++;
                    linha--;
                } else {
                    break;
                }
            }
        switch (numFlores) {
            case 4:
                return 3;
            case 5:
                return 5;
            default:
                return 0;
        }
    }
    
}
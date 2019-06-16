/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import decorator.nenufares.Nenufar;
import decorator.nenufares.NenufarBase;
import model.Agua;
import decorator.nenufares.NenufarClaro;
import decorator.nenufares.NenufarEscuro;
import decorator.sapos.Sapo;
import model.Peca;
import decorator.sapos.SapoAmarelo;
import decorator.sapos.SapoBase;
import decorator.sapos.SapoRosa;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class ConstruirPadrao2 extends CriadorDeTabuleiro {

    @Override
    public void construirTabuleiro(Peca[][] tabuleiro) {
        Sapo sapoBase = new SapoBase();
        Nenufar nenufarBase = new NenufarBase();
        tabuleiro[0][0] = new NenufarClaro(nenufarBase);
        tabuleiro[0][1] = new Agua();
        tabuleiro[0][2] = new SapoRosa(sapoBase);
        tabuleiro[0][3] = new Agua();
        tabuleiro[0][4] = new NenufarClaro(nenufarBase);

        tabuleiro[1][0] = new Agua();
        tabuleiro[1][1] = new NenufarClaro(nenufarBase);
        tabuleiro[1][2] = new NenufarClaro(nenufarBase);
        tabuleiro[1][3] = new NenufarClaro(nenufarBase); // Aleatorio?
        tabuleiro[1][4] = new Agua();

        tabuleiro[2][0] = new NenufarClaro(nenufarBase);
        tabuleiro[2][1] = new NenufarClaro(nenufarBase);
        tabuleiro[2][2] = new Agua();
        tabuleiro[2][3] = new NenufarClaro(nenufarBase);
        tabuleiro[2][4] = new NenufarEscuro(nenufarBase);

        tabuleiro[3][0] = new Agua();
        tabuleiro[3][1] = new NenufarClaro(nenufarBase);
        tabuleiro[3][2] = new SapoAmarelo(sapoBase);
        tabuleiro[3][3] = new NenufarClaro(nenufarBase);
        tabuleiro[3][4] = new Agua();

        tabuleiro[4][0] = new NenufarClaro(nenufarBase);
        tabuleiro[4][1] = new Agua();
        tabuleiro[4][2] = new NenufarClaro(nenufarBase);
        tabuleiro[4][3] = new Agua();
        tabuleiro[4][4] = new NenufarClaro(nenufarBase);
        super.construirTabuleiro(tabuleiro);
    }

}

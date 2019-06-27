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
import composite.Peca;
import decorator.sapos.SapoAmarelo;
import decorator.sapos.SapoBase;
import decorator.sapos.SapoRosa;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class ConstruirPadrao extends CriadorDeTabuleiro {

    @Override
    public void construirTabuleiro(Peca[][] tabuleiro) {
        Sapo sapoBase = new SapoBase();
        Sapo sapoBase2 = new SapoBase();
        Nenufar nenufarBase = new NenufarBase();
        Nenufar nenufarBase2 = new NenufarBase();
        NenufarClaro nenufarClaro= new NenufarClaro(nenufarBase);
        nenufarClaro.selecionarImageNenufar();
        SapoRosa sapoRosa = new SapoRosa(sapoBase);
        sapoRosa.selecionarImageSapo();

        tabuleiro[0][0] = nenufarBase;
        tabuleiro[0][1] = new Agua();
        tabuleiro[0][2] = nenufarBase;
        tabuleiro[0][3] = new Agua();
        tabuleiro[0][4] = nenufarBase;

        tabuleiro[1][0] = new Agua();
        tabuleiro[1][1] = sapoBase;
        tabuleiro[1][2] = nenufarBase;     
        
        NenufarEscuro nenufarEscuro= new NenufarEscuro(nenufarBase2);
        nenufarEscuro.selecionarImageNenufar();
        
        
        tabuleiro[1][3] = nenufarBase2; // Aleatorio?
        tabuleiro[1][4] = new Agua();
        
        SapoAmarelo sapoAmarelo = new SapoAmarelo(sapoBase2);
        sapoAmarelo.selecionarImageSapo();
        
        
        tabuleiro[2][0] = nenufarBase;
        tabuleiro[2][1] = nenufarBase;
        tabuleiro[2][2] = new Agua();
        tabuleiro[2][3] = sapoBase2;
        tabuleiro[2][4] = nenufarBase;

        tabuleiro[3][0] = new Agua();
        tabuleiro[3][1] = nenufarBase;
        tabuleiro[3][2] = nenufarBase;
        tabuleiro[3][3] = nenufarBase;
        tabuleiro[3][4] = new Agua();

        tabuleiro[4][0] = nenufarBase;
        tabuleiro[4][1] = new Agua();
        tabuleiro[4][2] = nenufarBase;
        tabuleiro[4][3] = new Agua();
        tabuleiro[4][4] = nenufarBase;
        
        
        super.construirTabuleiro(tabuleiro); //To change body of generated methods, choose Tools | Templates.
    }

}

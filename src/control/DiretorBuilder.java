/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Builder.CriadorDeTabuleiro;
import model.Peca;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class DiretorBuilder {
    private CriadorDeTabuleiro criador;

    public DiretorBuilder(CriadorDeTabuleiro criador) {
        this.criador = criador;
    }
    
    public void construir(Peca[][] pecas){
        criador.reset();
        criador.construirTabuleiro(pecas);
    }
    
}

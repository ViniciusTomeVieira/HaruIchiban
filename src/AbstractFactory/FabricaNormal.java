/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import java.util.ArrayList;

import model.Flor;



/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class FabricaNormal extends FabricaJogador{

    @Override
    public Jogador criarJogador(Jogador jogador) {
        return new JogadorNormal("", "",null, new Flor[2][4], new ArrayList<>(),"");
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import java.util.ArrayList;
import java.util.List;
import model.Flor;
import model.FlorAmarela;


/**
 *
 * @author Adroan
 */
public class FabricaNormal extends FabricaJogador{

    @Override
    public Jogador criarJogador(Jogador jogador) {
        return new JogadorNormal("", "",null, new Flor[2][4], new ArrayList<>(),"");
    }

    
}

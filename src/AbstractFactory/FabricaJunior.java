/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import java.util.List;
import model.Flor;


/**
 *
 * @author Adroan
 */
public class FabricaJunior extends FabricaJogador{

    @Override
    public Jogador criarJogador(String corDaFlor, boolean jogando, String nome, Flor florEscolhida, String juniorOuSenior, Flor[][] flores, List<Flor> mao) {
        return new JogadorJunior(corDaFlor, jogando, nome, florEscolhida, juniorOuSenior, flores, mao);
    }

    
}

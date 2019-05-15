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
public class FabricaSenior extends FabricaJogador{

    @Override
    public Jogador criarJogador(Jogador jogador) {
        return new JogadorSenior(jogador.getCorDaFlor(), jogador.getNome(), jogador.getFlorEscolhida(), jogador.getFlores(), jogador.getMao(),jogador.getJuniorSenior());
    }

    
    
}

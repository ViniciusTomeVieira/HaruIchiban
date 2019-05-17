/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;



/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class FabricaJunior extends FabricaJogador{

    @Override
    public Jogador criarJogador(Jogador jogador) {
        return new JogadorJunior(jogador.getCorDaFlor(),jogador.getNome(), jogador.getFlorEscolhida(),jogador.getFlores(), jogador.getMao(),jogador.getJuniorSenior());
    }

    
}

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
public class JogadorJunior extends Jogador {
    
    public JogadorJunior(String corDaFlor,String nome, Flor florEscolhida, Flor[][] flores, List<Flor> mao, String juniorSenior) {
        super(corDaFlor,nome, florEscolhida,flores, mao,juniorSenior);
    }
    
    
    
}

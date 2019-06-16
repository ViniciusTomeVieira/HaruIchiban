/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import java.util.List;
import decorator.flores.Flor;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public class JogadorNormal extends Jogador {

    public JogadorNormal(String corDaFlor, String nome, Flor florEscolhida, Flor[][] flores, List<Flor> mao,String juniorSenior) {
        super(corDaFlor, nome, florEscolhida, flores, mao,juniorSenior);
    }
    
}

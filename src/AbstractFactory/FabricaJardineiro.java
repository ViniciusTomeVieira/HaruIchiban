/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import model.Jogador;

/**
 *
 * @author Adroan
 */
public abstract class FabricaJardineiro {
    public abstract JardineiroSenior criarJardineiroSenior(Jogador jogador);
    public abstract JardineiroJunior criarJardineiroJunior(Jogador jogador);
}

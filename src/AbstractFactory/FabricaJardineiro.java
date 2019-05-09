/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

/**
 *
 * @author Adroan
 */
public abstract class FabricaJardineiro {
    public abstract JardineiroSenior criarJardineiroSenior();
    public abstract JardineiroJunior criarJardineiroJunior();
}

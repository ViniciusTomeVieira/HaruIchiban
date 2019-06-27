/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator.sapos;

import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public abstract class SapoDecorator extends Sapo{
    
  private Sapo sapo;

    public SapoDecorator(Sapo sapo) {
        this.sapo = sapo;
    }

    @Override
    public void selecionarImagemSapo(ImageIcon imagem) {
        this.sapo.selecionarImagemSapo(imagem);
    }

    @Override
    public void setNome(String nome) {
        this.sapo.setNome(nome); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

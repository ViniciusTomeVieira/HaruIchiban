/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import model.Peca;
import model.Tabuleiro;

/**
 *
 * @author Adroan
 */
public abstract class CriadorDeTabuleiro {
     private Tabuleiro tabuleiro;

   public Tabuleiro getTabuleiro(){
       return tabuleiro;
   }
    
   public void reset(){
       this.tabuleiro = new Tabuleiro();
   }
   
   public void construirTabuleiro(Peca[][] tabuleiro){
    getTabuleiro().setTabuleiro(tabuleiro);
   }
   
   public Peca[][] getTabuleiroCriado(){
       return getTabuleiro().getTabuleiro();
   }
     
     
}

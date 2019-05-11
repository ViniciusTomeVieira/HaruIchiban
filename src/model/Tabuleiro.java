/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Adroan
 */
public class Tabuleiro {
   private Peca[][] tabuleiro = new Peca[5][5];
   
   private int linha;
   
   private int coluna;

    public Tabuleiro() {
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
   
   
}

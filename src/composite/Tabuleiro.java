/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composite;

import Visitor.Visitor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 2.0
 */
public class Tabuleiro extends Objeto {
   private Peca[][] tabuleiro = new Peca[5][5];
   
   private List<Objeto> pecas = new ArrayList<>();

    public void add(Objeto peca){
        pecas.add(peca);
        
    }
    public void remove(Objeto peca){
        pecas.remove(peca);
        
    }
    public Objeto getPeca(int index){
        return pecas.get(index);
    }  

    public List<Objeto> getPecas() {
        return pecas;
    }
    
    
   
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
        povoarLista();
    }
    
    public Peca getPecaTabuleiro(int coluna, int linha){
        return tabuleiro[coluna][linha];
    }
    
    public void setPecaTabuleiro(int coluna, int linhaa, Peca peca){
        tabuleiro[coluna][linhaa] = peca;
    }

    @Override
    public void setImagem(ImageIcon imagem) {
        for(Objeto obj:pecas){
            obj.setImagem(imagem);
        }
    }

    private void povoarLista() {
        for(int i = 0;i < 5; i++){
            for(int j = 0; j < 5; j++){
                pecas.add(tabuleiro[i][j]);
            }
        }
    }
    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }

    public void organizar() {
        int index = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                tabuleiro[i][j] = (Peca)pecas.get(index);
                index++;
        }
        }
    }
   
}

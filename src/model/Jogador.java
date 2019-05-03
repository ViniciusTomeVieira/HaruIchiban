/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adroan
 */
public class Jogador {
    
    private Peca[][] flores;
    private String corDaFlor;
    private List<Flor> mao = new ArrayList<>();
    private int pontuacao = 0;
    private boolean jogando;

    public Peca[][] getFlores() {
        return flores;
    }

    public void setFlores(Peca[][] flores) {
        this.flores = flores;
    }

    public String getCorDaFlor() {
        return corDaFlor;
    }

    public void setCorDaFlor(String corDaFlor) {
        this.corDaFlor = corDaFlor;
    }

    public List<Flor> getMao() {
        return mao;
    }

    public void setMao(List<Flor> mao) {
        this.mao = mao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public boolean isJogando() {
        return jogando;
    }

    public void setJogando(boolean jogando) {
        this.jogando = jogando;
    }
    
    public void add(Flor flor){ // adiciona uma flor na mão do jogador
        this.mao.add(flor);
    }
    
    
    
    
}

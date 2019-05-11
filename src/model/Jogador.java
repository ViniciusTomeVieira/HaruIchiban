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
    
    private Flor[][] flores = new Flor[2][4];
    private String corDaFlor;
    private List<Flor> mao = new ArrayList<>();
    private int pontuacao = 0;
    private boolean jogando;
    private String nome;
    private Flor florEscolhida;
    private String juniorOuSenior;

    public String getJuniorOuSenior() {
        return juniorOuSenior;
    }

    public void setJuniorOuSenior(String juniorOuSenior) {
        this.juniorOuSenior = juniorOuSenior;
    }
    
    

    public Flor getFlorEscolhida() {
        return florEscolhida;
    }

    public void setFlorEscolhida(Flor florEscolhida) {
        this.florEscolhida = florEscolhida;
    }
    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

    public Flor[][] getFlores() {
        return flores;
    }

    public void setFlores(Flor[][] flores) {
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

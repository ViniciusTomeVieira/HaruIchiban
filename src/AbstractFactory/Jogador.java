/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import Visitor.Visitor;
import java.util.ArrayList;
import java.util.List;
import decorator.flores.Flor;

/**
 *
 * @author Vinicius Tome Vieira e Adroan Heinen
 * @since 01/05/2019
 * @version 1.0
 */
public abstract class Jogador {

    private Flor[][] flores = new Flor[2][4];
    private String corDaFlor;
    private List<Flor> mao = new ArrayList<>();
    private int pontuacao = 0;
    private String nome;
    private Flor florEscolhida;
    private String juniorSenior;

    public Jogador(String corDaFlor, String nome, Flor florEscolhida, Flor[][] flores, List<Flor> mao, String juniorSenior) {
        this.corDaFlor = corDaFlor;
        this.nome = nome;
        this.florEscolhida = florEscolhida;
        this.flores = flores;
        this.mao = mao;
        this.juniorSenior = juniorSenior;
    }

    public String getJuniorSenior() {
        return juniorSenior;
    }

    public void setJuniorSenior(String juniorSenior) {
        this.juniorSenior = juniorSenior;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Flor getFlorEscolhida() {
        return florEscolhida;
    }

    public void setFlorEscolhida(Flor florEscolhida) {
        this.florEscolhida = florEscolhida;
    }

    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Observer.Observador;
import javax.swing.Icon;
import model.Flor;
import model.Jogador;
import model.Peca;

/**
 *
 * @author vinny
 */
public interface GerenciadorJogo {
    
    void inicializarTabuleiro() throws Exception;
    Flor[][] inicializarFlores(Flor[][] floresJogador,Jogador jogador) throws Exception;
    void addObservador(Observador obs);
    Icon getPeca(int col, int row) throws Exception;
    Icon getFlor(int col, int row) throws Exception;
    public Jogador getJogador1();     
    public void setJogador1(Jogador jogador1) ;    
    public Jogador getJogador2();  
    public void setJogador2(Jogador jogador2);
    public void fluxoJogo();
    public void selecionarCores();
    public void setCorDasFlores(int corDasFlores);
    public int getCorDasFlores();
    public String getEstadoJogo();
    public void setEstadoJogo(String estadoJogo);
    public int getJogadorDaVez();
    public void setJogadorDaVez(int jogadorDaVez);
    public void escolherFlores();
    public Peca getFlorEscolhidaDeck();
    public void setFlorEscolhidaDeck(Flor florEscolhidaDeck);
    public Peca getFlorEscolhidaMao();
    public void setFlorEscolhidaMao(Flor florEscolhidaMao);
    public Flor[][] getFlorDaVez();
    public void setFlorDaVez(Flor[][] florDaVez);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Icon;
import model.Agua;
import model.FlorRosa;
import model.NenufarClaro;
import model.NenufarEscuro;
import model.Peca;
import model.SapoAmarelo;
import model.SapoRosa;

/**
 *
 * @author vinny
 */
public class GerenciadorJogoImpl implements GerenciadorJogo {
    
    private Peca[][] tabuleiro;
    private Peca[][] floresJog1;
    private Peca[][] floresJog2;
    private List<Observador> observadores = new ArrayList<>();
    private String estadoJogo; // SelecionarCor / EscolherFlores /   JogarFlor    /  JuniorEscuro    /    SeniorEscolhe  /   JuniorMovePeças /   SeniorEscolheEscuro     
                               //    Jogador1   / Jogador 1 e 2  / Jogador 1 e 2  /  JogadorJunior   /    Jogador Senior /   JogadorJunior   /   Jogador Senior

    @Override
    public void inicializarTabuleiro() throws Exception {
        tabuleiro = new Peca[5][5];
		tabuleiro[0][0] = new NenufarClaro();
		tabuleiro[0][1] = new Agua();
		tabuleiro[0][2] = new NenufarClaro();
		tabuleiro[0][3] = new Agua();
		tabuleiro[0][4] = new NenufarClaro();
		
		tabuleiro[1][0] = new Agua();
                tabuleiro[1][1] = new SapoRosa();
		tabuleiro[1][2] = new NenufarClaro();
		tabuleiro[1][3] = new NenufarEscuro(); // Aleatorio?
		tabuleiro[1][4] = new Agua();
		

		tabuleiro[2][0] = new NenufarClaro();
		tabuleiro[2][1] = new NenufarClaro();
		tabuleiro[2][2] = new Agua();
		tabuleiro[2][3] = new SapoAmarelo();
		tabuleiro[2][4] = new NenufarClaro();
                
                tabuleiro[3][0] = new Agua();
		tabuleiro[3][1] = new NenufarClaro();
		tabuleiro[3][2] = new NenufarClaro();
		tabuleiro[3][3] = new NenufarClaro();
		tabuleiro[3][4] = new Agua();
                
                tabuleiro[4][0] = new NenufarClaro();
		tabuleiro[4][1] = new Agua();
		tabuleiro[4][2] = new NenufarClaro();
		tabuleiro[4][3] = new Agua();
		tabuleiro[4][4] = new NenufarClaro();
    }
    @Override
    public void inicializarFlores(Peca[][] floresJogador) throws Exception {
        floresJogador = new Peca[2][4];
        Random valor = new Random();
        List<String> numerosSorteados = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                while (floresJogador[i][j] == null) {
                    int numero = valor.nextInt(8);
                    if (numerosSorteados.contains(numero + "")) {
                        System.out.println("Ja tem esse numero porra");
                    } else {
                        floresJogador[i][j] = new FlorRosa(numero + 1);
                        numerosSorteados.add(numero + "");
                        System.out.println("Flor inserida nessa porra");
                        System.out.println("Numero da flor: " + (numero+1));
                    }
                }

            }
        }

    }

    @Override
    public void addObservador(Observador obs) {
        observadores.add(obs);
    }

    @Override
    public Icon getPeca(int coluna, int linha) throws Exception {
        if(tabuleiro[coluna][linha] != null){
            return tabuleiro[coluna][linha].getImagem();
        }
        return null;
    }

    @Override
    public Icon getFlor(int coluna, int linha, Peca[][] floresJogador) throws Exception {
        if(floresJogador[coluna][linha] != null){
            return floresJogador[coluna][linha].getImagem();
        }
        return null;
    }

    
    
}

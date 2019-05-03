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
    private Peca[][] flores;
    private List<Observador> observadores = new ArrayList<>();

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
    public void inicializarFlores() throws Exception {
        flores = new Peca[4][2];
        Random valor = new Random();
        List<String> numerosSorteados = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; i < 2; i++) {
                while (flores[i][j] == null) {
                    int numero = valor.nextInt(8);
                    if (numerosSorteados.contains(numero + "")) {

                    } else {
                        flores[i][j] = new FlorRosa(numero + 1);
                        numerosSorteados.add(numero + "");
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
    public Icon getFlor(int coluna, int linha) throws Exception {
        if(flores[coluna][linha] != null){
            return flores[coluna][linha].getImagem();
        }
        return null;
    }

    
    
}

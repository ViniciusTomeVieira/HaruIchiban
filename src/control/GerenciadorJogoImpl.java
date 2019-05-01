/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import model.Agua;
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
    
}

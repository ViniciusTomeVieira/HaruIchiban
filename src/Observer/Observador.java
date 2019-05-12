package Observer;

public interface Observador {

	void mudouTabuleiro();

	void iniciouJogo();

	void fimDeJogo(String msgErro);
        void notificarFlorEscolhida();

    public void notificarJogadorDaVezAlterado();

    public void notificarJuniorSenior();

    public void notificarTabuleiroAlterado();
}

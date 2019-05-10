package view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static java.awt.BorderLayout.EAST;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import control.GerenciadorJogo;
import control.GerenciadorJogoImpl;
import Observer.Observador;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonListener;


public class HaruIchiban extends JFrame implements Observador, ActionListener {

    private static final long serialVersionUID = 1L;

    //Botoes mao do jogador        
    private JButton jbCarta1;
    private JButton jbCarta2;
    private JButton jbCarta3;
    private JButton jbCoachar;
    
    //Menu superior
    private JMenuBar menuBarSuperior;
    private JMenu menuMenu;
    private JMenuItem miNovo, miSair;
    
    //Mensagens no canto superior esquerdo
    private JTextArea jtaMensagem;

    //Textos mao do jogador
    private JTextArea jtaCarta1;
    private JTextArea jtaCarta2;
    private JTextArea jtaCarta3;

    //Layouts
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    
    //Mecanica do jogo
    private GerenciadorJogo gerenciador;
    
    //Tabelas
    private JTable Tbtabuleiro;
    private JTable TbFlores;

    

    // Modelo de tabela visual do tabuleiro
    class HeroiTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public int getRowCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return gerenciador.getPeca(col, row);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }
      // Renderizador de celulas do tabuleiro
      class HeroiRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }
    // Modelo de tabela visual das cartas do jogador
    class FloresTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                
                if(gerenciador.getFlor(col, row) == null){
                    System.out.println("Ta nulo essa caralhas");
                }
                return gerenciador.getFlor(col, row);
            } catch (Exception e) {               
                return null;
            }
        }

    }
    
  // Renderizador de celulas do tabuleiro  
  class FloresRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }    

    //Construtor da view
    public HaruIchiban() throws Exception {
        
        //Inicia gerenciador e faz algumas operações
        this.gerenciador = GerenciadorJogoImpl.getInstance();
        this.gerenciador.inicializarTabuleiro();
        int opcao = 2;
        gerenciador.setCorDasFlores(opcao);
        gerenciador.fluxoJogo();
        gerenciador.setJogadorDaVez(1);        
        gerenciador.getJogador1().setFlores(this.gerenciador.inicializarFlores(gerenciador.getJogador1().getFlores(),gerenciador.getJogador1()));
        gerenciador.getJogador2().setFlores(this.gerenciador.inicializarFlores(gerenciador.getJogador2().getFlores(),gerenciador.getJogador2()));
        this.gerenciador.addObservador(this);
        this.gerenciador.setFlorDaVez(gerenciador.getJogador2().getFlores());
        


        
        //Configura a view
        setTitle("HaruIchiban");
        setBounds(200,200,900,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

        //Inicia os componentes da tela
        
        
        //Selecionar Cores / int opcao é má prática, mudar depois
        //int opcao = JOptionPane.showConfirmDialog(getParent(), "chama no reskein", "Xesq", 1);
        
        //Testes...

        
        initComponents();
        pack();

    }

    //Inicia todos os componentes da interface
    private void initComponents() { 
        
        //Inicia e adiciona os menus
        menuBarSuperior = new JMenuBar();
        menuMenu = new JMenu("Menu");
        miNovo = new JMenuItem("Novo jogo"); miSair = new JMenuItem("Sair");
        miSair.addActionListener(this);miNovo.addActionListener(this);
        menuMenu.add(miNovo); menuMenu.add(miSair);
        menuBarSuperior.add(menuMenu);
        setJMenuBar(menuBarSuperior);
        
        
        //Inicia os componentes de layout
        constraints = new GridBagConstraints();
        layout = new GridBagLayout();
        
        
        // criar o tabuleiro e seus componentes
        Tbtabuleiro = new JTable();
        Tbtabuleiro.setModel(new HeroiTableModel());
        for (int x = 0; x < Tbtabuleiro.getColumnModel().getColumnCount(); x++) {
            Tbtabuleiro.getColumnModel().getColumn(x).setWidth(100);
            Tbtabuleiro.getColumnModel().getColumn(x).setMinWidth(100);
            Tbtabuleiro.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        Tbtabuleiro.setRowHeight(100);
        Tbtabuleiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Tbtabuleiro.setShowGrid(false);
        Tbtabuleiro.setIntercellSpacing(new Dimension(0, 0));
        Tbtabuleiro.setDefaultRenderer(Object.class, new HeroiRenderer());

        Tbtabuleiro.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicou");
                
            }
            
        }); 

        //Adiciona o tabuleiro no centro da tela
        add("Center",Tbtabuleiro);

        

        

        ActionListener cartaClicadaAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    //controle.setTipoHeroi(event.getActionCommand());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }
        };
        //cartaClicadaAction.actionPerformed(new ActionEvent(jrMontanha, ActionEvent.ACTION_PERFORMED, jrMontanha.getActionCommand()));


        // criar mão do jogador
        
        //jpMao.setLayout(new GridLayout(3,2));

       
        
        
        // criar os botoes de radio / ToDo: criar a parte das cartas do usuario
        JPanel jpMensagem = new JPanel();
        jpMensagem.setLayout(layout);
        jtaMensagem = new JTextArea(2, 2);
        jtaMensagem.setText("Xesquedeleeeeeeeeeeeeeeeeeeeee");
        jtaMensagem.setEditable(false);
        jtaMensagem.setLineWrap(true);
        jtaMensagem.setFont(new Font("Calibri", 1, 20));
        
        
        //Adiciona a mensagem no panel da mensagem
        constraints.gridx = 1;
        constraints.gridy = 0;
        jpMensagem.add(jtaMensagem, constraints);

        
        
        //Inicia o panel da esquerda
        JPanel panelEsquerda = new JPanel();
        panelEsquerda.setLayout(layout);


        
        //Criação dos botões da mão do jogador
        jbCarta1 = new JButton();
        jbCarta1.setIcon(new ImageIcon("imagens/florRosa.png"));
        jbCarta1.setMargin(new Insets(10,0,10,0));
        jbCarta2 = new JButton();
        jbCarta2.setIcon(new ImageIcon("imagens/florAmarela.png"));
        jbCarta2.setMargin(new Insets(10,0,10,0));
        jbCarta3 = new JButton();
        jbCarta3.setIcon(new ImageIcon("imagens/florRosa.png"));
        jbCarta3.setMargin(new Insets(10,0,10,0));

        //Criação das labels da mão do jogador
        jtaCarta1 = new JTextArea();
        jtaCarta1.setText("1");
        jtaCarta1.setFont(new Font("Calibri", 1, 20));
        jtaCarta1.setMargin(new Insets(10,10,10,10));
        jtaCarta2 = new JTextArea();
        jtaCarta2.setText("2");
        jtaCarta2.setFont(new Font("Calibri", 1, 20));
        jtaCarta3 = new JTextArea();
        jtaCarta3.setText("3");
        jtaCarta3.setFont(new Font("Calibri", 1, 20));



        
        //Cria a panel da mao do jogador
        JPanel jpMao = new JPanel();
        jpMao.setLayout(layout);

        // Faz a adição das cartas na mão do jogador
        constraints.gridx = 0;
        constraints.gridy = 0;
        jpMao.add(jbCarta1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        jpMao.add(jtaCarta1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 10;
        jpMao.add(jbCarta2, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 10;
        jpMao.add(jtaCarta2, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 10;
        jpMao.add(jbCarta3, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 10;
        jpMao.add(jtaCarta3, constraints);
        constraints.weighty = 0;

        //Configuração visual dos paineis
        jpMao.setBackground(Color.white);
        panelEsquerda.setBackground(Color.white);
        

        //Criação da panel da direita
        JPanel panelDireita = new JPanel();
        panelDireita.setLayout(layout);

        //Criação do placar
        JPanel jpPlacar = new JPanel();
        JLabel jlPlacar = new JLabel();
        jlPlacar.setText("2x2");
        jlPlacar.setFont(new Font("Calibri", 1, 20));

        //Adiciona o placar no painel do placar
        jpPlacar.add(jlPlacar);
        
        //Testes...
        
        

        //Table de flores do lado direito
        TbFlores = new JTable();
        TbFlores.setModel(new FloresTableModel());
        for (int x = 0; x < TbFlores.getColumnModel().getColumnCount(); x++) {
            TbFlores.getColumnModel().getColumn(x).setWidth(100);
            TbFlores.getColumnModel().getColumn(x).setMinWidth(100);
            TbFlores.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        TbFlores.setRowHeight(100);
        TbFlores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TbFlores.setShowGrid(true);
        TbFlores.setIntercellSpacing(new Dimension(0, 0));
        TbFlores.setDefaultRenderer(Object.class, new FloresRenderer());
        
        TbFlores.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicou");
                
            }
            
        }); 
        
        
        jbCoachar = new JButton("Coachar");
        
        //Adiciona o painel placar, tabela de flores e o botao coachar no painel da direita
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 10;
        panelDireita.add(jpPlacar,constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0;
        panelDireita.add(TbFlores,constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 10;
        panelDireita.add(jbCoachar,constraints); // Botao coachar
        
        
        //Adiciona o painel de mensagem e o painel da mao no painel da esquerda
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelEsquerda.add(jpMensagem, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panelEsquerda.add(jpMao, constraints);
        
        
        //Adiciona o painel da esquerda na esquerda da tela
        add("West",panelEsquerda);
        //Adiciona o painel da direita na direita da tela
        add("East",panelDireita);
        

    }

    
    //Metodo MAIN
    public static void main(String[] args) {
        try {
            HaruIchiban d = new HaruIchiban();
            d.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Metodo executado na primeira execucao do jogo
    @Override
    public void iniciouJogo() {
//        jbCriar.setEnabled(false);
//        jrMontanha.setEnabled(false);
//        jrAgua.setEnabled(false);
    }

    //Atualizar o tabuleiro principal
    @Override
    public void mudouTabuleiro() {
        Tbtabuleiro.repaint();
    }
    //Faz as ações do menu superior
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == miNovo){
            //Command
        }
        if(e.getSource() == miSair){
            System.exit(0);
        }
    }
    //Metodo executado ao final do jogo
    @Override
    public void fimDeJogo(String msgErro) {
        JOptionPane.showMessageDialog(null, msgErro);
        System.exit(0);
    }

}

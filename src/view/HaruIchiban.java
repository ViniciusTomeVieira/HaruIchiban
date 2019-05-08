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
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicButtonListener;

public class HaruIchiban extends JFrame implements Observador {

    private static final long serialVersionUID = 1L;

    //Botoes mao do jogador        
    private JButton jbCarta1;
    private JButton jbCarta2;
    private JButton jbCarta3;
    
    //Mensagens no canto superior esquerdo
    private JTextArea jtaMensagem;

    //Textos mao do jogador
    private JLabel jlCarta1;
    private JLabel jlCarta2;
    private JLabel jlCarta3;

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
                return gerenciador.getFlor(col, row);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
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
        this.gerenciador = GerenciadorJogoImpl.getInstance();
        this.gerenciador.inicializarTabuleiro();
        this.gerenciador.inicializarFlores(gerenciador.getJogador1().getFlores());
        this.gerenciador.inicializarFlores(gerenciador.getJogador2().getFlores());
        this.gerenciador.addObservador(this);

        setTitle("HaruIchiban");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        
        //Selecionar Cores / int opcao é má prática, mudar depois
        int opcao = JOptionPane.showConfirmDialog(getParent(), "chama no reskein", "Xesq", 1);
        gerenciador.setCorDasFlores(opcao);
        gerenciador.fluxoJogo();
        gerenciador.setJogadorDaVez(1);
        pack();

    }

    //Inicia todos os componentes da interface
    private void initComponents() {                             
        // criar o tabuleiro e seus componentes
        Tbtabuleiro = new JTable();
        Tbtabuleiro.setModel(new HeroiTableModel());
        for (int x = 0; x < Tbtabuleiro.getColumnModel().getColumnCount(); x++) {
            Tbtabuleiro.getColumnModel().getColumn(x).setWidth(200);
            Tbtabuleiro.getColumnModel().getColumn(x).setMinWidth(200);
            Tbtabuleiro.getColumnModel().getColumn(x).setMaxWidth(200);
        }
        Tbtabuleiro.setRowHeight(200);
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

        add(Tbtabuleiro, CENTER);

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2, 1, 0, 0));

        // criar os botoes de radio / ToDo: criar a parte das cartas do usuario
        JPanel jpMensagem = new JPanel();
        jtaMensagem = new JTextArea(2, 2);
        jtaMensagem.setText("Xesquedeleeeeeeeeeeeeeeeeeeeee");
        jtaMensagem.setEditable(false);
        jtaMensagem.setLineWrap(true);
        jtaMensagem.setFont(new Font("Calibri", 1, 20));
        jpMensagem.add(jtaMensagem, CENTER);

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

        jp.add(jpMensagem);

        // criar mão do jogador
        JPanel jpMao = new JPanel();
        //jpMao.setLayout(new GridLayout(3,2));

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();

        // layout.setConstraints(jpMao, constraints);
        jpMao.setLayout(layout);

        //Criação dos botões
        jbCarta1 = new JButton();
        jbCarta1.setIcon(new ImageIcon("imagens/florRosa.png"));
        jbCarta2 = new JButton();
        jbCarta2.setIcon(new ImageIcon("imagens/florAmarela.png"));
        jbCarta3 = new JButton();
        jbCarta3.setIcon(new ImageIcon("imagens/florRosa.png"));

        //Criação das labels
        jlCarta1 = new JLabel();
        jlCarta1.setText("1");
        jlCarta2 = new JLabel();
        jlCarta2.setText("2");
        jlCarta3 = new JLabel();
        jlCarta3.setText("3");

//        jbCriar = new JButton("Criar");
//        jbCriar.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                try {
//                    //controle.run();
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e.toString());
//                }
//            }
//        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        jpMao.add(jbCarta1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        jpMao.add(jbCarta2, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta2, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        jpMao.add(jbCarta3, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 100;
        jpMao.add(jlCarta3, constraints);

        jpMao.setBackground(Color.white);
        jpMao.setSize(300, getContentPane().getHeight());
        jp.setBackground(Color.white);
        jp.add(jpMao);
        add(jp, WEST);

        //Criação da panel da direita
        JPanel jpDireita = new JPanel();
        jpDireita.setLayout(new GridLayout(3, 1, 0, 0));

        //Criação do placar
        JPanel jpPlacar = new JPanel();
        JLabel jlPlacar = new JLabel();
        jlPlacar.setText("2x2");

        jpPlacar.add(jlPlacar);
        jpDireita.add(jpPlacar);
        
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
        jpDireita.add(TbFlores);

        add(jpDireita,EAST);

    }

    
    //Metodo MAIN
    public static void main(String[] args) {
        try {
            HaruIchiban d = new HaruIchiban();
            d.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
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

    //Metodo executado ao final do jogo
    @Override
    public void fimDeJogo(String msgErro) {
        JOptionPane.showMessageDialog(null, msgErro);
        System.exit(0);
    }

}

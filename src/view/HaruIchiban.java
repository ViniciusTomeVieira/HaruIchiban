package view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

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
import control.Observador;


public class HaruIchiban extends JFrame implements Observador {

	private static final long serialVersionUID = 1L;
	
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
	
	class HeroiRenderer extends DefaultTableCellRenderer {
	
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			setIcon((ImageIcon) value);
			
			return this;
		}

	}

	private GerenciadorJogo gerenciador;
	private JTable tabuleiro;
	
	public HaruIchiban() throws Exception {
		this.gerenciador = new GerenciadorJogoImpl();
		this.gerenciador.inicializarTabuleiro();
		this.gerenciador.addObservador(this);
		
		setTitle("HaruIchiban");
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		initComponents();
		pack();
	
	}

	private void initComponents() {

		// criar o tabuleiro e seus componentes
		tabuleiro = new JTable();
		tabuleiro.setModel(new HeroiTableModel());
		for (int x=0;x<tabuleiro.getColumnModel().getColumnCount();x++) {
			tabuleiro.getColumnModel().getColumn(x).setWidth(200);
			tabuleiro.getColumnModel().getColumn(x).setMinWidth(200);
			tabuleiro.getColumnModel().getColumn(x).setMaxWidth(200);
		}
		tabuleiro.setRowHeight(200);
		tabuleiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabuleiro.setShowGrid(false);
		tabuleiro.setIntercellSpacing(new Dimension(0, 0));
		tabuleiro.setDefaultRenderer(Object.class, new HeroiRenderer());

		tabuleiro.addKeyListener(new KeyAdapter(){
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					//gerenciador.pressTecla( e.getKeyCode() );
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.toString());
				}
			}
			
		});
		
		
		add(tabuleiro, CENTER);

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		
		// criar os botoes de radio / ToDo: criar a parte das cartas do usuario
		JPanel jrGrupo = new JPanel();

		ButtonGroup bgTipoHeroi = new ButtonGroup();
		
		jrMontanha = new JRadioButton("Montanha");
		jrMontanha.setSelected(true);
		jrMontanha.setActionCommand("Montanha");
		jrGrupo.add(jrMontanha);
		bgTipoHeroi.add(jrMontanha);
		
		jrAgua = new JRadioButton("\u00C1gua");
		jrGrupo.add(jrAgua);
		jrAgua.setActionCommand("Agua");
		bgTipoHeroi.add(jrAgua);
		
		ActionListener radioAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					//controle.setTipoHeroi(event.getActionCommand());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
			}
		};
		jrMontanha.addActionListener(radioAction);
		jrAgua.addActionListener(radioAction);
		radioAction.actionPerformed(new ActionEvent(jrMontanha, ActionEvent.ACTION_PERFORMED, jrMontanha.getActionCommand()));
		
		jp.add(jrGrupo, WEST);
		
		// botao criar
		JPanel jpCriar = new JPanel();
		jbCriar = new JButton("Criar");
		jbCriar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					//controle.run();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
			}
		});
		
		jpCriar.add(jbCriar);
		jp.add(jpCriar, CENTER);
		
		add(jp, SOUTH);
		
		
	}

	private JRadioButton jrMontanha;
	private JRadioButton jrAgua;
	private JButton jbCriar;
	
	public static void main(String[] args) {
		try {
			HaruIchiban d = new HaruIchiban();
			d.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}

	}

	@Override
	public void iniciouJogo() {
		jbCriar.setEnabled(false);
		jrMontanha.setEnabled(false);
		jrAgua.setEnabled(false);
	}

	@Override
	public void mudouTabuleiro() {
		tabuleiro.repaint();
	}

	@Override
	public void fimDeJogo(String msgErro) {
		JOptionPane.showMessageDialog(null, msgErro);
		System.exit(0);
	}

}

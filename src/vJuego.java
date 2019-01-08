import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class vJuego extends JFrame {

	private static final long serialVersionUID = 1L;

	public Solitario solitario;
	
	private JPanel contentPane;
	public Stats stats;
	public static vJuego frame;
	public JMenuItem mntmGuardar;
	public JMenuItem mntmGuardarComo;
	public JMenuItem mntmEstadisticas;
	public JMenuItem mntmFicheroDeEstadisticas;
	public JMenuItem mntmDeshacer;
	public JMenuItem mntmHacer;
	public JMenuItem mntmResolver;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new vJuego();
					JFrame.setDefaultLookAndFeelDecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public vJuego() {
		
		solitario = new Solitario();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 300, 100);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(-1);
			}
		});
		
		JMenu mnNuevo = new JMenu("Nuevo");
		mnArchivo.add(mnNuevo);
		
		JMenuItem mntmClasico = new JMenuItem("Clasico");
		mntmClasico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego = new SolitarioClasico("BarajaF");
				setContentPane(new pClasico(solitario.juego,frame));
				pack();
			}
		});
		mnNuevo.add(mntmClasico);
		
		JMenuItem mntmSaltos = new JMenuItem("Saltos");
		mntmSaltos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego = new SolitarioSaltos("BarajaE");
				setContentPane(new pSaltos(solitario.juego,frame)); 
				pack();
			}
		});
		mnNuevo.add(mntmSaltos);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego=solitario.cargarSolitario();
				if(solitario.Tipo.equals("Saltos"))
					setContentPane(new pSaltos(solitario.juego,frame));
				if(solitario.Tipo.equals("Clasico"))
					setContentPane(new pClasico(solitario.juego,frame));
				pack();
			}
		});
		mnArchivo.add(mntmAbrir);
		
		mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.guardarSolitario(0);
			}
		});
		mntmGuardar.setEnabled(false);;
		mnArchivo.add(mntmGuardar);
		
		mntmGuardarComo = new JMenuItem("Guardar como...");
		mntmGuardarComo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			solitario.juego.guardarSolitario(1);
			}
		});
		mntmGuardarComo.setEnabled(false);
		mnArchivo.add(mntmGuardarComo);
		mnArchivo.add(mntmSalir);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		mntmDeshacer = new JMenuItem("Deshacer");
		mntmDeshacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice-1), 1);
			}
		});
		mntmDeshacer.setEnabled(false);
		mnEditar.add(mntmDeshacer);
		
		mntmHacer = new JMenuItem("Hacer");
		mntmHacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice), 0);
			}
		});
		mntmHacer.setEnabled(false);
		mnEditar.add(mntmHacer);
		
		mntmResolver = new JMenuItem("Resolver");
		mntmResolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.resolverSolitario(1);
			}
		});
		mntmResolver.setEnabled(false);
		mnEditar.add(mntmResolver);
		
		JMenu mnHistorial = new JMenu("Historial");
		menuBar.add(mnHistorial);
		
		mntmEstadisticas = new JMenuItem("Estadisticas");
		mntmEstadisticas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stats==null){
					stats = new Stats();
					stats.readFichero(stats.file);
					stats.dialogStats.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					stats.dialogStats.setVisible(true);
				} else {
					stats.readFichero(stats.file);
					stats.dialogStats.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					stats.dialogStats.setVisible(true);
				}
			}
		});
		mntmEstadisticas.setEnabled(false);
		mnHistorial.add(mntmEstadisticas);
		
		mntmFicheroDeEstadisticas = new JMenuItem("Fichero de estadisticas");
		mntmFicheroDeEstadisticas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(stats!=null){
					stats.setFile();
					stats.readFichero(stats.file);
				} else {
					stats=new Stats();
					stats.readFichero(stats.file);
					mntmEstadisticas.setEnabled(true);
					stats.dialogStats.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					stats.dialogStats.setVisible(true);
				}
			}
		});
		mnHistorial.add(mntmFicheroDeEstadisticas);
		
		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mntmAyuda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JDialog ayuda = new dAyuda();
				ayuda.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				ayuda.setVisible(true);
			}
		});
		menuBar.add(mntmAyuda);

	}

	public Color getThisBackground() {
		return getBackground();
	}
	public void setThisBackground(Color background) {
		setBackground(background);
	}
}

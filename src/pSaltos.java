import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class pSaltos extends pJuego {
	
	public Solitario solitario;
	public JLabel lblNodescu;
	public pMontonS[] Montones = new pMontonS[52];

	public String ref;
	public int selector=0;
	public int seleccion;
	public int indiceD;
	public int indiceO;
	public int tipoD;
	public int tipoO;
	public int numCartas;
	public vJuego vjuego;
	public int movimientos;


	private static final long serialVersionUID = 1L;
	MigLayout LayForGame = new MigLayout("", "[10.00][38.00][38.00]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow]"
			+ "[10.00]", "[10.00][430.00]");

	/**
	 * Create the panel.
	 */
	public pSaltos(Solitario solitarioSaltos, vJuego vjuego) {
		this.vjuego=vjuego;
		this.solitario=solitarioSaltos;
		this.movimientos=0;
		setLayout(new BorderLayout(0, 0));
		panelbtns = new JPanel();
		scrollpane = new JScrollPane();
		add(scrollpane, BorderLayout.CENTER);
		interior=new JPanel();
		interior.setLayout(new MigLayout("", "[10.00][38.00][38.00,grow][grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][10.00]", "[10.00][430.00,grow][][10.00][20.00,grow]"));
		scrollpane.setLayout(new ScrollPaneLayout());
		scrollpane.setSize(1000,400);
		setPreferredSize(new Dimension(1010,410));
		interior.setBackground(new Color(0,155,0));
		
		panelbtns = new JPanel();
		panelbtns.setLayout(new GridLayout(1, 0, 0, 0));
		btnSig = new JButton("Sig >");
		btnSig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice+1), 0);
			}
		});
		btnAnt = new JButton("< Ant");
		btnAnt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice), 1);
			}
		});
		btnAnt.setEnabled(false);
		btnSig.setEnabled(false);
		
		lblMvmnt = new JLabel( solitario.indice + "/" + solitario.loMasLejos);
		lblMvmnt.setVerticalAlignment(SwingConstants.TOP);
		lblMvmnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblMvmnt.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblMvmnt.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelbtns.add(btnAnt);
		panelbtns.add(lblMvmnt);
		panelbtns.add(btnSig);

		lblNodescu=new JLabel();

		vjuego.mntmGuardar.setEnabled(true);
		vjuego.mntmGuardarComo.setEnabled(true);
		vjuego.mntmResolver.setEnabled(true);
		
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
        add(panelbtns, BorderLayout.SOUTH);	
        
		refrescarSolitario();
	}

	public void refrescarSolitario(){
		
		lblMvmnt.setText(solitario.indice + "/" + solitario.loMasLejos);
		
		if(solitario.noDescubiertas.cartasMonton.size() > 9){
			lblNodescu=solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1);
			lblNodescu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.noDescubiertas.cartasMonton.size() > 0){
						tipoO=0;
						indiceO=0;
						tipoD=1;
						indiceD=solitario.montones.size();
						ref=solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1).getRef();
						numCartas=1;
						hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						}
					}
				}
			});
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon;
			icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco.png");
			Vacio.setIcon(icon);
			lblNodescu=Vacio;
		}
		
		for(int i=0;i<solitario.montones.size();i++){
			Montones[i]=new pMontonS(solitario.montones.get(i),1,i, this);
		}

		interior.removeAll();
		interior.revalidate();

		
		for(int i=0; i<solitario.montones.size();i++){
			interior.add(Montones[i], "cell " + (i+2) + " 1" );
		}
		interior.add(lblNodescu, "cell 1 1");
		
		scrollpane.setViewportView(interior);
		if(solitario.indice>0){
			vJuego.frame.mntmDeshacer.setEnabled(true);
			btnAnt.setEnabled(true);
		}else{
			vJuego.frame.mntmDeshacer.setEnabled(false);
			btnAnt.setEnabled(false);
		}
		if(solitario.indice<solitario.loMasLejos){
			vJuego.frame.mntmHacer.setEnabled(true);
			btnSig.setEnabled(true);
		} else {
			vJuego.frame.mntmHacer.setEnabled(false);
			btnSig.setEnabled(false);
		}
	}
	
	public void hacerMvto(Mvto mvto) {
		if(movimientos==0){
			if(vjuego.stats==null){
				vjuego.stats=new Stats();
				vjuego.stats.intentosC++;
				vjuego.stats.writeFichero(vjuego.stats.file, 0);
			}
			movimientos++;
		}else{
			movimientos++;
		}
		if(solitario.mvtoCorrecto(mvto));
			solitario.hacerMovimiento(mvto, 0);
		refrescarSolitario();
		
		if(solitario.jugadasValidas.size()==0){
			vjuego.stats.exitosS++;
			JOptionPane.showMessageDialog(vjuego.frame,"Te han quedado" + solitario.montones.size() + " montones");
			if(vjuego.stats.file==null){
				vjuego.stats.writeFichero(vjuego.stats.file, 1);
			}else{
				vjuego.stats.writeFichero(vjuego.stats.file,0);
			}
		}
		
	}
}

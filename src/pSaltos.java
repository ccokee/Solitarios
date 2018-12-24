import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class pSaltos extends pJuego {
	
	public Solitario solitario;
	public JLabel lblNodescu;
	public pMontonS[] Montones = new pMontonS[52];
	public JLabel lblMvmnt;
	public JButton btnAnt,btnSig;
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
			+ "[10.00]", "[10.00][430.00]");

	/**
	 * Create the panel.
	 */
	public pSaltos(Solitario solitarioSaltos, vJuego vjuego) {
		this.vjuego=vjuego;
		this.solitario=solitarioSaltos;
		this.movimientos=0;
		setLayout(new MigLayout("", "[10.00][38.00][38.00,grow][grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][10.00]", "[10.00][430.00,grow][10.00][20.00,grow]"));
		setBackground(new Color(0,155,0));
		
		for(int i=0; i<solitario.montones.size();i++){
			add(Montones[i], "cell " + (i+2) + " 1" );
		}
		
		lblNodescu=new JLabel();
		add(lblNodescu, "cell 1 1");
		
		JPanel panelbtns = new JPanel();
		add(panelbtns, "cell 0 3 44 1,grow");
		panelbtns.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnAnt = new JButton("< Ant");
		panelbtns.add(btnAnt);
		
		lblMvmnt = new JLabel( solitario.indice + "/" + solitario.loMasLejos);
		panelbtns.add(lblMvmnt);
		
		btnSig = new JButton("Sig >");
		panelbtns.add(btnSig);
		
		lblNodescu=new JLabel();
		
		for(int i=0; i<solitario.montones.size();i++){
			add(Montones[i], "cell " + (i+2) +" 1");
		}
		btnAnt.setEnabled(false);
		btnSig.setEnabled(false);
		vjuego.mntmGuardar.setEnabled(true);
		vjuego.mntmGuardarComo.setEnabled(true);
		vjuego.mntmResolver.setEnabled(true);
		
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
			icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco");
			Vacio.setIcon(icon);
			lblNodescu=Vacio;
		}
		
		for(int i=0;i<solitario.montones.size();i++){
			Montones[i]=new pMontonS(solitario.montones.get(i),1,i, this);
		}
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
			vjuego.stats.intentosS++;
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

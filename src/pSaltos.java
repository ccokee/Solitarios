import java.awt.Color;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class pSaltos extends pJuego {
	
	public Solitario solitario;
	public JLabel lblNodescu;
	public pMontonS[] Montones = new pMontonS[52];
	public JLabel lblMvmnt;

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
	public pSaltos(Solitario solitarioSaltos) {
		this.solitario=solitarioSaltos;
		setBackground(new Color(0,155,0));
		setLayout(new MigLayout("", "[10.00][38.00][38.00,grow][grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][0.00,grow][10.00]", "[10.00][430.00,grow][10.00][20.00,grow]"));
		
		for(int i=0; i<solitario.montones.size();i++){
			add(Montones[i], "cell " + (i+2) + " 1" );
		}
		
		lblNodescu=new JLabel();
		add(lblNodescu, "cell 1 1");
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 3 44 1,grow");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton button = new JButton("< Ant");
		panel_1.add(button);
		
		lblMvmnt = new JLabel( solitario.indice + "/" + solitario.loMasLejos);
		panel_1.add(lblMvmnt);
		
		JButton btnSiguiente = new JButton("Sig >");
		panel_1.add(btnSiguiente);
		
		lblNodescu=new JLabel();
		
		for(int i=0; i<solitario.montones.size();i++){
			add(Montones[i], "cell " + (i+2) +" 1");
		}
	}

	public void refrescarSolitario(){
		
		lblMvmnt.setText(solitario.indice + "/" + solitario.loMasLejos);
		
		if(solitario.noDescubiertas.cartasMonton.size() > 9){
			lblNodescu=solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1);
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon;
			icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco");
			Vacio.setIcon(icon);
			lblNodescu=Vacio;
		}
		for(int i=0;i<solitario.montones.size();i++){
			Montones[i]=new pMontonS(solitario.montones.get(i),1,i);
		}
	}
}

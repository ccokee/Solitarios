import java.awt.Color;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;

public class pClasico extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Solitario solitario;
	public JLabel lblNodescu;
	public JLabel lblDescu;
	public pMontonC[] MontonesJuego = new pMontonC[7];
	public JLabel[] lblFinal = new JLabel[4];
	public JLabel lblMvmnt;
	/**
	 * Create the panel.
	 */

	
	public pClasico(Solitario solitarioClasico) {
		setSize(700,400);
		setLayout(new MigLayout("", "[10.00][50.00][50.00][20.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00][10.00,left]", "[10.00][75.00][10.00][225.00,grow][10.00][20.00,bottom]"));
		setBackground(new Color(0,155,0));
		
		this.solitario=solitarioClasico;
		
		for (int i=0;i<MontonesJuego.length;i++)
			add(MontonesJuego[i], "cell " + i + " 3");
		
		lblNodescu = new JLabel();
		add(lblNodescu, "cell 1 1");
		
		lblDescu = new JLabel();
		add(lblDescu, "cell 2 1");
		
		for(int i=0;i<lblFinal.length;i++){
			lblFinal[i] = new JLabel();
			add(lblFinal[i], "cell " + (i+8) + " 1");
		}
		
		JButton button = new JButton("< Ant");
		add(button, "cell 4 5 2 1,growx");
		
		lblMvmnt= new JLabel(solitario.indice + "/" + solitario.loMasLejos);
		lblMvmnt.setVerticalAlignment(SwingConstants.TOP);
		lblMvmnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblMvmnt.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblMvmnt.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblMvmnt, "cell 6 5");
		
		JButton btnSig = new JButton("Sig >");
		add(btnSig, "cell 7 5 2 1,growx");
		refrescarSolitario();
	}
	
	public void refrescarSolitario(){
		
		lblMvmnt= new JLabel(solitario.indice+"/"+solitario.loMasLejos);
		for(int i=0;i<lblFinal.length;i++){
			if(solitario.montonesSolucion.get(i).cartasMonton.size() > 9){
				lblFinal[i]=solitario.montonesSolucion.get(i).cartasMonton.get(solitario.montonesSolucion.get(i).cartasMonton.size()-1);
			} else {
				JLabel Vacio = new JLabel();
				ImageIcon icon;
				icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco");
				Vacio.setIcon(icon);
				lblFinal[i]=Vacio;
			}
		}
		for(int i=0;i<MontonesJuego.length;i++){
			if(solitario.montonesJuego.get(i).cartasMonton.size()>0){
				MontonesJuego[i]=new pMontonC(solitario.montonesJuego.get(i),1,i);
			} else {
				MontonesJuego[i]=new pMontonC(0,solitario.tipoBaraja);
			}
		}
		if(solitario.Descubiertas.cartasMonton.size()>0){
			lblDescu = solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1);
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon;
			icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco");
			Vacio.setIcon(icon);
			lblDescu = Vacio;
		}
		if(solitario.noDescubiertas.cartasMonton.size()>0){
			lblNodescu= solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1);
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon;
			icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco");
			Vacio.setIcon(icon);
			lblNodescu=Vacio;
		}
	}
}

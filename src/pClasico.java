import java.awt.Color;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	public String ref;
	public int selector=0;
	public int seleccion;
	public int indiceD;
	public int indiceO;
	public int tipoD;
	public int tipoO;
	public int numCartas;
	public JButton btnAnt;
	public JButton btnSig;
	public vJuego vjuego;
	public int movimientos;
	/**
	 * Create the panel.
	 */

	
	public pClasico(Solitario solitarioClasico,vJuego vjuego) {
		this.vjuego=vjuego;
		movimientos=0;
		setSize(850,450);
		setLayout(new MigLayout("", "[10.00][70.00][70.00][20.00,grow][70.00][70.00][70.00][70.00][70.00][70.00][70.00][70.00][10.00,left]", "[10.00][100.00][10.00][:250.00:250.00,top][10.00][20.00,bottom]"));
		setBackground(new Color(0,155,0));
		
		this.solitario=solitarioClasico;
		btnAnt = new JButton("< Ant");
		add(btnAnt, "cell 4 5 2 1,growx");
		
		lblMvmnt= new JLabel(solitario.indice + "/" + solitario.loMasLejos, JLabel.CENTER);
		lblMvmnt.setVerticalAlignment(SwingConstants.TOP);
		lblMvmnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblMvmnt.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblMvmnt.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblMvmnt, "cell 6 5,alignx center");
		
		btnSig = new JButton("Sig >");
		add(btnSig, "cell 7 5 2 1,growx");
		btnAnt.setEnabled(false);
		btnSig.setEnabled(false);
		vjuego.mntmGuardar.setEnabled(true);
		vjuego.mntmGuardarComo.setEnabled(true);
		vjuego.mntmResolver.setEnabled(true);
		refrescarSolitario();
	}
	
	public void refrescarSolitario(){
		
		lblMvmnt= new JLabel(solitario.indice+"/"+solitario.loMasLejos, JLabel.CENTER);
		for(int i=0;i<lblFinal.length;i++){
			int cnt=i;
			if(solitario.montonesSolucion.get(i).cartasMonton.size() > 0){
				lblFinal[i]=solitario.montonesSolucion.get(i).cartasMonton.get(solitario.montonesSolucion.get(i).cartasMonton.size()-1);
				lblFinal[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.montonesSolucion.get(cnt).cartasMonton.size() > 0){
						seleccion=cnt;
						solitario.montonesSolucion.get(cnt).cartasMonton.get(solitario.montonesSolucion.get(cnt).cartasMonton.size()-1).setPos(2);
						selector=1;
						tipoO=2;
						indiceO=cnt;
						ref=solitario.montonesSolucion.get(cnt).cartasMonton.get(solitario.montonesSolucion.get(cnt).cartasMonton.size()-1).getRef();
						numCartas=1;
						}
					} else {
						selector=0;
						tipoD=1;
						indiceD=cnt;
						if(seleccion==-1){
							solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1).setPos(1);
						}
						if(seleccion>=0 && indiceO==1){
							solitario.montonesJuego.get(indiceO).cartasMonton.get(seleccion).setPos(1);
						}
						if (seleccion>=0 && indiceO==2){
							solitario.montonesSolucion.get(indiceO).cartasMonton.get(seleccion).setPos(1);
						}
						hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						
					}
				}
			});
			} else {
				JLabel Vacio = new JLabel();
				ImageIcon icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco.png");
				Vacio.setIcon(icon);
				Vacio.setVisible(true);
				Vacio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(selector==1){
							selector=0;
							tipoD=1;
							indiceD=cnt;
							numCartas=1;
							if(seleccion==-1){
								solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1).setPos(1);
							}
							hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						}
					}
				});
				lblFinal[i]=Vacio;
			}
		}
		for(int i=0;i<MontonesJuego.length;i++){
			if(solitario.montonesJuego.get(i).cartasMonton.size()>0){
				MontonesJuego[i]=new pMontonC(solitario.montonesJuego.get(i),1,i, this);
			} else {
				MontonesJuego[i]=new pMontonC(0,i,solitario.tipoBaraja, this);
			}
		}
		if(solitario.Descubiertas.cartasMonton.size()>0){
			lblDescu = solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1);
			lblDescu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.Descubiertas.cartasMonton.size() > 0){
						selector=1;
						seleccion=-1;
						tipoO=0;
						indiceO=1;
						solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1).setPos(2);
						ref=solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1).getRef();
						numCartas=1;
						}
					}
				}
			});
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco.png");
			Vacio.setIcon(icon);
			Vacio.setVisible(true);
			Vacio.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.noDescubiertas.cartasMonton.size() > 0){
						tipoO=0;
						indiceO=0;
						tipoD=0;
						indiceD=1;
						ref=solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1).getRef();
						numCartas=1;
						hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						}
					}
				}
			});
			lblDescu = Vacio;
		}
		if(solitario.noDescubiertas.cartasMonton.size()>0){
			lblNodescu= solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1);
			lblNodescu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.noDescubiertas.cartasMonton.size() > 0){
						tipoO=0;
						indiceO=0;
						tipoD=0;
						indiceD=1;
						ref=solitario.noDescubiertas.cartasMonton.get(solitario.noDescubiertas.cartasMonton.size()-1).getRef();
						numCartas=1;
						hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						}
					}
				}
			});
		}else{
			JLabel Vacio = new JLabel();
			ImageIcon icon = new ImageIcon("Images/" + solitario.tipoBaraja + "/Hueco.png");
			Vacio.setIcon(icon);
			Vacio.setVisible(true);
			/* Mouselistener por si manda reciclar las cartas
			Vacio.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(selector==0){
						if(solitario.Descubiertas.cartasMonton.size() > 0){
						Monton aux;
						aux=solitario.Descubiertas;
						solitario.Descubiertas=solitario.noDescubiertas;
						solitario.noDescubiertas=aux;
						refrescarSolitario();
						}
					}
				}
			});
			*/
			lblNodescu=Vacio;
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
		for (int i=0;i<MontonesJuego.length;i++){
			add(MontonesJuego[i], "cell " + (i+4) + " 3");
		}
		add(lblNodescu, "cell 1 1");
		add(lblDescu, "cell 2 1");
		for(int i=0;i<lblFinal.length;i++){
			add(lblFinal[i], "cell " + (i+8) + " 1");
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
		if(solitario.montonesSolicionCompletos()){
			vjuego.stats.exitosC++;
			JOptionPane.showMessageDialog(vjuego.frame, "Has ganado!!");
			if(vjuego.stats.file==null){
				vjuego.stats.writeFichero(vjuego.stats.file, 1);
			}else{
				vjuego.stats.writeFichero(vjuego.stats.file,0);
			}
		}
		if(solitario.jugadasValidas.size()==0 && solitario.noDescubiertas.cartasMonton.size()==0)
			JOptionPane.showMessageDialog(vjuego.frame, "Has perdido!!");
		/* 
		  Por si manda ampliacion reciclar cartas
		  
		if(solitario.noDescubiertas.cartasMonton.size()==0){
			vuelta++;
		}
		if(solitario.vuelta>0 && solitario.jugadasValidasDesdeVuelta==numCartasBaraja){
			JOptionPane.showMessageDialog(vjuego.frame, "Has perdido!!");
		}
		*/
	}
}

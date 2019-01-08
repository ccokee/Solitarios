import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pClasico extends pJuego {
	
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
	public vJuego vjuego;
	public int movimientos;
	/**
	 * Create the panel.
	 */

	
	public pClasico(Solitario solitarioClasico,vJuego vjuego) {
		this.vjuego=vjuego;
		movimientos=0;
		setPreferredSize(new Dimension(1000,700));
		setLayout(new BorderLayout(0, 0));
		panelbtns = new JPanel();
		scrollpane = new JScrollPane();
		scrollpane.setLayout(new ScrollPaneLayout());
		add(scrollpane, BorderLayout.CENTER);
		interior=new JPanel();
		interior.setLayout(new MigLayout("", "[10.00][70.00][70.00][20.00,grow][70.00][70.00][70.00][70.00][70.00][70.00][70.00][70.00][10.00,left]", "[10.00][100.00][10.00][:250.00:250.00,grow,top][10.00][20.00,bottom]"));
		interior.setBackground(new Color(0,155,0));
		
		this.solitario=solitarioClasico;

		lblMvmnt= new JLabel(solitario.indice + "/" + solitario.loMasLejos, JLabel.CENTER);
		lblMvmnt.setVerticalAlignment(SwingConstants.TOP);
		lblMvmnt.setHorizontalAlignment(SwingConstants.CENTER);
		lblMvmnt.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblMvmnt.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnAnt = new JButton("< Ant");
		btnAnt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice), 1);
			}
		});
		btnSig = new JButton("Sig >");
		btnSig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				solitario.juego.hacerMovimiento(solitario.juego.movimientos.get(solitario.indice+1), 0);
			}
		});
		panelbtns.add(btnAnt);
		panelbtns.add(lblMvmnt);
		panelbtns.add(btnSig);
		btnAnt.setEnabled(false);
		btnSig.setEnabled(false);
		vjuego.mntmGuardar.setEnabled(true);
		vjuego.mntmGuardarComo.setEnabled(true);
		vjuego.mntmResolver.setEnabled(true);
		
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
		panelbtns.add(btnAnt, "cell 4 5 2 1,growx");
		panelbtns.add(lblMvmnt, "cell 6 5,alignx center");
		panelbtns.add(btnSig, "cell 7 5 2 1,growx");
        add(panelbtns, BorderLayout.SOUTH);	
        
		refrescarSolitario();
	}
	
	public void refrescarSolitario(){
		
		lblMvmnt.setText(solitario.indice+"/"+solitario.loMasLejos);
		for(int i=0;i<lblFinal.length;i++){
			int cnt=i;
			if(solitario.montonesSolucion.get(i).cartasMonton.size() > 0){
				lblFinal[i]=solitario.montonesSolucion.get(i).cartasMonton.get(solitario.montonesSolucion.get(i).cartasMonton.size()-1);
				if (lblFinal[i].getMouseListeners().length==0){
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
								tipoD=2;
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
				}
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
							tipoD=2;
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
			if(lblDescu.getMouseListeners().length==0){
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
						} else {
							if(tipoO!=0){
								selector=0;
								tipoD=0;
								indiceD=1;
								if(tipoO==1){
									solitario.montonesJuego.get(indiceO).cartasMonton.get(solitario.montonesJuego.get(indiceO).cartasMonton.size()-numCartas).setPos(1);
								}
								if(tipoO==2){
									solitario.montonesSolucion.get(indiceO).cartasMonton.get(solitario.montonesSolucion.get(indiceO).cartasMonton.size()-1).setPos(1);
								}
							} else {
								solitario.Descubiertas.cartasMonton.get(solitario.Descubiertas.cartasMonton.size()-1).setPos(1);
								selector=0;
							}
							hacerMvto(new Mvto(tipoO,indiceO,tipoD,indiceD,numCartas,ref));
						}
					}
				});
			}
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
			if(lblNodescu.getMouseListeners().length==0){
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
			}
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
		interior.removeAll();
		interior.revalidate();
		//Añadir nuevos
		for (int i=0;i<MontonesJuego.length;i++){
			interior.add(MontonesJuego[i], "cell " + (i+4) + " 3");
		}
		interior.add(lblNodescu, "cell 1 1");
		interior.add(lblDescu, "cell 2 1");
		for(int i=0;i<lblFinal.length;i++){
			interior.add(lblFinal[i], "cell " + (i+8) + " 1");
		}
		scrollpane.setViewportView(interior);
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

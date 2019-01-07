import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pMontonC extends JPanel {

	private static final long serialVersionUID = 1L;
	public GridBagConstraints gbc_constraints;
	public int id=0;
	public int type=0;
	public int cnt=0;
	public int seleccion=-1;
	public Monton monton;
	
	public pMontonC(Monton monton, int type, int id, pClasico pclasico) {
		this.id=id;
		this.type=type;
		this.monton=monton;
		setBackground(new Color(0,155,0));
		setSize(70,250);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		/*
		int[] altos = new int[20];
		for(int i=0;i<monton.cartasMonton.size();i++){
			altos[i]=10;
			if(i==monton.cartasMonton.size()-1){
				altos[i]=75;
			}
		}
		gridBagLayout.rowHeights=altos;
		*/
		setLayout(gridBagLayout);
		
		for(int i=0;i<monton.cartasMonton.size();i++){
			cnt=i;
			gbc_constraints=new GridBagConstraints();
			gbc_constraints.insets = new Insets(0, 0, 0, 0);
			gbc_constraints.gridx = 0;
			gbc_constraints.gridy = i;
			monton.cartasMonton.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(pclasico.selector==0){
						if(monton.cartasMonton.size()>0 && monton.cartasMonton.get(cnt).getPos()==0){
							pclasico.tipoO=1;
							pclasico.tipoD=1;
							pclasico.indiceD=id;
							pclasico.indiceO=id;
							pclasico.ref=monton.cartasMonton.get(cnt).getRef();
							//pclasico.MontonesJuego[pclasico.seleccion].monton.cartasMonton.get(pclasico.indiceD).setPos(1);
							pclasico.hacerMvto(new Mvto(pclasico.tipoO,pclasico.indiceO,pclasico.tipoD,pclasico.indiceD,1,pclasico.ref));
						}else{
							pclasico.seleccion=cnt;
							monton.cartasMonton.get(cnt).setPos(2);
							pclasico.selector=1;
							pclasico.tipoO=1;
							pclasico.indiceO=id;
							pclasico.ref=monton.cartasMonton.get(cnt).getRef();
							pclasico.numCartas=monton.cartasMonton.size()-cnt;
						}
					} else {
						if(pclasico.seleccion<0){
							pclasico.selector=0;
							pclasico.solitario.Descubiertas.cartasMonton.get(pclasico.solitario.Descubiertas.cartasMonton.size()-1).setPos(1);
						} else {
							//monton.cartasMonton.get(pclasico.seleccion).setPos(1);
							pclasico.MontonesJuego[pclasico.seleccion].monton.cartasMonton.get(pclasico.MontonesJuego[pclasico.seleccion].monton.cartasMonton.size()-pclasico.numCartas).setPos(1);
							pclasico.selector=0;
							pclasico.tipoD=1;
							pclasico.indiceD=id;
							pclasico.hacerMvto(new Mvto(pclasico.tipoO,pclasico.indiceO,pclasico.tipoD,pclasico.indiceD,pclasico.numCartas,pclasico.ref));
						}
					}
				}
			});
			if(i!=monton.cartasMonton.size()-1)
				monton.cartasMonton.get(i).setPreferredSize(new Dimension(70,15));
			add(monton.cartasMonton.get(i), gbc_constraints);
			}
		}

	public pMontonC() {
		setBackground(new Color(0,155,0));
		setSize(0,0);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{53, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		}

	public pMontonC(int type, int id, String baraja, pClasico pclasico) {
		this.type=type;
		setBackground(new Color(0,155,0));
		setSize(50,225);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{53, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		JLabel Vacio = new JLabel();
		ImageIcon icon;
		icon = new ImageIcon("Images/" + baraja + "/Hueco.png");
		gridBagLayout.rowHeights = new int[]{75, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbc_constraints=new GridBagConstraints();
		gbc_constraints.insets = new Insets(0, 0, 0, 0);
		gbc_constraints.gridx = 0;
		gbc_constraints.gridy = 0;
		Vacio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pclasico.selector==1){
					pclasico.selector=0;
					pclasico.tipoD=1;
					pclasico.indiceD=id;
					if(pclasico.seleccion==-1){
						pclasico.solitario.Descubiertas.cartasMonton.get(pclasico.solitario.Descubiertas.cartasMonton.size()-1).setPos(1);
					}
					pclasico.hacerMvto(new Mvto(pclasico.tipoO,pclasico.indiceO,pclasico.tipoD,pclasico.indiceD,pclasico.numCartas,pclasico.ref));
				}
			}
		});
		Vacio.setIcon(icon);
		add(Vacio, gbc_constraints);
		
	}
}


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pMontonS extends JPanel {
	private static final long serialVersionUID = 1L;
	public GridBagConstraints[] gbc_constraints = new GridBagConstraints[20];
	public Monton monton;
	public int id=0;
	public int type=0;
	
	public pMontonS(Monton monton, int type, int id, pSaltos psaltos) {
		this.id=id;
		this.type=type;
		this.monton=monton;
		setBackground(new Color(0,155,0));
		setSize(38,430);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		/*
		int[] altos = new int[20];
		for(int i=0;i<monton.cartasMonton.size();i++){
			altos[i]=5;
			if(i==monton.cartasMonton.size()-1){
				altos[i]=25;
			}
		}
		gridBagLayout.rowHeights=altos;
		*/
		setLayout(gridBagLayout);
		
		for(int i=0;i<monton.cartasMonton.size();i++){
			int cnt=i;
			gbc_constraints[i]=new GridBagConstraints();
			gbc_constraints[i].insets = new Insets(0, 0, 0, 0);
			gbc_constraints[i].gridx = 0;
			gbc_constraints[i].gridy = i;
			if(cnt==monton.cartasMonton.size()-1){
			monton.cartasMonton.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(psaltos.selector==0){
						psaltos.seleccion=cnt;
						monton.cartasMonton.get(cnt).setPos(2);
						psaltos.selector=1;
						psaltos.tipoO=1;
						psaltos.indiceO=id;
						psaltos.ref=monton.cartasMonton.get(cnt).getRef();
						psaltos.numCartas= 1;
					} else {
						//monton.cartasMonton.get(psaltos.seleccion).setPos(1);
						psaltos.selector=0;
						psaltos.tipoD=1;
						psaltos.indiceD=id;
						psaltos.Montones[psaltos.indiceO].monton.cartasMonton.get(psaltos.Montones[psaltos.indiceO].monton.cartasMonton.size()-1).setPos(1);
						psaltos.hacerMvto(new Mvto(psaltos.tipoO,psaltos.indiceO,psaltos.tipoD,psaltos.indiceD,psaltos.numCartas,psaltos.ref));
					}
				}
			});
			}
			if(i!=monton.cartasMonton.size()-1)
				monton.cartasMonton.get(i).setPreferredSize(new Dimension(57,10));
			add(monton.cartasMonton.get(i), gbc_constraints[i]);
			}
	}
	
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pMontonS extends JPanel {
	private static final long serialVersionUID = 1L;
	public GridBagConstraints gbc_constraints;
	public Monton monton;
	public int id=0;
	public int type=0;
	
	public pMontonS(Monton monton, int type, int id, pSaltos psaltos) {
		this.id=id;
		this.type=type;
		this.monton=monton;
		setBackground(new Color(0,155,0));
		setPreferredSize(new Dimension(680,40));
		GridBagLayout gridBagLayout = new GridBagLayout();

		setLayout(gridBagLayout);
		
		for(int i=0;i<monton.cartasMonton.size();i++){
			int cnt=i;
			gbc_constraints=new GridBagConstraints();
			gbc_constraints.gridx = 0;
			gbc_constraints.gridy = i;
			if(cnt==monton.cartasMonton.size()-1 && monton.cartasMonton.get(monton.cartasMonton.size()-1).getMouseListeners().length == 0){
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
			else
				monton.cartasMonton.get(i).setPreferredSize(new Dimension(57,90));
			add(monton.cartasMonton.get(i), gbc_constraints);
			}
		gbc_constraints=new GridBagConstraints();
		gbc_constraints.gridx = 0;
		gbc_constraints.gridy = 41;
		gbc_constraints.weighty=0.1;
		add(new JLabel(),gbc_constraints);
	}
	
}

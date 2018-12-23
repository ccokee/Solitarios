import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class pMontonS extends JPanel {
	private static final long serialVersionUID = 1L;
	public GridBagConstraints[] gbc_constraints = new GridBagConstraints[20];
	public int id=0;
	public int type=0;
	
	/**
	 * @wbp.parser.constructor
	 */
	public pMontonS(Monton monton, int type, int id) {
		this.id=id;
		this.type=type;
		setBackground(new Color(0,155,0));
		setSize(38,430);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{34, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		int[] altos = new int[20];
		for(int i=0;i<monton.cartasMonton.size();i++){
			altos[i]=5;
			if(i==monton.cartasMonton.size()-1){
				altos[i]=25;
			}
		}
		gridBagLayout.rowHeights=altos;
		setLayout(gridBagLayout);
		
		for(int i=0;i<monton.cartasMonton.size();i++){
			gbc_constraints[i].insets = new Insets(0, 0, 0, 0);
			gbc_constraints[i].gridx = 0;
			gbc_constraints[i].gridy = i;
			add(monton.cartasMonton.get(i), gbc_constraints);
			}
	}
	
	public pMontonS(int type, String baraja) {
		this.type=type;
		setBackground(new Color(0,155,0));
		setSize(50,225);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{34, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		JLabel Vacio = new JLabel();
		ImageIcon icon;
		icon = new ImageIcon("Images/" + baraja + "/Hueco");
		gridBagLayout.rowHeights = new int[]{25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbc_constraints[0].insets = new Insets(0, 0, 0, 0);
		gbc_constraints[0].gridx = 0;
		gbc_constraints[0].gridy = 0;
		Vacio.setIcon(icon);
		add(Vacio, gbc_constraints);
	}

}

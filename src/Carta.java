import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Carta extends JLabel{

	private static final long serialVersionUID = 1L;

	public String ref;
	public int pos;
	public String baraja;
	public ImageIcon icon;
	
	public Carta(String ref, String baraja, int pos){
		
		this.ref=ref;
		this.pos=pos;
		this.setBaraja(baraja);
		setHorizontalAlignment(JLabel.LEFT);
		setVerticalAlignment(JLabel.TOP);
		if(this.pos==1)
			System.out.println("Intentando crear carta: " +ref);
			icon = new ImageIcon("Images/" + baraja + "/" + ref.charAt(0)+"_of_"+ref.charAt(1)+".png");
		if(this.pos==0)
			icon = new ImageIcon("Images/" + baraja + "/Reverse.png");
		setIcon(icon);
		setVisible(true);
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getBaraja() {
		return baraja;
	}

	public void setBaraja(String baraja) {
		this.baraja = baraja;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
		if(this.pos==1){
			this.icon = new ImageIcon("Images/" + this.baraja + "/" + this.ref.charAt(0)+"_of_"+ this.ref.charAt(1)+".png");
			setIcon(this.icon);
		} else if(this.pos==0){
			this.icon = new ImageIcon("Images/" + this.baraja + "/Reverse.png");
			setIcon(this.icon);
		}else if(this.pos==2) {
			this.icon = new ImageIcon("Images/" + this.baraja + "/" + this.ref.charAt(0)+"_of_"+this.ref.charAt(1)+"I.png");
			setIcon(this.icon);
		}
	}
	
}

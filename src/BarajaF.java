import java.io.File;
import java.util.ArrayList;

public class BarajaF implements Baraja{
	
	//Hearts y Diamonds rojos %2=0
	public static String Refs[][]={	{"AC","2C","3C","4C","5C","6C","7C","8C","9C","TC","JC","QC","KC"},
			{"AH","2H","3H","4H","5H","6H","7H","8H","9H","TH","JH","QH","KH"},
			{"AS","2S","3S","4S","5S","6S","7S","8S","9S","TS","JS","QS","KS"},
			{"AD","2D","3D","4D","5D","6D","7D","8D","9D","TD","JD","QD","KD"}};
	
	public static int Comprobacion[][]=new int[4][13];
	public ArrayList<Carta> carta = new ArrayList<Carta>();
	public File file;
	
	public String Type="BarajaF";

	public BarajaF(){
		for (int i=0;i<4;i++){
			for (int j=0;j<13;j++){
				carta.add(new Carta(Refs[i][j], "BarajaF", 0));
			}
		}
	}
	
	@Override
	public boolean compruebaBaraja(int x, int y){
		Comprobacion[x][y]++;
		if(Comprobacion[x][y]>1)
			return false;
		else
		    return true;
	}
	
	@Override
	public int[] posicionCarta(Carta carta){
		int posiciones[]={-1,-1};
		for(int i=0;i<4;i++){
			for(int j=0;j<13;j++){
				if(Refs[i][j].equals(carta.getRef())){
					posiciones[0]=i;
					posiciones[1]=j;
				}
			}
		}
		return posiciones;
	}
	
	@Override
	public boolean addCarta(Carta carta){
		int posiciones[]=posicionCarta(carta);
		if( (posiciones[0]!=-1) && (compruebaBaraja(posiciones[0],posiciones[1])) ){
			this.carta.add(carta);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ArrayList<Carta> getCartas() {
		return carta;
	}

	@Override
	public Carta getCarta(int posi) {
		return carta.get(posi);
	}

	@Override
	public void setPosCarta(int posi, int pos) {
		carta.get(posi).setPos(pos);
	}
	
	@Override
	public boolean cartaApilablePaloNumero(Carta cartaActual, Carta cartaFutura){
		int posFutura[]=posicionCarta(cartaFutura);
		int posNueva[]=posicionCarta(cartaActual);
		if ((posFutura[0]==posNueva[0]) || (posFutura[1]==posNueva[1]))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean cartaApilableColorOpuestoSuperior(Carta cartaActual, Carta cartaFutura){
		int posFutura[]=posicionCarta(cartaFutura);
		int posNueva[]=posicionCarta(cartaActual);
		if ((posFutura[0]+1==posNueva[0]) && (posFutura[1]%2!=posNueva[1]%2))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean cartaApilableInmediataInferior(Carta cartaActual, Carta cartaFutura){
		int posFutura[]=posicionCarta(cartaFutura);
		int posNueva[]=posicionCarta(cartaActual);
		if ((posFutura[0]-1==posNueva[0]) && (posFutura[1]==posNueva[1]))
			return true;
		else
			return false;
	}
}

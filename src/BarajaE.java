import java.io.File;
import java.util.ArrayList;

public class BarajaE implements Baraja{
	
	//Sin color
	public static String Refs[][]={	{"AC","2C","3C","4C","5C","6C","7C","SC","CC","RC"},
			{"AO","2O","3O","4O","5O","6O","7O","SO","CO","RO"},
			{"AE","2E","3E","4E","5E","6E","7E","SE","CE","RE"},
			{"AB","2B","3B","4B","5B","6B","7B","SB","CB","RB"} };
	
	static int Comprobacion[][]=new int[4][10];
	public ArrayList<Carta> carta = new ArrayList<Carta>();
	public File file;
	public String Type= "BarajaE";
	
	public BarajaE(){
		for (int i=0;i<13;i++){
			for (int j=0;j<4;j++){
				carta.add(new Carta(Refs[i][j], "BarajaE", 0));
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
	public void setPosCarta(int posi, int pos) {
		carta.get(posi).setPos(pos);
	}

	@Override
	public Carta getCarta(int posi) {
		return carta.get(posi);
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

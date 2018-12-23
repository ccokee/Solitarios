import java.util.ArrayList;

public interface Baraja {
	
	public static String Refs[][]=new String[13][4];
	//public String Type=new String();
	public static String Type = null;
	
	public boolean compruebaBaraja(int x, int y);
	public int[] posicionCarta(Carta carta);
	public boolean addCarta(Carta carta);
	public ArrayList<Carta> getCartas();
	public void setPosCarta(int posi, int pos);
	public Carta getCarta(int posi);
	public boolean cartaApilablePaloNumero(Carta cartaActual, Carta cartaAnterior);	
	public boolean cartaApilableColorOpuestoSuperior(Carta cartaActual, Carta cartaFutura);
	public boolean cartaApilableInmediataInferior(Carta cartaActual, Carta cartaFutura);
}


public class Mvto {

	public int numCartas;
	public int tipoO;
	public int tipoD;
	public int indiceO;
	public int indiceD;
	public String ref;
	
	public Mvto(int tipoO,int indiceO,int tipoD, int indiceD, int numCartas, String ref){
		this.tipoD=tipoD;
		this.tipoO=tipoO;
		this.indiceD=indiceD;
		this.indiceO=indiceO;
		this.numCartas=numCartas;
		this.ref=ref;
	}

	public Mvto() {
		// TODO Auto-generated constructor stub
	}
}

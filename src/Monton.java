import java.util.ArrayList;

public class Monton {

	public int id;
	public int tipoM;
	public ArrayList<Carta> cartasMonton;
	
	public Monton(int tipoM,int id){
		this.id=id;
		this.tipoM=tipoM;
		cartasMonton= new ArrayList<Carta>();
	}
}

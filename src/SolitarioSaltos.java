import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SolitarioSaltos extends Solitario {

	
	public SolitarioSaltos(String TipoBaraja){
		indice=0;
		loMasLejos=0;
		switch(TipoBaraja){
		case "BarajaE":
			barajaJuego=new BarajaE();
			Collections.shuffle(barajaJuego.getCartas());
			barajaJuego=new BarajaF();
			montones=new ArrayList<Monton>();
			noDescubiertas = new Monton(0,0);
		break;
		case "BarajaF":
			barajaJuego=new BarajaF();
			Collections.shuffle(barajaJuego.getCartas());
			barajaJuego=new BarajaF();
			montones=new ArrayList<Monton>();
			noDescubiertas = new Monton(0,0);
		break;
		}
		jugadasValidas();
	}
	
	public SolitarioSaltos(File file){
		indice=0;
		loMasLejos=0;
		String linea=new String();
		String[] tokens;
		
		montones=null;
		
		try {
			this.file=file;
			Scanner parser = new Scanner(file);
			this.barajaJuego=new BarajaE();
			parser.nextLine();
			//Cartas sin extraer
			linea=parser.nextLine();
			tokens=linea.split(" ");
			for(int i=0;i<tokens.length;i++){
				Carta carta= new Carta(tokens[i],"BarajaE",0);
				barajaJuego.addCarta(carta);
				noDescubiertas.cartasMonton.add(carta);
			}
			//Montones
			while(parser.hasNextLine()){
				linea=parser.nextLine();
				
				for(int i=9;i<tokens.length;i++){
					Carta carta= new Carta(tokens[i],"BarajaE",1);
					barajaJuego.addCarta(carta);
					temp.cartasMonton.add(carta);
				}
				montones.add(temp);
			}
			parser.close();
		} catch (IOException ex){
			System.exit(-1);
		}
		jugadasValidas();
	}
	
	public void distribuirCartas(){
		
		noDescubiertas=new Monton(0,0);
		for(int i=barajaJuego.getCartas().size()-1;i>0;i--){
			noDescubiertas.cartasMonton.add(barajaJuego.getCarta(i));
		}
		montones.add(new Monton(1,0));
		montones.get(0).cartasMonton.add(barajaJuego.getCarta(0));
	}
	
	public boolean jugadasValidas(){
		jugadasValidas=new ArrayList<Mvto>();
		
		//mover 3 a la izquierda
		if(montones.size()>3){
			for(int i=3;i<montones.size();i++){
				if(montones.get(i).cartasMonton.size()>0 && montones.get(i-3).cartasMonton.size() > 0 &&
						barajaJuego.cartaApilablePaloNumero(montones.get(i).cartasMonton.get(montones.get(i).cartasMonton.size()-1),
								montones.get(i-3).cartasMonton.get(montones.get(i-3).cartasMonton.size()-1))){
					jugadasValidas.add(new Mvto(1,i,1,i-3,1,montones.get(i).cartasMonton.get(montones.get(i).cartasMonton.size()-1).getRef()));
				}
			}
		}
		//mover 1 a la iaquierda
		if(montones.size()>1){
			for(int i=1;i<montones.size();i++){
				if(montones.get(i).cartasMonton.size()>0 && montones.get(i-1).cartasMonton.size() > 0 &&
						barajaJuego.cartaApilablePaloNumero(montones.get(i).cartasMonton.get(montones.get(i).cartasMonton.size()-1),
								montones.get(i-1).cartasMonton.get(montones.get(i-1).cartasMonton.size()-1))){
					jugadasValidas.add(new Mvto(1,i,1,i-1,1,montones.get(i).cartasMonton.get(montones.get(i).cartasMonton.size()-1).getRef()));
				}
			}
		}
		//Sacar carta
		if(noDescubiertas.cartasMonton.size()>0){
			jugadasValidas.add(new Mvto(0,0,1,montones.size(),1,noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).getRef()));
		} else {
			return false;
		}
		return true;
	}
	
	public boolean haySiguienteMvto(){
		if(jugadasValidas.size()>0)
			return true;
		else
			return false;
	}
	
	public void hacerMovimiento(Mvto move, int deshaz){
		//No descubiertas a ultimo monton
		if(move.tipoO==0 && noDescubiertas.cartasMonton.size()>0){
			montones.add(new Monton(1,montones.size()));
			montones.get(move.indiceD).cartasMonton.add(
					noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1));
			//DescubrirCarta
			montones.get(move.indiceD).cartasMonton.get(montones.get(move.indiceD).cartasMonton.size()-1).setPos(1);
			//Eliminar carta del monto original
			noDescubiertas.cartasMonton.remove(noDescubiertas.cartasMonton.size()-1);
		}
		//Entre montones
		if(move.tipoO==1 && montones.get(move.indiceO).cartasMonton.size()>0){
			montones.get(move.indiceD).cartasMonton.add(
					montones.get(move.indiceO).cartasMonton.get(montones.get(move.indiceO).cartasMonton.size()-1));
			montones.get(move.indiceO).cartasMonton.remove(montones.get(move.indiceO).cartasMonton.size()-1);
			
			if(montones.get(move.indiceO).cartasMonton.size()==0){
				montones.remove(move.indiceO);
			}
		}
		//Devuelta al monton
		if(move.tipoO==1 && move.tipoD==0){
			if(montones.get(move.indiceO).cartasMonton.size()>0){
				//añadir
				noDescubiertas.cartasMonton.add(montones.get(move.indiceO).cartasMonton.get(montones.get(move.indiceO).cartasMonton.size()-1));
				//eliminar
				montones.get(move.indiceO).cartasMonton.remove(montones.get(move.indiceO).cartasMonton.size()-1);
				//cubrir carta
				noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).setPos(0);
			}
		}
		
		if (deshaz==0){
			if(indice==loMasLejos){
				movimientos.add(move);
			} else {
				for(int i=indice;i<loMasLejos;i++){
					movimientos.remove(i);
				}
			}
			indice++;
			loMasLejos++; //que se ha llegado
		}else{
			if(movimientos.size()>0){
				indice--;
			}
		}
		jugadasValidas();
	}
	
	public void deshacerMovimiento(Mvto move){
		if (indice>0){
			int aux;
			aux = move.indiceD;
			move.indiceD=move.indiceO;
			move.indiceO=aux;
			aux=move.tipoD;
			move.tipoD=move.tipoO;
			move.tipoO=aux;
			
			hacerMovimiento(move,1);
		}
	}
	
	@Override
	public boolean resolverSolitario(int orden){
		while (haySiguienteMvto()){
			if(orden==0)
				Collections.shuffle(jugadasValidas);
			hacerMovimiento(jugadasValidas.get(0),0);
		}
		return true;
	}
	
	@Override
	public boolean guardarSolitario(int como){
		JFileChooser fileChooser=new JFileChooser();
		if (file==null || como==1){
			int result = fileChooser.showSaveDialog(null);
			if(result==JFileChooser.APPROVE_OPTION){
				this.file = fileChooser.getSelectedFile();
			}
		}
		try {
			FileWriter writer = new FileWriter(file,false);
			PrintWriter printer = new PrintWriter(writer);
			String line = new String();
			printer.println("Solitario saltos");
			line=" ";
			if(noDescubiertas.cartasMonton.size()>0)
				line = noDescubiertas.cartasMonton.get(0).getRef();
			for(int i=1;i<noDescubiertas.cartasMonton.size();i++){
				line=(line + " " + noDescubiertas.cartasMonton.get(i).getRef());
			}
			printer.println(line);
			for(int i=0; i<montones.size();i++){
				line = "";
				if(montones.get(i).cartasMonton.size()>0)
					line=montones.get(i).cartasMonton.get(0).getRef(); 
				for(int j=1; j<montones.get(i).cartasMonton.size(); j++){
					line=(line + " " + montones.get(i).cartasMonton.get(j).getRef());
				}
				printer.print(line);
				printer.close();
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

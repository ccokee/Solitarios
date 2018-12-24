import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SolitarioClasico extends Solitario{

	
	public SolitarioClasico(String TipoBaraja){	
		indice=0;
		loMasLejos=0;
		switch(TipoBaraja){
		case "BarajaE":
			barajaJuego=new BarajaE();
			Collections.shuffle(barajaJuego.getCartas());
			distribuirCartas();
		break;
		case "BarajaF":
			barajaJuego=new BarajaF();
			Collections.shuffle(barajaJuego.getCartas());
			distribuirCartas();
		break;
		}
		jugadasValidas();
	}
	
	public SolitarioClasico(File file) {
		indice=0;
		loMasLejos=0;
		String linea=new String();
		String[] tokens;
		
		montonesSolucion.clear();
		montonesJuego.clear();
		
		for(int i=0;i<4;i++)
			montonesSolucion.add(new Monton(2,i));
		for(int i=0;i<7;i++)
			montonesJuego.add(new Monton(1,i));
		
		Descubiertas = new Monton(0,1);
		noDescubiertas = new Monton(0,0);
		
		try {
			this.file=file;
			Scanner parser = new Scanner(file);
			this.barajaJuego=new BarajaE();
			parser.nextLine();
			//Cartas sin extraer
			linea=parser.nextLine();
			tokens=linea.split(" ");
			for(int i=0;i<tokens.length;i++){
				Carta carta= new Carta(tokens[i],"BarajaF",0);
				barajaJuego.addCarta(carta);
				noDescubiertas.cartasMonton.add(carta);
			}
			//Cartas descubiertas
			linea=parser.nextLine();
			tokens=linea.split(" ");
			for(int i=0;i<tokens.length;i++){
				Carta carta = new Carta(tokens[i],"BarajaF",1);
				barajaJuego.addCarta(carta);
				Descubiertas.cartasMonton.add(carta);
			}
			//Montones
			for(int i=0;i<7;i++){
				temp = new Monton(1,i);
				linea=parser.nextLine();
				tokens=linea.split(" ");
				int pos=0;
				for(int j=0;j<tokens.length;j++){
					if(tokens[j].equals("*")){
						pos=1;
					} else {
						Carta carta= new Carta(tokens[j],"BarajaF",pos);
						barajaJuego.addCarta(carta);
						temp.cartasMonton.add(carta);
					}
				}
				montonesJuego.add(temp);
			}
			for(int i=0;i<4;i++){
				temp=new Monton(2,i);
				linea=parser.nextLine();
				tokens=linea.split(" ");
				for(int j=0;j<tokens.length;j++){
					Carta carta = new Carta(tokens[j],"BarajaF",0);
					barajaJuego.addCarta(carta);
					temp.cartasMonton.add(carta);
				}
				montonesSolucion.add(temp);
			}
			parser.close();
		} catch (IOException ex){
			System.exit(-1);
		}
		jugadasValidas();
	}
	
	public void distribuirCartas(){
		int posimazo=0;
		montonesSolucion=null;
		montonesJuego=null;
		for(int i=0;i<4;i++){
			montonesSolucion.add(new Monton(2,i));
		}
		for(int i=0;i<7;i++){
			montonesJuego.add(new Monton(1,i));
		}
		for(int i=0;i<montonesJuego.size();i++){
			for(int j=0;j<i;j++){
				barajaJuego.setPosCarta(posimazo, 0);
				montonesJuego.get(i).cartasMonton.add(barajaJuego.getCarta(posimazo));
				posimazo++;
			}
			barajaJuego.setPosCarta(posimazo,1);
			montonesJuego.get(i).cartasMonton.add(barajaJuego.getCarta(posimazo));
			posimazo++;
		}
		for(int i=barajaJuego.getCartas().size()-1;i>=posimazo;i--){
			noDescubiertas.cartasMonton.add(barajaJuego.getCarta(i));
		}
		Descubiertas = new Monton(0,1);
	}
	
	public boolean jugadasValidas(){
		jugadasValidas=new ArrayList<Mvto>();
		montonesVacios=new ArrayList<Integer>();
		//¿Ultima cubriera? Descubrir
		for(int i=0;i<montonesJuego.size();i++){
			if(montonesJuego.get(i).cartasMonton.size()>0 &&
					montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getPos()==0)
				jugadasValidas.add(new Mvto(1,i,1,i,1,
						montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
		}
		//¿Rey? pues a monton vacio
		//---busca monton vacio
		for(int i=0;i<montonesJuego.size();i++){
			if(montonesJuego.get(i).cartasMonton.size()==0)
				montonesVacios.add(i);
		}
		if(montonesVacios.size()>0){
			for(int i=0;i<montonesJuego.size();i++){
				for(int j=0;j<montonesJuego.get(i).cartasMonton.size();j++){
					if(montonesJuego.get(i).cartasMonton.get(j).getRef().contains("K") ||
							montonesJuego.get(i).cartasMonton.get(j).getRef().contains("R")){
						for(int k=0;k<montonesVacios.size();k++){
							jugadasValidas.add(new Mvto(1,i,1,montonesVacios.get(k),montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
						}
					}
				}
			}
			if(Descubiertas.cartasMonton.size() > 0 &&
					(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef().contains("K") || 
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef().contains("R"))){
				for(int i=0;i<montonesVacios.size();i++){
					jugadasValidas.add(new Mvto(0,1,1,montonesVacios.get(i),1, Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef()));
				}
			}
		}
		//Ultima carta descubierta a Solucion
		for(int i=0;i<montonesSolucion.size();i++){
			if(montonesSolucion.get(i).cartasMonton.size() > 0 &&
					(barajaJuego.cartaApilableInmediataInferior(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1), 
					montonesSolucion.get(i).cartasMonton.get(montonesSolucion.get(i).cartasMonton.size()-1)))){
				jugadasValidas.add(new Mvto(0,1,2,i,1, montonesSolucion.get(i).cartasMonton.get(montonesSolucion.get(i).cartasMonton.size()-1).getRef()));
			}
		}
		//Ultima descubierta a Juego
		for(int i=0;i<montonesJuego.size();i++){
			if(montonesJuego.get(i).cartasMonton.size() > 0 && 
					(barajaJuego.cartaApilableColorOpuestoSuperior(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1), 
					montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1)))){
				jugadasValidas.add(new Mvto(0,1,1,i,1,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
			}
		}
		//Montones entre si
		for(int i=0;i<montonesJuego.size();i++){
			for(int j=0;j<montonesJuego.get(i).cartasMonton.size();j++){
				if(montonesJuego.get(i).cartasMonton.get(j).getPos()==1){
					for(int k=0;k<montonesJuego.size();k++){
						if(i==k)
							k++;
						if(montonesJuego.get(i).cartasMonton.size() > 0 &&
								(barajaJuego.cartaApilableColorOpuestoSuperior(montonesJuego.get(i).cartasMonton.get(j),
								montonesJuego.get(k).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1)))){
							jugadasValidas.add(new Mvto(1,i,1,k,montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(k).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
						}
					}
				}
			}
		}
		//Montones a montones solucion
		for(int i=0;i<montonesJuego.size();i++){
			for(int j=0;j<montonesSolucion.size();j++){
				if(montonesSolucion.get(j).cartasMonton.size() > 0 && 
						(barajaJuego.cartaApilableInmediataInferior(montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1),
						montonesSolucion.get(j).cartasMonton.get(montonesSolucion.get(j).cartasMonton.size()-1)))){
					jugadasValidas.add(new Mvto(1,i,2,j,1,montonesSolucion.get(j).cartasMonton.get(montonesSolucion.get(j).cartasMonton.size()-1).getRef()));
				}
			}
		}

		//Descubrir Ultima carta
		if(noDescubiertas.cartasMonton.size()>0)
			jugadasValidas.add(new Mvto(0,0,0,1,1,noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).getRef()));
		else 
			return false;
		return true;
	}

	public boolean esJugadaInversa(Mvto jugada){
		if(movimientos.size() > 0 && 
				( jugada.indiceO == movimientos.get(movimientos.size()-1).indiceD &&
				jugada.indiceD == movimientos.get(movimientos.size()-1).indiceO &&
				jugada.tipoO == movimientos.get(movimientos.size()-1).tipoD &&
				jugada.tipoD == movimientos.get(movimientos.size()-1).tipoO ))
		return true;
		else
		return false;
	}
	
	public boolean haySiguienteMvto(){
		
		if(jugadasValidas.size()>0)
			return true;
		else
			return false;
	}
	
	public boolean resolverSolitario(int orden){
		while (haySiguienteMvto()){
			if(orden==0)
				Collections.shuffle(jugadasValidas);
			hacerMovimiento(jugadasValidas.get(0),0);
		}
		if(montonesSolicionCompletos())
			return true;
		else
			return false;
	}
	
	@Override
	public void hacerMovimiento(Mvto move, int deshaz){
		
		//No descubiertas a descubiertas
		if(move.tipoD==0 && move.indiceD==1 && move.tipoO==0 && move.indiceO==0){
			Descubiertas.cartasMonton.add(
					noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1));
			Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).setPos(1);
			noDescubiertas.cartasMonton.remove(noDescubiertas.cartasMonton.size()-1);
		}
		
		//Juego a mismo juego (descubrir carta)
		if(move.tipoD==1 && move.tipoO==1 && move.indiceD==move.indiceO){
			if(montonesJuego.get(move.indiceD).cartasMonton.get( montonesJuego.get(move.indiceD).cartasMonton.size()-1).getPos()==0)
				montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPos(1);
		}
		
		//Juego a Descubiertas
		if(move.tipoO==1 && move.tipoD==0 && move.indiceD==1){
			if(montonesJuego.get(move.indiceO).cartasMonton.size()>0){
				Descubiertas.cartasMonton.add(montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-1));
				montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.size()-1);
			}
		}
		//Solucion a Descubiertas
		if(move.tipoO==2 && move.tipoD==0 && move.indiceD==0){
			if(montonesSolucion.get(move.indiceO).cartasMonton.size()>0){
				Descubiertas.cartasMonton.add(montonesSolucion.get(move.indiceO).cartasMonton.get(montonesSolucion.get(move.indiceO).cartasMonton.size()-1));
				montonesSolucion.get(move.indiceO).cartasMonton.remove(montonesSolucion.get(move.indiceO).cartasMonton.size()-1);
			}
		}
		//Descubiertas a noDescubiertas
		if(move.tipoO==0 && move.tipoD==0 && move.indiceD==0 && move.indiceO==1){
			if(Descubiertas.cartasMonton.size()>0){
				//añadir
				noDescubiertas.cartasMonton.add(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
				//eliminar
				Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
				//cambiar pos
				noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).setPos(0);
			}
		}
		
		//descubiertas a solucion
		if(move.tipoO==0 && move.tipoD==2 && move.indiceO==1 && Descubiertas.cartasMonton.size()>0){
			montonesJuego.get(move.indiceD).cartasMonton.add(
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
			Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
		}
		
		//descubiertas a juego
		if(move.tipoO==0 && move.tipoD==1 && move.indiceO==1 && Descubiertas.cartasMonton.size()>0){
			montonesSolucion.get(move.indiceD).cartasMonton.add(
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
			Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
		}
		
		//juego a soluion
		if(move.tipoO==1 && move.tipoD==2 && montonesJuego.get(move.indiceO).cartasMonton.size()> 0){
			montonesSolucion.get(move.indiceD).cartasMonton.add(
					montonesJuego.get(move.indiceO).cartasMonton.get(
							montonesJuego.get(move.indiceO).cartasMonton.size()-1));
			montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.size()-1);
		}
		
		//Solucion a Juego
		if(move.tipoO==2 && move.tipoD==1 && montonesSolucion.get(move.indiceO).cartasMonton.size()>0){
			montonesJuego.get(move.indiceD).cartasMonton.add(
					montonesSolucion.get(move.indiceO).cartasMonton.get(
							montonesSolucion.get(move.indiceO).cartasMonton.size()-1));
			montonesSolucion.get(move.indiceO).cartasMonton.remove(montonesSolucion.get(move.indiceO).cartasMonton.size()-1);
		}
		
		//juego a juego
		if(move.tipoO==1 && move.tipoD==1 && move.indiceO!=move.indiceD){
			//copiar cartas a monton
			ArrayList<Carta> temporal=new ArrayList<Carta>();
			for(int i=1;i<move.numCartas+1;i++){
				temporal.add(0, montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i));
				montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i));
			}
			montonesJuego.get(move.tipoD).cartasMonton.addAll(temporal);
		}
		
		if (deshaz==0){
			if(indice==loMasLejos){
				movimientos.add(move);
			} else {
				for(int i=indice+1;i<movimientos.size();i++){
					movimientos.remove(i);
				}
				loMasLejos=indice;
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
	
	@Override
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
	public boolean mvtoCorrecto(Mvto mvto){
		if (jugadasValidas.contains(mvto))
			return true;
		else
			return false;
	}
	
	public boolean montonesSolicionCompletos() {
		if(montonesSolucion.get(0).cartasMonton.size()==barajaJuego.getCartas().size()/4 &&
				montonesSolucion.get(1).cartasMonton.size()==barajaJuego.getCartas().size()/4 &&
				montonesSolucion.get(2).cartasMonton.size()==barajaJuego.getCartas().size()/4 &&
				montonesSolucion.get(3).cartasMonton.size()==barajaJuego.getCartas().size()/4)
			return true;
		else
			return false;
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
			printer.println("Solitario clásico");
			line=" ";
			//Cartas sin sacar del mazo
			if(noDescubiertas.cartasMonton.size()>0)
				line = noDescubiertas.cartasMonton.get(0).getRef();
			for(int i=1;i<noDescubiertas.cartasMonton.size();i++){
				line=(line + " " + noDescubiertas.cartasMonton.get(i).getRef());
			}
			printer.println(line);
			//Cartas sacadas del mazo
			if(Descubiertas.cartasMonton.size()>0)
				line = Descubiertas.cartasMonton.get(0).getRef();
			for(int i=1;i<Descubiertas.cartasMonton.size();i++){
				line=(line + " " + Descubiertas.cartasMonton.get(i).getRef());
			}
			printer.println(line);
			//Montones Juego
			for(int i=0; i<montonesJuego.size();i++){
				int vueltaDada=0;
				line = "";
				if(montonesJuego.get(i).cartasMonton.size()>0){
					if(montonesJuego.get(i).cartasMonton.get(9).getPos()==1){
						line=montonesJuego.get(i).cartasMonton.get(0).getRef();
					} else {
						vueltaDada=1;
						line=("* " + montonesJuego.get(i).cartasMonton.get(0).getRef());	
					}
				}
				for(int j=1; j<montonesJuego.get(i).cartasMonton.size(); j++){
					
					if(montonesJuego.get(i).cartasMonton.get(j).getPos()==1 && vueltaDada==0){
						vueltaDada=1;
						line=(line + " *");
					}
					line=(line + " " + montonesJuego.get(i).cartasMonton.get(j).getRef());
				}
				printer.print(line);
			}
			// Montones Solucion
			for(int i=0; i<montonesSolucion.size();i++){
				line = "";
				if(montonesSolucion.get(i).cartasMonton.size()>0)
					line=montonesSolucion.get(i).cartasMonton.get(0).getRef(); 
				for(int j=1; j<montonesSolucion.get(i).cartasMonton.size(); j++){
					line=(line + " " + montonesSolucion.get(i).cartasMonton.get(j).getRef());
				}
				printer.print(line);
				printer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}

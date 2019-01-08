import java.awt.Dimension;
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
		Tipo="Clasico";
		indice=0;
		loMasLejos=0;
		movimientos= new ArrayList<Mvto>();
		System.out.println("Size movimientos: "+movimientos.size());
		switch(TipoBaraja){
		case "BarajaE":
			tipoBaraja=TipoBaraja;
			barajaJuego=new BarajaE();
			Collections.shuffle(barajaJuego.getCartas());
			barajaJuego=new BarajaF();
			montonesJuego=new ArrayList<Monton>();
			montonesSolucion=new ArrayList<Monton>();
			Descubiertas = new Monton(0,1);
			noDescubiertas = new Monton(0,0);
			distribuirCartas();
		break;
		case "BarajaF":
			tipoBaraja=TipoBaraja;
			barajaJuego=new BarajaF();
			montonesSolucion=new ArrayList<Monton>();
			montonesJuego=new ArrayList<Monton>();
			Descubiertas = new Monton(0,1);
			noDescubiertas = new Monton(0,0);
			Collections.shuffle(barajaJuego.getCartas());
			distribuirCartas();
		break;
		}
		jugadasValidas();
	}
	
	public SolitarioClasico(File file, String TipoBaraja) {
		Tipo="Clasico";
		tipoBaraja=TipoBaraja;
		indice=0;
		loMasLejos=0;
		String linea=new String();
		String[] tokens;
		
		movimientos= new ArrayList<Mvto>();
		montonesSolucion=new ArrayList<Monton>();
		montonesJuego=new ArrayList<Monton>();
		Descubiertas = new Monton(0,1);
		noDescubiertas = new Monton(0,0);

		try {
			this.file=file;
			Scanner parser = new Scanner(file);
			this.barajaJuego=new BarajaF();
			parser.nextLine();
			//Cartas sin extraer
			linea=parser.nextLine();
			System.out.println(linea);
			tokens=linea.split(" ");
			if(!linea.equals("")){
			for(int i=0;i<tokens.length;i++){
				Carta carta= new Carta(tokens[i],"BarajaF",0);
				barajaJuego.addCarta(carta);
				noDescubiertas.cartasMonton.add(carta);
			}
			}
			//Cartas descubiertas
			linea=parser.nextLine();
			if(!linea.equals("")){
			tokens=linea.split(" ");
			for(int i=0;i<tokens.length;i++){
				Carta carta = new Carta(tokens[i],"BarajaF",1);
				barajaJuego.addCarta(carta);
				Descubiertas.cartasMonton.add(carta);
			}
			}
			//Montones
			for(int i=0;i<7;i++){
				temp = new Monton(1,i);
				if(parser.hasNext()){
					linea=parser.nextLine();
					if(!linea.equals("")){
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
					}
				}
				montonesJuego.add(temp);
			}
			for(int i=0;i<4;i++){
				temp=new Monton(2,i);
				if(parser.hasNext()){
					linea=parser.nextLine();
					if(!linea.equals("")){
						tokens=linea.split(" ");
						for(int j=0;j<tokens.length;j++){
							Carta carta = new Carta(tokens[j],"BarajaF",1);
							barajaJuego.addCarta(carta);
							temp.cartasMonton.add(carta);
						}
					}
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
					if((montonesJuego.get(i).cartasMonton.get(j).getRef().contains("K") ||
							montonesJuego.get(i).cartasMonton.get(j).getRef().contains("R") ) && (
									montonesJuego.get(i).cartasMonton.get(j).getPos()==1)){
						for(int k=0;k<montonesVacios.size();k++){
							if(!esJugadaInversa(new Mvto(1,i,1,montonesVacios.get(k),montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef())))
							jugadasValidas.add(new Mvto(1,i,1,montonesVacios.get(k),montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
						}
					}
				}
			}
			if(Descubiertas.cartasMonton.size() > 0 &&
					(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef().contains("K") || 
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef().contains("R"))){
				for(int i=0;i<montonesVacios.size();i++){
					if(!esJugadaInversa(new Mvto(0,1,1,montonesVacios.get(i),1, Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef())))
						jugadasValidas.add(new Mvto(0,1,1,montonesVacios.get(i),1, Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef()));
				}
			}
		}
		//Ultima carta descubierta a Solucion
		for(int i=0;i<montonesSolucion.size();i++){
			if(montonesSolucion.get(i).cartasMonton.size() > 0 && Descubiertas.cartasMonton.size()>0&&
					(barajaJuego.cartaApilableInmediataInferior(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1), 
					montonesSolucion.get(i).cartasMonton.get(montonesSolucion.get(i).cartasMonton.size()-1)))){
				if(!esJugadaInversa(new Mvto(0,1,2,i,1, montonesSolucion.get(i).cartasMonton.get(montonesSolucion.get(i).cartasMonton.size()-1).getRef())))
				jugadasValidas.add(new Mvto(0,1,2,i,1, montonesSolucion.get(i).cartasMonton.get(montonesSolucion.get(i).cartasMonton.size()-1).getRef()));
			}
		}
		//Ultima descubierta a Juego
		for(int i=0;i<montonesJuego.size();i++){
			if(montonesJuego.get(i).cartasMonton.size() > 0 && 
					Descubiertas.cartasMonton.size() > 0 &&
					(barajaJuego.cartaApilableColorOpuestoSuperior(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1), 
					montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1)))){
				if(!esJugadaInversa(new Mvto(0,1,1,i,1,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef())))
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
								k<7 &&
								montonesJuego.get(k).cartasMonton.size() >0 && 
								montonesJuego.get(k).cartasMonton.get(montonesJuego.get(k).cartasMonton.size()-1).getPos()==1 &&
								(barajaJuego.cartaApilableColorOpuestoSuperior(montonesJuego.get(i).cartasMonton.get(j),
								montonesJuego.get(k).cartasMonton.get(montonesJuego.get(k).cartasMonton.size()-1)))){
							//System.out.println("Num cartas mvto: " + (montonesJuego.get(i).cartasMonton.size()-1-j));
							if(!esJugadaInversa(new Mvto(1,i,1,k,montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef())))
							jugadasValidas.add(new Mvto(1,i,1,k,montonesJuego.get(i).cartasMonton.size()-1-j,montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1).getRef()));
						}
					}
				}
			}
		}
		//Montones a montones solucion
		for(int i=0;i<montonesJuego.size();i++){
			for(int j=0;j<montonesSolucion.size();j++){
				if(montonesSolucion.get(j).cartasMonton.size() > 0 && montonesJuego.get(i).cartasMonton.size() > 0 &&
						(barajaJuego.cartaApilableInmediataInferior(montonesJuego.get(i).cartasMonton.get(montonesJuego.get(i).cartasMonton.size()-1),
						montonesSolucion.get(j).cartasMonton.get(montonesSolucion.get(j).cartasMonton.size()-1)))){
					if(!esJugadaInversa(new Mvto(1,i,2,j,1,montonesSolucion.get(j).cartasMonton.get(montonesSolucion.get(j).cartasMonton.size()-1).getRef())))
					jugadasValidas.add(new Mvto(1,i,2,j,1,montonesSolucion.get(j).cartasMonton.get(montonesSolucion.get(j).cartasMonton.size()-1).getRef()));
				}
			}
		}

		//Descubrir Ultima carta
		if(noDescubiertas.cartasMonton.size()>0){
			jugadasValidas.add(new Mvto(0,0,0,1,1,noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).getRef()));
			for(int i=0; i<jugadasValidas.size()-1;i++){
				System.out.println("Mvto Valido" + i + " " + jugadasValidas.get(i).toString());
			}	
		}else{ 
			return false;
		}
		return true;
	}

	public boolean esJugadaInversa(Mvto jugada){
		if(movimientos.size() > 0 && 
				( jugada.indiceO == movimientos.get(movimientos.size()-1).indiceD &&
				jugada.indiceD == movimientos.get(movimientos.size()-1).indiceO &&
				jugada.tipoO == movimientos.get(movimientos.size()-1).tipoD &&
				jugada.tipoD == movimientos.get(movimientos.size()-1).tipoO &&
				jugada.numCartas == movimientos.get(movimientos.size()-1).numCartas && 
				jugada.ref.equals(movimientos.get(movimientos.size()-1).ref) ))
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
			System.out.println("No descu a descu!!!");
			Descubiertas.cartasMonton.add(
					noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1));
			System.out.println("Descubrimos!");
			noDescubiertas.cartasMonton.remove(noDescubiertas.cartasMonton.size()-1);
			Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).setPos(1);
			if(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getMouseListeners().length>0)
			Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).removeMouseListener(
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getMouseListeners()[0]);
			System.out.println(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getRef());
		}
		
		//Juego a mismo juego (descubrir carta)
		if(move.tipoD==1 && move.tipoO==1 && move.indiceD==move.indiceO){
			System.out.println("Descubrir carta de juego!!!");
			if(deshaz==1){
				montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPos(0);
				montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPreferredSize(new Dimension(70,15));
			} else {
				montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPos(1);
				montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPreferredSize(new Dimension(70,100));
			}
		}
		
		//Juego a Descubiertas
		if(move.tipoO==1 && move.tipoD==0 && move.indiceD==1){
			System.out.println("Juego a Descubiertas (Deshacer)");
			if(montonesJuego.get(move.indiceO).cartasMonton.size()>0){
				Descubiertas.cartasMonton.add(montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-1));
				Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).setPos(1);
				Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).removeMouseListener(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getMouseListeners()[0]);
				montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.size()-1);
			}
		}
		//Solucion a Descubiertas
		if(move.tipoO==2 && move.tipoD==0 && move.indiceD==0){
			System.out.println("Solucion a Descubiertas (Deshacer)");
			if(montonesSolucion.get(move.indiceO).cartasMonton.size()>0){
				Descubiertas.cartasMonton.add(montonesSolucion.get(move.indiceO).cartasMonton.get(montonesSolucion.get(move.indiceO).cartasMonton.size()-1));
				Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).setPos(1);
				Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).removeMouseListener(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1).getMouseListeners()[0]);;
				montonesSolucion.get(move.indiceO).cartasMonton.remove(montonesSolucion.get(move.indiceO).cartasMonton.size()-1);
			}
		}
		//Descubiertas a noDescubiertas
		if(move.tipoO==0 && move.tipoD==0 && move.indiceD==0 && move.indiceO==1){
			System.out.println("Descubiertas a noDescubiertas (Deshacer)");
			if(Descubiertas.cartasMonton.size()>0){
				//añadir
				noDescubiertas.cartasMonton.add(Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
				//eliminar
				Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
				//cambiar pos
				noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).setPos(0);
				noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).removeMouseListener(noDescubiertas.cartasMonton.get(noDescubiertas.cartasMonton.size()-1).getMouseListeners()[0]);
			}
		}
		
		//descubiertas a solucion
		if(move.tipoO==0 && move.tipoD==1 && move.indiceO==1 && Descubiertas.cartasMonton.size()>0){
			System.out.println("Descubiertas a solucion!");
			montonesJuego.get(move.indiceD).cartasMonton.add(
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
			montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPos(1);
			montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).removeMouseListener(
					montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).getMouseListeners()[0]);
			Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
		}
		
		//descubiertas a juego
		if(move.tipoO==0 && move.tipoD==2 && move.indiceO==1 && Descubiertas.cartasMonton.size()>0){
			System.out.println("Descubiertas a Juego!!!");
			montonesSolucion.get(move.indiceD).cartasMonton.add(
					Descubiertas.cartasMonton.get(Descubiertas.cartasMonton.size()-1));
			montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).setPos(1);
			montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).removeMouseListener(
					montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).getMouseListeners()[0]);
			
			Descubiertas.cartasMonton.remove(Descubiertas.cartasMonton.size()-1);
		}
		
		//juego a soluion
		if(move.tipoO==1 && move.tipoD==2 && montonesJuego.get(move.indiceO).cartasMonton.size()> 0){
			System.out.println("Juego a Solucion!!");
			montonesSolucion.get(move.indiceD).cartasMonton.add(
					montonesJuego.get(move.indiceO).cartasMonton.get(
							montonesJuego.get(move.indiceO).cartasMonton.size()-1));
			montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).setPos(1);
			if(montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).getMouseListeners().length>0)
			montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).removeMouseListener( 
					montonesSolucion.get(move.indiceD).cartasMonton.get(montonesSolucion.get(move.indiceD).cartasMonton.size()-1).getMouseListeners()[0]);
			montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.size()-1);
		}
		
		//Solucion a Juego
		if(move.tipoO==2 && move.tipoD==1 && montonesSolucion.get(move.indiceO).cartasMonton.size()>0){
			System.out.println("Solucion a Juego (DesHacer)");
			montonesJuego.get(move.indiceD).cartasMonton.add(
					montonesSolucion.get(move.indiceO).cartasMonton.get(
							montonesSolucion.get(move.indiceO).cartasMonton.size()-1));
			montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).setPos(1);
			if(montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).getMouseListeners().length>0)
			montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).removeMouseListener( 
					montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1).getMouseListeners()[0]);
			montonesSolucion.get(move.indiceO).cartasMonton.remove(montonesSolucion.get(move.indiceO).cartasMonton.size()-1);
		}
		
		//juego a juego
		if(move.tipoO==1 && move.tipoD==1 && move.indiceO!=move.indiceD){
			System.out.println("Juego a Juego, numcartas: " +move.numCartas);
			//copiar cartas a monton
			ArrayList<Carta> temporal=new ArrayList<Carta>();
			for(int i=1;i<move.numCartas+1;i++){ // PJINOO i=1
				if(montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i).getMouseListeners().length>0)
				montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i).removeMouseListener( 
						montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i).getMouseListeners()[0]);
				temporal.add(0, montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i));
				montonesJuego.get(move.indiceO).cartasMonton.remove(montonesJuego.get(move.indiceO).cartasMonton.get(montonesJuego.get(move.indiceO).cartasMonton.size()-i));
			}
			montonesJuego.get(move.indiceD).cartasMonton.addAll(temporal);
			montonesJuego.get(move.indiceD).cartasMonton.get(montonesJuego.get(move.indiceD).cartasMonton.size()-1-move.numCartas).setPos(1);
		}
		
		if (deshaz==0){
			if(indice==loMasLejos){
				movimientos.add(move);
			} else {
				for(int i=indice+1;i<movimientos.size();i++){
					movimientos.remove(i);
				}
				movimientos.add(move);
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
			fileChooser.setDialogTitle("Guardar Como..");
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
			if(noDescubiertas.cartasMonton.size()>0){
				line = noDescubiertas.cartasMonton.get(0).getRef();
				for(int i=1;i<noDescubiertas.cartasMonton.size();i++){
					line=(line + " " + noDescubiertas.cartasMonton.get(i).getRef());
				}
				printer.println(line);
			} else {
				printer.println("");
			}
			//Cartas sacadas del mazo
			if(Descubiertas.cartasMonton.size()>0) {
				line = Descubiertas.cartasMonton.get(0).getRef();
					for(int i=1;i<Descubiertas.cartasMonton.size();i++){
						line=(line + " " + Descubiertas.cartasMonton.get(i).getRef());
					}
				printer.println(line);
			} else {
				printer.println("");
			}
			//Montones Juego
			if(montonesJuego.size()>0){
			for(int i=0; i<montonesJuego.size();i++){
				int vueltaDada=0;
				line = "";
				if(montonesJuego.get(i).cartasMonton.size()>0){
					if(montonesJuego.get(i).cartasMonton.get(0).getPos()==0){
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
				printer.println(line);
			}
			} else {
				printer.println("\n\n\n\n\n\n\n");
			}
			// Montones Solucion
			if(montonesSolucion.size()>0){
			for(int i=0; i<montonesSolucion.size();i++){
				line = "";
				if(montonesSolucion.get(i).cartasMonton.size()>0)
					line=montonesSolucion.get(i).cartasMonton.get(0).getRef(); 
				for(int j=1; j<montonesSolucion.get(i).cartasMonton.size(); j++){
					line=(line + " " + montonesSolucion.get(i).cartasMonton.get(j).getRef());
				}
				printer.println(line);
				printer.close();
				writer.close();
			}
			} else {
				printer.println("\n\n\n\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}

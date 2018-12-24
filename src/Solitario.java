import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Solitario {

	public Solitario juego;
	public int type;
	public File file;
	public ArrayList<Mvto> movimientos;
	public ArrayList<Mvto> jugadasValidas;
	public int indice;
	public int loMasLejos;
	public Baraja barajaJuego;
	public String tipoBaraja;
	public Monton noDescubiertas;
	public Monton Descubiertas;
	public Monton temp;
	public ArrayList<Monton> montonesJuego;
	public ArrayList<Monton> montonesSolucion;
	public ArrayList<Integer> montonesVacios;
	public ArrayList<Monton> montones;
	
	public Solitario(){
		juego=this;
	}
	
	public Solitario(int type){
		this.type=type;
		switch (type){
		case 0:
			this.tipoBaraja = "BarajaE";
			this.juego = new SolitarioSaltos(tipoBaraja);
			break;
		case 1:
			this.tipoBaraja = "BarajaF";
			this.juego = new SolitarioClasico(tipoBaraja);
			break;
		}
	}
	
	public Solitario cargarSolitario(){
		JFileChooser fileChooser=new JFileChooser();
		Scanner scanner;
		String line= new String();
		int result = fileChooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION){
			this.file = fileChooser.getSelectedFile();
			try {
				scanner = new Scanner(file);
				line=scanner.nextLine();
				if(line.equals("Solitario saltos")){
					this.type=0;
					juego = new SolitarioSaltos(file);
				} else if(line.equals("Solitario clásico")){
					this.type=1;
					juego = new SolitarioClasico(file);
				}
			} catch (FileNotFoundException e) {
				System.exit(-1);
				e.printStackTrace();
			}
		}
		return juego;
	}
	
	public boolean guardarSolitario(int como){
		return false;
	}

	public void hacerMovimiento(Mvto move, int deshaz) {
		
	}
	
	public void deshacerMovimiento(Mvto move){
		
	}

	public boolean resolverSolitario(int orden) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mvtoCorrecto(Mvto mvto) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean montonesSolicionCompletos(){
		return false;
	}
}

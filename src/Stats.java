import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Stats {

	public dStats dialogStats;
	public int intentosC;
	public int intentosS;
	public int exitosC;
	public int exitosS;
	public File file;
	public static boolean appendflse=false;
	
	public Stats(){
			JFileChooser fileChooser=new JFileChooser();
			int result = fileChooser.showOpenDialog(null);
			if(result==JFileChooser.APPROVE_OPTION){
				this.file = fileChooser.getSelectedFile();
				//readFichero(file);
			}
			this.intentosC=0;
			this.intentosS=0;
			this.exitosC=0;
			this.exitosS=0;
	}
	
	public void setFile(){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setDialogTitle("Abrir archivo de estadísticas");
		int result = fileChooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION){
			this.file = fileChooser.getSelectedFile();
		}
	}
	
	public boolean readFichero(File file){
		String linea=new String();
		
		try {
			this.file=file;
			Scanner parser = new Scanner(file);
			parser.nextLine();
			linea=parser.nextLine();
			if(linea.equals("Solitario saltos")){
				intentosS=parser.nextInt();
				exitosS=parser.nextInt();
			}
			linea=parser.nextLine();
			if(linea.equals("Solitario clásico")){
				intentosC=parser.nextInt();
				exitosC=parser.nextInt();
			}
			parser.close();
		} catch (IOException ex){
			intentosC=0;
			intentosS=0;
			exitosC=0;
			exitosS=0;
			return false;
		}
		dialogStats = new dStats();
		dialogStats.lblExitosClasico.setText("Exitos: " + exitosC);
		dialogStats.lblIntentosClasico.setText("Intentos: " + intentosC);
		dialogStats.lblExitosSaltos.setText("Exitos: " + exitosS);
		dialogStats.lblIntentosSaltos.setText("Intentos: " + intentosS);
		return true;
	}
	
	public boolean writeFichero(File file, int como){
		JFileChooser fileChooser=new JFileChooser();
		if (file==null || como==1){
			fileChooser.setDialogTitle("Guardar estadisticas como..");
			int result = fileChooser.showSaveDialog(null);
			if(result==JFileChooser.APPROVE_OPTION){
				this.file = fileChooser.getSelectedFile();
			}
		}
		try {
			FileWriter writer = new FileWriter(file);
			PrintWriter printer = new PrintWriter(writer,false);
			printer.println("Solitario saltos");
			printer.println(intentosS);
			printer.println(exitosS);
			printer.println("Solitario clásico");
			printer.println(intentosC);
			printer.println(exitosC);
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

package clienteserver50;

import java.util.*;
import java.io.*;

public class wordCount {

	String frase = "";
	ArrayList<String> lineas;
	ArrayList<objs> lisd;

	// lee lineas totales del archivo
	public ArrayList leerLineas(String paths){
		BufferedReader br = new BufferedReader(new FileReader(paths));
		String linea
		lineas =  new ArrayList();
		while((linea = br.readLine())!= null){
			lineas.add(linea);
		}
		return lineas;
	}

	// lee las palabras de una sola linea y las compara
	public ArrayList leePalabras(String linea){
		String temp[] = linea.split(" ");
		lisd = new ArrayList();
		int i;
		for(i=0; i < linea.length(); i++){
			int index = verifica(linea[i]);
			if(index==-1){
				objs palabra = new objs();
				palabra.setobject(linea[i], (double)1);
				lisd.add(palabra);
			}
			else{
				lisd[index].cant += 1;
			}
		}
		return lisd;
	}

	// verifica si la palabra esta en la linea
	public int verifica(string palabra){
		int i;
		for(i=0; i < lisd.length(); i++){
			if(lisd[i].word == palabra) return i;
			else return -1;
		}
	}

	public ArrayList subArray(ArrayList neo, int ini, int end){
		ArrayList<String> sub = new ArrayList();
		int h;
		for(h=ini; h < end, h++){
			sub.add(neo[h]);
		}
		return sub;

	}

}

package clienteserver50;
import java.io.Serializable;
public class Mensaje implements Serializable{
	private static final long serialVersionUID = -5399605122490343339L;
	public String str;
	public float m1[][],m2[][];
	public float res[][];

	public Mensaje(){
	}

	public Mensaje(String s){
		str=s;
	}

	public Mensaje(float a[][]){
		res=a;
	}

	public Mensaje(float a[][],String s){
		res=a;
		str=s;
	}
	
	public Mensaje(float a[][],float b[][]){
		m1=a;
		m2=b;
	}
}

package clienteserver50;

public class objs {

	String word ="";
	double cant ;

	public objs(){}

	public void setobject(String palabra, double cantidad){
		word = palabra;
		cant = cantidad;
	}

	public String getWord(){
		return word;
	}

	public double getCantidad(){
		return cant;
	}

}
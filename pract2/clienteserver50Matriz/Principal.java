package clienteserver50;

public class Principal {
    float a,b;

    public Principal(float a,float b){
    	this.a = a;
    	this.b = b;
    }

    public static void main(String arg[]){
    	new Principal(1,2).inicio();
    }

    public float funcion(float x){
        return x;
    }

    public float inicio(){
    	int num_hilos = 1; // define numero de hilos
    	float a = this.a;
    	float b = this.b;
    	float parte = (b-a)/num_hilos;
    	float suma = 0;
    	Hilo[] hilos = new Hilo[num_hilos];
    	for(int i=0;i<num_hilos;i++){
    		if(i!=num_hilos-1){
    			hilos[i] = new Hilo(i*parte,(i+1)*parte,i);
    			hilos[i].start();
    		}else{
    			hilos[i] = new Hilo(i*parte,b,i);
    			hilos[i].start();
    		}
    	}
    	
    	for(int i=0;i<num_hilos;i++){
    		try{
	    		hilos[i].join();
	    	}catch (Exception e){}
    	}
    	for(int i=0;i<num_hilos;i++){
    		suma += hilos[i].getSuma();
    	}
    	System.out.println("Suma Hilos: "+a+" , "+b+" : "+suma);
    	
    	return suma;
    }

    class Hilo extends Thread{
        float ini,fin,id;
        int num_seg=200;
        public float sum=0;
        
        public Hilo(float ini_,float fin_,float id_){
            ini=ini_;fin=fin_;id=id_;
        }

        public void run(){
        	float paso = (fin-ini)/num_seg;
        	//System.out.println(paso);
        	float a,b;
        	System.out.println("inicio"+id+":-->:"+ini+","+fin+" "+sum);
        	a = funcion(ini);
            for (float x = ini+paso; x <= fin; x=x+paso){
            	b = funcion(x);
                float t = paso*((a+b)/2f);
                sum +=t;
                System.out.println(id+":-->: a="+a+" b="+b+"   x="+x+" paso="+paso+",  t="+t+"  sum="+sum);
                a = b;
            } 
            System.out.println("fin"+id+":-->:"+sum+" "+sum);
        }

        public float getSuma(){
            return sum;
        }
    }
}

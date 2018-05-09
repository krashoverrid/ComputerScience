package clienteserver50;

public class Matriz {
    //double[] stotal;
    float matrizA[][],matrizB[][];
    public Matriz(){
    }
    
    public static void main(String arg[]){
    	//new Matriz().inicio();
    }
    public float funcion(float x){
        //return x*x;
        return x;
    }
    public void setMatriz(float a[][],float b[][]){
    	matrizA=a;
    	matrizB=b;
    }
    public void dividirMatriz(){
    	System.out.println("trabajo");
    	
    	float ma[][]=matrizA;
    	int af=matrizA.length, ac=matrizA[0].length;
    	
    	float mb[][]=matrizB;
    	int bf=matrizB.length, bc=matrizB[0].length;
    	int num_hilos=50;
    	int num_filas=af;
    	if(num_filas<num_hilos)num_hilos=num_filas;
    	int paso=num_filas/num_hilos;
    	Hilo[] hilos = new Hilo[num_hilos];
    	System.out.println("Matriz Separando: paso="+paso+" ... filas="+num_filas);
    	int i;
    	for(i=0;i<num_hilos;i++){
    		if(i!=num_hilos-1){
    			int ta=i*paso,tb=(i+1)*paso;
    			hilos[i] = new Hilo(subMatrizFilas(ma,ta,tb),mb,i);
    			//hilos[i].start();
    		}else{
    			int ta=i*paso,tb=num_filas;
    			hilos[i] = new Hilo(subMatrizFilas(ma,ta,tb),mb,i);
    			//hilos[i].start();
    		}
    	}
    	//System.out.println("Matriz iniciando hilos");
    	for(i=0;i<num_hilos;i++) hilos[i].start();
    	System.out.println("Respuestas: "+i);
    	for(i=0;i<num_hilos;i++){
    		try{
	    		hilos[i].join();
	    	}catch (Exception e){}
    	}
    	
    	System.out.println("Matriz Resultante: ");
    	
    }

    public float inicio(){
    	//System.out.println("bien");
    	int af=8, ac=7;
    	float ma[][]=generaMatriz(af,ac);
    	int bf=7, bc=8;
    	float mb[][]=generaMatriz(bf,bc);
    	imprimirMatriz(ma);
    	imprimirMatriz(mb);
    	System.out.println("suma uno");
    	imprimirMatriz(multiplica(ma,mb));
    	System.out.println("");
    	int num_hilos=4;
    	int num_filas=af;
    	int paso=num_filas/num_hilos;
    	Hilo[] hilos = new Hilo[num_hilos];
    	for(int i=0;i<num_hilos;i++){
    		if(i!=num_hilos-1){
    			int ta=i*paso,tb=(i+1)*paso;
    			hilos[i] = new Hilo(subMatrizFilas(ma,ta,tb),mb,i);
    			hilos[i].start();
    		}else{
    			int ta=i*paso,tb=num_filas;
    			hilos[i] = new Hilo(subMatrizFilas(ma,ta,tb),mb,i);
    			hilos[i].start();
    		}
    	}
    	
    	for(int i=0;i<num_hilos;i++){
    		try{
	    		hilos[i].join();
	    	}catch (Exception e){}
    	}
    	float suma=0;
    	float res[][]=new float[af][bc];
    	int im=0;//filas
    	for(int i=0;i<num_hilos;i++){
    		float m1[][] = hilos[i].getMatriz();
    		int i1=0;
    		for(i1=0;i1<m1.length;i1++)
    			for(int i2=0;i2<m1[i1].length;i2++)
    				res[im+i1][i2]=m1[i1][i2];
    		im+=i1;
    	}
    	System.out.println("Matriz Hilos: " + num_hilos);
    	imprimirMatriz(res);
    	
    	return 0;
    }

    public float[][] generaMatriz(int n,int m){
    	float matriz[][] = new float[n][m];
    	for(int i=0;i<n;i++){
    		for(int j=0;j<m;j++){
    			matriz[i][j]=(float)(Math.random());
    			//System.out.println(":"+matriz[i][j]);
    		}
    	}
        //imprimirMatriz(matriz);
    	return matriz;
    }

    public float[][] multiplica(float a[][],float b[][]){
    	if( a==null || b==null) return null;
    	// verificar que las matrices sean rectangulares
    	for(int i=1;i<a.length;i++)
    		if(a[i-1].length != a[i].length) return null;
    	for(int i=1;i<b.length;i++)
    		if(b[i-1].length != b[i].length) return null;
    	// que la multiplicacion sea posible
    	if(a.length==0){
    		if(b.length==0) return new float[0][0];
    		return null;
    	}
    	if(b.length==0){
    		if(a.length==0) return new float[0][0];
    		return null;
    	}
    	// ya tiene un tamaño rectangular mayor a cero
    	if(a[0].length != b.length) return null; // ya estamos seguro que a.length>0
    	// bien ya es posible multiplicar
    	
    	int n = a.length;
    	int m = b[0].length;
    	float r[][] = new float[n][m];
    	for(int i=0;i<n;i++){
    		for(int j=0;j<m;j++){
    			float t = 0;
    			for(int k=0;k<b.length;k++)
    				t += a[i][k]*b[k][j];
    			r[i][j] = t;
    		}
    	}
    	
    	return r;
    }
    public float[][] subMatrizFilas(float m[][],int fi,int fj){
    	if(!esMatriz(m))return null;
    	if(fi<0 || fj<0 || fj>m.length || fj<fi) return null;
    	if(m.length==0 || fi==fj) return new float[0][0];
    	//si es posible obtener
    	float nm[][] = new float[fj-fi][m[0].length];
    	
    	for(int i=0;i<fj-fi;i++){
    		for(int j=0;j<m[i].length;j++){
    			nm[i][j]=m[fi+i][j];
    		}
    	}
    	return nm;
    }
    public void imprimirMatriz(float m[][]){
    	if(m==null){
    		System.out.println("null");
    		return ;
    	}
    	String s="";
    	for(int i=0;i<m.length;i++){
    		for(int j=0;j<m[i].length;j++){
    			s = s+m[i][j]+" ";
    		}
    		s+="\n";
    	}
    	System.out.println(s);
    }
    public boolean esMatriz(float m[][]){
    	if(m==null) return false;
    	if(m.length==0) return true;//matriz vacia
    	for(int i=1;i<m.length;i++)
    		if(m[i-1].length != m[i].length) return false;
    	return true;
    }
    //*********************************************************
    class Hilo extends Thread{
        float ini,fin,id;
        float ma[][],mb[][];
        float mres[][];
        public float sum=0;
        
        public Hilo(float ma[][],float mb[][],float id_){
            this.ma=ma;
            this.mb=mb;
            this.id=id_;
        }
        public void run(){
        	mres=multiplica(ma,mb);
        }
        public float[][] getMatriz(){return mres;}
    }
}

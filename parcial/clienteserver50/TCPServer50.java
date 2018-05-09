
package clienteserver50;
import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;


public class TCPServer50 {
    private String message;
    
    int nrcli = 0; // numero de id del cliente

    public static final int SERVERPORT = 4444;
    //private OnMessageReceived messageListener = null;
    public OnMessageReceived messageListener = null;
    private boolean running = false;
    //TCPServerThread50[] sendclis = new TCPServerThread50[10];
    private ArrayList<TCPServerThread50> als; // manejar los clientes
    private ArrayList<Integer> ali; //manejar los id de los clientes
    

    PrintWriter mOut;
    BufferedReader in;
    
    ServerSocket serverSocket;
    wordCount sube;

    //el constructor pide una interface OnMessageReceived
    public TCPServer50(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
        
    }
    
    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    // recibe de la terminal del servidor
    //public void sendMessageTCPServer(String message){
    public void sendMessageTCPServer(Mensaje message){
        for (int i = 1; i <= nrcli; i++) {
            //sendclis[i].sendMessage(message); //update u1
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }
    
    // escucha de los servidores qeu manejan los clientes
    //public void sendMessageTCPServerWorker(String message){
    public void sendMessageTCPServerWorker(Mensaje message){
    	// aca es
    	//String s[] = message.split(" ");
    	String s[] = message.str.split(" ");
    	float a = Float.parseFloat(s[1]);
    	//float a = Float.parseFloat(s[1]);
    	float b = Float.parseFloat(s[2]);
    	//float particion = (b-a)/nrcli;

    	float parte = (b-a)/(nrcli-1);
    	for (int i = 0; i <= nrcli-2; i++) {
        
            //sendclis[i].sendMessage(message);

            //System.out.println("ENVIANDO A JUGADOR " + (i));
            float ia = a + parte*i;
            float ib = a + parte*(i+1);
            //sendclis[i+2].sendMessage("TRA "+ia+" "+ib); //update u1
	        System.out.println("ENVIANDO A: " + (i+2));

        }
    }
    // aqui se decide a quienes mandar a trabajar
    //public void realizarTrabajo(float a, float b){
    public void realizarTrabajo(ArrayList<String> temp){
    	if(als.size()==0){
            System.out.println("No tiene clientes el servidor");
            wordCount m = new wordCount();
            /*Matriz m = new Matriz();
            m.setMatriz(a_,b_);
            m.dividirMatriz();*/
        }else{
            int num_hilos = als.size();//clientes
            int num_filas = temp.length();
            int paso = num_filas/num_hilos;
            for(int i=0;i<num_hilos;i++){
                if(i!=num_hilos-1){
                    int ta=i*paso,tb=(i+1)*paso;
                    als.get(i).sendMessage(new Mensaje(m.subArray(temp, ta, tb)));
                }else{
                    int ta=i*paso,tb=num_filas;
                    als.get(i).sendMessage(new Mensaje(m.subArray(temp, ta, tb)));
                }
            }
        }


        /*
    	if(als.size()==0){
			System.out.println("es:");
			Matriz m = new Matriz();
			m.setMatriz(a_,b_);
			m.dividirMatriz();
    	}else{
    		// numero de clientes debe ser mayor a las filas
    		Matriz m = new Matriz();
    		
    		float ma[][]=a_;
			int af=a_.length,ac=a_[0].length;
			
			float mb[][]=b_;
			int bf=b_.length,bc=b_[0].length;
			
    		int num_hilos=als.size();//clientes
			int num_filas=af;
			int paso=num_filas/num_hilos;
			//Hilo[] hilos = new Hilo[num_hilos];
			//System.out.println("A cliente separando");
			for(int i=0;i<num_hilos;i++){
				if(i!=num_hilos-1){
					int ta=i*paso,tb=(i+1)*paso;
					als.get(i).sendMessage(new Mensaje(m.subMatrizFilas(ma,ta,tb),mb));
				}else{
					int ta=i*paso,tb=num_filas;
					als.get(i).sendMessage(new Mensaje(m.subMatrizFilas(ma,ta,tb),mb));
				}
			}
			//System.out.println("A cliente enviado");
    	}
    	System.out.println("Num de clientes: "+als.size());*/

    }
    
    public void run(){
        running = true;
        try{
            System.out.println("TCP Server"+"S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);
            als = new ArrayList<TCPServerThread50>();
            ali = new ArrayList<Integer>();
            
            while(running){
                Socket client = serverSocket.accept();
                System.out.println("TCP Server"+"S: Receiving...");
                ///nrcli++;
                System.out.println("Engendrado " + nrcli);
                ///sendclis[nrcli] = new TCPServerThread50(client,this,nrcli,sendclis);
                als.add(new TCPServerThread50(client,this,nrcli,als));
                ali.add(new Integer(nrcli));
                nrcli++;
                //Thread t = new Thread(sendclis[nrcli]);
                Thread t = new Thread(als.get(als.size()-1));
                t.start();
                System.out.println("Nuevo conectado:"+ nrcli+" jugadores conectados");
            }
            
        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    /*
    public  TCPServerThread50[] getClients(){
        return sendclis;
    } //*/

    public interface OnMessageReceived {
        //public void messageReceived(String message);
        public void messageReceived(Mensaje message);
    }
}


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

    //el constructor pide una interface OnMessageReceived
    public TCPServer50(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
        
    }
    
    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    // recibe de la terminal del servidor
    public void sendMessageTCPServer(String message){
        for (int i = 1; i <= nrcli; i++) {
            //sendclis[i].sendMessage(message); //update u1
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }
    
    // escucha de los servidores qeu manejan los clientes
    public void sendMessageTCPServerWorker(String message){
    	// aca es
    	String s[] = message.split(" ");
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
	        System.out.println("ENVIANDO A JUGADOR " + (i+2));

        }
    }
    // aqui se decide a quienes mandar a trabajar
    public void doWork(double a, double b, double part) throws InterruptedException{
        System.out.println("Trabajo enviado");
        int n_h=200;
        double parts=2000000000;
            
        if(als.size()==0){
            double var=(b-a)/n_h;
            Integral[] test= new Integral[n_h];
            double[] resu= new double[n_h];
            double ini=a, sig;
            double tt=0;

            for(int i=0;i<n_h;i++){
                sig=ini+var;
                System.out.println(ini+" "+sig+" "+parts/n_h);

                test[i] = new Integral(ini,sig, parts/n_h);
                test[i].start();
                ini=sig;
            }

            for (int i=0; i<n_h;i++ ) {
                test[i].join();
                
            }
            for (int i=0;i<n_h;i++){
                tt=tt+test[i].getval();
            }

            System.out.println("Integral calculada "+tt);
        }else{
            // realizan los trabajos los hilos
            double parte = (b-a)/als.size();
            double ini=a, sig;
            double var=(b-a)/n_h;
            for(int i=0;i<als.size();i++){
                sig=ini+parte;
                System.out.println("Resultado:\n "+ini+" "+sig);
                als.get(i).sendMessage("TRA "+ini+" "+sig);
                ini=sig;
                
            }

            /*nome jales pes*/
            //double var=(b-a)/n_h;
            Integral[] test= new Integral[n_h];
            double[] resu= new double[n_h];
            ini=a;
            double tt=0;

            for(int i=0;i<n_h;i++){
                sig=ini+var;
                
                test[i] = new Integral(ini,sig, parts/n_h);
                test[i].start();
                ini=sig;
            }

            for (int i=0; i<n_h;i++ ) {
                test[i].join();
                
            }
            for (int i=0;i<n_h;i++){
                tt=tt+test[i].getval();
            }

            System.out.println("Integral calculada "+tt);

        }
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
        public void messageReceived(String message);
    }
}

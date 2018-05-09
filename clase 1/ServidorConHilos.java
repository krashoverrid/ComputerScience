
//package pkg03serechil01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ServidorConHilos {
	public ArrayList<Socket> als;
    public static void main(String[] args) {
    	new ServidorConHilos().inicio();
    }
    public void inicio(){
        try {
        	// mandar
        	Hilo hilo = new Hilo();
        	hilo.start();
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            als = new ArrayList<Socket>();
            while (true) {                
                Socket entrante = s.accept();
                als.add(entrante);
                System.out.println("Cliente nuevo " + i);
                Runnable r = new ManejadorConHilos(entrante , i);
                Thread t = new Thread(r);
                t.start();
                i++;                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    class Hilo extends Thread{
        public Hilo(){
        }
        public void run(){
        	Scanner sc = new Scanner(System.in);
        	System.out.println("Servidor en linea");
        	
        	while(sc.hasNextLine()){
                String lineaout = sc.nextLine();
                System.out.println("Servidor: "+lineaout);
                //out.println("Servidor:"+lineaout);
                for(int i=0;i<als.size();i++){
                	try{
		            	Socket entrante = als.get(i);
		            	OutputStream secuenciaDeSalida = entrante.getOutputStream();
			            PrintWriter out = new PrintWriter(secuenciaDeSalida,true);
			            out.println("Servidor: "+lineaout);
			        }catch(IOException e){
			        	
			        }finally{
			        	//entrante.close();
			        }
                }
            }
        }
    }
}
//telnet 127.0.0.1 8189

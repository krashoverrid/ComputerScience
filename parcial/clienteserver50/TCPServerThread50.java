/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteserver50;

import clienteserver50.TCPServer50.OnMessageReceived;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;

import java.net.Socket;

import java.util.ArrayList;


public class TCPServerThread50 extends Thread{
    
    private Socket client;
    private TCPServer50 tcpserver;
    private int clientID;                 
    private boolean running = false;
    //public PrintWriter mOut;
    public ObjectOutputStream mOut;
    //public BufferedReader in;
    public ObjectInputStream in;
    private OnMessageReceived messageListener = null;
    //private String message;
    private Mensaje message;
    //TCPServerThread50[] cli_amigos;
    private ArrayList<TCPServerThread50>cli_amigos;

    //public TCPServerThread50(Socket client_, TCPServer50 tcpserver_, int clientID_,TCPServerThread50[] cli_ami_) {
    public TCPServerThread50(Socket client_, TCPServer50 tcpserver_, int clientID_,ArrayList<TCPServerThread50> cli_ami_) {
        this.client = client_;
        this.tcpserver = tcpserver_;
        this.clientID = clientID_;
        this.cli_amigos = cli_ami_;
        //message = new Mensaje();
    }
    
     public void trabajen(int cli){      
         //mOut.println("TRABAJAMOS ["+cli+"]...");
         //mOut.writeObject(new Mensaje("TRABAJAMOS ["+cli+"]..."));
         try{
            //out.writeObject(message);
            mOut.writeObject(new Mensaje("TRABAJAMOS ["+cli+"]..."));
        } catch (IOException ex) {
			System.out.println("eee1");
		}
    }
    
    public void run() {
        running = true;
        try {
            try {               
                boolean soycontador = false;//
                //mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                mOut = new ObjectOutputStream(client.getOutputStream());
                System.out.println("TCP Server"+ "C: Sent.");
                messageListener = tcpserver.getMessageListener();
                //in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                in = new ObjectInputStream(client.getInputStream());
                // escucha mensajes del cliente
                while (running) {
                    //message = new Mensaje(in.readLine());//
                    message = (Mensaje)in.readObject();
                    
                    //if (message.str != null && messageListener != null) {
                    if (message != null && messageListener != null) {
                    	System.out.println("Mensaje del cliente: "+clientID);
                        messageListener.messageReceived(message);
                    }else{
                    	break;
                    	// aqui se detecta que el cliente se desconecto
                    }
                    //System.out.println("Eco de:" + clientID +" dice:" + message);
                 
                    message = null;
                }
                System.out.println("RESPONSE FROM CLIENT"+ "S: Received Message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("TCP Server"+ "S: Error"+ e);
            } finally {
                client.close();
            }

        } catch (Exception e) {
            System.out.println("TCP Server"+ "C: Error"+ e);
        }
    }
    
    public void stopClient(){
        running = false;
    }
    
    //public void sendMessage(String message){//funcion de trabajo
    public void sendMessage(Mensaje message){//funcion de trabajo
        //if (mOut != null && !mOut.checkError()) {
        if (mOut != null ) {
            //mOut.println( message);
            //mOut.flush();
            //mOut.writeObject(message);
            try{
		        //out.writeObject(message);
		        //mOut.writeObject(new Mensaje("TRABAJAMOS ["+cli+"]..."));
		        mOut.writeObject(message);
		    } catch (IOException ex) {
				System.out.println("eee2");
			}
        }
    }
    
}

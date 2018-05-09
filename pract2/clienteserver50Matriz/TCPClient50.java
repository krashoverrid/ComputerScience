/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteserver50;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient50 {

    //private String servermsj;
    private Mensaje servermsj;
    public  String SERVERIP;
    public static final int SERVERPORT = 4444;
    //private OnMessageReceived mMessageListener = null;
    public OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    //PrintWriter out;
    ObjectOutputStream out;
    //BufferedReader in;
    ObjectInputStream in;

    public TCPClient50(String ip,OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }
    //public void sendMessage(String message){
    public void sendMessage(Mensaje message){
        //if (out != null && !out.checkError()) {
        if (out != null ) {
            //out.println(message);
            //out.flush();
            try{
	            out.writeObject(message);
	        } catch (IOException ex) {
				System.out.println("eee");
			}
        }
    }
    public void stopClient(){
        mRun = false;
    }
    public void run() {
        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            System.out.println("TCP Client"+ "C: Conectando...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("TCP Client"+ "C: Sent.");
                System.out.println("TCP Client"+ "C: Done.");
                //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                in = new ObjectInputStream(socket.getInputStream());
                while (mRun) {
                	System.out.println("Cliente: Esperando mensaje");
                    //servermsj = in.readLine();
                    servermsj = (Mensaje)in.readObject();
                    if (servermsj != null && mMessageListener != null) {
                    	// recive el mensaje y procesa en Cliente50
                        //mMessageListener.messageReceived(servermsj);
                        //mMessageListener.messageReceived(new Mensaje(servermsj));
                        mMessageListener.messageReceived(servermsj);
                    }
                    servermsj = null;
                    System.out.println("Cliente: Esperando mensaje Fin");
                }
            } catch (Exception e) {
                System.out.println("TCP"+ "S: Error"+e);

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP"+ "C: Error"+ e);
        }
    }
    public interface OnMessageReceived {
        //public void messageReceived(String message);
        public void messageReceived(Mensaje message);
    }
}


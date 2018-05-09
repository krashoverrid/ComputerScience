
package clienteserver50;

import java.util.Scanner;

public class Servidor50 {
   TCPServer50 mTcpServer;
   Scanner sc;
   public static void main(String[] args) {
       Servidor50 objser = new Servidor50();
       objser.iniciar();
   }
   void iniciar(){
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                      mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived(){
                            @Override
                            //public void messageReceived(String message){
                            public void messageReceived(Mensaje message){
                                ServidorRecibe(message);
                            }
                        }
                    );
                    mTcpServer.run();                   
                }
            }
        ).start();
        //-----------------
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        while( !salir.equals("s")){
        	// detectar que tarea hacer mandar la tarea, definir una funcion
            salir = sc.nextLine();
            //ServidorEnvia(salir);
            //ServidorEnvia(new Mensaje(salir)); //solo envia el mensaje,  no realiza trabajo
            // este hilo se encarga de escuchar
            String s[] = salir.split(" ");
            if (s.length > 4){
            	if( (s[0].equals("tra") || s[0].equals("TRA"))){
            		System.out.println("Generando Datos");
            		int a1 = Integer.parseInt(s[1]);
            		int b1 = Integer.parseInt(s[2]);
            		int a2 = Integer.parseInt(s[3]);
            		int b2 = Integer.parseInt(s[4]);

            		Matriz m = new Matriz();
                System.out.println("Matriz A [" + a1 + "][" + b1 +"]");
            		System.out.println("Matriz B [" + a2 + "][" + b2 +"]");
            		float m1[][] = m.generaMatriz(a1,b1);
            		float m2[][] = m.generaMatriz(a2,b2);
            		
            	
            		System.out.println("Generando Matrices Aleatorias");
	            	mTcpServer.realizarTrabajo(m1,m2);
	            	System.out.println("----------------");
            	}
            }else{
            	System.out.println("No es el formato");
            }
       }
       System.out.println("Servidor bandera 02"); 
   
   }
   //void ServidorRecibe(String llego){
   void ServidorRecibe(Mensaje llego){
       //System.out.println("SERVIDOR40 El mensaje:" + llego);
       //System.out.println("SERVIDOR40 El mensaje del cliente es:" + llego.str);
       System.out.println("Respuesta: "+llego.str);
       
   }
   //void ServidorEnvia(String envia){
   void ServidorEnvia(Mensaje envia){
        if (mTcpServer != null) {
            mTcpServer.sendMessageTCPServer(envia);
        }
   }
}

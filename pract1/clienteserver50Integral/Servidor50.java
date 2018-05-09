
package clienteserver50;

import java.util.Scanner;

public class Servidor50 {
   TCPServer50 mTcpServer;
   Scanner sc;
   public static void main(String[] args) throws InterruptedException{
       Servidor50 objser = new Servidor50();
       objser.iniciar();
   }
   void iniciar() throws InterruptedException{
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                      mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message){
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
            ServidorEnvia(salir);
            // este hilo se encarga de escuchar
            String s[] = salir.split(" ");
            if (s.length > 2){
            	if( (s[0].equals("tra") || s[0].equals("TRA"))){
            		double a1 = Double.parseDouble(""+s[1]);
            		double b1 = Double.parseDouble(""+s[2]);
	            	//mTcpServer.realizarTrabajo(a1,b1);
                mTcpServer.doWork(a1,b1,2000000.0);
            	}
            }else{
            	System.out.println("No es el formato");
            }
       }
       System.out.println("Servidor bandera 02"); 
   
   }
   void ServidorRecibe(String llego){
       System.out.println("SERVIDOR40 El mensaje:" + llego);
   }
   void ServidorEnvia(String envia){
        if (mTcpServer != null) {
            mTcpServer.sendMessageTCPServer(envia);
        }
   }
}

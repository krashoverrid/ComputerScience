
package clienteserver50;

import java.util.Scanner;

class Cliente50{
    TCPClient50 mTcpClient;
    Scanner sc;
    public static void main(String[] args)  {
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
    }
    void iniciar(){
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                    //mTcpClient = new TCPClient50("192.168.20.99",
                    //mTcpClient = new TCPClient50("192.168.0.104",
                    mTcpClient = new TCPClient50("localhost",
                        new TCPClient50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message){
                                ClienteRecibe(message);
                            }
                        }
                    );
                    mTcpClient.run();                   
                }
            }
        ).start();
        //---------------------------
       
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }
        System.out.println("Cliente bandera 02");
    
    }
    void ClienteRecibe(String llego){
        System.out.println("CLINTE50 El mensaje::" + llego);
        
            if ( llego.trim().contains("TRA")){
                String arrayString[] = llego.split("\\s+");
                double rpta = 0;
                if (arrayString[1].equals("TRA")){
                    System.out.println("TCPClient recibo trabajo:");
                    int dat = Integer.parseInt(arrayString[0]);
                    rpta = funcion(dat);
                    System.out.println("TCPClient resulado:" + rpta);                                                    
                }
                ClienteEnvia("La Respuesta es:" + rpta);
            }
    }
    void ClienteEnvia(String envia){
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }
    double funcion(int fin){
        double sum = 0;
        for(int j = 0; j<=fin;j++ ){
            sum = sum + Math.sin(j*Math.random());
        }
        return sum;
    }

}

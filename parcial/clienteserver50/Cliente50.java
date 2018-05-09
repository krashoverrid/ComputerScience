
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
                    mTcpClient = new TCPClient50("192.168.0.110",
                    //mTcpClient = new TCPClient50("127.0.0.1",
                        new TCPClient50.OnMessageReceived(){
                            @Override
                            //public void messageReceived(String message){
                            public void messageReceived(Mensaje message){
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
            //ClienteEnvia(salir);
            ClienteEnvia(new Mensaje(salir));
        }
        System.out.println("Cliente 02");
    
    }
    //void ClienteRecibe(String llego){
    void ClienteRecibe(Mensaje llego){
        

        System.out.println("**********************\nMensaje recivido");
        wordCount m = new wordCount();
        if(llego != null){
        	System.out.println("nuevo mensaje");

            ArrayList<objs> ff =  llego.cade;

            ArrayList<objs> das = m.leePalabras(ff);
            ClienteEnvia(new Mensaje(das,"confirmado"));

        	/*float res[][] = m.multiplica(llego.m1,llego.m2);
        	
        	//Matriz m = new Matriz();
        	m.setMatriz(llego.m1,llego.m2);
        	m.dividirMatriz();
        	ClienteEnvia(new Mensaje(res,"confirmado"));*/
        }
      
    }
    //void ClienteEnvia(String envia){
    void ClienteEnvia(Mensaje envia){
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }
    double funcion1(float fin){
        //return sum;
        return fin;
    }
    public float f(float x){
        return x;
    }
    double funcion(float ini,float fin){
    	float paso = (fin-ini)/200;
    	//System.out.println(paso);
    	//*
    	float a,b;
    	a = f(ini);
    	float sum = 0;
        for (float x = ini+paso; x <= fin; x=x+paso){
        	b = f(x);
            sum += paso*((a+b)/2.0);
            a = b;
        } 
        return sum;
    }

}

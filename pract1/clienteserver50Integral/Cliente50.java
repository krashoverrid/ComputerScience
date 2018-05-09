
package clienteserver50;

import java.util.Scanner;

class Cliente50{
    TCPClient50 mTcpClient;
    Scanner sc;
    public static void main(String[] args)  throws InterruptedException{
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
    }
    void iniciar() throws InterruptedException{
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                    //mTcpClient = new TCPClient50("192.168.20.99",
                    mTcpClient = new TCPClient50("192.168.0.110",
                    //mTcpClient = new TCPClient50("127.0.0.1",
                        new TCPClient50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message) {
                                try{
                                    ClienteRecibe(message);
                                }catch(InterruptedException e){
                                    
                                }
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
    void ClienteRecibe(String llego) throws InterruptedException{
        System.out.println("CLINTE50 El mensaje::" + llego);
        
            if ( llego.trim().contains("TRA") || llego.trim().contains("tra")){
                String s[] = llego.split(" ");
                double rpta = 0;
                double a = Double.parseDouble(""+s[1]);
                double b = Double.parseDouble(""+s[2]);
               

                int n_h=100;
                double var=(b-a)/n_h;
                Integral[] test= new Integral[n_h];
                double[] resu= new double[n_h];
                double ini=a, sig;
                double tt=0;
               
                for(int i=0;i<n_h;i++){
                    sig=ini+var;
                    //System.out.println(ini+" "+sig+" "+2000000);

                    test[i] = new Integral(ini,sig, 2000000);
                    test[i].start();
                    ini=sig;
                }

                for (int i=0; i<n_h;i++ ) {
                    test[i].join();
                    
                }
                for (int i=0;i<n_h;i++){
                    tt=tt+test[i].getval();
                }
                
                rpta=tt;
                /*
                String arrayString[] = llego.split("\\s+");
                double rpta = 0;
                if (arrayString[1].equals("TRA")){
                    System.out.println("TCPClient recibo trabajo:");
                    int dat = Integer.parseInt(arrayString[0]);
                    rpta = funcion(dat);
                    System.out.println("TCPClient resulado:" + rpta);                                                    
                }*/
                ClienteEnvia("La Respuesta del nodo es :" + rpta);
            }
    }
    void ClienteEnvia(String envia){
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

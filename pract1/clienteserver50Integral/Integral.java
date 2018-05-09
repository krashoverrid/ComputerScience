package clienteserver50;

public class Integral extends Thread{
            private double val;
            private double start;
            private double end;
            private double partition;
            
            public Integral(double start,double end, double partition){
                this.start = start;
                this.end = end;
                this.partition = partition;
                
            }

            public double getval(){
                return val;
            }

            public double f(double x){
                return x;
            }

            double funcion(double start,double end, double partition){
                double paso = (end-start)/partition;
                //System.out.println(paso);
                //System.out.println(partition);
                double a,b;
                a = f(start);
                double sum = 0;
                for (double x = start+paso; x <= end; x=x+paso){
                    b = f(x);
                    sum += paso*((a+b)/2.0);
                    a = b;
                } 
                return sum;
            }
            
            public void run(){
                val= funcion(start,end, partition);   
            }

            
        }
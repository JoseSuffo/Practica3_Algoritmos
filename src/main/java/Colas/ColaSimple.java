package Colas;

public class ColaSimple <T>{
    private int inicio, fin, max;
    private final T[] colaSimple;

    public ColaSimple(){
        inicio = -1;
        fin = -1;
        this.max = 5;
        colaSimple = (T[]) new Object[max];
    }

    public ColaSimple(int max){
        inicio = -1;
        fin = -1;
        this.max = max;
        colaSimple = (T[]) new Object[max];
    }

    public void insertarDato(T dato){
        if(fin<max-1){
            fin++;
            colaSimple[fin] = dato;
            if(fin==-1){
                inicio=0;
            }
        }else{
            System.out.println("Desbordamiento");
        }
    }

    public T eliminarDato(){
        if(inicio!=-1){
            T dato =  colaSimple[inicio];
            if(inicio==fin){
                inicio=-1;
                fin=-1;
            }else{
                inicio++;
            }
            return dato;
        } else{
            System.out.println("Subdesbordamiento");
            return null;
        }
    }

    public T verProximo(){
        if(inicio!=-1){
            return colaSimple[inicio];
        }else{
            System.out.println("Cola Vacía");
            return null;
        }
    }
}
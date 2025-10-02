package Colas;

public class ColaCircular <T> {
    int inicio, fin, max;
    T[] colaCircular;

    public ColaCircular(int max) {
        inicio = -1;
        fin = -1;
        this.max = max;
        colaCircular = (T[]) new Object[max];
    }

    public ColaCircular() {
        inicio = -1;
        fin = -1;
        colaCircular = (T[]) new Object[5];
    }

    public void insertar(T dato) {
        if ((fin == max - 1 && inicio == 0) || fin + 1 == inicio) {
            System.out.println("Desbordamiento");
        } else {
            if (fin == max - 1) {
                fin = 0;
            } else {
                fin++;
            }
            colaCircular[fin] = dato;
            if (inicio == -1) {
                inicio = 0;
            }
        }
    }

    public T eliminar() {
        T dato = null;
        if (inicio == -1) {
            System.out.println("Subdesbordamiento");
        } else {
            dato = colaCircular[inicio];
            if (inicio == fin) {
                inicio = -1;
                fin = -1;
            } else {
                if (inicio == max - 1) {
                    inicio = 0;
                } else {
                    inicio++;
                }
            }
        }
        return dato;
    }

    public T verProximo() {
        if (inicio == -1) {
            System.out.println("Cola circular vacía");
            return null;
        } else {
            return colaCircular[inicio];
        }
    }

    public int getTamano(){
        if(inicio == -1){
            return 0;
        }
        return fin-inicio+1;
    }

    public void clear(){
        inicio = -1;
        fin = -1;
        colaCircular = (T[]) new Object[max];
    }
}
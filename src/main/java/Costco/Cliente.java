package Costco;

import java.util.Random;

public class Cliente {
    int tiempoPago;
    int tiempoEspera;

    public Cliente(){
        tiempoPago = 0;
        tiempoEspera = 0;
    }

    public void generarTiempoPago(int tiempo){
        Random r = new Random();
        tiempoPago = r.nextInt(tiempo);
    }

    public void generarTiempoEspera(int tiempo){
        Random r = new Random();
        tiempoEspera = r.nextInt(tiempo);
    }
}

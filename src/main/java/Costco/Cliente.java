package Costco;

import java.util.Random;

public class Cliente {
    private int tiempoPago, tiempoEspera, tiempoLlegada, tiempoInicioPago, tiempoSalida;

    public Cliente(){
        tiempoPago = generarTiempoPago();
        tiempoEspera = generarTiempoEspera();
    }

    public int generarTiempoPago(){
        Random r = new Random();
        return r.nextInt(3,6);
    }

    public int generarTiempoEspera(){
        Random r = new Random();
        return r.nextInt(1,3);
    }

    public int getTiempoEsperaTotal(){
        return Math.max(0, tiempoInicioPago-tiempoLlegada);
    }

    public int getTiempoTotalEnSistema(){
        return Math.max(0, tiempoSalida-tiempoLlegada);
    }

    public int getTiempoPagoTotal(){
        return tiempoPago;
    }

    public int getTiempoEspera(){
        return tiempoEspera;
    }

    public int getTiempoLlegada(){
        return tiempoLlegada;
    }

    public int getTiempoInicioPago(){
        return tiempoInicioPago;
    }

    public int getTiempoSalida(){
        return tiempoSalida;
    }

    public void setTiempoLlegada(int tiempoLlegada){
        this.tiempoLlegada = tiempoLlegada;
    }

    public void setTiempoPago(int tiempoPago){
        this.tiempoPago = tiempoPago;
    }

    public void setTiempoEspera(int tiempoEspera){
        this.tiempoEspera = tiempoEspera;
    }

    public void setTiempoInicioPago(int tiempoInicioPago){
        this.tiempoInicioPago = tiempoInicioPago;
    }

    public void setTiempoSalida(int tiempoSalida){
        this.tiempoSalida = tiempoSalida;
    }
}
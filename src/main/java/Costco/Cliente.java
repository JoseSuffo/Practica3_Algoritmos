package Costco;

import java.util.Random;

public class Cliente {
    private int tiempoPago, tiempoLlegada, tiempoInicioPago, tiempoSalida;

    public Cliente(){
        tiempoPago = generarTiempoPago();
    }

    public int generarTiempoPago(){
        Random r = new Random();
        return r.nextInt(3,6);
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

    public void setTiempoLlegada(int tiempoLlegada){
        this.tiempoLlegada = tiempoLlegada;
    }

    public void setTiempoPagoTotal(int tiempoPago){
        this.tiempoPago = tiempoPago;
    }


    public void setTiempoInicioPago(int tiempoInicioPago){
        this.tiempoInicioPago = tiempoInicioPago;
    }

    public void setTiempoSalida(int tiempoSalida){
        this.tiempoSalida = tiempoSalida;
    }
}
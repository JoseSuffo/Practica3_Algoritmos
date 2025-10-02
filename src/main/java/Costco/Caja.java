package Costco;

import Colas.ColaSimple;


public class Caja {
    private ColaSimple<Cliente> cola;
    private int clientesMaximos, numCaja, clientesAtendidos, tiempoAtencion;
    private boolean abierta;

    public Caja(int numCaja) {
        cola  = new ColaSimple<>();
        clientesMaximos = 4;
        this.numCaja = numCaja;
    }

    public void agregarCliente(Cliente cliente) {
        cola.insertarDato(cliente);
    }

    public Cliente quitarCliente() {
        return cola.eliminarDato();
    }

    public int getNumCaja(){
        return numCaja;
    }

    public int getNumClientes(){
        return cola.getTamano();
    }

    public void registrarClienteAtendido(){
        clientesAtendidos++;
    }

    public void registrarTiempoAtencion(int minutos){
        tiempoAtencion+=minutos;
    }

    public boolean cajaAbierta(){
        return abierta;
    }

    public void abrirCaja(){
        abierta = true;
    }

    public void cerrarCaja(){
        abierta = false;
    }

    public int getTiempoAtencion(){
        return tiempoAtencion;
    }

    public int getClientesAtendidos(){
        return clientesAtendidos;
    }
}

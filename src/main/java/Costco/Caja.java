package Costco;

import Colas.ColaSimple;

public class Caja {
    private ColaSimple<Cliente> filaInterna = new ColaSimple<>(100); // capacidad arbitraria
    private Cliente clienteActual;
    private int tiempoRestante;

    private int numCaja;
    private int clientesAtendidos;
    private int tiempoAtencion;
    private boolean abierta;
    public int tiempoAbierta;

    public Caja(int numCaja) {
        this.numCaja = numCaja;
        this.abierta = false;
    }

    public void agregarCliente(Cliente c) {
        filaInterna.insertarDato(c);
    }

    public Cliente atender(int minuto) {
        if (clienteActual == null && !filaInterna.estaVacia()) {
            clienteActual = filaInterna.eliminarDato();
            tiempoRestante = clienteActual.getTiempoPagoTotal();
            clienteActual.setTiempoInicioPago(minuto);
        }

        if (clienteActual != null) {
            tiempoRestante--;
            if (tiempoRestante <= 0) {
                clienteActual.setTiempoSalida(minuto);
                registrarClienteAtendido();
                registrarTiempoAtencion(clienteActual.getTiempoPagoTotal());
                Cliente atendido = clienteActual;
                clienteActual = null;
                return atendido;
            }
        }
        return null;
    }

    public int getNumClientes() {
        return filaInterna.getTamano() + (clienteActual != null ? 1 : 0);
    }

    public int getNumCaja() {
        return numCaja;
    }

    public void registrarClienteAtendido() {
        clientesAtendidos++;
    }

    public void registrarTiempoAtencion(int minutos) {
        tiempoAtencion += minutos;
    }

    public boolean cajaAbierta() {
        return abierta;
    }

    public void abrirCaja() {
        abierta = true;
    }

    public void cerrarCaja() {
        abierta = false;
    }

    public void incrementarTiempoAbierta(){
        if(abierta){
            tiempoAbierta++;
        }
    }

    public int getTiempoAbierta() {
        return tiempoAbierta;
    }
}
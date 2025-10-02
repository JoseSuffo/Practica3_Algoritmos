package Costco;

import Colas.ColaSimple;

import java.util.ArrayList;
import java.util.Random;

public class SimulacionCostco {
    private ArrayList<Caja> cajas;
    private ArrayList<Cliente> clientesAtendidos;
    private ColaSimple<Cliente> filaUnica = new ColaSimple<>(1000);
    private int minutoActual=0;
    private int llegadas=0;
    private String modoFila;

    public SimulacionCostco(String modoFila) {
        cajas = new ArrayList<>();
        clientesAtendidos = new ArrayList<>();
        this.modoFila = modoFila;
        for(int i = 0; i < 12; i++){
            Caja c = new Caja(i+1);
            cajas.add(c);
        }
    }

    public void flujoSimulacion(){
        for (minutoActual = 0; minutoActual < 600; minutoActual++) { // 10 horas * 60 minutos
            generarCliente(minutoActual);
            generarPagos(minutoActual);
            abrirCajaNueva();
            cerrarCajasVacia();
            recolectarEstadisticas();
        }
    }

    public void agregarCliente(Cliente cliente, int caja) {
        for (Caja c : cajas) {
            if (c.getNumCaja() == caja) {
                c.agregarCliente(cliente);
            }
        }
    }

    public void eliminarCliente(int caja) {
        for (Caja c : cajas) {
            if (c.getNumCaja() == caja) {
                c.quitarCliente();
            }
        }
    }

    public boolean comprobarClientesEnCaja(int caja){
        for (Caja c : cajas) {
            if (c.getNumCaja() == caja && c.getNumClientes()>5) {
                return true;
            }
        }
        return false;
    }

    public Caja obtenerMejorCajaDisponible(int caja){
        Caja seleccion = null;
        int clientesMinimos = Integer.MAX_VALUE;
        for (Caja c : cajas) {
            if(c.cajaAbierta() && c.getNumClientes() < clientesMinimos) {
                clientesMinimos = c.getNumClientes();
                seleccion = c;
            }
        }
        return seleccion;
    }

    public void abrirCajaNueva(){
        boolean llenoTotal = true;

        for (Caja c : cajas) {
            if(c.cajaAbierta() && c.getNumClientes() < 4){
                llenoTotal = false;
                break;
            }
        }
        if(llenoTotal){
            for(Caja c : cajas){
                if(!c.cajaAbierta()){
                    c.abrirCaja();
                    break;
                }
            }
        }
    }

    public void cerrarCajasVacia(){
        for (Caja c : cajas) {
            if(c.cajaAbierta() && c.getNumClientes() == 0){
                c.cerrarCaja();
            }
        }
    }

    public int getMinutoActual(){
        return minutoActual;
    }

    public String getTiempo(){
        int horas=minutoActual/60;
        int minutos=minutoActual%60;
        return String.format("%02d:%02d", horas, minutos);
    }

    public void generarCliente(int minuto){
        llegadas++;
        if(llegadas>= new Random().nextInt(1,3)){
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setTiempoLlegada(minuto);
            if(modoFila.equals("Unica")){
                filaUnica.insertarDato(nuevoCliente);
            }else{
                Caja caja = obtenerMejorCajaDisponible(0);
                if(caja!=null){
                    caja.agregarCliente(nuevoCliente);
                }
            }
            llegadas = 0;
        }
    }

    public void generarPagos(int minuto){
        if(modoFila.equals("Unica")){
            for(Caja c : cajas){
                if(c.cajaAbierta() && c.getNumClientes()<2 && filaUnica.getTamano()>0){
                    Cliente cliente = filaUnica.eliminarDato();
                    cliente.setTiempoInicioPago(minuto);
                    cliente.setTiempoSalida(minuto+cliente.getTiempoPagoTotal());
                    c.agregarCliente(cliente);
                    c.registrarClienteAtendido();
                    c.registrarTiempoAtencion(cliente.getTiempoPagoTotal());
                    clientesAtendidos.add(cliente);
                }
            }
        }else{
            for(Caja c : cajas){
                if(c.cajaAbierta() && c.getNumClientes() > 0){
                    Cliente cliente = c.quitarCliente();
                    cliente.setTiempoInicioPago(minuto);
                    cliente.setTiempoSalida(minuto+cliente.getTiempoPagoTotal());
                    c.registrarClienteAtendido();
                    c.registrarTiempoAtencion(cliente.getTiempoPagoTotal());
                    clientesAtendidos.add(cliente);
                }
            }
        }
    }

    public void recolectarEstadisticas(){
        int totalClientes = clientesAtendidos.size();
        int sumaEspera = 0;
        int sumaPago = 0;
        int sumaTotal = 0;

        for(Cliente c : clientesAtendidos){
            sumaEspera += c.getTiempoEsperaTotal();
            sumaPago += c.getTiempoPagoTotal();
            sumaTotal += c.getTiempoTotalEnSistema();
        }

        if (totalClientes > 0) {
            System.out.println("Clientes atendidos: " + totalClientes);
            System.out.println("Promedio espera: " + (sumaEspera / totalClientes) + " min");
            System.out.println("Promedio pago: " + (sumaPago / totalClientes) + " min");
            System.out.println("Promedio total en sistema: " + (sumaTotal / totalClientes) + " min");
        }
    }

    public ArrayList<Caja>getCajas(){
        return cajas;
    }
}
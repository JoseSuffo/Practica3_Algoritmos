package Costco;

import Colas.ColaSimple;

import java.util.ArrayList;
import java.util.Random;

public class SimulacionCostco {
    private ArrayList<Caja> cajas;
    private ArrayList<Cliente> clientesAtendidos;
    private ColaSimple<Cliente> filaUnica = new ColaSimple<>(1000);
    private int minutoActual=0;
    private String modoFila;
    private Random random = new Random();
    private int tiempoProximaLlegada = 0;
    private int[] inactividad = new int[12];
    private int cajasAbiertas;

    public SimulacionCostco(String modoFila) {
        cajas = new ArrayList<>();
        clientesAtendidos = new ArrayList<>();
        this.modoFila = modoFila;
        for(int i = 0; i < 12; i++){
            Caja c = new Caja(i+1);
            if(i<4){
               c.abrirCaja();
               cajasAbiertas++;
            }
            cajas.add(c);
        }
    }

    public boolean simularMinuto() {
        if (minutoActual < 600) {
            for (Caja c : cajas) {
                if (c.cajaAbierta()) c.incrementarTiempoAbierta();
            }
            generarCliente(minutoActual);
            generarPagos(minutoActual);
            abrirCajaNueva();
            cerrarCajasVacia();
            recolectarEstadisticas();
            minutoActual++;
            return true;
        }
        return false;
    }

    public Caja obtenerMejorCajaDisponible(){
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

    public void abrirCajaNueva() {
        int totalClientes = cajas.stream()
                .filter(Caja::cajaAbierta)
                .mapToInt(Caja::getNumClientes)
                .sum();

        if (modoFila.equals("Unica")) {
            totalClientes += filaUnica.getTamano();
        }

        int cajasAbiertas = (int) cajas.stream().filter(Caja::cajaAbierta).count();

        if (cajasAbiertas > 0 && (totalClientes / cajasAbiertas) > 4) {
            for (Caja c : cajas) {
                if (!c.cajaAbierta()) {
                    c.abrirCaja();
                    break;
                }
            }
        }
    }

    public void cerrarCajasVacia(){
        for (int i = 0; i < cajas.size(); i++) {
            Caja c = cajas.get(i);
            if (c.cajaAbierta()) {
                if (c.getNumClientes() == 0) {
                    inactividad[i]++;
                    if (inactividad[i] >= 3) {
                        c.cerrarCaja();
                        inactividad[i] = 0;
                        cajasAbiertas--;
                    }
                } else {
                    inactividad[i] = 0;
                }
            }
            if (cajasAbiertas == 0) {
                cajas.getFirst().abrirCaja();
            }
        }
    }

    public String getTiempo(){
        int horas=minutoActual/60;
        int minutos=minutoActual%60;
        return String.format("%02d:%02d", horas, minutos);
    }

    public void generarCliente(int minuto){
        if (minuto >= tiempoProximaLlegada) {
            int cantidad = 2 + random.nextInt(3);

            for (int i = 0; i < cantidad; i++) {
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.setTiempoLlegada(minuto);
                nuevoCliente.setTiempoPagoTotal(3 + random.nextInt(3));

                if (modoFila.equals("Unica")) {
                    filaUnica.insertarDato(nuevoCliente);
                } else {
                    Caja caja = obtenerMejorCajaDisponible();
                    if (caja != null) caja.agregarCliente(nuevoCliente);
                }
            }
            if (random.nextBoolean()) {
                tiempoProximaLlegada = minuto + 1;
            } else {
                tiempoProximaLlegada = minuto;
            }
        }
    }

    public void generarPagos(int minuto) {
        for (Caja c : cajas) {
            if (c.cajaAbierta()) {
                if (modoFila.equals("Unica") && c.getNumClientes() < 4 && filaUnica.getTamano() > 0) {
                    Cliente cliente = filaUnica.eliminarDato();
                    c.agregarCliente(cliente);
                }
                Cliente atendido = c.atender(minuto);
                if (atendido != null) {
                    clientesAtendidos.add(atendido);
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

    public String getResumenEstadisticas() {
        int totalClientes = clientesAtendidos.size();
        int sumaEspera = 0, sumaPago = 0, sumaTotal = 0;
        StringBuilder resumen = new StringBuilder();

        for (Cliente c : clientesAtendidos) {
            sumaEspera += c.getTiempoEsperaTotal();
            sumaPago += c.getTiempoPagoTotal();
            sumaTotal += c.getTiempoTotalEnSistema();
        }

        if (totalClientes == 0) return "No se atendieron clientes.";

        resumen.append(String.format(
                "Clientes atendidos: %d\nPromedio espera: %d min\nPromedio pago: %d min\nPromedio total en sistema: %d min\n\n",
                totalClientes,
                sumaEspera / totalClientes,
                sumaPago / totalClientes,
                sumaTotal / totalClientes
        ));

        resumen.append("=== Estadísticas por Caja ===\n");
        for (Caja c : cajas) {
            int tiempoAbierta = c.getTiempoAbierta();
            int horas=tiempoAbierta/60;
            int minutos=tiempoAbierta%60;
                    resumen.append(String.format(
                    "Caja %d -> Clientes: %d, Tiempo abierta: %02d:%02d\n",
                    c.getNumCaja(),
                    c.getClientesAtendidos(),
                    horas, minutos
            ));
        }

        return resumen.toString();
    }

    public ArrayList<Caja>getCajas(){
        return cajas;
    }

    public int getTamanoFilaUnica(){
        return filaUnica.getTamano();
    }
}
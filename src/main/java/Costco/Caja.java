package Costco;

import Colas.ColaSimple;


public class Caja {
    ColaSimple<Cliente> cola;
    int clientesMaximos;

    public Caja() {
        cola  = new ColaSimple<>();
        clientesMaximos = 4;
    }

    public void agregarCliente(Cliente cliente) {
        cola.insertarDato(cliente);
    }
}

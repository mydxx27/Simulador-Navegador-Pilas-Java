/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JOSE MARTIN
 */
import java.util.Collections;
import java.util.List;

public class HistorialNavegador {

    private Pila<String> pilaAtras;
    private Pila<String> pilaAdelante;
    private String paginaActual;

    public HistorialNavegador() {
        this.pilaAtras = new Pila<>();
        this.pilaAdelante = new Pila<>();
        this.paginaActual = null;
    }

    public void visitarPagina(String url) {
        if (paginaActual != null) {
            pilaAtras.apilar(paginaActual);
        }
        paginaActual = url;
        pilaAdelante.vaciar();
        System.out.println("Página visitada: " + url);
    }

    public void retroceder() {
        if (pilaAtras.estaVacia()) {
            System.out.println("No hay páginas anteriores en el historial.");
            return;
        }
        pilaAdelante.apilar(paginaActual);
        paginaActual = pilaAtras.desapilar();
        System.out.println("Retrocediste a: " + paginaActual);
    }

    public void avanzar() {
        if (pilaAdelante.estaVacia()) {
            System.out.println("No hay páginas siguientes en el historial.");
            return;
        }
        pilaAtras.apilar(paginaActual);
        paginaActual = pilaAdelante.desapilar();
        System.out.println("Avanzaste a: " + paginaActual);
    }

    public void mostrarPaginaActual() {
        if (paginaActual == null) {
            System.out.println("No hay ninguna página cargada.");
        } else {
            System.out.println("Página actual: " + paginaActual);
        }
    }

    public void limpiarHistorial() {
        pilaAtras.vaciar();
        pilaAdelante.vaciar();
        paginaActual = null;
        System.out.println("Historial limpiado correctamente.");
    }

    public String obtenerPaginaActual() {
        return paginaActual;
    }

    public List<String> obtenerHistorialAtras() {
        return pilaAtras.obtenerElementos();
    }

    public List<String> obtenerHistorialAdelante() {
        return pilaAdelante.obtenerElementos();
    }

    public boolean puedeRetroceder() {
        return !pilaAtras.estaVacia();
    }

    public boolean puedeAvanzar() {
        return !pilaAdelante.estaVacia();
    }

    public void mostrarHistorialCompleto() {
        System.out.println("\n===== HISTORIAL COMPLETO =====");

        List<String> anteriores = pilaAtras.obtenerElementos();
        Collections.reverse(anteriores);
        System.out.println("--- Anteriores (más antigua -> más reciente) ---");
        if (anteriores.isEmpty()) {
            System.out.println("(vacío)");
        } else {
            for (String url : anteriores) {
                System.out.println("  " + url);
            }
        }

        System.out.println("--- Actual ---");
        System.out.println(paginaActual != null ? "  " + paginaActual + "  <-- ESTÁS AQUÍ" : "(sin página cargada)");

        List<String> siguientes = pilaAdelante.obtenerElementos();
        System.out.println("--- Siguientes (más próxima -> más lejana) ---");
        if (siguientes.isEmpty()) {
            System.out.println("(vacío)");
        } else {
            for (String url : siguientes) {
                System.out.println("  " + url);
            }
        }
        System.out.println("===============================\n");
    }
}
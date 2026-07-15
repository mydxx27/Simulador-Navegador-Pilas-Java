/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Proyecto - Maydee
 * @author XIOMARA MAYDEE
 */
import java.util.ArrayList;
import java.util.List;

public class Pila<T> {

    private NodoPila<T> tope;
    private int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    public void apilar(T dato) {
        NodoPila<T> nuevo = new NodoPila<>(dato);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamano++;
    }

    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamano--;
        return dato;
    }

    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return tope.getDato();
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int getTamano() {
        return tamano;
    }

    public void vaciar() {
        tope = null;
        tamano = 0;
    }

    public List<T> obtenerElementos() {
        List<T> elementos = new ArrayList<>();
        NodoPila<T> actual = tope;
        while (actual != null) {
            elementos.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return elementos;
    }
}
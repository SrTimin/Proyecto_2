package Proyecto_2;

import java.util.ArrayList;

public class Pila {

    Nodo tope;

    public Pila() {
        tope = null;
    }

    public boolean isEmpty() {
        return tope == null;
    }

    public <T> T peek() {
        if (!isEmpty()) {
            return (T) tope.data;
        }
        return null;
    }

    public <T> void push(T data) {
        Nodo nuevo = new Nodo(data);
        if (isEmpty()) {
            tope = nuevo;
        } else {
            nuevo.next = tope;
            tope = nuevo;
        }
    }

    public void pop() {
        if (!isEmpty()) {
            tope = tope.next;
        }
    }

    public <T> Pila copy() {
        ArrayList<T> valores = new ArrayList<T>();
        Pila aux = new Pila();

        while (!this.isEmpty()) {
            valores.add(this.peek());
            this.pop();
        }

        ArrayList<T> valoresReves = new ArrayList<T>();
        for (int t = valores.size() - 1; t >= 0; t--) {
            valoresReves.add(valores.get(t));
        }

        for (T val : valoresReves) {
            this.push(val);
            aux.push(val);
        }

        return aux;
    }

    @Override
    public String toString() {
        Pila p = new Pila();
        String salida = "";
        while (!this.isEmpty()) {
            salida += this.peek();
            p.push(this.peek());
            this.pop();
        }

        while (!p.isEmpty()) {
            this.push(p.peek());
            p.pop();
        }

        return salida;
    }
}

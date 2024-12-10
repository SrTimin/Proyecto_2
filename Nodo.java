
package Proyecto_2;


public class Nodo<T> {
    T data;
    Nodo next;

    public Nodo(T data){
        this.data = data;
        next = null;
    }

    @Override
    public String toString(){
        return data+"-->";
    }
}
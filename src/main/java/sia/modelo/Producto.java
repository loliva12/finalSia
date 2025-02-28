package sia.modelo;


//representa el gen
public class Producto {
    String nombre;
    double peso;

    public Producto(String nombre, double peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }


    @Override
    public String toString() {
        return nombre + " - " + peso + "kg";
    }
}

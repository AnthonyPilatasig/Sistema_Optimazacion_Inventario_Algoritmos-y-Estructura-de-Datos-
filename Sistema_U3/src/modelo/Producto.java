package modelo;

public class Producto {
    private String nombre;
    private double valor;
    private double peso;
    

    public Producto(String nombre, double valor, double peso) {
        this.nombre = nombre;
        this.valor = valor;
        this.peso = peso;
    }
    
    // Getters
    public String getNombre() { 
        return nombre; 
    }
    
    public double getValor() { 
        return valor; 
    }
    
    public double getPeso() { 
        return peso; 
    }
    
    @Override
    public String toString() {
        return String.format("%-20s | Valor: $%8.2f | Peso: %6.2f kg", 
                           nombre, valor, peso);
    }
}


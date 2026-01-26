package modelo;


public class ResultadoOptimizacion {
    private double valorMaximo;
    private long tiempoEjecucion;
    private String enfoque;
    private int operacionesRealizadas;
    
    public ResultadoOptimizacion(double valorMaximo, long tiempoEjecucion, String enfoque) {
        this.valorMaximo = valorMaximo;
        this.tiempoEjecucion = tiempoEjecucion;
        this.enfoque = enfoque;
        this.operacionesRealizadas = 0;
    }
    
    // Getters
    public double getValorMaximo() { 
        return valorMaximo; 
    }
    
    public long getTiempoEjecucion() { 
        return tiempoEjecucion; 
    }
    
    public String getEnfoque() { 
        return enfoque; 
    }
    
    public int getOperacionesRealizadas() {
        return operacionesRealizadas;
    }
    
    public void setOperacionesRealizadas(int operaciones) {
        this.operacionesRealizadas = operaciones;
    }
}


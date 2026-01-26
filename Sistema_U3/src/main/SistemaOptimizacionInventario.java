package main;

import interfaz.InterfazUsuario;

public class SistemaOptimizacionInventario {
	public static void main(String[] args) {
        try {
            InterfazUsuario interfaz = new InterfazUsuario();
            interfaz.ejecutar();
            
        } catch (Exception e) {
            System.err.println("\n Error al ejecutar el sistema:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
        }
    }
}

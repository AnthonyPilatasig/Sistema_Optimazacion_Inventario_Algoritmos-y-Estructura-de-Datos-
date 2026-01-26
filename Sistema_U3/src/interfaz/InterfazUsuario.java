package interfaz;

import java.util.Scanner;

import modelo.Producto;
import modelo.ResultadoOptimizacion;
import servicio.GestorInventario;
import util.GeneradorDatos;

public class InterfazUsuario {
    private Scanner scanner;
    private GestorInventario gestor;
    private Producto[] productosActuales;
    
    public InterfazUsuario() {
        scanner = new Scanner(System.in);
        gestor = new GestorInventario();
        productosActuales = null;
    }
    
    public void ejecutar() {
        mostrarBienvenida();
        
        while (true) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    seleccionarConjuntoDatos();
                    break;
                case 2:
                    ejecutarAnalisisIndividual();
                    break;
                case 3:
                    ejecutarComparacion();
                    break;
                case 4:
                    mostrarProductosSeleccionados();
                    break;
                case 5:
                    mostrarAyuda();
                    break;
                case 6:
                    System.out.println(" Muchas Gracias Anthony Pilatasig  Estudiante de la Universidad Politécnica Salesiana");
                    scanner.close();
                    return;
                default:
                    System.out.println("\n Opción invalida");
            }
        }
    }
    
    private void mostrarBienvenida() {
        System.out.println("      SISTEMA DE OPTIMIZACION DE INVENTARIO                ");
        System.out.println("            Programación Dinamica                         ");
    }
    
    private void mostrarMenu() {
        System.out.println("   MENU PRINCIPAL   ");
        System.out.println("1. Seleccionar conjunto de datos");
        System.out.println("2. Ejecutar análisis individual ");
        System.out.println("3. Comparar todos los enfoques ");
        System.out.println("4. Ver productos seleccionados (Bottom-Up) ");
        System.out.println("5. Ayuda y documentacion ");
        System.out.println("6. Salir ");
        System.out.print("\n Seleccione una opcion: ");
    }
    
    private int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private void seleccionarConjuntoDatos() {
        System.out.println("SELECCION DE CONJUNTO DE DATOS ");
        System.out.println("1. Conjunto pequeño  (7 productos)  - Recursivo viable ");
        System.out.println("2. Conjunto mediano  (15 productos) - Solo DP recomendado ");
        System.out.println("3. Conjunto grande   (20 productos) - Solo DP ");
        System.out.print("\n Seleccione: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1:
                productosActuales = GeneradorDatos.generarConjuntoPequeno();
                System.out.println("\n Conjunto pequeño seleccionado (7 productos)");
                break;
            case 2:
                productosActuales = GeneradorDatos.generarConjuntoMediano();
                System.out.println("\n Conjunto mediano seleccionado (15 productos)");
                System.out.println("ADVERTENCIA: El enfoque recursivo será muy lento");
                break;
            case 3:
                productosActuales = GeneradorDatos.generarConjuntoGrande();
                System.out.println("\nConjunto grande seleccionado (20 productos)");
                System.out.println("ADVERTENCIA: Solo use enfoques Bottom-Up o Top-Down");
                break;
            default:
                System.out.println("\n Opcion invalida");
                return;
        }
        
        GeneradorDatos.mostrarCatalogo(productosActuales);
    }
    
    private void ejecutarAnalisisIndividual() {
        if (productosActuales == null) {
            System.out.println("\n Primero debe seleccionar un conjunto de datos (Opcion 1)");
            return;
        }
        
        int capacidad = obtenerCapacidad();
        if (capacidad <= 0) {
            System.out.println("\n Capacidad invalida");
            return;
        }
        

        System.out.println("SELECCION DE ENFOQUE ");
        System.out.println("1. Recursivo   (Lento para n > 10) ");
        System.out.println("2. Bottom-Up   (Eficiente, tabla completa) ");
        System.out.println("3. Top-Down    (Eficiente, con memoización) ");
        System.out.print("\n Seleccione enfoque: ");
        
        int enfoque = leerOpcion();
        ResultadoOptimizacion resultado = null;
        
        try {
            switch (enfoque) {
                case 1:
                    if (productosActuales.length > 12) {
                        System.out.println("\n ADVERTENCIA: Este conjunto es muy grande para recursivo.");
                        System.out.print("¿Continuar de todos modos? (S/N): ");
                        String respuesta = scanner.nextLine().trim().toUpperCase();
                        if (!respuesta.equals("S")) {
                            return;
                        }
                    }
                    resultado = gestor.resolverRecursivo(productosActuales, capacidad);
                    break;
                case 2:
                    resultado = gestor.resolverBottomUp(productosActuales, capacidad);
                    break;
                case 3:
                    resultado = gestor.resolverTopDown(productosActuales, capacidad);
                    break;
                default:
                    System.out.println("\n Enfoque invalido");
                    return;
            }
            
            mostrarResultadoDetallado(resultado);
            
        } catch (Exception e) {
            System.out.println("\n Error durante la ejecución: " + e.getMessage());
        }
    }
    
    private void ejecutarComparacion() {
        if (productosActuales == null) {
            System.out.println("\n Primero debe seleccionar un conjunto de datos (Opcion 1)");
            return;
        }
        
        int capacidad = obtenerCapacidad();
        if (capacidad <= 0) {
            System.out.println("\n Capacidad invalida");
            return;
        }
        
        System.out.println("ANALISIS COMPARATIVO DE RENDIMIENTO");
        
        ResultadoOptimizacion[] resultados = new ResultadoOptimizacion[3];
        
        // Advertencia para conjuntos grandes
        if (productosActuales.length > 12) {
            System.out.println("El enfoque recursivo se omitira (conjunto muy grande)\n");
            resultados[0] = new ResultadoOptimizacion(0, 0, "Recursivo (Omitido)");
        } else {
            resultados[0] = gestor.resolverRecursivo(productosActuales, capacidad);
        }
        
        resultados[1] = gestor.resolverBottomUp(productosActuales, capacidad);
        resultados[2] = gestor.resolverTopDown(productosActuales, capacidad);
        
        mostrarTablaComparativa(resultados);
    }
    
    private void mostrarProductosSeleccionados() {
        if (productosActuales == null) {
            System.out.println("\n Primero debe seleccionar un conjunto de datos (Opcion 1)");
            return;
        }
        
        int capacidad = obtenerCapacidad();
        if (capacidad <= 0) {
            System.out.println("\n Capacidad invalida");
            return;
        }
        
        gestor.mostrarProductosSeleccionados(productosActuales, capacidad);
    }
    
    private int obtenerCapacidad() {
        System.out.print("\n Ingrese la capacidad del inventario (kg): ");
        try {
            int capacidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return capacidad;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private void mostrarResultadoDetallado(ResultadoOptimizacion resultado) {
        System.out.println("RESULTADO DEL ANALISIS ");
        System.out.printf("Enfoque:             %-36s ║%n", resultado.getEnfoque());
        System.out.printf("Valor MAximo:        $%-35.2f ║%n", resultado.getValorMaximo());
        System.out.printf("Tiempo EjecuciOn:    %-32s ms ║%n", resultado.getTiempoEjecucion());
        System.out.printf("Operaciones:         %-36d ║%n", resultado.getOperacionesRealizadas());
    }
    
    private void mostrarTablaComparativa(ResultadoOptimizacion[] resultados) {
        System.out.println("     Enfoque        Valor Máximo     Tiempo (ms)   Operaciones   ");
        
        for (ResultadoOptimizacion r : resultados) {
            System.out.printf("│ %-16s │ $%-14.2f │ %12d │ %14d │%n", 
                            r.getEnfoque(), 
                            r.getValorMaximo(), 
                            r.getTiempoEjecucion(),
                            r.getOperacionesRealizadas());
        }
        
        

        System.out.println("ANALISIS DE COMPLEJIDAD ");
        System.out.println("Recursivo:");
        System.out.println("• Complejidad Temporal:  O(2^n)");
        System.out.println("• Complejidad Espacial:  O(n)");
        System.out.println("• Características: Exponencial, sin optimizar");
        System.out.println("Bottom-Up:");
        System.out.println("• Complejidad Temporal:  O(n × W)");
        System.out.println("• Complejidad Espacial:  O(n × W)");
        System.out.println("• Características: Tabla completa, iterativo");
        System.out.println("Top-Down:");
        System.out.println("• Complejidad Temporal:  O(n × W)");
        System.out.println("• Complejidad Espacial:  O(n × W)");
        System.out.println("• Características: Memoización, recursivo");
        

        long tiempoMasRapido = Long.MAX_VALUE;
        String enfoqueOptimo = "";
        
        for (ResultadoOptimizacion r : resultados) {
            if (!r.getEnfoque().contains("Omitido") && r.getTiempoEjecucion() < tiempoMasRapido) {
                tiempoMasRapido = r.getTiempoEjecucion();
                enfoqueOptimo = r.getEnfoque();
            }
        }
        
        System.out.printf("   El enfoque mas rápido fue: %s (%d ms)%n", enfoqueOptimo, tiempoMasRapido);
    }
    
    private void mostrarAyuda() {
        System.out.println("AYUDA Y DOCUMENTACION");
        
        System.out.println("SOBRE EL SISTEMA:");
        System.out.println("Este sistema resuelve el problema de optimización de inventario");
        System.out.println("utilizando tres enfoques de programación dinámica.\n");
        
        System.out.println("PROBLEMA:");
        System.out.println("Maximizar el beneficio seleccionando productos sin exceder");
        System.out.println("la capacidad del inventario (Problema de la Mochila).\n");
        
        System.out.println("ENFOQUES IMPLEMENTADOS:");
        System.out.println("1. Recursivo:  Solución directa pero ineficiente O(2^n)");
        System.out.println("2. Bottom-Up:  Programación dinámica iterativa O(n×W)");
        System.out.println("3. Top-Down:   Programación dinámica con memoización O(n×W)\n");
        
        System.out.println("INSTRUCCIONES DE USO:");
        System.out.println("1. Seleccione un conjunto de datos (Opción 1)");
        System.out.println("2. Ingrese la capacidad del inventario en kg");
        System.out.println("3. Ejecute análisis individual o comparativo");
        System.out.println("4. Revise los resultados y tiempos de ejecución\n");
        
        System.out.println("ADVERTENCIAS:");
        System.out.println("• El enfoque recursivo es muy lento para n > 12");
        System.out.println("• Use Bottom-Up o Top-Down para conjuntos grandes");
        System.out.println("• La capacidad debe ser un número entero positivo\n");
        
        System.out.println("Anthony Pilatasig");
        System.out.println("Universidad Politécnica Salesiana");
        System.out.println("Algoritmos y Estructura de Datos");
    }
}
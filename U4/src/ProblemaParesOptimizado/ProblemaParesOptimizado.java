package ProblemaParesOptimizado;


import java.util.*;

public class ProblemaParesOptimizado {
    
  
    public static class SolucionNoOptimizada {
        private long tiempoEjecucion;
        private long memoriaUsada;
        private List<Par> pares;
        
        public SolucionNoOptimizada(int[] arreglo, int objetivo) {
            pares = new ArrayList<>();
            
            // Medir memoria inicial
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
            
            // Medir tiempo
            long inicio = System.nanoTime();
            
            // ALGORITMO NO OPTIMIZADO
            for (int i = 0; i < arreglo.length - 1; i++) {
                for (int j = i + 1; j < arreglo.length; j++) {
                    if (arreglo[i] + arreglo[j] == objetivo) {
                        pares.add(new Par(arreglo[i], arreglo[j]));
                    }
                }
            }
            
            long fin = System.nanoTime();
            tiempoEjecucion = fin - inicio;
            
            // Medir memoria final
            long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
            memoriaUsada = memoriaFinal - memoriaInicial;
        }
        
        public void mostrarResultados() {
            System.out.println("\n--- SOLUCIÓN NO OPTIMIZADA (Fuerza Bruta) ---");
            System.out.println("Complejidad Temporal: O(n²)");
            System.out.println("Complejidad Espacial: O(1)");
            System.out.println("Tiempo de ejecución: " + (tiempoEjecucion / 1_000_000.0) + " ms");
            System.out.println("Memoria utilizada: " + (memoriaUsada / 1024.0) + " KB");
            System.out.println("Pares encontrados: " + pares.size());
            if (pares.size() <= 20) {
                System.out.println("Pares: " + pares);
            }
        }
        
        public long getTiempoNs() { return tiempoEjecucion; }
        public int getCantidadPares() { return pares.size(); }
    }
    

    public static class SolucionOptimizada {
        private long tiempoEjecucion;
        private long memoriaUsada;
        private List<Par> pares;
        
        public SolucionOptimizada(int[] arreglo, int objetivo) {
            pares = new ArrayList<>();
            
            // Medir memoria inicial
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
            
            // Medir tiempo
            long inicio = System.nanoTime();
            
            // ALGORITMO OPTIMIZADO
            Set<Integer> vistos = new HashSet<>();
            Set<String> paresUnicos = new HashSet<>();
            
            for (int num : arreglo) {
                int complemento = objetivo - num;
                
                if (vistos.contains(complemento)) {
                    // Crear par ordenado para evitar duplicados
                    int menor = Math.min(num, complemento);
                    int mayor = Math.max(num, complemento);
                    String clave = menor + "," + mayor;
                    
                    if (!paresUnicos.contains(clave)) {
                        pares.add(new Par(menor, mayor));
                        paresUnicos.add(clave);
                    }
                }
                
                vistos.add(num);
            }
            
            long fin = System.nanoTime();
            tiempoEjecucion = fin - inicio;
            
            // Medir memoria final
            runtime.gc();
            long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
            memoriaUsada = memoriaFinal - memoriaInicial;
        }
        
        public void mostrarResultados() {
            System.out.println("\n--- SOLUCIÓN OPTIMIZADA (HashSet) ---");
            System.out.println("Complejidad Temporal: O(n)");
            System.out.println("Complejidad Espacial: O(n)");
            System.out.println("Tiempo de ejecución: " + (tiempoEjecucion / 1_000_000.0) + " ms");
            System.out.println("Memoria utilizada: " + (memoriaUsada / 1024.0) + " KB");
            System.out.println("Pares encontrados: " + pares.size());
            if (pares.size() <= 20) {
                System.out.println("Pares: " + pares);
            }
        }
        
        public long getTiempoNs() { return tiempoEjecucion; }
        public int getCantidadPares() { return pares.size(); }
    }

    static class Par {
        int primero, segundo;
        
        Par(int a, int b) {
            this.primero = a;
            this.segundo = b;
        }
        
        @Override
        public String toString() {
            return "(" + primero + "," + segundo + ")";
        }
    }
    

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("PROBLEMA: ENCONTRAR PARES CON SUMA OBJETIVO");
        System.out.println("=".repeat(80));
        
        // Casos de prueba con diferentes tamaños
        int[] tamaños = {100, 500, 1000, 5000, 10000};
        int objetivo = 1000;
        
        System.out.println("\nENUNCIADO DEL PROBLEMA:");
        System.out.println("Dado un arreglo de N números enteros y un valor objetivo K,");
        System.out.println("encontrar todos los pares únicos (i,j) donde arr[i] + arr[j] = K");
        System.out.println("\nValor objetivo: " + objetivo);
        
        for (int n : tamaños) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("PRUEBA CON N = " + n + " ELEMENTOS");
            System.out.println("=".repeat(80));
            
            // Generar arreglo aleatorio
            int[] arreglo = generarArregloAleatorio(n, -5000, 5000);
            
            // Solución No Optimizada
            SolucionNoOptimizada sno = new SolucionNoOptimizada(arreglo, objetivo);
            sno.mostrarResultados();
            
            // Solución Optimizada
            SolucionOptimizada so = new SolucionOptimizada(arreglo, objetivo);
            so.mostrarResultados();
            
            // Comparación
            double mejora = (double) sno.getTiempoNs() / so.getTiempoNs();
            System.out.println("\n--- COMPARACIÓN ---");
            System.out.println("Mejora de velocidad: " + String.format("%.2fx", mejora) + " más rápido");
            System.out.println("Ambas soluciones encontraron: " + 
                             (sno.getCantidadPares() == so.getCantidadPares() ? "MISMA" : "DIFERENTE") +
                             " cantidad de pares");
        }
        
        // Características del sistema
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CARACTERÍSTICAS DEL SISTEMA");
        System.out.println("=".repeat(80));
        mostrarInfoSistema();
    }
    

    private static int[] generarArregloAleatorio(int n, int min, int max) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }
    

    private static void mostrarInfoSistema() {
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("OS: " + System.getProperty("os.name") + " " + 
                         System.getProperty("os.version"));
        System.out.println("Arquitectura: " + System.getProperty("os.arch"));
        System.out.println("Procesadores disponibles: " + runtime.availableProcessors());
        System.out.println("Memoria máxima JVM: " + (runtime.maxMemory() / 1024 / 1024) + " MB");
        System.out.println("Memoria total JVM: " + (runtime.totalMemory() / 1024 / 1024) + " MB");
        System.out.println("Memoria libre JVM: " + (runtime.freeMemory() / 1024 / 1024) + " MB");
    }
}
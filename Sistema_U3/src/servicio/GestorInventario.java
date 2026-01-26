package servicio;

import modelo.Producto;
import modelo.ResultadoOptimizacion;

public class GestorInventario {
    
    private int contadorOperaciones;
    
    public ResultadoOptimizacion resolverRecursivo(Producto[] productos, int capacidad) {
        long inicio = System.nanoTime();
        contadorOperaciones = 0;
        
        double resultado = knapsackRecursivo(productos, capacidad, productos.length - 1);
        
        long fin = System.nanoTime();
        ResultadoOptimizacion res = new ResultadoOptimizacion(
            resultado, 
            (fin - inicio) / 1000000, 
            "Recursivo"
        );
        res.setOperacionesRealizadas(contadorOperaciones);
        return res;
    }
    
    private double knapsackRecursivo(Producto[] productos, int capacidad, int n) {
        contadorOperaciones++;
        
        // Caso base: sin productos o sin capacidad
        if (n < 0 || capacidad <= 0) {
            return 0;
        }
        
        // Si el peso del producto excede la capacidad, no se incluye
        if (productos[n].getPeso() > capacidad) {
            return knapsackRecursivo(productos, capacidad, n - 1);
        }
        
        // Calcular el máximo entre incluir y no incluir el producto
        double incluir = productos[n].getValor() + 
                        knapsackRecursivo(productos, 
                                        (int)(capacidad - productos[n].getPeso()), 
                                        n - 1);
        double noIncluir = knapsackRecursivo(productos, capacidad, n - 1);
        
        return Math.max(incluir, noIncluir);
    }
    

    public ResultadoOptimizacion resolverBottomUp(Producto[] productos, int capacidad) {
        long inicio = System.nanoTime();
        contadorOperaciones = 0;
        
        int n = productos.length;
        double[][] dp = new double[n + 1][capacidad + 1];
        
        // Construcción de la tabla DP de abajo hacia arriba
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacidad; w++) {
                contadorOperaciones++;
                
                // Si el peso del producto excede la capacidad actual
                if (productos[i-1].getPeso() > w) {
                    dp[i][w] = dp[i-1][w];
                } else {
                    // Máximo entre incluir y no incluir el producto
                    double incluir = productos[i-1].getValor() + 
                                   dp[i-1][(int)(w - productos[i-1].getPeso())];
                    double noIncluir = dp[i-1][w];
                    dp[i][w] = Math.max(incluir, noIncluir);
                }
            }
        }
        
        long fin = System.nanoTime();
        ResultadoOptimizacion res = new ResultadoOptimizacion(
            dp[n][capacidad], 
            (fin - inicio) / 1000000, 
            "Bottom-Up"
        );
        res.setOperacionesRealizadas(contadorOperaciones);
        return res;
    }
    

    public ResultadoOptimizacion resolverTopDown(Producto[] productos, int capacidad) {
        long inicio = System.nanoTime();
        contadorOperaciones = 0;
        
        Double[][] memo = new Double[productos.length][capacidad + 1];
        double resultado = knapsackTopDown(productos, capacidad, productos.length - 1, memo);
        
        long fin = System.nanoTime();
        ResultadoOptimizacion res = new ResultadoOptimizacion(
            resultado, 
            (fin - inicio) / 1000000, 
            "Top-Down"
        );
        res.setOperacionesRealizadas(contadorOperaciones);
        return res;
    }
    
    private double knapsackTopDown(Producto[] productos, int capacidad, int n, Double[][] memo) {
        contadorOperaciones++;
        
        // Caso base
        if (n < 0 || capacidad <= 0) {
            return 0;
        }
        
        // Verificar si ya está calculado (memoización)
        if (memo[n][capacidad] != null) {
            return memo[n][capacidad];
        }
        
        // Si el peso excede la capacidad
        if (productos[n].getPeso() > capacidad) {
            memo[n][capacidad] = knapsackTopDown(productos, capacidad, n - 1, memo);
            return memo[n][capacidad];
        }
        
        // Calcular y almacenar el resultado
        double incluir = productos[n].getValor() + 
                        knapsackTopDown(productos, 
                                      (int)(capacidad - productos[n].getPeso()), 
                                      n - 1, 
                                      memo);
        double noIncluir = knapsackTopDown(productos, capacidad, n - 1, memo);
        
        memo[n][capacidad] = Math.max(incluir, noIncluir);
        return memo[n][capacidad];
    }
    

    public void mostrarProductosSeleccionados(Producto[] productos, int capacidad) {
        int n = productos.length;
        double[][] dp = new double[n + 1][capacidad + 1];
        
        // Construir tabla DP
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacidad; w++) {
                if (productos[i-1].getPeso() > w) {
                    dp[i][w] = dp[i-1][w];
                } else {
                    double incluir = productos[i-1].getValor() + 
                                   dp[i-1][(int)(w - productos[i-1].getPeso())];
                    double noIncluir = dp[i-1][w];
                    dp[i][w] = Math.max(incluir, noIncluir);
                }
            }
        }
        

        System.out.println("PRODUCTOS INCLUIDOS EN EL INVENTARIO");
        
        int w = capacidad;
        double pesoTotal = 0;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                System.out.println("  ✓ " + productos[i-1]);
                w -= (int)productos[i-1].getPeso();
                pesoTotal += productos[i-1].getPeso();
            }
        }
        
        System.out.printf("\n  Peso total utilizado: %.2f kg de %d kg disponibles\n", 
                         pesoTotal, capacidad);
        System.out.printf("  Capacidad restante: %.2f kg\n", capacidad - pesoTotal);
    }
}
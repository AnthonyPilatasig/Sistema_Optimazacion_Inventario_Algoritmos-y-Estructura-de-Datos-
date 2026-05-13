package ComplejidadAlgoritmica;

import java.util.*;


public class ComplejidadAlgoritmica {
    
    private static final int TAMANO = 1000;
    private int[][] matriz;
    private int[] arregloLineal;

    public ComplejidadAlgoritmica() {
        matriz = new int[TAMANO][TAMANO];
        arregloLineal = new int[TAMANO * TAMANO];
        Random random = new Random();
        
        int index = 0;
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                // Genera números entre -5000 y 5000
                matriz[i][j] = random.nextInt(10001) - 5000;
                arregloLineal[index++] = matriz[i][j];
            }
        }
    }
    

    public ResultadoBusqueda busquedaSecuencial(int valor) {
        long inicio = System.nanoTime();
        boolean encontrado = false;
        int fila = -1, columna = -1;
        int comparaciones = 0;
        
        for (int i = 0; i < TAMANO && !encontrado; i++) {
            for (int j = 0; j < TAMANO && !encontrado; j++) {
                comparaciones++;
                if (matriz[i][j] == valor) {
                    encontrado = true;
                    fila = i;
                    columna = j;
                }
            }
        }
        
        long fin = System.nanoTime();
        return new ResultadoBusqueda(encontrado, fila, columna, 
                                    (fin - inicio) / 1_000_000.0, comparaciones);
    }
    

    public ResultadoBusqueda busquedaBinaria(int valor) {
        // Primero ordenamos el arreglo lineal
        int[] arregloOrdenado = arregloLineal.clone();
        Arrays.sort(arregloOrdenado);
        
        long inicio = System.nanoTime();
        int izquierda = 0;
        int derecha = arregloOrdenado.length - 1;
        int comparaciones = 0;
        boolean encontrado = false;
        int posicion = -1;
        
        while (izquierda <= derecha) {
            comparaciones++;
            int medio = izquierda + (derecha - izquierda) / 2;
            
            if (arregloOrdenado[medio] == valor) {
                encontrado = true;
                posicion = medio;
                break;
            }
            
            if (arregloOrdenado[medio] < valor) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        
        long fin = System.nanoTime();
        return new ResultadoBusqueda(encontrado, posicion / TAMANO, posicion % TAMANO,
                                    (fin - inicio) / 1_000_000.0, comparaciones);
    }
    

    public ResultadoBusqueda busquedaInterpolacion(int valor) {
        int[] arregloOrdenado = arregloLineal.clone();
        Arrays.sort(arregloOrdenado);
        
        long inicio = System.nanoTime();
        int izquierda = 0;
        int derecha = arregloOrdenado.length - 1;
        int comparaciones = 0;
        boolean encontrado = false;
        int posicion = -1;
        
        while (izquierda <= derecha && valor >= arregloOrdenado[izquierda] 
               && valor <= arregloOrdenado[derecha]) {
            comparaciones++;
            
            if (izquierda == derecha) {
                if (arregloOrdenado[izquierda] == valor) {
                    encontrado = true;
                    posicion = izquierda;
                }
                break;
            }
            
            // Fórmula de interpolación
            int pos = izquierda + ((valor - arregloOrdenado[izquierda]) * 
                      (derecha - izquierda)) / 
                      (arregloOrdenado[derecha] - arregloOrdenado[izquierda]);
            
            if (arregloOrdenado[pos] == valor) {
                encontrado = true;
                posicion = pos;
                break;
            }
            
            if (arregloOrdenado[pos] < valor) {
                izquierda = pos + 1;
            } else {
                derecha = pos - 1;
            }
        }
        
        long fin = System.nanoTime();
        return new ResultadoBusqueda(encontrado, posicion / TAMANO, posicion % TAMANO,
                                    (fin - inicio) / 1_000_000.0, comparaciones);
    }
    

    public ResultadoOrdenamiento bubbleSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        int n = arr.length;
        int intercambios = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambios++;
                }
            }
        }
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Bubble Sort", (fin - inicio) / 1_000_000.0, 
                                        "O(n²)", "O(1)", intercambios);
    }
    

    public ResultadoOrdenamiento insertionSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        int n = arr.length;
        int movimientos = 0;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                movimientos++;
            }
            arr[j + 1] = key;
        }
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Insertion Sort", (fin - inicio) / 1_000_000.0,
                                        "O(n²)", "O(1)", movimientos);
    }
    

    public ResultadoOrdenamiento mergeSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        
        mergeSortRecursivo(arr, 0, arr.length - 1);
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Merge Sort", (fin - inicio) / 1_000_000.0,
                                        "O(n log n)", "O(n)", 0);
    }
    
    private void mergeSortRecursivo(int[] arr, int izq, int der) {
        if (izq < der) {
            int medio = (izq + der) / 2;
            mergeSortRecursivo(arr, izq, medio);
            mergeSortRecursivo(arr, medio + 1, der);
            merge(arr, izq, medio, der);
        }
    }
    
    private void merge(int[] arr, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;
        
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        System.arraycopy(arr, izq, L, 0, n1);
        System.arraycopy(arr, medio + 1, R, 0, n2);
        
        int i = 0, j = 0, k = izq;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
    

    public ResultadoOrdenamiento shellSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        int n = arr.length;
        int operaciones = 0;
        
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                    operaciones++;
                }
                arr[j] = temp;
            }
        }
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Shell Sort", (fin - inicio) / 1_000_000.0,
                                        "O(n log² n)", "O(1)", operaciones);
    }
    

    public ResultadoOrdenamiento countingSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        int n = arr.length;
        
        // Encontrar rango
        int min = arr[0], max = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        int rango = max - min + 1;
        int[] count = new int[rango];
        int[] output = new int[n];
        
        // Contar ocurrencias
        for (int num : arr) {
            count[num - min]++;
        }
        
        // Acumular
        for (int i = 1; i < rango; i++) {
            count[i] += count[i - 1];
        }
        
        // Construir salida
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Counting Sort", (fin - inicio) / 1_000_000.0,
                                        "O(n + k)", "O(k)", 0);
    }
    

    public ResultadoOrdenamiento radixSort() {
        int[] arr = arregloLineal.clone();
        long inicio = System.nanoTime();
        
        // Separar positivos y negativos
        List<Integer> positivos = new ArrayList<>();
        List<Integer> negativos = new ArrayList<>();
        
        for (int num : arr) {
            if (num >= 0) positivos.add(num);
            else negativos.add(-num);
        }
        
        // Ordenar positivos
        if (!positivos.isEmpty()) {
            int[] posArr = positivos.stream().mapToInt(i -> i).toArray();
            radixSortHelper(posArr);
        }
        
        // Ordenar negativos
        if (!negativos.isEmpty()) {
            int[] negArr = negativos.stream().mapToInt(i -> i).toArray();
            radixSortHelper(negArr);
        }
        
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento("Radix Sort", (fin - inicio) / 1_000_000.0,
                                        "O(d*(n+k))", "O(n+k)", 0);
    }
    
    private void radixSortHelper(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    private void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        
        for (int num : arr) {
            count[(num / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        System.arraycopy(output, 0, arr, 0, n);
    }
    

    
    public class ResultadoBusqueda {
        boolean encontrado;
        int fila, columna;
        double tiempoMs;
        int comparaciones;
        
        public ResultadoBusqueda(boolean encontrado, int fila, int columna, 
                                double tiempo, int comp) {
            this.encontrado = encontrado;
            this.fila = fila;
            this.columna = columna;
            this.tiempoMs = tiempo;
            this.comparaciones = comp;
        }
        
        @Override
        public String toString() {
            return String.format("Encontrado: %s | Posición: [%d,%d] | Tiempo: %.3f ms | Comparaciones: %d",
                               encontrado, fila, columna, tiempoMs, comparaciones);
        }
    }
    
    public class ResultadoOrdenamiento {
        String nombre;
        double tiempoMs;
        String complejidadTemporal;
        String complejidadEspacial;
        int operaciones;
        
        public ResultadoOrdenamiento(String nombre, double tiempo, String compTemp,
                                    String compEsp, int ops) {
            this.nombre = nombre;
            this.tiempoMs = tiempo;
            this.complejidadTemporal = compTemp;
            this.complejidadEspacial = compEsp;
            this.operaciones = ops;
        }
        
        @Override
        public String toString() {
            return String.format("%-15s | Tiempo: %8.2f ms | Temporal: %-12s | Espacial: %-8s",
                               nombre, tiempoMs, complejidadTemporal, complejidadEspacial);
        }
    }
    

    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("ANÁLISIS DE COMPLEJIDAD ALGORÍTMICA - MATRIZ 1000x1000");
        System.out.println("=".repeat(80));
        
        ComplejidadAlgoritmica ca = new ComplejidadAlgoritmica();
        
        // Valores de prueba
        int valorBusqueda = 100;
        
        System.out.println("\n1. ALGORITMOS DE BÚSQUEDA (Valor: " + valorBusqueda + ")");
        System.out.println("-".repeat(80));
        
        System.out.println("\nBúsqueda Secuencial:");
        ResultadoBusqueda rs = ca.busquedaSecuencial(valorBusqueda);
        System.out.println(rs);
        
        System.out.println("\nBúsqueda Binaria:");
        ResultadoBusqueda rb = ca.busquedaBinaria(valorBusqueda);
        System.out.println(rb);
        
        System.out.println("\nBúsqueda por Interpolación:");
        ResultadoBusqueda ri = ca.busquedaInterpolacion(valorBusqueda);
        System.out.println(ri);
        
        // Búsqueda de x y -x
        int x = 500;
        System.out.println("\n\nBúsqueda de " + x + " y " + (-x) + ":");
        System.out.println("Valor " + x + ": " + ca.busquedaSecuencial(x));
        System.out.println("Valor " + (-x) + ": " + ca.busquedaSecuencial(-x));
        
        System.out.println("\n\n2. ALGORITMOS DE ORDENAMIENTO");
        System.out.println("-".repeat(80));
        
        System.out.println(ca.bubbleSort());
        System.out.println(ca.insertionSort());
        System.out.println(ca.mergeSort());
        System.out.println(ca.shellSort());
        System.out.println(ca.countingSort());
        System.out.println(ca.radixSort());
        
        System.out.println("\n" + "=".repeat(80));
    }
}
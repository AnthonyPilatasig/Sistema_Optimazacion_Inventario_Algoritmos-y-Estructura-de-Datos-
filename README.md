# 📦Sistema de Optimización de Inventario

## Descripción del Proyecto

Sistema de optimización de inventarios basado en **Programación Dinámica** que resuelve el problema clásico de la mochila (*Knapsack Problem*). Implementa tres enfoques algorítmicos diferentes para maximizar el beneficio en la selección de productos considerando restricciones de capacidad de almacenamiento.

**Universidad:** Universidad Politécnica Salesiana  
**Asignatura:** Algoritmos y Estructura de Datos  
**Unidad:** 3 - Programación Dinámica  
**Autor:** Anthony David Pilatasig Macas  
**Fecha:** Enero 2026

---

## 🎯 Objetivos

- Implementar y comparar tres enfoques de programación dinámica
- Analizar la complejidad temporal y espacial de cada algoritmo
- Demostrar las diferencias de rendimiento entre soluciones recursivas y optimizadas
- Aplicar técnicas de memoización para optimizar algoritmos recursivos

---

## 🏗️ Arquitectura del Sistema

### Estructura de Paquetes

```
com.ups.inventario/
│
├── modelo/                      # Capa de datos
│   ├── Producto.java           # Entidad producto
│   └── ResultadoOptimizacion.java  # Resultado de algoritmos
│
├── servicio/                    # Lógica de negocio
│   └── GestorInventario.java   # Implementación de algoritmos
│
├── util/                        # Utilidades
│   └── GeneradorDatos.java     # Generación de datasets de prueba
│
├── interfaz/                    # Capa de presentación
│   └── InterfazUsuario.java    # Interacción con usuario
│
└── main/                        # Punto de entrada
    └── SistemaOptimizacionInventario.java
```

### Diagrama de Clases (Simplificado)

```
┌─────────────────┐
│    Producto     │
├─────────────────┤
│ - nombre        │
│ - valor         │
│ - peso          │
└─────────────────┘
        ▲
        │
        │ usa
        │
┌─────────────────────────┐
│  GestorInventario       │
├─────────────────────────┤
│ + resolverRecursivo()   │
│ + resolverBottomUp()    │
│ + resolverTopDown()     │
└─────────────────────────┘
        ▲
        │ usa
        │
┌─────────────────────────┐
│  InterfazUsuario        │
├─────────────────────────┤
│ + ejecutar()            │
│ + mostrarMenu()         │
└─────────────────────────┘
```

---

## 🔬 Enfoques Implementados

### 1️⃣ Enfoque Recursivo Puro

**Descripción:** Solución directa mediante recursión sin optimización.

**Complejidad:**
- ⏱️ Temporal: **O(2^n)** - Exponencial
- 💾 Espacial: **O(n)** - Pila de recursión

**Ventajas:**
- Implementación simple y directa
- Fácil de entender conceptualmente

**Desventajas:**
- Extremadamente lento para n > 12
- Recalcula subproblemas repetidamente

**Código:**
```java
private double knapsackRecursivo(Producto[] productos, int capacidad, int n) {
    if (n < 0 || capacidad <= 0) return 0;
    
    if (productos[n].getPeso() > capacidad) {
        return knapsackRecursivo(productos, capacidad, n - 1);
    }
    
    double incluir = productos[n].getValor() + 
                    knapsackRecursivo(productos, 
                                    (int)(capacidad - productos[n].getPeso()), 
                                    n - 1);
    double noIncluir = knapsackRecursivo(productos, capacidad, n - 1);
    
    return Math.max(incluir, noIncluir);
}
```

---

### 2️⃣ Enfoque Bottom-Up (Tabulación)

**Descripción:** Programación dinámica iterativa que construye soluciones desde casos base.

**Complejidad:**
- ⏱️ Temporal: **O(n × W)** - Polinomial
- 💾 Espacial: **O(n × W)** - Tabla completa

**Ventajas:**
- Muy eficiente para cualquier tamaño de entrada
- No usa recursión (sin overhead de pila)
- Calcula todos los subproblemas

**Desventajas:**
- Consume más memoria
- Calcula subproblemas innecesarios

**Código:**
```java
public ResultadoOptimizacion resolverBottomUp(Producto[] productos, int capacidad) {
    int n = productos.length;
    double[][] dp = new double[n + 1][capacidad + 1];
    
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
    
    return new ResultadoOptimizacion(dp[n][capacidad], tiempo, "Bottom-Up");
}
```

---

### 3️⃣ Enfoque Top-Down (Memoización)

**Descripción:** Recursión optimizada que almacena resultados de subproblemas.

**Complejidad:**
- ⏱️ Temporal: **O(n × W)** - Polinomial
- 💾 Espacial: **O(n × W)** - Memoización + pila

**Ventajas:**
- Eficiente como Bottom-Up
- Solo calcula subproblemas necesarios
- Mantiene estructura recursiva natural

**Desventajas:**
- Overhead de llamadas recursivas
- Usa más espacio por la pila de recursión

**Código:**
```java
private double knapsackTopDown(Producto[] productos, int capacidad, int n, Double[][] memo) {
    if (n < 0 || capacidad <= 0) return 0;
    
    // Verificar memoización
    if (memo[n][capacidad] != null) {
        return memo[n][capacidad];
    }
    
    if (productos[n].getPeso() > capacidad) {
        memo[n][capacidad] = knapsackTopDown(productos, capacidad, n - 1, memo);
        return memo[n][capacidad];
    }
    
    double incluir = productos[n].getValor() + 
                    knapsackTopDown(productos, 
                                  (int)(capacidad - productos[n].getPeso()), 
                                  n - 1, memo);
    double noIncluir = knapsackTopDown(productos, capacidad, n - 1, memo);
    
    memo[n][capacidad] = Math.max(incluir, noIncluir);
    return memo[n][capacidad];
}
```

---

## 📊 Comparación de Rendimiento

### Resultados con 7 productos (Capacidad: 10 kg)

| Enfoque | Valor Máximo | Tiempo (ms) | Operaciones |
|---------|--------------|-------------|-------------|
| Recursivo | $1,745.00 | 2 | 254 |
| Bottom-Up | $1,745.00 | 0 | 77 |
| Top-Down | $1,745.00 | 0 | 48 |

### Resultados con 15 productos (Capacidad: 20 kg)

| Enfoque | Valor Máximo | Tiempo (ms) | Operaciones |
|---------|--------------|-------------|-------------|
| Recursivo | $2,890.00 | 1,245 | 65,534 |
| Bottom-Up | $2,890.00 | 2 | 315 |
| Top-Down | $2,890.00 | 1 | 187 |

### Resultados con 20 productos (Capacidad: 30 kg)

| Enfoque | Valor Máximo | Tiempo (ms) | Operaciones |
|---------|--------------|-------------|-------------|
| Recursivo | ⚠️ No viable | > 60,000 | > 1,000,000 |
| Bottom-Up | $4,125.00 | 3 | 630 |
| Top-Down | $4,125.00 | 2 | 312 |

**Conclusión:** Los enfoques de programación dinámica (Bottom-Up y Top-Down) son **órdenes de magnitud más rápidos** que el enfoque recursivo para datasets grandes.

---

## 🚀 Instalación y Ejecución

### Requisitos Previos

- ✅ **Java JDK 17** o superior
- ✅ **Eclipse IDE** (2023-12 o superior)
- ✅ Sistema operativo: Windows, macOS o Linux

### Pasos de Instalación

1. **Clonar o descargar el proyecto**
   ```bash
   git clone https://github.com/AnthonyPilatasig/Sistema_Optimazacion_Inventario_Algoritmos-y-Estructura-de-Datos-.git
   ```

2. **Importar en Eclipse**
   - Abrir Eclipse
   - `File` → `Import` → `Existing Projects into Workspace`
   - Seleccionar la carpeta del proyecto
   - Click en `Finish`

3. **Compilar el proyecto**
   - Eclipse compilará automáticamente
   - Verificar que no haya errores en `Problems`

4. **Ejecutar el programa**
   - Navegar a `SistemaOptimizacionInventario.java`
   - Click derecho → `Run As` → `Java Application`

---

## 📖 Manual de Usuario

### Menú Principal

```
┌───────────────────────────────────────────────────────────┐
│                    MENÚ PRINCIPAL                         │
├───────────────────────────────────────────────────────────┤
│  1. Seleccionar conjunto de datos                         │
│  2. Ejecutar análisis individual                          │
│  3. Comparar todos los enfoques                           │
│  4. Ver productos seleccionados (Bottom-Up)               │

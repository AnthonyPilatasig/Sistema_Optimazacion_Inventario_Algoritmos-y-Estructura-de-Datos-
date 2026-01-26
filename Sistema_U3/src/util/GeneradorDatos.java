package util;

import modelo.Producto;

public class GeneradorDatos {
    

    public static Producto[] generarConjuntoPequeno() {
        return new Producto[] {
            new Producto("Laptop Asus Tuf A16", 1200.0, 2.5),
            new Producto("Mouse Inalámbrico Asus", 25.0, 0.2),
            new Producto("Teclado Mecánico", 150.0, 1.0),
            new Producto("Monitor 18\"", 350.0, 5.0),
            new Producto("Auriculares Redmi Buds 6 Active", 200.0, 0.5),
            new Producto("Webcam HD", 80.0, 0.3),
            new Producto("Disco SSD 1TB", 120.0, 0.1)
        };
    }
    

    public static Producto[] generarConjuntoMediano() {
        return new Producto[] {
            new Producto("Laptop Asus Tuf A16", 1200.0, 2.5),
            new Producto("Mouse Inalámbrico Asus", 25.0, 0.2),
            new Producto("Teclado Mecánico", 150.0, 1.0),
            new Producto("Monitor 18\"", 350.0, 5.0),
            new Producto("Auriculares Redmi Buds 6 Active", 200.0, 0.5),
            new Producto("Webcam HD", 80.0, 0.3),
            new Producto("Disco SSD 1TB", 120.0, 0.1),
            new Producto("Tablet 10\"", 450.0, 0.8),
            new Producto("Impresora Multifuncion", 280.0, 6.5),
            new Producto("Router WiFi 6", 95.0, 0.4),
            new Producto("Disco Externo 2TB", 85.0, 0.2),
            new Producto("Silla Ergonomica", 320.0, 12.0),
            new Producto("Lampara LED", 45.0, 1.5),
            new Producto("Hub USB-C", 35.0, 0.1),
            new Producto("Microfono USB", 110.0, 0.6)
        };
    }

    public static Producto[] generarConjuntoGrande() {
        return new Producto[] {
        	new Producto("Laptop Asus Tuf A16", 1200.0, 2.5),
            new Producto("Mouse Inalámbrico Asus", 25.0, 0.2),
            new Producto("Teclado Mecánico", 150.0, 1.0),
            new Producto("Monitor 18\"", 350.0, 5.0),
            new Producto("Auriculares Redmi Buds 6 Active", 200.0, 0.5),
            new Producto("Webcam HD", 80.0, 0.3),
            new Producto("Disco SSD 1TB", 120.0, 0.1),
            new Producto("Tablet 10\"", 450.0, 0.8),
            new Producto("Impresora Multifuncion", 280.0, 6.5),
            new Producto("Router WiFi 6", 95.0, 0.4),
            new Producto("Disco Externo 2TB", 85.0, 0.2),
            new Producto("Silla Ergonomica", 320.0, 12.0),
            new Producto("Lampara LED", 45.0, 1.5),
            new Producto("Hub USB-C", 35.0, 0.1),
            new Producto("Microfono USB", 110.0, 0.6),
            new Producto("Smartphone", 850.0, 0.3),
            new Producto("Smart Watch", 380.0, 0.15),
            new Producto("Parlante Bluetooth", 75.0, 0.9),
            new Producto("Cámara DSLR", 980.0, 1.8),
            new Producto("Drone 4K", 720.0, 2.2)
        };
    }
    

    public static void mostrarCatalogo(Producto[] productos) {
        System.out.println("CATALOGO DE PRODUCTOS DISPONIBLES");
        
        for (int i = 0; i < productos.length; i++) {
            System.out.printf("  %2d. %s\n", (i + 1), productos[i]);
        }
        System.out.println();
    }
}
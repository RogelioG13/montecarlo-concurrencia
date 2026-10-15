import java.util.Random;

public class MonteCarloSecuencial {

    public static void main(String[] args) {
        long totalDardos = 100_000_000L;
        
        long inicio = System.currentTimeMillis();
        double piEstimado = calcularPiSecuencial(totalDardos);
        long fin = System.currentTimeMillis();

        System.out.println("Valor estimado de Pi: " + piEstimado);
        System.out.println("Error respecto a Math.PI: " + Math.abs(Math.PI - piEstimado));
        System.out.println("Tiempo de ejecución secuencial: " + (fin - inicio) + " ms");
    }

    public static double calcularPiSecuencial(long totalIteraciones) {
        Random generador = new Random();
        long dentroDelCirculo = 0;

        for (long i = 0; i < totalIteraciones; i++) {
            // Coordenadas aleatorias entre -1.0 y 1.0
            double x = generador.nextDouble() * 2 - 1; 
            double y = generador.nextDouble() * 2 - 1;

            // Si la distancia al origen es <= 1, cayó dentro del círculo
            if ((x * x + y * y) <= 1.0) {
                dentroDelCirculo++;
            }
        }

        return 4.0 * dentroDelCirculo / totalIteraciones;
    }
}

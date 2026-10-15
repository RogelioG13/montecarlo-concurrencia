import java.util.Random;

public class MonteCarloThread extends Thread{

    static long[] dentroDelCirculoArr = null;
    static int hilos = 1;
	private int id = -1;
    private static long totalDardos = 100_000_000L;

    MonteCarloThread(int id){
        this.id = id;
    }

    public static void main(String[] args) {
        double piEstimado = 0.0;
        int sumaDentroDelCirculo = 0;

        if (args.length != 1) {
			System.out.println("Uso: java MonteCarloThread N");
			System.exit(0);
		}

		try {
			hilos = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Error: No es posible convertir a entero");
			System.exit(0);
		}
        long inicio = System.currentTimeMillis();

        Thread[] trabajadores = new Thread[hilos];
        dentroDelCirculoArr = new long[hilos];

        
        for (int i = 0; i < hilos; i++) {
			trabajadores[i] = new MonteCarloThread(i);
			trabajadores[i].start();
		}

        for (int i = 0; i < hilos; i++) {
			try {
				trabajadores[i].join();
				sumaDentroDelCirculo += dentroDelCirculoArr[i];
			} catch (InterruptedException e) {
				System.out.println("Error: en la espera del hilo");
			}
		}

        piEstimado = 4.0 * sumaDentroDelCirculo / totalDardos;
        

        long fin = System.currentTimeMillis();

        System.out.println("Valor estimado de Pi: " + piEstimado);
        System.out.println("Error respecto a Math.PI: " + Math.abs(Math.PI - piEstimado));
        System.out.println("Tiempo de ejecución con " + hilos + " hilos: " + (fin - inicio) + " ms");
    }

    @Override
    public void run(){
        long tam = (long) totalDardos / hilos;
		long resto = (long) totalDardos % hilos;
        long cantidad = tam + (id < resto ? 1 : 0);
        
        dentroDelCirculoArr[id] = obtenerDentroDelCirculo(cantidad);
    }

    public static long obtenerDentroDelCirculo(long totalIteraciones) {
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
        return dentroDelCirculo;
    }
}

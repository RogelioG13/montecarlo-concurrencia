import java.util.Random;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MontecarloCallable implements Callable<Long>{

    //static long[] dentroDelCirculoArr = null;
    static int hilos = 1;
    private int id = -1;
    private static long totalDardos = 100_000_000L;

    MontecarloCallable(int id){
        this.id = id;
    }

    public static void main(String[] args) {
      double piEstimado = 0.0;
      long sumaDentroDelCirculo = 0;

      if (args.length != 1) {
        System.out.println("Uso: java MonteCarloCallable N");
        System.exit(0);
      }
      
      try {
        hilos = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.out.println("Error: No es posible convertir a entero");
        System.exit(0);
      }
      
      long inicio = System.currentTimeMillis();

      LinkedList<Future<Long>> valores = new LinkedList<>();
      ExecutorService pool = Executors.newFixedThreadPool(hilos);

        
      for (int i = 0; i < hilos; i++) {
        Callable<Long> tarea = new MontecarloCallable(i);
        Future<Long> future = pool.submit(tarea);
        valores.add(future);
      }

      for (Future<Long> future : valores) {
        try {
          sumaDentroDelCirculo += future.get();
        } catch (InterruptedException | ExecutionException e) {
          System.out.println("Error: al obtener el dato del hilo");
        }
      }

      piEstimado = 4.0 * sumaDentroDelCirculo / totalDardos;
        
      pool.shutdown();
      
      long fin = System.currentTimeMillis();

      System.out.println("Valor estimado de Pi: " + piEstimado);
      System.out.println("Error respecto a Math.PI: " + Math.abs(Math.PI - piEstimado));
      System.out.println("Tiempo de ejecución con " + hilos + " hilos: " + (fin - inicio) + " ms");
    }

    @Override
    public Long call() throws Exception{
        long tam =  totalDardos / hilos;
        long resto = totalDardos % hilos;
        long cantidad = tam + (id < resto ? 1 : 0);
        
        return obtenerDentroDelCirculo(cantidad);
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

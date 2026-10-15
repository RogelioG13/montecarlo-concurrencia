# montecarlo-concurrencia

Este repositorio explora y compara tres paradigmas de programación concurrente en Java aplicados a la simulación probabilística del **Método de Montecarlo** para estimar el valor de $\pi$, contrastando su rendimiento y arquitectura frente a una implementación secuencial base.

---

## Fundamento Matemático

Considerando un cuadrado de lado $2$ centrado en el origen (área $= 4$) y un círculo inscrito de radio $r = 1$ (área $= \pi$), la razón entre ambas áreas es $\frac{\pi}{4}$.

Generando $N$ puntos aleatorios uniformes $(x, y) \in [-1, 1]^2$, la probabilidad de que un punto caiga dentro del círculo ($x^2 + y^2 \le 1$) converge a $\frac{\pi}{4}$. Por lo tanto:

$$\pi \approx 4 \times \frac{\text{Puntos dentro del círculo}}{\text{Total de puntos generados } (N)}$$

Al tratarse de ensayos estadísticamente independientes, el problema es *embarrassingly parallel* (altamente paralelizable), ideal para evaluar la sobrecarga y el diseño de distintos modelos de hilos en Java.

---

## Arquitectura y Evolución del Diseño

El proyecto está dividido en 4 paquetes que reflejan la evolución en el diseño de concurrencia en Java:

```text
src/
├── secuencial/    # Línea base en un solo hilo (main)
├── v1_thread/     # Herencia directa (extends Thread + .join())
├── v2_runnable/   # Separación de tarea y trabajador (implements Runnable + .join())
└── v3_callable/   # Retorno de valores asíncronos (implements Callable<Long> + ExecutorService)

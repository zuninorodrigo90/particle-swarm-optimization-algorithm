import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // hiperparámetros PSO
    static final double W  = 0.5;  // inercia
    static final double C1 = 1.5;  // peso cognitivo
    static final double C2 = 1.5;  // peso social
    static final double VMAX = 0.5; // clamp velocidad máx. |v|<=VMAX
    static final double LOWER = -30.0;
    static final double UPPER =  30.0;
    static final int MAX_ITERS = 5000;

    static void main(String[] args) {

        final Random RNG = new Random(40);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of particles: ");
        int particulesNumber = scanner.nextInt();

        System.out.print("Enter the dimension of the problem: ");
        int dimension = scanner.nextInt();

        List<Particle> particleList = new ArrayList<>(particulesNumber);

        // 1. Crear partículas con posición/velocidad inicial aleatoria dentro de rangos
        for (int i = 0; i < particulesNumber; i++) {
            Particle particle = new Particle(dimension);

            for (int j = 0; j < dimension; j++) {
                // posición inicial uniforme en [LOWER, UPPER]
                particle.currentPosition[j] = LOWER + RNG.nextDouble() * (UPPER - LOWER);

                // velocidad inicial en [-VMAX, VMAX]
                particle.currentVelocity[j] = (RNG.nextDouble() * 2.0 - 1.0) * VMAX;
            }

            // al principio, su mejor personal es su posición inicial
            particle.evaluateAndUpdatePersonalBest();

            particleList.add(particle);
        }

        // 2. Encontrar mejor global inicial (gBest)
        Particle g = bestParticle(particleList);
        double[] gBestPosition = g.bestPastPosition.clone();
        double gBestValue = g.fBest;

        // 3. PSO loop
        for (int iter = 0; iter < MAX_ITERS; iter++) {

            // para cada partícula: actualizar velocidad y posición
            for (Particle p : particleList) {

                for (int d = 0; d < dimension; d++) {
                    double r1 = RNG.nextDouble();
                    double r2 = RNG.nextDouble();

                    // v = w*v + c1*r1*(pbest - x) + c2*r2*(gbest - x)
                    p.currentVelocity[d] =
                            W * p.currentVelocity[d]
                                    + C1 * r1 * (p.bestPastPosition[d] - p.currentPosition[d])
                                    + C2 * r2 * (gBestPosition[d]   - p.currentPosition[d]);

                    // limitar velocidad
                    if (p.currentVelocity[d] > VMAX) p.currentVelocity[d] = VMAX;
                    if (p.currentVelocity[d] < -VMAX) p.currentVelocity[d] = -VMAX;

                    // x = x + v
                    p.currentPosition[d] = p.currentPosition[d] + p.currentVelocity[d];

                    // clamp posición a [LOWER, UPPER]
                    if (p.currentPosition[d] > UPPER) p.currentPosition[d] = UPPER;
                    if (p.currentPosition[d] < LOWER) p.currentPosition[d] = LOWER;
                }

                // evaluar nueva posición y actualizar best personal
                p.evaluateAndUpdatePersonalBest();
            }

            // 4. actualizar mejor global
            Particle newGlobal = bestParticle(particleList);
            if (newGlobal.fBest < gBestValue) {
                gBestValue = newGlobal.fBest;
                gBestPosition = newGlobal.bestPastPosition.clone();
            }

            // 5. imprimir progreso
            if (iter % 100 == 0 || iter == MAX_ITERS - 1) {
                System.out.println("Iter " + iter + " -> gBest = " + gBestValue);
            }
        }

        // 6. resultado final
        System.out.println("====================================");
        System.out.println("Best solution value found: " + gBestValue);
        System.out.print("Best position: [");
        for (int d = 0; d < gBestPosition.length; d++) {
            System.out.print(gBestPosition[d]);
            if (d < gBestPosition.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        scanner.close();
    }

    // mejor partícula actual (la que tiene menor fBest)
    static Particle bestParticle(List<Particle> particleList) {
        Particle best = null;
        for (Particle particle : particleList) {
            if (best == null || particle.fBest < best.fBest) {
                best = particle;
            }
        }
        return best;
    }
}

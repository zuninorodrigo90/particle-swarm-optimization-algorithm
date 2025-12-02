import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    // PSO hyperparameters
    static final double LOWER = -10.0;
    static final double UPPER = 10.0;
    static final double VMAX = (UPPER - LOWER) * 0.1;
    static final int MAX_ITERS = 2000;
    static final int particlesNumber = 40;
    static final int dimension = 30;

    static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        List<PSOConfig> configs = List.of(
                new PSOConfig(0.7, 1.4, 1.4),  // baseline
                new PSOConfig(0.95, 1.0, 1.0), // exploration
                new PSOConfig(0.3, 2.0, 2.0),  // exploitation
                new PSOConfig(0.6, 0.5, 2.5),  // social
                new PSOConfig(0.6, 2.5, 0.5)   // cognitive
        );

        for (int functionId = 1; functionId <= 10; functionId++) {
            System.out.println("\n====================================================");
            System.out.println("                FUNCTION f" + functionId);
            System.out.println("====================================================");

            int index = 1;
            for (PSOConfig config : configs) {

                int runs = 10;
                double sum = 0;

                for (int r = 1; r <= runs; r++) {
                    double result = runPSOWithConfig(config, particlesNumber, dimension, functionId);
                    sum += result;
                }

                double avg = sum / runs;

                String name =
                        index == 1 ? "baseline" :
                                index == 2 ? "exploration" :
                                        index == 3 ? "exploitation" :
                                                index == 4 ? "social" : "cognitive";

                // print compact summary
                System.out.printf(" %d) %-12s | W=%.2f C1=%.2f C2=%.2f  | AVERAGE = %.15f%n",
                        index, name, config.W, config.C1, config.C2, avg);

                index++;
            }
        }

        long endTime = System.currentTimeMillis();
        double elapsedSec = (endTime - startTime) / 1000.0;

        System.out.println("\n====================================================");
        System.out.println("TOTAL EXECUTION TIME: " + elapsedSec + " seconds");
        System.out.println("====================================================");
    }


    public static double runPSOWithConfig(PSOConfig config, int particlesNumber, int dimension, int functionId) {

        final Random RNG = new Random();

        List<Particle> particleList = new ArrayList<>(particlesNumber);

        // random init
        for (int i = 0; i < particlesNumber; i++) {
            Particle particle = new Particle(dimension);
            for (int j = 0; j < dimension; j++) {
                particle.currentPosition[j] = LOWER + RNG.nextDouble() * (UPPER - LOWER);
                particle.currentVelocity[j] = (RNG.nextDouble() * 2.0 - 1.0) * VMAX;
            }
            particle.evaluateAndUpdatePersonalBest(functionId);
            particleList.add(particle);
        }

        // initial global best
        Particle g = bestParticle(particleList);
        double[] gBestPosition = g.bestPastPosition.clone();
        double gBestValue = g.fBest;

        // main PSO loop
        for (int iter = 0; iter < MAX_ITERS; iter++) {
            for (Particle p : particleList) {

                for (int d = 0; d < dimension; d++) {
                    double r1 = RNG.nextDouble();
                    double r2 = RNG.nextDouble();

                    p.currentVelocity[d] =
                            config.W * p.currentVelocity[d]
                                    + config.C1 * r1 * (p.bestPastPosition[d] - p.currentPosition[d])
                                    + config.C2 * r2 * (gBestPosition[d] - p.currentPosition[d]);

                    if (p.currentVelocity[d] > VMAX) p.currentVelocity[d] = VMAX;
                    if (p.currentVelocity[d] < -VMAX) p.currentVelocity[d] = -VMAX;

                    p.currentPosition[d] += p.currentVelocity[d];

                    if (p.currentPosition[d] > UPPER) p.currentPosition[d] = UPPER;
                    if (p.currentPosition[d] < LOWER) p.currentPosition[d] = LOWER;
                }

                p.evaluateAndUpdatePersonalBest(functionId);
            }

            Particle newGlobal = bestParticle(particleList);
            if (newGlobal.fBest < gBestValue) {
                gBestValue = newGlobal.fBest;
                gBestPosition = newGlobal.bestPastPosition.clone();
            }
        }

        return gBestValue;
    }

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

import java.util.Random;

public class Particle {
    public double[] currentPosition;
    public double[] currentVelocity;
    public double[] bestPastPosition;
    public double fBest; // mejor valor objetivo personal hasta ahora

    private final int dimension;
    private final Random rnd = new Random();

    public Particle(int dimension) {
        this.dimension = dimension;
        this.currentPosition = new double[dimension];
        this.currentVelocity = new double[dimension];
        this.bestPastPosition = new double[dimension];
        this.fBest = Double.POSITIVE_INFINITY;
    }

    // función objetivo f1(x) (x_i^2))
    public double objective1(double[] x) {
        double s = 0.0;
        for (int i = 0; i < x.length; i++) {
            s += x[i] * x[i];
        }
        return s;
    }

    //función objetivo f3(x)
    public double objective2(double[] x) {
        double total = 0.0;
        for (int i = 0; i < x.length; i++) {
            double partialSum = 0.0;
            for (int j = 0; j <= i; j++) {
                partialSum += x[j];
            }
            total += partialSum * partialSum;
        }
        return total;
    }

    //función objetivo f5(x)
    public double objective3(double[] x) {
        double sum = 0.0;
        for (int i = 0; i < x.length - 1; i++) {
            double xi = x[i];
            double xi1 = x[i + 1];
            sum += 100 * Math.pow((xi1 - xi * xi), 2) + Math.pow((1 - xi), 2);
        }
        return sum;
    }

    // función objetivo f8(x) (sin)
    public double objective4(double[] x) {
        double s = 0.0;
        for (int i = 0; i < x.length; i++) {
            double xi = x[i];
            s += -xi * Math.sin(Math.sqrt(Math.abs(xi)));
        }
        return s;
    }


    // evaluar posición actual y actualizar best personal si mejora
    public void evaluateAndUpdatePersonalBest() {
        double value = objective3(currentPosition);
        if (value < fBest) {
            fBest = value;
            System.arraycopy(currentPosition, 0, bestPastPosition, 0, dimension);
        }
    }
}

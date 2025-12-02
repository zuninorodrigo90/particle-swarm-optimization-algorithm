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

    // f1 - Sphere
    public double f1(double[] x) {
        double s = 0;
        for (double xi : x) s += xi * xi;
        return s;
    }

    // f2 - Schwefel 2.22
    public double f2(double[] x) {
        double sum = 0;
        double mul = 1;
        for (double xi : x) {
            sum += Math.abs(xi);
            mul *= Math.abs(xi);
        }
        return sum + mul;
    }

    // f3 - Schwefel 1.2
    public double f3(double[] x) {
        double total = 0;
        for (int i = 0; i < x.length; i++) {
            double ps = 0;
            for (int j = 0; j <= i; j++) ps += x[j];
            total += ps * ps;
        }
        return total;
    }

    // f4 - Schwefel 2.21
    public double f4(double[] x) {
        double max = Math.abs(x[0]);
        for (int i = 1; i < x.length; i++) {
            max = Math.max(max, Math.abs(x[i]));
        }
        return max;
    }

    // f5 - Rosenbrock
    public double f5(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length - 1; i++) {
            double a = x[i + 1] - x[i] * x[i];
            double b = 1 - x[i];
            sum += 100 * a * a + b * b;
        }
        return sum;
    }

    // f6 - Step Function
    public double f6(double[] x) {
        double sum = 0;
        for (double xi : x) sum += Math.floor(xi + 0.5) * Math.floor(xi + 0.5);
        return sum;
    }

    // f7 - Quartic + random noise
    public double f7(double[] x) {
        double s = 0;
        for (int i = 0; i < x.length; i++) s += (i + 1) * x[i] * x[i] * x[i] * x[i];
        return s + rnd.nextDouble(); // small noise
    }

    // f8 - Schwefel
    public double f8(double[] x) {
        double s = 0;
        for (double xi : x) s += -xi * Math.sin(Math.sqrt(Math.abs(xi)));
        return s;
    }

    // f9 - Rastrigin
    public double f9(double[] x) {
        double sum = 10 * x.length;
        for (double xi : x) sum += xi * xi - 10 * Math.cos(2 * Math.PI * xi);
        return sum;
    }

    // f10 - Ackley
    public double f10(double[] x) {
        double a = 0;
        double b = 0;
        for (double xi : x) {
            a += xi * xi;
            b += Math.cos(2 * Math.PI * xi);
        }
        double n = x.length;
        return -20 * Math.exp(-0.2 * Math.sqrt(a / n)) -
                Math.exp(b / n) + 20 + Math.E;
    }


    // evaluate current position for selected objective and update personal best
    public void evaluateAndUpdatePersonalBest(int functionId) {
        double value = evaluateFunction(functionId, currentPosition);
        if (value < fBest) {
            fBest = value;
            System.arraycopy(currentPosition, 0, bestPastPosition, 0, dimension);
        }
    }

    // maps function index to corresponding objective
    public double evaluateFunction(int functionId, double[] x) {
        return switch (functionId) {
            case 1 -> f1(x);
            case 2 -> f2(x);
            case 3 -> f3(x);
            case 4 -> f4(x);
            case 5 -> f5(x);
            case 6 -> f6(x);
            case 7 -> f7(x);
            case 8 -> f8(x);
            case 9 -> f9(x);
            case 10 -> f10(x);
            default -> throw new IllegalArgumentException("Invalid function ID");
        };
    }


}

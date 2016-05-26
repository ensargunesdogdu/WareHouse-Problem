import java.util.Random;

/**
 * Created by z003b09e on 25.05.2016.
 */
public class GenoType implements Comparable<GenoType> {
    int[] match;
    Double fitness;
    private Problem problem;
    Random random = new Random();

    public GenoType(int[] match, double feasible, Problem problem) {
        this.match = match;
        this.fitness = feasible;
        this.problem = problem;
    }

    public int[] getMatch() {
        return match;
    }

    public void setMatch(int[] match) {
        this.match = match;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(GenoType o) {
        if (fitness > o.getFitness())
            return 1;
        else if (fitness.equals(o.getFitness()))
            return 0;
        return -1;
//        return fitness - o.getFitness() >= 0 ? 1 : -1;
    }

    public void print() {
        System.out.format("%.3f%n", fitness);     // -->  "3.142"
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : match) {
            stringBuilder.append(i).append(" ");
        }
        System.out.println(stringBuilder.toString());
    }

    public int[] matchWith(GenoType genoType) {
        int[] offSpring = new int[match.length];
        for (int i = 0; i < match.length; i++) {
            boolean b = random.nextBoolean();//if true get from first one
            if (b) {
                offSpring[i] = match[i];
            } else {
                offSpring[i] = genoType.getMatch()[i];
            }
        }
        return offSpring;
    }

    public void mutate() {
        double lastFitness = fitness;
        fitness = -1.0;
        while (fitness < 0) {
            int i = random.nextInt(match.length);
            int j = random.nextInt(match.length);
            while (i == j)
                j = random.nextInt(match.length);
            int index1 = match[i];
            match[i] = match[j];
            match[j] = match[index1];
            fitness = problem.isFeasible(match, false);
            if (fitness > (lastFitness * problem.lastFeasibleDomain * 1.1))
                fitness = -1.0;
        }
    }
}

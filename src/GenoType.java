/**
 * Created by z003b09e on 25.05.2016.
 */
public class GenoType implements Comparable<GenoType> {
    int[] match;
    Double fitness;

    public GenoType(int[] match, double feasible) {
        this.match = match;
        this.fitness = feasible;
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
        return fitness - o.getFitness() >= 0 ? 1 : -1;
    }

    public void print() {
        System.out.println(fitness);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : match) {
            stringBuilder.append(i).append(" ");
        }
        System.out.println(stringBuilder.toString());
    }
}

import java.util.Random;

/**
 * Created by z003b09e on 25.05.2016.
 */
public class Chromosome implements Comparable<Chromosome> {
    int[] match;
    Double fitness;
    private Problem problem;
    Random random = new Random();

    public Chromosome(int[] match, double feasible, Problem problem) {
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
    public int compareTo(Chromosome o) {
        if (fitness > o.getFitness())
            return 1;
        else if (fitness.equals(o.getFitness()))
            return 0;
        return -1;
//        return fitness - o.getFitness() >= 0 ? 1 : -1;
    }

    public void print() {
        System.out.format("%.3f%n", fitness);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : match) {
            stringBuilder.append(i).append(" ");
        }
        System.out.println(stringBuilder.toString());
    }

    public int[] matchWith(Chromosome genoType) {
        int[] offSpring = new int[match.length];
        Customer[] customers = problem.getCustomers();
        for (int i = 0; i < match.length; i++) {
            int wareHouseX = match[i];
            int wareHouseY = genoType.getMatch()[i];
            Customer customer = customers[i];
            double[] costs = customer.costToWareHouses;
            if (isXBetter(wareHouseX, wareHouseY, i))
                offSpring[i] = wareHouseX;
            else {
                offSpring[i] = wareHouseY;
            }
        }
        return offSpring;
    }

    private boolean isXBetter(int wareHouseX, int wareHouseY, int index) {
        WareHouse X = problem.getWareHouses()[wareHouseX];
        WareHouse Y = problem.getWareHouses()[wareHouseY];
        Customer customer = problem.getCustomers()[index];
        double costToWareHouseX = customer.costToWareHouses[wareHouseX];
        double costToWareHouseY = customer.costToWareHouses[wareHouseY];
        isWareHouseinTheMatch(wareHouseX, index);
        isWareHouseinTheMatch(wareHouseY, index);
        double totalCostToX = isWareHouseinTheMatch(wareHouseX, index) ? costToWareHouseX : costToWareHouseX + X.getRemainingCapacity();
        double totalCostToY = isWareHouseinTheMatch(wareHouseY, index) ? costToWareHouseY : costToWareHouseY + Y.getRemainingCapacity();
        return totalCostToX - totalCostToY < 0;
    }

    private boolean isWareHouseinTheMatch(int wareHouse, int index) {
        for (int i = 0; i < index; i++) {
            if (match[i] == wareHouse)
                return true;
        }
        return false;
    }

    public void mutate() {
        double lastFitness = fitness;
        fitness = -1.0;
        int remianedNumberOfTry = 15;
        int[] oldmatch = match;
        while (fitness < 0) {
            int i = random.nextInt(match.length);
            int j = random.nextInt(match.length);
            int trycount = 15;
            while (i == j && trycount > 0) {
                j = random.nextInt(match.length);
                trycount--;
            }
            int index1 = match[i];
            match[i] = match[j];
            match[j] = index1;
            fitness = problem.isFeasible(match, true);
            if (fitness > (lastFitness * problem.lastFeasibleDomain * 1.1))
                fitness = -1.0;
            remianedNumberOfTry--;
            if (remianedNumberOfTry < 0) {
                match = oldmatch;
                fitness = problem.isFeasible(match, false);
                break;
            }
        }
    }

    public void mutate2() {
        double lastFitness = fitness;
        fitness = -1.0;
        int remianedNumberOfTry = 15;
        int[] oldmatch = match;
        while (fitness < 0) {
            int i = random.nextInt(match.length);
            int j = random.nextInt(match.length);
            int trycount = 15;
            while (i == j && trycount > 0) {
                j = random.nextInt(match.length);
                trycount--;
            }
            int index1 = match[i];
            match[i] = match[j];
            match[j] = index1;
            fitness = problem.isFeasible(match, true);
            if (fitness > lastFitness)
                fitness = -1.0;
            remianedNumberOfTry--;
            if (remianedNumberOfTry < 0) {
                match = oldmatch;
                fitness = problem.isFeasible(match, false);
                break;
            }
        }
    }
}

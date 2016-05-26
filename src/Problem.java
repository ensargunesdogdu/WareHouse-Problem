import java.util.*;

/**
 * Created by ens on 5/26/16.
 */
public class Problem {
    static Random random = new Random();
    static int pmutation = 5; //out of 10
    static int popSize = 1000;
    static int maxExecution = 100;
    static double replaceRatio = 0.1;
    static double offSpringRation = 0.2;
    private Customer[] customers;
    private WareHouse[] wareHouses;

    public Problem(Customer[] customers, WareHouse[] wareHouses) {
        this.customers = customers;
        this.wareHouses = wareHouses;
    }

    public void solve() {
        List<GenoType> population = createInitialPopulation(customers, wareHouses);
        int nofOffsprings = (int) (offSpringRation * popSize);
        for (int i = 0; i < maxExecution; i++) {
            Collections.sort(population);
            List<GenoType> offSprings = createOffsprings(population, nofOffsprings, wareHouses, customers);
            Collections.sort(offSprings);
            for (int j = 0; j < nofOffsprings; j++) {
                int removeIndex = popSize - 1 - j;
                population.remove(removeIndex);
                population.add(removeIndex, offSprings.get(nofOffsprings - 1 - j));
            }
            mutatePopulation(population);
        }
        printBestOne(population);
    }

    private static void mutatePopulation(List<GenoType> population) {
        for (GenoType genoType : population) {
            int i = random.nextInt(10);
            if (i > pmutation) {
                genoType.mutate();
            }
        }

    }

    private List<GenoType> createOffsprings(List<GenoType> population, int numberOfParentWillBeReplaced, WareHouse[] wareHouses, Customer[] customers) {
        List<GenoType> list = new ArrayList<>();
        for (int i = 0; i < numberOfParentWillBeReplaced * 2; i = i + 2) {
            double feasible = -1;
            int[] offSpring = new int[population.get(0).getMatch().length];
            while (feasible <= 0) {
                offSpring = population.get(i).matchWith(population.get(i + 1));
                feasible = isFeasible(offSpring);
            }
            list.add(new GenoType(offSpring, feasible, this));
        }
        return list;
    }

    private void printBestOne(List<GenoType> initialPopulation) {
        Collections.sort(initialPopulation);
        initialPopulation.get(0).print();
    }

    private List<GenoType> createInitialPopulation(Customer[] customers, WareHouse[] wareHouses) {
        List<GenoType> population = new LinkedList<GenoType>();
        for (int i = 0; i < popSize; i++) {
            population.add(createGene(customers, wareHouses));
        }
        return population;
    }

    private GenoType createGene(Customer[] customers, WareHouse[] wareHouses) {
        int[] match = new int[customers.length];
        double feasible = -1;
        while (feasible <= 0) {
            for (int i = 0; i < match.length; i++) {
                match[i] = random.nextInt(wareHouses.length);
            }
            feasible = isFeasible(match);
        }
        return new GenoType(match, feasible, this);
    }

    static Double lastFeasibleNumber = null;
    static double lastFeasibleDomain = 1.05;

    public double isFeasible(int[] match) {
        return isFeasible(match, true);
    }

    public double isFeasible(int[] match, boolean checkLast) {
        resetRemaininCapacities(wareHouses);
        double fitness = 0.0;
        for (int i = 0; i < match.length; i++) {
            int i1 = match[i];
            WareHouse wareHouse = wareHouses[i1];
            if (!wareHouse.isAdded()) {
                fitness += wareHouse.getSetupCost();
                wareHouse.setAdded(true);
            }
            wareHouse.setRemainingCapacity(wareHouse.getRemainingCapacity() - customers[i].getDemand());
            if (wareHouse.getRemainingCapacity() < 0)
                return -1;
            fitness += customers[i].getCostToWareHouses()[i1];
        }
        if (checkLast) {
            if (lastFeasibleNumber == null)
                lastFeasibleNumber = fitness;
            if (lastFeasibleNumber * lastFeasibleDomain < fitness)
                return -1;
        }
        return fitness;
    }

    private void resetRemaininCapacities(WareHouse[] wareHouses) {
        for (WareHouse wareHouse : wareHouses) {
            wareHouse.setRemainingCapacity(wareHouse.getCapacity());
            wareHouse.setAdded(false);
        }
    }
}

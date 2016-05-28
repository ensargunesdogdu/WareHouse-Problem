import java.util.*;

/**
 * Created by ens on 5/26/16.
 */
public class Problem {
    static Random random = new Random();
    static int pmutation = 8; //out of 10
    static int popSize = 1200;
    static int maxExecution = 100;
    static double replaceRatio = 0.1;
    static double offSpringRation = 0.5;
    int pBestValue = 15; //out of 100
    double initChangeRatio = 0.4;

    private Customer[] customers;
    private WareHouse[] wareHouses;
    int geneLength;

    static Double lastFeasibleNumber = null;
    static double lastFeasibleDomain = 1.02;

    public Problem(Customer[] customers, WareHouse[] wareHouses) {
        this.customers = customers;
        this.wareHouses = wareHouses;
        this.geneLength = customers.length;
    }

    int iterationCount = 3;

    public void doubleSolve() {
        List<Chromosome> pop = null;
        for (int i = 0; i < iterationCount; i++) {
            pop = solve(pop);
        }
        printBestOne(pop);
    }

    public List<Chromosome> solve(List<Chromosome> pop) {
        List<Chromosome> population = createInitialPopulation();
        if (pop != null) {
            Collections.sort(population);
            Collections.sort(pop);
            for (int i = 0; i < popSize * initChangeRatio; i++) {
                population.remove(popSize - 1 - i);
                population.add(popSize - 1 - i, pop.get(i));
            }
        }
        int nofOffsprings = (int) (offSpringRation * popSize);
        for (int i = 0; i < maxExecution; i++) {
            Collections.sort(population);
            List<Chromosome> offSprings = createOffsprings(population, nofOffsprings, wareHouses, customers);
            Collections.sort(offSprings);
            for (int j = 0; j < nofOffsprings; j++) {
                int removeIndex = popSize - 1 - j;
                population.remove(removeIndex);
                population.add(removeIndex, offSprings.get(nofOffsprings - 1 - j));
            }
            mutatePopulation(population);
//            System.out.println("i = " + i);
        }
//        System.out.println(" End of solve");
        return population;
    }

    private static void mutatePopulation(List<Chromosome> population) {
        for (Chromosome genoType : population) {
            int i = random.nextInt(10);
            if (i > pmutation) {
                genoType.mutate();
            }
        }
    }

    private List<Chromosome> createOffsprings(List<Chromosome> population, int numberOfParentWillBeReplaced, WareHouse[] wareHouses, Customer[] customers) {
        List<Chromosome> list = new ArrayList<>();
        for (int i = 0; i < numberOfParentWillBeReplaced * 2; i = i + 2) {
            double feasible = -1;
            int[] offSpring = new int[geneLength];
            while (feasible <= 0) {
                offSpring = population.get(i).matchWith(population.get(i + 1));
                feasible = isFeasible(offSpring);
            }
            list.add(new Chromosome(offSpring, feasible, this));
        }
        return list;
    }

    private void printBestOne(List<Chromosome> initialPopulation) {
        Collections.sort(initialPopulation);
        initialPopulation.get(0).print();
    }

    private List<Chromosome> createInitialPopulation() {
        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < popSize; i++) {
            population.add(createGene());
        }
        return population;
    }


    private Chromosome createGene() {
        int[] match = new int[geneLength];
        double feasible = -1;
        while (feasible <= 0) {
            if (random.nextBoolean()) {
                for (int i = geneLength - 1; i >= 0; i--) {
                    if (random.nextInt(100) < pBestValue) {
                        match[i] = getCustomerBest(i,match);
                    } else
                        match[i] = random.nextInt(wareHouses.length);
                }
                feasible = isFeasible(match);
            } else {
                for (int i = 0; i < geneLength; i++) {
                    if (random.nextInt(100) < pBestValue) {
                        match[i] = getCustomerBest(i, match);
                    } else
                        match[i] = random.nextInt(wareHouses.length);
                }
                feasible = isFeasible(match);
            }

        }
        return new Chromosome(match, feasible, this);
    }

    private int getCustomerBest(int customerIndex, int[] match) {
        Customer customer = customers[customerIndex];
        return customer.getBestWareHouse(wareHouses);
    }

    public double isFeasible(int[] match) {
        return isFeasible(match, true);
    }

    public double isFeasible(int[] match, boolean checkLast) {
        resetRemaininCapacities(wareHouses);
        return getFitness(match);
    }

    private double getFitness(int[] match) {
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
        return fitness;
    }

    private void resetRemaininCapacities(WareHouse[] wareHouses) {
        for (WareHouse wareHouse : wareHouses) {
            wareHouse.setRemainingCapacity(wareHouse.getCapacity());
            wareHouse.setAdded(false);
        }
    }

    public WareHouse[] getWareHouses() {
        return wareHouses;
    }

    public void setWareHouses(WareHouse[] wareHouses) {
        this.wareHouses = wareHouses;
    }

    public Customer[] getCustomers() {
        return customers;
    }
}

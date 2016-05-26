import java.util.*;

/**
 * Created by z003b09e on 26.05.2016.
 */
public class Problem {
    Double pmutation = 0.1;
    int popSize = 1000;
    double offSpringRation = 0.3;
    int maxExecution = 100;
    Random random = new Random();
    Map<Integer[], Double> fitnessList = new HashMap<>();

    private final Customer[] customers;
    private final WareHouse[] wareHouses;

    public Problem(Customer[] customers, WareHouse[] wareHouses) {
        this.customers = customers;
        this.wareHouses = wareHouses;
    }

    public void solve() {
        List<GenoType> population = createInitialPopulation(customers, wareHouses);
        int numberOfParentWillBeReplaced = (int) offSpringRation * popSize;
        for (int i = 0; i < maxExecution; i++) {
            Collections.sort(population);
            for (int j = 0; j < numberOfParentWillBeReplaced; j++) {
                List<GenoType> offSprings = createInitialPopulation(customers, wareHouses);
                Collections.sort(offSprings);
                int removeIndex = popSize - 1 - j;
                population.remove(removeIndex);
                population.add(removeIndex, offSprings.get(numberOfParentWillBeReplaced - 1 - j));
            }
        }
        printBestOne(population);
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
        Integer[] match = new Integer[customers.length];
        double feasible = -1;
        while (feasible <= 0) {
            for (int i = 0; i < match.length; i++) {
                match[i] = random.nextInt(wareHouses.length);
            }
            feasible = isFeasible(match, wareHouses, customers);
        }
        return new GenoType(match, feasible);
    }

    private Double isFeasible(Integer[] match, WareHouse[] wareHouses, Customer[] customers) {
        if (fitnessList.get(match) != null)
            return fitnessList.get(match);
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
                return -1d;
            fitness += customers[i].getCostToWareHouses()[i1];
        }
        fitnessList.put(match, fitness);
        return fitness;
    }

    private void resetRemaininCapacities(WareHouse[] wareHouses) {
        for (WareHouse wareHouse : wareHouses) {
            wareHouse.setRemainingCapacity(wareHouse.getCapacity());
            wareHouse.setAdded(false);
        }
    }
}

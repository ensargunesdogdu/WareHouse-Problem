import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by ens on 3/22/16.
 */
public class Main {
    static Random random = new Random();
    static Double pmutation = 0.1;
    static int popSize = 100;
    static int maxExecution = 100;

    public static void main(String[] args) throws IOException {
        String file_name = "sample.txt";
        FileReader reader = new FileReader(file_name);
        BufferedReader input = new BufferedReader(reader);
        String line = input.readLine();
        String[] split = line.split(" ");
        int numberofWh = Integer.parseInt(split[0]);
        int numberofCm = Integer.parseInt(split[1]);
        WareHouse[] wareHouses = new WareHouse[numberofWh];
        Customer[] customers = new Customer[numberofCm];
        for (int i = 0; i < numberofWh; i++) {
            line = input.readLine();
            String[] whInfo = line.split(" ");
            wareHouses[i] = new WareHouse(Integer.valueOf(whInfo[0]), Double.valueOf(whInfo[1]));
        }
        for (int i = 0; i < numberofCm; i++) {
            String demand = input.readLine();
            String[] costs = input.readLine().split(" ");
            double[] costsDouble = new double[costs.length];
            for (int j = 0; j < costs.length; j++) {
                String cost = costs[j];
                costsDouble[j] = Double.valueOf(cost);
            }
            customers[i] = new Customer(Integer.valueOf(demand), costsDouble);
        }
        List<GenoType> initialPopulation = createInitialPopulation(customers, wareHouses);
        printBestOne(initialPopulation);
    }

    private static void printBestOne(List<GenoType> initialPopulation) {
        Collections.sort(initialPopulation);
        initialPopulation.get(0).print();
    }

    private static List<GenoType> createInitialPopulation(Customer[] customers, WareHouse[] wareHouses) {
        List<GenoType> population = new LinkedList<GenoType>();
        for (int i = 0; i < popSize; i++) {
            population.add(createGene(customers, wareHouses));
        }
        return population;
    }

    private static GenoType createGene(Customer[] customers, WareHouse[] wareHouses) {
        int[] match = new int[customers.length];
        double feasible = -1;
        while (feasible <= 0) {
            for (int i = 0; i < match.length; i++) {
                match[i] = random.nextInt(wareHouses.length);
            }
            feasible = isFeasible(match, wareHouses, customers);
        }
        return new GenoType(match, feasible);
    }

    private static double isFeasible(int[] match, WareHouse[] wareHouses, Customer[] customers) {
        resetRemaininCapacities(wareHouses);
        double fitness = 0.0;
        for (int i = 0; i < match.length; i++) {
            int i1 = match[i];
            WareHouse wareHouse = wareHouses[i1];
            if (!wareHouse.isAdded())
                fitness += wareHouse.getSetupCost();
            wareHouse.setRemainingCapacity(wareHouse.getRemainingCapacity() - customers[i].getDemand());
            if (wareHouse.getRemainingCapacity() < 0)
                return -1;
            fitness += customers[i].getCostToWareHouses()[i1];
        }
        return fitness;
    }

    private static void resetRemaininCapacities(WareHouse[] wareHouses) {
        for (WareHouse wareHouse : wareHouses) {
            wareHouse.setRemainingCapacity(wareHouse.getCapacity());
            wareHouse.setAdded(false);
        }
    }
}

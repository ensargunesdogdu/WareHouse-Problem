import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by ens on 3/22/16.
 */
//python submit.pyc 3 -u gunesdogdu16@itu.edu.tr -p fojyidga -b
public class Solver {
//    static WareHouse[] wareHouses;
//    static Customer[] customers;

    public static void main(String[] args) throws IOException {
//        String file_name = "sample.txt";
        if (args.length != 1) {
            System.out.printf("Usage: No input_file Please add a file\n");
            System.exit(1);
        }
        String file_name = args[0];
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
        Problem problem = new Problem(customers, wareHouses);
        problem.doubleSolve();
    }
}

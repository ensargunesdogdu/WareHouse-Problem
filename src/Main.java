import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ens on 3/22/16.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        String file_name = "wl_3_1.txt";
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
        for (int i = 0; i < numberofWh; i++) {
            String demand = input.readLine();
            String[] costs = input.readLine().split(" ");
            double[] costsDouble = new double[costs.length];
            for (int j = 0; j < costs.length; j++) {
                String cost = costs[j];
                costsDouble[j] = Double.valueOf(cost);
            }
            customers[i] = new Customer(Integer.valueOf(demand), costsDouble);
        }
        System.out.println("customers.length = " + customers.length);
        System.out.println("wareHouses.length = " + wareHouses.length);
    }
}

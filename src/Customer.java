/**
 * Created by ens on 3/22/16.
 */
public class Customer {
    int demand;
    double[] costToWareHouses;

    public Customer(int demand, double[] costToWareHouses) {
        this.demand = demand;
        this.costToWareHouses = costToWareHouses;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public double[] getCostToWareHouses() {
        return costToWareHouses;
    }

    public void setCostToWareHouses(double[] costToWareHouses) {
        this.costToWareHouses = costToWareHouses;
    }

    public int getBestWareHouse(WareHouse[] wareHouses) {
        double min = costToWareHouses[0] + wareHouses[0].getSetupCost();
        int minIndex = 0;
        for (int i = 0; i < costToWareHouses.length; i++) {
            double cost = costToWareHouses[i] + wareHouses[i].getSetupCost();
            if (cost < min) {
                min = cost;
                minIndex = i;
            }
        }
        return minIndex;
    }
}

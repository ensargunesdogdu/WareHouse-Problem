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
}

/**
 * Created by ens on 3/22/16.
 */
public class WareHouse {
    int capacity;
    double setupCost;

    public WareHouse(int capacity, double setupCost) {
        this.capacity = capacity;
        this.setupCost = setupCost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSetupCost() {
        return setupCost;
    }

    public void setSetupCost(double setupCost) {
        this.setupCost = setupCost;
    }
}

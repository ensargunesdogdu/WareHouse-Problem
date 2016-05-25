/**
 * Created by ens on 3/22/16.
 */
public class WareHouse {
    int capacity;
    double setupCost;
    int remainingCapacity;
    private boolean added = false;

    public WareHouse(int capacity, double setupCost) {
        this.capacity = capacity;
        this.setupCost = setupCost;
        this.remainingCapacity = capacity;
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

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }
}

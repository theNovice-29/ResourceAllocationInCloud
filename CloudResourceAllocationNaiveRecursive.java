public class CloudResourceAllocationNaiveRecursive {

    static class VM {
        int cpuDemand;
        int ramDemand;
        int cpuCost;
        int ramCost;

        VM(int cpuDemand, int ramDemand, int cpuCost, int ramCost) {
            this.cpuDemand = cpuDemand;
            this.ramDemand = ramDemand;
            this.cpuCost = cpuCost;
            this.ramCost = ramCost;
        }
    }

    public static void main(String[] args) {
        VM[] VMs = {
                new VM(2, 4, 5, 10), // VM1
                new VM(1, 2, 4, 8),  // VM2
                new VM(3, 6, 6, 12)  // VM3
        };
        int availableCPU = 6; // Total available CPUs
        int availableRAM = 12; // Total available RAM in GB

        int minCost = cloudResourceAllocationNaiveRecursive(0, availableCPU, availableRAM, VMs, 0);
        System.out.println("The minimum cost of allocating resources to all VMs is: $" + minCost);
    }

    public static int cloudResourceAllocationNaiveRecursive(int index, int availableCPU, int availableRAM, VM[] VMs, int currentCost) {
        // Base case: If no more VMs need to be allocated, return the current cost
        if (index == VMs.length) {
            return currentCost;
        }

        // Calculate the cost if we allocate resources to the current VM
        int costWithCurrentVM = Integer.MAX_VALUE;
        if (availableCPU >= VMs[index].cpuDemand && availableRAM >= VMs[index].ramDemand) {
            int costForCurrentVM = VMs[index].cpuCost * VMs[index].cpuDemand + VMs[index].ramCost * VMs[index].ramDemand;
            costWithCurrentVM = cloudResourceAllocationNaiveRecursive(index + 1, availableCPU - VMs[index].cpuDemand, availableRAM - VMs[index].ramDemand, VMs, currentCost + costForCurrentVM);
        }

        // Calculate the cost if we do not allocate resources to the current VM
        int costWithoutCurrentVM = cloudResourceAllocationNaiveRecursive(index + 1, availableCPU, availableRAM, VMs, currentCost);

        // Return the minimum of the two options
        return Math.max(costWithCurrentVM, costWithoutCurrentVM);
    }
}
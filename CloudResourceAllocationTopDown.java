import java.util.HashMap;
import java.util.Map;

public class CloudResourceAllocationTopDown {
    private static final int[] CPU_COSTS = {5, 4, 6}; // Cost per CPU unit for VMs
    private static final int[] RAM_COSTS = {10, 8, 12}; // Cost per GB RAM for VMs
    private static final int[][] DEMANDS = {{2, 4}, {1, 2}, {3, 6}}; // CPU and RAM demands for VMs
    private static final int TOTAL_CPU_RESOURCES = 6; // Total available CPU units
    private static final int TOTAL_RAM_RESOURCES = 12; // Total available RAM GB

    private Map<String, Integer> memo; // Memoization table

    public CloudResourceAllocationTopDown() {
        memo = new HashMap<>();
    }

    public int allocate(int i, int remainingCPU, int remainingRAM) {
        String key = i + "," + remainingCPU + "," + remainingRAM;

        // Check memoization table
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Base case: all VMs are considered
        if (i >= DEMANDS.length) {
            return 0;
        }

        // If resources cannot be allocated to this VM, return infinity (large number)
        if (DEMANDS[i][0] > remainingCPU || DEMANDS[i][1] > remainingRAM) {
            return Integer.MAX_VALUE;
        }

        // Allocate resources to the current VM and calculate the cost
        int costWithCurrentVM = CPU_COSTS[i] * DEMANDS[i][0] + RAM_COSTS[i] * DEMANDS[i][1]
                + allocate(i + 1, remainingCPU - DEMANDS[i][0], remainingRAM - DEMANDS[i][1]);

        // Save result in memoization table
        memo.put(key, costWithCurrentVM);

        return costWithCurrentVM;
    }

    public static void main(String[] args) {
        CloudResourceAllocationTopDown allocator = new CloudResourceAllocationTopDown();
        int minCost = allocator.allocate(0, TOTAL_CPU_RESOURCES, TOTAL_RAM_RESOURCES);
        System.out.println("Minimum cost of allocating resources to all VMs: $" + minCost);
    }
}

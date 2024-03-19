public class CloudResourceAllocationBottomUp {

    public static int findMinimumCost(int[] cpuDemand, int[] ramDemand, int[] cpuCost, int[] ramCost, int totalCPU, int totalRAM) {
        int n = cpuDemand.length; // Number of VMs

        // Creating the DP table
        // +1 because we also consider the state with 0 CPUs and RAMs allocated
        int[][][] dp = new int[n + 1][totalCPU + 1][totalRAM + 1];

        // Fill the table with infinity to represent uncalculated/unreachable states
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= totalCPU; j++) {
                for (int k = 0; k <= totalRAM; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

        // Base case: 0 cost for allocating 0 resources to 0 VMs
        dp[0][0][0] = 0;

        // Populate the DP table
        for (int i = 1; i <= n; i++) {
            for (int cpu = 0; cpu <= totalCPU; cpu++) {
                for (int ram = 0; ram <= totalRAM; ram++) {
                    // If CPU and RAM are sufficient for the demands of VM i
                    if (cpu >= cpuDemand[i - 1] && ram >= ramDemand[i - 1]) {
                        // Compute cost if CPU and RAM are allocated to VM i
                        int cost = cpuCost[i - 1] * cpuDemand[i - 1] + ramCost[i - 1] * ramDemand[i - 1];

                        // Only update if the previous state is already calculated (not infinity)
                        if (dp[i - 1][cpu - cpuDemand[i - 1]][ram - ramDemand[i - 1]] != Integer.MAX_VALUE) {
                            dp[i][cpu][ram] = Math.min(dp[i][cpu][ram], dp[i - 1][cpu - cpuDemand[i - 1]][ram - ramDemand[i - 1]] + cost);
                        }
                    }
                    // Otherwise, carry over the cost from the previous VM if the current state is not yet updated or if it's cheaper
                    if (dp[i - 1][cpu][ram] != Integer.MAX_VALUE) {
                        dp[i][cpu][ram] = Math.min(dp[i][cpu][ram], dp[i - 1][cpu][ram]);
                    }
                }
            }
        }

        // Return the minimum cost found at the end of the table, which is the answer
        return dp[n][totalCPU][totalRAM];
    }

    public static void main(String[] args) {
        // Example usage:
        int[] cpuDemand = {2, 1, 3}; // CPU demands for VM1, VM2, VM3
        int[] ramDemand = {4, 2, 6}; // RAM demands in GB for VM1, VM2, VM3
        int[] cpuCost = {5, 4, 6};   // Cost per CPU unit for VM1, VM2, VM3
        int[] ramCost = {10, 8, 12}; // Cost per GB RAM for VM1, VM2, VM3
        int totalCPU = 6;            // Total available CPU units
        int totalRAM = 12;           // Total available RAM in GB

        int minimumCost = findMinimumCost(cpuDemand, ramDemand, cpuCost, ramCost, totalCPU, totalRAM);
        System.out.println("The minimum cost of allocating resources to all VMs is: $" + minimumCost);
    }
}
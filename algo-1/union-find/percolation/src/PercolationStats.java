public class PercolationStats 
{
	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 */
	public PercolationStats(int N, int T)
	{
		
	}
	
	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 */
	public double mean()
	{
		return 0.0d;
	}
	
	/**
	 * Sample standard deviation of percolation threshold
	 */
	public double stddev()
	{
		return 0.0d;
	}
	
	/**
	 * Returns lower bound of the 95% confidence interval
	 */
	public double confidenceLo()
	{
		return 0.0d;
	}
	
	/**
	 * Returns upper bound of the 95% confidence interval
	 */
	public double confidenceHi()
	{
		return 0.0d;
	}
	/**
	 * Entry point. Takes two arguments: 
	 */
	public static void main(String[] args) 
	{
		//System.out.println("Hello, world!");
		int N = 5;
		
		Percolation percolation = new Percolation(N);
		int randomRow;
		int randomColumn;
		do
		{
			randomRow = StdRandom.uniform(1, N + 1);
			randomColumn = StdRandom.uniform(1, N + 1);
			
			//percolation.open(randomRow, randomColumn);
			tryPercolate(percolation, randomRow, randomColumn);
		}
		while (!percolation.percolates());
		
		/*
		tryPercolate(percolation, 1, 1);
		tryPercolate(percolation, 1, 2);
		tryPercolate(percolation, 1, 3);
		
		tryPercolate(percolation, 3, 1);
		tryPercolate(percolation, 3, 2);
		tryPercolate(percolation, 3, 3);
		
		tryPercolate(percolation, 2, 3);
		
		tryPercolate(percolation, 5, 1);
		tryPercolate(percolation, 5, 2);
		tryPercolate(percolation, 5, 3);
		
		tryPercolate(percolation, 4, 3);
		*/
	}
	
	public static void tryPercolate(Percolation percolation, int i, int j)
	{
		percolation.open(i, j);
		System.out.println("open: " + i + "," + j);
		System.out.println(percolation.toString());
		System.out.println("percolates: " + percolation.percolates());
		System.out.println("\n");
	}
}

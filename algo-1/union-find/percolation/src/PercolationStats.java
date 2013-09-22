public class PercolationStats 
{
	private double[] _simulationsResults;
	
	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 */
	public PercolationStats(int N, int T)
	{
		_simulationsResults = new double[T];
		runMonteCarlo(N, T);
	}
	
	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 */
	public double mean()
	{
		double sum = 0;
		int len = _simulationsResults.length;
		for (int i = 0; i < len; i++)
		{
			sum += _simulationsResults[i];
		}
		
		return (double) sum / len;
	}
	
	/**
	 * Sample standard deviation of percolation threshold
	 */
	public double stddev()
	{
		double mean = mean();
		
		double sum = 0;
		int len = _simulationsResults.length;
		for (int i = 0; i < len; i++)
		{
			sum += Math.pow((double)(_simulationsResults[i] - mean), 2);
		}
		
		return Math.sqrt(sum / (len - 1));
	}
	
	/**
	 * Returns lower bound of the 95% confidence interval
	 */
	public double confidenceLo()
	{
		return mean() - 1.96d * stddev() / Math.sqrt(_simulationsResults.length);
	}
	
	/**
	 * Returns upper bound of the 95% confidence interval
	 */
	public double confidenceHi()
	{
		return mean() + 1.96d * stddev() / Math.sqrt(_simulationsResults.length);
	}
	
	private void runMonteCarlo(int gridLength, int times)
	{
		double openSitesCount;
		for (int i = 0; i < times; i++)
		{
			openSitesCount = runPercolation(gridLength);
			_simulationsResults[i] = openSitesCount;
		}
	}
	
	private double runPercolation(int gridLength)
	{
		Percolation percolation = new Percolation(gridLength);
		int randomRow;
		int randomColumn;
		
		int openSitesCount = 0;
		
		do
		{
			randomRow = StdRandom.uniform(1, gridLength + 1);
			randomColumn = StdRandom.uniform(1, gridLength + 1);
			
			if (!percolation.isOpen(randomRow, randomColumn))
			{
				percolation.open(randomRow, randomColumn);
				//tryPercolate(percolation, randomRow, randomColumn);
				openSitesCount++;
			}
		}
		while (!percolation.percolates());
		
		//System.out.println("Open sites count = " + openSitesCount);
		
		return (double) openSitesCount / (gridLength * gridLength);
	}
	
	/**
	 * Entry point. Takes two arguments: 
	 */
	public static void main(String[] args) 
	{
		//System.out.println("Hello, world!");
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(N, T);
		double mean = stats.mean();
		double stddev = stats.stddev();
		
		double confidenceLo = stats.confidenceLo();
		double confidenceHi = stats.confidenceHi();
		
		System.out.println("mean                    = " + mean);
		System.out.println("stddev                  = " + stddev);
		System.out.println("95% confidence interval = " + confidenceLo + ", " + confidenceHi);
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

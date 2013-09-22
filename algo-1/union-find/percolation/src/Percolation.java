public class Percolation 
{
	private WeightedQuickUnionUF _unionQuickFind;
	private Boolean[] _openSites;
	private int _gridLength;
	private int _upperVirtualIndex;
	private int _lowerVirtualIndex;
	
	/**
	 * Create N-by-N grid, with all sites blocked
	 */
	public Percolation(int N)
	{
		_unionQuickFind = new WeightedQuickUnionUF(N * N + 2);
		_gridLength = N;
		_openSites = new Boolean[N * N + 2];
		_openSites[0] = true;
		_openSites[N * N + 1] = true;
		for (int i = 1; i < N * N + 1; i++)
		{
			_openSites[i] = false;
		}
		
		_upperVirtualIndex = 0;
		_lowerVirtualIndex = N * N + 1;
	}
	
	/**
	 * Open site (row i, column j) if it is not already
	 */
	public void open(int i, int j)
	{
		int openValueIndex = 1 + ((i - 1) * _gridLength + (j - 1));
		_openSites[openValueIndex] = true;
		
		connectRow(openValueIndex, i - 1, j);
		connectRow(openValueIndex, i + 1, j);
		
		connectColumn(openValueIndex, i, j - 1);
		connectColumn(openValueIndex, i, j + 1);
	}
	
	private void connectRow(int openValueIndex, int i, int j)
	{
		int rowIndex;
		
		if (i > 0 && i <= _gridLength)
		{
			rowIndex = 1 + ((i - 1) * _gridLength + (j - 1));
			if (isOpen(i, j))
			{
				_unionQuickFind.union(rowIndex, openValueIndex);
			}
		}
		else
		{
			if (i == 0)
			{
				rowIndex = _upperVirtualIndex;
			}
			else
			{
				rowIndex = _lowerVirtualIndex;
			}
			_unionQuickFind.union(rowIndex, openValueIndex);
		}
	}
	
	private void connectColumn(int openValueIndex, int i, int j)
	{
		if (j > 0 && j <= _gridLength && isOpen(i, j))
		{
			_unionQuickFind.union((int) 1 + ((i - 1) * _gridLength + (j - 1)), openValueIndex);
		}
	}
	
	/**
	 * Is site (row i, column j) open?
	 */
	public boolean isOpen(int i, int j)
	{
		int arrayIndex = 1 + ((i - 1) * _gridLength + (j - 1));
		return _openSites[arrayIndex];
	}
	
	/**
	 * Is site (row i, column j) full?
	 */
	public boolean isFull(int i, int j)
	{
		int arrayIndex = 1 + ((i - 1) * _gridLength + (j - 1));
		return _openSites[arrayIndex];
	}
	
	/**
	 * Does the system percolate?
	 */
	public boolean percolates()
	{
		return _unionQuickFind.connected(_upperVirtualIndex, _lowerVirtualIndex);
	}
	
	@Override
	public String toString() 
	{
		String s = "";
		
		s += "Upper: " + (_openSites[0] ? "1" : "0") + "\n";
		
		for (int i = 1; i <= _gridLength; i++)
		{
			for (int j = 1; j <= _gridLength; j++)
			{
				s += (_openSites[(i - 1) * _gridLength + j] ? "1" : "0") + " ";
			}
			
			s += "\n";
		}
		
		s += "Lower: " + (_openSites[_gridLength * _gridLength + 1] ? "1" : "0");
		
		return s;
	}
}

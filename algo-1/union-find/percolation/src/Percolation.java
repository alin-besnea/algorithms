public class Percolation 
{
	private int _gridLength;
	private int _upperVirtualIndex;
	private int _lowerVirtualIndex;
	
	private WeightedQuickUnionUF _unionQuickFind;
	private Boolean[] _openSites;
	
	/**
	 * Create N-by-N grid, with all sites blocked
	 */
	public Percolation(int N)
	{
		_gridLength = N;
		_unionQuickFind = new WeightedQuickUnionUF(_gridLength * _gridLength + 2);
		
		initVirtualIndices();
		initOpenSites();
	}
	
	/**
	 * Open site (row i, column j) if it is not already
	 */
	public void open(int i, int j)
	{
		int openValueIndex = convertToArrayIndex(i, j);
		_openSites[openValueIndex] = true;
		
		connectRow(openValueIndex, i - 1, j);
		connectRow(openValueIndex, i + 1, j);
		
		connectColumn(openValueIndex, i, j - 1);
		connectColumn(openValueIndex, i, j + 1);
	}
	
	/**
	 * Is site (row i, column j) open?
	 */
	public boolean isOpen(int i, int j)
	{
		int arrayIndex = convertToArrayIndex(i, j);
		return _openSites[arrayIndex];
	}
	
	/**
	 * Is site (row i, column j) full?
	 */
	public boolean isFull(int i, int j)
	{
		int arrayIndex = convertToArrayIndex(i, j);
		return isOpen(i, j) && _unionQuickFind.connected(_upperVirtualIndex, arrayIndex);
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
		
		//s += "Upper: " + (_openSites[0] ? "1" : "0") + "\n";
		
		for (int i = 1; i <= _gridLength; i++)
		{
			for (int j = 1; j <= _gridLength; j++)
			{
				s += (_openSites[(i - 1) * _gridLength + j] ? "1" : "0") + " ";
			}
			
			s += "\n";
		}
		
		//s += "Lower: " + (_openSites[_gridLength * _gridLength + 1] ? "1" : "0");
		
		return s;
	}
	
	private void initVirtualIndices()
	{
		_upperVirtualIndex = 0;
		_lowerVirtualIndex = _gridLength * _gridLength + 1;
	}
	
	/**
	 * @private
	 */
	private void initOpenSites() 
	{
		_openSites = new Boolean[_gridLength * _gridLength + 2];
		
		_openSites[_upperVirtualIndex] = true;
		_openSites[_lowerVirtualIndex] = true;
		
		for (int i = 1; i < _lowerVirtualIndex; i++)
		{
			_openSites[i] = false;
		}
	}
	
	/**
	 * @private
	 */
	private int convertToArrayIndex(int i, int j)
	{
		return 1 + ((i - 1) * _gridLength + (j - 1));
	}
	
	/**
	 * @private
	 */
	private void connectRow(int openValueIndex, int i, int j)
	{
		int rowIndex;
		
		if (i > 0 && i <= _gridLength)
		{
			rowIndex = convertToArrayIndex(i, j);
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
	
	/**
	 * @private
	 */
	private void connectColumn(int openValueIndex, int i, int j)
	{
		if (j > 0 && j <= _gridLength && isOpen(i, j))
		{
			_unionQuickFind.union(convertToArrayIndex(i, j), openValueIndex);
		}
	}
}

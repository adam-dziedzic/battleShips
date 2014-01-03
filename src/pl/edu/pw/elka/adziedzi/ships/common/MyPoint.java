/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.common;

import java.util.Comparator;
import java.io.*;

/**
 * Represents Point(x,y).
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 1, 2009
 * common
 */
public class MyPoint implements Serializable
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
	private int x;
	private int y;
	
	/**
	 * Tworzy nowa instancje Point.
	 */
	public MyPoint()
	{
		x=0;
		y=0;
	}
	
	/**
	 * Tworzy nowa instancje Point z zadanych wspolrzednych (x,y).
     * 
	 * @param x
	 * @param y
	 */
	public MyPoint(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Porownuje dwie wspolrzedne.
     * 
	 * @param other Point porownywany ze wzorcem.
	 * @return Zwraca true, jezeli wspolrzedne x i y w obu obiektach sa 
	 * takie same, w innym wypadku zwraca false.
	 */
	public boolean equals(MyPoint other)
	{
		 return ( (this.x == other.getX()) && (this.y == other.getY()) );
	}

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MyPoint other = (MyPoint) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    
    @Override
    public int hashCode() {
    	// TODO Auto-generated method stub
    	return 47*x + y;
    }
    
    /**
	 * Ustawia nowe wartosci (x,y).
     * 
	 * @param x
	 * @param y
	 */
	public MyPoint setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	/**
	 * 
	 * @return Zwraca wartosc wspolrzednej x.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * 
	 * @return Zwraca wartosc wspolrzednej y.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * 
	 * @param x Ustala wartosc wspolrzednej x.
	 */
	public void setX(int x) 
	{
		this.x = x;
	}

	/**
	 * 
	 * @param y Ustala wartosc wspolrzednej y.
	 */
	public void setY(int y) 
	{
		this.y = y;
	}
	
	
	/**
     * 
	 * @return Comparator: to compare points in respect of x coordinate.
	 */
	public static Comparator<MyPoint> getXComparator()
	{
		return XComparator.instance();
	}
	
	
	/**
	 * Comparator: to compare points in respect of x coordinate.
     * 
	 */
	private static class XComparator implements Comparator<MyPoint>
	{
		private static XComparator xComparator = null;
		
		public static XComparator instance()
		{
			 if(xComparator == null)
			 {
				  xComparator = new XComparator();
			 }
			 return xComparator;
		}
		
		public int compare(final MyPoint a, final MyPoint b)
		{
			return a.x - b.x;
		}
	}
	
	/**
     * 
	 * @return Comparator: to compare points in respect of y coordinate.
	 */
	public static Comparator<MyPoint> getYComparator()
	{
		return YComparator.instance();
	}
	
	
	/**
	 * Comparator: to compare points in respect of y coordinate.
     * 
	 */
	private static class YComparator implements Comparator<MyPoint>
	{
		private static YComparator yComparator = null;
		
		public static YComparator instance()
		{
			 if(yComparator == null)
			 {
				  yComparator = new YComparator();
			 }
			 return yComparator;
		}
		
		public int compare(final MyPoint a, final MyPoint b)
		{
			return a.y - b.y;
		}
	}
	
}

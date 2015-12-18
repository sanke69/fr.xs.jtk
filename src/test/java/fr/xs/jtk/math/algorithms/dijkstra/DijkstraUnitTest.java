package fr.xs.jtk.math.algorithms.dijkstra;
import org.junit.Assert;
import org.junit.Test;

import fr.xs.jtk.math.algorithms.dijsktra.Dijkstra;

public class DijkstraUnitTest {

	private final int[][] graphe = new int[][] { 
		{ 0, 85, 217, -1, 173, -1, -1, -1, -1, -1 }, 
		{ 85, 0, -1, -1, -1, 80, -1, -1, -1, -1 }, 
		{ 217, -1, 0, -1, -1, -1, 186, 103, -1, -1 },
		{ -1, -1, -1, 0, -1, -1, -1, 183, -1, -1 }, 
		{ 173, -1, -1, -1, 0, -1, -1, -1, -1, 502 }, 
		{ -1, 80, -1, -1, -1, 0, -1, -1, 250, -1 }, 
		{ -1, -1, 186, -1, -1, -1, 0, -1, -1, -1 },
		{ -1, -1, 103, 183, -1, -1, -1, 0, -1, 167 }, 
		{ -1, -1, -1, -1, -1, 250, -1, -1, 0, 84 }, 
		{ -1, -1, -1, -1, 502, -1, -1, 167, 84, 0 }
	};

	@Test
	public void testGetPathIntArrayArrayIntInt() {
		final Dijkstra dijkstra = new Dijkstra();
		final Object[] actuals = dijkstra.getPath(this.graphe, 0, 9).toArray();
		final Object[] expected = new Object[] { 0, 2, 7, 9 };
		Assert.assertArrayEquals(expected, actuals);
	}

	@Test
	public void testGetPathIntArrayArrayIntIntArray() {
		final Dijkstra dijkstra = new Dijkstra();
		final Object[] actuals = dijkstra.getPath(this.graphe, 0, new int[] { 9, 3 }).toArray();
		final Object[] expected = new Object[] { 0, 2, 7, 9 };
		Assert.assertArrayEquals(expected, actuals);
	}

	@Test
	public void testGetPathIntArrayArrayIntArrayIntArray() {
		final Dijkstra dijkstra = new Dijkstra();
		final Object[] actuals = dijkstra.getPath(this.graphe, new int[] { 3, 1 }, new int[] { 9, 8 }).toArray();
		final Object[] expected = new Object[] { 1, 5, 8 };
		Assert.assertArrayEquals(expected, actuals);
	}
}
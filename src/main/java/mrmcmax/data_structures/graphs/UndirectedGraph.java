package mrmcmax.data_structures.graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class UndirectedGraph extends DirectedGraph {
	
	/************************/
	/****** SETTERS *********/
	/************************/
	
	public UndirectedGraph(int vertices) {
		super(vertices);
	}
	
	@Override
	public void addEdge(int v_in, int v_out) {
		array.get(v_in).add(new OneEndpointEdge(v_out));
		array.get(v_out).add(new OneEndpointEdge(v_in));
		numEdges++;
	}
	
	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity));
		array.get(v_out).add(new OneEndpointEdge(v_in, capacity));
		numEdges++;
	}
	
	public static UndirectedGraph completeUndirectedGraph(int n) {
		UndirectedGraph kn = new UndirectedGraph(n);
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (j != i) {
					kn.addEdge(i, j);
				}
			}
		}
		return kn;
	}
	

	/************************/
	/****** GETTERS *********/
	/************************/
	
	
	/**
	 * Iterator over the edges of the graph, in the order
	 * defined by the adjacency list representation
	 * @return
	 */
	public Iterator<TwoEndpointEdge> edgeIterator() {
		return new Iterator<TwoEndpointEdge>() {

			private int v;
			private Iterator<OneEndpointEdge> adj;
			
			//Instance initialization block: where we initialize v and adj
			{
				v = 0;
				adj = array.get(v).iterator();
			}
			
			@Override
			public boolean hasNext() {
				if (v < array.size() - 1) {
					return true;
				} else if (v == array.size() - 1) {
					return adj.hasNext();
				} else return false;
			}

			@Override
			public TwoEndpointEdge next() {
				OneEndpointEdge edge;
				if (adj.hasNext()) {
					edge = adj.next();
					return new TwoEndpointEdge(v, edge.endVertex);
				} else {
					v++;
					adj = array.get(v).iterator();
					return next(); //Try again
				}
			}
		};
	}
	
	public HashSet<TwoEndpointEdge> edgeSet() {
		HashSet<TwoEndpointEdge> edgeSet = new HashSet<>(this.m());
		Iterator<TwoEndpointEdge> edgeIterator = edgeIterator();
		while (edgeIterator.hasNext()) {
			edgeSet.add(edgeIterator.next());
		}
		return edgeSet;
	}
	
	public RealMatrix getLaplacianMatrix() {
		double[][] adjMatrix = getAdjacencyMatrix();
		RealMatrix A = MatrixUtils.createRealMatrix(adjMatrix);
		double[] degreeSequence = getDegreeSequence();
		RealMatrix degreeMatrix = MatrixUtils.createRealDiagonalMatrix(degreeSequence);
		RealMatrix laplacian = degreeMatrix.subtract(A);
		return laplacian;
	}
	
	public double[][] getLaplacianMatrixData() {
		return getLaplacianMatrix().getData();
	}

	public RealMatrix getInverseLaplacian() {
		EigenDecomposition spectre = spectralDecompositionLaplacian();
		double[] eigenvalues_i = spectre.getRealEigenvalues();
		double[] eigenvalues_sorted = spectre.getRealEigenvalues();
		Arrays.sort(eigenvalues_sorted);
		double[][] eigenvectors = new double[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			//eigenvectors[i] = spectre.getEigenvector(eigenvalues_sorted[i]);
		}
		return null;
	}
	
	/************************/
	/***** ALGORITHMS *******/
	/************************/
	
	public EigenDecomposition spectralDecomposition() {
		double[][] adjMatrix = getAdjacencyMatrix();
		RealMatrix A = MatrixUtils.createRealMatrix(adjMatrix);
		EigenDecomposition eig = new EigenDecomposition(A);
		return eig;
	}
	
	public EigenDecomposition spectralDecompositionLaplacian() {
		RealMatrix laplacian = getLaplacianMatrix();
		EigenDecomposition eig = new EigenDecomposition(laplacian);
		return eig;
	}
}







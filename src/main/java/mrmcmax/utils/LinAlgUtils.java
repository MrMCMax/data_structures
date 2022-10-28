package mrmcmax.utils;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;

import mrmcmax.data_structures.graphs.UndirectedGraph;

public class LinAlgUtils {
	
	public static void printEigenDecomp(double[][] matrix) {
		RealMatrix M = MatrixUtils.createRealMatrix(matrix);
		EigenDecomposition eig = new EigenDecomposition(M);
		//Print eigen vector matrix first
		RealMatrix X = eig.getV();
		System.out.println("EIGENVECTOR MATRIX X: ");
		RealMatrixFormat matrixFormat = new RealMatrixFormat("", "", "", "\n", "", ", ");
		System.out.println(matrixFormat.format(X));
		System.out.println("EIGENVALUE MATRIX LAMBDA: ");
		System.out.println(matrixFormat.format(eig.getD()));
	}
	
	public static void printMatrix(double[][] matrix) {
		RealMatrix M = MatrixUtils.createRealMatrix(matrix);
		RealMatrixFormat matrixFormat = new RealMatrixFormat("", "", "", "\n", "", ", ");
		System.out.println(matrixFormat.format(M));
	}
	
	public static double[][] multiplyMatrices(double[][] M1, double[][] M2) {
		RealMatrix A = MatrixUtils.createRealMatrix(M1);
		RealMatrix B = MatrixUtils.createRealMatrix(M2);
		return (A.multiply(B)).getData();
	}
	
	public static double[][] addMatrices(double[][] M1, double[][] M2) {
		RealMatrix A = MatrixUtils.createRealMatrix(M1);
		RealMatrix B = MatrixUtils.createRealMatrix(M2);
		return (A.add(B)).getData();
	}
	
	public static double[][] getInverse(double[][] M) {
		RealMatrix A = MatrixUtils.createRealMatrix(M);
		return (MatrixUtils.inverse(A)).getData();
	}
	
	public static void main(String[] args) {
		UndirectedGraph k = UndirectedGraph.completeUndirectedGraph(4);
		double[][] a = k.getAdjacencyMatrix();
		printMatrix(a);
		EigenDecomposition eig = k.spectralDecomposition();
		double sum = 0;
		double[] eigv = eig.getRealEigenvalues();
		for (int i = 0; i < k.n(); i++) {
			sum += eigv[i] * eigv[i] * eigv[i];
		}
		System.out.println(sum);
		double[][] a2 = LinAlgUtils.multiplyMatrices(a, a);
		double[][] a3 = LinAlgUtils.multiplyMatrices(a, a2);
		EigenDecomposition eig2 = new EigenDecomposition(MatrixUtils.createRealMatrix(a3));
		double[] eigv2 = eig2.getRealEigenvalues();
		double sum2 = 0;
		for (int i = 0; i < eigv2.length; i++) {
			sum2 += eigv2[i];
		}
		System.out.println(sum2);
	}
	

	/*
	StringBuilder sb = new StringBuilder("[");
	sb.append(eig.getRealEigenvalue(0)).append(" + ")
		.append(eig.getImagEigenvalue(0)).append("i");;
	for (int i = 1; i < matrix.length; i++) {
		sb.append(", ").append(eig.getRealEigenvalue(i))
			.append(" + ").append(eig.getImagEigenvalue(i)).append("i");
	}
	sb.append("]");
	System.out.println(sb.toString());
	*/
}


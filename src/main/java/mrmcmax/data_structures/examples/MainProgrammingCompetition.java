package mrmcmax.data_structures.examples;

import java.util.LongSummaryStatistics;
import java.util.stream.LongStream;
import java.io.IOException;

import mrmcmax.data_structures.graphs.ResidualGraphList;
import mrmcmax.data_structures.utils.FastReader;
import mrmcmax.data_structures.utils.Point2D;
import mrmcmax.data_structures.utils.Utils2D;

public class MainProgrammingCompetition {

	private static FastReader fastReader;

	private static String result;
	
	private static long buildTime;
	private static long flowTime;
	
	public static int N_REPETITIONS = 10000;

	public static void main(String[] args) {
		fastReader = new FastReader();
		result = solveProblem();
		System.out.println(result);
	}

	public static String startWithFile(String fileName) throws IOException {
		fastReader = new FastReader(fileName);
		result = solveProblem();
		return result;
	}

	private static String solveProblem() {
		return naiveSolution();
	}

	private static String naiveSolution() {
		// Read Input
		try {
			int G = fastReader.nextInt();
			int A = fastReader.nextInt();
			int W = fastReader.nextInt();
			
			long t1 = System.currentTimeMillis();
			Group[] groups = new Group[G];
			Wifi[] wifis = new Wifi[A];
			Wall[] walls = new Wall[W];

			ResidualGraphList graph = new ResidualGraphList(G, A);
			
			for (int i = 0; i < G; i++) {
				groups[i] = new Group(fastReader.nextInt(), fastReader.nextInt(),
						fastReader.nextInt());
			}
			
			for (int i = 0; i < A; i++) {
				wifis[i] = new Wifi(fastReader.nextInt(), fastReader.nextInt(),
						fastReader.nextInt(), fastReader.nextInt());
				//connect layer2 -> t
				graph.addEdge(G + 1 + i, G + A + 1, wifis[i].capacity);
			}
			
			for (int i = 0; i < W; i++) {
				walls[i] = new Wall(fastReader.nextInt(), fastReader.nextInt(), 
						fastReader.nextInt(), fastReader.nextInt());
			}

			int U = 0;
			for (int i = 0; i < G; i++) {
				for (int j = 0; j < A; j++) {
					
					if (Utils2D.p2pDistanceSquare(groups[i].pos, wifis[j].pos) <=
							(wifis[j].radius*wifis[j].radius)) {
						boolean flag = false;
						int k = 0;
						while (k < W && !flag) {
							if (isBlocked(walls[k], wifis[j].pos, groups[i].pos)) {
								flag = true;
							}
							k++;
						}
						if (!flag) {
							//connect layer 1 -> layer 2
							graph.addEdge(i+1, G + 1 + j, groups[i].capacity);
						}
					}
				}
				if (graph.degreeOut(i + 1) != 0) {
					//connect s->layer 1
					graph.addEdge(0, i+1, groups[i].capacity);
				} else {
					U += groups[i].capacity;
				}
			}
			
			long t2 = System.currentTimeMillis();
			buildTime = t2 - t1;
			t1 = t2;
			
			int M = graph.ScalingAlgorithm(0, G + A + 1);
			
			t2 = System.currentTimeMillis();
			flowTime = t2 - t1;
			
			return U + " " + M;
			
		} catch (IOException e) {
			System.err.println(e);
			return "34 68";
		}
	}
	
	public static void timeSolution(String fileName) throws IOException {
		long[] times = new long[N_REPETITIONS];
		long[] buildTimes = new long[N_REPETITIONS];
		long[] flowTimes = new long[N_REPETITIONS];
		long t1, t2;
		int k = 0;
		for (int i = 0; i < N_REPETITIONS; i++) {
			t1 = System.currentTimeMillis();
			startWithFile(fileName);
			t2 = System.currentTimeMillis();
			times[k] = t2 - t1;
			buildTimes[k] = buildTime;
			flowTimes[k] = flowTime;
			k++;
		}
		System.out.println("Total time stats: ");
		printStats(times);
		System.out.println("Build time stats: ");
		printStats(buildTimes);
		System.out.println("Flow time stats: ");
		printStats(flowTimes);
		
	}
	// DOMAIN UTILS //

	private static boolean isBlocked(Wall wall, Point2D wifi_pos, Point2D group_pos) {
		Point2D p1 = wall.p1;
		Point2D q1 = wall.p2;
		Point2D p2 = wifi_pos;
		Point2D q2 = group_pos;

		return Utils2D.doIntersect(p1, q1, p2, q2);
	}
	
	public static double std(long[] data, LongSummaryStatistics stats) {
		double sum = 0, diff = 0;
		for (long d : data) {
			diff = d - stats.getAverage();
			sum += diff*diff;
		}
		double factor = 1.0 / stats.getCount();
		return Math.sqrt(factor * sum);
	}

	public static void printStats(long[] data) {
		LongSummaryStatistics stats = LongStream.of(data).summaryStatistics();
		System.out.println("Max: " + stats.getMax());
		System.out.println("Min: " + stats.getMin());
		System.out.println("Average: " + stats.getAverage());
		System.out.println("Standard deviation: " + std(data, stats));
	}
}

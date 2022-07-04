package mrmcmax.persistence;

import java.io.IOException;
import java.util.HashMap;

import mrmcmax.data_structures.graphs.Graph;
import mrmcmax.data_structures.graphs.UndirectedGraph;

/**
 * This is a class that reads a TikZ formatted graph and returns a Graph data
 * structure. Only keeps adjacency data.
 * 
 * @author max
 *
 */
public class TikZReader implements IGraphReader {

	@Override
	public Graph loadGraphAbsolute(String absolutePath) throws IOException {
		return loadGraphRelative(absolutePath);
	}

	@Override
	public Graph loadGraphRelative(String relativePath) throws IOException {
		IFileReader reader = new FastReader(relativePath);
		return readTikZ(reader);
	}

	private Graph readTikZ(IFileReader reader) throws IOException {
		Graph ret = null;
		HashMap<Integer, Integer> vertices = new HashMap<>();
		int n = 0;
		// Now we are going to read and check that all is in place
		String line = reader.readLine().trim();
		mustBe(line, "\\begin{tikzpicture}");
		line = reader.readLine().trim();
		mustBe(line, "\\begin{pgfonlayer}{nodelayer}");
		line = reader.readLine().trim();
		while (!line.startsWith("\\end")) {
			int ID = parseNodeLine(line, ret);
			vertices.put(ID, n++);
			line = reader.readLine().trim();
		}
		mustBe(line, "\\end{pgfonlayer}");
		line = reader.readLine().trim();
		// End of vertex parsing
		ret = new UndirectedGraph(n);

		// Edge parsing
		mustBe(line, "\\begin{pgfonlayer}{edgelayer}");
		line = reader.readLine().trim();
		while (!line.startsWith("\\end")) {
			int[] endpoints = parseEdgeLine(line, ret);
			ret.addEdge(vertices.get(endpoints[0]), vertices.get(endpoints[1]));
			line = reader.readLine().trim();
		}
		return ret;
	}

	private int parseNodeLine(String line, Graph ret) throws IOException {
		String[] parts = line.split(" ");
		mustBe(parts[0], "\\node");
		int nodePart = 1;
		if (parts[nodePart].startsWith("[")) {
			while (!parts[nodePart].endsWith("]")) {
				nodePart++;
			}
			nodePart++;
		}
		// Trim leading and trailing parenthesis
		int nodeID = -1;
		try {
			nodeID = Integer.parseInt(parts[nodePart].substring(1, parts[nodePart].length() - 1));
		} catch (NumberFormatException e) {
			throw new IOException("Bad format: " + e);
		}
		return nodeID;
	}

	private int[] parseEdgeLine(String line, Graph ret) throws IOException {
		String[] parts = line.split(" ");
		mustBe(parts[0], "\\draw");
		int originPart = 1;
		if (parts[originPart].startsWith("[")) {
			while (!parts[originPart].endsWith("]")) {
				originPart++;
			}
			originPart++;
		}
		// Trim leading and trailing parenthesis
		int origin = -1;
		try {
			origin = Integer.parseInt(parts[originPart].substring(1, parts[originPart].length() - 1));
		} catch (NumberFormatException e) {
			throw new IOException("Bad format: " + e);
		}
		String endPart = parts[parts.length - 1];
		// Trim leading and trailing parenthesis
		int end = -1;
		try {
			end = Integer.parseInt(parts[parts.length - 1].substring(1, parts[parts.length - 1].length() - 2));
		} catch (NumberFormatException e) {
			throw new IOException("Bad format: " + e);
		}
		return new int[] {origin, end};
	}

	private void mustBe(String line, String mustBe) throws IOException {
		if (!line.equals(mustBe))
			throw new IOException("Bad format TikZ file: " + mustBe);
	}
}

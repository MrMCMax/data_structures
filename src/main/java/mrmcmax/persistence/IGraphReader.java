package mrmcmax.persistence;

import java.io.IOException;

import mrmcmax.data_structures.graphs.Graph;
/**
 * Interface that represents a graph loader.
 * @author max
 *
 */
public interface IGraphReader {

	/**
	 * Loads a graph from the given file,
	 * specified by an absolute path.
	 * @param absolutePath the absolute path of the graph data file.
	 * @return the graph object represented in the file
	 */
	Graph loadGraphAbsolute(String absolutePath) throws IOException;
	
	/**
	 * Loads a graph from the given file,
	 * specified by an relative path.
	 * @param relativePath the absolute path of the graph data file.
	 * @return the graph object represented in the file
	 */
	Graph loadGraphRelative(String relativePath) throws IOException;
}

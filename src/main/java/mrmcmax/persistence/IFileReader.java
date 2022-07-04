package mrmcmax.persistence;

import java.io.IOException;

public interface IFileReader {

	String readLine() throws IOException;

	int nextInt() throws IOException;

	long nextLong() throws IOException;

	double nextDouble() throws IOException;

	void close() throws IOException;

}
package mrmcmax.data_structures.trees;

public interface PlungePriorityQueue<Key extends Comparable<Key>> {
	
	void add(Key key, int value);
	
	Entry<Key> peekMin();
	
	Entry<Key> retrieveMin();
	
	void plunge(int value, Key newV);
	
	Key keyOf(int value);
	
	boolean isEmpty();
	
	class Entry<Key> {
		
		private Key key;
		private int value;
		
		public Entry(Key k, int v) {
			this.key = k; this.value = v;
		}
		
		public Key getKey() { return key; }
		
		public int getValue() { return value; }
		
		public void setKey(Key newV) {
			key = newV;
		}
		
		public String toString() {
			return "(" + key.toString() + ", " + value + ")";
		}
	}
}

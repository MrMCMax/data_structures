package mrmcmax.data_structures.graphs.maxflow;

import mrmcmax.data_structures.graphs.ResidualGraphList;

public class ScalingEdmondsKarp extends FlowAlgorithm {

	public ScalingEdmondsKarp() {
		super("ScalingEdmondsKarp");
	}
	
	@Override
	public long maxFlow(ResidualGraphList g) {
		return g.ScalingAlgorithm(g.getSource(), g.getSink());
	}
}

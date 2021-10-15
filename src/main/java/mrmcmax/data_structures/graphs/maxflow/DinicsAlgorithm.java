package mrmcmax.data_structures.graphs.maxflow;

import mrmcmax.data_structures.graphs.ResidualGraphList;

public class DinicsAlgorithm extends FlowAlgorithm {

	public DinicsAlgorithm() {
		super("DinicsAlgorithm");
	}
	@Override
	public long maxFlow(ResidualGraphList g) {
		return g.DinicAlgorithm(g.getSource(), g.getSink());
	}

}

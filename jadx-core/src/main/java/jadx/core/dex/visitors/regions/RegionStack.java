package jadx.core.dex.visitors.regions;

import jadx.core.dex.nodes.BlockNode;
import jadx.core.dex.nodes.IRegion;
import jadx.core.dex.nodes.MethodNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class RegionStack {
	private static final Logger LOG = LoggerFactory.getLogger(RegionStack.class);
	private static final boolean DEBUG = false;

	static {
		if (DEBUG)
			LOG.debug("Debug enabled for {}", RegionStack.class);
	}

	private static final class State {
		final Set<BlockNode> exits;
		IRegion region;

		public State() {
			exits = new HashSet<BlockNode>(4);
		}

		private State(State c) {
			exits = new HashSet<BlockNode>(c.exits);
		}

		public State copy() {
			return new State(this);
		}

		@Override
		public String toString() {
			return "Region: " + region + ", exits: " + exits;
		}
	}

	private final Deque<State> stack;
	private State curState;

	public RegionStack(MethodNode mth) {
		if (DEBUG)
			LOG.debug("New RegionStack: {}", mth);
		this.stack = new ArrayDeque<State>();
		this.curState = new State();
	}

	public void push(IRegion region) {
		stack.push(curState);
		if (stack.size() > 1000)
			throw new StackOverflowError("Deep code hierarchy");

		curState = curState.copy();
		curState.region = region;
		if (DEBUG)
			LOG.debug("Stack push: {}: {}", size(), curState);
	}

	public void pop() {
		curState = stack.pop();
		if (DEBUG)
			LOG.debug("Stack  pop: {}: {}", size(), curState);
	}

	/**
	 * Add boundary(exit) node for current stack frame
	 *
	 * @param exit boundary node, null will be ignored
	 */
	public void addExit(BlockNode exit) {
		if (exit != null)
			curState.exits.add(exit);
	}

	public boolean containsExit(BlockNode exit) {
		return curState.exits.contains(exit);
	}

	public IRegion peekRegion() {
		return curState.region;
	}

	public int size() {
		return stack.size();
	}

	@Override
	public String toString() {
		return "Region stack size: " + size() + ", last: " + curState;
	}
}

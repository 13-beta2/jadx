package jadx.core;

import jadx.core.dex.nodes.ClassNode;
import jadx.core.dex.visitors.DepthTraverser;
import jadx.core.dex.visitors.IDexTreeVisitor;
import jadx.core.utils.exceptions.DecodeException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProcessClass implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessClass.class);

	private final ClassNode cls;
	private final List<IDexTreeVisitor> passes;

	public ProcessClass(ClassNode cls, List<IDexTreeVisitor> passes) {
		this.cls = cls;
		this.passes = passes;
	}

	@Override
	public void run() {
		try {
			cls.load();
			for (IDexTreeVisitor visitor : passes) {
				DepthTraverser.visit(visitor, cls);
			}
		} catch (DecodeException e) {
			LOG.error("Decode exception: " + cls, e);
		} finally {
			cls.unload();
		}
	}
}

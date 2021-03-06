package jadx.core.dex.visitors.regions;

import jadx.core.dex.nodes.BlockNode;
import jadx.core.dex.nodes.IContainer;
import jadx.core.dex.nodes.IRegion;
import jadx.core.dex.nodes.MethodNode;
import jadx.core.dex.regions.Region;
import jadx.core.dex.visitors.AbstractVisitor;
import jadx.core.utils.exceptions.JadxException;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanRegions extends AbstractVisitor {
	private static final Logger LOG = LoggerFactory.getLogger(CleanRegions.class);

	@Override
	public void visit(MethodNode mth) throws JadxException {
		if (mth.isNoCode() || mth.getBasicBlocks().size() == 0)
			return;

		IRegionVisitor removeEmptyBlocks = new AbstractRegionVisitor() {
			@Override
			public void enterRegion(MethodNode mth, IRegion region) {
				if (!(region instanceof Region))
					return;

				for (Iterator<IContainer> it = region.getSubBlocks().iterator(); it.hasNext(); ) {
					IContainer container = it.next();
					if (container instanceof BlockNode) {
						BlockNode block = (BlockNode) container;
						if (block.getInstructions().isEmpty()) {
							try {
								it.remove();
							} catch (UnsupportedOperationException e) {
								LOG.warn("Can't remove block: {} from: {}, mth: {}", block, region, mth);
							}
						}
					}

				}
			}
		};
		DepthRegionTraverser.traverseAll(mth, removeEmptyBlocks);

	}
}

package jadx.core.dex.nodes.parser;

import jadx.core.dex.nodes.DexNode;
import jadx.core.dex.nodes.FieldNode;
import jadx.core.utils.exceptions.DecodeException;

import java.util.List;

import com.android.dx.io.DexBuffer.Section;
import com.android.dx.util.Leb128Utils;

public class StaticValuesParser extends EncValueParser {

	public StaticValuesParser(DexNode dex, Section in) {
		super(dex, in);
	}

	public void processFields(List<FieldNode> fields) throws DecodeException {
		int size = Leb128Utils.readUnsignedLeb128(in);
		visitArray(size);

		for (int i = 0; i < size; i++) {
			Object value = parseValue();
			fields.get(i).getAttributes().add(new FieldValueAttr(value));
		}
	}
}

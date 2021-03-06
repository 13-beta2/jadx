package jadx.core.dex.attributes;

public enum AttributeType {

	/* Multi attributes */

	JUMP(false),

	LOOP(false),
	CATCH_BLOCK(false),

	/* Uniq attributes */

	EXC_HANDLER(true),
	SPLITTER_BLOCK(true),
	FORCE_RETURN(true),

	FIELD_VALUE(true),

	JADX_ERROR(true),
	METHOD_INLINE(true),

	ENUM_CLASS(true),

	ANNOTATION_LIST(true),
	ANNOTATION_MTH_PARAMETERS(true),

	SOURCE_FILE(true),

	DECLARE_VARIABLE(true);

	private static final int notUniqCount;
	private final boolean uniq;

	static {
		// place all not unique attributes at first
		int last = -1;
		AttributeType[] vals = AttributeType.values();
		for (int i = 0; i < vals.length; i++) {
			AttributeType type = vals[i];
			if (type.notUniq())
				last = i;
		}
		notUniqCount = last + 1;
	}

	public static int getNotUniqCount() {
		return notUniqCount;
	}

	private AttributeType(boolean isUniq) {
		this.uniq = isUniq;
	}

	public boolean isUniq() {
		return uniq;
	}

	public boolean notUniq() {
		return !uniq;
	}
}

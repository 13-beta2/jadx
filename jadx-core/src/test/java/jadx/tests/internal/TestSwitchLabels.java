package jadx.tests.internal;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.or;

public class TestSwitchLabels extends InternalJadxTest {
	public static class TestCls {
		public static final int CONST_ABC = 0xABC;
		public static final int CONST_CDE = 0xCDE;

		public static class Inner {
			private static final int CONST_CDE_PRIVATE = 0xCDE;
			public int f1(int arg0) {
				switch (arg0) {
					case CONST_CDE_PRIVATE:
						return CONST_ABC;
				}
				return 0;
			}
		}

		public static int f1(int arg0) {
			switch (arg0) {
				case CONST_ABC:
					return CONST_CDE;
			}
			return 0;
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		assertThat(code, containsString("case CONST_ABC:"));
		assertThat(code, containsString("return CONST_CDE;"));

		cls.addInnerClass(getClassNode(TestCls.Inner.class));
		assertThat(code, containsString("case CONST_CDE_PRIVATE:"));
		assertThat(code, containsString(".CONST_ABC;"));
	}
}

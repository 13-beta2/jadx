package jadx.tests.internal;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestRedundantThis extends InternalJadxTest {

	public static class TestCls {
		public int field1 = 1;
		public int field2 = 2;

		public boolean f1() {
			return false;
		}

		public int method() {
			f1();
			return field1;
		}

		public void method2(int field2) {
			this.field2 = field2;
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);

		String code = cls.getCode().toString();

		assertThat(code, not(containsString("this.f1();")));
		assertThat(code, not(containsString("return this.field1;")));

		assertThat(code, containsString("this.field2 = field2;"));
	}
}

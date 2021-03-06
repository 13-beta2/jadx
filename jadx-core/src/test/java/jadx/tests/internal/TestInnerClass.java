package jadx.tests.internal;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TestInnerClass extends InternalJadxTest {

	public static class TestCls {
		public class Inner {
			public class Inner2 {
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, containsString("Inner"));
		assertThat(code, containsString("Inner2"));
		// assertThat(code, not(containsString("this$0")));
		// assertThat(code, not(containsString("super()")));
		// assertThat(code, not(containsString("/* synthetic */")));
	}
}

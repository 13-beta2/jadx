package jadx.core.utils;

import jadx.core.utils.exceptions.JadxRuntimeException;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	public static String cleanObjectName(String obj) {
		int last = obj.length() - 1;
		if (obj.charAt(0) == 'L' && obj.charAt(last) == ';')
			return obj.substring(1, last).replace('/', '.');
		else
			return obj;
	}

	public static String makeQualifiedObjectName(String obj) {
		return 'L' + obj.replace('.', '/') + ';';
	}

	public static String escape(String str) {
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			switch (c) {
				case '.':
				case '/':
				case ';':
				case '$':
				case '<':
				case '[':
					sb.append('_');
					break;

				case ']':
					sb.append('A');
					break;

				case '>':
				case ',':
				case ' ':
				case '?':
				case '*':
					break;

				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

	public static String listToString(Iterable<?> list) {
		if (list == null)
			return "";

		StringBuilder str = new StringBuilder();
		for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
			Object o = it.next();
			str.append(o);
			if (it.hasNext())
				str.append(", ");
		}
		return str.toString();
	}

	public static String arrayToString(Object[] array) {
		if (array == null)
			return "";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public static String mergeSignature(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static int getGenericEnd(String sign) {
		int end = -1;
		if (sign.startsWith("<")) {
			int pair = 1;
			for (int pos = 1; pos < sign.length(); pos++) {
				char c = sign.charAt(pos);
				if (c == '<')
					pair++;
				else if (c == '>')
					pair--;

				if (pair == 0) {
					end = pos;
					break;
				}
			}
		}
		return end;
	}

	public static void makeDirsForFile(File file) {
		File dir = file.getParentFile();
		if (!dir.exists()) {
			// if directory already created in other thread mkdirs will return false,
			// so check dir existence again
			if (!dir.mkdirs() && !dir.exists())
				throw new JadxRuntimeException("Can't create directory " + dir);
		}
	}
}

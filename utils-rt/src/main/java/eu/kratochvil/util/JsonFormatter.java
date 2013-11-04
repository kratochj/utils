package eu.kratochvil.util;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Formatter for <a href="https://code.google.com/p/json-simple/">JSON SImple implementation</a>
 * <p/>
 * <code><pre>
 *     public static void main(final String[] args) throws JSONException{
 *          final JSONObject obj =
 *          new JSONObject("{\"glossary\":{\"title\": \"example glossary\", \"GlossDiv\":{\"title\": \"S\", " +
 *              "\"GlossList\":{\"GlossEntry\":{\"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": " +
 *              "\"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", " +
 *              "\"GlossDef\":{\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", " +
 *              "\"GlossSeeAlso\": [\"GML\", \"XML\"]}, \"GlossSee\": \"markup\"}}}}}");
 *          System.out.println(JsonFormatter.format(obj));
 *      }
 * </pre></code>
 *
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class JsonFormatter {

	public static final int DEFAULT_INDENTATION_SIZE = 4;
	public static final char DEFAULT_INDENTATION_CHAR = ' ';

	public static String format(final JSONObject object) {
		final JsonVisitor visitor = new JsonVisitor(DEFAULT_INDENTATION_SIZE, DEFAULT_INDENTATION_CHAR);
		visitor.visit(object, 0);
		return visitor.toString();
	}

	private static class JsonVisitor {

		private final StringBuilder builder = new StringBuilder();
		private final int indentationSize;
		private final char indentationChar;

		public JsonVisitor(final int indentationSize, final char indentationChar) {
			this.indentationSize = indentationSize;
			this.indentationChar = indentationChar;
		}

		private void visit(final JSONArray array, final int indent) {
			final int length = array.size();
			if (length == 0) {
				write("[]", indent);
			} else {
				write("[", indent);
				for (Object anArray : array) {
					visit(anArray, indent + 1);
				}
				write("]", indent);
			}

		}

		private void visit(final JSONObject obj, final int indent) {
			final int length = obj.size();
			if (length == 0) {
				write("{}", indent);
			} else {
				write("{", indent);
				final Iterator keys = obj.keySet().iterator();
				while (keys.hasNext()) {
					final String key = (String) keys.next();
					write(key + " :", indent + 1);
					visit(obj.get(key), indent + 1);
					if (keys.hasNext()) {
						write(",", indent + 1);
					}
				}
				write("}", indent);
			}

		}

		private void visit(final Object object, final int indent) {
			if (object instanceof JSONArray) {
				visit((JSONArray) object, indent);
			} else if (object instanceof JSONObject) {
				visit((JSONObject) object, indent);
			} else {
				if (object instanceof String) {
					write("\"" + object + "\"", indent);
				} else {
					write(String.valueOf(object), indent);
				}
			}

		}

		private void write(final String data, final int indent) {
			for (int i = 0; i < (indent * indentationSize); i++) {
				builder.append(indentationChar);
			}
			builder.append(data).append('\n');
		}

		@Override
		public String toString() {
			return builder.toString();
		}

	}


}

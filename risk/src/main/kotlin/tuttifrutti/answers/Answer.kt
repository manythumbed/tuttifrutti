package tuttifrutti.answers

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.JsonReader

abstract class Answer(val label: String?)

class Text(val text: String, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)
class Flag(val flag: Boolean, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)

class AnswerTypeAdapter() : TypeAdapter<Answer>() {
	override fun write(writer: JsonWriter?, value: Answer?) {
		if (value != null && writer != null) {
			when (value) {
				is Text -> {
					writeText(value, writer)
				}
			}
		}
	}

	private fun writeText(value: Text, writer: JsonWriter) {
		writer.beginObject()
		writer.name("text")
		writer.value(value.text)
		if (value.label != null) {
			writer.name("label")
			writer.value(value.label)
		}
		if (!value.children.empty) {
			writer.name("children")
			writer.beginArray()
			value.children.forEach { a -> this.write(writer, a) }
			writer.endArray()
		}
		writer.endObject()
	}

	override fun read(input: JsonReader?): Answer? {
		throw UnsupportedOperationException()
	}
}


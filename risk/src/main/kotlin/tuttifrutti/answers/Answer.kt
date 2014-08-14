package tuttifrutti.answers

import com.google.gson.JsonElement
import java.lang.reflect.Type
import com.google.gson.JsonSerializer
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonArray

abstract class Answer(val label: String?)

class Text(val text: String, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)
class Flag(val flag: Boolean, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)

class AnswerSerializer : JsonSerializer<Answer> {
	override fun serialize(src: Answer?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
		if (src != null && context != null) {
			return when (src) {
				is Text -> text(src, context)
				is Flag -> flag(src, context)
				else -> null
			}
		}

		return null
	}

	private fun text(value: Text, context: JsonSerializationContext): JsonObject {
		val json = JsonObject()
		json.addProperty("text", value.text)
		if (value.label != null) {
			json.addProperty("label", value.label)
		}
		if (!value.children.empty) {
			val children = JsonArray()
			value.children.forEach { a -> children.add(context.serialize(a)) }
			json.add("children", children)
		}
		return json

	}

	private fun flag(value: Flag, context: JsonSerializationContext): JsonObject {
		val json = JsonObject()
		json.addProperty("flag", value.flag)
		if (value.label != null) {
			json.addProperty("label", value.label)
		}
		if (!value.children.empty) {
			val children = JsonArray()
			value.children.forEach { a -> children.add(context.serialize(a)) }
			json.add("children", children)
		}
		return json
	}
}


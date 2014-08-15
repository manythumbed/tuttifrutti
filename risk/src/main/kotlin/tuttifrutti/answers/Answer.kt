package tuttifrutti.answers

import com.google.gson.JsonElement
import java.lang.reflect.Type
import com.google.gson.JsonSerializer
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.google.gson.JsonParser

abstract class Answer(val label: String?)

data class Text(val text: String, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)
data class Flag(val flag: Boolean, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)

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
		writeCommon(json, context, value.label, value.children)
		return json

	}

	private fun flag(value: Flag, context: JsonSerializationContext): JsonObject {
		val json = JsonObject()
		json.addProperty("flag", value.flag)
		writeCommon(json, context, value.label, value.children)
		return json
	}

	private fun writeCommon(json: JsonObject, context: JsonSerializationContext, label: String?, children: List<Answer>) {
		if (label != null) {
			json.addProperty("label", label)
		}
		if (!children.empty) {
			val answers = JsonArray()
			children.forEach { a -> answers.add(context.serialize(a)) }
			json.add("children", answers)
		}
	}
}

class AnswerDeserializer() : JsonDeserializer<Answer> {
	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Answer? {
		if (json != null && context != null) {
			val data = json.getAsJsonObject()
			if (data != null) {
				val text = readText(data, context)
				if (text != null) {
					return text
				}
				val flag = readFlag(data, context)
				if (flag != null) {
					return flag
				}
			}
		}

		return null
	}

	private fun readText(data: JsonObject, context: JsonDeserializationContext): Text? {
		val primitive = data.getAsJsonPrimitive("text")
		if (primitive != null) {
			val text = primitive.getAsString()
			if (text != null) {
				return Text(text, label(data), children(data, context))
			}
		}
		return null
	}

	private fun readFlag(data: JsonObject, context: JsonDeserializationContext): Flag? {
		val primitive = data.getAsJsonPrimitive("flag")
		if (primitive != null) {
			return Flag(primitive.getAsBoolean(), label(data), children(data, context))
		}
		return null
	}

	private fun label(data: JsonObject): String? {
		val primitive = data.getAsJsonPrimitive("label")
		if (primitive != null) {
			return primitive.getAsString()
		}

		return null
	}

	private fun children(data: JsonObject, context: JsonDeserializationContext): List<Answer> {
		val children = linkedListOf<Answer>()
		val array = data.getAsJsonArray("children")
		if (array != null) {
			array.forEach { e ->
				val child = context.deserialize<Answer>(e, javaClass<Answer>())
				if (child != null) {
					children.add(child)
				}
			}
		}
		return children
	}
}

object Answers {
	private val gson = gson()

	private fun gson(): Gson {
		val builder = GsonBuilder()
		builder.registerTypeAdapter(javaClass<Text>(), AnswerSerializer())
		builder.registerTypeAdapter(javaClass<Text>(), AnswerDeserializer())
		builder.registerTypeAdapter(javaClass<Flag>(), AnswerSerializer())
		builder.registerTypeAdapter(javaClass<Flag>(), AnswerDeserializer())
		builder.registerTypeAdapter(javaClass<Answer>(), AnswerDeserializer())

		return builder.create()!!
	}

	fun toJson(answers: List<Answer>) = gson.toJson(answers)

	fun fromJson(json: String): List<Answer>? {
		val jsonParser = JsonParser()
		val jsonElement = jsonParser.parse(json)
		if (jsonElement != null) {
			val array = jsonElement.getAsJsonArray()
			if (array != null) {
				val answers = linkedListOf<Answer>()
				array.forEach { e ->
					val answer = gson.fromJson(e, javaClass<Answer>())
					if (answer != null) {
						answers.add(answer)
					}
				}
				return answers
			}
		}
		return null
	}
}



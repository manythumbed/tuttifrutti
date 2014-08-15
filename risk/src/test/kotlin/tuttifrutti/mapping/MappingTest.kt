package tuttifrutti.mapping

import org.junit.Test as test
import tuttifrutti.answers.Answer
import tuttifrutti.answers.Text
import tuttifrutti.answers.Flag
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import tuttifrutti.answers.text

data class Person(val title: String?, val first: String, val last: String)

fun map(answers: List<Answer>): Person? {
	val title = text("person.title", answers)
	val first = text("person.first", answers)
	val last = text("person.last", answers)

	if (first != null && last != null) {
		return Person(title, first, last)
	}

	return null
}

fun build(person: Person): List<Answer> {
	return listOf(Text(empty(person.title), "person.title"), Text(person.first, "person.first"), Text(person.last, "person.first"))
}

fun empty(string: String?) = if (string != null) string else ""

class MappingTest() {
	test fun shouldMapAnswersFromSimpleStructure() {
		val answers: List<Answer> = listOf(
			Text("title", "person.title"),
			Text("firstname", "person.first"),
			Text("lastname", "person.last"),
			Flag(true)
		)

		assertNotNull(map(answers)) { person ->
			assertEquals(Person("title", "firstname", "lastname"), person)
		}
	}

	test fun shouldMapAnswersFromComplexStructure() {
		val answers: List<Answer> = listOf(
			Flag(false, null, listOf(Text("title", "person.title"))),
			Text("empty", "unused.label",
				listOf(Text("firstname", "person.first"), Text("lastname", "person.last"))),
			Flag(true)
		)

		assertNotNull(map(answers)) { person ->
			assertEquals(Person("title", "firstname", "lastname"), person)
		}
	}

	test fun shouldBuildAnswers() {
		val person = Person("title", "first", "last")

		val answers = build(person)

		assertEquals(3, answers.size)
	}
}

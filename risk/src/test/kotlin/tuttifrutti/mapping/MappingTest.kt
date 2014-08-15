package tuttifrutti.mapping

import org.junit.Test as test
import tuttifrutti.answers.Answer
import tuttifrutti.answers.Text
import tuttifrutti.answers.Flag
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import tuttifrutti.answers.Answers

data class Person(val title: String?, val first: String, val last: String)

fun map(answers: List<Answer>): Person? {
	val title = extractText(answers, "person.title")
	val first = extractText(answers, "person.first")
	val last = extractText(answers, "person.last")

	if (first != null && last != null) {
		return Person(title, first, last)
	}

	return null
}

fun build(person: Person): List<Answer> {
	return listOf(Text(empty(person.title)), Text(person.first), Text(person.last))
}

fun empty(string: String?) = if (string != null) string else ""

private fun extractText(answers: List<Answer>, label: String): String? {
	val answer = answers.firstOrNull() { a -> a.label == label }
	if (answer != null && answer is Text) {
		return answer.text
	}

	return null
}

class MappingTest() {
	test fun shouldMapAnswers() {
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

	test fun shouldBuildAnswers() {
		val person = Person("title", "first", "last")

		val answers = build(person)

		assertEquals(3, answers.size)
	}
}

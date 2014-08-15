package tuttifrutti.answers

import org.junit.Test as test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals

class AnswerTest {

	test fun shouldSaveAndLoadViaJson() {
		val answers = listOf(Text("abc", "x.y.z", listOf(Flag(false))), Flag(true, "p.q.r"))
		val json = Answers.toJson(answers)

		if (json != null) {
			assertNotNull(Answers.fromJson(json)) { a ->
				assertEquals(2, a.size)
				assertEquals(answers.get(0), a.get(0))
				assertEquals(answers.get(1), a.get(1))
			}
		}
	}

	test fun shouldExtractValueFromAnswer() {
		assertEquals(null, Text("test").text("label"))
		assertEquals(null, Text("test").flag("label"))
		assertEquals(null, Flag(false).text("label"))
		assertEquals(null, Flag(false).flag("label"))

		assertEquals("test", Text("test", "label").text("label"))
		assertEquals(null, Text("test", "label").flag("label"))
		assertEquals(null, Flag(false, "label").text("label"))
		assertEquals(false, Flag(false, "label").flag("label"))

		val answer = Text("level1", null, listOf(Flag(true, null, listOf(Text("level3a", "a"), Text("level3b", "b"), Flag(true, "c")))))

		assertEquals(null, answer.text("missing"))
		assertEquals(null, answer.flag("missing"))
		assertEquals("level3a", answer.text("a"))
		assertEquals(null, answer.flag("a"))
		assertEquals("level3b", answer.text("b"))
		assertEquals(null, answer.flag("b"))
		assertEquals(null, answer.text("c"))
		assertEquals(true , answer.flag("c"))
	}

	test fun shouldFindAnswer()	{
		val answer = Text("level1", null, listOf(Flag(true, null, listOf(Text("level3a", "a"), Text("level3b", "b"), Flag(true, "c")))))

		assertEquals(null, answer.find("missing"))
		assertEquals(Text("level3a", "a"), answer.find("a"))
		assertEquals(Text("level3b", "b"), answer.find("b"))
		assertEquals(Flag(true, "g"), answer.find("c"))

		assertEquals(Text("xyz"), Text("xyz", "b"))
	}
}

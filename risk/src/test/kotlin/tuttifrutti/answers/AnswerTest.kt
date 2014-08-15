package tuttifrutti.answers

import org.junit.Test as test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals

class AnswerTest {

	test fun shouldSaveAndLoadViaJson() {
		val answers = listOf(Text("abc", "x.y.z", listOf(Flag(false))), Flag(true, "p.q.r"))
		val json = Answers.toJson(answers)

		if (json != null) {
			println(json)
			assertNotNull(Answers.fromJson(json)) { a ->
				assertEquals(2, a.size)
				assertEquals(answers.get(0), a.get(0))
				assertEquals(answers.get(1), a.get(1))
			}
		}
	}
}

package tuttifrutti.mapping

import tuttifrutti.answers.Answer

trait Field {
}

data class Words(val text: String, val label: String? = null) : Field
data class Code(val text: String, val code: String, val children: List<Field> = listOf()) : Field
data class Choice(val text: String, val codes: List<Code>, val label: String? = null) : Field

enum class Positive {
	First
	Second
}

data class Confirmation(val text: String, val first: Code, val second: Code, val positive: Positive, val label: String? = null) : Field
data class Collection(val items: List<Field>, val label: String? = null) : Field
data class Group(val items: List<Field>, val label: String? = null) : Field

trait Specification {
	fun inject(answers: List<Answer>)
	fun fields(): List<Field>
}
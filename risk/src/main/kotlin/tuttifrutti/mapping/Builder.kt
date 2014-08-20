package tuttifrutti.mapping

import tuttifrutti.answers.Answer

trait Field {
}

data class Words(val text: String, val label: String? = null) : Field
data class Code(val text: String, val code: String, val children: List<Field> = listOf()) : Field
data class Choice(val text: String, val codes: List<Code>, val label: String? = null) : Field

data class Confirmation(val text: String, val first: Code, val second: Code, val positive: String, val label: String? = null) : Field	{
	{
		require(first.code != second.code, "The first and second code must be different")
		require(first.code == positive || second.code == positive, "The positive value must be either the first or second code")
	}
}

data class Collection(val items: List<Field>, val label: String? = null) : Field
data class Group(val items: List<Field>, val label: String? = null) : Field

trait Specification {
	fun inject(answers: List<Answer>)
	fun fields(): List<Field>
}
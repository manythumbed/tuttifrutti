package tuttifrutti.mapping

import tuttifrutti.answers.Answer

trait Field	{
}

trait Specification	{
	fun inject(answers: List<Answer>)
	fun fields(): List<Field>
}
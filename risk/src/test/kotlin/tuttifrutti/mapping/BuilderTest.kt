package tuttifrutti.mapping

import org.junit.Test as test

class BuilderTest	{
	test fun shouldAllowFieldsToBeSpecified()	{
		val person = Group(listOf(Words("First", "first"), Words("Last", "last")), "person")

		val choice = Choice("What?", listOf(
			Code("Animal", "AN", listOf(Words("Species", "species"), Words("Name", "name"))),
			Code("Vegetable", "VT"),
			Code("Mineral", "MN"),
			Code("Other", "ZZ", listOf(Words("Description", "choice.description")))
		), "choice")

		val yes = Code("Yes", "YES")
		val no = Code("No", "NO")

		val confirm = Confirmation("Happy?", yes, no, yes.code)

		val collection = Collection(listOf(confirm, confirm))

		val group = Group(listOf(person, choice, confirm, collection))


	}
}


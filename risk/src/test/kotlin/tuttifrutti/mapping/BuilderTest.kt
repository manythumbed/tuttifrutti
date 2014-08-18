package tuttifrutti.mapping

import org.junit.Test as test

class BuilderTest	{
	test fun shouldAllowFieldsToBeSpecified()	{
		val person = Group(listOf(Words("First", "first"), Words("Last", "last")), "person")

		val choice = Choice(listOf(
			Code("Animal", "AN", listOf(Words("Species", "species"), Words("Name", "name"))),
			Code("Vegetable", "VT"),
			Code("Mineral", "MN"),
			Code("Other", "ZZ", listOf(Words("Description", "choice.description")))
		), "choice")
	}
}


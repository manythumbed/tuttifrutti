package tuttifrutti.data

enum class Title {
	Mr
	Mrs
	Miss
	Ms
	Dr
}

data class Person(val title: Title, val first: String, val last: String)	{
	{
		require(first.trim().isNotEmpty(), "First name must be a non-empty string")
		require(last.trim().isNotEmpty(), "Last name must be a non-empty string")
	}
}

data class Postcode(val code:String)	{
	{
		require(code.trim().isNotEmpty(), "Postcode must be a non-empty string")
	}
}

data class Address(val postcode: Postcode, val line1: String, val line2:String? = null, val line3:String? = null)

data class Telephone(val telephone: String)

data class Email(val email: String)

data class ContactDetails(val address: Address, val telephone: Telephone, val email:Email? = null)

data class PolicyHolder(val person: Person, val contactDetail: ContactDetails)

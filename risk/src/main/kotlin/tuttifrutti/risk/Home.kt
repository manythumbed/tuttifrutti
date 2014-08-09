package tuttifrutti.risk

enum class Lock {
	FiveLever
	Other
}

enum class PatioLock {
	Multipoint
	AntiLift
	Key
	Other
}

enum class Alarm {
	BellOnly
	Connected
}

abstract class Description()

enum class HouseType {
	Detached
	SemiDetached
	MidTerrace
	EndTerrace
	Maisonette
	TownHouse
}

data class House(val category: HouseType) : Description()

enum class FlatType {
	PurposeBuiltLow
	ConvertedLow
	PurposeBuiltHigh
	ConvertedHigh
	Maisonette
}

data class Flat(val category: FlatType, val moreThanFourFloors: Boolean, val moreThanTenFlats: Boolean) : Description()

data class Other() : Description()

enum class Ownership {
	Owned
	Mortgaged
	RentedFurnished
	RentedUnfurnished
}

enum class Occupants {
	SoleOccupier
	Family
	FamilyAndLodgers
	Shared
	Tenants
}

enum class WhenOccupied {
	Day
	Night
	DayAndNight
	Weekends
	Unoccupied
}

data class Occupied(val occupants: Occupants, val whenOccupied: WhenOccupied)

data class Security(
	val lockedWindows: Boolean,
	val mainDoor: Lock,
	val patioLock: PatioLock? = null,
	val otherDoors: Lock? = null,
	val alarm: Alarm? = null)

data class Year(val year: Int)

enum class Bedrooms {
	One
	Two
	Three
	Four
	Five
	Six
	Seven
	MoreThanSeven
}

enum class Residence {
	Main
	SecondHome
	Rental
	PersonalHoliday
	HolidayLet
}

enum class Walls {
	Standard
}

data class Condition(val largeTrees: Boolean, val walls: Walls)

data class Property(
	val address: Address,
	val built: Year,
	val bedrooms: Bedrooms,
	val description: Description,
	val residence: Residence,
	val ownership: Ownership,
	val occupied: Occupied,
	val condition: Condition,
	val security: Security)

package tuttifrutti.order

data class Risk(id: String)

data class Price(val amount: Double) {
	{
		require(amount >= 0, "A price cannot be less than zero")
	}

	fun add(p: Price) = Price(amount + p.amount)

	fun reduce(p: Price): Price {
		val reducedAmount = amount - p.amount
		return when {
			reducedAmount >= 0 -> Price(reducedAmount)
			else -> Price(0.0)
		}
	}
}

abstract class Item(val risk: Risk, val id: String)
data class Product(risk: Risk, id: String) : Item(risk, id)
data class Policy(risk: Risk, id: String) : Item(risk, id)

data class LineItem(val item: Item, val value: Price, val cost: Price) {
	{
		require(value.amount >= cost.amount, "A line item cannot cost more than value")
	}

	val discount = value.reduce(cost)
}

class Order {
	private val itemSet = hashSetOf<LineItem>()
	val items: Set<LineItem>
		get() = itemSet

	private val zero = Price(0.0)
	private var orderDiscount = zero
	val discount: Price
		get() = orderDiscount

	val value: Price
		get() = itemSet.fold(Price(0.0)) { sum, item -> sum.add(item.value) }

	val cost: Price
		get() = itemSet.fold(Price(0.0)) { sum, item -> sum.add(item.cost) }.reduce(orderDiscount)

	val saving = value.reduce(cost)

	fun add(item: Item, price: Price) {
		withItem(item) {
			itemSet.remove(it)
		}
		itemSet.add(LineItem(item, price, price))
	}

	fun remove(item: Item) {
		itemSet.removeAll(itemSet.filter { it.item == item })
	}

	fun discount(item: Item, amount: Price) {
		//		val i = itemSet.firstOrNull({ it.item == item })
		//		if (i != null) {
		//			itemSet.remove(i)
		//			itemSet.add(LineItem(i.item, i.value, i.value.reduce(amount)))
		//		}
		withItem(item) {
			itemSet.remove(it)
			itemSet.add(LineItem(it.item, it.value, it.value.reduce(amount)))
		}
	}

	fun removeDiscount(item: Item) {
//		val i = itemSet.firstOrNull({ it.item == item })
//		if (i != null) {
//			itemSet.remove(i)
//			itemSet.add(LineItem(i.item, i.value, i.value))
//		}
		withItem(item) {
			itemSet.remove(it)
			itemSet.add(LineItem(it.item, it.value, it.value))
		}
	}

	private fun withItem(item: Item, block: (item: LineItem) -> Unit) {
		val i = itemSet.firstOrNull({ it.item == item })
		if (i != null) {
			block(i)
		}
	}

	fun discount(amount: Price) {
		orderDiscount = amount
	}

	fun removeDiscount() {
		orderDiscount = zero
	}
}

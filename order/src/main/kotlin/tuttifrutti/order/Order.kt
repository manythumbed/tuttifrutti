package tuttifrutti.order

import java.math.BigDecimal
import java.math.RoundingMode

data class Risk(id: String)

data class Price(private val price: Double) {
	{
		require(price >= 0, "A price cannot be less than zero")
	}

	private val internalAmount = decimal(price)

	val amount: Double
		get() = internalAmount.doubleValue()

	fun increase(p: Price): Price {
		return Price(internalAmount.add(decimal(p.amount)).setScale(2, RoundingMode.HALF_UP).doubleValue())
	}

	fun decrease(p: Price): Price {
		val reducedAmount = internalAmount.subtract(decimal(p.amount)).setScale(2, RoundingMode.HALF_UP).doubleValue()
		return when {
			reducedAmount >= 0 -> Price(reducedAmount)
			else -> Price(0.0)
		}
	}

	private fun decimal(value: Double): BigDecimal = BigDecimal(value).setScale(2, RoundingMode.HALF_UP)
}

abstract class Item(val risk: Risk, val id: String)
data class Product(risk: Risk, id: String) : Item(risk, id)
data class Policy(risk: Risk, id: String) : Item(risk, id)

data class LineItem(val item: Item, val value: Price, val cost: Price) {
	{
		require(value.amount >= cost.amount, "A line item cannot cost more than value")
	}

	val discount = value.decrease(cost)
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
		get() = itemSet.fold(Price(0.0)) { sum, item -> sum.increase(item.value) }

	val cost: Price
		get() = itemSet.fold(Price(0.0)) { sum, item -> sum.increase(item.cost) }.decrease(orderDiscount)

	val saving: Price
		get() = value.decrease(cost)

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
		withItem(item) {
			itemSet.remove(it)
			itemSet.add(LineItem(it.item, it.value, it.value.decrease(amount)))
		}
	}

	fun removeDiscount(item: Item) {
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

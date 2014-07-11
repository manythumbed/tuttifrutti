package tuttifrutti.order

import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.failsWith

class PriceTest(): TestCase()	{
	fun testPrice()	{
		val p = Price(12.34)

		assertEquals(12.34, p.amount)
	}
}

class LineItemTest() : TestCase() {

	fun testConstructor() {
		failsWith(javaClass<IllegalArgumentException>()) {
			LineItem(Product(Risk("risk"), "P"), Price(1.00), Price(2.00))
		}
	}
}

class OrderTest() : TestCase() {

	val product1 = Product(Risk("home"), "AVIVA")
	val product2 = Product(Risk("motor"), "AVIVA")
	val zero = Price(0.0)
	val cheap = Price(12.34)
	val expensive = Price(999.99)

	fun testAddRemoveItem() {
		val order = Order()

		order.add(product1, cheap)
		assertEquals(1, order.items.size)
		assertEquals(cheap, order.value)
		assertEquals(cheap, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.add(product2, expensive)
		assertEquals(2, order.items.size)
		assertEquals(cheap.increase(expensive), order.value)
		assertEquals(cheap.increase(expensive), order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.remove(product1)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.remove(product2)
		assertEquals(0, order.items.size)
		assertEquals(zero, order.value)
		assertEquals(zero, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.remove(product2)
		assertEquals(0, order.items.size)
		assertEquals(zero, order.value)
		assertEquals(zero, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)
	}

	fun testPreventDuplicateLineItems()	{
		val order = Order()

		order.add(product1, cheap)
		assertEquals(1, order.items.size)
		assertEquals(cheap, order.value)
		assertEquals(cheap, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.add(product1, expensive)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.add(product2, expensive)
		assertEquals(2, order.items.size)
		assertEquals(expensive.increase(expensive), order.value)
		assertEquals(expensive.increase(expensive), order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)
	}

	fun testItemDiscount()	{
		val order = Order()

		order.add(product1, expensive)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.discount(product1, cheap)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive.decrease(cheap), order.cost)
		assertEquals(zero, order.discount)
		assertEquals(cheap, order.saving)

		order.discount(product1, expensive)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(zero, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(expensive, order.saving)

		order.discount(product1, expensive.increase(cheap))
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(zero, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(expensive, order.saving)
	}

	fun testOrderDiscount()	{
		val order = Order()

		order.add(product1, expensive)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive, order.cost)
		assertEquals(zero, order.discount)
		assertEquals(zero, order.saving)

		order.discount(cheap)
		assertEquals(1, order.items.size)
		assertEquals(expensive, order.value)
		assertEquals(expensive.decrease(cheap), order.cost)
		assertEquals(cheap, order.discount)
		assertEquals(cheap, order.saving)
	}
}
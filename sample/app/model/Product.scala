package model

case class Product(id: Int, name: String, price: Double, inStock: Boolean, comments: List[Comment] = List[Comment]()) {
	def getId = id
	def getName = name
	def getPrice = price
	def isInStock = inStock
	def getComments = comments
}

object Product {

	private val products = Map(1 -> Product(1, "Fresh Sweet Basil", 4.99, inStock = true),
														 2 -> Product(2, "Italian Tomato", 1.25, inStock = false,
															 List(Comment(1, "I'm so sad this product is no longer available!"),
															      Comment(2, "When do you expect to have it back?"))),
	                           3 -> Product(3, "Yellow Bell Pepper", 2.50, inStock = true),
	                           4 -> Product(4, "Old Cheddar", 18.75, inStock = true,
														 	List(Comment(3, "Very tasty! I'd definitely buy it again!"))))

	def findById(id: Int): Option[Product] = {
		products.get(id)
	}

	def findAll(): List[Product] = {
		products.values.toList
	}
}

package model

case class OrderLine(product: Product, amount: Int, purchasePrice: Double) {
	def getProduct = product
	def getAmount = amount
	def getPurchasePrice = purchasePrice
}

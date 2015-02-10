package model

case class User(firstName: String, lastName: String, age: Int) {

	def getFirstName = firstName
	def getLastName = lastName
	def getAge = age
}

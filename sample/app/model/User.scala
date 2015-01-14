package model

case class User(firstName: String, lastName: String, age: Int, nationality: String) {
	def getName = firstName + " " + lastName
}

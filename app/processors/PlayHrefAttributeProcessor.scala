package processors

import org.thymeleaf.Arguments
import org.thymeleaf.dom.Element
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor.ModificationType
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor
import utils.ActionConverter

class PlayHrefAttributeProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor("href") {

	private val HREF_ATTRIBUTE = "href"
	private val HREF_ATTRIBUTE_PRECEDENCE = 1000

	override def getTargetAttributeName(p1: Arguments, p2: Element, p3: String): String = HREF_ATTRIBUTE

	override def removeAttributeIfEmpty(p1: Arguments, p2: Element, p3: String, p4: String): Boolean = true

	override def getModificationType(p1: Arguments, p2: Element, p3: String, p4: String): ModificationType = ModificationType.SUBSTITUTION

	override def getPrecedence: Int = HREF_ATTRIBUTE_PRECEDENCE

	override def getTargetAttributeValue(arguments: Arguments, element: Element, attributeName: String): String = {
		val attributeValue = element.getAttributeValue(attributeName)
		ActionConverter.convertActionStringToUrl(arguments, attributeValue)
	}
}

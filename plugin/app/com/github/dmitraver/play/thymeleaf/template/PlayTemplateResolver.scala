package com.github.dmitraver.play.thymeleaf.template

import org.thymeleaf.templateresolver.TemplateResolver

/**
 * Template resolver configured to use [[com.github.dmitraver.play.thymeleaf.template.PlayResourceResolver]]
 * in order to read the template
 */
class PlayTemplateResolver(resourceResolver: PlayResourceResolver) extends TemplateResolver {
	setResourceResolver(resourceResolver)
}

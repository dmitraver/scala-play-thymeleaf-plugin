package com.dmitraver.play.thymeleaf.template

import org.thymeleaf.templateresolver.TemplateResolver

class PlayTemplateResolver(resourceResolver: PlayResourceResolver) extends TemplateResolver {
	setResourceResolver(resourceResolver)
}

package com.dmitraver.play.thymeleaf.template

import java.io._

import org.thymeleaf.TemplateProcessingParameters
import org.thymeleaf.resourceresolver.IResourceResolver
import play.{Logger, Play}

class PlayResourceResolver extends IResourceResolver {

	private val PLAY_RESOURCE_RESOLVER_NAME = "PLAY_RESOURCE_RESOLVER"

	override def getName: String = PLAY_RESOURCE_RESOLVER_NAME

	override def getResourceAsStream(templateProcessingParameters: TemplateProcessingParameters, resourceName: String): InputStream = {
		val rootPath: File = Play.application.path
		val templateFile: File = new File(rootPath, resourceName)

		try {
			new BufferedInputStream(new FileInputStream(templateFile))
		} catch {
			case e: FileNotFoundException =>
				Logger.error("Resource file is not found", e)
				null
		}
	}
}

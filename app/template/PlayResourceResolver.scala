package template

import java.io._

import org.thymeleaf.TemplateProcessingParameters
import org.thymeleaf.resourceresolver.IResourceResolver
import play.{Logger, Play}

class PlayResourceResolver extends IResourceResolver {

	private val PLAY_RESOURCE_RESOLVER_NAME = "PLAY_RESOURCE_RESOLVER"
	private val DEFAULT_TEMPLATES_DIRECTORY_PREFIX: String = "public"

	private var prefix = ""

	def setTemplatesDirectoryPrefix(prefix: String) {
		this.prefix = prefix
	}

	override def getName: String = PLAY_RESOURCE_RESOLVER_NAME

	override def getResourceAsStream(templateProcessingParameters: TemplateProcessingParameters, resourceName: String): InputStream = {
		val rootPath: File = Play.application.path
		val templatesDirectoryPrefix: String = if (!prefix.isEmpty) prefix else DEFAULT_TEMPLATES_DIRECTORY_PREFIX
		val templateFile: File = new File(rootPath, templatesDirectoryPrefix + "/" + resourceName)

		try {
			new BufferedInputStream(new FileInputStream(templateFile))
		}
		catch {
			case e: FileNotFoundException =>
				Logger.error("Resource file is not found", e)
				null
		}
	}
}

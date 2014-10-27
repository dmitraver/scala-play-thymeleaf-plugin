package template;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import play.Logger;
import play.Play;

import java.io.*;

public class PlayResourceResolver implements IResourceResolver {

    private static final String PLAY_RESOURCE_RESOLVER_NAME = "PLAY_RESOURCE_RESOLVER";
    private static final String DEFAULT_TEMPLATES_DIRECTORY_PREFIX = "public";

    private String prefix;

    @Override
    public String getName() {
        return PLAY_RESOURCE_RESOLVER_NAME;
    }

    public void setTemplatesDirectoryPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
        File rootPath = Play.application().path();
        String templatesDirectoryPrefix = (prefix != null) ? prefix : DEFAULT_TEMPLATES_DIRECTORY_PREFIX;
        File templateFile = new File(rootPath, templatesDirectoryPrefix + "/" + resourceName);
        try {
            return new BufferedInputStream(new FileInputStream(templateFile));
        } catch (FileNotFoundException e) {
            Logger.error("Resource file is not found", e);
            return null;
        }
    }
}

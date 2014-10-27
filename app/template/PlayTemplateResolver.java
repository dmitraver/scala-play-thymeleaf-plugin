package template;

import org.thymeleaf.templateresolver.TemplateResolver;

public class PlayTemplateResolver extends TemplateResolver {

    public PlayTemplateResolver(PlayResourceResolver playResourceResolver) {
        super.setResourceResolver(playResourceResolver);
    }
}

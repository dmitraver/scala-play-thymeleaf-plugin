package processors;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import java.util.Map;


public class PlayHrefAttributeProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {

    private static final String HREF_ATTRIBUTE = "href";
    private static final int ATTRIBUTE_PRECEDENCE = 1000;

    public PlayHrefAttributeProcessor() {
        super(HREF_ATTRIBUTE);
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String s) {
        return null;
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String s, String s2) {
        return null;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String s, String s2) {
        return false;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}

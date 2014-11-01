package l18n;

import org.thymeleaf.Arguments;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;
import play.api.i18n.Lang;
import play.i18n.Messages;

public class PlayMessageResolver implements IMessageResolver {

    private static final String MESSAGE_RESOLVER_NAME = "PLAY_MESSAGE_RESOLVER";
    private static final int MESSAGE_RESOLVER_ORDER = 0;

    private Lang lang;

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    @Override
    public String getName() {
        return MESSAGE_RESOLVER_NAME;
    }

    @Override
    public Integer getOrder() {
        return MESSAGE_RESOLVER_ORDER;
    }

    @Override
    public MessageResolution resolveMessage(Arguments arguments, String key, Object[] messageParameters) {
        String message = Messages.get(lang, key, messageParameters);
        return new MessageResolution(message);
    }

    @Override
    public void initialize() {

    }
}

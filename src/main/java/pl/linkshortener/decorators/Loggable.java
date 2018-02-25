package pl.linkshortener.decorators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.linkshortener.commands.Command;
import pl.linkshortener.commands.Executable;

public class Loggable implements Executable {

    private final Logger logger = LoggerFactory.getLogger(Loggable.class);

    private final Executable origin;

    public Loggable(Executable origin) {
        this.origin = origin;
    }

    public <C extends Command<R>, R extends Command.Response> R execute(C command) {
        logger.info(">>> {}", /*ReflectionToStringBuilder.toString(command, SHORT_PREFIX_STYLE)*/command);
        R response = origin.execute(command);
        logger.info("<<< {} ", /*ReflectionToStringBuilder.toString(response, SHORT_PREFIX_STYLE)*/response);
        return response;
    }
}
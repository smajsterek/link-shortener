package pl.linkshortener.decorators;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.linkshortener.commands.Command;
import pl.linkshortener.commands.Executable;

public class Retryable implements Executable {

    private final Logger logger = LoggerFactory.getLogger(Retryable.class);

    private final Executable origin;

    public Retryable(Executable origin) {
        this.origin = origin;
    }

    @Override
    public <C extends Command<R>, R extends Command.Response> R execute(C command) {
        Retry retry = Retry.of("command", RetryConfig.custom().maxAttempts(3).build());
        R response = null;
        try {
            response = retry.executeCallable(() -> origin.execute(command));
        } catch (Exception e) {
            logger.error("Failed to execute command.", e);
        }
        return response;
    }
}

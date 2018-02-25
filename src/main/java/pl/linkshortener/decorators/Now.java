package pl.linkshortener.decorators;

import pl.linkshortener.commands.Command;
import pl.linkshortener.commands.Executable;

public class Now {
    private final Executable executable;

    public Now(Executable executable) {
        this.executable = executable;
    }

    public <R extends Command.Response, C extends Command<R>> R execute(C command) {
        return executable.execute(command);
    }
}

package pl.linkshortener.decorators;

import pl.linkshortener.commands.Command;
import pl.linkshortener.commands.Executable;
import pl.linkshortener.commands.Reaction;

public class Executor implements Executable {

    private Reaction reaction;

    public Executor(Reaction reaction) {
        this.reaction = reaction;
    }

    @Override
    public <C extends Command<R>, R extends Command.Response> R execute(C command) {
        return (R)reaction.react(command);
    }
}

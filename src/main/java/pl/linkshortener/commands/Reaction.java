package pl.linkshortener.commands;

public interface Reaction<C extends Command<R>, R extends Command.Response> {
    R react(C command);
}

package pl.linkshortener.commands;

public interface Executable {
    <C extends Command<R>, R extends Command.Response> R execute(C command);
}

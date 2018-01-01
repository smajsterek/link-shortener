package pl.linkshortener.commands;

public interface Command<T extends Command.Response> {
    interface Response {
        class Void implements Command.Response {

        }
    }
}

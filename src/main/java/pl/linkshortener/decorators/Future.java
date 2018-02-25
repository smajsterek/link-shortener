package pl.linkshortener.decorators;

import pl.linkshortener.commands.Command;
import pl.linkshortener.commands.Executable;

import java.util.concurrent.CompletableFuture;

public class Future {

    private final Executable executable;

    public Future(Executable executable) {
        this.executable = executable;
    }

    public <R extends Command.Response, C extends Command<R>> CompletableFuture<R> schedule(C command) {
        return CompletableFuture.supplyAsync(() -> executable.execute(command));
    }
}


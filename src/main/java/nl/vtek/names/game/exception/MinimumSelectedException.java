package nl.vtek.names.game.exception;


public class MinimumSelectedException extends BadRequestException {
    public MinimumSelectedException() {
        super("Selecteer minimaal 3 kaarten");
    }
}

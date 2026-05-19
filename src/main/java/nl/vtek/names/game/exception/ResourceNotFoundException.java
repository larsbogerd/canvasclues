package nl.vtek.names.game.exception;

/**
 * This is for all 404 errors.
 *
 * Subclasses plug in their resource name and id; the message gets
 * formatted as "X not found: Y".
 */
public abstract class ResourceNotFoundException extends RuntimeException {
    protected ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " not found: " + id);
    }
}

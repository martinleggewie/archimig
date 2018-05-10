package org.codemaker.archimig.model.descriptors;

/**
 * Marker interface for all kind of transformation descriptions.
 * <p>
 * A transformation description describes one type of change (= transformation) which shall be applied to the system
 * architecture. Such descriptions are the key concept of a migration consisting of several migration steps: A system,
 * component, functionality does not just "spawn" but is added or removed to the system environment, and we can handle
 * such transformations in a generic way.
 *
 * @author Martin Leggewie
 * @since 06.02.2018
 */
public interface TrafoDescriptor {
}

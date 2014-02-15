package org.galaxyproject.sampletracker.logic.galaxy;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.net.galaxy.GalaxyRestAdapter;

/**
 * Base resource controller that create a resource instance of required type.
 * 
 * @param <T> Type of resource this controller rules
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
abstract class ResourceController<T> {

    @Inject private GalaxyRestAdapter mRestAdapter;

    private final Class<T> mClazz;

    protected ResourceController(Class<T> clazz) {
        mClazz = clazz;
    }

    /**
     * Provides an instance of Galaxy resource of <T> type.
     * 
     * @return Instance of <T> resource
     */
    protected T resource() {
        return mRestAdapter.createResource(mClazz);
    }
}

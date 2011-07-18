/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * A CacheManager is used for looking up Caches and controls their lifecycle. It represents a collection of caches.
 * To the extent that implementations have configuration at the CacheManager level, it is a way for these caches
 * to share common configuration. For example a CacheManager might be clustered so all caches in that CacheManager
 * will participate in the same cluster.
 * <p/>
 * CacheManagers are created by {@link CacheManagerFactory}.
 * <p/>
 * CacheManagers
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface CacheManager {

    /**
     * Get the name of this cache manager
     *
     * @return the name of this cache manager
     */
    String getName();

    /**
     * Adds an uninitialised {@link Cache} to the CacheManager and starts it.
     * If a cache with the same name has been previously added that cache will be stopped.
     * <p/>
     * @param cache the cache to add
     * @throws IllegalStateException if the cache is not {@link Status#UNITIALISED} before this method is called.
     * @throws CacheException        if there was an error adding the cache to the CacheManager
     */
    void addCache(Cache<?, ?> cache);

    /**
     * Looks up a named cache.
     *
     *
     * @param cacheName the name of the cache to look for
     * @return the Cache or null if it does exist
     * @throws IllegalStateException if the Cache is not {@link Status#STARTED}
     */
    <K, V> Cache getCache(String cacheName);

    /**
     * Remove a cache from the CacheManager. The cache will be stopped.
     *
     * @param cacheName the cache name
     * @return true if the cache was removed
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     */
    boolean removeCache(String cacheName) throws IllegalStateException;

    /**
     * This method will return a UserTransaction.
     *
     * @return the UserTransaction. This should be cast to javax.transaction.UserTransaction.
     */
    Object getUserTransaction();

    /**
     * Shuts down the CacheManager.
     * <p/>
     * Each cache will be shut down in no guaranteed order. While caches are being shut down their status and the status of
     * CacheManager is {@link Status#STOPPING}. As they are shut down their status is change to {@link Status#STOPPED}. Finally
     * the CacheManager's status is changed to {@link Status#STOPPED}
     * <p/>
     * A {@link IllegalStateException} will be thrown if an operation is performed on CacheManager or any contained Cache while
     * they are stopping or are a stopped.
     * <p/>
     * A given CacheManager instance cannot be restarted after it has been stopped. A new one must be created.
     */
    void shutdown();
}

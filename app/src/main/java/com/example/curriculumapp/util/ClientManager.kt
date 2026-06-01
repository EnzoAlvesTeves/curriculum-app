package com.example.curriculumapp.util

/**
 * Manages lazy API client initialization and resets.
 * Allows clearing cached clients when base URL changes.
 */
object ClientManager {
    private val cachedClients = mutableMapOf<String, Any?>()

    /**
     * Store a lazy value with a factory function for later recreation
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> managedLazy(key: String, factory: () -> T): T {
        return cachedClients.getOrPut(key) { factory() } as T
    }

    /**
     * Clear all cached clients when URL changes
     */
    fun clearClients() {
        cachedClients.clear()
    }
}



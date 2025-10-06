

package com.example.jetnews.data.interests

import com.example.jetnews.utils.addOrRemove
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

typealias TopicsMap = Map<String, List<String>>


@OptIn(ExperimentalCoroutinesApi::class)
class InterestsRepository {

    
    val topics by lazy {
        mapOf(
            "Android" to listOf("Jetpack Compose", "Kotlin", "Jetpack"),
            "Programming" to listOf("Kotlin", "Declarative UIs", "Java"),
            "Technology" to listOf("Pixel", "Google")
        )
    }

    
    private val selectedTopics = MutableStateFlow(setOf<TopicSelection>())

    
    private val mutex = Mutex()

    
    suspend fun toggleTopicSelection(topic: TopicSelection) {
        mutex.withLock {
            val set = selectedTopics.value.toMutableSet()
            set.addOrRemove(topic)
            selectedTopics.value = set
        }
    }

    
    fun observeTopicsSelected(): Flow<Set<TopicSelection>> = selectedTopics
}

data class TopicSelection(val section: String, val topic: String)

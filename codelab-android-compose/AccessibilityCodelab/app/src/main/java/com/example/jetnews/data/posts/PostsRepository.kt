

package com.example.jetnews.data.posts

import com.example.jetnews.data.posts.impl.posts
import com.example.jetnews.model.Post
import com.example.jetnews.utils.addOrRemove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class PostsRepository {
    
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    
    fun getPost(postId: String?): Post? {
        return posts.find { it.id == postId }
    }

    
    fun getPosts(): List<Post> {
        return posts
    }

    
    fun observeFavorites(): Flow<Set<String>> = favorites

    
    fun toggleFavorite(postId: String) {
        val set = favorites.value.toMutableSet()
        set.addOrRemove(postId)
        favorites.value = set
    }
}

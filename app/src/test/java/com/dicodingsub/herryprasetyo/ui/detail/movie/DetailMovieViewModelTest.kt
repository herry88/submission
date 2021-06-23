package com.dicodingsub.herryprasetyo.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
        viewModel.setMovieId(movieId)
    }

    @Test
    fun getSelectedMovie() {
        val liveDataMovie = MutableLiveData<MovieEntity>()
        val movieEntity = dummyMovie
        liveDataMovie.value = movieEntity
        Mockito.`when`(movieRepository.getDetailMovie(movieId)).thenReturn(liveDataMovie)

        assertNotNull(liveDataMovie.value?.title)
        assertEquals(dummyMovie.title, liveDataMovie.value?.title)
        assertNotNull(liveDataMovie.value?.score)
        assertEquals(dummyMovie.score, liveDataMovie.value?.score)
        assertNotNull(liveDataMovie.value?.release)
        assertEquals(dummyMovie.release, liveDataMovie.value?.release)
        assertNotNull(liveDataMovie.value?.genre)
        assertEquals(dummyMovie.genre, liveDataMovie.value?.genre)
        assertNotNull(liveDataMovie.value?.description)
        assertEquals(dummyMovie.description, liveDataMovie.value?.description)

        assertEquals(dummyMovie.poster, liveDataMovie.value?.poster)


        val observer = Mockito.mock(Observer::class.java) as Observer<*>
        viewModel.movie.observeForever(observer as Observer<in MovieEntity?>)
        Mockito.verify(observer).onChanged(movieEntity)

    }
}
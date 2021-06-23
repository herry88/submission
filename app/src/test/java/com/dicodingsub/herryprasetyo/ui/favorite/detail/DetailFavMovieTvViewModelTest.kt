@file:Suppress("DEPRECATION")

package com.dicodingsub.herryprasetyo.ui.favorite.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFavMovieTvViewModelTest {
    private lateinit var viewModel: DetailFavMovieTvViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    private val dummyTvShow = DataDummy.generateDummyTVShows()[0]
    private val tvShowId = dummyTvShow.id

    @Before
    fun setUp() {
        viewModel =
            DetailFavMovieTvViewModel(movieRepository)
        viewModel.setMovieId(movieId)
        viewModel.setTvShowId(tvShowId)

    }

    @Test
    fun getFavMovieById() {
        val liveDataMovie = MutableLiveData<MovieEntity>()
        val movieEntity = dummyMovie
        liveDataMovie.value = movieEntity
        Mockito.`when`(movieRepository.getFavMovieById(movieId)).thenReturn(liveDataMovie)

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
        viewModel.movieFav.observeForever(observer as Observer<in MovieEntity?>)
        Mockito.verify(observer).onChanged(movieEntity)
    }


    @Test
    fun getFavTvById() {
        val liveDataMovie = MutableLiveData<TvEntity>()
        val tvEntity = dummyTvShow
        liveDataMovie.value = tvEntity
        Mockito.`when`(movieRepository.getFavTvById(tvShowId)).thenReturn(liveDataMovie)

        assertNotNull(liveDataMovie.value?.title)
        assertEquals(dummyTvShow.title, liveDataMovie.value?.title)
        assertNotNull(liveDataMovie.value?.score)
        assertEquals(dummyTvShow.score, liveDataMovie.value?.score)
        assertNotNull(liveDataMovie.value?.release)
        assertEquals(dummyTvShow.release, liveDataMovie.value?.release)
        assertNotNull(liveDataMovie.value?.genre)
        assertEquals(dummyTvShow.genre, liveDataMovie.value?.genre)
        assertNotNull(liveDataMovie.value?.description)
        assertEquals(dummyTvShow.description, liveDataMovie.value?.description)
        assertEquals(dummyTvShow.poster, liveDataMovie.value?.poster)


        val observer = Mockito.mock(Observer::class.java) as Observer<*>
        viewModel.tvFav.observeForever(observer as Observer<in TvEntity?>)
        Mockito.verify(observer).onChanged(tvEntity)
    }
}
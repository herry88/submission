@file:Suppress("UNCHECKED_CAST", "DEPRECATION")

package com.dicodingsub.herryprasetyo.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class DetailViewModelTest{
    private lateinit var  viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private  lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<MovieEntity>

    private val  dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    private val dummyTvShow = DataDummy.generateDummyTVShows()[0]
    private val tvShowId = dummyTvShow.id

    private val dummyIdMovieNotFound = "234234234234234"
    private val dummyIdTvNotFound = "32321243523232"

    @Before
    fun setUp(){
        viewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getSelectedMovie(){
        viewModel.setMovieId(movieId)
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie
        Mockito.`when`(movieRepository.getDetailMovie(movieId)).thenReturn(movie)

        val movieEntity = viewModel.getMovieById().value as MovieEntity
        verify(movieRepository).getDetailMovie(movieId)

        assertNotNull(movieEntity)
        assertNotNull(movieEntity.title)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertNotNull(movieEntity.score)
        assertEquals(dummyMovie.score, movieEntity.score)
        assertNotNull(movieEntity.release)
        assertEquals(dummyMovie.release, movieEntity.release)
        assertNotNull(movieEntity.genre)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertNotNull(movieEntity.description)
        assertEquals(dummyMovie.description, movieEntity.description)

        assertEquals(dummyMovie.poster, movieEntity.poster)
        (viewModel.getMovieById() as LiveData<MovieEntity>).observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
    @Test
    fun getSelectedTvShow() {
        viewModel.setTvShowId(tvShowId)

        val tvShow = MutableLiveData<MovieEntity>()
        tvShow.value = dummyTvShow
        Mockito.`when`(movieRepository.getDetailTv(tvShowId)).thenReturn(tvShow)

        val movieEntity = viewModel.getTvShowById().value as MovieEntity
        verify(movieRepository).getDetailTv(tvShowId)


        assertNotNull(movieEntity)
        assertNotNull(movieEntity.title)
        assertEquals(dummyTvShow.title, movieEntity.title)
        assertNotNull(movieEntity.score)
        assertEquals(dummyTvShow.score, movieEntity.score)
        assertNotNull(movieEntity.release)
        assertEquals(dummyTvShow.release, movieEntity.release)
        assertNotNull(movieEntity.genre)
        assertEquals(dummyTvShow.genre, movieEntity.genre)
        assertNotNull(movieEntity.description)
        assertEquals(dummyTvShow.description, movieEntity.description)
        assertEquals(dummyTvShow.poster, movieEntity.poster)


        (viewModel.getTvShowById() as LiveData<MovieEntity>).observeForever(observer)
        verify(observer).onChanged(dummyTvShow)

    }


    @Test
    fun shouldReturnNullIfIdMovieNotFound(){
        viewModel.setMovieId(dummyIdMovieNotFound)
        val movie = MutableLiveData<MovieEntity>()
        movie.value = null
        whenever(movieRepository.getDetailMovie(dummyIdMovieNotFound)).thenReturn(movie)
        val movieNull = viewModel.getMovieById().value
        assertNull(movieNull)
        verify(movieRepository).getDetailMovie(dummyIdMovieNotFound)

        (viewModel.getMovieById() as LiveData<MovieEntity>).observeForever(observer)
        verify(observer).onChanged(movieNull)
    }


    @Test
    fun shouldReturnNullIfIdTvNotFound(){
        viewModel.setTvShowId(dummyIdTvNotFound)
        val tv = MutableLiveData<MovieEntity>()
        tv.value = null
        whenever(movieRepository.getDetailTv(dummyIdTvNotFound)).thenReturn(tv)
        val tvNull = viewModel.getTvShowById().value
        assertNull(tvNull)
        verify(movieRepository).getDetailTv(dummyIdTvNotFound)

        (viewModel.getTvShowById() as LiveData<MovieEntity>).observeForever(observer)
        verify(observer).onChanged(tvNull)
    }

}
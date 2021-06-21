package com.dicodingsub.herryprasetyo.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.verify
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
class MovieViewModelTest{
    private  lateinit var viewModel: MovieViewModel

    @Mock
    private  lateinit var movieRepository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp(){
        viewModel = MovieViewModel(movieRepository)

    }

    @Test
    fun getMovies(){
        val dummyMovie = DataDummy.generateDummyMovies()
        val liveDataMovies = MutableLiveData<List<MovieEntity>>()
        liveDataMovies.value = dummyMovie

        Mockito.`when`(movieRepository.getMovies()).thenReturn(
            liveDataMovies
        )

        val movieEntity = viewModel.getMovies().value
        verify(movieRepository).getMovies()
        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)
        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}
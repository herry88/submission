package com.dicodingsub.herryprasetyo.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.PagedListUtil
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel


    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var dataSourceFactory: PopularMoviesDataSourceFactory

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>


    @Before
    fun setUp() {
        dataSourceFactory = Mockito.mock(PopularMoviesDataSourceFactory::class.java)
        viewModel = MovieViewModel(movieRepository, dataSourceFactory)
    }

    @Test
    fun getMovies() {
        val pagedMovies = MutableLiveData<PagedList<MovieEntity>>()
        pagedMovies.value = PagedListUtil.mockPagedList(DataDummy.generateDummyMovies())
        Mockito.`when`(movieRepository.getMovies(dataSourceFactory)).thenReturn(
            pagedMovies
        )

        val pagedList = viewModel.getMovies().value
//        verify<MovieRepository>(movieRepository).getMovies(dataSourceFactory)

        assertNotNull(pagedList)
        assertEquals(10, pagedList?.size)
        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(pagedMovies.value)

    }

}
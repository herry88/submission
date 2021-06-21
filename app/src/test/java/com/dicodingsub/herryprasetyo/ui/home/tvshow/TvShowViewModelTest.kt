package com.dicodingsub.herryprasetyo.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest{
    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp(){
        viewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShows(){
        val dummyTv = DataDummy.generateDummyTVShows()
        val liveDataTv = MutableLiveData<List<MovieEntity>>()
        liveDataTv.value = dummyTv

        Mockito.`when`(movieRepository.getTvShows()).thenReturn(
            liveDataTv
        )

        val movieEntity = viewModel.getTVShows().value
        verify<MovieRepository>(movieRepository).getTvShows()
        Assert.assertNotNull(movieEntity)
        Assert.assertEquals(10, movieEntity?.size)

        viewModel.getTVShows().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}
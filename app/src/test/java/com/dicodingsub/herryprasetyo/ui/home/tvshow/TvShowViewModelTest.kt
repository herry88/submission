package com.dicodingsub.herryprasetyo.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.PagedListUtil
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
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

@RunWith(MockitoJUnitRunner.Silent::class)
internal class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var dataSourceFactory: PopularTvDataSourceFactory

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<TvEntity>>


    @Before
    fun setUp() {
        dataSourceFactory = Mockito.mock(PopularTvDataSourceFactory::class.java)
        viewModel = TvShowViewModel(movieRepository, dataSourceFactory)
    }

    @Test
    fun getTvShows() {
        val pagedMovies = MutableLiveData<PagedList<TvEntity>>()
        pagedMovies.value = PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows())

        Mockito.`when`(movieRepository.getTvShows(dataSourceFactory)).thenReturn(
            pagedMovies
        )

        val pagedList = viewModel.getTVShows().value
        verify(movieRepository).getTvShows(dataSourceFactory)

        Assert.assertNotNull(pagedList)
        Assert.assertEquals(10, pagedList?.size)

        viewModel.getTVShows().observeForever(observer)
        verify(observer).onChanged(pagedMovies.value)
    }
}
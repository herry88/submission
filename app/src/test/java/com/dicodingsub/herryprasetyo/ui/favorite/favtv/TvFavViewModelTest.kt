package com.dicodingsub.herryprasetyo.ui.favorite.favtv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvFavViewModelTest {

    private lateinit var viewModel: TvFavViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var observer: Observer<PagedList<TvEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvFavViewModel(repository)
    }


    @Test
    fun getTvs() {
        val dummyTvs = pagedList
        `when`(dummyTvs.size).thenReturn(5)
        val mutableLiveDataTv = MutableLiveData<PagedList<TvEntity>>()
        mutableLiveDataTv.value = dummyTvs

        `when`(repository.getFavTvs()).thenReturn(mutableLiveDataTv)
        val tvEntities = viewModel.getTvs().value

        verify<MovieRepository>(repository).getFavTvs()
        assertNotNull(tvEntities)
        assertEquals(5, tvEntities?.size)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(dummyTvs)
    }


}
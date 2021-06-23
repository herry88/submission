package com.dicodingsub.herryprasetyo.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.util.DataDummy
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class DetailTvViewModelTest {
    private lateinit var viewModel: DetailTvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyTvShow = DataDummy.generateDummyTVShows()[0]
    private val tvShowId = dummyTvShow.id


    @Before
    fun setUp() {
        viewModel =
            DetailTvViewModel(movieRepository)
        viewModel.setTvShowId(tvShowId)
    }

    @Test
    fun getSelectedTvShow() {
        val liveDataMovie = MutableLiveData<TvEntity>()
        val tvEntity = dummyTvShow
        liveDataMovie.value = tvEntity
        Mockito.`when`(movieRepository.getDetailTv(tvShowId)).thenReturn(liveDataMovie)

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
        viewModel.tv.observeForever(observer as Observer<in TvEntity?>)
        Mockito.verify(observer).onChanged(tvEntity)

    }
}
package com.dicodingsub.herryprasetyo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.LocalDataSource
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.RemoteDataSource
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
import com.dicodingsub.herryprasetyo.util.AppExecutors
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MovieRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val repository = FakeMovieRepository(remote, local, appExecutors)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val movieId = DataDummy.generateDummyMoviesResponse().results[0].id
    private val detailMovieResponse = DataDummy.generateDummyDetailMovieResponse()

    private val tvId = DataDummy.generateDummyTvShowResponse().results[0].id
    private val detailTvResponse = DataDummy.generateDummyDetailTvShowResponse()

    private val dummyTvEntityId = DataDummy.generateDummyTvEntity().id
    private val dummyMovieEntityId = DataDummy.generateDummyMovieEntity().id


    @Test
    fun getPagedPopularMovies() {
        val dataSourceFactory = mock(PopularMoviesDataSourceFactory::class.java)
        val pagedMovies = MutableLiveData<PagedList<MovieEntity>>()
        pagedMovies.value = PagedListUtil.mockPagedList(DataDummy.generateDummyMovies())
        Mockito.`when`(remote.getPagedPopularMovies(dataSourceFactory)).thenReturn(pagedMovies)

        repository.getMovies(dataSourceFactory)
        Mockito.verify(remote).getPagedPopularMovies(dataSourceFactory)
        Assert.assertNotNull(pagedMovies)
        assertEquals(10, pagedMovies.value?.size)
    }


    @Test
    fun getDetailMovieById() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                .onDetailMovieReceived(detailMovieResponse)
            null
        }.`when`(remote).getDetailMovie(eq(movieId.toString()), any())

        val movieEntity = LiveDataTestUtil.getValue(repository.getDetailMovie(movieId.toString()))
        Mockito.verify(remote).getDetailMovie(eq(movieId.toString()), any())
        Assert.assertNotNull(movieEntity)
    }


    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        repository.getFavMovies()
        val movies = PagedListUtil.mockPagedList(DataDummy.generateDummyMovies())

        Mockito.verify(local).getAllMovie()
        Assert.assertNotNull(movies)
        assertEquals(10, movies.size.toLong())
    }

    @Test
    fun getFavMovieById() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovieEntity()
        Mockito.`when`(local.getMovieById(dummyMovieEntityId)).thenReturn(dummyEntity)

        val movieEntity = LiveDataTestUtil.getValue(repository.getFavMovieById(dummyMovieEntityId))
        Mockito.verify(local).getMovieById(dummyMovieEntityId)
        Assert.assertNotNull(movieEntity)
    }


    @Test
    fun getPagedPopularTv() {
        val dataSourceFactory = mock(PopularTvDataSourceFactory::class.java)
        val pagedTv = MutableLiveData<PagedList<TvEntity>>()
        pagedTv.value = PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows())
        Mockito.`when`(remote.getPagedPopularTv(dataSourceFactory)).thenReturn(pagedTv)

        repository.getTvShows(dataSourceFactory)
        Mockito.verify(remote).getPagedPopularTv(dataSourceFactory)
        Assert.assertNotNull(pagedTv)
        assertEquals(10, pagedTv.value?.size)
    }


    @Test
    fun getDetailTvById() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailTvCallback)
                .onDetailTvReceived(detailTvResponse)
            null
        }.`when`(remote).getDetailTv(eq(tvId.toString()), any())

        val movieEntity = LiveDataTestUtil.getValue(repository.getDetailTv(tvId.toString()))
        Mockito.verify(remote).getDetailTv(eq(tvId.toString()), any())
        Assert.assertNotNull(movieEntity)

    }


    @Test
    fun getFavoriteTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        Mockito.`when`(local.getAllTv()).thenReturn(dataSourceFactory)
        repository.getFavTvs()
        val tvs = PagedListUtil.mockPagedList(DataDummy.generateDummyTVShows())

        Mockito.verify(local).getAllTv()
        Assert.assertNotNull(tvs)
        assertEquals(10, tvs.size.toLong())
    }


    @Test
    fun getFavTvById() {
        val dummyEntity = MutableLiveData<TvEntity>()
        dummyEntity.value = DataDummy.generateDummyTvEntity()
        Mockito.`when`(local.getTvById(dummyTvEntityId)).thenReturn(dummyEntity)

        val tvEntity = LiveDataTestUtil.getValue(repository.getFavTvById(dummyTvEntityId))
        Mockito.verify(local).getTvById(dummyTvEntityId)
        Assert.assertNotNull(tvEntity)
    }


}
package com.dicodingsub.herryprasetyo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.RemoteDataSource
import com.dicodingsub.herryprasetyo.util.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class MovieRepositoryTest{
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val repository = FakeMoviesRepo(remote)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val moviesResponse = DataDummy.generateDummyMoviesResponse()
    private val movieId = DataDummy.generateDummyMoviesResponse().results[0].id
    private val detailMovieResponse = DataDummy.generateDummyDetailMovieResponse()
    private val tvShowResponse = DataDummy.generateDummyTvShowResponse()
    private val tvId = DataDummy.generateDummyTvShowResponse().results[0].id
    private val detailTvResponse = DataDummy.generateDummyDetailTvShowResponse()

    @Test
    fun getPopularMovies() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onMovieReceived(moviesResponse.results)
            null
        }.`when`(remote).getPopularMovie(any())

        val popularMovies = LiveDataTest.getValue(repository.getMovies())
        Mockito.verify(remote).getPopularMovie(any())
        Assert.assertNotNull(popularMovies)
    }


    @Test
    fun getDetailMovieById() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                .onDetailMovieReceived(detailMovieResponse)
            null
        }.`when`(remote).getDetailMovie(eq(movieId.toString()), any())

        val movieEntity = LiveDataTest.getValue(repository.getDetailMovie(movieId.toString()))
        Mockito.verify(remote).getDetailMovie(eq(movieId.toString()), any())
        Assert.assertNotNull(movieEntity)
    }


    @Test
    fun getPopularTv() {
        doAnswer { invocationOnMock ->
            (invocationOnMock.arguments[0] as RemoteDataSource.LoadTvShowsCallback)
                .onTvShowsReceived(tvShowResponse.results)
            null
        }.`when`(remote).getTvShows(any())

        val popularTV = LiveDataTest.getValue(repository.getTvShows())
        Mockito.verify(remote).getTvShows(any())
        Assert.assertNotNull(popularTV)
    }

    @Test
    fun getDetailTvById() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailTvCallback)
                .onDetailTvReceived(detailTvResponse)
            null
        }.`when`(remote).getDetailTv(eq(tvId.toString()), any())

        val movieEntity = LiveDataTest.getValue(repository.getDetailTv(tvId.toString()))
        Mockito.verify(remote).getDetailTv(eq(tvId.toString()), any())
        Assert.assertNotNull(movieEntity)

    }
}
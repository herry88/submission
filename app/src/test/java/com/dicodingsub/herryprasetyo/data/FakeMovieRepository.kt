package com.dicodingsub.herryprasetyo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.LocalDataSource
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.RemoteDataSource
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.util.AppExecutors

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {


    companion object {
        private const val LOCAL_PAGE_SIZE = 5
    }


    override fun getMovies(dataSourceFactory: PopularMoviesDataSourceFactory): LiveData<PagedList<MovieEntity>> =
        remoteDataSource.getPagedPopularMovies(dataSourceFactory)

    override fun getTvShows(dataSourceFactory: PopularTvDataSourceFactory): LiveData<PagedList<TvEntity>> =
        remoteDataSource.getPagedPopularTv(dataSourceFactory)


    override fun getDetailMovie(id: String): LiveData<MovieEntity?> {
        val detailResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovieCallback {
            override fun onDetailMovieReceived(data: DetailMovieResponse?) {
                data?.let { response ->
                    val genresList = response.genres.map { it.name }
                    val movieEntity = MovieEntity(
                        response.id.toString(),
                        response.poster_path,
                        response.title,
                        response.vote_average.toString(),
                        response.release_date,
                        genresList.joinToString(separator = ", "),
                        response.overview
                    )
                    detailResult.postValue(movieEntity)
                } ?: detailResult.postValue(null)

            }

        })
        return detailResult
    }

    override fun getDetailTv(id: String): LiveData<TvEntity?> {
        val detailResult = MutableLiveData<TvEntity>()
        remoteDataSource.getDetailTv(id, object : RemoteDataSource.LoadDetailTvCallback {
            override fun onDetailTvReceived(data: DetailTvResponse?) {
                data?.let { response ->
                    val genresList = response.genres.map { it.name }
                    val tvEntity = TvEntity(
                        response.id.toString(),
                        response.poster_path,
                        response.name,
                        response.vote_average.toString(),
                        response.first_air_date,
                        genresList.joinToString(separator = ", "),
                        response.overview
                    )
                    detailResult.postValue(tvEntity)
                } ?: detailResult.postValue(null)
            }

        })
        return detailResult
    }

    override fun getFavMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(LOCAL_PAGE_SIZE)
            .setPageSize(LOCAL_PAGE_SIZE)
            .build()
        return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
    }

    override fun getFavTvs(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(LOCAL_PAGE_SIZE)
            .setPageSize(LOCAL_PAGE_SIZE)
            .build()
        return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
    }


    override fun insertFavMovie(entity: MovieEntity) {
        appExecutors.diskIO().execute {
            localDataSource.insertMovies(entity)
        }
    }

    override fun deleteFavMovie(entity: MovieEntity) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavMovie(entity)
        }
    }


    override fun insertFavTv(entity: TvEntity) {
        appExecutors.diskIO().execute {
            localDataSource.insertTvs(entity)

        }
    }

    override fun deleteFavTv(entity: TvEntity) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavMovie(entity)

        }
    }

    override fun getFavMovieById(id: String): LiveData<MovieEntity> =
        localDataSource.getMovieById(id)

    override fun getFavTvById(id: String): LiveData<TvEntity> = localDataSource.getTvById(id)


}
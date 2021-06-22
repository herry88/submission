package com.dicodingsub.herryprasetyo.util

import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.Genres
import com.dicodingsub.herryprasetyo.data.source.remote.response.movie.PopularMoviesResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.movie.ResultMovie
import com.dicodingsub.herryprasetyo.data.source.remote.response.tvshow.PopularTvResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.tvshow.ResultsTv


object DataDummy {

    fun generateDummyMovies(): ArrayList<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                "1",
                "R.drawable.poster_alita",
                "Alita : Battle Angle",
                "70",
                "Feb 02, 2019",
                "2h 2m",
                "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past."
            )
        )

        movies.add(
            MovieEntity(
                "2",
                "R.drawable.poster_aquaman",
                "Aquaman",
                "68",
                "Des 21, 2018",
                "2h 24m",
                "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne."
            )
        )

        movies.add(
            MovieEntity(
                "3",
                "R.drawable.poster_t34",
                "T-34",
                "66",
                "Jan 01, 2019",
                "2h 19m",
                "In 1944, a courageous group of Russian soldiers managed to escape from German captivity in a half-destroyed legendary T-34 tank. Those were the times of unforgettable bravery, fierce fighting, unbreakable love, and legendary miracles."
            )
        )

        movies.add(
            MovieEntity(
                "4",
                "R.drawable.poster_cold_persuit",
                "Cold Pursuit",
                "55",
                "feb 08, 2019",
                "1h 59m",
                "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution."
            )
        )

        movies.add(
            MovieEntity(
                "5",
                "R.drawable.poster_spiderman",
                "Spiderman: Into the Spider Verse",
                "84",
                "Des 12, 2018",
                "1h 57m",
                "Peter Parker is the only Spider-Man, except there are other Spider-Men out there: in the multiverse known as 'The Spider-Verse'. After down-on-his-luck teenager Miles Morales gets bitten by a radioactive spider, he sees Spider-Man stop the Kingpin's dimension-crossing super-collider; only to be killed by the Kingpin afterwards. Seeing his hero die before him, Miles vows to avenge the wall-crawler and destroy the collider once and for all. Unknown to Morales, there's another Peter Parker from another dimension."

            )
        )

        movies.add(
            MovieEntity(
                "6",
                "R.drawable.poster_master_z",
                "Master Z: The Ip Man Legacy",
                "65",
                "Des 20, 2018",
                "1h 47m",
                "While keeping a low profile after his defeat by Ip Man, Cheung Tin Chi gets into trouble after getting in a fight with a powerful foreigner."
            )
        )

        movies.add(
            MovieEntity(
                "7",
                "R.drawable.poster_creed",
                "Creed II",
                "7.1",
                "Nov 21, 2018",
                "2h 10m",
                "Under the tutelage of Rocky Balboa, newly crowned heavyweight champion Adonis Creed faces off against Viktor Drago, the son of Ivan Drago."
            )
        )

        movies.add(
            MovieEntity(
                "8",
                "R.drawable.poster_how_to_train",
                "How to Train Your Dragon: The Hidden World",
                "77",
                "Feb 22, 2019",
                "1h 44m",
                "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind."
            )
        )

        movies.add(
            MovieEntity(
                "9",
                "R.drawable.poster_infinity_war",
                "Avengers: Infinity War",
                "83",
                "Apr 27, 2018",
                "2h 22m",
                "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain."
            )
        )

        movies.add(
            MovieEntity(
                "10",
                "R.drawable.poster_ralph",
                "Ralph Breaks the Internet",
                "72",
                "Okt 21, 2018",
                "1h 52m",
                "Video game bad guy Ralph and fellow misfit Vanellope von Schweetz must risk it all by traveling to the World Wide Web in search of a replacement part to save Vanellope's video game, Sugar Rush. In way over their heads, Ralph and Vanellope rely on the citizens of the internet — the netizens — to help navigate their way, including an entrepreneur named Yesss, who is the head algorithm and the heart and soul of trend-making site BuzzzTube."
            )
        )

        return movies
    }


    fun generateDummyTVShows(): ArrayList<TvEntity> {
        val tvShowList = ArrayList<TvEntity>()

        tvShowList.add(
            TvEntity(
                "1",
                "R.drawable.poster_arrow",
                "Arrow",
                "62",
                "Okt 10, 2012",
                "44m",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow."

            )
        )

        tvShowList.add(
            TvEntity(
                "2",
                "R.drawable.poster_iron_fist",
                "Iron Fist ",
                "65",
                "Nov 12, 2012",
                "55m",
                "Danny Rand returns to New York City after being missing for years, trying to reconnect with his past and his family legacy. He fights against the criminal element corrupting his world around him with his incredible kung-fu mastery and ability to summon the awesome power of the fiery Iron Fist."
            )
        )

        tvShowList.add(
            TvEntity(
                "3",
                "R.drawable.poster_dragon_ball",
                "Dragon Ball",
                "77",
                "Sept 9, 1995",
                "44m",
                "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish."

            )
        )

        tvShowList.add(
            TvEntity(
                "4",
                "R.drawable.poster_naruto_shipudden",
                "Naruto: Shippuden",
                "86",
                "Okt 28, 2009",
                "24m",
                "Naruto Uzumaki, is a loud, hyperactive, adolescent ninja who constantly searches for approval and recognition, as well as to become Hokage, who is acknowledged as the leader and strongest of all ninja in the village."

            )
        )

        tvShowList.add(
            TvEntity(
                "5",
                "R.drawable.poster_ncis",
                "NCIS",
                "78",
                "Nov 23, 2003",
                "1h",
                "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues."
            )
        )

        tvShowList.add(
            TvEntity(
                "6",
                "R.drawable.poster_flash",
                "The Flash",
                "78",
                "Okt 07, 2014",
                "44m",
                "Special Agent Leroy Jethro Gibbs is the leader of a team of special agents belonging to the NCIS (Naval Criminal Investigative Service) Major Case Response Team. Gibbs, a former Marine, is a tough investigator and a highly skilled interrogator who relies on his gut instinct as much as evidence. Gibbs' second in command is Senior Field Agent Tony DiNozzo, a womanizing, movie-quoting former Baltimore Homicide Detective, who despite being the class clown always gets the job done. The team also consists of probationary field agent Eleanor Bishop, a former NSA agent, as well as Junior Field Agent Timothy McGee, a computer-savvy agent often mocked by DiNozzo. Assisting them are Abby Sciuto, the energetic-but-Goth lab tech who is like a daughter to Gibbs, and Dr. Donald Mallard, nicknamed Ducky, the eccentric medical examiner full of unusual stories. This team of elite agents, based in Washington, D.C., solve criminal cases involving Marine and Navy personnel and their families, sometimes traveling the United States - or the world - to do it.,"
            )
        )

        tvShowList.add(
            TvEntity(
                "7",
                "R.drawable.poster_gotham",
                "Gotham",
                "43m",
                "Sept 22, 2014",
                "72",
                "Everyone knows the name Commissioner Gordon. He is one of the crime world's greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon's story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world's most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?"
            )
        )

        tvShowList.add(
            TvEntity(
                "8",
                "R.drawable.poster_shameless",
                "Shameless",
                "86",
                "Jan 9, 2011",
                "46m",
                "Watch the Irish American family the Gallaghers dealing with their alcoholic father Frank. Fiona, the eldest daughter, takes the role of the parent to her five brothers and sisters. Lip, Ian, Debbie, Carl, and Liam deal with life on the South Side of Chicago. Fiona balances her sex life and raising her siblings. Every episode is another crazy situation that one or more of the Gallagher six get into. Watch them grow and learn how to make their way in life with what little they have."
            )
        )

        tvShowList.add(
            TvEntity(
                "9",
                "R.drawable.poster_hanna",
                "Hanna",
                "70",
                "March 29, 2019",
                "50m",
                "This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film."
            )
        )

        tvShowList.add(
            TvEntity(
                "10",
                "R.drawable.poster_supernatural",
                "Supernatural",
                "84",
                "Sept 13, 2005",
                "44m",
                "The story revolves around 2 brothers, Sam and Dean Winchester as they follow their father's footsteps, hunting down evil supernatural creatures such as monsters, demons and even fallen gods while trying to save innocent people along the way. Continuing the \"family business\" after their father's death, the brothers soon discovered that the \"hunt\" doesn't just involve slashing and hacking monsters and demons, but also dealing with more powerful creatures such as angels, reapers and even Death."
            )
        )

        return tvShowList
    }


    fun generateDummyMoviesResponse(): PopularMoviesResponse {
        val dataMovie = ArrayList<ResultMovie>()
        dataMovie.add(
            ResultMovie(
                "Mulan",
                337401,
                "path poster 1"
            )
        )
        dataMovie.add(
            ResultMovie(
                "Film 2",
                2,
                "path poster 2"
            )
        )
        return PopularMoviesResponse(dataMovie)
    }

    fun generateDummyDetailMovieResponse(): DetailMovieResponse {
        return DetailMovieResponse(
            true,
            arrayListOf(Genres(1, "Perang")),
            337401,
            "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
            "path poster 1",
            "1997-25-01",
            "Mulan",
            9.7

        )
    }

    fun generateDummyTvShowResponse(): PopularTvResponse {
        val dataTv = ArrayList<ResultsTv>()
        dataTv.add(
            ResultsTv(
                "Tvshow 1",
                1,
                "path Tvshow 1"
            )
        )
        dataTv.add(
            ResultsTv(
                "Tvshow 2",
                2,
                "path Tvshow 2"
            )
        )
        return PopularTvResponse(dataTv)
    }


    fun generateDummyDetailTvShowResponse(): DetailTvResponse {
        return DetailTvResponse(
            true,
            arrayListOf(Genres(1, "Perang")),
            1,
            "overiviewnya",
            "path poster 1",
            "1997-25-01",
            "Film 1",
            9.7

        )
    }

    fun generateDummyTvEntity(): TvEntity {
        return TvEntity(
            "1",
            "path poster 1",
            "overiviewnya",
            "7.5",
            "1997-25-01",
            "Perang",
            "Fil peram"

        )
    }

    fun generateDummyMovieEntity(): MovieEntity {
        return MovieEntity(
            "1",
            "path poster 1",
            "overiviewnya",
            "7.5",
            "1997-25-01",
            "Perang",
            "Fil peram"

        )
    }
}
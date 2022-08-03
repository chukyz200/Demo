package com.example.movieApp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movieApp.R
import com.example.movieApp.core.Resource
import com.example.movieApp.data.model.Movie
import com.example.movieApp.databinding.FragmentMovieBinding
import com.example.movieApp.presentation.MovieViewModel
import com.example.movieApp.ui.main.adapters.MoviesAdapter
import com.example.movieApp.ui.main.adapters.concat.PopularConcatAdapter
import com.example.movieApp.ui.main.adapters.concat.TopRatedConcatAdapter
import com.example.movieApp.ui.main.adapters.concat.UpcomingConcatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), MoviesAdapter.OnMovieClickListener {

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var binding: FragmentMovieBinding


    private val viewModel by viewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.movies.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MoviesAdapter(
                                    it.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MoviesAdapter(
                                    it.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MoviesAdapter(
                                    it.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )

                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: $it.exception ")
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}
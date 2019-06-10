package com.example.serietracking.multicall

import retrofit2.Call

class Callable<T, V> (val call: Call<T>, val mapper: (T) -> V)
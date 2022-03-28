package com.example.mobilliumchallenge.model.entities.upcoming

import com.example.mobilliumchallenge.model.entities.common.Dates
import com.example.mobilliumchallenge.model.entities.common.Result

data class UpcomingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
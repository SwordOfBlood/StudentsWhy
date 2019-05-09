package com.example.studentswhy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RuzAPI
{
    @GET("personlessons?")
    Call<List<Lesson>> getDataForStudents(@Query("email") String email,
                                @Query("fromdate") String fromDate,
                                @Query("todate") String toDate);
    @GET("personlessons?")
    Call<List<Lesson>> getDataForTeachers(@Query("email") String email,
                                          @Query("fromdate") String fromDate,
                                          @Query("todate") String toDate,
                                          @Query("receiverType") String receiverType);
}

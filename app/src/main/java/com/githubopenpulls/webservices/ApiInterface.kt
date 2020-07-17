package com.githubopenpulls.webservices

import com.githubopenpulls.models.PullInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("{owner_name}/{repo_name}/pulls")
    fun getOpenPulls(
        @Path("owner_name") ownerName: String,
        @Path("repo_name") repoName: String,
        @Query("state") state: String
    ): Observable<ArrayList<PullInfo>>
}
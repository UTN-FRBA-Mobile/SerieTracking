package com.example.serietracking

import android.os.Parcel
import android.os.Parcelable

class RichEpisode() : Parcelable {
    var tv: TVCompleteModel? = null
    var season: SeasonModel? = null
    var episode: Episode? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RichEpisode> {
        override fun createFromParcel(parcel: Parcel): RichEpisode {
            return RichEpisode(parcel)
        }

        override fun newArray(size: Int): Array<RichEpisode?> {
            return arrayOfNulls(size)
        }
    }
}
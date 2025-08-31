package com.example.rutascda.v2.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enigma implements Parcelable {
    private String name;
    private String description;
    private String answer;

    protected Enigma(Parcel in) {
        name = in.readString();
        description = in.readString();
        answer = in.readString();
    }

    public static final Creator<Enigma> CREATOR = new Creator<Enigma>() {
        @Override
        public Enigma createFromParcel(Parcel in) {
            return new Enigma(in);
        }

        @Override
        public Enigma[] newArray(int size) {
            return new Enigma[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(answer);
    }
}

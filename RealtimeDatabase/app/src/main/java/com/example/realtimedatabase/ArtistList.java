package com.example.realtimedatabase;

import android.app.Activity;
import android.widget.ArrayAdapter;


import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity content;
    private List<Artist> artistList;

    public ArtistList(Activity content, List<Artist> artistList) {
        super(content, R.layout.item_list, artistList);
        this.content = content;
        this.artistList = artistList;
    }
}

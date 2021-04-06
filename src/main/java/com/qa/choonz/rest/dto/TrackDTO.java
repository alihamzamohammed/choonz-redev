package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.PlaylistTracks;

public class TrackDTO {

    private long id;
    private String name;
    private Album album;
    private List<PlaylistTracks> playlistTracks;
    private int duration;
    private String lyrics;

    public TrackDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	public TrackDTO(long id, String name, Album album,  List<PlaylistTracks> playlistTracks, int duration, String lyrics) {
		super();
		this.id = id;
		this.name = name;
		this.album = album;
		this.playlistTracks = playlistTracks;
		this.duration = duration;
		this.lyrics = lyrics;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Playlist> getPlaylistTracks() {
		return playlistTracks.stream().map(playlistTrack -> playlistTrack.getPlaylist()).collect(Collectors.toList());
	}

	public void setPlaylistTracks(List<PlaylistTracks> playlistTracks) {
		this.playlistTracks = playlistTracks;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}


	@Override
	public int hashCode() {
		return Objects.hash(album, duration, id, lyrics, name, playlistTracks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TrackDTO))
			return false;
		TrackDTO other = (TrackDTO) obj;
		return Objects.equals(album, other.album) && duration == other.duration && id == other.id
				&& Objects.equals(lyrics, other.lyrics) && Objects.equals(name, other.name)
				&& Objects.equals(playlistTracks, other.playlistTracks);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackDTO [id=").append(id).append(", name=").append(name).append(", album=").append(album)
				.append(", playlistTracks=").append(playlistTracks).append(", duration=").append(duration)
				.append(", lyrics=").append(lyrics).append("]");
		return builder.toString();
	}



	
}


package com.spartaglobal.musicapiproject.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumId{

	@JsonProperty("artistId")
	private ArtistId artistId;

	@JsonProperty("id")
	private int id;

	@JsonProperty("title")
	private String title;

	public void setArtistId(ArtistId artistId){
		this.artistId = artistId;
	}

	public ArtistId getArtistId(){
		return artistId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}
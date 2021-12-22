package com.spartaglobal.musicapiproject.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackPOJO{

	public TrackPOJO() {
	}

	@JsonProperty("genreId")
	private GenreId genreId;

	@JsonProperty("unitPrice")
	private double unitPrice;

	@JsonProperty("milliseconds")
	private int milliseconds;

	@JsonProperty("composer")
	private Object composer;

	@JsonProperty("bytes")
	private int bytes;

	@JsonProperty("name")
	private String name;

	@JsonProperty("mediaTypeId")
	private MediaTypeId mediaTypeId;

	@JsonProperty("albumId")
	private AlbumId albumId;

	@JsonProperty("id")
	private int id;

	public void setGenreId(GenreId genreId){
		this.genreId = genreId;
	}

	public GenreId getGenreId(){
		return genreId;
	}

	public void setUnitPrice(double unitPrice){
		this.unitPrice = unitPrice;
	}

	public double getUnitPrice(){
		return unitPrice;
	}

	public void setMilliseconds(int milliseconds){
		this.milliseconds = milliseconds;
	}

	public int getMilliseconds(){
		return milliseconds;
	}

	public void setComposer(Object composer){
		this.composer = composer;
	}

	public Object getComposer(){
		return composer;
	}

	public void setBytes(int bytes){
		this.bytes = bytes;
	}

	public int getBytes(){
		return bytes;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMediaTypeId(MediaTypeId mediaTypeId){
		this.mediaTypeId = mediaTypeId;
	}

	public MediaTypeId getMediaTypeId(){
		return mediaTypeId;
	}

	public void setAlbumId(AlbumId albumId){
		this.albumId = albumId;
	}

	public AlbumId getAlbumId(){
		return albumId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}
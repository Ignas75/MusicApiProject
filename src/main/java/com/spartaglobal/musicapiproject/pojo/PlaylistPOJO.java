package com.spartaglobal.musicapiproject.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaylistPOJO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}
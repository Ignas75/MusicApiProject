package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PopularityByCountryController {

    @Autowired
    AlbumPopularityByCountryRepository albumPopularityByCountryRepository;
    @Autowired
    ArtistPopularityByCountryRepository artistPopularityByCountryRepository;
    @Autowired
    GenrePopularityByCountryRepository genrePopularityByCountryRepository;
    @Autowired
    PlaylistPopularityByCountryRepository playlistPopularityByCountryRepository;
    @Autowired
    TrackPopularityByCountryRepository trackPopularityByCountryRepository;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/chinook/popularitybycountry/albums")
    public ResponseEntity<?> getAlbumPopularityByBillingCountry(@RequestParam String country, @RequestParam int numRecords, @RequestParam String sortType, @RequestHeader("Authorization") String authToken){
        if (!authorizationService.isAuthorizedForAction(authToken.split(" ")[1], "/chinook/popularitybycountry/albums")) {
            if (sortType.equals("DESC")) {
                List<AlbumPopularityByCountry> albumPopularityByCountries = albumPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(albumPopularityByCountries, HttpStatus.OK);
            } else if (sortType.equals("ASC")) {
                List<AlbumPopularityByCountry> albumPopularityByCountries = albumPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(albumPopularityByCountries, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>("You are not authorized for this page with your current access level", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/chinook/popularitybycountry/artists")
    public ResponseEntity<?> getArtistPopularityByBillingCountry(@RequestParam String country, @RequestParam int numRecords, @RequestParam String sortType, @RequestHeader("Authorization") String authToken){
        if (!authorizationService.isAuthorizedForAction(authToken.split(" ")[1], "/chinook/popularitybycountry/artists")) {
            if (sortType.equals("DESC")) {
                List<ArtistPopularityByCountry> artistPopularityByCountries = artistPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(artistPopularityByCountries, HttpStatus.OK);
            } else if (sortType.equals("ASC")) {
                List<ArtistPopularityByCountry> artistPopularityByCountries = artistPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(artistPopularityByCountries, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  else return new ResponseEntity<>("You are not authorized for this page with your current access level", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/chinook/popularitybycountry/genres")
    public ResponseEntity<?> getGenrePopularityByBillingCountry(@RequestParam String country, @RequestParam int numRecords, @RequestParam String sortType, @RequestHeader("Authorization") String authToken){
        if (!authorizationService.isAuthorizedForAction(authToken.split(" ")[1], "/chinook/popularitybycountry/genres")) {
            if (sortType.equals("DESC")) {
                List<GenrePopularityByCountry> genrePopularityByCountries = genrePopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(genrePopularityByCountries, HttpStatus.OK);
            } else if (sortType.equals("ASC")) {
                List<GenrePopularityByCountry> genrePopularityByCountries = genrePopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(genrePopularityByCountries, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  else return new ResponseEntity<>("You are not authorized for this page with your current access level", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/chinook/popularitybycountry/playlists")
    public ResponseEntity<?> getPlaylistPopularityByBillingCountry(@RequestParam String country, @RequestParam int numRecords, @RequestParam String sortType, @RequestHeader("Authorization") String authToken){
        if (!authorizationService.isAuthorizedForAction(authToken.split(" ")[1], "/chinook/popularitybycountry/playlists")) {
            if (sortType.equals("DESC")) {
                List<PlaylistPopularityByCountry> playlistPopularityByCountries = playlistPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(playlistPopularityByCountries, HttpStatus.OK);
            } else if (sortType.equals("ASC")) {
                List<PlaylistPopularityByCountry> playlistPopularityByCountries = playlistPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(playlistPopularityByCountries, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  else return new ResponseEntity<>("You are not authorized for this page with your current access level", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/chinook/popularitybycountry/tracks")
    public ResponseEntity<?> getTrackPopularityByBillingCountry(@RequestParam String country, @RequestParam int numRecords, @RequestParam String sortType, @RequestHeader("Authorization") String authToken){
        if (!authorizationService.isAuthorizedForAction(authToken.split(" ")[1], "/chinook/popularitybycountry/tracks")) {
            if (sortType.equals("DESC")) {
                List<TrackPopularityByCountry> trackPopularityByCountries = trackPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(trackPopularityByCountries, HttpStatus.OK);
            } else if (sortType.equals("ASC")) {
                List<TrackPopularityByCountry> trackPopularityByCountries = trackPopularityByCountryRepository.findAllByBillingCountry(country, Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, numRecords);
                return new ResponseEntity<>(trackPopularityByCountries, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  else return new ResponseEntity<>("You are not authorized for this page with your current access level", HttpStatus.UNAUTHORIZED);
    }
}

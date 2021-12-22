package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class TrackController {
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthorizationService as;
    @Autowired
    private CustomerController cc;
    @Autowired
    private InvoiceService is;
    
    @GetMapping("/chinook/track/buy")
    public ResponseEntity buyTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/track/buy")){
            return new ResponseEntity<>("Not Customer", HttpStatus.UNAUTHORIZED);
        }
        String customerEmail;
        try {
            customerEmail = tokenRepository.getByAuthToken(token).getEmail();
        }catch (NullPointerException e){
            return new ResponseEntity<>("Token Not Valid",HttpStatus.FORBIDDEN);
        }
        // Authorized to carry out purchase
        if(!as.isAuthorizedForAction(token,"/chinook/track/buy")){
            return new ResponseEntity<>("Not Customer", HttpStatus.UNAUTHORIZED);
        }
        // Find track
        List<Track> t  = trackRepository.findById(id).stream().toList();
        if (t.isEmpty()){
            return new ResponseEntity<>("Track does not exist", HttpStatus.NO_CONTENT);
        }
        // Find customer
        Customer c = customerRepository.findAll().stream().filter(s-> Objects.equals(s.getEmail(), customerEmail)).toList().get(0);
        if(cc.getCustomerTracks(c.getId()).equals(t)){
            return new ResponseEntity<>("Customer already owns the track", HttpStatus.OK);
        }
        // Create invoice
        is.createInvoice(t,c);
        return new ResponseEntity<>("Invoice(s) created", HttpStatus.OK);
    }

    @PostMapping("/chinook/track/create")
    public ResponseEntity createTrack(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Track newTrack){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/track/create")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        trackRepository.save(newTrack);
        return new ResponseEntity(newTrack, HttpStatus.OK);
    }

    @GetMapping("chinook/track/read")
    public ResponseEntity readTrack(@RequestParam Integer id){
        Optional<Track> track = trackRepository.findById(id);
        if (track.isPresent()) {
            return new ResponseEntity(track,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/chinook/track/update")
    public ResponseEntity updateTrack(@RequestBody Track newState, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/track/update")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Track> oldState = trackRepository.findById(newState.getId());
        if(oldState.isEmpty()) return null;
        trackRepository.save(newState);
        return new ResponseEntity(newState, HttpStatus.OK);
    }

    @DeleteMapping(value = "/chinook/track/delete")
    public ResponseEntity deleteTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/track/delete")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        trackRepository.delete(trackRepository.getById(id));
        return new ResponseEntity("Track deleted", HttpStatus.OK);
    }
    public void deleteTrack(Integer id){
        trackRepository.delete(trackRepository.getById(id));
    }
}

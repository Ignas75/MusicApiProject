package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class AdminAccess {

    @Autowired
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private GenreRepository genreRepository;
    private InvoicelineRepository invoicelineRepository;
    private InvoiceRepository invoiceRepository;
    private MediatypeRepository mediatypeRepository;
    private PlaylisttrackRepository playlisttrackRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private TrackRepository trackRepository;

    @PostMapping(value="/Chinook/new-album")
    public Album createAlbum(@Valid @RequestBody Album album1){
        return albumRepository.save(album1);
    }

    @PostMapping(value="/Chinook/new-artist")
    public Artist createArtist(@Valid @RequestBody Artist artist){
        return artistRepository.save(artist);
    }

    @PostMapping(value="/Chinook/new-customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @PostMapping(value="/Chinook/new-employee")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PostMapping(value="/Chinook/new-genre")
    public Genre createGenre(@Valid @RequestBody Genre genre1){
        return genreRepository.save(genre1);
    }

    @PostMapping(value="/Chinook/new-invoice-line")
    public Invoiceline createInvoiceLine(@Valid @RequestBody Invoiceline invoiceline){
        return invoicelineRepository.save(invoiceline);
    }

    @PostMapping(value="/Chinook/new-invoice")
    public Invoice createInvoice(@Valid @RequestBody Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    @PostMapping(value="/Chinook/new-mediatype")
    public Mediatype createMediatype(@Valid @RequestBody Mediatype mediatype){
        return mediatypeRepository.save(mediatype);
    }

    @PostMapping(value="/Chinook/new-playlist-track")
    public Playlisttrack createPlaylist(@Valid @RequestBody Playlisttrack playlisttrack){
        return playlisttrackRepository.save(playlisttrack);
    }

    @PostMapping(value="/Chinook/new-role")
    public Role createRole(@Valid @RequestBody Role role){
        return roleRepository.save(role);
    }

    @PostMapping(value="/Chinook/new-token")
    public Token createToken(@Valid @RequestBody Token token){
        return tokenRepository.save(token);
    }

    @PostMapping(value="/Chinook/new-track")
    public Track createTrack(@Valid @RequestBody Track track1){
        return trackRepository.save(track1);
    }

   /* @DeleteMapping(value="/Chinook/del-album")
    public Album delAlbum(@PathVariable )
    */

}

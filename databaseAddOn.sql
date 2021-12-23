USE Chinook;
DROP TABLE IF EXISTS EndpointPermissions;
DROP TABLE IF EXISTS Tokens;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS ArchiveInvoices;
DROP VIEW IF EXISTS TrackPopularityByCountry;
DROP VIEW IF EXISTS AlbumPopularityByCountry;
DROP VIEW IF EXISTS GenrePopularityByCountry;
DROP VIEW IF EXISTS PlaylistPopularityByCountry;
DROP VIEW IF EXISTS ArtistPopularityByCountry;
DROP VIEW IF EXISTS GlobalAlbumPopularity;
DROP VIEW IF EXISTS GlobalArtistPopularity;
DROP VIEW IF EXISTS GlobalPlaylistPopularity;
DROP VIEW IF EXISTS GlobalGenrePopularity;
DROP VIEW IF EXISTS GlobalTrackPopularity;
DROP TABLE IF EXISTS ArchiveInvoices;


CREATE EVENT IF NOT EXISTS Chinook.autoDelTokens
    ON SCHEDULE
        EVERY 1 DAY
    COMMENT 'Token has expired'
    DO
    DELETE
    from Chinook.Tokens
    WHERE timestampdiff(DAY, now(), dateCreated) > 7;

CREATE TABLE ArchiveInvoices
(
    ArchiveID    INT auto_increment NOT NULL Primary KEY,
    firstName    VARCHAR(50)        NOT NULL,
    lastName     VARCHAR(50)        NOT NULL,
    EmailAddress VARCHAR(50)        NOT NULL,
    Address      VARCHAR(255)       NOT NULL,
    PostalCode   VARCHAR(10)        NOT NULL,
    InvoiceDate  DATE               NOT NULL,
    Total        DECIMAL(10, 2)     NOT NULL
);

CREATE TABLE EndpointPermissions
(
    rowID           INTEGER AUTO_INCREMENT PRIMARY KEY,
    url             NVARCHAR(255) NOT NULL,
    isForCustomer   BIT,
    isForSalesStaff BIT,
    isForAdmins     BIT
);

CREATE table Roles
(
    roleID   INT NOT NULL,
    roleDesc VARCHAR(50),
    CONSTRAINT PK_Roles PRIMARY KEY (roleID)
);

CREATE table Tokens
(
    tokenID     INT          NOT NULL auto_increment,
    authToken   VARCHAR(255) NOT NULL,
    email       VARCHAR(50)  NOT NULL,
    roleID      INT          NOT NULL,
    dateCreated DATE         NOT NULL,
    CONSTRAINT PK_Token PRIMARY KEY (tokenID),
    FOREIGN KEY (roleID) REFERENCES Roles (roleID)
);

INSERT INTO EndpointPermissions(url, isForCustomer, isForSalesStaff, isForAdmins)
VALUES ("/chinook/album/delete", 0, 0, 1),
       ("/chinook/album/create", 0, 0, 1),
       ("/chinook/album/update", 0, 0, 1),
       ("/chinook/album/buy",1,0,0),
       ("/chinook/artist/create", 0, 0, 1),
       ("/chinook/artist/update", 0, 0, 1),
       ("/chinook/artist/delete", 0, 0, 1),
       ("/chinook/discontinuedtrack", 0 ,0,1),
       ("/chinook/discontinuedtrack/delete", 0,0,1),
       ("/chinook/discontinuedtrack/insert", 0,0,1),
       ("/chinook/invoice/delete", 0,1, 1),
       ("/chinook/popularitybycountry/albums",0,1,1),
       ("/chinook/popularitybycountry/artists",0,1,1),
       ("/chinook/popularitybycountry/genres",0,1,1),
       ("/chinook/popularitybycountry/playlists",0,1,1),
       ("/chinook/popularitybycountry/tracks",0,1,1),
       ("/chinook/playlist/delete", 0, 0, 1),
       ("/chinook/playlist/add", 0, 0, 1),
       ("/chinook/playlist/remove", 0, 0, 1),
       ("/chinook/playlist/create", 0, 0, 1),
       ("/chinook/playlist/buy", 1, 0, 0),
       ("/chinook/track/buy", 1, 0, 0),
       ("/chinook/track/create", 0, 0, 1),
       ("/chinook/track/update", 0, 0, 1),
       ("/chinook/track/delete", 0, 0, 1),
       ("/chinook/customer/create", 0, 1, 1),
       ("/chinook/customer/update", 0, 1, 1),
       ("/chinook/customer/delete", 0, 1, 1);



INSERT INTO Roles
VALUES (1, 'Admin'),
       (2, 'Sales'),
       (3, 'Customers');

CREATE VIEW GlobalTrackPopularity AS
SELECT t.Name, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
GROUP BY t.Name;

CREATE VIEW GlobalAlbumPopularity AS
SELECT a.Title, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Album a
              ON t.AlbumId = a.AlbumId
GROUP BY a.Title;

CREATE VIEW GlobalGenrePopularity AS
SELECT g.Name, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Genre g
              ON t.GenreId = g.GenreId
GROUP BY g.Name;

CREATE VIEW GlobalArtistPopularity AS
SELECT ar.Name, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Album a
              ON t.AlbumId = a.AlbumId
         JOIN Artist ar
              ON ar.ArtistId = a.ArtistId
GROUP BY ar.Name;

CREATE VIEW GlobalPlaylistPopularity AS
SELECT p.Name, COUNT(il.TrackID) as "Popularity"
FROM Track t
         JOIN PlaylistTrack pt
              ON t.TrackId = pt.TrackId
         JOIN Playlist p
              ON p.PlaylistID = pt.playlistID
         JOIN InvoiceLine il
              ON t.TrackId = il.TrackID
GROUP BY p.Name;

CREATE VIEW TrackPopularityByCountry
AS
SELECT t.Name, i.BillingCountry, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Invoice i
              ON il.InvoiceID = i.InvoiceID
GROUP BY t.Name, i.BillingCountry;

CREATE VIEW AlbumPopularityByCountry
AS
SELECT a.Title, i.BillingCountry, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Album a
              ON t.AlbumId = a.AlbumId
         JOIN Invoice i
              ON il.InvoiceID = i.InvoiceID
GROUP BY a.Title, i.BillingCountry;

CREATE VIEW GenrePopularityByCountry
AS
SELECT g.Name, i.BillingCountry, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Genre g
              ON t.GenreId = g.GenreId
         JOIN Invoice i
              ON il.InvoiceID = i.InvoiceID
GROUP BY g.Name, i.BillingCountry;

CREATE VIEW ArtistPopularityByCountry
AS
SELECT ar.Name, i.BillingCountry, COUNT(il.TrackID) as "Popularity"
FROM InvoiceLine il
         JOIN Track t
              ON il.TrackId = t.trackId
         JOIN Album a
              ON t.AlbumId = a.AlbumId
         JOIN Artist ar
              ON ar.ArtistId = a.ArtistId
         JOIN Invoice i
              ON il.InvoiceID = i.InvoiceID
GROUP BY ar.Name, i.BillingCountry;

CREATE VIEW PlaylistPopularityByCountry
AS
SELECT p.Name, i.BillingCountry, COUNT(il.TrackID) as "Popularity"
FROM Track t
         JOIN PlaylistTrack pt
              ON t.TrackId = pt.TrackId
         JOIN Playlist p
              ON p.PlaylistID = pt.playlistID
         JOIN InvoiceLine il
              ON t.TrackId = il.TrackID
         JOIN Invoice i
              ON il.InvoiceID = i.InvoiceID
GROUP BY p.Name, i.BillingCountry;
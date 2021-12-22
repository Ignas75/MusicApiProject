# Introduction

This project is for the creation of a web api which can be used by apps to interface with the database used by a 
related web app. 

## Initialisation 
This is for setting up the hosting of the api. 
Provided you have access to the initial database there are tables to be created by running:
databaseAddOn.sql found in the root folder of the project 


## Interfacing with API

### Authentication
Throughout the api, there will be required headers called "Authorization" which are expected to receive a token to 
authorise the api method being called. If the api method does not specify that it requires the heading then it is 
intended to not require it and can be called without a token. 


#### Logging in
When a user (whether employee or customer) logs in and is locally authenticated they need a token to be created. 

##### GET New Token Request

**API:** generateNewAuthToken

**API method:** /chinook/token/create

**Required Parameter:** String emailAddress

**Returns:** response entity with a string containing the confirmation message upon success

##### DELETE Token

If a user wants to effectively log out, this is the process for deleting their current token. 

**API:** clearExistingAuthToken

**API method:** /chinook/token/create

**Required Parameter:** String emailAddress

**Returns :** response entity with a string containing the confirmation/failure message


### Tracks

### Albums

### Artists

### DiscontinuedTracks 

### Invoices

### Playlists

### Popularity searches

### UserContentSearch

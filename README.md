# Introduction

This project is for the creation of a web api which can be used by apps to interface with the database used by a 
related web app. 

## Initialisation 
This is for setting up the hosting of the api. 
Provided you have access to the initial database there are tables to be created by running:
databaseAddOn.sql found in the root folder of the project 


## Interfacing with API

### Authentication
Throughout the api, there will be a required header ```Authorization``` that should have the value ```Basic <token>``` where <token> is the authentication token for that user
This documentation assumes that **every endpoint requires a RequestHeader for Authorisation unless started otherwise.**

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

### GET
**Endpoint:** /chinook/album\
**Requires Authentication:** NO\
**Parameter List**\
    &emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/json``` representing the Album found or NULL of type ```application/text```if not found\

**Endpoint:** /chinook/album/tracks\
**Requires Authentication:** NO\
**Parameter List**\
    &emsp;Required - albumId - Integer\
**Return Value** HTTP Response of type ```application/json``` representing a list of Tracks, which can be empty if not found\

**Endpoint:** /chinook/artist\
**Requires Authentication:** NO\
**Parameter List**\
    &emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/json``` representing the Artist or NULL of type ```application/text```if not found\

**Endpoint:** /chinook/customer
**Requires Authentication:** NO 
**Parameter List**
    &emsp;Required - id - Integer 
**Return Value** HTTP Response of type ```application/json``` representing the customer found with status 200

**Endpoint:** /chinook/customer/tracks
**Requires Authentication:** NO
**Parameter List**
    &emsp;Required - customerId - Integer
**Return Value** HTTP Response of type ```application/json``` representing a list of Tracks bought by the customer, 
which can be empty if not found or if customer did not buy any tracks. 

**Endpoint:** /chinook/discontinuedtrack
**Requires Authentication:** NO
**Parameter List**
    &emsp;Required - paramName - type
**Return Value**

**Endpoint:** chinook/album/customer/cost\
**Requires Authentication:** NO\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - albumId - Integer\
&emsp;Required - customerId - Integer\
**Return Value** HTTP Response of type ```application/text``` containing the total cost and a status ```200```

**Endpoint:** chinook/album/cost\
**Requires Authentication:** NO\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - albumId - Integer\
**Return Value** HTTP Response of type ```application/text``` containing the total cost and a status ```200```


**Endpoint:** chinook/track/buy\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body and Query String\
**Parameter List**\
&emsp;Required - id - Integer\
&emsp;Required - Request Body containing authorization header in same format as specified\
**Return Value** HTTP Response of type ```application/text``` containing one of:
- "Not Authorized" and a status of ```401```
- "Token Not Valid" and a status ```403```
- "Track does not exist" and a status ```404```
- "Customer already owns this track" and a status of ```200```
- "Invoice(s) created" and a status ```200```

**Endpoint:** /chinook/track\
**Requires Authentication:** NO\
**Parameter List**\
&emsp;Required - id - Integer
**Return Value** HTTP Response of type ```application/json``` representing the Track found with status ```200```

### POST
**Endpoint:** chinook/album/create\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of an Album\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Album Created" and a status ```200```

**Endpoint:** chinook/artist/create\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of an Album\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Artist Created" and a status ```200```

**Endpoint:** chinook/customer/create\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Customer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Customer Created" and a status ```200```

**Endpoint:** chinook/discontinuedtrack/insert\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a DiscontinuedTrack\
**Return Value** HTTP Response of type ```application/json``` containing the new DiscontinuedTrack with status code ```200```.

**Endpoint:** chinook/playlist/create\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Playlist\
**Return Value** HTTP Response of type ```application/json``` containing the new Playlist with status code ```200```

**Endpoint:** chinook/playlist/add\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\**Parameter List**\
&emsp;Required - JSON Body representative of a Playlist Track\
**Return Value** HTTP Response of type ```application/json``` containing the new Playlist Track with status code ```200```

**Endpoint:** chinook/playlist/remove\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Playlist Track\
**Return Value** HTTP Response of type ```application/json``` containing the new Playlist Track with status code ```200```

**Endpoint:** chinook/album/purchase\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - Integer - albumID\
&emsp;Required - Integer - customerID\
&emsp;Required - String - billingAddress\
&emsp;Required - String - billingCity\
&emsp;Required - String - billingCountry\
&emsp;Required - String - postalCode\
**Return Value** HTTP Response of type ```application/text``` containing one of:
- "Not Authorized" and a status of ```401```
- "No customer entry exists with that ID" and a status ```404```
- "No album entry exists with that ID" and a status ```404```
- "Successfully registered purchase of N tracks" and a status ```200```

**Endpoint:** chinook/track/create\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Track\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Track Created" and a status ```200```

### PUT
**Endpoint:** chinook/album/update\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of an Album\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Album Updated" and a status ```200```

**Endpoint:** chinook/artist/update\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of an Artist\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Artist Updated" and a status ```200```

**Endpoint:** chinook/customer/update\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Customer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Customer Updated" and a status ```200```

**Endpoint:** chinook/track/update\
**Requires Authentication:** YES\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Track\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Track Updated" and a status ```200```

### PATCH

**Endpoint:** chinook/playlist/update\
**Requires Authentication:** NO\
**Parameters Supplied As:** Request Body\
**Parameter List**\
&emsp;Required - JSON Body representative of a Playlist\
**Return Value** HTTP Response of type ```application/json``` containing the updated Playlist as JSON and a status ```200``` or ```application/text``` and the string "null" with status ```200```


### DELETE

**Endpoint:** chinook/album/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Album Deleted" and a status ```200```

**Endpoint:** chinook/artist/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Artist Deleted" and a status ```200```

**Endpoint:** chinook/token/delete\
**Requires Authentication:** NO\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - emailAddress - String\
**Return Value** HTTP Response of type ```application/json``` containing a single key "Message", with value "Token cleared. You will need a new token to use the services again." and a status of ```200``` or "email address not registered" and a status ```404```

**Endpoint:** chinook/customer/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**
&emsp;Required - id - Integer
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Customer Deleted" and a status ```200```

**Endpoint:** chinook/discontinuedtrack/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Track removed from discontinued" and a status ```200```

**Endpoint:** chinook/invoice/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Invoice Deleted" and a status ```200```

**Endpoint:** chinook/playlist/delete\
**Requires Authentication:** NO\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** Empty HTTP Response with status ```204```

**Endpoint:** chinook/track/delete\
**Requires Authentication:** YES\
**Parameters Supplied As:** Query String\
**Parameter List**\
&emsp;Required - id - Integer\
**Return Value** HTTP Response of type ```application/text``` containing either "Not Authorized" and a status of ```401``` or "Track Deleted" and a status ```200```
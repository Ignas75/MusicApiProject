DROP TABLE IF EXISTS EndpointPermissions;
DROP table IF EXISTS Roles;
DROP table IF EXISTS Tokens;

CREATE TABLE EndpointPermissions(
                                    rowID INTEGER AUTO_INCREMENT PRIMARY KEY,
                                    url NVARCHAR(255) NOT NULL,
                                    isForCustomer BIT,
                                    isForSalesStaff BIT,
                                    isForAdmins BIT
);

CREATE table Roles(
                      roleID INT NOT NULL,
                      roleDesc VARCHAR(50),
                      CONSTRAINT PK_Roles PRIMARY KEY (roleID)
);

CREATE table Tokens (
                        tokenID INT NOT NULL auto_increment,
                        authToken VARCHAR(255) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        roleID INT NOT NULL,
                        dateCreated DATE NOT NULL,
                        CONSTRAINT PK_Token PRIMARY KEY (tokenID),
                        FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);

INSERT INTO EndpointPermissions(url,isForCustomer,isForStaff,isForAdmins)
VALUES
       ("/chinook/album/delete",0,0,1),
       ("/chinook/album/create",0,0,1),
       ("/chinook/album/update",0,0,1),
       ("chinook/invoice/delete",0,0,1),
       ("/chinook/playlist/delete",0,0,1),
       ("/chinook/playlist/add",0,0,1),
       ("/chinook/playlist/remove",0,0,1),
       ("/chinook/playlist/create",0,0,1),
       ("/chinook/playlist/buy",1,0,0),
       ("/chinook/track/buy",1,0,0),
       ("/chinook/track/create",0,0,1),
       ("/chinook/track/update",0,0,1),
       ("/chinook/track/delete",0,0,1),
       ("/chinook/customer/create",0,1,1),
       ("/chinook/customer/update",0,1,1),
       ("/chinook/customer/delete",0,1,1),
       ("/chinook/artist/create",0,0,1),
       ("/chinook/artist/update",0,0,1),
       ("/chinook/artist/delete",0,0,1);


INSERT INTO Roles VALUES (1, 'Admin'), (2, 'Sales'), (3, 'Customers');
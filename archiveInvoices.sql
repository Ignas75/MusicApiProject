use
chinook;
DROP TABLE IF exists ArchiveInvoices;
CREATE TABLE ArchiveInvoices
(
    ArchiveID    INT auto_increment NOT NULL Primary KEY,
    firstName    VARCHAR(50)    NOT NULL,
    lastName     VARCHAR(50)    NOT NULL,
    EmailAddress VARCHAR(50)    NOT NULL,
    Address      VARCHAR(255)   NOT NULL,
    PostalCode   VARCHAR(10)    NOT NULL,
    InvoiceDate  DATE           NOT NULL,
    Total        DECIMAL(10, 2) NOT NULL
);
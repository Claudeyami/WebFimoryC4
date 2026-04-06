-- Baseline placeholder migration for existing production schema.
-- Real table migrations will be added in subsequent phases.

-- Series table
CREATE TABLE Series (
    SeriesID INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(255) NOT NULL,
    Description NVARCHAR(MAX),
    CoverImage NVARCHAR(500),
    CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),
    UpdatedAt DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Chapters table
CREATE TABLE Chapters (
    ChapterID INT IDENTITY(1,1) PRIMARY KEY,
    SeriesID INT NOT NULL,
    Title NVARCHAR(255) NOT NULL,
    Content NVARCHAR(MAX),
    ChapterNumber INT,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),
    UpdatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (SeriesID) REFERENCES Series(SeriesID) ON DELETE CASCADE
);

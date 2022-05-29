USE [Fs3]
GO

/****** Object:  Table [dbo].[StudentQuestion]    Script Date: 23/05/2022 10:18:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[StudentQuestion](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category] [nvarchar](50) NULL,
	[title] [nvarchar](50) NULL,
	[question] [nvarchar](550) NULL,
	[color] [nchar](10) NULL,
 CONSTRAINT [PK_StudentQuestion] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


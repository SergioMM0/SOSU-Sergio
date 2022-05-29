USE [Fs3]
GO

/****** Object:  Table [dbo].[subcategory]    Script Date: 23/05/2022 10:18:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[subcategory](
	[issue] [nvarchar](max) NULL,
	[subcategoryID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryFid] [int] NOT NULL,
 CONSTRAINT [PK_subcategory] PRIMARY KEY CLUSTERED 
(
	[subcategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


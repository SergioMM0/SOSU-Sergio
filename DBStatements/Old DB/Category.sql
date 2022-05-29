USE [Fs3]
GO

/****** Object:  Table [dbo].[Category]    Script Date: 23/05/2022 10:16:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Category](
	[categoryid] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[content] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


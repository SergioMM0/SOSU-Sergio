USE [Fs3]
GO

/****** Object:  Table [dbo].[observationstable]    Script Date: 23/05/2022 10:17:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[observationstable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[patientid] [int] NOT NULL,
	[content] [nvarchar](max) NULL,
 CONSTRAINT [PK_observationstable] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


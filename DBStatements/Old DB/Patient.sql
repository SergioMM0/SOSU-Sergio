USE [Fs3]
GO

/****** Object:  Table [dbo].[Patient]    Script Date: 23/05/2022 10:17:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Patient](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [nvarchar](200) NOT NULL,
	[last_name] [nvarchar](200) NOT NULL,
	[dateofBirth] [date] NULL,
	[gender] [nvarchar](50) NULL,
	[weight] [nvarchar](50) NULL,
	[height] [nvarchar](50) NULL,
	[cpr] [nvarchar](50) NOT NULL,
	[phone_number] [nvarchar](50) NULL,
	[schoolid] [int] NOT NULL,
	[isCopy] [int] NULL,
 CONSTRAINT [PK_Patient] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Patient]  WITH CHECK ADD  CONSTRAINT [FK_Patient_School] FOREIGN KEY([schoolid])
REFERENCES [dbo].[School] ([id])
GO

ALTER TABLE [dbo].[Patient] CHECK CONSTRAINT [FK_Patient_School]
GO


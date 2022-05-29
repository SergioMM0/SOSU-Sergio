USE [Fs3]
GO

/****** Object:  Table [dbo].[Case]    Script Date: 23/05/2022 10:16:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Case](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Description_of_the_condition] [nvarchar](max) NULL,
	[CategoryName] [nvarchar](max) NULL,
	[SubCategoryName] [nvarchar](max) NULL,
	[name] [nvarchar](50) NULL,
	[schoolid] [int] NULL,
	[isCopy] [int] NULL,
 CONSTRAINT [PK_Case] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[Case]  WITH CHECK ADD  CONSTRAINT [FK_Case_Case] FOREIGN KEY([id])
REFERENCES [dbo].[Case] ([id])
GO

ALTER TABLE [dbo].[Case] CHECK CONSTRAINT [FK_Case_Case]
GO

ALTER TABLE [dbo].[Case]  WITH CHECK ADD  CONSTRAINT [FK_Case_School] FOREIGN KEY([schoolid])
REFERENCES [dbo].[School] ([id])
GO

ALTER TABLE [dbo].[Case] CHECK CONSTRAINT [FK_Case_School]
GO


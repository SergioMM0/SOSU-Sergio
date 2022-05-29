USE [Fs3]
GO

/****** Object:  Table [dbo].[GroupUsers]    Script Date: 23/05/2022 10:17:16 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GroupUsers](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Groupid] [int] NOT NULL,
	[studentid] [int] NOT NULL,
 CONSTRAINT [PK_GroupUsers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[GroupUsers]  WITH CHECK ADD  CONSTRAINT [FK_GroupUsers_Groups] FOREIGN KEY([Groupid])
REFERENCES [dbo].[Groups] ([id])
GO

ALTER TABLE [dbo].[GroupUsers] CHECK CONSTRAINT [FK_GroupUsers_Groups]
GO

ALTER TABLE [dbo].[GroupUsers]  WITH CHECK ADD  CONSTRAINT [FK_GroupUsers_users] FOREIGN KEY([studentid])
REFERENCES [dbo].[users] ([userid])
GO

ALTER TABLE [dbo].[GroupUsers] CHECK CONSTRAINT [FK_GroupUsers_users]
GO


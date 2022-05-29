USE [Fs3]
GO

/****** Object:  Table [dbo].[Questionaire]    Script Date: 23/05/2022 10:17:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Questionaire](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Date] [datetime] NOT NULL,
	[SickPatientId] [int] NULL,
 CONSTRAINT [PK_Questionaire] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Questionaire]  WITH CHECK ADD  CONSTRAINT [FK_Questionaire_SickPatient] FOREIGN KEY([SickPatientId])
REFERENCES [dbo].[SickPatient] ([SickPatientid])
GO

ALTER TABLE [dbo].[Questionaire] CHECK CONSTRAINT [FK_Questionaire_SickPatient]
GO


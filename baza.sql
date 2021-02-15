BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "uposlenik" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"lozinka" TEXT,
	"korisnicko_ime"	TEXT,
	"datum_rodjenja"	TEXT,
	"datum_uposlenja"	TEXT,
	"pristup"	BIT,
	PRIMARY KEY("id")
);
INSERT INTO "uposlenik" VALUES (1,'Meho','Mehić','lozinka','mehomehic','11/05/1964','21/04/2000',1);
INSERT INTO "uposlenik" VALUES (2,'Dado','Dadić','lozinka','dadodadic','01/09/1977','08/07/2000',0);
INSERT INTO "uposlenik" VALUES (3,'Lana','Lanić','lozinka','lanalanic','26/12/1996','01/11/2000',0);
INSERT INTO "uposlenik" VALUES (4,'Darko','Darkić','lozinka','darkodarkic','21/04/1994','20/01/2000',0);
INSERT INTO "uposlenik" VALUES (5,'Mišo','Mišić','lozinka','misomisic','28/06/1990','10/08/2001',0);
COMMIT;

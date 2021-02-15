BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "uposlenik" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"korisnicko_ime"	TEXT,
	"datum_rodjenja"	TEXT,
	"datum_uposlenja"	INTEGER,
	"pristup"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "uposlenik" VALUES (1,'Meho','Mehić','mehomehic','11/05/1964','21/04/2000','true');
INSERT INTO "uposlenik" VALUES (2,'Dado','Dadić','dadodadic','01/09/1977','08/07/2000','false');
INSERT INTO "uposlenik" VALUES (3,'Lana','Lanić','lanalanic','26/12/1996','01/11/2000','false');
INSERT INTO "uposlenik" VALUES (4,'Darko','Darkić','darkodarkic','21/04/1994','20/01/2000','false');
INSERT INTO "uposlenik" VALUES (5,'Mišo','Mišić','misomisic','28/06/1990','10/08/2001','false');
COMMIT;

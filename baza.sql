BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "uposlenik" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"lozinka" TEXT,
	"korisnicko_ime" TEXT,
	"datum_rodjenja" TEXT,
	"datum_uposlenja" TEXT,
	"administrator"	BIT DEFAULT 0,
	PRIMARY KEY("id")
);
INSERT INTO "uposlenik" VALUES (1,'Meho','Mehić','lozinka','mehomehic','2000-02-02','2000-09-02',1);
INSERT INTO "uposlenik" VALUES (2,'Dado','Dadić','lozinka','dadodadic','1977-03-20','2000-09-02',0);
INSERT INTO "uposlenik" VALUES (3,'Lana','Lanić','lozinka','lanalanic','1996-11-27','2000-09-02',0);
INSERT INTO "uposlenik" VALUES (4,'Darko','Darkić','lozinka','darkodarkic','1994-01-01','2000-09-02',0);
INSERT INTO "uposlenik" VALUES (5,'Mišo','Mišić','lozinka','misomisic','1990-01-14','2000-09-02',0);
COMMIT;

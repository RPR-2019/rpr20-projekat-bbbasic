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
CREATE TABLE IF NOT EXISTS "vozilo" (
	"id"	INTEGER,
	"tip_vozila"	TEXT,
	"marka" TEXT,
	"model" TEXT,
	"godina_proizvodnje" INT,
	"registracija" TEXT UNIQUE,
	"broj_sasije" INT UNIQUE,
	"boja" TEXT,
	"vrsta_boje" TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "vozilo" VALUES(1, 'Putnički','Suzuki','Ignis',2007,'A12-A-345','JS1VX51L982100325','Siva','Metalik');
INSERT INTO "vozilo" VALUES(2, 'Putnički','Fiat','Panda',2019,'E21-O-213','3C3CFFAR2ET358453','Crna','Folija');
INSERT INTO "vozilo" VALUES(3, 'Autobus','Volvo','S60',2000,'T00-T-208','4VGJDAWF0WN861479','Smeđa','Obična');
CREATE TABLE IF NOT EXISTS "klijent" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"mjesto_prebivalista" TEXT,
    "broj_telefona" TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "klijent" VALUES (1,'Patak','Patkić','Branilaca Sarajeva 21','062-222-222');
INSERT INTO "klijent" VALUES (2,'Ivo','Ivić','Vladimira Valtera Perića 13','061-111-111');
CREATE TABLE IF NOT EXISTS "tehnicki_pregled" (
	"id"	INTEGER,
	"datum_pregleda" TEXT,
	"vozilo_id"	INT,
	"klijent_id" INT,
    "vrsta_tehnickog_pregleda" TEXT,
    "status_tehnickog_pregleda" TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "tehnicki_pregled" VALUES (1,'2000-02-02', 1, 2,'Redovni','Zakazan');
INSERT INTO "tehnicki_pregled" VALUES (2,'2000-02-02', 2, 1,'Redovni','Kompletiran');
INSERT INTO "tehnicki_pregled" VALUES (3,'2000-02-02', 3, 2,'Redovni','Otkazan');
CREATE TABLE IF NOT EXISTS "tim_tehnicki_pregled" (
    "tehnicki_pregled_id" INT,
    "uposlenik_id" INT
);
INSERT INTO "tim_tehnicki_pregled" VALUES (1,1);
INSERT INTO "tim_tehnicki_pregled" VALUES (2,1);
INSERT INTO "tim_tehnicki_pregled" VALUES (3,1);
COMMIT;

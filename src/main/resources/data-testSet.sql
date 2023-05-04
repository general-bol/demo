INSERT INTO role (nom) VALUES ("ROLE_ADMINISTRATEUR"),("ROLE_UTILISATEUR");
INSERT INTO entreprise(nom) VALUES ("Google"),("Amazon"),("Facebook"),("Microsoft");
INSERT INTO pays(nom) VALUES ("France"),("Italie"),("Allemagne"),("Espagne");
INSERT INTO job(nom) VALUES("developpeur"),("testeur"),("chef de projet"),("stagiare");
INSERT INTO utilisateur(nom,prenom,pays_id,entreprise_id,email,password,role_id, created_date, updated_date)
    VALUES("DOE","John",2,1,"john@doe","$2a$10$iwpMHVP1MNyrRrCgnnoi2OhPHjrtl2Qi7y/mFewg3jUwoVPPXmR9O",2,'2022-12-03',UTC_TIMESTAMP()),
          ("DOZER","Bill",3,2,"bill@dozer","$2a$10$SoBuGcVPO3Qez/noYh1R1u.P/Bm0cvPIjkkGGzfnU5LYnGHuCc5T.",1,'2023-02-25',UTC_TIMESTAMP()),
          ("CAMAIS","Leon",4,3,"camais@leon","$2a$10$Fs/zQr2OL/gySgKIzxlQ4.jzyBSZiqZI6luPY0JXScmxE/JsFKsSC",2,'2021-03-05',UTC_TIMESTAMP());
INSERT INTO user_job(utilisateur_id, job_id) VALUES(1,3),(2,1);


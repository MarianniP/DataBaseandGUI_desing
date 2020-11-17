1) SELECT user.name, surname, etaireia.name, id, salary, COUNT(job.id) AS 'NUMBER OF CANDIDATES FOR THE JOB'
     FROM job
     INNER JOIN recruiter ON recruiter.username=job.recruiter
     INNER JOIN user ON user.username=recruiter.username
     INNER JOIN etaireia ON etaireia.AFM=recruiter.firm
     INNER JOIN applies ON applies.job_id=job.id
     WHERE job.salary>1900 GROUP BY id;

2) SELECT username,certificates, AVG(grade), COUNT(cand_usrname)
     FROM candidate
     INNER JOIN has_degree ON has_degree.cand_usrname=candidate.username
     GROUP BY cand_usrname HAVING COUNT(cand_usrname)>1;

3) SELECT username, COUNT(cand_usrname), AVG(salary)
     FROM candidate
     INNER JOIN applies ON applies.cand_usrname=candidate.username
     INNER JOIN job ON job.id=applies.job_id
     GROUP BY username HAVING AVG(salary)>1800;

4) SELECT etaireia.name, city, position, title
     FROM etaireia
     INNER JOIN recruiter ON recruiter.firm=etaireia.AFM
     INNER JOIN job ON job.recruiter=recruiter.username
     INNER JOIN requires ON requires.job_id=job.id
     INNER JOIN antikeim ON antikeim.title=requires.antikeim_title
     WHERE city='Patra' AND title LIKE '%Program%';

5) SELECT recruiter.username,COUNT(distinct job.id), COUNT( distinct interview.rname), AVG(salary)
    
   FROM job
     
   INNER JOIN recruiter ON recruiter.username=job.recruiter
	
   INNER JOIN interview ON recruiter.username=interview.rname
    
   GROUP BY username HAVING COUNT( distinct job.id)>2 ORDER BY AVG(salary) DESC;

-----------------------------------------------------------------------


-----------------------------------------------------------------------

TRIGGER II

DELIMITER $
CREATE TRIGGER no_delete
BEFORE DELETE ON applies
FOR EACH ROW
BEGIN
	DECLARE date DATE;
	DECLARE j_id INT(4);
	SET date=CURDATE();
	SET j_id=OLD.job_id;
	IF(date>(SELECT submission_date FROM job WHERE job.id=j_id)) THEN
		SIGNAL SQLSTATE VALUE '45000'
		SET MESSAGE_TEXT = 'SUBMISSION DATE HAS PASSED';
	END IF;
	
END$
DELIMITER ;

-----------------------------------------------------------------------


=======+++++=======+++++========
STORE PROCEDURE 
=======+++++=======+++++========

DELIMITER $

CREATE PROCEDURE choose_candidate(IN id_job INT(4), OUT CandName VARCHAR(12))
BEGIN
 BEGIN
  DECLARE CandName VARCHAR(12);
  DECLARE CandPers INT;
  DECLARE CandEduc INT;
  DECLARE CandExp INT;
  DECLARE CandInter INT;
  DECLARE CandScore INT;
  DECLARE Flag INT;
  DECLARE candlist_cursorIII CURSOR FOR 
   SELECT cname
   FROM interview
   WHERE id_job=interview.inter_job AND ( personality IS NULL  OR  experience IS NULL  OR  education IS NULL );
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET Flag=1;
  OPEN candlist_cursorIII;
  SET Flag=0;
  FETCH candlist_cursorIII INTO CandName;
  WHILE (Flag=0) DO
   SELECT CandName AS 'Evaluation not finished';
   FETCH candlist_cursorIII INTO CandName;
  END WHILE;
  CLOSE candlist_cursorIII;
 END;
 BEGIN
  DECLARE CandName VARCHAR(12);
  DECLARE CandPers INT;
  DECLARE CandEduc INT ;
  DECLARE CandExp INT ;
  DECLARE CandInter INT;
  DECLARE CandScore INT;
  DECLARE finishedflag INT;
  DECLARE candlist_cursor CURSOR FOR 
   SELECT cname, AVG(personality), education, experience, COUNT(cname), SUM( personality + education + experience )
   FROM interview
   WHERE id_job=interview.inter_job AND personality != 0  AND experience != 0 AND education != 0 GROUP BY cname ORDER BY SUM( personality + education + experience ) DESC;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedflag=1;
  OPEN candlist_cursor;
  SET finishedflag=0;
  FETCH candlist_cursor INTO CandName, CandPers, CandEduc, CandExp, CandInter, CandScore;
  WHILE (finishedflag=0) DO
   SELECT CandName AS 'NAME', CandPers AS 'PERSONALITY', CandEduc AS 'EDUCATION', CandExp AS 'EXPERIENCE', CandInter 'NUMBER_OF_INTERVIEWS', CandScore AS 'SCORE';
   FETCH candlist_cursor INTO CandName, CandPers, CandEduc, CandExp, CandInter, CandScore;
  END WHILE;
  CLOSE candlist_cursor ;
 END;
 BEGIN
  DECLARE CandName VARCHAR(12);
  DECLARE CandPers INT;
  DECLARE CandEduc INT ;
  DECLARE CandExp INT ;
  DECLARE CandInter INT;
  DECLARE CandScore INT;
  DECLARE Finished INT;
  DECLARE candlist_cursorII CURSOR FOR 
   SELECT cname
   FROM interview
   WHERE id_job=interview.inter_job AND ( personality = 0 OR education = 0 OR experience = 0 );
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET Finished=1;
  OPEN candlist_cursorII;
  SET Finished=0;
  FETCH candlist_cursorII INTO CandName;
  WHILE (Finished=0) DO
   SELECT CandName AS 'NAME', CONCAT_WS ("/", "no prior experience", "inadequeate education", "failed the interview" ) AS 'Rejection Reason';
   FETCH candlist_cursorII INTO CandName;
  END WHILE;
  CLOSE candlist_cursorII;
 END; 
END$

DELIMITER ;

-----------------------------------------------

HISTORY TRIGGER (III)
-----------------------------------------------

INSERT

1)

DROP TRIGGER IF EXISTS history_user_insert;

DELIMITER $
CREATE TRIGGER history_user_insert
AFTER INSERT ON user
FOR EACH ROW
BEGIN
INSERT INTO history
( mod_sql, modtable, moduser )
VALUES
(CONCAT("INSERT INTO user ( username,password,name,surname,reg_date,email) VALUES (","'",NEW.username,"'",",","'", NEW.password,"'",",", "'",NEW.name,"'",",","'" ,NEW.surname,"'",",", "'",NEW.reg_date,"'",",","'" ,NEW.email,"'",")" ),
"user",
user()
);
END $
DELIMITER ;




2)
DELIMITER $
CREATE TRIGGER history_recruiter_insert

AFTER INSERT ON recruiter

FOR EACH ROW

BEGIN

INSERT INTO history
( mod_sql, modtable, moduser )

VALUES
(CONCAT("INSERT INTO recruiter ( username, exp_years,firm) VALUES (","'",NEW.username,"'",",","'", NEW.exp_years,"'",",","'" ,NEW.firm,"'",")" ),
"candidate",
user()
);

END $

DELIMITER ;


3)
DROP TRIGGER IF EXISTS history_candidate_insert;

DELIMITER $
CREATE TRIGGER history_candidate_insert
AFTER INSERT ON candidate
FOR EACH ROW
BEGIN
INSERT INTO history
( mod_sql, modtable, moduser )
VALUES
(CONCAT("INSERT INTO candidate ( username, bio, sistatikes, certificates) VALUES (","'",NEW.username,"'",",","'", NEW.bio,"'",",", "'",NEW.sistatikes,"'",",","'" ,NEW.certificates,"'",")" ),
"candidate",
user()
);
END $
DELIMITER ;


4)
DROP TRIGGER IF EXISTS history_job_insert;

DELIMITER $
CREATE TRIGGER history_job_insert
AFTER INSERT ON job
FOR EACH ROW
BEGIN
INSERT INTO history
( mod_sql, modtable, moduser )
VALUES
(CONCAT("INSERT INTO job ( start_date, salary, position, edra, recruiter, announce_date, submission_date) VALUES (","'",NEW.start_date,"'",",","'", NEW.salary,"'",",", "'",NEW.position,"'",",","'" ,NEW.edra,"'",",", "'",NEW.recruiter,"'",",","'", NEW.announce_date, "'", ",", NEW.submission_date,"'",")" ),
"job",
user()
);
END $
DELIMITER ;


5)
DROP TRIGGER IF EXISTS history_etaireia_insert;

DELIMITER $
CREATE TRIGGER history_etaireia_insert
AFTER INSERT ON etaireia
FOR EACH ROW
BEGIN
INSERT INTO history
( mod_sql, modtable, moduser )
VALUES
(CONCAT("INSERT INTO etaireia ( AFM, DOY, name, tel, street, num, city, country) VALUES VALUES (","'",NEW.AFM,"'",",","'", NEW.DOY,"'",",", "'",NEW.name,"'",",","'" ,NEW.tel,"'",",", "'",NEW.street,"'",",","'", NEW.num, "'", ",", "'", NEW.city, "'", ",", NEW.country,"'",")" ),
"etaireia",
user()
);
END $
DELIMITER ;
========================================================================================================================================================================


UPDATE 
-------------------------------

1)
DROP TRIGGER IF EXISTS history_user_update;
DELIMITER $
CREATE TRIGGER history_user_update
AFTER UPDATE ON user
FOR EACH ROW
BEGIN
 INSERT INTO history
   ( mod_sql, modtable, moduser )
 VALUES
   (
   CONCAT( 
     "UPDATE user SET ",
     if( NEW.password = OLD.password, "", CONCAT( "password=", CAST( NEW.password AS CHAR ), "," ) ),
     if( NEW.name = OLD.name, "", CONCAT( "name=", "'", CAST( NEW.name AS CHAR ), "'," ) ),
     if( NEW.surname = OLD.surname, "", CONCAT( "surname=", "'", CAST( NEW.surname AS CHAR ), "'," ) ),
     if( NEW.reg_date = OLD.reg_date, "", CONCAT( "reg_date=", "'", CAST( NEW.reg_date AS CHAR ), "'," ) ),
     if( NEW.email = OLD.email, "", CONCAT( "email=", "'", CAST( NEW.email AS CHAR ), "'," ) ),
     CONCAT( "usrname=", CAST( NEW.usrname AS CHAR ) ),
   " WHERE ", "usrname=", CAST( NEW.usrname AS CHAR )
   ),
   
   "user",
   user()
   );
END $
DELIMITER ;


2)
DROP TRIGGER IF EXISTS history_candidate_update;
DELIMITER $
CREATE TRIGGER history_candidate_update
AFTER UPDATE ON candidate
FOR EACH ROW
BEGIN
 INSERT INTO history
   ( mod_sql, modtable, moduser )
 VALUES
   (
   CONCAT( 
     "UPDATE candidate SET ",
     if( NEW.bio = OLD.bio, "", CONCAT( "bio=", CAST( NEW.bio AS CHAR ), "," ) ),
     if( NEW.sistatikes = OLD.sistatikes, "", CONCAT( "sistatikes=", "'", CAST( NEW.sistatikes AS CHAR ), "'," ) ),
     if( NEW.certificates = OLD.certificates, "", CONCAT( "certificates=", "'", CAST( NEW.certificates AS CHAR ), "'," ) ),
     CONCAT( "username=", CAST( NEW.username AS CHAR ) ),
   " WHERE ", "username=", CAST( NEW.username AS CHAR )
   ),
   
   "candidate",
   user()
   );
END $
DELIMITER ;


3)
DROP TRIGGER IF EXISTS history_recruiter_update;
DELIMITER $
CREATE TRIGGER history_recruiter_update
AFTER UPDATE ON recruiter
FOR EACH ROW
BEGIN
 INSERT INTO history
   ( mod_sql, modtable, moduser )
 VALUES
   (
   CONCAT( 
     "UPDATE recruiter SET ",
     if( NEW.exp_years = OLD.exp_years, "", CONCAT( "exp_years=", CAST( NEW.exp_years AS CHAR ), "," ) ),
     if( NEW.firm = OLD.firm, "", CONCAT( "firm=", "'",  NEW.firm  , "'," ) ),
     CONCAT( "username=", CAST( NEW.username AS CHAR ) ),
   " WHERE ", "username=", CAST( NEW.username AS CHAR )
   ),
   
   "recruiter",
   user()
   );
END $
DELIMITER ;


4)
DROP TRIGGER IF EXISTS history_job_update;

DELIMITER $

CREATE TRIGGER history_job_update

AFTER UPDATE ON job

FOR EACH ROW

BEGIN
 INSERT INTO history
   ( mod_sql, modtable, moduser )
 
VALUES
   (
   CONCAT( 
     "UPDATE job SET ",
     
if( NEW.start_date = OLD.start_date, "", CONCAT( "start_date=", CAST( NEW.start_date AS CHAR ), "," ) ),
     
if( NEW.salary = OLD.salary, "", CONCAT( "salary=", "'", CAST( NEW.salary AS CHAR ), "'," ) ),
     
if( NEW.position = OLD.position, "", CONCAT( "position=", "'", CAST( NEW.position AS CHAR ), "'," ) ),
     
if( NEW.edra = OLD.edra, "", CONCAT( "edra=", "'", CAST( NEW.edra AS CHAR ), "'," ) ),
     
if( NEW.recruiter = OLD.recruiter, "", CONCAT( "recruiter", "'", CAST( NEW.recruiter AS CHAR ), "'," ) ),
     
if( NEW.announce_date= OLD.announce_date, "", CONCAT( "announce_date=", "'", CAST( NEW.announce_date AS CHAR ), "'," ) ),
     
if( NEW.submission_date = OLD.submission_date, "", CONCAT( "submission_date=", "'", CAST( NEW.submission_date AS CHAR ), "'," ) ),
     
CONCAT( "id=", CAST( NEW.id AS CHAR ) ),
   " WHERE ", "id=", CAST( NEW.id AS CHAR )
   ),
   
   "job",
   user()
   );

END $

DELIMITER ;


5)
DROP TRIGGER IF EXISTS history_etaireia_after_update;

DELIMITER $
CREATE TRIGGER history_etaireia_after_update

AFTER UPDATE ON etaireia

FOR EACH ROW
BEGIN
 INSERT INTO history
   ( mod_sql, modtable, moduser )
 
VALUES
   (
   CONCAT( 
     "UPDATE etaireia SET ",
     
if( NEW.DOY = OLD.DOY, "", CONCAT( "DOY=", "'", CAST( NEW.DOY AS CHAR ), "'," ) ),
   
  if( NEW.name = OLD.name, "", CONCAT( "name=", "'", CAST( NEW.name AS CHAR ), "'," ) ),
     
if( NEW.tel = OLD.tel, "", CONCAT( "tel=", "'", CAST( NEW.tel AS CHAR ), "'," ) ),
   
  if( NEW.street = OLD.street, "", CONCAT( "street=", "'", CAST( NEW.street AS CHAR ), "'," ) ),
     
if( NEW.num = OLD.num, "", CONCAT( "num=", "'", CAST( NEW.num AS CHAR ), "'," ) ),
    
 if( NEW.city = OLD.city, "", CONCAT( "city=", "'", CAST( NEW.city AS CHAR ), "'," ) ),
    
 if( NEW.country = OLD.country, "", CONCAT( "country=", "'", CAST( NEW.country AS CHAR ), "'," ) ),
    
 CONCAT( "AFM=", CAST( NEW.AFM AS CHAR ) ),
   
" WHERE ", "AFM=", CAST( NEW.AFM AS CHAR )
   ),
   
 
  "etaireia",
   user()
   );

END $

DELIMITER ;

-----------------------------------------

DELETE
---------

1)

DROP TRIGGER IF EXISTS history_user_delete;
DELIMITER $
CREATE TRIGGER history_user_delete
AFTER DELETE ON user
FOR EACH ROW
BEGIN
 INSERT INTO history ( mod_sql, modtable, moduser ) 
 VALUES 
   ( 
   CONCAT( "DELETE FROM user WHERE usrname=", OLD.usrname ),

 "user",
 user() 
 );
END $
DELIMITER ;

2)
DROP TRIGGER IF EXISTS history_candidate_delete;
DELIMITER $
CREATE TRIGGER history_candidate_delete
AFTER DELETE ON candidate
FOR EACH ROW
BEGIN
 INSERT INTO history ( mod_sql, modtable, moduser ) 
 VALUES 
   ( 
   CONCAT( "DELETE FROM candidate WHERE username=", OLD.username ),

 "candidate",
 user() 
 );
END $
DELIMITER ;


3)
DROP TRIGGER IF EXISTS history_recruiter_delete;
DELIMITER $$
CREATE TRIGGER history_recruiter_delete
AFTER DELETE ON recruiter
FOR EACH ROW
BEGIN
 INSERT INTO history ( mod_sql, modtable, moduser ) 
 VALUES 
   ( 
   CONCAT( "DELETE FROM recruiter WHERE username=",  OLD.username ),

 "recruiter",
 user() 
 );
END $
DELIMITER ;

4)
DROP TRIGGER IF EXISTS history_job_delete;
DELIMITER $
CREATE TRIGGER history_job_delete
AFTER DELETE ON job
FOR EACH ROW
BEGIN
 INSERT INTO history ( mod_sql, modtable, moduser ) 
 VALUES 
   ( 
   CONCAT( "DELETE FROM job WHERE id=", CAST( OLD.id AS CHAR ) ),

 "job",
 user() 
 );
END $
DELIMITER ;


5)
DROP TRIGGER IF EXISTS history_etaireia_delete;
DELIMITER $
CREATE TRIGGER history_etaireia_delete
AFTER DELETE ON etaireia
FOR EACH ROW
BEGIN
 INSERT INTO history ( mod_sql, modtable, moduser ) 
 VALUES 
   ( 
   CONCAT( "DELETE FROM etaireia WHERE AFM=",  OLD.AFM ),

 "etaireia",
 user() 
 );
END $
DELIMITER ;
-----------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------


TRIGGER FOR JOB UPDATE 

DROP TRIGGER IF EXISTS job_before_update;

DELIMITER $

CREATE TRIGGER job_before_update
BEFORE UPDATE ON job
FOR EACH ROW
BEGIN
DECLARE ID INT(4);
DECLARE adate DATE;
SET ID=OLD.ID;
SET adate=CURDATE();
IF NEW.id != OLD.id THEN 
	SET NEW.id=ID;
END IF;
IF NEW.announce_date!= adate THEN 
	SET NEW.announce_date=adate;
END IF;
END$

DELIMITER ;

+++++++++++++++++++++++++++++++++++++++++++++++++++++++

TRIGGER FOR INSERT JOB

DELIMITER $

CREATE TRIGGER job_before_insert
BEFORE INSERT ON job
FOR EACH ROW
BEGIN
DECLARE adate DATE;
SET adate=CURDATE();
IF NEW.announce_date!= adate THEN 
	SET NEW.announce_date=adate;
END IF;
END$

DELIMITER ;

+++++++++++++++++++++++++++++++++++++++++++++++++++++++

TRIGGER FOR ETAIREIA BEFORE UPDATE

DROP TRIGGER IF EXISTS etaireia_before_update;

DELIMITER $
CREATE TRIGGER etaireia_before_update
BEFORE UPDATE ON etaireia
FOR EACH ROW
BEGIN
DECLARE AFM CHAR(9);
DECLARE DOY VARCHAR(15);
DECLARE NAME VARCHAR(35);
SET AFM=OLD.AFM;
SET DOY=OLD.DOY;
SET NAME=OLD.name;
IF NEW.AFM!= OLD.AFM THEN 
	SET NEW.AFM=AFM;
END IF;
IF NEW.DOY!= OLD.DOY THEN 
	SET NEW.DOY=OLD.DOY;
END IF;
IF NEW.name!= OLD.name THEN 
	SET NEW.name=NAME;
END IF;
END$
DELIMITER ;


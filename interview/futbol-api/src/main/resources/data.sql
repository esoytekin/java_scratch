-- insert players

INSERT INTO player (id, birth_date, name, surname)
VALUES (1, {ts '2000-09-17'}, 'player1-name', 'player1-surname');
INSERT INTO player (id, birth_date, name, surname)
VALUES (2, {ts '2001-08-27'}, 'player2-name', 'player2-surname');
INSERT INTO player (id, birth_date, name, surname)
VALUES (3, {ts '2001-05-27'}, 'player3-name', 'player3-surname');
INSERT INTO player (id, birth_date, name, surname)
VALUES (4, {ts '1995-04-27'}, 'player4-name', 'player4-surname');
INSERT INTO player (id, birth_date, name, surname)
VALUES (5, {ts '1999-03-27'}, 'player5-name', 'player5-surname');

-- insert teams

insert into team (id, name, currency) VALUES (1, 'team1', 'TL');
INSERT INTO team (id, name, currency) VALUES (2, 'team2', 'EUR');
INSERT INTO team (id, name, currency) VALUES (3, 'team3', 'EUR');
INSERT INTO team (id, name, currency) VALUES (4, 'team4', 'TL');
INSERT INTO team (id, name, currency) VALUES (5, 'team5', 'USD');

-- populate league data

INSERT INTO league (id, team_id, player_id, start_date, end_date) VALUES (1,1,1, {ts '2012-02-01'}, {ts '2012-07-31'});
INSERT INTO league (id, team_id, player_id, start_date, end_date) VALUES (2,2,1, {ts '2012-08-01'}, {ts '2012-11-30'});
INSERT INTO league (id, team_id, player_id, start_date, end_date) VALUES (3,3,1, {ts '2012-12-01'}, {ts '2013-03-31'});


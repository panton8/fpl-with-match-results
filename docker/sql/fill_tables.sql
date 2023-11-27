-- Users
INSERT INTO users (id, user_name, email, password, access, budget)
VALUES
    (1, 'admin', 'fpladmin@gmail.com', 'b27e517dd8d579a66bd67883f98862f0', 'Admin', 100.0),
    (2, 'panton8', 'anton@gmail.com', 'a83a26d4887950da95a6236c67fcc220', 'Base', 100.0),
    (3, 'andrew', 'andrew@mail.ru', 'd914e3ecf6cc481114a3f534a5faf90b', 'Base', 100.0);


--Managers
INSERT INTO managers (id, name, surname, nationality, age)
VALUES
    (1, 'Pep', 'Guardiola', 'Spanish', 52),
    (2, 'Ange', 'Postecoglou', 'Australian', 58),
    (3, 'Jürgen', 'Klopp', 'German', 56),
    (4, 'Mikel', 'Arteta', 'Spanish', 41),
    (5, 'Roberto', 'De Zerbi', 'Italian', 44),
    (6, 'David', 'Moyes', 'Scottish', 60),
    (7, 'Unai', 'Emery', 'Spanish', 51),
    (8, 'Mauricio', 'Pochettino', 'Argentinian', 51),
    (9, 'Andoni', 'Iraola', 'Spanish', 41),
    (10, 'Erik', 'ten Hag', 'Dutch', 53),
    (11, 'Steve', 'Cooper', 'Welsh', 43),
    (12, 'Rob', 'Edwards', 'Welsh', 40),
    (13, 'Gary ', 'ONeil', 'English', 40),
    (14, 'Roy', 'Hodgson', 'English', 76),
    (15, 'Eddie', 'Howe', 'English', 45),
    (16, 'Sean', 'Dyche', 'English', 52),
    (17, 'Vincent', 'Kompany', 'Belgian', 37),
    (18, 'Marco', 'Silva', 'Portuguese', 46),
    (19, 'Paul', 'Heckingbottom', 'English', 46),
    (20, 'Thomas', 'Frank', 'Danish', 49);


--Clubs
INSERT INTO clubs (name, founded, stadium, manager_id)
VALUES
    ('Manchester City', '1880-11-23', 'Etihad Stadium', 1),
    ('Tottenham', '1882-09-05', 'Tottenham Hotspur Stadium', 2),
    ('Liverpool', '1892-03-15', 'Anfield Stadium', 3),
    ('Arsenal', '1886-10-04', 'Emirates Stadium', 4),
    ('Brighton', '1901-06-24', 'AMEX Stadium', 5),
    ('West Ham', '1895-06-29', 'London Stadium', 6),
    ('Aston Villa', '1874-03-16', 'Villa Park Stadium', 7),
    ('Chelsea', '1905-03-10' , 'Stamford Bridge Stadium', 8),
    ('Bournemouth', '1899-09-12', 'Vitality Stadium', 9),
    ('Manchester United', '1878-05-17', 'Old Trafford', 10),
    ('Nottingham Forest', '1865-12-12', 'The City Ground Stadium', 11),
    ('Luton Town', '1885-04-11', 'Kenilworth Road Stadium', 12),
    ('Wolverhampton', '1877-01-12', 'Molineux Stadium', 13),
    ('Crystal Palace', '1905-01-01', 'Selhurst Park Stadium', 14),
    ('Newcastle', '1892-12-09', 'St James Park Stadium', 15),
    ('Everton', '1878-02-24', 'Goodison Park Stadium', 16),
    ('Burnley', '1882-05-18', 'Turf Moor Stadium', 17),
    ('Fulham', '1879-08-16', 'Craven Cottage Stadium',18),
    ('Sheffield United', '1889-03-22', 'Bramall Lane Stadium', 19),
    ('Brentford', '1889-10-10', 'Gtech Community Stadium', 20);


-- Players

INSERT INTO players (id, name, surname, club, price, pos, is_healthy)
VALUES
    --GKP
    (13, 'Robert', 'Sanchez', 'Chelsea', 6.0, 'GKP', true),
    (15, 'Dean', 'Henderson', 'Nottingham Forest', 6.1, 'GKP', true),
    (16, 'James', 'Trafford', 'Burnley', 5.2, 'GKP', true),
    (46, 'Nick', 'Pope', 'Newcastle', 6.3, 'GKP', true),
    (47, 'Jason', 'Steele', 'Brighton', 5.5, 'GKP', true),
    (48, 'Lukasz', 'Fabianski', 'West Ham', 6.1, 'GKP', true),
    (49, 'Emiliano', 'Martinez', 'Aston Villa', 6.2, 'GKP', true),
    (50, 'Jose', 'Sa', 'Wolverhampton', 6.0, 'GKP', true),
    (51, 'Thomas', 'Kaminski', 'Luton Town', 5.4, 'GKP', true),
    (52, 'Bernd', 'Leno', 'Fulham', 6.1, 'GKP', true),
    --DEF
    (2, 'Reece', 'James', 'Chelsea', 6.4, 'DEF', false),
    (11, 'Trent', 'Alexander-Arnold', 'Liverpool', 6.3, 'DEF', false),
    (12, 'Marc', 'Guehi', 'Crystal Palace', 5.9, 'DEF', true),
    (29, 'Lisandro', 'Martinez', 'Manchester United', 6.2, 'DEF', true),
    (30, 'Diego', 'Carlos', 'Aston Villa', 6.9, 'DEF', true),
    (31, 'Jamaal', 'Lascelles', 'Newcastle', 6.0, 'DEF', true),
    (17, 'Max', 'Lowe', 'Sheffield United', 5.7, 'DEF', true),
    (18, 'Issa', 'Diop', 'Fulham', 5.8, 'DEF', true),
    (19, 'Ilya', 'Zabarnyi', 'Bournemouth', 5.9, 'DEF', true),
    (20, 'Kieran', 'Trippier', 'Newcastle', 6.4, 'DEF', true),
    (21, 'Craig', 'Dawson', 'Wolverhampton', 5.6, 'DEF', true),
    (39, 'Cristian', 'Romero', 'Tottenham', 6.6, 'DEF', true),
    (40, 'Wesley', 'Fofana', 'Chelsea', 6.9, 'DEF', false),
    (41, 'Pedro', 'Porro', 'Tottenham', 6.3, 'DEF', true),
    (42, 'Sven', 'Botman', 'Newcastle', 6.5, 'DEF', true),
    (43, 'Renan', 'Lodi', 'Nottingham Forest', 5.9, 'DEF', true),
    (44, 'Malo', 'Gusto', 'Chelsea', 5.8, 'DEF', true),
    (45, 'Nathan', 'Patterson', 'Everton', 5.4, 'DEF', false),
    (65, 'Harry', 'Maguire', 'Manchester United', 6.2, 'DEF', true),
    -- MID
    (1, 'Sandro', 'Tonali', 'Newcastle', 6.7, 'MID', true),
    (28, 'Kaoru', 'Mitoma', 'Brighton', 6.9, 'MID', true),
    (32, 'Fabio', 'Vieira', 'Arsenal', 6.4, 'MID', true),
    (14, 'James', 'Ward-Prowse', 'West Ham', 6.8, 'MID', true),
    (4, 'Phil', 'Foden', 'Manchester City', 7.7, 'MID', true),
    (5, 'Bukayo', 'Saka', 'Arsenal', 8.1, 'MID', true),
    (22, 'Dominik', 'Szoboszlai', 'Liverpool', 6.0, 'MID', true),
    (23, 'Donny', 'van de Beek', 'Manchester United', 6.5, 'MID', true),
    (24, 'Carney', 'Chukwuemeka', 'Chelsea', 5.8, 'MID', false),
    (25, 'Mathias', 'Jensen', 'Brentford', 6.1, 'MID', true),
    (7, 'Enzo', 'Fernandez', 'Chelsea', 7.1, 'MID', true),
    (8, 'Declan', 'Rice', 'Arsenal', 7.0, 'MID', true),
    (9, 'Marcus', 'Rashford', 'Manchester United', 10.6, 'MID', true),
    (33, 'Kevin', 'De Bruyne', 'Manchester City', 11.1, 'MID', false),
    (34, 'Luis', 'Diaz', 'Liverpool', 8.3, 'MID', true),
    (35, 'Bernardo', 'Silva', 'Manchester City', 8.8, 'MID', true),
    (36, 'Mohamed', 'Salah', 'Liverpool', 11.4, 'MID', true),
    (37, 'Mason', 'Mount', 'Manchester United', 7.9, 'MID', false),
    (38, 'Gabriel', 'Martinelli', 'Arsenal', 8.1, 'MID', false),
    (53, 'Amadou', 'Onana', 'Everton', 6.5, 'MID', true),
    (54, 'Mateo', 'Kovacic', 'Manchester City', 6.7, 'MID', false),
    (55, 'John', 'McGinn', 'Aston Villa', 6.2, 'MID', true),
    (56, 'Cheick', 'Doucoure', 'Crystal Palace', 6.0, 'MID', true),
    (57, 'Romeo', 'Lavia', 'Chelsea', 5.9, 'MID', false),
    (64, 'Maxwel', 'Cornet', 'West Ham', 6.6, 'MID', false),
    -- FWD
    (3, 'Erling', 'Haaland', 'Manchester City', 12.2, 'FWD', true),
    (26, 'Nikolas', 'Jackson', 'Chelsea', 7.5, 'FWD', true),
    (27, 'Dominic', 'Solanke', 'Bournemouth', 6.4, 'FWD', true),
    (10, 'Gabriel', 'Jesus', 'Arsenal', 9.9, 'FWD', true),
    (6, 'Richarlison', '', 'Tottenham', 7.9, 'FWD', true),
    (59, 'Alexander', 'Isak', 'Newcastle', 7.6, 'FWD', true),
    (58, 'Hee-chan', ' Hwang', 'Wolverhampton', 6.7, 'FWD', true),
    (60, 'Dominic', 'Calvert-Lewin', 'Everton', 6.6, 'FWD', true),
    (61, 'Evan', 'Ferguson', 'Brighton', 7.5, 'FWD', true),
    (62, 'Taiwo', 'Awoniyi', 'Nottingham Forest', 6.3, 'FWD', true),
    (63, 'Ollie', 'Watkins', 'Aston Villa', 6.4, 'FWD', true);


-- Statistics
INSERT INTO statistics (game_week, goals, assists, minutes, own_goals, yellow_cards, red_cards, saves, clean_sheet, player_id)
VALUES
    (1, 0, 0, 88, 0, 1, 1, 0, 1, 1),
    (1, 0, 1, 90, 0, 0, 0, 0, 1, 2),
    (1, 3, 1, 74, 0, 0, 0, 0, 0, 3),
    (1, 0, 1, 45, 0, 0, 0, 0, 0, 4),
    (1, 0, 2, 77, 0, 0, 0, 0, 0, 5),
    (1, 1, 0, 90, 0, 1, 0, 0, 1, 6),
    (1, 0, 0, 90, 0, 0, 0, 0, 1, 7),
    (1, 0, 0, 90, 0, 0, 0, 0, 0, 8),
    (1, 1, 1, 68, 0, 0, 0, 0, 0, 9),
    (1, 1, 0, 84, 0, 0, 0, 0, 0, 10),
    (1, 0, 0, 19, 0, 0, 1, 0, 0, 11),
    (1, 0, 0, 90, 0, 1, 0, 0, 1, 12),
    (1, 0, 0, 90, 0, 0, 0, 4, 1, 13),
    (1, 1, 0, 0, 0, 0, 0, 0, 0, 14),
    (1, 0, 0, 0, 0, 0, 0, 7, 0, 15),
    (1, 0, 0, 90, 0, 0, 0, 1, 0, 16),
    (1, 0, 0, 0, 0, 0, 0, 0, 1, 17),
    (1, 0, 0, 90, 1, 1, 0, 0, 0, 18),
    (1, 0, 0, 90, 0, 0, 0, 0, 0, 19),
    (1, 0, 1, 90, 0, 0, 0, 0, 1, 20),
    (1, 0, 0, 59, 0, 1, 0, 0, 0, 21),
    (1, 0, 0, 29, 0, 0, 0, 0, 0, 22),
    (1, 0, 0, 0, 0, 0, 0, 0, 0, 23),
    (1, 0, 0, 0, 0, 0, 0, 0, 0, 24),
    (1, 0, 0, 64, 0, 0, 0, 0, 0, 25),
    (1, 0, 1, 23, 0, 0, 0, 0, 0, 26),
    (1, 1, 0, 90, 0, 0, 0, 0, 0, 27),
    (1, 1, 1, 71, 0, 0, 0, 0, 1, 28),
    (1,0, 0, 0, 0, 0, 0, 0, 0, 29),
    (1, 0, 0, 0, 0, 0, 0, 0, 0, 30),
    (1, 0, 0, 0, 0, 0, 0, 0, 0, 31),
    (1, 0, 0, 51, 0, 0, 0, 0, 0, 32),
    (1, 0, 0, 88, 0, 1, 1, 0, 1, 33),
    (1, 0, 1, 90, 0, 0, 0, 0, 1, 34),
    (1, 3, 1, 74, 0, 0, 0, 0, 0, 35),
    (1, 0, 1, 45, 0, 0, 0, 0, 0, 36),
    (1, 0, 2, 77, 0, 0, 0, 0, 0, 37),
    (1, 1, 0, 90, 0, 1, 0, 0, 1, 38),
    (1, 0, 0, 90, 0, 0, 0, 0, 1, 39),
    (1, 0, 0, 90, 0, 0, 0, 0, 0, 40),
    (1, 1, 1, 68, 0, 0, 0, 0, 0, 41),
    (1, 1, 1, 84, 0, 0, 0, 0, 0, 42),
    (1, 0, 0, 19, 0, 0, 1, 0, 0, 43),
    (1, 0, 0, 90, 0, 1, 0, 0, 1, 44),
    (1, 0, 0, 90, 0, 0, 0, 4, 1, 45),
    (1, 0, 0, 90, 0, 0, 0, 0, 0, 46),
    (1, 0, 0, 90, 0, 0, 0, 7, 0, 47),
    (1, 0, 0, 90, 0, 0, 0, 6, 1, 48),
    (1, 0, 0, 90, 0, 0, 0, 4, 1, 49),
    (1, 0, 0, 90, 1, 1, 0, 0, 0, 50),
    (1, 0, 0, 90, 0, 0, 0, 3, 1, 51),
    (1, 1, 1, 90, 0, 0, 0, 0, 1, 52),
    (1, 0, 0, 59, 0, 1, 0, 0, 0, 53),
    (1, 0, 1, 29, 0, 0, 0, 0, 0, 54),
    (1, 0, 0, 0, 0, 0, 0, 0, 0, 55),
    (1, 0, 0, 16, 0, 0, 0, 0, 0, 56),
    (1, 1, 0, 64, 0, 0, 0, 0, 0, 57),
    (1, 0, 0, 77, 0, 0, 0, 0, 0, 58),
    (1, 1, 0, 90, 0, 0, 0, 0, 0, 59),
    (1, 1, 1, 71, 0, 0, 0, 0, 1, 60),
    (1, 0, 0, 90, 1, 0, 0, 0, 0, 61),
    (1, 1, 0, 36, 0, 0, 0, 0, 0, 62),
    (1, 0, 1, 60, 0, 0, 0, 0, 0, 63),
    (1, 0, 0, 12, 0, 0, 0, 0, 0, 64),
    (1, 0, 0, 90, 0, 1, 0, 0, 0, 65);


--Referees
INSERT INTO referees (id, name, surname, nationality, age, appointments)
VALUES
    (1, 'Michael', 'Oliver', 'English', 38, 0),
    (2, 'Anthony', 'Taylor', 'English', 44, 0),
    (3, 'Craig', 'Pawson', 'English', 44, 0),
    (4, 'Chris', 'Kavanagh', 'English', 38, 0),
    (5, 'Darren', 'England', 'English', 37, 0),
    (6, 'Paul', 'Tierney', 'English', 42, 0),
    (7, 'Simon', 'Hooper', 'English', 41, 0),
    (8, 'David', 'Coote', 'English', 36, 0),
    (9, 'Andrew', 'Madley', 'English', 40, 0),
    (10, 'Stuart', 'Attwell', 'English', 40, 0),
    (11, 'Robert', 'Jones', 'English', 39, 0),
    (12, 'Darren', 'Bond', 'English', 35, 0);

--Matches
INSERT INTO matches (id, date, time, home_club, guest_club, home_club_goals, guest_club_goals, referee_id)
VALUES
    (1, '2023-08-11', '22:00:00', 'Burnley', 'Manchester City', 0, 3, 1),
    (2, '2023-08-12', '15:00:00', 'Arsenal', 'Nottingham Forest',2, 1, 2),
    (3, '2023-08-12', '16:00:00', 'Sheffield United', 'Crystal Palace', 0, 1, 3),
    (4, '2023-08-12', '16:00:00', 'Everton', 'Fulham', 0, 1, 4),
    (5, '2023-08-12', '16:00:00', 'Brighton', 'Luton Town', 4, 1, 5),
    (6, '2023-08-12', '18:30:00', 'Bournemouth', 'West Ham', 1, 1, 6),
    (7, '2023-08-12', '18:30:00', 'Newcastle', 'Aston Villa', 5, 1, 7),
    (8, '2023-08-13', '14:30:00', 'Brentford', 'Tottenham', 2, 2, 8),
    (9, '2023-08-13', '16:00:00', 'Chelsea', 'Liverpool', 1, 1, 9),
    (10, '2023-08-14', '22:45:00', 'Manchester United', 'Wolverhampton', 1, 0, 10);
INSERT INTO matches (id, date, time, home_club, guest_club, referee_id)
VALUES
    (11, '2023-09-30', '17:00:00', 'Aston Villa', 'Brighton', 12),
    (12, '2023-09-30', '17:00:00', 'Bournemouth', 'Arsenal',11),
    (13, '2023-09-30', '17:00:00', 'Everton', 'Luton Town', 10),
    (14, '2023-09-30', '17:00:00', 'Manchester United', 'Crystal Palace', 9),
    (15, '2023-09-30', '17:00:00', 'Newcastle', 'Burnley', 8),
    (16, '2023-09-30', '17:00:00', 'West Ham', 'Sheffield United', 7),
    (17, '2023-09-30', '18:30:00', 'Wolverhampton', 'Manchester City', 6),
    (18, '2023-09-30', '19:30:00', 'Tottenham', 'Liverpool', 5),
    (19, '2023-10-01', '16:00:00', 'Nottingham Forest', 'Brentford', 4),
    (20, '2023-10-02', '22:00:00', 'Fulham', 'Chelsea', 3);

--News
INSERT INTO news (id, title, text, match_id)
VALUES
    (1,
     'Chelsea and Liverpool drew for the seventh time in a row.', 'Chelsea and Liverpool earned their seventh draw in a row, taking into account all competitions, exchanging goals in the first half - 1:1.
    Newcomers Robert Sanchez, Axel Disasi and Nicolas Jackson were named in the Blues starting line-up, as well as Levi Colwill, who returned from his loan at Brighton.
    Reds coach Jurgen Klopp released his newcomers Dominik Szoboszlai and Alexis McAllister from the first minutes.
    Liverpool played more energetically at the beginning of the match, being the first to come close to scoring. In the 12th minute, Mohamed Salah stepped outside the penalty area, manipulated the ball and hit the crossbar!
    Four minutes later, Chelsea responded. Jackson was already ready to close Raheem Sterlings cross into the goalkeeper, but visiting goalkeeper Alisson was the first to catch the ball.
    And in the 18th minute the Reds opened the scoring. Salah advanced along the right flank, after which he cut a low pass into the center of the penalty area, from where Luis Diaz hit the ball - 0:1.
    After the missed goal, the hosts began to open up more, and thanks to this, their opponents had opportunities for counterattacks.
    Salah was the most active at Liverpool. Soon, Thiago Silva took Mohameds shot in the penalty area, then the Egyptian realized a one-on-one opportunity, but due to a VAR offside, the goal was not counted.
    Despite their defensive failures, Chelsea never stopped moving forward and were rewarded for this with a return goal in the 37th minute. Ben Chilwell made a header from the edge of the penalty area towards the goalkeeper, and there Disasi kicked in touch - 1:1.
    In the 39th minute, Chilwell suddenly jumped up one on one in the center, passed Alisson and rolled the ball into an empty net, but here too the VAR system intervened, determining offside - the goal was not counted.
    The end of the first half remained for Chelsea, and Jackson even scored an inaccurate shot from a lethal distance.
    Liverpool also started the second half better. After Luis Diazs header, the ball ricocheted off Jackson and went near the post. It might have appeared that Nicolas played with a hand, but VAR did not confirm this.
    In the 55th minute, the hosts responded with Chilwells shot from the left edge of the penalty area, but Alisson saved his team. With Bens moment, the "blues" extinguished the opponent''s attacking impulse and began to move forward themselves.
    In the 71st minute, Alisson once again helped the Reds by stopping a shot from Jackson, who had broken through on the left edge of the penalty area.
    Towards the end the game became dull. The teams chose not to take risks, agreeing to a draw. Already in stoppage time, substitute Darwin Nunez scored a dangerous shot with a ricochet, but missed, to which the hosts responded with a breakthrough from Mikhail Mudrik, but he was unable to shoot, nor could he lead his partner to strike.
    A draw looks like a fair result. Both teams had good periods, but no one dared to go for the victory.', 9);

--Table
INSERT INTO clubs_stat (club, played, wins, draws, losses, goals_for, goals_against, goals_difference, points)
VALUES
    ('Manchester City', 6, 6, 0, 0, 16, 3, 13, 18),
    ('Sheffield United', 6, 0, 1, 5, 5, 17, -12, 1),
    ('Newcastle', 6, 3, 0, 3, 16, 7, 9, 9),
    ('West Ham', 6, 3, 1, 2, 11, 10, 1, 10),
    ('Nottingham Forest', 6, 2, 1, 3, 7, 9, -2, 7),
    ('Crystal Palace', 6, 2, 2, 2, 6, 7, -1, 8),
    ('Aston Villa', 6, 4, 0, 2, 12, 10, 2, 12),
    ('Luton Town', 5, 0, 1, 4, 3, 11, -8, 1),
    ('Burnley', 5, 0, 1, 4, 4, 13, -9, 2),
    ('Manchester United', 6, 3, 0, 3, 7, 10, -3, 9),
    ('Bournemouth', 6, 0, 3, 0, 5, 11, -6, 3),
    ('Tottenham', 6, 4, 2, 0, 15, 7, 8, 14),
    ('Brentford', 6, 1, 3, 2, 9, 9, 0, 6),
    ('Liverpool', 6, 5, 1, 0, 15, 5, 10, 16),
    ('Brighton', 6, 5, 0, 1, 18, 8, 10, 15),
    ('Fulham', 6, 2, 2, 2, 6, 7, -1, 8),
    ('Everton', 6, 1, 1, 4, 5, 10, -5, 4),
    ('Arsenal', 6, 4, 2, 0, 11, 6, 5, 14),
    ('Chelsea', 6, 1, 2, 3, 5, 6, -1, 5),
    ('Wolverhampton', 6, 1, 1, 4, 6, 12, -6, 4);


INSERT INTO teams (id, name, points, available_transfers, user_id)
VALUES
    (1, 'adminTeam', 0, 2, 1),
    (2, 'ChelseaFan', 0, 2, 2),
    (3, 'dreamFC', 0, 2, 3);

INSERT INTO teams_players (team_id, player_id, is_captain,is_starter)
VALUES
    (1, 16, false, true),
    (1, 46, false, false),
    (1, 2, false, true),
    (1, 29, false, true),
    (1, 18, false, false),
    (1, 44, false, true),
    (1, 17, false, false),
    (1, 28, false, false),
    (1, 7, false, true),
    (1, 37, false, true),
    (1, 9, false, true),
    (1, 5, false, true),
    (1, 3, false, true),
    (1, 62, true, true),
    (1, 27, false, true),
-------------------
    (2, 13, false, true),
    (2, 15, false, false),
    (2, 11, false, true),
    (2, 30, false, true),
    (2, 20, false, true),
    (2, 45, false, true),
    (2, 39, false, false),
    (2, 14, false, false),
    (2, 35, false, false),
    (2, 38, false, true),
    (2, 36, false, true),
    (2, 22, false, true),
    (2, 3, true, true),
    (2, 59, false, true),
    (2, 63, false, true),
-------------------
    (3, 47, false,false),
    (3, 52, false, true),
    (3, 12, true, true),
    (3, 31, false, true),
    (3, 19, false, false),
    (3, 65, false, true),
    (3, 21, false, false),
    (3, 23, false, true),
    (3, 8, false,true),
    (3, 54, false, true),
    (3, 25, false, true),
    (3, 24, false, true),
    (3, 3, false, true),
    (3, 60, false, false),
    (3, 26, false, true);
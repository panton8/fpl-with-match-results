-- Users
CREATE TABLE IF NOT EXISTS users (
     id serial NOT NULL PRIMARY KEY,
     user_name varchar NOT NULL,
     email varchar NOT NULL,
     password varchar NOT NULL,
     access varchar NOT NULL,
     budget numeric NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_users_email_password ON users (email, password);

--Managers
CREATE TABLE IF NOT EXISTS managers(
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    surname varchar NOT NULL,
    nationality varchar NOT NULl,
    age int NOT NULL
);

--Clubs
CREATE TABLE IF NOT EXISTS clubs(
    name varchar NOT NULL PRIMARY KEY ,
    founded date NOT NULL,
    stadium varchar NOT NULL,
    manager_id serial NOT NULL REFERENCES managers (id)
);

--Players
CREATE TABLE IF NOT EXISTS players (
   id serial NOT NULL PRIMARY KEY,
   name varchar NOT NULL,
   surname varchar NOT NULL,
   club varchar NOT NULL REFERENCES clubs (name),
   price numeric NOT NULL,
   pos char(3) NOT NULL,
   is_healthy boolean NOT NULL
);

--Teams
CREATE TABLE IF NOT EXISTS teams (
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    points int NOT NULL,
    available_transfers int NOT NULL,
    user_id serial REFERENCES users (id)
);

-- Link between teams and players
CREATE TABLE IF NOT EXISTS teams_players (
    team_id serial NOT NULL REFERENCES teams (id),
    player_id serial NOT NULL REFERENCES players (id),
    is_captain boolean NOT NULL,
    is_starter boolean NOT NULL
);

ALTER TABLE teams_players
    ADD CONSTRAINT teams_players_connection PRIMARY KEY (player_id, team_id);
CREATE INDEX IF NOT EXISTS idx_teams_players_team_id ON teams_players (team_id);

-- Statistics
CREATE TABLE IF NOT EXISTS statistics(
    game_week int NOT NULL,
    goals int NOT NULL,
    assists int NOT NULL,
    minutes int NOT NULL,
    own_goals int NOT NULL,
    yellow_cards int NOT NULL,
    red_cards int NOT NULL,
    saves int NOT NULL,
    clean_sheet int NOT NULL,
    player_id serial NOT NULL REFERENCES players (id)
);

ALTER TABLE statistics
    ADD CONSTRAINT statistics_connection PRIMARY KEY (game_week, player_id);

--Referees
CREATE TABLE IF NOT EXISTS referees(
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    surname varchar NOT NULL,
    nationality varchar NOT NULl,
    age int NOT NULL
);

--Matches
CREATE TABLE IF NOT EXISTS matches(
    id serial NOT NULL PRIMARY KEY,
    date date NOT NULL,
    time time NOT NULL,
    home_club varchar NOT NULL REFERENCES clubs (name),
    guest_club varchar NOT NULL REFERENCES clubs (name),
    home_club_goals int,
    guest_club_goals int,
    referee_id serial NOT NULL REFERENCES referees (id)
);

CREATE INDEX IF NOT EXISTS idx_matches_date ON matches (date);
CREATE INDEX IF NOT EXISTS idx_matches_home_club ON matches (home_club);
CREATE INDEX IF NOT EXISTS idx_matches_guest_club ON matches (guest_club);

--Table
CREATE TABLE IF NOT EXISTS clubs_stat(
  club varchar NOT NULL PRIMARY KEY REFERENCES clubs (name),
  played int NOT NULL,
  wins int NOT NULL,
  draws int NOT NULL,
  losses int NOT NULL,
  goals_for int NOT NULL,
  goals_against int NOT NULL,
  goals_difference int NOT NULL,
  points int NOT NULL
);

--News
CREATE TABLE IF NOT EXISTS news(
  id serial NOT NULL PRIMARY KEY,
  title varchar NOT NULL,
  text text NOT NULL,
  match_id serial NOT NULL REFERENCES matches (id)
);
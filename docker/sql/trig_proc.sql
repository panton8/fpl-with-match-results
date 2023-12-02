-- Function for club stats updating
CREATE OR REPLACE FUNCTION update_clubs_stat()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE clubs_stat
    SET played = played + 1,
        wins = CASE WHEN NEW.home_club_goals > NEW.guest_club_goals THEN wins + 1 ELSE wins END,
        draws = CASE WHEN NEW.home_club_goals = NEW.guest_club_goals THEN draws + 1 ELSE draws END,
        losses = CASE WHEN NEW.home_club_goals < NEW.guest_club_goals THEN losses + 1 ELSE losses END,
        goals_for = goals_for + NEW.home_club_goals,
        goals_against = goals_against + NEW.guest_club_goals,
        goals_difference = goals_difference - NEW.guest_club_goals + NEW.home_club_goals,
        points = CASE WHEN NEW.home_club_goals > NEW.guest_club_goals THEN points + 3
                      WHEN NEW.home_club_goals = NEW.guest_club_goals THEN points + 1
                      ELSE points END
    WHERE club = NEW.home_club;

    UPDATE clubs_stat
    SET played = played + 1,
        wins = CASE WHEN NEW.guest_club_goals > NEW.home_club_goals THEN wins + 1 ELSE wins END,
        draws = CASE WHEN NEW.guest_club_goals = NEW.home_club_goals THEN draws + 1 ELSE draws END,
        losses = CASE WHEN NEW.guest_club_goals < NEW.home_club_goals THEN losses + 1 ELSE losses END,
        goals_for = goals_for + NEW.guest_club_goals,
        goals_against = goals_against + NEW.home_club_goals,
        goals_difference = goals_difference + NEW.guest_club_goals - NEW.home_club_goals,
        points = CASE WHEN NEW.guest_club_goals > NEW.home_club_goals THEN points + 3
                      WHEN NEW.guest_club_goals = NEW.home_club_goals THEN points + 1
                      ELSE points END
    WHERE club = NEW.guest_club;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


-- Trigger for clubs stat updating
CREATE TRIGGER update_clubs_stat_trigger
    AFTER UPDATE ON matches
    FOR EACH ROW
EXECUTE PROCEDURE update_clubs_stat();


-- Function for news adding
CREATE OR REPLACE FUNCTION add_news()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO news (title, text, match_id)
    VALUES ('New match result', 'Match between ' || NEW.home_club || ' and ' || NEW.guest_club || ' ended with ' || NEW.home_club_goals || '-' || NEW.guest_club_goals, NEW.id);

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


-- trigger for news adding
CREATE TRIGGER add_news_trigger
    AFTER UPDATE ON matches
    FOR EACH ROW
EXECUTE PROCEDURE add_news();

-- Function for ref appointments
CREATE OR REPLACE FUNCTION add_ref_match()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE referees
    SET appointments = appointments + 1

    WHERE id = NEW.referee_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


-- trigger for ref appointments
CREATE TRIGGER add_ref_match_trigger
    AFTER INSERT ON matches
    FOR EACH ROW
EXECUTE PROCEDURE add_ref_match();

-----

CREATE OR REPLACE FUNCTION insert_match_without_goals(
    p_date DATE,
    p_time TIME,
    p_home_club VARCHAR,
    p_guest_club VARCHAR,
    p_referee_id INT
)
    RETURNS VOID AS $$
BEGIN
    INSERT INTO matches (date, time, home_club, guest_club, referee_id)
    VALUES (p_date, p_time, p_home_club, p_guest_club, p_referee_id);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_match_goals(
    p_match_id INT,
    p_home_goals INT,
    p_guest_goals INT
)
    RETURNS VOID AS $$
BEGIN
    UPDATE matches
    SET home_club_goals = p_home_goals,
        guest_club_goals = p_guest_goals
    WHERE id = p_match_id;
END;
$$ LANGUAGE plpgsql;

------------------------------------

CREATE OR REPLACE FUNCTION check_transfer()
    RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT available_transfers FROM teams WHERE id = NEW.team_id) < 1 THEN
        RAISE EXCEPTION 'No available transfers for the team.';
    END IF;

    IF (SELECT budget FROM users WHERE id = (SELECT user_id FROM teams WHERE id = NEW.team_id)) +
       (SELECT price FROM players WHERE id = OLD.player_id) -
       (SELECT price FROM players WHERE id = NEW.player_id) < 0 THEN
        RAISE EXCEPTION 'Insufficient budget for the transfer.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION perform_transfer_update(
    p_team_id INT,
    p_new_player_id INT,
    p_old_player_id INT
)
    RETURNS VOID AS $$
DECLARE
    user_budget NUMERIC;
    new_player_price NUMERIC;
    old_player_price NUMERIC;
BEGIN
    UPDATE teams_players
    SET player_id = p_new_player_id
    WHERE team_id = p_team_id AND player_id = p_old_player_id;

    UPDATE teams
    SET available_transfers = available_transfers - 1
    WHERE id = p_team_id;

    SELECT budget INTO user_budget
    FROM users
    WHERE id = (SELECT user_id FROM teams WHERE id = p_team_id);

    SELECT price INTO new_player_price FROM players WHERE id = p_new_player_id;
    SELECT price INTO old_player_price FROM players WHERE id = p_old_player_id;

    UPDATE users
    SET budget = user_budget + old_player_price - new_player_price
    WHERE id = (SELECT user_id FROM teams WHERE id = p_team_id);
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER check_transfer_trigger
    BEFORE UPDATE ON teams_players
    FOR EACH ROW
EXECUTE PROCEDURE check_transfer();
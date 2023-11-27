-- Procedure for club stats updating
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


-- trigger for clubs stat updating
CREATE TRIGGER update_clubs_stat_trigger
    AFTER UPDATE ON matches
    FOR EACH ROW
EXECUTE PROCEDURE update_clubs_stat();


-- procedure for news adding
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

-- procedure for ref appointments
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

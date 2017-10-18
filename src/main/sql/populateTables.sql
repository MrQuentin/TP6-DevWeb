DELETE FROM film;
DELETE FROM genre;

INSERT INTO `genre`(`genre_id`,`name`) VALUES (1,'Drama');
INSERT INTO `genre`(`genre_id`,`name`) VALUES (2,'Adventure');
INSERT INTO `genre`(`genre_id`,`name`) VALUES (3,'Action');

INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) VALUES (1, 'Captain Fantastic', '2016-10-12', 1, 118, 'Matt Ross','In the forests of the Pacific Northwest, a father devoted to raising his six kids with a rigorous physical and intellectual education is forced to leave his paradise and enter the world, challenging his idea of what it means to be a parent.');
INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) VALUES (2, 'Mademoiselle', '2016-11-01', 1, 144, 'Park Chan-Wook', 'A woman is hired as a handmaiden to a Japanese heiress, but secretly she is involved in a plot to defraud her.');
INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) VALUES (3, 'Miss Peregrine\'s Home for Peculiar Children', '2016-10-05', 2, 127, 'Tim Burton', 'When Jacob discovers clues to a mystery that stretches across time, he finds Miss Peregrine\'s Home for Peculiar Children. But the danger deepens after he gets to know the residents and learns about their special powers.');
INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) VALUES (4, 'Doctor Strange', '2016-10-26', 1, 115, 'Scott Derrickson', 'A former neurosurgeon embarks on a journey of healing only to be drawn into the world of the mystic arts.');

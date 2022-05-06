CREATE TABLE movie (
  id BIGSERIAL PRIMARY KEY,
  title varchar(255) not null,
  author varchar(255) not null
); 

INSERT INTO movie(title, author) VALUES('Uma Aventura', 'Filipe Gonçalves');
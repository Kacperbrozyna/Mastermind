DROP DATABASE IF EXISTS mastermindDB;

CREATE DATABASE mastermindDB;

use mastermindDB;

create table game(
Id INT PRIMARY KEY AUTO_INCREMENT,
timeStarted timestamp not null,
generatedAnswer varchar(7) not null,
gameFinished BOOLEAN DEFAULT false);

create table gameRound (
roundId int primary key auto_increment,
timeOfGuess timestamp not null,
userGuess varchar(7) not null,
noOfExact int default 0,
noOfPartial int default 0,
gameId int default 1 not null,
foreign key (gameId) references game(Id)
);

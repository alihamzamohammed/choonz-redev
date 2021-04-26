insert into genre
values (1, 'very loud music', 'pop');
insert into artist
values (1, 'artist number 1');
insert into artist
values (2, 'artist number 2');

insert into album
values (1, 'album.png', 'artist number 1 first album', 1);

insert into album_genre
values (1, 1);
insert into track
values(
        1,
        1,
        'A regular test track with no name',
        'Track 1',
        1,
        1
    );
insert into playlist
values (
        1,
        'playlist.png',
        'my favourite songs',
        'My Playlist'
    );
insert into playlist_tracks
values (1, 1);
insert into track_contributing_artists
values (2, 1)
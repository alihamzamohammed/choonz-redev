insert into genre
values (1, 'pop', 'very loud music');
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
        'Track 1',
        'A regular test track with no name',
        1,
        1
    );
insert into playlist
values (
        1,
        'My playlist',
        'my favourite songs',
        'playlist.png'
    );
insert into playlist_tracks
values (1, 1);
insert into artist_contributed_tracks
values (2, 1)
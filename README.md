# Twitch Link

Twitch Link is a Java Plugin for announce live streams in spigot servers.

## Installation

Copy the JAR package to the "/plugins" directory of your server  to install. Use a version referring to a version of your server.

## Last Releases
[twitchlink-v1.0.0-beta-1.16.1.jar](shorturl.at/gxIJ7) - 08.31.2020

[twitchlink-v1.0.0-beta-1.16.2.jar](shorturl.at/fuCKO) - 08.31.2020

## Permissions

```
twitchlink.admin
```
```
twitchlink.live
```
```
twitchlink.info
```
```
twitchlink.save.own
```
```
twitchlink.save.any
```


## Usage and Permissions

| Command                                      | Permission         |Default|
| -------------------------------------------- |:-------------------|:------:|
| ```twitch```                                 | twitchlink.live    |   op   |
| ```twitch live```                            | twitchlink.live    |   op   |
| ```twitch save <TwitchUsername>```           | twitchlink.save.own|   op   |
| ```twitch save <Player> <TwitchUsername>```  | twitchlink.save.any|   op   |
| ```twitch delete <TwitchUsername>```         | twitchlink.del.own |   op   |
| ```twitch delete <Player> <TwitchUsername>```| twitchlink.del.any |   op   |
| ```twitch <player> info```                   | twitchlink.info    |   op   |
| ```twitch enable <value>```                  | twitchlink.admin   |   op   |





## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

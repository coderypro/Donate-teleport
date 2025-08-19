# DonateTeleport

A Minecraft plugin that provides donation-based teleportation features for Spigot/Paper servers.

## Features

- **Multiple Teleportation Options**: Spawn, Home, VIP, and Premium teleportation
- **Permission-Based System**: Different teleportation options based on donation ranks
- **Cooldown System**: Configurable cooldowns to prevent spam
- **Admin Commands**: Reload configuration without restarting
- **Configurable Locations**: Set custom VIP and Premium teleportation locations

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/dtp` | Show available teleportation commands | `donateteleport.use` |
| `/dtp spawn` | Teleport to spawn | `donateteleport.use` |
| `/dtp home` | Teleport to home (VIP+) | `donateteleport.vip` |
| `/dtp vip` | Teleport to VIP area (VIP+) | `donateteleport.vip` |
| `/dtp premium` | Teleport to Premium area (Premium+) | `donateteleport.premium` |
| `/dtpreload` | Reload plugin configuration | `donateteleport.admin` |

## Permissions

- `donateteleport.use` - Basic teleportation (default: true)
- `donateteleport.vip` - VIP teleportation features (default: false)
- `donateteleport.premium` - Premium teleportation features (default: false)
- `donateteleport.admin` - Administrative commands (default: op)

## Configuration

The plugin creates a `config.yml` file with the following options:

```yaml
# Cooldown time in seconds between teleportations
cooldown: 30

# Special locations for donation ranks
locations:
  vip:
    world: "world"
    x: 100.5
    y: 64.0
    z: 200.5
  premium:
    world: "world"
    x: -100.5
    y: 64.0
    z: -200.5
```

## Installation

1. Download the latest release JAR file
2. Place it in your server's `plugins/` directory
3. Restart your server
4. Configure the locations in `plugins/DonateTeleport/config.yml`
5. Reload with `/dtpreload` or restart the server

## Building from Source

### Requirements
- Java 8 or higher
- Maven
- Internet connection (for downloading Spigot API)

### Build Steps

1. Clone this repository
2. Uncomment the repositories and dependencies sections in `pom.xml`
3. Run the build script: `./build.sh`

Or manually:
```bash
mvn clean package
```

The compiled JAR will be in the `target/` directory.

### Alternative: Local Spigot Installation

If you need to build offline or have connectivity issues:

1. Download BuildTools.jar from Spigot
2. Run: `java -jar BuildTools.jar --rev 1.20.1`
3. Build the plugin: `mvn clean package`

## Usage for Server Owners

1. **Set up locations**: Edit the config file to set VIP and Premium teleportation locations
2. **Configure permissions**: Use your permissions plugin to grant ranks appropriate permissions
3. **Set cooldowns**: Adjust the cooldown time to fit your server's needs

## Donation Integration

This plugin works with any permission-based donation system:

- **Basic users**: Can use `/dtp spawn`
- **VIP donors**: Get access to `/dtp home` and `/dtp vip`
- **Premium donors**: Get access to all commands including `/dtp premium`

Simply grant the appropriate permissions when players make donations.

## Support

If you encounter issues:

1. Check the server console for error messages
2. Verify permissions are set correctly
3. Ensure locations are configured properly
4. Try reloading the config with `/dtpreload`

## License

This project is open source. Feel free to modify and distribute according to your needs.

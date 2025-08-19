# DonateTeleport Installation Guide

## Quick Start for Server Owners

### 1. Installation

1. **Download the Plugin**
   - Either download a pre-built JAR file or build from source
   - Place the JAR file in your server's `plugins/` directory

2. **First Launch**
   - Start/restart your Minecraft server
   - The plugin will create a `plugins/DonateTeleport/` folder with `config.yml`

3. **Basic Configuration**
   - Edit `plugins/DonateTeleport/config.yml` to set teleport locations
   - Use `/dtpadmin setvip` and `/dtpadmin setpremium` in-game to set locations

### 2. Setting Up Teleport Locations

**In-Game Setup (Recommended):**
1. Go to the location where you want VIP teleports to go
2. Run `/dtpadmin setvip`
3. Go to the location where you want Premium teleports to go  
4. Run `/dtpadmin setpremium`

**Manual Configuration:**
Edit `plugins/DonateTeleport/config.yml`:
```yaml
locations:
  vip:
    world: "world"
    x: 100.5
    y: 64.0
    z: 200.5
    yaw: 90.0
    pitch: 0.0
  premium:
    world: "world_nether"
    x: -50.0
    y: 70.0
    z: 150.0
    yaw: 180.0
    pitch: 0.0
```

### 3. Permissions Setup

**For LuckPerms:**
```
/lp group default permission set donateteleport.use true
/lp group vip permission set donateteleport.vip true
/lp group premium permission set donateteleport.premium true
/lp group admin permission set donateteleport.admin true
```

**For PermissionsEx:**
Add to your permissions.yml:
```yaml
groups:
  default:
    permissions:
      - donateteleport.use
  vip:
    inheritance:
      - default
    permissions:
      - donateteleport.vip
  premium:
    inheritance:
      - vip
    permissions:
      - donateteleport.premium
```

### 4. Donation Integration

When players donate:
1. **VIP Donors**: Grant `donateteleport.vip` permission
2. **Premium Donors**: Grant `donateteleport.premium` permission

This can be done manually or automated with donation plugins like:
- BuycraftX
- Tebex
- Enjin
- Any plugin that can grant permissions on donation

### 5. Commands for Players

| Command | Permission | Description |
|---------|-----------|-------------|
| `/dtp` | `donateteleport.use` | Show help |
| `/dtp spawn` | `donateteleport.use` | Teleport to spawn |
| `/dtp home` | `donateteleport.vip` | Teleport to bed/home |
| `/dtp vip` | `donateteleport.vip` | Teleport to VIP area |
| `/dtp premium` | `donateteleport.premium` | Teleport to Premium area |

### 6. Admin Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/dtpadmin setvip` | `donateteleport.admin` | Set VIP teleport location |
| `/dtpadmin setpremium` | `donateteleport.admin` | Set Premium teleport location |
| `/dtpadmin info` | `donateteleport.admin` | Show current locations |
| `/dtpreload` | `donateteleport.admin` | Reload configuration |

### 7. Configuration Options

**config.yml options:**
- `cooldown: 30` - Seconds between teleports (0 to disable)
- Location coordinates for VIP and Premium areas
- World names for cross-world teleportation

**Tips:**
- Admins bypass cooldowns automatically
- Players can only see commands they have permission to use
- Home teleport uses player's bed spawn location
- All teleports respect world boundaries and safety

### 8. Troubleshooting

**Common Issues:**
1. **"Location not configured"** - Use `/dtpadmin setvip` or `/dtpadmin setpremium`
2. **"No permission"** - Check permission setup with your permissions plugin
3. **"World not found"** - Ensure world names in config match exactly
4. **Commands not working** - Check plugin loaded with `/plugins`

**Support:**
- Check server console for error messages
- Use `/dtpadmin info` to verify locations are set
- Test permissions with a test player account
- Reload config with `/dtpreload` after changes

### 9. Building from Source

If you need to build the plugin yourself:

1. **Requirements:**
   - Java 8+
   - Maven
   - Internet connection

2. **Steps:**
   ```bash
   git clone <repository>
   cd Donate-teleport
   cp pom-production.xml pom.xml  # Use production POM
   mvn clean package
   ```

3. **Result:**
   - Built JAR will be in `target/` directory
   - Use `donate-teleport-1.0.0.jar` in your plugins folder
# Ultralight Forge 1.8.9
An example of the [Ultralight](https://ultralig.ht/ "") HTML renderer in Minecraft Forge 1.8.9
A few general modding tips are also included 

## What works
- Rendering HTML
- Mouse input
- Keyboard input
- Java/JavaScript interop (implementation not shown)


## QuickStart
This example can be run out of the box. Simply

- Clone this repo
- Run the gradle task `runClient` 
- Join a world, any world
- Press right shift to open the demo GUI


## General Modding

### Audio
- `Step 1` Place your audio file under `assets/<modID>/sounds` in .ogg format

- `Step 2` Register your file in sounds.json with the following format
```json
  "whatever.name":{
  "category": "master",
  "sounds":[
    {
      "name":"<modID>:my_sound","stream":false
    }
   ]
  }
```
The above will look for the file `my_sound.ogg` under `assets/<modID>/sounds/` and make it accessible by `whatever.name`
The category is trivial.

- `Step 3` Play your sound file using the accessible name
```java
    Minecraft.getMinecraft().thePlayer.playSound("<modID>:whatever.name", volume, pitch);
```

#### Notes
- You can play vanilla minecraft sounds with example above. Simply replace `<modID>` with `minecraft`. A complete list of vanilla sounds can be found [here](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments "")
- It's good practice to name audio files using snake case



### Rendering Textures
Your image can be anywhere under `assets/<modID>`. Reference that image _relatively_ in the code like so
```java
    public static final ResourceLocation myImage = new ResourceLocation(MODID, path);
```
The above will look for an image at `assets/<modID>/path`. 
Drawing can then be done with the convenient RenderUtils class.
```java
RenderUtils.drawTexture(resourceLocation, x, y, width, height, rgbaCombined);
```
If you are drawing without a gui active (aka directly to the screen), make sure to fix your
x & y coordinates using the ScaledResolution class


## Gradle tasks
- `build` Builds the mod into a usable jar file under build/libs
- `runClient` Runs the mod in minecraft on the fly



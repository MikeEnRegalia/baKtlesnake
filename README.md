# Battlesnake Starter for Kotlin
This is a simple starter project containing the bare-bone Battlesnake API implementation. This starter is a great way to get started writing a Battlesnake in Kotlin! This starter makes use of Ktor, a Kotlin Web library designed for making APIs and websites, to name a few applications.  

To start, it is recommended that you first read the [Quick Start guide](https://docs.battlesnake.com/guides/getting-started) in the Battlesnake docs.
Then, you can open up Battlesnake.kt, and change some values that are marked with comments. Lastly, you can write your movement logic in Logic.kt.
Good luck with writing your first Battlesnake!

## Customizing your snake
You can customize your snake in `customizations.json`.
For example, if I wanted the `iguana` head and tail, and I would want a red snake, my `customizations.json` would look like this:
```json
{
    "color": "#FF0000",
    "head": "iguana",
    "tail": "iguana"
}
```

These changes will not take effect immediately, you have to click the "Reload" button next to your Battlesnake, inside the Battlesnake list.

## Changing logic
You can look inside the Logic.kt file, on how to get started adding logic to your snake!

## Running your snake
For convenience, this project uses the gradle `application` plugin to run your snake, to get started for testing.
Simply run `./gradlew run` to run your snake. IntelliJ Idea will also automatically generate run configurations, so you can use that as well.  

Eventually you might want to deploy to a server.
You can use `./gradlew build` to create a "fat jar", then run the artifact using `java -jar build\libs\YourNameHere-1.0.jar`
Alternatively, you can use the convenient distributions inside of `build/distributions`.
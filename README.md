# BeachBuddy_Android
A simple App that helps you have fun in the sun by monitoring the weather, manages a Beach List, and keeps track of a leader board. 

![GitHub Logo](/BeachBuddy_Screenshot.jpg)

This is a client used to help me use my first .NET Core app used to help me learn: 
- Backend Development (obviously) 
- Working with Databases
- Push Notifications w/ Firebase 
- Twilio Integration 
- Hitting external APIs
- HTML scrapping (not proud of it) 
- Caching
- Docker

Note: In order to compile, you will need to create a class called, `APIKeys.kt` at `src/main/java/com/andrewkingmarshall/beachbuddy/ApiKeys.kt`. Should look like this: 

```
package com.andrewkingmarshall.beachbuddy

const val AppSecretHeader: String = "<SOME_SECRET_HEADER>"
```

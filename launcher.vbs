Set WshShell = CreateObject("WScript.Shell")

' Set the folder where the .bat is located
WshShell.CurrentDirectory = "C:\Development\Java\cat-random-map-generator"

' Then run the .bat file
batPath = "launcher.bat"
WshShell.Run batPath, 0

Set WshShell = Nothing

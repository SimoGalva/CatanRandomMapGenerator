Set WshShell = CreateObject("WScript.Shell")

' Inserisci qui il percorso assoluto dove si trova il bat o il jar
batPath = """C:\GitRep\cat-random-map-generator\launcher.bat"""

' Lancia il bat senza mostrare la console
WshShell.Run batPath, 0

Set WshShell = Nothing

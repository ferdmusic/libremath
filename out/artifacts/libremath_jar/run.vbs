' Erstelle ein Objekt für das Dateisystem
Set objFSO = CreateObject("Scripting.FileSystemObject")

' Bestimme den Pfad zum aktuellen Verzeichnis, in dem sich das Skript befindet
strSkriptVerzeichnis = objFSO.GetParentFolderName(WScript.ScriptFullName)

' Erstelle den vollständigen Pfad zur JAR-Datei
strJarDatei = objFSO.BuildPath(strSkriptVerzeichnis, "libremath.jar")

' Überprüfe, ob die JAR-Datei existiert
If objFSO.FileExists(strJarDatei) Then
    ' Führe den Befehl aus, um die JAR-Datei auszuführen
    ' Hier ersetzt du "DeinMainClass" durch den Namen deiner Hauptklasse
    strCommand = "java -jar """ & strJarDatei & """ DeinMainClass"
    Set objShell = CreateObject("WScript.Shell")
    objShell.Run strCommand, 1, True
Else
    WScript.Echo "Die Datei libremath.jar wurde nicht im gleichen Ordner wie das Skript gefunden."
End If

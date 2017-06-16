# FPT-Übung1
#### Current version: Beta Release 1.0
## Beschreibung

Der Mediaplayer kann MP3 Dateien einlesen und abspielen, dazu werden diese in eine playlist geladen die alle songs enthält.
Von der Playlist "AllSongs" kann man Lieder einzeln oder alle Lieder in eine Warteschlange tun.

Die Warteschlange zeigt an welche Lieder gespielt werden sollen, an der ersten stelle steht der aktuell gespielte Song.

## Hauptansicht

Links befindet sich ein Menü welches zugriff auf Funktionalität bereitstellt.
Mittig werden alle Lieder bereitgestellt diese Liste dient als Library.
Rechts ist die aktuelle Warteschlange.
Unten kann man zwischen Play und Pause wechseln und genauso auch ein Song überspringen.

## Menü 
- Open Files: Über diesen Eintrag ist es möglich die Library und Warteschlange aus diversen Quellen zu beziehen. 
      Quelle sind: Local (auf dem Datenträger gespeicherte MP3 Dateien), XML (XML Serialisierte Dateien), Binär (Binär  
      Serialisierte Dateien), DataBase (Dateien aus einer Datenbank) und OpenJPA.
- Edit Song: Hier können details von einem Song verändert werden.
- Delete from Queue: Hier kann die Warteschlange entfernend manipuliert werden.
- Add ever song to queue: Fügt alle Lieder in der Library in die Warteschlange
- Save: Speichert Playlist oder Library ab.
      Hierzu kann zwischen Binärer und XML Serialisierung gewählt werden genauso wie Datenbank und OpenJPA

## Steuerelemente

Die Steuerelemente befinden sich auf der unteren Seite des Fensters

Zuerst sind zwei Buttons zu sehen.
- der erste Button dient als Play und Pause gleichzeitig, ein klicken wechselt den zustand des Media Players. Startzustand ist Pause.
- der Zweite Button dient als skip Button, ein klick bewirkt das überspringen des aktuellen Liedes.


- Zeit fortschrittsbalken (unter den Steuerelementen) angezeigt, dieser passt sich jedem Song neu an

## All Song ansicht

Hier befindet sich lediglich eine Anzeige aller geladenen Lieder und die Funktionalität alle hinzuzufügen.
Einzelne Lieder werden mit doppelklick hinzugefügt.

## Current Queue 

Hier befindet sich lediglich eine Anzeige aller Warteschlange.

## Detail ansicht

Wenn ein Cover vorhanden ist wird das Fenster breiter dargestellt da Rechts dann das Cover angezeigt wird.

Links zu sehen sind Titel, Album, Artist, ID, Pfad und Erscheinungsjahr des jeweilig ausgewählten Liedes.
Diese Metadaten sind bearbeitbar und werden sofort geändert da hier auf die Technik der SimpleObjectProperty gesetzt wurde.

## Sonstiges
- Main.java ermöglicht wurde ein parametisierter Start
      <br/>-R:/Users/user/Desktop/logfile.log => Umleitung alle System.err und System.out ausgaben in eine logdatei
      <br/>-RWARN:/Users/user/Desktop/logfile.log => Umleitung von System.out ausgaben in eine logdatei
      <br>-RCRIT:/Users/user/Desktop/logfile.log => Umleitung von System.err ausgaben in eine logdatei
- Util: 
      <br/> -Hex/String konverter
      <br/> convertToHex(String) => Konvertiert einen String in ein hexadezimalen string mit einem offset von 4
      <br/>       d.H: 0000 0001 0002 .... 00FF 00100 ... FFFF
      <br/> - Anzeige von Hinweisen
      <br/>     Es gibt zwei verschiedene einmal 
      <br/>       showAlert(String) zeigt ein hinweis fenster
      <br/>       showAlert(String, AlertType) zeigt ein hinweis mit entsprechenden Icon
      
      
## Aufgabenstellungen
### Aufgabenstellung 1

In unserer FPT Übung 1 sollen wir in Gruppen einen Mediaplayer erstellen dazu wurde ein Projekt mit 3 Interface zur verfügung gestellt

Aufgabe war es diese zu implementieren und dazugehörige Funktionalitäten hinzuzufüfen.

### Aufgabenstellung 2
In unserer FPT Übung 2 sollten wir als gruppe die erste Aufgabe erweitern um eine persistierungs möglichkeit, vorgabe war es ein IDGenerator zu gestalten mit entsprechender exception.
Außerdem wurde gefordert über XML und Binär zu serialisieren genauso mit einer Datenbank zu arbeiten und OpenJPA zu nutzen.

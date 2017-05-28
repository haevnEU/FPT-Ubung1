# FPT-Übung1
#### Current version: Beta Release 1.0
## Beschreibung

Der Mediaplayer kann MP3 Dateien einlesen und abspielen, dazu werden diese in eine playlist geladen die alle songs enthält.
Von der Playlist "AllSongs" kann man Lieder einzeln oder alle Lieder in eine Warteschlange tun.

Die Warteschlange zeigt an welche Lieder gespielt werden sollen, an der ersten stelle steht der aktuell gespielte Song.

## Hauptansicht

Links befindet sich die Warteschlange, hier ist es möglich mit alt + doppelklick lieder zu entfernen, mit doppelklick und gedrückter strg/ctrl ist es möglich details anzeigen zu lassen.
Der Button All Songs öffnet sie Liste aller geladenen Lieder
Im unteren fünftel befinden sich die Play/Pause, Skip und Seek funktionalitäten.

Der Restliche platz ist für die Anzeige des Titel, Artisten, Album und Cover reserviert, es wird immer mittig zentriert.

## Menü 
Wurde entfernt und durch Shortcuts ersetzt.

- Song details werden durc strg/ctrl Doppelklick auf das jeweilige lied geöffnet
- Alt + Doppelklick entfernt ein Lied aus der Warteschlange
- Alt + L öffnet den Lade dialog

## Steuerelemente

Die Steuerelemente befinden sich auf der unteren Seite des Fensters

Zuerst sind zwei Buttons zu sehen.
- der erste Button dient als Play und Pause gleichzeitig, ein klicken wechselt den zustand des Media Players. Startzustand ist Pause.
- der Zweite Button dient als skip Button, ein klick bewirkt das überspringen des aktuellen Liedes.


- Zeit fortschrittsbalken (unter den Steuerelementen) angezeigt, dieser passt sich jedem Song neu an

## All Song ansicht

Hier befindet sich lediglich eine Anzeige aller geladenen Lieder und die Funktionalität alle hinzuzufügen.
Einzelne Lieder werden mit doppelklick hinzugefügt.

## Detail ansicht

Wenn ein Cover vorhanden ist wird das Fenster breiter dargestellt da Rechts dann das Cover angezeigt wird.

Links zu sehen sind Titel, Album, Artist, ID, Pfad und Erscheinungsjahr des jeweilig ausgewählten Liedes.
Diese Metadaten sind bearbeitbar und werden sofort geändert da hier auf die Technik der SimpleObjectProperty gesetzt wurde.


### Aufgabenstellung

In unserer FPT Übung 1 sollen wir in Gruppen einen Mediaplayer erstellen dazu wurde ein Projekt mit 3 Interface zur verfügung gestellt

Aufgabe war es diese zu implementieren und dazugehörige Funktionalitäten hinzuzufüfen.

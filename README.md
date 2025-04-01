# Labbyk

**Der Koran in verschieden sprachen.**

Jederzeit offline in 90 verschiedenen Sprachen zum Rezitieren und Nachschlagen.

## Design

<div>
  <img src="./img/screen1.png" width="200">
  <img src="./img/screen2.png" width="200">
  <img src="./img/screen3.png" width="200">
  <img src="./img/screen4.png" width="200">
</div>


## Features
- [x] Daten werden verschlüsselt in der Cloud gespeichert und sind von überall zugänglich.
- [x] Übersichtliche Darstellung aller wichtigen Informationen.
- [x] Anlegen, verwalten von Kunden.
- [x] Erstellen und verwalten eines detaillierten Produktkatalogs.
- [x] Angebote mit wenigen Klicks erstellen.

## Technischer Aufbau

#### Projektaufbau
In diesem Projekt wurde die MVVM-Architektur angewendet, um die Trennung von View und Logik sicherzustellen. Die einzelnen Komponenten wie Views (inkl. SubViews), ViewModels, Models, Modifiers, Fonts, Manager, die API-Anbindung sowie Extensions wurden in separaten Ordnern strukturiert abgelegt.

Zur Sicherstellung eines einheitlichen Designs wurden die Farbdefinitionen zentral in den Assets hinterlegt.

#### Datenspeicherung
Alle vom Nutzer eingegebenen Daten werden im Firebase Firestore und Firebase Authentication (FireAuth) gespeichert.

Die Entscheidung für Firebase basiert auf den folgenden Vorteilen:

Echtzeit-Synchronisierung: Daten werden automatisch zwischen Client und Server synchronisiert.
Offline-Unterstützung: Nutzer können die Anwendung auch ohne Internetverbindung verwenden, und die Daten werden später synchronisiert.
Skalierbarkeit: Firebase bietet eine skalierbare Infrastruktur, die sich an die Anforderungen des Projekts anpassen lässt.

#### API Calls
In dieser App wird die Google API verwendet, insbesondere für die Adressüberprüfung beim Anlegen von Kunden. Die API wird genutzt, um die eingegebene Adresse zu validieren und sicherzustellen, dass diese korrekt und vollständig ist.

#### 3rd-Party Frameworks
Das PDFKit-Framework wird verwendet, um PDF-Dokumente zu generieren. Es dient unter anderem dazu, Angebote als PDF zu exportieren.


## Ausblick

- [ ] Rechnungserstellung
- [ ] Mitarbeiterverwaltung
- [ ] E-Mail-Versand für Angebote / Rechnungen
- [ ] Individuelle Vorlagen
- [ ] Android App

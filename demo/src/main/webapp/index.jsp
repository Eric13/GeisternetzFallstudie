<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:p="http://primefaces.org/ui">
<head>
    <title>Geisternetz Startseite</title>
    <style>
        /* Grundlayout */
           header {
            display: flex;
            align-items: center;
            padding: 10px;
            background-color: #f0f0f0;
        }

        header img {
            height: 80px;
            margin-right: 20px;
        }

        nav {
            margin-top: 10px;
            background-color: #333;
        }

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }

        nav li {
            margin-right: 10px;
        }

        nav a {
            display: block;
            padding: 10px 15px;
            color: white;
            text-decoration: none;
        }

        nav a:hover {
            background-color: #555;
        }

        main {
            padding: 20px;
        }
    </style>
</head>
<body>

    <!-- Header mit Logo, Adresse und Titel -->
    <header>
        
        <div>
            <h1>Geisternetz-Überwachung</h1>
            <p>Musterstraße 12, 12345 Musterstadt</p>
        </div>
    </header>

    <!-- Navigation -->
    <nav>
        <ul>
            <li><a href="startseite.xhtml">Startseite</a></li>
            <li><a href="netzMelden.xhtml">Netz melden</a></li>
            <li><a href="netzBergen.xhtml">Offene Netze</a></li>
            <li><a href="registrieren.xhtml">Registrieren / Login</a></li>
        </ul>
    </nav>
    <main>
        <h2>Willkommen bei der Geisternetz-Webanwendung!</h2>
        <p>Hier können Sie Geisternetze melden, die Bergung planen und den Status einsehen.
            Verwenden Sie die Navigation oben, um zu den verschiedenen Funktionen zu gelangen.
            Sie können sich als meldender Benutzer oder bergender Nutzer registrieren und anmelden.
        </p>

    
    </main>

</body>
</html>

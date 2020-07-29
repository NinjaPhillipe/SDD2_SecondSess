#Fonctionnement:
	3 librairies sont requise pour compiler et executer le projet.
		Le junit-4.13.jar
		hamcrest-core-1.3.jar
		javafx-sdk-11.0.2
	Les liens de telechargement sont disponible ci dessous

	Pour compiler le programme depuis les sources, un petit
	script bash est fourni permetant de compiler et de lancer le programme.
	
	Pour compiler:
		./build.sh compile 
	Pour lancer:
		./build.sh run 

	Pour lancer les tests ./build.sh test

	Eventuellement vous pouvez precisez votre propre path pour la librairie
	javaFX en deuxieme argument. (javaFX_version/lib)
	
	Par defaut le jdk se trouve a la racine du dossier c'est a dire
	PATH_TO_DIRECTORY/javafx-sdk-11.0.2/lib

lien vers le sdk utiliser
	 http://gluonhq.com/download/javafx-11-0-2-sdk-linux/

Il a juste besoin d'etre placer a la racine du projet


SI on mets xend plus petit que xstart alors on mets xend=xstart

https://github.com/junit-team/junit4/wiki/Download-and-Install

https://repo1.maven.org/maven2/junit/junit/4.13/junit-4.13.jar
https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

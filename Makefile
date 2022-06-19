JAVAC   =       /usr/bin/javac
SRCDIR  =src
BINDIR  =bin
DOCDIR  =doc
.SUFFIXES:      .java .class
default:
	$(JAVAC) -d	$(BINDIR) $(SRCDIR)/*.java $<; java -cp $(BINDIR) Sever
startchat:
	java -cp $(BINDIR) HandleChatWindow         $(s)    $(d)    $(t)
clean:
	rm $(BINDIR)/*.class
doc:
	javadoc -cp bin/ -d doc/ src/*.java

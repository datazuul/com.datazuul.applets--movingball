package com.datazuul.applets.games.movingball;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Ralf Eichinger
 * @see http://www.java-tutorial.net/BildschirmflackernEng.html
 * @see http://www.javacooperation.gmxhome.de/BildschirmflackernEng.html
 */
public class MovingBallDoubleBuffered extends Applet implements Runnable {
  // Initialisierung der Variablen

  int x_pos = 10; // x - Position des Balles
  int y_pos = 100; // y - Position des Balles
  int radius = 20; // Radius des Balles

  // Variablen für die Doppelpufferung
  private Image dbImage;
  private Graphics dbg;

  public void init() {
    setBackground(Color.blue);
  }

  public void start() {
    // Schaffen eines neuen Threads, in dem das Spiel läuft
    Thread th = new Thread(this);
    // Starten des Threads
    th.start();
  }

  public void stop() {

  }

  public void destroy() {

  }

  public void run() {
    // Erniedrigen der ThreadPriority um zeichnen zu erleichtern
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

    // Solange true ist läuft der Thread weiter
    while (true) {
      // Verändern der x- Koordinate
      x_pos++;

      // Neuzeichnen des Applets
      repaint();

      try {
	// Stoppen des Threads für in Klammern angegebene Millisekunden
	Thread.sleep(20);
      } catch (InterruptedException ex) {
	// do nothing
      }

      // Zurücksetzen der ThreadPriority auf Maximalwert
      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }
  }

  /**
   * Update - Methode, Realisierung der Doppelpufferung zur Reduzierung des Bildschirmflackerns
   */
  public void update(Graphics g) {
    // Initialisierung des DoubleBuffers
    if (dbImage == null) {
      dbImage = createImage(this.getSize().width, this.getSize().height);
      dbg = dbImage.getGraphics();
    }

    // Bildschirm im Hintergrund löschen
    dbg.setColor(getBackground());
    dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

    // Auf gelöschten Hintergrund Vordergrund zeichnen
    dbg.setColor(getForeground());
    paint(dbg);

    // Nun fertig gezeichnetes Bild Offscreen auf dem richtigen Bildschirm
    // anzeigen
    g.drawImage(dbImage, 0, 0, this);
  }

  public void paint(Graphics g) {
    g.setColor(Color.red);

    g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
  }
}

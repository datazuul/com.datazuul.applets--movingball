package com.datazuul.applets.basics.events;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Ralf Eichinger
 *
 */
public class MouseKeyListener extends Applet implements Runnable, KeyListener,
	MouseListener, MouseMotionListener {

  private int speed;

  int currentLine;

  // Spielthread
  private Thread th;

  // Variablen für die Doppelpufferung
  private Image dbImage;
  private Graphics dbg;

  public void init() {
    setBackground(Color.gray);
    currentLine = 10;
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void start() {
    // Schaffen eines neuen Threads, in dem das Spiel läuft
    th = new Thread(this);
    // Starten des Threads
    th.start();
  }

  public void stop() {
    // Thread stoppen
    th.stop();
    // Thread auf null setzen
    th = null;
  }

  public void keyPressed(KeyEvent e) {
    getGraphics().drawString("User pressed key " + e.getKeyChar(), 10,
	    currentLine);
    currentLine += 20;
  }

  public void keyReleased(KeyEvent e) {
    getGraphics().drawString("User released key " + e.getKeyChar(), 10,
	    currentLine);
    currentLine += 20;
  }

  public void keyTyped(KeyEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    getGraphics().drawString(
	    "User clicked mouse " + e.getClickCount() + " times!", 10,
	    currentLine);
    currentLine += 20;
  }

  public void mouseEntered(MouseEvent e) {
    getGraphics().drawString(
	    "Mouse entered applet at " + e.getX() + " " + e.getY(), 10,
	    currentLine);
    currentLine += 20;
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseDragged(MouseEvent e) {
  }

  public void run() {
    // Erniedrigen der ThreadPriority um zeichnen zu erleichtern
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

    // Solange true ist läuft der Thread weiter
    while (true) {
      try {
	paint(getGraphics());

	// Stoppen des Threads für in Klammern angegebene Millisekunden
	Thread.sleep(speed);
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
    // no double buffering
  }

  public void paint(Graphics g) {
    if (currentLine > 380) {
      g.setColor(Color.blue);
      g.fillRect(0, 0, 300, 300);
      currentLine = 10;
    }
    g.setColor(Color.yellow);
  }
}

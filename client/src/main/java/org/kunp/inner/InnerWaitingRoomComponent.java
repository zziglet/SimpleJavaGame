package org.kunp.inner;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.swing.*;

public class InnerWaitingRoomComponent extends JPanel {

  public InnerWaitingRoomComponent(
          Set<String> sessionIds,
          String roomName,
          BufferedReader in,
          PrintWriter out,
          String sessionId) {

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createLineBorder(Color.GRAY));
    setPreferredSize(new Dimension(350, 200));

    JLabel nameLabel = new JLabel("게임 룸");
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(nameLabel, BorderLayout.NORTH);

    // 사용자 목록 패널 추가
    InnerWaitingRoomListPanel listPanel = new InnerWaitingRoomListPanel(sessionIds);
    add(listPanel, BorderLayout.CENTER);

    // 컨트롤 패널 추가
    InnerWaitingRoomControlPanel controlPanel =
            new InnerWaitingRoomControlPanel(roomName, in, out, sessionId);
    add(controlPanel, BorderLayout.SOUTH);



    Thread thread = new Thread(() -> {
      sessionIds.clear();
      try {
        String message;
        while ((message = in.readLine()) != null) {
          System.out.println(message);
          String[] tokens = message.split("\\|");
          sessionIds.add(tokens[1]);
          listPanel.updateSessionList(sessionIds);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }
}

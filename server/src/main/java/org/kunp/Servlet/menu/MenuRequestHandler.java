package org.kunp.Servlet.menu;

import org.kunp.Servlet.message.MenuMessage;
import org.kunp.Servlet.session.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MenuRequestHandler {
  private static MenuRequestHandler menuRequestHandler;
  private final WaitingRoomRegistry waitingRoomRegistry = WaitingRoomRegistry.getInstance();

  private MenuRequestHandler() {
  }

  public static MenuRequestHandler getInstance() {
    if (menuRequestHandler == null) {
      menuRequestHandler = new MenuRequestHandler();
    }
    return menuRequestHandler;
  }

  public void handleMenuRequest(Session session, MenuMessage message) throws IOException {
    //100번 : 대기방 조회
    //101번 : 대기방 입장
    //102번 : 대기방 생성
    //103번 : 대기방 퇴장
    switch (message.getType()) {
      case 100:
        System.out.println("대기방 조회");
        System.out.println(waitingRoomRegistry.getWaitingRooms());
        OutputStream outputStream = (OutputStream) session.getAttributes().get("ops");
        outputStream.write(String.format("105|%s\n", waitingRoomRegistry.getWaitingRooms()).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        System.out.println("send!");
        break;
      case 101:
        System.out.println("대기방 입장");
        waitingRoomRegistry.enterWaitingRoom(session, message.getRoomName());
        break;
      case 102:
        System.out.println("대기방 생성");
        waitingRoomRegistry.createWaitingRoom(message.getRoomName());
        break;
      case 103:
        System.out.println("대기방 퇴장");
        waitingRoomRegistry.leaveWaitingRoom(session, message.getRoomName());
        break;
    }

  }
}

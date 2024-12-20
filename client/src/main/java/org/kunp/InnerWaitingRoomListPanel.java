package org.kunp;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class InnerWaitingRoomListPanel extends JPanel {
    private JPanel gridPanel;

    public InnerWaitingRoomListPanel(Set<String> sessionIds) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(350, 150));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2열 그리드 레이아웃
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        updateSessionList(sessionIds);
    }

    public void updateSessionList(Set<String> sessionIds) {
        gridPanel.removeAll(); // 기존 컴포넌트 제거

        // 짝수 인원만 추가
        if (sessionIds.size() % 2 != 0) {
            sessionIds.add("대기 중"); // 임시로 빈 자리 추가
        }

        for (String id : sessionIds) {
            JPanel userPanel = new JPanel();
            userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            userPanel.setLayout(new GridBagLayout()); // 중앙 정렬을 위해 GridBagLayout 사용

            JLabel label = new JLabel(id);
            label.setFont(new Font("Arial", Font.BOLD, 16)); // 글꼴 설정 (크기 및 스타일)
            label.setHorizontalAlignment(SwingConstants.CENTER); // 중앙 정렬

            userPanel.add(label); // 패널에 라벨 추가
            gridPanel.add(userPanel);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}



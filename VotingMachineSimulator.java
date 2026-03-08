import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VotingMachineSimulator extends JFrame {
    private int votesA;
    private int votesB;
    private int votesC;

    private final JLabel lblVotesA;
    private final JLabel lblVotesB;
    private final JLabel lblVotesC;
    private final JLabel lblTotalVotes;

    public VotingMachineSimulator() {
        setTitle("Симулятор электронного голосования");
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(16, 16));

        Color appBackground = new Color(17, 24, 39);
        getContentPane().setBackground(appBackground);

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setBackground(new Color(31, 41, 55));
        headingPanel.setBorder(new EmptyBorder(22, 26, 22, 26));

        JLabel heading = new JLabel("🗳️ Панель электронного голосования", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(243, 244, 246));

        JLabel subtitle = new JLabel("Выберите кандидата и отслеживайте результаты в реальном времени", JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(156, 163, 175));

        headingPanel.add(heading, BorderLayout.CENTER);
        headingPanel.add(subtitle, BorderLayout.SOUTH);
        add(headingPanel, BorderLayout.NORTH);

        JPanel votePanel = new JPanel(new GridLayout(1, 3, 18, 18));
        votePanel.setBackground(appBackground);
        votePanel.setBorder(new EmptyBorder(0, 18, 0, 18));

        votePanel.add(createCandidateCard("Кандидат А", new Color(59, 130, 246), () -> votesA++));
        votePanel.add(createCandidateCard("Кандидат B", new Color(16, 185, 129), () -> votesB++));
        votePanel.add(createCandidateCard("Кандидат C", new Color(239, 68, 68), () -> votesC++));

        add(votePanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        resultPanel.setBorder(new EmptyBorder(12, 18, 18, 18));
        resultPanel.setBackground(appBackground);

        lblVotesA = createResultBox("Голоса за А", "0");
        lblVotesB = createResultBox("Голоса за B", "0");
        lblVotesC = createResultBox("Голоса за C", "0");
        lblTotalVotes = createResultBox("Всего голосов", "0");

        resultPanel.add(wrapBox(lblVotesA));
        resultPanel.add(wrapBox(lblVotesB));
        resultPanel.add(wrapBox(lblVotesC));
        resultPanel.add(wrapBox(lblTotalVotes));

        add(resultPanel, BorderLayout.SOUTH);
    }

    private JPanel createCandidateCard(String candidateName, Color accent, Runnable onVote) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(new Color(31, 41, 55));
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel nameLabel = new JLabel(candidateName, JLabel.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(new Color(229, 231, 235));

        JLabel hint = new JLabel("Нажмите, чтобы отдать голос", JLabel.CENTER);
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hint.setForeground(new Color(156, 163, 175));

        JButton voteButton = new JButton("Голосовать");
        voteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        voteButton.setBackground(accent);
        voteButton.setForeground(Color.WHITE);
        voteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        voteButton.setFocusPainted(false);
        voteButton.addActionListener(e -> {
            onVote.run();
            updateResults();
        });

        JPanel center = new JPanel(new GridLayout(2, 1));
        center.setOpaque(false);
        center.add(nameLabel);
        center.add(hint);

        card.add(center, BorderLayout.CENTER);
        card.add(voteButton, BorderLayout.SOUTH);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 2),
                new EmptyBorder(14, 14, 14, 14)
        ));

        return card;
    }

    private JLabel createResultBox(String title, String value) {
        JLabel label = new JLabel("<html><div style='text-align:center;'><span style='color:#9CA3AF;'>" + title
                + "</span><br><span style='font-size:22px; color:#F9FAFB;'><b>" + value + "</b></span></div></html>", JLabel.CENTER);
        label.setOpaque(false);
        return label;
    }

    private JPanel wrapBox(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 41, 55));
        panel.setBorder(new EmptyBorder(10, 8, 10, 8));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void updateResults() {
        int totalVotes = votesA + votesB + votesC;
        lblVotesA.setText(resultHtml("Голоса за А", votesA));
        lblVotesB.setText(resultHtml("Голоса за B", votesB));
        lblVotesC.setText(resultHtml("Голоса за C", votesC));
        lblTotalVotes.setText(resultHtml("Всего голосов", totalVotes));
    }

    private String resultHtml(String title, int value) {
        return "<html><div style='text-align:center;'><span style='color:#9CA3AF;'>" + title
                + "</span><br><span style='font-size:22px; color:#F9FAFB;'><b>" + value + "</b></span></div></html>";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VotingMachineSimulator().setVisible(true));
    }
}

package demo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

public class SmartHomeGUI extends JFrame {
    private jiadian[] devices;
    private smartname controller;
    private JLabel[] statusLabels;
    private JButton[] controlButtons;
    private JPanel mainPanel;
    
    // 现代简约配色方案
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);     // Google蓝
    private static final Color SUCCESS_COLOR = new Color(52, 168, 83);      // 绿色
    private static final Color DANGER_COLOR = new Color(234, 67, 53);       // 红色
    private static final Color WARNING_COLOR = new Color(251, 188, 5);      // 黄色
    private static final Color BG_COLOR = new Color(250, 250, 250);         // 浅灰背景
    private static final Color CARD_BG = Color.WHITE;                        // 卡片白色
    private static final Color TEXT_PRIMARY = new Color(32, 33, 36);        // 深灰文字
    private static final Color TEXT_SECONDARY = new Color(95, 99, 104);     // 浅灰文字
    private static final Color BORDER_COLOR = new Color(218, 220, 224);     // 边框灰
    
    public SmartHomeGUI() {
        // 加载设备数据
        devices = DataManager.loadData();
        if (devices == null) {
            devices = new jiadian[4];
            devices[0] = new tv("小米电视", true);
            devices[1] = new lamp("LED灯泡", true);
            devices[2] = new washma("美的洗衣机", false);
            devices[3] = new air("美的空调", false);
        }
        
        controller = smartname.getInstance();
        
        // 初始化界面
        initUI();
        
        // 窗口关闭时保存数据
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveData(devices);
                System.exit(0);
            }
        });
    }
    
    private void initUI() {
        setTitle("智能家居控制系统");
        setSize(750, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // 主面板 - 简约白色背景
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(BG_COLOR);
        
        // 标题区域
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        JLabel titleLabel = new JLabel("🏠 智能家居控制系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // 设备列表面板
        JPanel devicePanel = new JPanel();
        devicePanel.setLayout(new GridLayout(4, 1, 0, 12));
        devicePanel.setBackground(BG_COLOR);
        devicePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        statusLabels = new JLabel[4];
        controlButtons = new JButton[4];
        
        String[] icons = {"📺", "💡", "🧺", "❄️"};
        
        for (int i = 0; i < 4; i++) {
            JPanel deviceRow = createDeviceRow(i, icons[i]);
            devicePanel.add(deviceRow);
        }
        
        mainPanel.add(devicePanel, BorderLayout.CENTER);
        
        // 底部按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JButton refreshButton = createModernButton("🔄 刷新", PRIMARY_COLOR);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatusDisplay();
            }
        });
        
        JButton saveButton = createModernButton("💾 保存", SUCCESS_COLOR);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManager.saveData(devices);
                showNotification("状态已保存", SUCCESS_COLOR);
            }
        });
        
        JButton exitButton = createModernButton("🚪 退出", DANGER_COLOR);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    SmartHomeGUI.this,
                    "确定要退出吗？",
                    "确认退出",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    DataManager.saveData(devices);
                    System.exit(0);
                }
            }
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // 初始更新状态显示
        updateStatusDisplay();
    }
    
    private JPanel createDeviceRow(final int index, String icon) {
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout(15, 0));
        row.setBackground(CARD_BG);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // 左侧：图标和名称
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftPanel.setBackground(CARD_BG);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        
        JLabel nameLabel = new JLabel(devices[index].getName());
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_PRIMARY);
        
        leftPanel.add(iconLabel);
        leftPanel.add(nameLabel);
        
        // 中间：状态标签（与按钮文字一致）
        statusLabels[index] = new JLabel("开启", SwingConstants.CENTER);
        statusLabels[index].setFont(new Font("微软雅黑", Font.PLAIN, 14));
        statusLabels[index].setPreferredSize(new Dimension(80, 30));
        statusLabels[index].setOpaque(false);
        
        // 右侧：控制按钮
        controlButtons[index] = createModernButton("关闭", DANGER_COLOR);
        controlButtons[index].setPreferredSize(new Dimension(90, 32));
        controlButtons[index].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlDevice(index);
            }
        });
        
        row.add(leftPanel, BorderLayout.WEST);
        row.add(statusLabels[index], BorderLayout.CENTER);
        row.add(controlButtons[index], BorderLayout.EAST);
        
        return row;
    }
    
    private void controlDevice(int index) {
        // 执行控制操作
        controller.control(devices[index]);
        
        // 更新界面显示
        updateStatusDisplay();
        
        // 显示操作提示
        String deviceName = devices[index].getName();
        String status = devices[index].isStatus() ? "已开启" : "已关闭";
        Color statusColor = devices[index].isStatus() ? SUCCESS_COLOR : DANGER_COLOR;
        showNotification(deviceName + " " + status, statusColor);
    }
    
    private void updateStatusDisplay() {
        for (int i = 0; i < devices.length; i++) {
            boolean isOn = devices[i].isStatus();
            
            // 更新状态标签（与按钮文字一致）
            if (isOn) {
                statusLabels[i].setText("开启");
                statusLabels[i].setForeground(SUCCESS_COLOR);
                controlButtons[i].setText("关闭");
                updateButtonStyle(controlButtons[i], DANGER_COLOR);
            } else {
                statusLabels[i].setText("关闭");
                statusLabels[i].setForeground(DANGER_COLOR);
                controlButtons[i].setText("开启");
                updateButtonStyle(controlButtons[i], SUCCESS_COLOR);
            }
        }
        
        // 刷新面板
        mainPanel.repaint();
    }
    
    /**
     * 创建现代简约风格按钮
     */
    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        button.setForeground(color);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 10));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    /**
     * 更新按钮样式
     */
    private void updateButtonStyle(JButton button, Color color) {
        button.putClientProperty("color", color);
        button.repaint();
    }
    
    /**
     * 显示简约通知
     */
    private void showNotification(String message, Color color) {
        JOptionPane.showMessageDialog(this,
            message,
            "提示",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        // 使用系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 在事件调度线程中创建和显示GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SmartHomeGUI().setVisible(true);
            }
        });
    }
}

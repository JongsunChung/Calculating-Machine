package calculating_machine;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Kim Hee Jin (hjkim010509@gmail.com)
 * 
 * @version 1.0
 * 
 * @created 2024-11-01
 * @lastModified 2024-11-01
 * 
 * @see ChatGPT : 코드 참조
 * 		계산기아이콘.png : 위키백과(https://ko.wikipedia.org/wiki/%ED%8C%8C%EC%9D%BC:Windows_Calculator_icon.png)
 * 
 * */


/**
 * JButton의 모양을 바꾸는 클래스입니다.
 * 
 * @created 2024-11-01
 * @lastModified 2024-11-01
 * */
class RoundedButton extends JButton {
    private int radius; 				 // 모서리 둥글기

    public RoundedButton(String label, int radius) {
        super(label);
        this.radius = radius;
        setContentAreaFilled(false);	 // 기본 배경 색상 비활성화
        setFocusPainted(false);			 // 포커스 효과 비활성화
        setOpaque(false);				 // 버튼의 배경을 투명하게 설정
    }

    @Override
    protected void paintComponent(Graphics g) {	// 둥글게 그리기
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        super.paintComponent(g); 			// 버튼 텍스트 그리기
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.lightGray); 		// 테두리 색상
        g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius); // 둥근 테두리 그리기
    }

    @Override
    public void setBorder(Border border) {
        // 기본 테두리 설정 무시
    }
}


/**
 * GUI 프로그램을 이용하여 구현한 계산기 클래스입니다.
 * 
 * @created 2024-11-01
 * @lastModified 2024-11-01
 * */
public class Calculate extends JFrame implements ActionListener {
	
    // 컴포넌트 선언
    private JTextArea textarea, historyarea, extraarea;
    
    private RoundedButton[] numberButtons; 
    private RoundedButton addButton, subButton, mulButton, divButton, equalButton, clearButton, 
    					  remainButton, cleareraserButton, eraserButton, fountainButton, squareButton,
    					  logButton, plusminusButton, dotButton;
    
    private JPanel epanel, bpanel;

    private double num1, num2, result;
    private char operator;

    public Calculate() {
    	
        // 프레임 설정
        setTitle("Calculator");
        ImageIcon icon = new ImageIcon("src/calculating_machine/계산기아이콘.png");
        setIconImage(icon.getImage()); // 아이콘

        setLayout(new BorderLayout(10, 10));

        showNorth();
        showCenter();

        setSize(520, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
	/**
	 * 계산기 버튼을 눌렀을 때 발생하는 이벤트를 관리하는 함수입니다.
	 * 
	 * @created 2024-11-01
	 * @lastModified 2024-11-01
	 * */
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	// 숫자 버튼
        for (int i = 0; i < numberButtons.length; i++) {
            if (e.getSource() == numberButtons[i]) {
                textarea.setText(textarea.getText().concat(String.valueOf(i)));
            }
        }
        
        // 나머지 연산자
        if (e.getSource() == remainButton) {
            num1 = Double.parseDouble(textarea.getText());
            operator = '%';
            textarea.setText("");
        }
        
        // 분수로 전환
        if (e.getSource() == fountainButton) {
            num1 = Double.parseDouble(textarea.getText());
            textarea.setText("");
            result = 1 / num1;
            textarea.setText(String.valueOf(result));
        }
        
        // 제곱
        if (e.getSource() == squareButton) {
            num1 = Double.parseDouble(textarea.getText());
            textarea.setText("");
            double result = Math.pow(num1, 2);
            textarea.setText(String.valueOf(result));
        }
        
        // 제곱근
        if (e.getSource() == logButton) {
            num1 = Double.parseDouble(textarea.getText());
            textarea.setText("");
            double result = Math.sqrt(num1);
            textarea.setText(String.valueOf(result));
        }
        
        // 양수, 음수 전환
        if (e.getSource() == plusminusButton) {
            String currentText = textarea.getText();
            if (!currentText.isEmpty()) {
                if (currentText.startsWith("-")) {
                    currentText = currentText.substring(1);
                } else {
                    currentText = "-" + currentText;
                }
                textarea.setText(currentText);
            }
        }
        
        // 소수점 찍기
        if (e.getSource() == dotButton) {
        	String currentText = textarea.getText();
        	if (currentText.isEmpty()) {		// 현재 텍스트가 비워져 있으면 앞에 0을 추가
        		currentText = "0.";
        		textarea.setText(currentText);
        	} else if (currentText.contains(".")) {	// 현재 텍스트에 '.'이 포함되어 있으면 추가 안함
        		currentText = currentText;
        		textarea.setText(currentText);
        	} else {
        		currentText = currentText + "." ;
        		textarea.setText(currentText);
        	}
        }
        
        // 더하기
        if (e.getSource() == addButton) {
            num1 = Double.parseDouble(textarea.getText());
            operator = '+';
            textarea.setText("");
        }
        
        // 빼기
        if (e.getSource() == subButton) {
            num1 = Double.parseDouble(textarea.getText());
            operator = '-';
            textarea.setText("");
        }
        
        // 곱하기
        if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(textarea.getText());
            operator = '*';
            textarea.setText("");
        }
        
        // 나누기
        if (e.getSource() == divButton) {
            num1 = Double.parseDouble(textarea.getText());
            operator = '/';
            textarea.setText("");
        }
        
        // 등호
        if (e.getSource() == equalButton) {
            num2 = Double.parseDouble(textarea.getText());
            switch (operator) {
                case '%':
                    result = num1 % num2;
                    break;
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
            }
            textarea.setText(String.valueOf(result));
        }
        
        // C 버튼
        if (e.getSource() == clearButton) {
            textarea.setText("");
        }
        
        // CE버튼
        if (e.getSource() == cleareraserButton) {
            textarea.setText("0");
        }
        
        // E버튼
        if (e.getSource() == eraserButton) {
            String currentText = textarea.getText();
            if (!currentText.isEmpty()) {
                currentText = currentText.substring(0, currentText.length() - 1);	// 마지막 문자를 제거
                textarea.setText(currentText);
            }
        }
    }

    
    /**
     * 윗 쪽 패널, 입력과 결과를 보여주는 창
     * 
     * @created 2024-11-01
     * @lastModified 2024-11-01
     * */
    void showNorth() {
    	// 패널 생성
        epanel = new JPanel(new GridLayout(3, 0));
        
        // GUI 배경색 가져옴
        Color defaultBackgroundColor = UIManager.getColor("Panel.background");
        
        // 여백을 위한 영역
        extraarea = new JTextArea(1, 12);
        extraarea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        extraarea.setEditable(false);
        
        extraarea.setBackground(defaultBackgroundColor);
        
        // 식 입력창(구현안됨)
        historyarea = new JTextArea(1, 12);
        historyarea.setFont(new Font("Arial", Font.PLAIN, 18));
        historyarea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        historyarea.setEditable(false);
        historyarea.setForeground(Color.gray);
        
        historyarea.setText("");
        
        historyarea.setBackground(defaultBackgroundColor);
        
        // 숫자 입력창
        textarea = new JTextArea(1, 12);
        textarea.setFont(new Font("Arial", Font.PLAIN, 44));
        textarea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textarea.setEditable(false);
        textarea.setForeground(Color.black);
        
        textarea.setBackground(defaultBackgroundColor);
        
        
        // 패널 추가
        epanel.add(extraarea);
        epanel.add(historyarea);
        epanel.add(textarea);
        
        add(epanel, BorderLayout.NORTH);
    }

    
    /**
     * 밑 쪽 패널, 버튼 생성 및 추가 함수
     * 
     * @created 2024-11-01
     * @lastModified 2024-11-01
     * */
    void showCenter() {
        // 숫자 버튼 생성
        numberButtons = new RoundedButton[10]; // 둥근 버튼 배열 초기화
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new RoundedButton(String.valueOf(i), 10); // 반지름을 10픽셀로 설정
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            numberButtons[i].setBackground(Color.WHITE); // 버튼 배경색

            numberButtons[i].setMargin(new Insets(10, 10, 10, 10));
            numberButtons[i].addActionListener(this);
        }
        
        // 버튼 생성
        remainButton = new RoundedButton("%", 10);
        cleareraserButton = new RoundedButton("CE", 10);
        clearButton = new RoundedButton("C", 10);
        eraserButton = new RoundedButton("E", 10);
        
        fountainButton = new RoundedButton("1/x", 10);
        squareButton = new RoundedButton("x²", 10);
        logButton = new RoundedButton("²√x", 10);
        divButton = new RoundedButton("/", 10);
        
        mulButton = new RoundedButton("*", 10);
        subButton = new RoundedButton("-", 10);
        addButton = new RoundedButton("+", 10);
        
        plusminusButton = new RoundedButton("+/-", 10);
        dotButton = new RoundedButton(".", 10);
        equalButton = new RoundedButton("=", 10);

        
        // 액션리스러 설정
        remainButton.addActionListener(this);
        cleareraserButton.addActionListener(this);
        clearButton.addActionListener(this);
        eraserButton.addActionListener(this);
        
        fountainButton.addActionListener(this);
        squareButton.addActionListener(this);
        logButton.addActionListener(this);
        divButton.addActionListener(this);
        
        mulButton.addActionListener(this);
        subButton.addActionListener(this);
        addButton.addActionListener(this);
        equalButton.addActionListener(this);
        
        plusminusButton.addActionListener(this);
        dotButton.addActionListener(this);

        
        // 버튼 폰트 설정
        remainButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cleareraserButton.setFont(new Font("Arial", Font.PLAIN, 18));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18));
        eraserButton.setFont(new Font("Arial", Font.PLAIN, 18));
        
        fountainButton.setFont(new Font("Arial", Font.PLAIN, 18));
        squareButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logButton.setFont(new Font("Arial", Font.PLAIN, 18));
        divButton.setFont(new Font("Arial", Font.PLAIN, 18));
        
        mulButton.setFont(new Font("Arial", Font.PLAIN, 18));
        subButton.setFont(new Font("Arial", Font.PLAIN, 18));
        addButton.setFont(new Font("Arial", Font.PLAIN, 18));

        plusminusButton.setFont(new Font("Arial", Font.PLAIN, 18));
        dotButton.setFont(new Font("Arial", Font.PLAIN, 18));
        equalButton.setFont(new Font("Arial" ,Font.PLAIN, 18));
        // '='버튼 배경색 변경
        equalButton.setBackground(new Color(0, 120, 212));
        // '='폰트 색 변경
        equalButton.setForeground(Color.white);

        
        // 패널 설정
        bpanel = new JPanel();
        bpanel.setLayout(new GridLayout(6, 4, 5, 5));
        bpanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        
        // 버튼 추가
        bpanel.add(remainButton);
        bpanel.add(cleareraserButton);
        bpanel.add(clearButton);
        bpanel.add(eraserButton);

        bpanel.add(fountainButton);
        bpanel.add(squareButton);
        bpanel.add(logButton);
        bpanel.add(divButton);

        bpanel.add(numberButtons[7]);
        bpanel.add(numberButtons[8]);
        bpanel.add(numberButtons[9]);
        bpanel.add(mulButton);
                
        bpanel.add(numberButtons[4]);
        bpanel.add(numberButtons[5]);
        bpanel.add(numberButtons[6]);
        bpanel.add(subButton);
        
        bpanel.add(numberButtons[1]);
        bpanel.add(numberButtons[2]);
        bpanel.add(numberButtons[3]);
        bpanel.add(addButton);
        
        bpanel.add(plusminusButton);
        bpanel.add(numberButtons[0]);
        bpanel.add(dotButton);
        bpanel.add(equalButton);
        
        
        // 패널 추가
        add(bpanel, BorderLayout.CENTER);
    }

    /**
     * 실행 함수
     * 
     * @created 2024-11-01
     * @lastModified 2024-11-01
     * */
    public static void main(String[] args) {
        new Calculate();
    }
}
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Player{
    public final static int type_human = 0;
    public final static int type_computer = 1;
    public final static int tactics_0 = 0;
    public final static int tactics_1 = 1;
    public final static int tactics_2 = 2;
    
    private int color;
    private int type;
    private int tactics;

    Player(int c, int t, int tact){
        if(c == Stone.black || c == Stone.white)
            color = c;
        else{
            System.out.println("プレイヤーの石は黒か白でなければいけません:" + c);
            System.exit(0);
        }
        if(t == type_human || t == type_computer)
            type = t;
        else{
            System.out.println("プレイヤーは人間かコンピュータでなければいけません:" + t);
            System.exit(0);
        }
        if(tact == tactics_0 || tact == tactics_1 || tact == tactics_2)
            tactics = tact;
        else{
            System.out.println("作戦0,1,2から選んでください:" + tact);
            System.exit(0);
        }
    }

    int getColor(){ return color; }

    int getType(){ return type; }

    Point tactics(Board bd){
        if(tactics == tactics_0){
            int[] gp_x = new int[60];
            int[] gp_y = new int[60];
            int n = 0;
            if(color == Stone.black){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_black[i][j] > 0){
                            gp_x[n] = i;
                            gp_y[n] = j;
                            n++;
                        }
                    }
                }
                Random random = new Random();
                if(n>1){
                    int rand = random.nextInt(n);
                    return (new Point(gp_x[rand], gp_y[rand]));
                }else{
                    return (new Point(gp_x[0], gp_y[0]));
                }
            }else if(color == Stone.white){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_white[i][j] > 0){
                            gp_x[n] = i;
                            gp_y[n] = j;
                            n++;
                        }
                    }
                }
                Random random = new Random();
                if(n>1){
                    int rand = random.nextInt(n);
                    return (new Point(gp_x[rand], gp_y[rand]));
                }else{
                    return (new Point(gp_x[0], gp_y[0]));
                }
            }
            return (new Point(-1, -1));
        }else if(tactics == tactics_1){
            if(color == Stone.black){
                int max = 0;
                int idx_x = -1;
                int idx_y = -1;
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_black[i][j] > max){
                            max = bd.eval_black[i][j];
                            idx_x = i;
                            idx_y = j;
                        }
                    }
                }
                return new Point(idx_x, idx_y);
            }else if(color == Stone.white){
                int max = 0;
                int idx_x = -1;
                int idx_y = -1;
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_white[i][j] > max){
                            max = bd.eval_white[i][j];
                            idx_x = i;
                            idx_y = j;
                        }
                    }
                }
                return (new Point(idx_x, idx_y));
            }
            return (new Point(-1, -1));
        }else if(tactics == tactics_2){
            int max = 0;
            int max_edge = 0;
            Point p = new Point();
            Point p_opt = new Point(-1, -1);

            if(color == Stone.black){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_black[i][j] > 0){
                            if(i % 7 == 0 && j % 7 == 0){
                                return (new Point(i, j));
                            }else if(i % 7 == 0 || j % 7 == 0){
                                if(bd.eval_black[i][j] > max_edge){
                                    max_edge = bd.eval_black[i][j];
                                    p_opt = new Point(i, j);
                                }
                            }
                            if(bd.eval_black[i][j] > max){
                                max = bd.eval_black[i][j];
                                p = new Point(i, j);
                            }
                        }
                    }
                }
            }else if(color == Stone.white){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(bd.eval_white[i][j] > 0){
                            if(i % 7 == 0 && j % 7 == 0){
                                return (new Point(i, j));
                            }else if(i % 7 == 0 || j % 7 == 0){
                                if(bd.eval_white[i][j] > max_edge){
                                    max_edge = bd.eval_white[i][j];
                                    p_opt = new Point(i, j);
                                }
                            }
                            if(bd.eval_white[i][j] > max){
                                max = bd.eval_white[i][j];
                                p = new Point(i, j);
                            }
                        }
                    }
                }
            }
            if(p_opt.x >= 0 && p_opt.y >= 0){
                return p_opt;
            }else{
                return p;
            }
        }
        return (new Point(-1, -1));
    }

    Point nextMove(Board bd, Point p){
        if(type == type_human)
            return p;
        else if(type == type_computer)
            return tactics(bd);
        return (new Point(-1, -1));
    }
}

class Stone{
    public final static int black = 1;
    public final static int white = 2;
    private int obverse;

    Stone(){
        obverse = 0;
    }

    void setObverse(int color){
        if(color == black || color == white)
            obverse = color;
        else
            System.out.println("黒か白かでなければいけません");
    }

    void paint(Graphics g, Point p, int rad){
        if(obverse == black){
            g.setColor(Color.black);
            g.fillOval(p.x-rad/2, p.y-rad/2, rad, rad);
        }else if(obverse == white){
            g.setColor(Color.white);
            g.fillOval(p.x-rad/2, p.y-rad/2, rad, rad);
        }
    }

    int getObverse(){
        return obverse;
    }

    void doReverse(){
        if(obverse == black){
            obverse = white;
        }else if(obverse == white){
            obverse = black;
        }
    }
}

class Board{
    private Stone[][] board = new Stone[8][8];
    public int num_grid_black;
    public int num_grid_white;
    private Point[] direction = new Point[8];
    public int[][] eval_black = new int[8][8];
    public int[][] eval_white = new int[8][8];

    Board(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Stone();
            }
        }
        board[3][3].setObverse(1);
        board[3][4].setObverse(2);
        board[4][3].setObverse(2);
        board[4][4].setObverse(1);

        direction[0] = new Point(1, 0);
        direction[1] = new Point(1, 1);
        direction[2] = new Point(0, 1);
        direction[3] = new Point(-1, 1);
        direction[4] = new Point(-1, 0);
        direction[5] = new Point(-1, -1);
        direction[6] = new Point(0, -1);
        direction[7] = new Point(1, -1);
    }

    void paint(Graphics g, int unit_size){
        g.setColor(Color.black);
        g.fillRect(0, 0, unit_size*(10), unit_size*(10));
        g.setColor(new Color(0, 85, 0));
        g.fillRect(unit_size, unit_size, unit_size*8, unit_size*8);
        g.setColor(Color.black);
        for(int i = 0; i < 9; i++){
            g.drawLine(unit_size, unit_size*(i+1), unit_size*9, unit_size*(i+1));
        }
        for(int i = 0; i < 9; i++){
            g.drawLine(unit_size*(i+1), unit_size, unit_size*(i+1), unit_size*9);
        }
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                g.fillRect(unit_size*3+unit_size*4*i-3, unit_size*3+unit_size*4*j-3, 6, 6);
            }
        }
        int rad = (unit_size*8)/10;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Point p = new Point();
                p.x = unit_size*(i+1)+unit_size/2;
                p.y = unit_size*(j+1)+unit_size/2;
                board[i][j].paint(g, p, rad); 
            }
        }
    }

    boolean isOnBoard(int x, int y){
        if(x < 0 || 7 < x || y < 0 || 7 < y)
            return false;
        else
            return true;
    }

    void setStone(int x, int y, int s){
        board[x][y].setObverse(s);
    }

    void evaluateBoard(){
        num_grid_black = 0;
        num_grid_white = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                eval_black[i][j] = countReverseStone(i, j, 1);
                if(eval_black[i][j] > 0) num_grid_black++;
                eval_white[i][j] = countReverseStone(i, j, 2);
                if(eval_white[i][j] > 0) num_grid_white++;
            }
        }
    }

    int countStone(int s){
        int cnt = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getObverse() == s){
                    cnt = cnt + 1;
                }
            }
        }
        return cnt;
    }

    ArrayList<Stone> getLine(int x, int y, Point d){
        ArrayList<Stone> line = new ArrayList<Stone>();
        int cx = x + d.x;
        int cy = y + d.y;
        while(isOnBoard(cx, cy) && board[cx][cy].getObverse() != 0){
            line.add(board[cx][cy]);
            cx += d.x;
            cy += d.y;
        }
        return line;
    }

    int countReverseStone(int x, int y, int s){
        if(board[x][y].getObverse() != 0) return -1;
        int cnt = 0;
        for(int d = 0; d < 8; d++){
            ArrayList<Stone> line = new ArrayList<Stone>();
            line = getLine(x, y, direction[d]);
            int n = 0;
            while(n < line.size() && line.get(n).getObverse() != s) n++;
            if(1 <= n && n < line.size()) cnt += n;
        }
        return cnt;
    }

    void printBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.printf("%2d ", board[j][i].getObverse());
            }
            System.out.println("");
        }
    }

    void printEval(){
        System.out.println("Black(1):");
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.printf("%2d ", eval_black[j][i]);
            }
            System.out.println("");
        }
        System.out.println("White(2):");
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.printf("%2d ", eval_white[j][i]);
            }
            System.out.println("");
        }
    }

    void setStoneAndReverse(int x, int y, int s){
        board[x][y].setObverse(s);
        for(int d = 0; d < 8; d++){
            ArrayList<Stone> line = new ArrayList<Stone>();
            line = getLine(x, y, direction[d]);
            int n = 0;
            while(n < line.size()){
                if(line.get(n).getObverse() == s){
                    int cx = x + direction[d].x;
                    int cy = y + direction[d].y;
                    for(int i = 0; i < n; i++){
                        board[cx][cy].doReverse();
                        cx += direction[d].x;
                        cy += direction[d].y;
                    }
                    break;
                }
                n++;
            }
        }
    }
}

public class Reversi extends JPanel{
    public final static int UNIT_SIZE = 40;
    private Board board = new Board();
    private int turn;
    private Player[] player = new Player[2];

    public Reversi(){
        setPreferredSize(new Dimension(UNIT_SIZE*10, UNIT_SIZE*10));
        addMouseListener(new MouseProc());
        player[0] = new Player(Stone.black, Player.type_computer, 2);
        player[1] = new Player(Stone.white, Player.type_computer, 0);
        turn = Stone.black;
        board.evaluateBoard();
    }

    public void paintComponent(Graphics g){
        String msg1 = "";
        board.paint(g, UNIT_SIZE);
        g.setColor(Color.white);
        if(turn == Stone.black)
            msg1 = "黒の番です";
        else
            msg1 = "白の番です";
        if(player[turn-1].getType() == Player.type_computer)
            msg1 += "(考えています)";
        String msg2 = "[黒:" + board.countStone(Stone.black) +
                    ",白" + board.countStone(Stone.white) + "]";
        g.drawString(msg1, UNIT_SIZE/2, UNIT_SIZE/2);
        g.drawString(msg2, UNIT_SIZE/2, 19*UNIT_SIZE/2);
    }

    public static void main(String[] args){
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new Reversi());
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    void EndMessageDialog(){
        int black = board.countStone(Stone.black);
        int white = board.countStone(Stone.white);
        String str = "[黒:" + black + ",白" + white +"]で";
        if(black > white)
            str += "黒の勝ち";
        else if(black < white)
            str += "白の勝ち";
        else
            str += "引き分け";
        JOptionPane.showMessageDialog(this, str, "ゲーム終了", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    void MessageDialog(String str){
        JOptionPane.showMessageDialog(this, str, "情報", JOptionPane.INFORMATION_MESSAGE);
    }

    void changeTurn(){
        if(turn == Stone.black) turn = Stone.white;
        else if(turn == Stone.white) turn = Stone.black;
    }

    class MouseProc extends MouseAdapter{
        public void mouseClicked(MouseEvent me){
            Point point = me.getPoint();
            Point gp = new Point();
            gp.x = point.x / UNIT_SIZE - 1;
            gp.y = point.y / UNIT_SIZE - 1;
            if(!board.isOnBoard(gp.x, gp.y)) return;
            removeMouseListener(this);
            if(player[turn-1].getType() == Player.type_human){
                if((player[turn-1].getColor() == Stone.black
                        && board.num_grid_black == 0) ||
                        (player[turn-1].getColor() == Stone.white
                        && board.num_grid_white == 0)){
                    MessageDialog("あなたはパスです");
                    changeTurn();
                    repaint();
                }
                else if((player[turn-1].getColor() == Stone.black
                        && board.eval_black[gp.x][gp.y] > 0) ||
                        (player[turn-1].getColor() == Stone.white
                        && board.eval_white[gp.x][gp.y] > 0)){
                    board.setStoneAndReverse(gp.x, gp.y, player[turn-1].getColor());
                    board.evaluateBoard();
                    changeTurn();
                    repaint();
                    if(board.num_grid_black == 0 && board.num_grid_white == 0)
                        EndMessageDialog();
                }
                if(player[turn-1].getType() == Player.type_human)
                    addMouseListener(this);
            }
            if(player[turn-1].getType() == Player.type_computer){
                Thread th = new TacticsThread();
                th.start();
            }
            board.evaluateBoard();
        }
    }

    class TacticsThread extends Thread{
        public void run(){
            try{
                Thread.sleep(10);
                Point nm = player[turn-1].nextMove(board, new Point(-1, -1));
                if(nm.x == -1 && nm.y == -1){
                    MessageDialog("相手はパスです");
                }
                else{
                    board.setStoneAndReverse(nm.x, nm.y, player[turn-1].getColor());
                    board.evaluateBoard();
                }
                changeTurn();
                repaint();
                addMouseListener(new MouseProc());
                if(board.num_grid_black == 0 && board.num_grid_white == 0)
                    EndMessageDialog();
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
}
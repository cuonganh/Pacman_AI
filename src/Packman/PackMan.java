package Packman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Scanner;

import javax.imageio.ImageIO;

public class PackMan implements KeyListener {

    int width, height, delay;
    final int STEP = 2;
    Ghost ghost1 = new Ghost();
    Ghost ghost2 = new Ghost();
    Pack pack = new Pack();
    BufferedImage packman, ghost;
    int frame;
    int mang = 0;
    int columns, rows;
    // r=122 c=54
    int len = 38, phai = 39, xuong = 40, trai = 37;

    ArrayList<String> lines = new ArrayList<String>();
    BufferedImage[] mazeImages = new BufferedImage[4];
    mecung[] mazes = new mecung[1];
    int mazeNo = 0;
    int i1 = 0, i2 = 0;
    int[][] check1 = new int[2][1000];
    int[][] check2 = new int[2][1000];
    int lengpack = 100, lengghost = 100;
    int checkde = 0;
    int count = 0;
    int gx = 0, gy = 0;
    int x;
    int y;
    int futuremove[][] = new int[2][50];
    char[][] mmap;
    char[][] tmpmap1;
    char[][] tmpmap2;
    int[][] toadothuoc = new int[2][50];
    int tongthuoc;
    char a1, a2, a3, a4, a5, b1, b2, b3, b4, b5;
    char a11, a22, a33, a44, a55;

    public PackMan() {
        ghost2.gr = 42;
        ghost2.gc = 214;
        // load thong tin\
        mazes[0] = new mecung(0);
        // cap nhat thong tin
        // get copy cells
        mmap = mazes[mazeNo].laybong();

        rows = mazes[mazeNo].rows;
        columns = mazes[mazeNo].columns;
        pack.row = mazes[mazeNo].row; // toa do packman cu
        pack.column = mazes[mazeNo].column;

        width = mazes[mazeNo].width + 50;
        height = mazes[mazeNo].height + 50;

        frame = 0;
        pack.curDir = pack.reqDir = KeyEvent.VK_RIGHT;

        // =242 c=214
        try {
            packman = ImageIO.read(new File("image/packman.png"));
            ghost = ImageIO.read(new File("image/ghost.png"));
            for (int m = 0; m < 4; m++) {
                mazeImages[m] = ImageIO.read(new File("image/" + m + m + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int tmp[][] = new int[2][1000];

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (37 <= key && key <= 40) {
            pack.reqDir = key;
        }

    }

    int htmp;
    int checklengghuong;

    public void update() {
        delay = 15;
        frame++;
        if (frame > 5) {
            frame = 0;
        }
        futurecount = 0;
        tmpmap1 = mazes[mazeNo].laybong(); // map cho packman
        tmpmap2 = mazes[mazeNo].laybong(); // map cho gbf ghost
        if (pack.mang > 0) {
            future(pack.row, pack.column, 0);
        }

        // kiem tra xem con ma co tim duoc packman k ? neu k thi tu chon 1 dinh bat di
        // de di den
        checklengghuong = 9990000;
        luachontarget();
        // tmpmap1 = mazes[mazeNo].getCells();
        // loang(ghost1.gr, ghost1.gc, pack.row, pack.column, 0, ghost1.ghuong);
        toado();

        // lay huong di chuyen nguoi choi
        if (pack.move(pack.reqDir, columns, rows, b2, b3, b4, b5) == SUCCESS) {
            pack.curDir = pack.reqDir;

        } else {
            pack.move(pack.curDir, columns, rows, b2, b3, b4, b5);
        }
        veduoi();
        endgame();

    }

    public int dodai(int x, int x1, int y, int y1) {

        int a = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
        return a;
    }

    int rt, ct;

    public void luachontarget() {

        // ai(ghost1.gr, ghost1.gc, pack.row, pack.column);
        if (ghost1.mang > 0) {
            int tmp = dodai(ghost1.gr, toadothuoc[0][0], ghost1.gc, toadothuoc[1][0]), tmp2;
            int a = (int) Math.sqrt(dodai(ghost1.gr, pack.row, ghost1.gc, pack.column));
            if (tongthuoc > 4) {
                for (int i = 1; i < tongthuoc - 1; i++) {
                    tmp2 = dodai(ghost1.gr, toadothuoc[0][i], ghost1.gc, toadothuoc[1][i]);
                    if (tmp >= tmp2) {
                        tmp = tmp2;
                        rt = toadothuoc[0][i];
                        ct = toadothuoc[1][i];
                    }
                }
                if (tmp < dodai(ghost1.gr, pack.row, ghost1.gc, pack.column) - 20) {

                    luachontuonglai(1);
                } else {
                    // loang(ghost1.gr, ghost1.gc, pack.row, pack.column, 0, ghost1.ghuong);
                    gbf(ghost1.gr, ghost1.gc, pack.row, pack.column, ghost1.ghuong);
                    ghost1.ghuong = htmp;
                    tmpmap2 = mazes[mazeNo].laybong();
                    asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost2.ghuong);
                    ghost2.ghuong = htmp;

                }
            } else if (futurecount > 2) {
                int a12 = dodai(pack.row, rows / 2, pack.column, columns / 2);
                if (a12 < 1500) {
                    luachontuonglai(2);
                } else {
                    luachontuonglai(3);
                }

            } else {
                gbf(ghost1.gr, ghost1.gc, pack.row, pack.column, ghost1.ghuong);
                ghost1.ghuong = htmp;
                tmpmap2 = mazes[mazeNo].laybong();
                asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost2.ghuong);
                ghost2.ghuong = htmp;
            }
        } else {
            asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost2.ghuong);
            ghost2.ghuong = htmp;
        }

    }

    public void luachontuonglai(int chedo) {
        switch (chedo) {
            case 1:
                chienthuat1();
                break;
            case 2:
                chienthuat2();
                break;
            case 3:
                chienthuat3();
                break;

        }

    }

    // luat 2
    public void chienthuat1() { // ghost 1 an bong
        gbf(ghost1.gr, ghost1.gc, rt, ct, ghost1.ghuong);
        ghost1.ghuong = htmp;
        tmpmap2 = mazes[mazeNo].laybong();
        asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost2.ghuong);
        ghost2.ghuong = htmp;
    }

    public void chienthuat2() { // doi xung 2 dau`
 
        int r = 2 * pack.row - ghost2.gr;
        int c = 2 * pack.column - ghost2.gc;
        int tmp1 = 99999999;
        int tmp1a = 0;
        for (int i = 0; i < futurecount; i++) {
            int a = dodai(r, futuremove[0][i], c, futuremove[1][i]);
            if (tmp1 > a) {
                tmp1 = a;
                tmp1a = i;
            }
        }
        tmpmap1[futuremove[0][tmp1a]][futuremove[1][tmp1a]] = '4';
        gbf(ghost1.gr, ghost1.gc, futuremove[0][tmp1a], futuremove[1][tmp1a], ghost1.ghuong);
        ghost1.ghuong = htmp;
        tmpmap2 = mazes[mazeNo].laybong();
        asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost1.ghuong);
        ghost2.ghuong = htmp;

    }

    public void chienthuat3() { // binh thuong
        int[] futurepoint = new int[30];// diem so
        int temp1 = 999999999;
        int temp1a = 0;
        int temp1b;
        if (ghost1.mang > 0) {
            // +1 cho 2 vi tri xa nhat
            // -1 cho 2 vij tri gan nhat
            for (int i = 0; i < futurecount; i++) {
                int a = dodai(ghost1.gr, futuremove[0][i], ghost1.gc, futuremove[1][i]);
                if (temp1 > a) {
                    temp1 = a;
                    temp1a = i;
                }
            }
            temp1b = temp1a;
            futurepoint[temp1a]++;
            temp1 = 999999;
            for (int i = 0; i < futurecount; i++) {
                if (i != temp1b) {
                    int a = dodai(ghost1.gr, futuremove[0][i], ghost1.gc, futuremove[1][i]);
                    if (temp1 > a) {
                        temp1 = a;
                        temp1a = i;
                    }
                }

            }
            futurepoint[temp1a]++;
            temp1 = 0;
            for (int i = 0; i < futurecount; i++) {
                int a = dodai(ghost1.gr, futuremove[0][i], ghost1.gc, futuremove[1][i]);
                if (temp1 < a) {
                    temp1 = a;
                    temp1a = i;
                }
            }
            temp1b = temp1a;
            futurepoint[temp1a]--;
            temp1 = 0;
            for (int i = 0; i < futurecount; i++) {
                if (i != temp1b) {
                    int a = dodai(ghost1.gr, futuremove[0][i], ghost1.gc, futuremove[1][i]);
                    if (temp1 < a) {
                        temp1 = a;
                        temp1a = i;
                    }
                }

            }
            futurepoint[temp1a]--;

        }

        temp1 = 999999999;
        if (ghost2.mang > 0) {
            // +1 cho 2 vi tri xa nhat
            // -1 cho 2 vij tri gan nhat
            for (int i = 0; i < futurecount; i++) {
                int a = dodai(ghost2.gr, futuremove[0][i], ghost2.gc, futuremove[1][i]);
                if (temp1 > a) {
                    temp1 = a;
                    temp1a = i;
                }
            }
            temp1b = temp1a;
            futurepoint[temp1a]++;
            temp1 = 999999;
            for (int i = 0; i < futurecount; i++) {
                if (i != temp1b) {
                    int a = dodai(ghost2.gr, futuremove[0][i], ghost2.gc, futuremove[1][i]);
                    if (temp1 > a) {
                        temp1 = a;
                        temp1a = i;
                    }
                }

            }
            futurepoint[temp1a]++;
            temp1 = 0;
            for (int i = 0; i < futurecount; i++) {
                int a = dodai(ghost2.gr, futuremove[0][i], ghost2.gc, futuremove[1][i]);
                if (temp1 < a) {
                    temp1 = a;
                    temp1a = i;
                }
            }
            temp1b = temp1a;
            futurepoint[temp1a]--;
            temp1 = 0;
            for (int i = 0; i < futurecount; i++) {
                if (i != temp1b) {
                    int a = dodai(ghost2.gr, futuremove[0][i], ghost2.gc, futuremove[1][i]);
                    if (temp1 < a) {
                        temp1 = a;
                        temp1a = i;
                    }
                }
            }
            futurepoint[temp1a]--;
        }
        // tim so lon nhat
        temp1 = 0;
        for (int i = 0; i < futurecount; i++) {
            int a = futurepoint[i];
            if (temp1 > a) {
                temp1 = a;
                temp1a = i;
            }
        }
        tmpmap1[futuremove[0][temp1a]][futuremove[1][temp1a]] = '4';
        gbf(ghost1.gr, ghost1.gc, futuremove[0][temp1a], futuremove[1][temp1a], ghost1.ghuong);
        ghost1.ghuong = htmp;
        tmpmap2 = mazes[mazeNo].laybong();
        asao(ghost2.gr, ghost2.gc, pack.row, pack.column, ghost2.ghuong);
        ghost2.ghuong = htmp;
    }

    int[][] dinhasao = new int[6][300];// r , c , leng , huong , khoang cach chimbay
    int countdinh = 0, timthay = 0; // tim thay la nguoi choi trong khoang 50 buoc cua ghost

    public void asao(int r, int c, int tr, int tc, int ghosthuong) {
        for (int i = 0; i < 300; i++) {
            dinhasao[5][i] = 0;
        }

        countdinh = 0;
        timthay = 0;
        int tmp = 99999999;
        int tmp2 = 0;
        dfs(r, c, tr, tc, 0, ghosthuong, ghosthuong, 0);
        int count = 0;
        while (timthay == 0) {

            tmp = 99999999;
            tmp2 = 0;
            for (int i = 0; i < countdinh - 1; i++) {
                if (dinhasao[5][i] != 1) {
                    if (tmp >= (dinhasao[2][i] + dinhasao[4][i])) {// tim min cua f(n)= a + n
                        tmp = (dinhasao[2][i] + dinhasao[4][i]);
                        htmp = dinhasao[3][i];
                        tmp2 = i;
                    }
                }

            }
            dinhasao[5][tmp2] = 1;
            dfs(dinhasao[0][tmp2], dinhasao[1][tmp2], tr, tc, 3, dinhasao[3][tmp2], dinhasao[3][tmp2], dinhasao[2][tmp2]);
            count++;
            if (count == 100) {
                gbf(r, c, tr, tc, ghosthuong);
                break;
            }

        }

    }

    public void gbf(int r, int c, int tr, int tc, int ghosthuong) {
        countdinh = 0;
        timthay = 0;
        int tmp = 99999999;
        int tmp2 = 0;
        dfs(r, c, tr, tc, 0, ghosthuong, ghosthuong, 0);

        if (timthay == 0) {
            for (int i = 0; i < countdinh - 1; i++) {
                if (tmp >= (dinhasao[2][i] + dinhasao[4][i])) {// tim min cua f(n)= a + n
                    tmp = (dinhasao[2][i] + dinhasao[4][i]);
                    htmp = dinhasao[3][i];
                    tmp2 = i;
                }
            }
        }

    }

    // thaut toan DFS
    public int dfs(int r, int c, int tr, int tc, int leng, int huong, int ghosthuong, int lengbatdau) {
        if (leng > 30) {
            int a = (int) Math.sqrt(dodai(tr, r, tc, c));

            dinhasao[0][countdinh] = r;
            dinhasao[1][countdinh] = c;
            dinhasao[2][countdinh] = leng + lengbatdau;
            dinhasao[3][countdinh] = huong;
            dinhasao[4][countdinh] = a;
            countdinh++;

            return 0;
        } else if (r - tr == 0 && c - tc == 0) {

            if (mmap[r][c] != '0') {
                tmpmap2[r][c] = '4';
                timthay = 1;
                htmp = huong;
                checklengghuong = 0;
            }

            return 1;
        }

        if (tmpmap2[r][c] != '0' && tmpmap2[r][c] != '8' && mmap[r][c] != '6') {//
            tmpmap2[r][c] = '8';
            if (leng < 1) {
                if (ghosthuong != len) {

                    gx = 0;
                    gy = 1;// xuong

                    dqdfs(r, c, tr, tc, leng, xuong, ghosthuong, lengbatdau);

                }

                if (ghosthuong != trai) {
                    gx = 1;
                    gy = 0;// phai
                    dqdfs(r, c, tr, tc, leng, phai, ghosthuong, lengbatdau);

                }
                if (ghosthuong != phai) {

                    gx = -1;
                    gy = 0;// trai
                    dqdfs(r, c, tr, tc, leng, trai, ghosthuong, lengbatdau);
                }
                if (ghosthuong != xuong) {
                    gx = 0;
                    gy = -1; // len
                    dqdfs(r, c, tr, tc, leng, len, ghosthuong, lengbatdau);
                }
                return 1;
            } else {

                gx = 0;
                gy = -1; // len
                dqdfs(r, c, tr, tc, leng, huong, ghosthuong, lengbatdau);
                gx = -1;
                gy = 0;// trai
                dqdfs(r, c, tr, tc, leng, huong, ghosthuong, lengbatdau);
                gx = 1;
                gy = 0;// phai
                dqdfs(r, c, tr, tc, leng, huong, ghosthuong, lengbatdau);

                gx = 0;
                gy = 1;// xuong
                dqdfs(r, c, tr, tc, leng, huong, ghosthuong, lengbatdau);

                return 1;
            }
        }

        return 0;
    }

    public int dqdfs(int r, int c, int tr, int tc, int leng, int huong, int ghosthuong, int lengbatdau) {
        if (tmpmap1[r + gy][c + gx] != '0') {
            // mmapfuture[r][c]='0';
            if (dfs(r + gy, c + gx, tr, tc, leng + 1, huong, ghosthuong, lengbatdau) == 1) {
                r = r - gy;
                c = c - gx;
                return 1;
            }
        }
        return 0;
    }
    int futurecount = 0;

    public void addfuture(int r, int c) {
        int check = 0;

        futuremove[0][futurecount] = r;
        futuremove[1][futurecount] = c;
        if (futurecount < 1) {
            futurecount++;
            // mmapfuture[r][c] = '4';
        } else {
            for (int i = 0; i < futurecount; i++) {

                int a = (r - futuremove[0][i]) * (r - futuremove[0][i])
                        + (c - futuremove[1][i]) * (c - futuremove[1][i]);
                if (a < 100) {
                    check = 1;

                    if (mmap[r][futuremove[1][i]] != '0') {
                        futuremove[0][i] = r;
                    } else {
                        futuremove[1][i] = c;
                    }

                }

            }
            if (check == 0) {
                futurecount++;
                // tmpmap1[r][c] = '4';
            }
        }

    }

    public int future(int r, int c, int leng) {

        if (leng == 100) {
            if (mmap[r][c] != '0') {
                addfuture(r, c);

            }
            return 0;
        }

        if (tmpmap1[r][c] != '0' && tmpmap1[r][c] != '8' && mmap[r][c] != '7') {
            tmpmap1[r][c] = '8';

            if (leng == 0) {
                if (pack.curDir == len) {
                    x = 0;
                    y = -1; // len
                    dqft(r, c, leng);
                    x = 1;
                    y = 0;// phai
                    dqft(r, c, leng);
                    x = -1;
                    y = 0;// trai
                    dqft(r, c, leng);
                }
                if (pack.curDir == phai) {
                    x = 0;
                    y = -1; // len
                    dqft(r, c, leng);
                    x = 1;
                    y = 0;// phai
                    dqft(r, c, leng);
                    x = 0;
                    y = 1;// xuong
                    dqft(r, c, leng);
                }
                if (pack.curDir == trai) {
                    x = 0;
                    y = -1; // len
                    dqft(r, c, leng);
                    x = -1;
                    y = 0;// trai
                    dqft(r, c, leng);
                    x = 0;
                    y = 1;// xuong
                    dqft(r, c, leng);
                }
                if (pack.curDir == xuong) {
                    x = 1;
                    y = 0;// phai
                    dqft(r, c, leng);
                    x = -1;
                    y = 0;// trai
                    dqft(r, c, leng);
                    x = 0;
                    y = 1;// xuong
                    dqft(r, c, leng);
                }
                return 1;
            } else {
                x = 0;
                y = -1; // len
                dqft(r, c, leng);
                x = 1;
                y = 0;// phai
                dqft(r, c, leng);
                x = -1;
                y = 0;// trai
                dqft(r, c, leng);
                x = 0;
                y = 1;// xuong
                dqft(r, c, leng);
                return 1;
            }

        }
        return 0;
    }

    public int dqft(int r, int c, int leng) {
        if (tmpmap1[r + y][c + x] != '0') {
            // mmapfuture[r][c]='0';
            if (future(r + y, c + x, leng + 1) == 1) {
                return 1;
            }
        }
        return 0;
    }

    public void toado() {
        /*
		 * a1 hien tai a2 len a3 xuong a4 trai a5 phai
         */
        if (pack.column < 7) {
            // row = row;
            pack.column = columns - 7;
        }

        if (pack.column > columns - 7) {
            // row = row;
            pack.column = 7;
        }
        if (ghost1.mang > 0) {
            a1 = mazes[mazeNo].laybando(ghost1.gr + ghost1.yz, ghost1.gc + ghost1.xz);
            a2 = mazes[mazeNo].laybando(ghost1.gr - 1, ghost1.gc);
            a3 = mazes[mazeNo].laybando(ghost1.gr + 1, ghost1.gc);
            a4 = mazes[mazeNo].laybando(ghost1.gr, ghost1.gc - 1);
            a5 = mazes[mazeNo].laybando(ghost1.gr, ghost1.gc + 1);
            ghost1.gdieukhien(a1, a2, a3, a4, a5, columns, rows);
        }
        if (ghost2.mang > 0) {
            a11 = mazes[mazeNo].laybando(ghost2.gr + ghost2.yz, ghost2.gc + ghost2.xz);
            a22 = mazes[mazeNo].laybando(ghost2.gr - 1, ghost2.gc);
            a33 = mazes[mazeNo].laybando(ghost2.gr + 1, ghost2.gc);
            a44 = mazes[mazeNo].laybando(ghost2.gr, ghost2.gc - 1);
            a55 = mazes[mazeNo].laybando(ghost2.gr, ghost2.gc + 1);
            ghost2.gdieukhien(a11, a22, a33, a44, a55, columns, rows);
        }
        if (pack.mang > 0) {
            b2 = mazes[mazeNo].laybando(pack.row, pack.column - 1);
            b3 = mazes[mazeNo].laybando(pack.row, pack.column + 1);
            b4 = mazes[mazeNo].laybando(pack.row - 1, pack.column);
            b5 = mazes[mazeNo].laybando(pack.row + 1, pack.column);

        }

    }

    public void veduoi() {
        i1++;
        i2++;
        if (i1 == lengpack) {
            checkde = 1;
            i1 = 0;
        }
        if (i2 == lengghost) {
            checkde = 1;
            i2 = 0;
        }

        if (checkde == 1) {
            mmap[check1[0][i1]][check1[1][i1]] = '8';
            mmap[check2[0][i2]][check2[1][i2]] = '8';
        }
        if (pack.mang > 0) {
            check1[0][i1] = pack.row;
            check1[1][i1] = pack.column;

            // tang do dai
            if (mmap[pack.row][pack.column] == '9') {
                lengpack += 10;

            }

            mmap[pack.row][pack.column] = '6';
        }

        if (ghost1.mang > 0) {
            check2[0][i2] = ghost1.gr;
            check2[1][i2] = ghost1.gc;
            if (mmap[ghost1.gr][ghost1.gc] == '9') {
                lengghost += 10;

            }
            mmap[ghost1.gr][ghost1.gc] = '7';
        }

    }

    public void xoaduoi() {

        for (int a = 0; a < lengpack; a++) {
            mmap[check1[0][a]][check1[1][a]] = '8';
        }

        for (int a = 0; a < lengghost; a++) {
            mmap[check2[0][a]][check2[1][a]] = '8';
        }
    }

    public void endgame() {
        if (pack.mang > 0) {
            if (mmap[pack.row + 1][pack.column] == '7' || mmap[pack.row - 1][pack.column] == '7'
                    || mmap[pack.row][pack.column + 1] == '7' || mmap[pack.row][pack.column - 1] == '7'
                    || mmap[pack.row][pack.column] == '7') {
                pack.mang--;

                if (pack.mang >= 0) {
                    pack.row = mazes[mazeNo].row;
                    pack.column = mazes[mazeNo].column;
                    pack.curDir = len;
                    xoaduoi();
                } else {
                    System.out.println("thua!!");
                }
            }
            int a32 = dodai(pack.row, ghost1.gr, pack.column, ghost1.gc);
            int a12 = dodai(pack.row, ghost2.gr, pack.column, ghost2.gc);
            if (a32 < 4 || a12 < 4 || (a12 < 4 && a32 < 4)) {
                pack.mang--;

                if (pack.mang >= 0) {
                    pack.row = mazes[mazeNo].row;
                    pack.column = mazes[mazeNo].column;
                    pack.curDir = len;
                    xoaduoi();
                }
            }
        } else {
            System.out.println("thua!!");
        }

        if (mmap[ghost1.gr - 1][ghost1.gc] == '6' || mmap[ghost1.gr + 1][ghost1.gc] == '6'
                || mmap[ghost1.gr][ghost1.gc - 1] == '6' || mmap[ghost1.gr][ghost1.gc + 1] == '6'
                || mmap[ghost1.gr][ghost1.gc] == '6') {
            ghost1.returnbase(1);
            xoaduoi();
            ghost1.mang--;
        }
        if (mmap[ghost2.gr - 1][ghost2.gc] == '6' || mmap[ghost2.gr + 1][ghost2.gc] == '6'
                || mmap[ghost2.gr][ghost2.gc - 1] == '6' || mmap[ghost2.gr][ghost2.gc + 1] == '6'
                || mmap[ghost2.gr][ghost2.gc] == '6') {
            if (ghost1.mang == 0) {
                ghost2.returnbase(2);
                if (ghost1.mang == 0) {
                    ghost2.mang--;
                    if (ghost2.mang == 0) {
                        System.out.print("thang !!!!!!!!!!!!!!");
                    }
                }
            }

        }
    }

    static int SUCCESS = 1, FALSE = 0;

    public void draw(Graphics2D g) {
        tongthuoc = 0;
        // ve cac thuc khac
        g.drawImage(mazeImages[mazeNo], 0, 0, null);
        g.setColor(Color.white);

        // TODO Auto-generated method stub
        for (int r = 0; r < mazes[mazeNo].rows; r++) {
            for (int c = 0; c < mazes[mazeNo].columns; c++) {
                if (mmap[r][c] == '7') { // duoi
                    g.setPaint(Color.RED);
                    g.fillOval(c * STEP - 2, r * STEP - 3, 6, 6);

                }
                if (mmap[r][c] == '6') { // duoi
                    g.setPaint(Color.BLUE);
                    g.fillOval(c * STEP - 2, r * STEP - 3, 6, 6);

                }
                //  if (tmpmap1[r][c] == '8') {
                //    g.setPaint(Color.RED); // tim duong cua packman
                //    g.fillOval(c * STEP - 2, r * STEP - 3, 6, 6);
                //    }
                if (tmpmap2[r][c] == '8') {
                    //   g.setPaint(Color.YELLOW);// tim duong cua ghost
                    //   g.fillOval(c * STEP - 2, r * STEP - 3, 6, 6);
                }
                if (mmap[r][c] == '9') { // thuoc
                    tongthuoc++;
                    toadothuoc[0][tongthuoc] = r;
                    toadothuoc[1][tongthuoc] = c;

                    g.setPaint(Color.YELLOW);
                    g.fillOval(c * STEP - 5, r * STEP - 6, 9, 9);

                }

            }
        }

        // ve packman
        if (pack.mang > 0) {
            g.drawImage(packman.getSubimage((frame / 2) * 30, (pack.curDir - 37) * 30, 28, 28), pack.column * STEP - 14,
                    pack.row * STEP - 14, null);
        }

        // ve ghost
        if (ghost1.mang > 0) {
            g.drawImage(ghost.getSubimage((frame / 2) * 30, (ghost1.ghuong - 37) * 30, 28, 28), ghost1.gc * STEP - 14,
                    ghost1.gr * STEP - 14, null);
        }
        if (ghost2.mang > 0) {
            g.drawImage(ghost.getSubimage((frame / 2) * 30, (ghost2.ghuong - 37) * 30, 28, 28), ghost2.gc * STEP - 14,
                    ghost2.gr * STEP - 14, null);

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}

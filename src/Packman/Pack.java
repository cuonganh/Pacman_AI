package Packman;

import java.awt.event.KeyEvent;

public class Pack {

    int row, column;
    int reqDir, curDir;
    int mang = 4;
    int len = 38, phai = 39, xuong = 40, trai = 37;
    /*
	 a1 hien tai
	 a2 len
	 a3 xuong
	 a4 trai
	 a5 phai
	 
	char a1 = mazes[mazeNo].charAt(ghost.gy+ghost.yz, ghost.gx+ghost.xz);
	char a2 = mazes[mazeNo].charAt(ghost.gy-1, ghost.gx);
	char a3 = mazes[mazeNo].charAt(ghost.gy+1, ghost.gx);
	char a4 = mazes[mazeNo].charAt(ghost.gy, ghost.gx-1);
	char a5 = mazes[mazeNo].charAt(ghost.gy, ghost.gx+1);
	
     */
    static int SUCCESS = 1, FALSE = 0;

    public int move(int reqDir, int columns, int rows, char b2, char b3, char b4, char b5) {
        switch (reqDir) {
            case KeyEvent.VK_LEFT: //37
                if (column > 0 && b2 != '0' && curDir != KeyEvent.VK_RIGHT) {
                    column -= 1;
                    return SUCCESS;
                }

                break;

            case KeyEvent.VK_RIGHT: //39
                if (column < columns - 1 && b3 != '0' && curDir != KeyEvent.VK_LEFT) {
                    column += 1;
                    return SUCCESS;
                }

                break;
            case KeyEvent.VK_UP: //38
                if (row > 0 && b4 != '0' && curDir != KeyEvent.VK_DOWN) {
                    row -= 1;
                    return SUCCESS;
                }
                break;
            case KeyEvent.VK_DOWN: //40
                if (row < rows - 1 && b5 != '0' && curDir != KeyEvent.VK_UP) {
                    row += 1;
                    return SUCCESS;
                }
                break;

        }
        return FALSE;
    }

}

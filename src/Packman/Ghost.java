package Packman;

public class Ghost {

    int gc = 54;
    int gr = 122;
    int len = 38, phai = 39, xuong = 40, trai = 37;
    int ghuong = xuong;
    int yz = 0, xz = 0;
    int mang = 2;

    /*
	 a1 hien tai
	 a2 len
	 a3 xuong
	 a4 trai
	 a5 phai
     */
    public void returnbase(int i) {
        if (i == 1) {
            gc = 54;
            gr = 122;
        } else {
            gr = 42;
            gc = 214;
        }
    }

    public void gdieukhien(char a1, char a2, char a3, char a4, char a5, int columns, int rows) {

        if (a1 == '0') {

            //	System.out.println(xz+" "+yz);
            if (yz == 0) {
                if (a2 == '0') {
                    ghuong = xuong;

                } else if (a3 == '0') {
                    ghuong = len;
                }
                /*
				 else {
					int a =(int) (Math.random()*2);
					if(a==0) {
						ghuong = len;
					}
					else {
						ghuong = xuong;
					}
					
				}
                 */

            } else if (xz == 0) {
                if (a4 == '0') {
                    ghuong = phai;

                } else if (a5 == '0') {
                    ghuong = trai;
                }
                /*
				 else {
					int a =(int) (Math.random()*2);
					if(a==0) {
						ghuong = trai;
					}
					else {
						ghuong = phai;
					}
				}
                 */

            }

        }
        if (ghuong == 37) { //trai
            xz = -1;
            yz = 0;
            if (gc > 0 && a4 != '0') {
                gc += xz;

            }
            if (gc < 4) {
                //row = row;
                gc = columns - 4;
            }

        }
        if (ghuong == 38) { // len
            xz = 0;
            yz = -1;
            if (gr > 0 && a2 != '0') {
                gr += yz;
            }
        }
        if (ghuong == 39) { //phai
            xz = 1;
            yz = 0;
            if (gc < columns - 1 && a5 != '0') {
                gc += xz;
            }
            if (gc == columns - 2) {
                gc = 1;
            }

        }
        if (ghuong == 40) { //xuong
            xz = 0;
            yz = 1;
            if (gr < rows - 1 && a3 != '0') {
                gr += yz;
            }
        }
    }

}

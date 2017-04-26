/****************************************************************************************************
 * Project : 02 Array Teams Move
 * Author  : David Fielder
 * Date    : 09/13/2012
 * Abstract: This project loads an array from a list of teams that is given to it. It is suppose to 
 * swap the location of two teams that the user inputs through a J Option Pane. I had troubles getting
 * the numbers to switch right, i also had problems getting it to sort right. I'm not sure what I am 
 * doing wrong with this program. 
 ****************************************************************************************************/
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
public class DavidFielder02ArrayTeamsMove
{
  public static void main(String[] args) throws IOException
  {
    FileReader     disk     = new FileReader("teams.txt");
    BufferedReader teamfile = new BufferedReader(disk);
    String         teamList[] = new String[10];
    String         fromTo;
    int            i;

    for(i=0;i<10;i++)
      teamList[i] = teamfile.readLine();

    disp(teamList);
    fromTo = getRequest();           //primer read

    while(!fromTo.equals("0"))
      {
        moveTeam(teamList,fromTo);
        disp(teamList);
        fromTo = getRequest();       //next request
      }
  }
  //-----------------------------------------------------------------------input
  public static String getRequest()
  {
    StringTokenizer tok;
    String  prompt = "Enter from,to positions (0=exit)";
    String  title  = "Proj 2   Sally Student";
    String  input  = "", err = "";
    int     from, to;
    boolean valid  = false;

    while(!valid)
      {
        input = JOptionPane.showInputDialog(null,prompt+err,title,-1);
        err   = "";            //reset for possible new error
        if(input.equals("0"))  //test for exit request
          valid = true;
        else
          {
            tok  = new StringTokenizer(input,",");
            try
              {
                from = Integer.parseInt(tok.nextToken());
                to   = Integer.parseInt(tok.nextToken());
                if(tok.hasMoreTokens())
                  err = "\n\nAttempted entry: " + input + "\nToo many positions. Try again.";
                else
                if(input.indexOf(",")<input.lastIndexOf(","))
                  err = "\n\nAttempted entry: " + input + "\nToo many commas. Try again.";
                else
                if(from < 1 || from > 10 || to < 1 || to > 10)
                  err = "\n\nAttempted entry: " + input + "\nInvalid row number. Try again.";
                else
                if(from == to)
                  err = "\n\nAttempted entry: " + input + "\nInvalid request - same from,to. Try again.";
                else
                  valid = true;
              }
            catch(NoSuchElementException e) //missing token
              {
                err = "\n\nAttempted entry: " + input + "\nRequires from,to. Try again.";
              }
            catch(NumberFormatException e)  //non-numeric entry
              {
                err = "\n\nAttempted entry: " + input + "\nIllegal characer. Try again.";
              }
          }//end if
      }//end while
    return input;
  }
  //----------------------------------------------------------------------------

  //------------------------------------------------------------------------move
  public static void moveTeam(String teamList[],String fromTo)
  {
    StringTokenizer tok;
    int             from;
    int             to;

    tok  = new StringTokenizer(fromTo,",");
    from = Integer.parseInt(tok.nextToken());
    to   = Integer.parseInt(tok.nextToken());
    from--;                               //convert to subscript
    to--;
    if(from>to)                           //determine direction to move
      moveUp(teamList,from,to);
    else
      moveDown(teamList,from,to);
  }

  //---------------------------------------------------------------------move up
  public static void moveUp(String teamList[], int from, int to)
  {
    String clipboard;
    int    row;

    clipboard = teamList[from];           //copy

    for(row=from;row>to;row--)            //shift others down
      teamList[row] = teamList[row-1];

    teamList[row] = clipboard;            //paste
  }
  //-------------------------------------------------------------------move down
  public static void moveDown(String teamList[], int from, int to)
  {
    String clipboard;
    int    row;

    clipboard = teamList[from];           //copy

    for(row=from;row<to;row++)            //shift others up
      teamList[row] = teamList[row+1];

    teamList[row] = clipboard;            //paste
  }
  //---------------------------------------------------------------------display
  public static void disp(String teamList[])
  {
    int r;
    System.out.println("----------------");
    for(r=0;r<10;r++)
      System.out.printf("%2d %s\n",r+1,teamList[r]);
  }
  //----------------------------------------------------------------------------
}

package assignments.filesearcher;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.*;
import java.io.IOException;

public class FileSearcherX extends Frame implements ActionListener
{
	Panel1 p1;
	Panel2 p2;
	Panel3 p3;


	public FileSearcherX()
	{
         super("File Searcher X");
	     add(p1=new Panel1(),BorderLayout.NORTH);
		 add(p2=new Panel2(),BorderLayout.WEST);
		 add(p3=new Panel3(),BorderLayout.EAST);

		 p1.b1.addActionListener(this);
	     p1.t2.addActionListener(this);
		 p1.b2.addActionListener(new ActionListener()
		{
			 public void actionPerformed(ActionEvent e)
			{
				 p2.ta.setText(" ");
				 inFile("c:","");
				 inFile("d:","");
				 inFile("e:","");
			}
		});
         

         addWindowListener(new WindowAdapter()
		{
			 public void windowClosing(WindowEvent e)
			{
				 System.exit(0);
			}
		});
		setSize(800,800);
		setVisible(true);

	}//end of constructor

	public void actionPerformed(ActionEvent e)
	{
        p2.ta.setText(" ");
		//p2.ta.append("Inside ActionPerformed\n");
		String searchdirectory=p1.t2.getText();

		inFile(searchdirectory,"");



	}

	public void inFile(String parentdirectory, String file)
	{
		//p2.ta.append("\nInside InFile\n");
		//p2.ta.append("parentdirectory = "+parentdirectory+"\n");
		//p2.ta.append("file = "+file+"\n");
		String thisfilepath=parentdirectory+"\\"+file;
		//p2.ta.append("thisfilepath = "+thisfilepath+"\n");

		//Path path=Paths.get(thisfilepath);
		File f=new File(thisfilepath);
		String searchString=p1.t1.getText();
/*
		if(f.isFile())
		{
           // p2.ta.append("Inside if(f.isFile())\n");
            
			if(searchString.equals(f.getName()))
			{
				p2.ta.append("\nGOT IT! at :"+thisfilepath+"\n\n");
			    
			}
			
		}
	else*/ if(f.isDirectory())
		  {
			if(searchString.equals(f.getName()))
			{
				p2.ta.append("\nGOT IT! at :"+thisfilepath+"\n\n");
			    
			}
			//p2.ta.append("Inside if(f.isDirectory())\n");
			String[] directorylist=f.list();
			if(directorylist!=null)
			{
				for(String listfile: directorylist)
				{
					//p2.ta.append("listfile = "+listfile+"\n");
					if(listfile.indexOf(".")!=-1)
					{
                       if(listfile.equals(searchString))
						   p2.ta.append("\nGOT IT! at :"+thisfilepath+"\\"+listfile+"\n\n");

					}else
					{
					inFile(thisfilepath,listfile);
					}
				}
			}
		}
	}//end of inFile


	public static void main(String args[])
	{
          new FileSearcherX();
	}

   
}
class Panel1 extends Panel
{
	Button b1,b2;
	TextField t1,t2;

	public Panel1()
	{
		add(t1=new TextField(15));
		add(t2=new TextField(40));
		add(b1=new Button("Search Specified Folder"));
		add(b2=new Button("Universal Search"));
	}
}

class Panel2 extends Panel
{
	TextArea ta;
//	TextField t;
	public Panel2()
	{
		add(ta=new TextArea(60,100));
	//	add(t=new TextField(20));
	}
}

class Panel3 extends Panel
{
}

class Panel4 extends Panel
{
}
/*
class InFile
{
	String str;
	Path parentpath;
	public InFile(String str,Path parentpath)
	{
		this.str=str;
		this.parentpath=parentpath;
		Path thisFilepath=Paths.get(parentpath+str);
		File f=new File(thisFilepath);

	}
	
	Path thisFilepath=Paths.get(parentpath+str);
}
*/

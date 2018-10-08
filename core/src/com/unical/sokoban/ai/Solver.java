package com.unical.sokoban.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.unical.sokoban.logic.World;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.DLV2AnswerSets;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class Solver {

	private World world;
	private int[][] sokobanMatrix;
	private static String encodingResource = "encodings/sokoban";
	private static Handler handler;
	private ArrayList<MoveBox> movements;

	public Solver(World world) {
		this.world = world;
		movements = new ArrayList<MoveBox>();
		sokobanMatrix = world.getLogicMap();
	}

	public void solve() throws Exception {

		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		handler.addOption(new OptionDescriptor("-n=0 "));
		InputProgram facts = new ASPInputProgram();

		try {
			ASPMapper.getInstance().registerClass(MoveBox.class);
		} catch (ObjectNotValidException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAnnotationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < world.boxes.size(); i++) {
			try {
				int x = (int) (world.boxes.get(i).getX() / 64);
				int y = (int) (world.boxes.get(i).getY() / 64);
				facts.addObjectInput(new BoxEmbasp(i, x, y));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < world.getRows(); i++) {

			for (int j = 0; j < world.getCols(); j++) {
				if (sokobanMatrix[i][j] != 2) {
					if (j + 1 < world.getCols() && sokobanMatrix[i][j + 1] != 2) {
						facts.addObjectInput(new Adj(i,j,i,j+1));
					}
					if (j - 1 >= 0 && sokobanMatrix[i][j - 1] != 2) {
						facts.addObjectInput(new Adj(i,j,i,j-1));
					}
					if (i + 1 < world.getRows() && sokobanMatrix[i + 1][j] != 2) {
						facts.addObjectInput(new Adj(i,j,i+1,j));
					}
					if (i-1>= 0 && sokobanMatrix[i - 1][j] != 2) {
						facts.addObjectInput(new Adj(i,j,i-1,j));
					}
					if(sokobanMatrix[i][j]==1 || sokobanMatrix[i][j]==4) {
						facts.addObjectInput(new Cell(i,j,sokobanMatrix[i][j]));
					}
				}
			}

		}

		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addProgram(getEncodings(encodingResource));
		handler.addProgram(encoding);

		Output o = handler.startSync();

		DLV2AnswerSets answers = (DLV2AnswerSets) o;

		if (answers.getAnswersets().size() <= 0) {
			System.out.println("NO ANSWER SETS");
		} else
			for (AnswerSet a : answers.getAnswersets()) {
				try {
					for (Object obj : a.getAtoms()) {
						if (obj instanceof MoveBox) {
							MoveBox movement = (MoveBox) obj;
							movements.add(movement);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	private static String getEncodings(String encodingResource) {
		BufferedReader reader;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(encodingResource));
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public ArrayList<MoveBox> getMovements() {
		return movements;
	}

}

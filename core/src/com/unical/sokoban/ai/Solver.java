package com.unical.sokoban.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.unical.sokoban.Sokoban;
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

		BoxEmbasp b = null;
		for (int i = 0; i < world.boxes.size(); i++) {
			try {
				int x = (int) (world.boxes.get(i).getX() / 64);
				int y = (int) (world.boxes.get(i).getY() / 64);
				b = new BoxEmbasp(i, x, y);
				facts.addObjectInput(b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Right r = null;
		Top t = null;
		Cell c = null;
		for (int i = 0; i < world.getRows(); i++) {
			for (int j = 0; j < world.getCols(); j++) {
				if (sokobanMatrix[i][j] != 2) {
					if (j + 1 < world.getCols() && sokobanMatrix[i][j + 1] != 2) {
						t = new Top(i, j, i, j + 1);
						facts.addObjectInput(t);
					}
					if (i + 1 < world.getRows() && sokobanMatrix[i + 1][j] != 2) {
						r = new Right(i, j, i + 1, j);
						facts.addObjectInput(r);
					}
					if (sokobanMatrix[i][j] == 1 || sokobanMatrix[i][j] == 4) {
						c = new Cell(i, j, sokobanMatrix[i][j]);
						facts.addObjectInput(c);
					}
				}
			}

		}

		switch (world.boxes.size()) {
		case 1:
			facts.addObjectInput(new MaxMosse(2));
			break;
		case 2:
			facts.addObjectInput(new MaxMosse(12));
			break;
		case 3:
			facts.addObjectInput(new MaxMosse(10));
			break;
		case 4:
			facts.addObjectInput(new MaxMosse(12));
			break;
		case 5:
			facts.addObjectInput(new MaxMosse(22));
			break;
		default:
			break;
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

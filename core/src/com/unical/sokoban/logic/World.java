package com.unical.sokoban.logic;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import com.unical.sokoban.scenes.Hud;
import com.unical.sokoban.Sokoban;
import com.unical.sokoban.sprites.Box;
import com.unical.sokoban.sprites.Player;
import com.unical.sokoban.sprites.EndPoint;
import com.unical.sokoban.sprites.Wall;
import com.unical.sokoban.sprites.Player.Action;
import com.unical.sokoban.sprites.Player.Direction;

public class World {

	private Player player;
	public ArrayList<Box> boxes = new ArrayList<Box>();
	public ArrayList<EndPoint> targets = new ArrayList<EndPoint>();
	private final int rows = Sokoban.WIDTH / 64;
	private final int cols = Sokoban.HEIGHT / 64;
	private int[][] logicMap = new int[rows][cols];
	private boolean undo;

	public World(TiledMap map, Player player) {
		this.player = player;
		reset(map);
	}

	public void print() {
		System.out.println("\n");
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				System.out.print(logicMap[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public void update(float delta) {

		int playerX1 = (int) (player.x / 64);
		int playerY1 = (int) (player.y / 64);
		int playerX2 = (int) (player.x / 64);
		int playerY2 = (int) (player.y / 64);
		int hDir = 0;
		int vDir = 0;

		if (player.action == Action.RUNNING && player.direction == Direction.UP) {
			playerY2 = playerY1 + 1;
			vDir++;
		} else if (player.action == Action.RUNNING && player.direction == Direction.DOWN) {
			playerY2 = playerY1 - 1;
			vDir--;
		} else if (player.action == Action.RUNNING && player.direction == Direction.RIGHT) {
			playerX2 = playerX1 + 1;
			hDir++;
		} else if (player.action == Action.RUNNING && player.direction == Direction.LEFT) {
			playerX2 = playerX1 - 1;
			hDir--;
		}

		if ((logicMap[playerX2][playerY2] == 0 || logicMap[playerX2][playerY2] == 4)
				&& !isOnEndPoint(playerX1, playerY1)) {
			logicMap[playerX2][playerY2] = 1;
			logicMap[playerX1][playerY1] = 0;
			player.x = playerX2 * 64;
			player.y = playerY2 * 64;
			player.px = playerX1 * 64;
			player.py = playerY1 * 64;
			resetBlockMoved();
		} else if ((logicMap[playerX2][playerY2] == 0 || logicMap[playerX2][playerY2] == 4)
				&& isOnEndPoint(playerX1, playerY1)) {
			logicMap[playerX2][playerY2] = 1;
			logicMap[playerX1][playerY1] = 4;
			player.x = playerX2 * 64;
			player.y = playerY2 * 64;
			player.px = playerX1 * 64;
			player.py = playerY1 * 64;
			resetBlockMoved();
		} else if (logicMap[playerX2][playerY2] == 3 && logicMap[playerX2 + hDir][playerY2 + vDir] != 2
				&& logicMap[playerX2 + hDir][playerY2 + vDir] != 3) {
			if (!isOnEndPoint(playerX1, playerY1)) {
				logicMap[playerX2 + hDir][playerY2 + vDir] = 3;
				logicMap[playerX2][playerY2] = 1;
				logicMap[playerX1][playerY1] = 0;
			} else {
				logicMap[playerX2 + hDir][playerY2 + vDir] = 3;
				logicMap[playerX2][playerY2] = 1;
				logicMap[playerX1][playerY1] = 4;
			}
			player.x = playerX2 * 64;
			player.y = playerY2 * 64;
			player.px = playerX1 * 64;
			player.py = playerY1 * 64;
			resetBlockMoved();
			for (int i = 0; i < boxes.size(); i++) {
				int blockX = (int) (boxes.get(i).getX() / 64);
				int blockY = (int) (boxes.get(i).getY() / 64);
				if (blockX == playerX2 && blockY == playerY2) {
					boxes.get(i).setX((blockX + hDir) * 64);
					boxes.get(i).setY((blockY + vDir) * 64);
					boxes.get(i).px = blockX * 64;
					boxes.get(i).py = blockY * 64;
					boxes.get(i).moved = true;
				}
			}
		}

		if (undo) {
			undo = false;
			int newX, newY, oldX, oldY;
			oldX = (int) (player.x / 64);
			oldY = (int) (player.y / 64);
			player.x = player.px;
			player.y = player.py;
			newX = (int) (player.x / 64);
			newY = (int) (player.y / 64);

			if (!isOnEndPoint(oldX, oldY)) {
				logicMap[newX][newY] = 1;
				logicMap[oldX][oldY] = 0;
			} else {
				logicMap[newX][newY] = 1;
				logicMap[oldX][oldY] = 4;
			}

			boolean lastMoved = true;
			for (int i = boxes.size() - 1; i >= 0; i--) {
				if (boxes.get(i).moved && lastMoved) {
					float blockX = boxes.get(i).getX();
					float blockY = boxes.get(i).getY();
					boxes.get(i).setX(boxes.get(i).px);
					boxes.get(i).setY(boxes.get(i).py);
					if (!isOnEndPoint((int) (blockX / 64), (int) (blockY / 64))) {
						logicMap[(int) (blockX / 64)][(int) (blockY / 64)] = 0;
						logicMap[(int) (boxes.get(i).getX() / 64)][(int) (boxes.get(i).getY() / 64)] = 3;
					} else {
						logicMap[(int) (blockX / 64)][(int) (blockY / 64)] = 4;
						logicMap[(int) (boxes.get(i).getX() / 64)][(int) (boxes.get(i).getY() / 64)] = 3;
					}
					lastMoved = false;
				}
				boxes.get(i).moved = false;
			}
		}
			for (int i = 0; i < boxes.size(); i++) {
				boxes.get(i).update();
				targets.get(i).update();
			}
	}

	private void resetBlockMoved() {
		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).moved = false;
		}
	}

	private boolean isOnEndPoint(int x, int y) {
		for (int i = 0; i < targets.size(); i++) {
			int tx = (int) (targets.get(i).getX() / 64);
			int ty = (int) (targets.get(i).getY() / 64);
			if (tx == x && ty == y) {
				return true;
			}
		}
		return false;
	}

	public boolean levelCompleted() {
		int remainingEndPoints = 0;
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				if (logicMap[i][j] == 4) {
					remainingEndPoints++;
				}
			}
		}

		if (isOnEndPoint((int) player.x / 64, (int) player.y / 64)) {
			remainingEndPoints++;
		}

		Hud.ends = remainingEndPoints;

		if (remainingEndPoints == 0) {
			return true;
		}
		return false;
	}

	public void reset(TiledMap map) {
		// TODO Auto-generated method stub
		int id=0;
		boxes.clear();
		targets.clear();
		Hud.ends = 0;
		undo=false;
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				logicMap[i][j] = 0;
				if (i == 0 || j == 0 || i == getRows() - 1 || j == getCols() - 1) {
					logicMap[i][j] = 2;
				}
			}
		}

		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Wall(map, rect);
			logicMap[(int) (rect.x / 64)][(int) (rect.y / 64)] = 2;
		}

		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			int X=(int) (rect.x / 64);
			int Y=(int) (rect.y / 64);
			Box b = new Box(map, rect);
			b.setId(id++);
			boxes.add(b);
			logicMap[X][Y] = 3;
		}

		for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			targets.add(new EndPoint(map, rect));
			logicMap[(int) (rect.x / 64)][(int) (rect.y / 64)] = 4;
			Hud.ends++;
		}
		resetPlayer();
		logicMap[(int) (player.x / 64)][(int) (player.y / 64)] = 1;
	}

	private void resetPlayer() {
		// TODO Auto-generated method stub
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				if (logicMap[i][j]==0) {
					player.x=i*64;
					player.y=j*64;
					player.px=player.x;
					player.py=player.y;
					player.action = Action.STANDING;
					player.direction = Direction.DOWN;
				}
			}
		}
		
	}

	public boolean isUndo() {
		return undo;
	}

	public void setUndo(boolean undo) {
		this.undo = undo;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int[][] getLogicMap() {
		return logicMap;
	}

	public void setLogicMap(int[][] logicMap) {
		this.logicMap = logicMap;
	}

}

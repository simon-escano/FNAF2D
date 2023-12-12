package AI;

import main.Game;

import java.util.ArrayList;

public class Pathfinder {
    Game game;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(Game game) {
        this.game = game;
        createNodes();
    }

    public void createNodes() {
        nodes = new Node[game.map.cols][game.map.rows];
        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.cols; j++) {
                nodes[j][i] = new Node(j, i);
            }
        }
    }

    public void resetNodes() {
        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.cols; j++) {
                nodes[j][i].open = false;
                nodes[j][i].checked = false;
                nodes[j][i].solid = false;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        if (startCol > game.map.cols || startRow > game.map.rows) {
            startCol /= 2;
            startRow /= 2;
        }
        if (goalCol > game.map.cols || goalRow > game.map.rows) {
            goalCol /= 2;
            goalRow /= 2;
        }
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);
        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.cols; j++) {
                int tileNum = game.tileManager.mapTileNumber[j][i];
                if (game.tileManager.tiles[tileNum].collision) {
                    nodes[j][i].solid = true;
                }
                getCost(nodes[j][i]);
            }
        }
    }

    public void getCost(Node node) {
        int xDist = Math.abs(node.col - startNode.col);
        int yDist = Math.abs(node.row - startNode.row);
        node.gCost = xDist + yDist;

        xDist = Math.abs(node.col - goalNode.col);
        yDist = Math.abs(node.row - goalNode.row);
        node.hCost = xDist + yDist;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            if (row - 1 >= 0) {
                openNode(nodes[col][row-1]);
            }
            if (col - 1 >= 0) {
                openNode(nodes[col-1][row]);
            }
            if (row + 1 < game.map.rows) {
                openNode(nodes[col][row+1]);
            }
            if (col + 1 < game.map.cols) {
                openNode(nodes[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.isEmpty()) {
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}

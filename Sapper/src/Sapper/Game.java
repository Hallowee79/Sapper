package Sapper;

public class Game
{
   private Bomb bomb;
   private Flag flag;
   private GameStat state;

    public GameStat getState()
    {
        return state;
    }

    public Game (int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start ()
    {
       bomb.start();
       flag.start();
       state = GameStat.PLAYED;
    }
    //Размешение картинки втом или ином месте
    public Box getBox (Coord coord)
    {
        if (flag.get(coord) == Box.opened)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }
    //Левая кнопка мышки
    public void pressLeftButton (Coord coord)
    {
        if (gameOver ()) return;
        openBox (coord);
        checkWinner();
    }
    // Проверка на Победа
    private void checkWinner ()
    {
        if  (state == GameStat.PLAYED)
        {
            if (flag.getCountClosedBoxes() == bomb.getTotalBombs())
            {
                state = GameStat.WINNER;
            }
        }
    }
    //Значения
    private void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case opened : setOpenedToClosedBoxesAroundNumber(coord);return;
            case flaged : return;
            case closed :
                switch (bomb.get(coord))
                {
                    case zero: openBoxesAround (coord); return;
                    case bomb: openBombs (coord); return;
                    default: flag.setOpenedToBox(coord);  return;
                }

        }
    }
    //Помечает все бомбы при поражении
    private void openBombs(Coord bombed)
    {
        state = GameStat.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.bomb)
                flag.setOpenedToClosedBombBox (coord);
            else
                flag.setNobombToFlagedSafeBox (coord);
    }
    //Открывает клетки вокруг бомбы
    private void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsArround(coord))
            openBox(around);
    }
    // Правая кнопка мыши
    public void pressRightButton(Coord coord)
    {
        if (gameOver ()) return;
        flag.toggleFlagedToBox (coord);
    }
    //Проверка на Поражение
    private boolean gameOver()
    {
        if (state == GameStat.PLAYED)
            return false;
        return true;
    }

    //Проверка открытия закрытых ячеек вокруг числа
    private void setOpenedToClosedBoxesAroundNumber (Coord coord)
    {
        if (bomb.get(coord) != Box.bomb)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsArround(coord))
                    if (flag.get(around) == Box.closed)
                        openBox(around);
    }

}

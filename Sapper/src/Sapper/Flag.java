package Sapper;

class Flag
{
    private Matrix flagMap;
    private int countOfclosedBoxes;

    void start ()
    {
        flagMap = new Matrix(Box.closed);
        countOfclosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord)
    {
        return flagMap.get(coord);
    }
    //Открытие клетки
    public void setOpenedToBox(Coord coord)
    {
        flagMap.set(coord, Box.opened);
        countOfclosedBoxes--;
    }
    //Установка и убирания флага
    void toggleFlagedToBox (Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case flaged : setClosedToBox (coord); break;
            case closed : setFlagedToBomb(coord); break;
        }
    }
    //Убирания
    private void setClosedToBox(Coord coord)
    {
        flagMap.set(coord, Box.closed);
    }
    //Установка
    public void setFlagedToBomb(Coord coord)
    {
        flagMap.set(coord, Box.flaged);
    }
    //количества закрытых полей
    int getCountClosedBoxes()
    {
        return countOfclosedBoxes;
    }
    //Передаёт кординату где взорвалась бомба
    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord, Box.bombed);
    }
    //открывает все бомбы при поражении
    void setOpenedToClosedBombBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.closed)
            flagMap.set(coord, Box.opened);
    }
    //Неправильный флажок
    void setNobombToFlagedSafeBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.flaged)
            flagMap.set(coord, Box.nobomb);
    }
    //Открывает закрытые ячейки вокруг числа
    int getCountOfFlagedBoxesAround (Coord coord)
    {
        int count = 0;
        for (Coord around : Ranges.getCoordsArround(coord))
            if (flagMap.get(around) == Box.flaged)
                count++;
        return count;
    }
}

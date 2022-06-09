import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.sql.rowset.serial.SerialStruct;
import javax.swing.table.TableRowSorter;
import java.util.concurrent.atomic.AtomicBoolean;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Solution {
    static Essence essence = Parser.inputData.getEssence();
    private char[] playingFieldsChar;
    private static int[] playingFieldsInt;
    private static int[][] playingFieldsIntMatrix;
    private static int[][] auxiliaryMatrix;

    // 1-е действие
    private int[] charToInt(String strPlayingFields, Essence essence) {
        setPlayingFieldsChar(strPlayingFields.toCharArray());
        playingFieldsInt = new int[playingFieldsChar.length];
        for (int i = 0; i < playingFieldsChar.length; i++) {
            switch (playingFieldsChar[i]) {
                case 'S': playingFieldsInt[i] = essence.getSwamp();
                    break;
                case 'W': playingFieldsInt[i] = essence.getWater();
                    break;
                case 'T': playingFieldsInt[i] = essence.getThicket();
                    break;
                case 'P': playingFieldsInt[i] = essence.getPlain();
                    break;
            }
//            System.out.print(playingFieldsInt[i] + ", ");
        }
//        System.out.println();
        return playingFieldsInt;
    }

    // 2-е действие
    private int[][] arrayToMatrix(int[] playingFieldsInt) {
        int sizeSqr = (int) Math.sqrt(playingFieldsInt.length);
        playingFieldsIntMatrix = new int[sizeSqr][sizeSqr];
        System.out.println();
        for (int i = 0; i < playingFieldsInt.length; i++) {
            playingFieldsIntMatrix[i/sizeSqr][i%sizeSqr] = playingFieldsInt[i];
            System.out.print(playingFieldsIntMatrix[i / sizeSqr][i % sizeSqr] + " ");
            if (i%sizeSqr==sizeSqr-1) {
                System.out.println();
            }
        }
        System.out.println();
        return playingFieldsIntMatrix;
    }

    // 3-е действие
    private int[][] createToAuxiliaryMatrix(int[] playingFieldsInt){
        int sizeSqr = (int) Math.sqrt(playingFieldsInt.length);
        auxiliaryMatrix = new int[sizeSqr][sizeSqr];
        for (int i = 0; i < playingFieldsInt.length; i++) {
            auxiliaryMatrix[i / sizeSqr][i % sizeSqr] = Integer.MAX_VALUE;
//            System.out.print(auxiliaryMatrix[i / sizeSqr][i % sizeSqr] + " ");
//            if (i%sizeSqr==sizeSqr-1) {
//                System.out.println();
//            }
        }
        auxiliaryMatrix[0][0] = 0;
        return auxiliaryMatrix;
    }

    // 4-е действие (Алгоритм Дейкстры по поиску кратчайшего пути, гарантировано
    // находит кратчайший путь) Для нашей задачи (частного случая размерности
    // 4 на 4 у Существа можно опустить шаги вверх и влево, так как с указанными
    // весами и размерностью обход по контурам дешевле возвратов к полю вверх или влево
    public static int getResult(String strPlayingFields, String strRace){
        System.out.println();
        System.out.println(strPlayingFields);
        System.out.println();
        System.out.println(strRace);

        Solution solution = new Solution(strPlayingFields.toCharArray());

        solution.charToInt(Parser.inputData.getPlayingFields(), Solution.essence);
        solution.arrayToMatrix(solution.charToInt(Parser.inputData.getPlayingFields(), Solution.essence));
        solution.createToAuxiliaryMatrix(solution.charToInt(Parser.inputData.getPlayingFields(), Solution.essence));

        int sizeSqr = (int) Math.sqrt(strPlayingFields.length());
        System.out.println();

//        while (auxiliaryMatrix[sizeSqr-1][sizeSqr-1]== Integer.MAX_VALUE) {
//            for (int i = 0; i < auxiliaryMatrix.length; i++) {
//                for (int j = 0; j < auxiliaryMatrix[0].length; j++) {
//                    if (j < 3 && (auxiliaryMatrix[i][j + 1] > auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i][j + 1])) {
//                        auxiliaryMatrix[i][j + 1] = auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i][j + 1];
//                    }
//                    if (j > 0 && (auxiliaryMatrix[i][j - 1] > auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i][j - 1])) {
//                        auxiliaryMatrix[i][j - 1] = auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i][j - 1];
//                    }
//                    if (i < 3 && (auxiliaryMatrix[i + 1][j] > auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i + 1][j])) {
//                        auxiliaryMatrix[i + 1][j] = auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i + 1][j];
//                    }
//                    if (i > 0 && (auxiliaryMatrix[i - 1][j] > auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i - 1][j])) {
//                        auxiliaryMatrix[i - 1][j] = auxiliaryMatrix[i][j] + playingFieldsIntMatrix[i - 1][j];
//                    }
//                    System.out.println(auxiliaryMatrix[i][j]);
//                    if (auxiliaryMatrix[i][j]==sizeSqr-1){
//                        System.out.println();
//                    }
//                }
//            }
//        }
//        System.out.println();
//        System.out.println(auxiliaryMatrix[3][3]);
//        return auxiliaryMatrix[3][3];

        ////////////////////////////////
        for (int i = 0; i < strPlayingFields.length(); i++) {
            if (i % 4 < 3 && (auxiliaryMatrix[i / 4][i % 4 + 1] > auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4][i % 4 + 1])) {
                auxiliaryMatrix[i / 4][i % 4 + 1] = auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4][i % 4 + 1];
            }
            if (i % 4 > 0 && (auxiliaryMatrix[i / 4][i % 4 - 1] > auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4][i % 4 - 1])) {
                auxiliaryMatrix[i / 4][i % 4 - 1] = auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4][i % 4 - 1];
            }
            if (i / 4 < 3 && (auxiliaryMatrix[i / 4 + 1][i % 4] > auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4 + 1][i % 4])) {
                auxiliaryMatrix[i / 4 + 1][i % 4] = auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4 + 1][i % 4];
            }
            if (i / 4 > 0 && (auxiliaryMatrix[i / 4 - 1][i % 4] > auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4 - 1][i % 4])) {
                auxiliaryMatrix[i / 4 - 1][i % 4] = auxiliaryMatrix[i / 4][i % 4] + playingFieldsIntMatrix[i / 4 - 1][i % 4];
            }
            System.out.print(auxiliaryMatrix[i / sizeSqr][i % sizeSqr] + " ");
            if (i%sizeSqr==sizeSqr-1) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println(auxiliaryMatrix[sizeSqr-1][sizeSqr-1]);
        return auxiliaryMatrix[sizeSqr-1][sizeSqr-1];
    }
}

package jb.sudoku;

import java.util.List;

class PlayField {
    private int mFieldId;
    private PlayCell[] mCells;
    private boolean mPencilMode;
    private int mSelection;
    private int mSelectionRow;
    private int mSelectionColumn;

    private int[] mDigitCount = new int[10];

    PlayField() {
        int lCount;

        mFieldId = 0;
        mCells = new PlayCell[81];
        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount] = new PlayCell();
        }
        mDigitCount[0] = 81;
        for (lCount = 1; lCount < mDigitCount.length; lCount++) {
            mDigitCount[lCount] = 0;
        }
        mPencilMode = false;
        mSelection = 0;
        mSelectionRow = 0;
        mSelectionColumn = 0;
    }

    PlayField(int pFieldId, List<DbCell> pCells, int pSelection, boolean pPencil) {
        int lCount;
        DbCell lDbCell;

        mFieldId = pFieldId;
        mCells = new PlayCell[81];
        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount] = new PlayCell();
        }
        for (lCount = 0; lCount < pCells.size(); lCount++) {
            lDbCell = pCells.get(lCount);
            mCells[lDbCell.xCellNumber()].xInitPlayCell(lDbCell);
        }
        sDigitCount();
        mPencilMode = pPencil;
        mSelection = pSelection;
        mSelectionRow = mSelection / 9;
        mSelectionColumn = mSelection % 9;
    }

    PlayField(int pFieldId, PlayField pField){
        int lCount;

        mFieldId = pFieldId;
        mCells = new PlayCell[81];
        for (lCount = 0; lCount < mCells.length; lCount++){
            mCells[lCount] = new PlayCell(pField.mCells[lCount]);
        }
        mPencilMode = pField.mPencilMode;
        mSelection = pField.mSelection;
        mSelectionRow = mSelection / 9;
        mSelectionColumn = mSelection % 9;
        sDigitCount();
    }

    private void sDigitCount() {
        int lCount;

        for (lCount = 0; lCount < mDigitCount.length; lCount++) {
            mDigitCount[lCount] = 0;
        }
        for (lCount = 0; lCount < mCells.length; lCount++) {
            mDigitCount[mCells[lCount].xValue()]++;
        }
    }

    boolean xSolved() {
        return (mDigitCount[0] == 0) ? true : false;
    }

    boolean xEmptyField() {
        return (mDigitCount[0] == 81) ? true : false;
    }

    PlayCell[] xCells() {
        return mCells;
    }

    void xCells(Cell[] pCells) {
        int lCount;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount].xInitCell(pCells[lCount]);
        }
        mPencilMode = false;
        sDigitCount();
    }

    int xFieldId(){
        return mFieldId;
    }

    boolean xPencilMode() {
        return mPencilMode;
    }

    void xPencilFlip() {
        mPencilMode = !mPencilMode;
    }

    PlayCell xSelectedCell() {
        return mCells[mSelection];
    }

    PlayCell xCell(int pRow, int pColumn) {
        return mCells[(pRow * 9) + pColumn];
    }

    int xSelection() {
        return mSelection;
    }

    int xSelectionRow() {
        return mSelectionRow;
    }

    void xSelectionRow(int pRow) {
        if (pRow >= 0 && pRow <= 8) {
            mSelectionRow = pRow;
            mSelection = (mSelectionRow * 9) + mSelectionColumn;
        }
    }

    int xSelectionColumn() {
        return mSelectionColumn;
    }

    void xSelectionColumn(int pColumn) {
        if (pColumn >= 0 && pColumn <= 8) {
            mSelectionColumn = pColumn;
            mSelection = (mSelectionRow * 9) + mSelectionColumn;
        }
    }

    int xDigitCount(int pDigit) {
        if (pDigit >= 1 && pDigit <= 9) {
            return mDigitCount[pDigit];
        } else {
            return 0;
        }
    }

    void xResetField() {
        int lCount;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount].xReset();
        }
        sDigitCount();
        mPencilMode = false;
    }

    void xFixField() {
        int lCount;
        Cell lCell;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            lCell = mCells[lCount];
            if (lCell.xValue() == 0) {
                lCell.xFixed(false);
            } else {
                lCell.xFixed(true);
            }
        }
        sDigitCount();
    }

    String xGame() {
        StringBuilder lBuilder;
        int lCount;

        lBuilder = new StringBuilder();
        for (lCount = 0; lCount < mCells.length; lCount++) {
            if (mCells[lCount].xFixed()) {
                lBuilder.append(mCells[lCount].xValue());
            } else {
                lBuilder.append("0");
            }
        }
        return lBuilder.toString();
    }

    void xSetCellValue(int pValue) {
        PlayCell lCell;

        lCell = mCells[mSelection];
        if (lCell.xValue() == pValue) {
            lCell.xValueReset();
            mDigitCount[0]++;
            mDigitCount[pValue]--;
        } else {
            if (lCell.xValue() == 0) {
                mDigitCount[0]--;
            } else {
                mDigitCount[lCell.xValue()]--;
            }
            lCell.xValue(pValue);
            mDigitCount[pValue]++;
        }
    }


    void xResetConflicts() {
        int lCount;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount].xConflict(false);
        }
    }

    void xInitPencil() {
        int lCount;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount].xSetPencils();
        }
    }

    void xClearPencil() {
        int lCount;

        for (lCount = 0; lCount < mCells.length; lCount++) {
            mCells[lCount].xClearPencils();
        }
    }
}
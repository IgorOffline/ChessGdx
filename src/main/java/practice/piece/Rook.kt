package practice.piece

import practice.GameMaster
import practice.board.*
import practice.board.ui.LetterNumber

class Rook {
    companion object {
        fun rookMovement(board: Board, gameMaster: GameMaster, square: Square) : Boolean {
            return rookMovementTop(board, gameMaster, square) ||
                    rookMovementBottom(board, gameMaster, square) ||
                    rookMovementLeft(board, gameMaster, square) ||
                    rookMovementRight(board, gameMaster, square)
        }

        private fun rookMovementTop(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            for (i in square.number.index + 1 .. 7) {
                val rookMovementTopBottom = rookMovementTopBottom(board, gameMaster, square, i)
                rookMovementTopBottom?.let {
                    return rookMovementTopBottom
                }
            }

            return false
        }

        private fun rookMovementBottom(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            for (i in square.number.index - 1 downTo 0) {
                val rookMovementTopBottom = rookMovementTopBottom(board, gameMaster, square, i)
                rookMovementTopBottom?.let {
                    return rookMovementTopBottom
                }
            }

            return false
        }

        private fun rookMovementLeft(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            for (i in square.letter.index - 1 downTo 0) {
                val rookMovementLeftRight = rookMovementLeftRight(board, gameMaster, square, i)
                rookMovementLeftRight?.let {
                    return rookMovementLeftRight
                }
            }

            return false
        }

        private fun rookMovementRight(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            for (i in square.letter.index + 1 .. 7) {
                val rookMovementLeftRight = rookMovementLeftRight(board, gameMaster, square, i)
                rookMovementLeftRight?.let {
                    return rookMovementLeftRight
                }
            }

            return false
        }

        private fun rookMovementTopBottom(board: Board, gameMaster: GameMaster, square: Square, i: Int) : Boolean? {

            val letter = square.letter
            val number = LetterNumber.getNumberEnum(i)

            return rookMovementTopBottomLeftRight(board, gameMaster, letter, number)
        }

        private fun rookMovementLeftRight(board: Board, gameMaster: GameMaster, square: Square, i: Int) : Boolean? {

            val letter = LetterNumber.getLetterEnum(i)
            val number = square.number

            return rookMovementTopBottomLeftRight(board, gameMaster, letter, number)
        }

        private fun rookMovementTopBottomLeftRight(board: Board, gameMaster: GameMaster, letter: Letter, number: practice.board.Number) : Boolean? {

            val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
            val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            var breakEnemy = false

            board.board.forEach board@ {
                if (it.letter == letter && it.number == number) {
                    if (it.pieceColor == pieceColorFriendly) {
                        return false
                    } else if (it.pieceColor == pieceColorEnemy) {
                        breakEnemy = true
                        return@board
                    }
                }
            }
            if (gameMaster.equalLetterNumber(letter, number)) {
                return true
            }
            if (breakEnemy) {
                return false
            }

            return null
        }

        fun rookChecksKingTop(board: Board, sqKing: Square, rookPieceColor: PieceColor): Boolean {
            for (i in sqKing.number.index - 1 downTo 0) {
                board.board.forEach board@ {
                    val letter = sqKing.letter
                    val number = LetterNumber.getNumberEnum(i)
                    if (it.letter == letter && it.number == number) {
                        if (it.piece == Piece.ROOK && it.pieceColor == rookPieceColor) {
                            return true
                        } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                            return false
                        }
                    }
                }
            }

            return false
        }

        fun rookChecksKingBottom(board: Board, sqKing: Square, rookPieceColor: PieceColor): Boolean {
            for (i in sqKing.number.index + 1 .. 7) {
                board.board.forEach board@ {
                    val letter = sqKing.letter
                    val number = LetterNumber.getNumberEnum(i)
                    if (it.letter == letter && it.number == number) {
                        if (it.piece == Piece.ROOK && it.pieceColor == rookPieceColor) {
                            return true
                        } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                            return false
                        }
                    }
                }
            }

            return false
        }

        fun rookChecksKingLeft(board: Board, sqKing: Square, rookPieceColor: PieceColor): Boolean {
            for (i in sqKing.letter.index - 1 downTo 0) {
                board.board.forEach board@ {
                    val letter = LetterNumber.getLetterEnum(i)
                    val number = sqKing.number
                    if (it.letter == letter && it.number == number) {
                        if (it.piece == Piece.ROOK && it.pieceColor == rookPieceColor) {
                            return true
                        } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                            return false
                        }
                    }
                }
            }

            return false
        }

        fun rookChecksKingRight(board: Board, sqKing: Square, rookPieceColor: PieceColor): Boolean {
            for (i in sqKing.letter.index + 1 .. 7) {
                board.board.forEach board@ {
                    val letter = LetterNumber.getLetterEnum(i)
                    val number = sqKing.number
                    if (it.letter == letter && it.number == number) {
                        if (it.piece == Piece.ROOK && it.pieceColor == rookPieceColor) {
                            return true
                        } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                            return false
                        }
                    }
                }
            }

            return false
        }
    }
}
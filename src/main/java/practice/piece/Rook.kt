package practice.piece

import practice.GameMaster
import practice.board.Board
import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
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

            val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
            val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            var breakEnemy = false

            for (i in square.number.index + 1 .. 7) {
                val letter = square.letter
                val number = LetterNumber.getNumberEnum(i)
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
            }

            return false
        }

        private fun rookMovementBottom(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
            val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            var breakEnemy = false

            for (i in square.number.index - 1 downTo 0) {
                val letter = square.letter
                val number = LetterNumber.getNumberEnum(i)
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
            }

            return false
        }

        private fun rookMovementLeft(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
            val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            var breakEnemy = false

            for (i in square.letter.index - 1 downTo 0) {
                val letter = LetterNumber.getLetterEnum(i)
                val number = square.number
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
            }

            return false
        }

        private fun rookMovementRight(board: Board, gameMaster: GameMaster, square: Square) : Boolean {

            val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
            val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            var breakEnemy = false

            for (i in square.letter.index + 1 .. 7) {
                val letter = LetterNumber.getLetterEnum(i)
                val number = square.number
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
            }

            return false
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
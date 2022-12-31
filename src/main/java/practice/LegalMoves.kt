package practice

import practice.board.*
import practice.piece.*

data class LegalMoves(var legalMoves: Map<Square, List<Square>>) {

    fun calculate(gameMaster: GameMaster, firstCalculation: Boolean) {

        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val oppositePieceColor = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        gameMaster.whiteKingInCheck = false
        gameMaster.blackKingInCheck = false

        var king: Square? = null

        gameMaster.board.board.forEach {
            if (it.piece == Piece.KING && it.pieceColor == pieceColor) {
                king = it
                return@forEach
            }
        }

        val kingLegalMoves = King.kingMoves(king!!, gameMaster.board).toMutableList()

        val kingAndRooksLegalMoves = mutableMapOf<Square, List<Square>>()

        gameMaster.board.board.forEach { boardSquare ->
            if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == pieceColor) {
                val rookMoves = Rook.rookMoves(boardSquare, gameMaster.board)
                kingAndRooksLegalMoves[boardSquare] = rookMoves.squares
                kingLegalMoves.removeIf { it.letter == boardSquare.letter && it.number == boardSquare.number }
            } else if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == oppositePieceColor) {
                val oppositeRookMoves = Rook.rookMoves(boardSquare, gameMaster.board)
                kingLegalMoves.removeAll(oppositeRookMoves.squares)
                if (oppositeRookMoves.opponentsKingInCheck) {
                    gameMaster.whiteKingInCheck = pieceColor == PieceColor.WHITE
                    gameMaster.blackKingInCheck = pieceColor == PieceColor.BLACK
                }
            }
        }

        kingAndRooksLegalMoves[king!!] = kingLegalMoves

        if (gameMaster.whiteKingInCheck && firstCalculation) {
            val illegalMoves = mutableListOf<Square>()
            kingLegalMoves.forEach { kingLegalMove ->
                val newBoard = gameMaster.board.deepCopy()
                val newLegalMoves = LegalMoves(emptyMap())
                val newGameMaster = GameMaster(newBoard, newLegalMoves)
                val kingNewBoard = newBoard.board.find { it.letter == king!!.letter && it.number == king!!.number }
                newGameMaster.fromSquare = kingNewBoard!!
                val toSquareNewBoard = newBoard.board.find { it.letter == kingLegalMove.letter && it.number == kingLegalMove.number }
                newGameMaster.toSquare = toSquareNewBoard!!
                //println("${newGameMaster.fromSquare}, ${newGameMaster.toSquare}")
                newGameMaster.move()
                newLegalMoves.calculate(newGameMaster, false)
                if (newGameMaster.whiteKingInCheck) {
                    illegalMoves.add(toSquareNewBoard)
                }
            }
            if (illegalMoves.isNotEmpty()) {
                illegalMoves.forEach { illegalMove ->
                    val kingLegalMove = kingLegalMoves.find { it.letter == illegalMove.letter && it.number == illegalMove.number }
                    kingLegalMoves.remove(kingLegalMove)
                    println("illegalMove= $kingLegalMove")
                }

                kingAndRooksLegalMoves[king!!] = kingLegalMoves
            }
        }

        legalMoves = kingAndRooksLegalMoves

        if (firstCalculation) {
            var legalMovesToStr = "$pieceColor: "

            legalMoves.keys.forEach {
                legalMovesToStr += "${legalMoves[it]!!.size}, "
            }

            println("$legalMovesToStr, white/blackKingInCheck= ${gameMaster.whiteKingInCheck}/${gameMaster.blackKingInCheck}")
        }
    }
}

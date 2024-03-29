package practice.legal

import practice.GameMaster
import practice.board.*
import practice.piece.*

data class LegalMoves(var legalMoves: Map<Square, List<Square>>) {

    fun calculate(gameMaster: GameMaster) {

        val phase1LegalMoves = mutableMapOf<Square, List<Square>>()
        val phase2LegalMoves = mutableMapOf<Square, List<Square>>()

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

        gameMaster.board.board.forEach { boardSquare ->
            if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == pieceColor) {
                val rookMoves = Rook.rookMoves(boardSquare, gameMaster.board)
                phase1LegalMoves[boardSquare] = rookMoves.squares
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

        phase1LegalMoves[king!!] = kingLegalMoves

        if (!gameMaster.whiteKingInCheck && !gameMaster.blackKingInCheck) {
            legalMoves = phase1LegalMoves
        } else {
            phase1LegalMoves.keys.forEach { piece ->
                val legalMoves = phase1LegalMoves[piece]!!
                val prunedMoves = pruneMoves(gameMaster, legalMoves, piece)
                phase2LegalMoves[piece] = prunedMoves
            }

            legalMoves = phase2LegalMoves

            checkmateCheck(gameMaster)
        }

        var legalMovesToStr = "$pieceColor: "

        legalMoves.keys.forEach {
            legalMovesToStr += "${legalMoves[it]!!.size}, "
        }

        println("$legalMovesToStr, white/blackKingInCheck= ${gameMaster.whiteKingInCheck}/${gameMaster.blackKingInCheck}")
    }

    private fun pruneMoves(gameMaster: GameMaster, legalMoves: List<Square>, piece: Square): MutableList<Square> {

        val prunedMoves = mutableListOf<Square>()

        legalMoves.forEach { legalMove ->
            val newBoard = gameMaster.board.deepCopy()
            val newLegalMoves = LegalMoves(emptyMap())
            val newGameMaster = GameMaster(newBoard, newLegalMoves)
            val pieceNewBoard = newBoard.board.find { it.letter == piece.letter && it.number == piece.number }
            newGameMaster.fromSquare = pieceNewBoard!!
            val toSquareNewBoard = newBoard.board.find { it.letter == legalMove.letter && it.number == legalMove.number }
            newGameMaster.toSquare = toSquareNewBoard!!
            newGameMaster.move()
            Pruning.prune(newGameMaster, piece.pieceColor)

            val pruneWhite = piece.pieceColor == PieceColor.WHITE && !newGameMaster.whiteKingInCheck
            val pruneBlack = piece.pieceColor == PieceColor.BLACK && !newGameMaster.blackKingInCheck

            if (pruneWhite || pruneBlack) {
                prunedMoves.add(toSquareNewBoard)
            }
        }

        return prunedMoves
    }

    private fun checkmateCheck(gameMaster: GameMaster) {

        var legalMovesCounter = 0

        legalMoves.values.forEach { piecesLegalMoves -> legalMovesCounter += piecesLegalMoves.size }

        if (legalMovesCounter == 0) {
            if (gameMaster.whiteKingInCheck) {
                gameMaster.whiteKingCheckmated = true
            } else {
                gameMaster.blackKingCheckmated = true
            }
        }
    }
}

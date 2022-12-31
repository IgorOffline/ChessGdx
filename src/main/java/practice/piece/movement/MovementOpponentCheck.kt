package practice.piece.movement

import practice.board.Square

data class MovementOpponentCheck(val squares: List<Square>, val opponentsKingInCheck: Boolean)

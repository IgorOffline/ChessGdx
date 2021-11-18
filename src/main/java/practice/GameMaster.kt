package practice

import practice.board.Square

data class GameMaster(var fromSquare: Square? = null,
                      var toSquare: Square? = null,
                      var whiteToMove: Boolean = true,
                      var whiteKingInCheck: Boolean = false,
                      var blackKingInCheck: Boolean = false)
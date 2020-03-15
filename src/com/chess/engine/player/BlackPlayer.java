package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player {
	
	public BlackPlayer(final Board board, 
	                   final Collection<Move> whiteStandardLegaltsMoves, 
	                   final Collection<Move> blackStandardLegaltsMoves ) {
		super(board, blackStandardLegaltsMoves, whiteStandardLegaltsMoves);
}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}
	
	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}
	
	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, 
			                                        final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			//Black king side castle.
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.caculateAttackOnTile(5, opponentsLegals).isEmpty() &&
					   Player.caculateAttackOnTile(6, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(this.board,
	                                                           this.playerKing, 
	                                                           6, 
	                                                          (Rook)rookTile.getPiece(), 
	                                                           rookTile.getTileCoordinate(), 
	                                                           5));				
					}
				}
			}
			//Black queen side castle.
			if(!this.board.getTile(1).isTileOccupied() && 
			   !this.board.getTile(2).isTileOccupied() && 
			   !this.board.getTile(3).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
				   Player.caculateAttackOnTile(2, opponentsLegals).isEmpty() &&
				   Player.caculateAttackOnTile(3, opponentsLegals).isEmpty() &&
				   rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new QueenSideCastleMove(this.board,
                                                            this.playerKing, 
                                                            2, 
                                                           (Rook)rookTile.getPiece(), 
                                                            rookTile.getTileCoordinate(), 
                                                            3));				
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}


}

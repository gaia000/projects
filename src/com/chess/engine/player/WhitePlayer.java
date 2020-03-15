package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class WhitePlayer extends Player {
	
	public WhitePlayer(final Board board, 
			           final Collection<Move> whiteStandardLegaltsMoves, 
			           final Collection<Move> blackStandardLegaltsMoves ) {
		super(board, whiteStandardLegaltsMoves, blackStandardLegaltsMoves);
		
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}
	

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, 
			                                        final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.caculateAttackOnTile(61, opponentsLegals).isEmpty() &&
					   Player.caculateAttackOnTile(62, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook()) {
						kingCastles.add(new KingSideCastleMove(this.board,
								                               this.playerKing, 
								                               62, 
								                               (Rook)rookTile.getPiece(), 
								                               rookTile.getTileCoordinate(), 
								                               61));
					}
				}
			}
			//White queen side castle.
			if(!this.board.getTile(59).isTileOccupied() && 
			   !this.board.getTile(58).isTileOccupied() && 
			   !this.board.getTile(57).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
				   Player.caculateAttackOnTile(58, opponentsLegals).isEmpty() &&
				   Player.caculateAttackOnTile(59, opponentsLegals).isEmpty() &&
				   rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new QueenSideCastleMove(this.board,
                                                            this.playerKing, 
                                                            58, 
                                                            (Rook)rookTile.getPiece(), 
                                                            rookTile.getTileCoordinate(), 
                                                            59));				
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}


}

package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import static com.chess.engine.board.Move.*;


public class Bishop extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = { -9,-7,7,9 };


	public Bishop( final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.BISHOP, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		for (final int candidateDestinationOffset : CANDIDATE_MOVE_VECTOR_COORDINATE ) {
			 int candidateDestinationCoordinate = this.piecePosition;
			 while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				 if (isFirstColumnExclusion( candidateDestinationCoordinate,candidateDestinationOffset) ||
						 isEighthColumnExclusion( candidateDestinationCoordinate,candidateDestinationOffset) ) {
					 break;
				 }
				 
				 candidateDestinationCoordinate += candidateDestinationOffset;
				 
				 if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					 final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
						if (!candidateDestinationTile.isTileOccupied()) {
							legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));	
						}
						else {
							final Piece pieceAtDestination = candidateDestinationTile.getPiece();
							final Alliance pieceAlliance= pieceAtDestination.getPieceAlliance();
							if (this.pieceAlliance!= pieceAlliance) {
								legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));	
							}
							break;
						}
				 }
			 }
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7);
	}

	@Override
	public Bishop movePiece(final Move move) {
		return new Bishop (move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

}

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

public class Knight extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-17,-15,-10,-6,6,10,15,17};

	public Knight(final Alliance pieceAlliance,
			      final int piecePosition) {
		super(PieceType.KNIGHT, pieceAlliance, piecePosition, true);
	}
	
	public Knight(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
    super(PieceType.KNIGHT, pieceAlliance,piecePosition, isFirstMove);
    }


	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move>legalMoves = new ArrayList<>();
		
		for (final int currentCandidate : CANDIDATE_MOVE_COORDINATE ) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(this.piecePosition,currentCandidate) || 
						isSecondColumnExclusion(this.piecePosition,currentCandidate) ||
						isSeventhColumnExclusion(this.piecePosition,currentCandidate) ||
						isEighthColumnExclusion(this.piecePosition,currentCandidate)){
					continue;
				}
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if (!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));	
				}
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance= pieceAtDestination.getPieceAlliance();
					if (this.pieceAlliance!= pieceAlliance) {
						legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));	
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public Knight movePiece(final Move move) {
		return new Knight (move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	@Override 
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || 
				candidateOffset == 6 || candidateOffset == 15);
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || 
				candidateOffset == 6);
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || 
				candidateOffset == -6);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ( candidateOffset == -15 || 
				candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17 );
	}

}
